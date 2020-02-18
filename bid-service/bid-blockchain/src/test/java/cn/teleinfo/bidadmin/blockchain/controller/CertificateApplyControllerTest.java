package cn.teleinfo.bidadmin.blockchain.controller;

import cn.teleinfo.bidadmin.blockchain.BlockChainApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springblade.core.test.BladeBootTest;
import org.springblade.core.test.BladeSpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = BlockChainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-blockchain", profile = "dev", enableLoader = true)
public class CertificateApplyControllerTest {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private String imagePath;
    private String name = "史维君";
    private String idCardNumber = "622425199211047317";
    private String bid = "did:bid:b1f819f1411ea7b100b58977";
    private String publicKey = "";

    @Before
    public void setUp() {
        //此种方式可通过spring上下文来自动配置一个或多个controller
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //此种方式，手工指定想要的controller
        // mockMvc = MockMvcBuilders.standaloneSetup(new FrontUserFaceController()).build();
        imagePath = "/Users/shanpengfei/work/idea/bif/bid-admin/bid-service/bid-blockchain/src/test/resources/pic/";
    }

    @Test
    public void issuerCertificate() throws Exception {
        RequestBuilder request = null;

        File file = new File(imagePath + "shiweijun.jpg");

        MockMultipartFile firstFile = new MockMultipartFile("file", "src/test/resources/pic/register.jpg",
                MediaType.IMAGE_JPEG_VALUE, new FileInputStream(file));

        //构造请求
        request = multipart("/front/certificate/apply")
                .file(firstFile)
                .param("name", name)
                .param("idCardNumber", idCardNumber)
                .param("bid", bid)
                .param("publicKey", publicKey);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
//                .andExpect(jsonPath("$.msg", is("注册成功")))//使用jsonPath解析JSON返回值，判断具体的内容, 此处不希望status返回E
                .andDo(print());//打印结果
    }
}