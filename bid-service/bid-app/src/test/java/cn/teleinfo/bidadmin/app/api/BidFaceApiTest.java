package cn.teleinfo.bidadmin.app.api;

import cn.hutool.core.img.ImgUtil;
import cn.teleinfo.bidadmin.app.AppApplication;
import cn.teleinfo.bidadmin.app.entity.FaceInfo;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-app", profile = "dev", enableLoader = true)
public class BidFaceApiTest {

    private String image;

    @Before
    public void before()  {
        image = "/Users/shanpengfei/work/idea/bif/bid-admin/bid-service/bid-app/src/test/resources/pic/";
    }

    @Autowired
    private  BidFaceApi api;

    @Test
    public void detect() {
        String img = ImgUtil.toBase64(ImgUtil.read(image+"c.jpg"), ImgUtil.IMAGE_TYPE_PNG);
        JSONObject r = api.detect(img);
        System.out.println(r);
    }

    @Test
    public void addUser() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";
        String img = ImgUtil.toBase64(ImgUtil.read(image+"c.jpg"), ImgUtil.IMAGE_TYPE_PNG);
        R r = api.addUser(bidAddress, img);
        System.out.println(r);
    }

    @Test
    public void updateUser() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";
        String img = ImgUtil.toBase64(ImgUtil.read(image+"c.jpg"), ImgUtil.IMAGE_TYPE_PNG);
        R r = api.updateUser(bidAddress, img);
        System.out.println(r);
    }

    @Test
    public void delete() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";

        R r = api.faceDelete(bidAddress, "cf44f7dbd68743444c9a55ec0683deed");
        System.out.println(r);
    }

    @Test
    public void search() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";
        String img = ImgUtil.toBase64(ImgUtil.read(image+"c.jpg"), ImgUtil.IMAGE_TYPE_PNG);
        boolean r = api.search(bidAddress, img);
        System.out.println(r);
    }

    @Test
    public void personVerify() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";
        String img = ImgUtil.toBase64(ImgUtil.read(image+"c.jpg"), ImgUtil.IMAGE_TYPE_PNG);
        boolean r = api.personVerify(img,"","");
        System.out.println(r);
    }


    @Test
    public void getGroupUsers() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";

        List<String> list = api.getGroupUsers(0, 100);
        for (String s : list) {
            System.out.println(s);
        }
    }


    @Test
    public void faceGetlist() {
        String bidAddress = "did_bid_b1f819f1411ea7b100b5897d";

        List<FaceInfo> list = api.faceGetlist(bidAddress);
        if (null != list && !list.isEmpty()) {
            for (FaceInfo f : list) {
                System.out.println(f);
            }
        }
    }

}