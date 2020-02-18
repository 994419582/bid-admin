package cn.teleinfo.bidadmin.blockchain;

import cn.teleinfo.bidadmin.blockchain.entity.CertificateApply;
import org.bifj.crypto.Credentials;
import org.bifj.crypto.ECKeyPair;
import org.bifj.crypto.Keys;
import org.bifj.crypto.Sign;
import org.bifj.utils.Numeric;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ICertificateApplyServiceTest {

    @Test
    public void issuerCertificate() {
        CertificateApply c = issuerCertificate("spf", "131123199510221234", "did:bid:19e22b7a0902bac941fe083a", "gongyao");
        System.out.println(c);
    }

    public static CertificateApply issuerCertificate(String name, String idCardNumber, String bid, String publicKey) {

        // 创建证书，然后证书有五个字段，分别准备
        CertificateApply certificate = new CertificateApply();
        // 第一个：id
        String id = Keys.createBID(bid);
        certificate.setId(id);

        // 第二个：Context
        certificate.setContext("https://example.com/realname/template/v1");

        // 第三个：Content
        CertificateApply.Content content = new CertificateApply.Content();
        content.setName(name);
        content.setIdCardNumber(idCardNumber);
        content.setBirthday(idCardNumber.substring(6, 14));
        content.setEncryption("");
        certificate.setContent(content);

        // 第四个：Metadata
        CertificateApply.Metadata metadata = new CertificateApply.Metadata();
//        LocalDateTime now = LocalDateTime.now();

        LocalDateTime now = LocalDateTime.of(2019, 3, 2, 3, 4, 5);
        metadata.setCreateTime(now);
        metadata.setExp(2);
        metadata.setIat(now.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        metadata.setIssuer("did:bid:6cc796b8d6e2fbebc9b3cf9e");
        metadata.setSubject(bid);
        metadata.setRevocation(false);
        certificate.setMetadata(metadata);

        // 第五个：Signature
        CertificateApply.Signature signature = new CertificateApply.Signature();
        signature.setAlgorithm("ECDSAwithSHA256");
        signature.setFormat("pgp");
        signature.setPublicKeyId(publicKey);
        certificate.setSignature(signature);

        Credentials credentials = Credentials.create("d37df84af4156fe9ab65a5642418cd7bd9e9371acb5ae1bd282d1d473bcb1f13");

        StringBuilder messageString = new StringBuilder();
        messageString.append(content.getName()).append(content.getIdCardNumber()).append(content.getBirthday());
        messageString.append(metadata.getCreateTime()).append(metadata.getIssuer()).append(metadata.getSubject()).append(metadata.getIat()).append(metadata.getExp());


        System.out.println(messageString.toString());
        byte[] bytes = messageString.toString().getBytes();
        System.out.println(bytes);

//        byte[] contentBytes = CertificateApply.toByteArray(content);
//        byte[] metadataBytes = CertificateApply.toByteArray(metadata);
//
//        byte[] message = new byte[contentBytes.length + metadataBytes.length];
//        System.arraycopy(contentBytes, 0, message, 0, contentBytes.length);
//        System.arraycopy(metadataBytes, 0, message, contentBytes.length, metadataBytes.length);

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
        return certificate;
    }
}