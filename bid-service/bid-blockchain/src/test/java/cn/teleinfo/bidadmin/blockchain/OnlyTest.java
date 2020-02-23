package cn.teleinfo.bidadmin.blockchain;

import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import cn.teleinfo.bidadmin.blockchain.util.IPUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.bifj.abi.datatypes.Type;
import org.bifj.abi.datatypes.Uint;
import org.bifj.abi.datatypes.Utf8String;
import org.bifj.abi.datatypes.generated.Uint64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlyTest {

    @Test
    public void testCandidates() {
        List<Type> allCandidates = new ArrayList<>();
        allCandidates.add(new Uint64(2));
        allCandidates.add(new Utf8String("did:bid:d6c1d80d02169572c0611285did:bid:fb03d18e135844f72e2724c3"));

        List<String> addressList = new ArrayList<>();
        if (allCandidates != null && allCandidates.size() == 2) {// 数量和地址
            Uint64 count = (Uint64)allCandidates.get(0);
            Utf8String addresses = (Utf8String)allCandidates.get(1);

            int addressLength = count.getValue().intValue() * 32;
            for (int i = 0; i < addressLength;) {
                String address = addresses.getValue().substring(i, i+=32);
                addressList.add(address);
            }
        }
    }

    @Test
    public void testUint64() {
        Uint64 u = new Uint64(new BigInteger("30"));
        int r = 0;

        Object o = u;

        System.out.println(u.getValue().intValue());

        System.out.println(r);
    }

    @Test
    public void testString () {
        String url = "enode://e5d8da2d9b3f0a953975d97e0df669aaa6ad36f23d6ab33febfb6d9a0d6dcd981b4287ac48a7ded42ad999b5763c4357c3cf27fcec432e115bcdee45305e3371@127.0.0.1:12340?discport=0";
        System.out.println(url);

        int start = url.indexOf("@");
        System.out.println(url.substring(start+1));

        String urlPattern = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        Pattern p= Pattern.compile(urlPattern);
        Matcher m=p.matcher(url);

        if(m.find()){
            System.out.println(m.group());
        }

    }

    @Test
    public void testLocation() {
        String url = "enode://e5d8da2d9b3f0a953975d97e0df669aaa6ad36f23d6ab33febfb6d9a0d6dcd981b4287ac48a7ded42ad999b5763c4357c3cf27fcec432e115bcdee45305e3371@127.0.0.1:12340?discport=0";
        System.out.println(url);

        String ip = IPUtils.filterIP(url);
        System.out.println(ip);

        String addresses = null;
        try {
//            addresses = IPUtils.getAddresses("ip="+ip, "utf-8");
            addresses = IPUtils.getAddresses("ip=106.38.3.253", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("result ==" + addresses);
    }

    @Test
    public void testJson() {
//        String str = "{'Owner':'did:bid:200fd76b9548f0effa824a23', 'VoteCandidates':'[{addr:did:bid:b1f819f1411ea7b100b5897d, vote:3},{addr:did:bid:c82a830cc31ccc052bc78837, vote:3}]'}";


//        String str = "{'Owner':'did:bid:200fd76b9548f0effa824a23', 'VoteCandidates':'[{'addr': 'did:bid:b1f819f1411ea7b100b5897d', 'vote': '3'},{'addr': 'did:bid:c82a830cc31ccc052bc78837', 'vote': '3'}]'}";


        String str = "{'Owner':'did:bid:200fd76b9548f0effa824a23', 'VoteCandidates':\"[{'addr': 'did:bid:b1f819f1411ea7b100b5897d', 'vote': '3'},{'addr': 'did:bid:c82a830cc31ccc052bc78837', 'vote': '3'}]\"}";

        System.out.println(str.length());
        System.out.println(str.substring(0, 67));

        JSONObject json = JSONObject.parseObject(str);
        System.out.println(json);


        String arr = "[{'addr': 'did:bid:b1f819f1411ea7b100b5897d', 'vote': '3'},{'addr': 'did:bid:c82a830cc31ccc052bc78837', 'vote': '3'}]";
        JSONArray array = JSONArray.parseArray(arr);
        System.out.println(arr);

        String s = "{'Owner':'0000000000000000000000000000000000000000', 'VoteCandidates':'[]'}";
        System.out.println(s.length());
    }

    @Test
    public void testTime() {
        LocalDateTime now = LocalDateTime.now();
        //当天的零点
        System.out.println("当天的零点:  "+ LocalDateTime.of(now.toLocalDate(), LocalTime.MIN));

        //当天的最后时间
        System.out.println("当天的最后时间:  "+LocalDateTime.of(now.toLocalDate(), LocalTime.MAX));
    }
}
