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
package cn.teleinfo.bidadmin.blockchain.service.impl;

import cn.teleinfo.bidadmin.blockchain.bifj.abi.document.Go_Document;
import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocument;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentAttr;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentAuth;
import cn.teleinfo.bidadmin.blockchain.entity.BidDocumentPuk;
import cn.teleinfo.bidadmin.blockchain.mapper.BidDocumentMapper;
import cn.teleinfo.bidadmin.blockchain.service.IBidDocumentService;
import cn.teleinfo.bidadmin.blockchain.vo.BidDocumentVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bifj.abi.datatypes.Utf8String;
import org.bifj.abi.datatypes.generated.Uint64;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Service
public class BidDocumentServiceImpl extends ServiceImpl<BidDocumentMapper, BidDocument> implements IBidDocumentService {


    @Autowired
    BidDocumentPukServiceImpl pukService;
    @Autowired
    BidDocumentAuthServiceImpl authService;
    @Autowired
    BidDocumentAttrServiceImpl attrService;

    @Autowired
    private Go_Document document;
    @Autowired
    private BIFClientTemplate bifClientTemplate;

    @Override
    public IPage<BidDocumentVO> selectBidDocumentPage(IPage<BidDocumentVO> page, BidDocumentVO bidDocument) {
        return page.setRecords(baseMapper.selectBidDocumentPage(page, bidDocument));
    }

    /**
     * @return
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     */
//	@Cacheable(value = "bid", key = "#entity.getBid()", unless = "#entity eq null ")
    public BidDocument saveOrUpdateBID(BidDocument entity) {
        super.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public BidDocument FindDDOByType(int type, String address) {
        if (StringUtil.isEmpty(address)) {
            return null;
        }
        try {
            BigInteger balanceBI = bifClientTemplate.getBalance(address);

            Utf8String documentString = document.FindDDOByType(new Uint64(0), new Utf8String(address)).sendAsync().get();

            if (balanceBI != null && documentString != null) {
                JSONObject jsonObject = JSONObject.parseObject(documentString.getValue());

                BidDocument document = new BidDocument();

                document.setBalance(balanceBI.toString());

                if (jsonObject.getString("Id").equals("0000000000000000000000000000000000000000")) {
                    document.setBid(address);
                    document.setContexts("https://w3id.org/future-method/v1");
                    document.setIsEnable(1);
                    document.setName(address);
                    document.setType(1);
                } else {
                    document.setBid(jsonObject.getString("Id"));
                    document.setContexts(jsonObject.getString("Context"));
                    document.setIsEnable(jsonObject.getInteger("IsEnable"));
                    document.setName(jsonObject.getString("Name"));
                    document.setType(jsonObject.getInteger("Type"));
                }
                return document;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String FindDDOByType(String address) {
        Utf8String documentString=new Utf8String("");
        try {
            documentString = document.FindDDOByType(new Uint64(0), new Utf8String(address)).sendAsync().get();
            JSONObject jsonObject = JSONObject.parseObject(documentString.getValue());
            if (jsonObject.getString("Id").equals("0000000000000000000000000000000000000000")) {
                return "";
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return documentString.toString();
    }

    /**
     * @param bid 主键id
     * @return
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * key 缓存在redis中的key
     * unless 表示条件表达式成立的话不放入缓存
     */
//	@Cacheable(value = "bid", key = "#bid", unless = "#result eq null ")
    public BidDocument getByBId(String bid) {
        return baseMapper.findDocumentByBId(bid);
    }

    //	@Transactional
    public void saveDocument(String jsonString, String address, String balance) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        BidDocument document = getByBId(address);
        if (document == null) {
            document = new BidDocument();
        }
        document.setBalance(balance);
        if (jsonObject.getString("Id").equals("0000000000000000000000000000000000000000")) {
            document.setBid(address);
            document.setContexts("https://w3id.org/future-method/v1");
            document.setIsEnable(1);
            document.setName(address);
            document.setType(1);
        } else {
            document.setBid(jsonObject.getString("id"));
            document.setContexts(jsonObject.getString("@context"));
            document.setIsEnable(jsonObject.getInteger("isEnable"));
            document.setName(jsonObject.getString("name"));
            document.setType(jsonObject.getInteger("type"));
        }

        BidDocument documentQuery = getByBId(document.getBid());

        if (documentQuery == null) {
            baseMapper.insert(document);
        } else {
            document.setId(documentQuery.getId());
            baseMapper.updateById(document);
        }

        JSONArray puk = jsonObject.getJSONArray("publicKeys");
        List<BidDocumentPuk> puks = new ArrayList<>();

        for (Object json : puk) {
            BidDocumentPuk d = new BidDocumentPuk();
            JSONObject j = (JSONObject) json;
            d.setId(j.getString("keyId"));
            d.setOwner(jsonObject.getString("id"));
            d.setAuthority(j.getString("authority"));
            d.setController(j.getString("controller"));
            d.setPublicKey(j.getString("publicKey"));
            d.setType(j.getString("type"));
            puks.add(d);
        }
        if (!puks.isEmpty()) {
            pukService.saveBatchDocumentPuks(puks);
        }

        JSONArray auth = jsonObject.getJSONArray("authentications");
        List<BidDocumentAuth> auths = new ArrayList<>();
        for (Object json : auth) {
            BidDocumentAuth d = new BidDocumentAuth();
            JSONObject j = (JSONObject) json;
            d.setId(j.getString("proofId"));
            d.setOwner(jsonObject.getString("id"));
            d.setProofID(j.getString("proofId"));
            d.setPublicKey(j.getString("publicKey"));
            d.setType(j.getString("type"));
            auths.add(d);
        }
        if (!auths.isEmpty()) {
            authService.saveBatchDocumentAuths(auths);
        }

        JSONArray attr = jsonObject.getJSONArray("attributes");
        List<BidDocumentAttr> attrs = new ArrayList<>();
        for (Object json : attr) {
            BidDocumentAttr d = new BidDocumentAttr();
            JSONObject j = (JSONObject) json;
            d.setOwner(jsonObject.getString("id"));

            d.setType(j.getString("attrType"));
            d.setType(j.getString("value"));
            attrs.add(d);
        }
        if (!attrs.isEmpty()) {
            attrService.saveBatchDocumentAttrs(attrs);
        }

    }
}
