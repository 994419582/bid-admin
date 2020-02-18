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

import cn.teleinfo.bidadmin.blockchain.entity.Certificate;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateApply;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateInfo;
import cn.teleinfo.bidadmin.blockchain.mapper.CertificateApplyMapper;
import cn.teleinfo.bidadmin.blockchain.properties.CertificateProperties;
import cn.teleinfo.bidadmin.blockchain.service.ICertificateApplyService;
import cn.teleinfo.bidadmin.blockchain.service.ICertificateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bifj.crypto.Credentials;
import org.bifj.crypto.ECKeyPair;
import org.bifj.crypto.Keys;
import org.bifj.crypto.Sign;
import org.bifj.utils.Numeric;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2019-12-09
 */
@Service
public class CertificateApplyServiceImpl extends ServiceImpl<CertificateApplyMapper, CertificateApply> implements ICertificateApplyService {

    @Autowired
    private CertificateProperties properties;
    @Autowired
    private ICertificateService certificateService;

    @Override
    public R<CertificateApply> issuerCertificate(String name, String idCardNumber, String bid, String publicKey) {

        // 创建证书，然后证书有五个字段，分别准备
        CertificateApply certificate = new CertificateApply();
        // 第一个：id
        String id = Keys.createBID(bid);
        certificate.setId(id);

        // 第二个：Context
        certificate.setContext(properties.getContext());

        // 第三个：Content
        CertificateApply.Content content = new CertificateApply.Content();
        content.setName(name);
        content.setIdCardNumber(idCardNumber);
        content.setBirthday(idCardNumber.substring(6, 14));
        certificate.setContent(content);

        // 第四个：Metadata
        CertificateApply.Metadata metadata = new CertificateApply.Metadata();
        LocalDateTime now = LocalDateTime.now();

        metadata.setCreateTime(now);
        metadata.setExp(properties.getPeriod());
        metadata.setIat(now.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        metadata.setIssuer(properties.getOwner());
        metadata.setSubject(bid);
        metadata.setRevocation(false);
        certificate.setMetadata(metadata);

        // 第五个：Signature
        CertificateApply.Signature signature = new CertificateApply.Signature();
        signature.setAlgorithm(properties.getKeyType());
        signature.setFormat(properties.getFormat());
        signature.setPublicKeyId(publicKey);
        certificate.setSignature(signature);

        Credentials credentials = Credentials.create(properties.getPrivateKey());

        StringBuilder messageString = new StringBuilder();
        messageString.append(content.getName()).append(content.getIdCardNumber()).append(content.getBirthday());
        messageString.append(metadata.getCreateTime()).append(metadata.getIssuer()).append(metadata.getSubject()).append(metadata.getIat()).append(metadata.getExp());

        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        Sign.SignatureData signatureData = Sign.signMessage(messageString.toString().getBytes(), ecKeyPair, true);

        String v = Numeric.toHexString(signatureData.getV());
        String r = Numeric.toHexString(signatureData.getR());
        String s = Numeric.toHexString(signatureData.getS());

        StringBuilder sb = new StringBuilder(v).append(r.substring(2)).append(s.substring(2));
        signature.setValue(sb.toString());
//        try {
//            Cipher cipher=Cipher.getInstance("secp256k1");
//            cipher.init(Cipher.ENCRYPT_MODE, credentials);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        content.setEncryption("加密后的数据");
//        content.setName(null);
//        content.setIdCardNumber(null);
//        content.setBirthday(null);

        // 上链
        CertificateInfo info = new CertificateInfo();
        info.setId(certificate.getId());
        info.setContext(certificate.getContext());
        info.setIssuerAlgorithm(certificate.getSignature().getAlgorithm());
        info.setIssuerSignature(certificate.getSignature().getValue());
        info.setPeriod((int) certificate.getMetadata().getExp());
        info.setSubject(certificate.getMetadata().getSubject());
        certificateService.registerCertificate(info);

        // 存库
        Certificate c = new Certificate();
        c.setIsEnable(certificate.getMetadata().isRevocation() ? 1 : 0);
        c.setIssuedTime(now);
        c.setIssuer(certificate.getMetadata().getIssuer());
        c.setOwner(certificate.getMetadata().getSubject());
        c.setOwnerIDCard(idCardNumber);
        c.setOwnerName(name);
        c.setPeriod((int) certificate.getMetadata().getExp());
        c.setProofId(certificate.getId());
        certificateService.save(c);

        return R.data(certificate);
    }
}
