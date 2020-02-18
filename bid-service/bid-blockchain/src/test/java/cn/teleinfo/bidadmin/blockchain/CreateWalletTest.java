package cn.teleinfo.bidadmin.blockchain;

import org.bifj.crypto.*;
import org.junit.Test;

import java.io.File;
import java.math.BigInteger;
import java.util.Calendar;

public class CreateWalletTest {

    @Test
    public void testCandidates() throws Exception {
        // 注册帐户
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
        String privateKey = privateKeyInDec.toString(16);
        System.out.println(privateKey);

        // 获取地址
        Credentials credentials = Credentials.create(privateKey);
        String address = credentials.getAddress();
        System.out.println(address);

        // 生成keyStore文件
        WalletFile file = Wallet.createStandard("pass", credentials.getEcKeyPair());

        // 解密keyStore文件
        ECKeyPair pair = Wallet.decrypt("pass", file);
        String priv = pair.getPrivateKey().toString(16); // 私钥
        System.out.println(priv);
        String pubv = pair.getPublicKey().toString(16); // 公钥
        System.out.println(pubv);
    }

    @Test
    public void testGenerateLightNewWalletFile() throws Exception {
        File file = new File(".");
        String s ="";// AndroidWalletUtils.generateLightNewWalletFile("node", file);
        System.out.println(s);
    }

    @Test
    public void testGenerateWalletFile() throws Exception {
        File file = new File(".");
        Credentials credentials = WalletUtils.loadCredentials("node", file);
        String s = WalletUtils.generateWalletFile("node", credentials.getEcKeyPair(), file, false);
        System.out.println(s);
    }

    @Test
    public void testLoadLightNewWalletFile() throws Exception {
        File file = new File("./UTC--2019-12-09T10-18-00.853000000Z--did:bid:6d12faad791dbe817283c6be.json");
        Credentials credentials = WalletUtils.loadCredentials("node", file);
        System.out.println(credentials.getEcKeyPair().hashCode());
    }

    @Test
    public void testTime() {
        int milli = 44;
        StringBuffer d = new StringBuffer();
        d = milli > 99 ? d.append(milli) : milli > 9 ? d.append("0").append(milli) : d.append("00").append(milli);
        System.out.println(d.toString());

        System.out.println(getUTCTimeStr());
    }


    /**
     * 得到UTC时间，类型为字符串，格式为
     * 'UTC--'yyyy-MM-dd'T'HH-mm-ss.SSS000000'Z--'
     * 如果获取失败，返回null
     *
     * @return
     */
    private static String getUTCTimeStr() {
        StringBuffer d = new StringBuffer();
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int milli = cal.get(Calendar.MILLISECOND);

        d.append("UTC--").append(year);
        d.append("-");
        d = month > 9 ? d.append(month) : d.append(0).append(month);
        d.append("-");
        d = day > 9 ? d.append(day) : d.append(0).append(day);
        d.append("T");
        d = hour > 9 ? d.append(hour) : d.append(0).append(hour);
        d.append("-");
        d = minute > 9 ? d.append(minute) : d.append(0).append(minute);
        d.append("-");
        d = second > 9 ? d.append(second) : d.append(0).append(second);
        d.append(".");
        d = milli > 99 ? d.append(milli) : milli > 9 ? d.append("0").append(milli) : d.append("00").append(milli);
        d.append("000000Z--");

        return d.toString();
    }
}
