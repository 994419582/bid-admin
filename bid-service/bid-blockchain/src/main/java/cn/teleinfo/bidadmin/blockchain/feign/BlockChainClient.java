/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.teleinfo.bidadmin.blockchain.feign;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import cn.teleinfo.bidadmin.blockchain.document.BlockDocument;
import cn.teleinfo.bidadmin.blockchain.document.EventDocument;
import cn.teleinfo.bidadmin.blockchain.document.TranscationDocument;
import cn.teleinfo.bidadmin.blockchain.entity.Block;
import cn.teleinfo.bidadmin.blockchain.entity.Events;
import cn.teleinfo.bidadmin.blockchain.entity.Transactions;
import cn.teleinfo.bidadmin.blockchain.es.service.BlockSearchService;
import cn.teleinfo.bidadmin.blockchain.es.service.EventSearchService;
import cn.teleinfo.bidadmin.blockchain.es.service.TranscationSearchService;
import cn.teleinfo.bidadmin.blockchain.service.IBlockService;
import cn.teleinfo.bidadmin.blockchain.service.IDposPullInfoService;
import cn.teleinfo.bidadmin.blockchain.service.IEventsService;
import cn.teleinfo.bidadmin.blockchain.service.ITranscationsService;
import cn.teleinfo.bidadmin.common.tool.BeanUtils;
import cn.teleinfo.bidadmin.common.tool.DateUtils;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.bifj.protocol.core.methods.response.CoreBlock;
import org.bifj.protocol.core.methods.response.TransactionReceipt;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Notice Feign
 *
 * @author Chill
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class BlockChainClient implements IBlockChainClient {


	 IBlockService service;
	 ITranscationsService transcationsService;
	 IEventsService eventsService;

	 BlockSearchService blockSearchService;
	 EventSearchService eventSearchService;
	 TranscationSearchService transcationSearchService;
	 BIFClientTemplate template;
	IDposPullInfoService infoService;

	/**
	 * 执行定时任务
	 *
	 * @return
	 */
	@GetMapping(API_PREFIX + "/executeTask")
//	@Scheduled(cron="0/2 * * * * ?")
	public R executeTask() {
		Integer num = service.findLastNumber();
		if (num ==null){
			num =0;
		}
//		num=294;
		try {
			CoreBlock.Block coreBlock = template.getBlockByNumber(BigInteger.valueOf(num + 1), true);
			if (coreBlock != null) {
				Block block = new Block();
				block.setNumber(coreBlock.getNumber().longValue());
				block.setHash(coreBlock.getHash());
				block.setParentHash(coreBlock.getParentHash());
				block.setNonce(coreBlock.getNonce() + "");
				block.setSha3Uncles(coreBlock.getSha3Uncles());
				block.setLogsBloom(coreBlock.getLogsBloom());
				block.setAuthor(coreBlock.getAuthor());
				block.setMiner(coreBlock.getMiner());
				block.setMixHash(coreBlock.getMixHash());
				block.setDifficulty(coreBlock.getDifficulty() + "");
				block.setTotalDifficulty(coreBlock.getTotalDifficulty() + "");
				block.setExtraData(coreBlock.getExtraData());
				block.setSize(coreBlock.getSize() + "");
				block.setGasLimit(coreBlock.getGasLimit() + "");
				block.setReceiptsRoot(coreBlock.getReceiptsRoot());
				block.setStateRoot(coreBlock.getStateRoot());
				block.setTransactionsRoot(coreBlock.getTransactionsRoot());
				block.setGasUsed(coreBlock.getGasUsed() + "");
				block.setTimestamp(DateUtils.date2LocalDateTime(coreBlock.getTimestamp().longValue()));
				block.setTransactionCount(coreBlock.getTransactions().size());
				infoService.pullDocumentInfo(block.getMiner());
				boolean flag = service.save(block);
				List <Transactions> transactions = new ArrayList<Transactions>();
				List <Events> eventList = new ArrayList<Events>();
				if (flag) {
					List<CoreBlock.TransactionResult> transactionResults = coreBlock.getTransactions();
					for (CoreBlock.TransactionResult trans : transactionResults) {
						//组装交易事件
						Transactions transaction = new Transactions();
						transaction = JSON.parseObject(JSON.toJSON(trans).toString(), Transactions.class);
						TransactionReceipt receipt = template.getTransactionReceipt(transaction.getHash());
						transaction.setTxfrom(receipt.getFrom());
						transaction.setTxto(receipt.getTo());
						transaction.setCreateDate(block.getTimestamp());
						transactions.add(transaction);

						//组装交易收据
						Events events = new Events();
						events = JSON.parseObject(JSON.toJSON(receipt).toString(), Events.class);
						events.setEvents(receipt.getLogs().toString());
						events.setTxfrom(receipt.getFrom());
						events.setTxto(receipt.getTo());
						eventList.add(events);
						//获取交易人的ddo文档
						infoService.pullDocumentInfo(events.getTxfrom());
						infoService.pullDocumentInfo(events.getTxto());
					}

				} else {
					return R.fail("获取失败");
				}

				transcationsService.saveBatch(transactions);
				eventsService.saveBatch(eventList);
				BlockDocument dBlockDocument = new BlockDocument();
				BeanUtils.copyBeanProp(dBlockDocument,block );
				dBlockDocument.setId(block.getHash());
				dBlockDocument.setBlockHash(block.getHash());
				dBlockDocument.setBlockNumber(block.getNumber());
				dBlockDocument.setTimestamp(DateUtils.formatLocalDate(block.getTimestamp()));//block.getTimestamp().format("yyyy-MM-dd HH:mm:ss");
				blockSearchService.save(dBlockDocument);
				List<TranscationDocument> esTrans=new ArrayList();
				transactions.forEach(x ->{
					TranscationDocument eTransac = new TranscationDocument();
					BeanUtils.copyBeanProp(eTransac,x );
					eTransac.setId(x.getHash());
					eTransac.setCreateDate(DateUtils.formatLocalDate(x.getCreateDate()));
					esTrans.add(eTransac);
				});
				transcationSearchService.save(esTrans);
				List<EventDocument> esEvent=new ArrayList();
				for(Events t :eventList){
					EventDocument event = new EventDocument();
					event.setId(t.getTransactionHash());
					BeanUtils.copyBeanProp(event,t );
					esEvent.add(event);
				}
				eventSearchService.save(esEvent);
//				client.saveEvent(eventList);

			}
		} catch(Exception e){
				System.out.println("重复保存：" + e.getMessage());
		}
		return R.success("获取成功");
	}



}
