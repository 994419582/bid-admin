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
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import lombok.AllArgsConstructor;
import org.bifj.protocol.core.methods.request.Transaction;
import org.bifj.utils.Convert;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Notice Feign
 *
 * @author Chill
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class SendIntegralClient implements ISendIntegralClient {


	BIFClientTemplate template;
	BifProperties bifProperties;

	/**
	 * 执行定时任务
	 *
	 * @return
	 */
	@GetMapping(API_PREFIX + "/sendIntegral")
	public  R sendIntegral(@RequestParam("bid") String bid){
		String from =template.privateKey2Bid(bifProperties.loadMsgPrivate);
		BigDecimal value = Convert.toWei(BigDecimal.ONE, Convert.Unit.BIFER);//new BigInteger("18600000000000000000000");
		BigInteger balance= template.getBalance(from);
		if (balance.compareTo(value.toBigInteger())<0){
			return R.fail("账户余额不足");
		}
		Transaction transaction = new Transaction(from, bid, value.toBigInteger(), null);
		String hash = template.sendRawTransaction(bifProperties.chainId,bifProperties.loadMsgPrivate,transaction);
		if (hash!=null){
			return R.success("发送成功");
		}
		return R.fail("发送失败");
	}



}
