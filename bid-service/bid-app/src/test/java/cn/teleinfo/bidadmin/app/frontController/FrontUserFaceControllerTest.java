package cn.teleinfo.bidadmin.app.frontController;

import cn.hutool.core.img.ImgUtil;
import cn.teleinfo.bidadmin.app.AppApplication;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@BladeBootTest(appName = "bid-app", profile = "dev", enableLoader = true)
public class FrontUserFaceControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private String bidAddress = "did_bid_b1f819f1411ea7b100b58977";
    private String imagePath;

    @Before
    public void setUp() {
        //此种方式可通过spring上下文来自动配置一个或多个controller
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //此种方式，手工指定想要的controller
        // mockMvc = MockMvcBuilders.standaloneSetup(new FrontUserFaceController()).build();
        imagePath = "/Users/shanpengfei/work/idea/bif/bid-admin/bid-service/bid-app/src/test/resources/pic/";
    }

    @Test
    public void addFace() throws Exception {
        RequestBuilder request = null;

        File file = new File(imagePath + "b.jpg");

        MockMultipartFile firstFile = new MockMultipartFile("file", "src/test/resources/pic/register.jpg",
                MediaType.IMAGE_JPEG_VALUE, new FileInputStream(file));

        //构造请求
        request = multipart("/app/userface/addFace")
                .file(firstFile)
                .param("bid", bidAddress);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andExpect(jsonPath("$.msg", is("注册成功")))//使用jsonPath解析JSON返回值，判断具体的内容, 此处不希望status返回E
                .andDo(print());//打印结果
    }

    @Test
    public void search() throws Exception {
        RequestBuilder request = null;

        File file = new File(imagePath + "b.jpg");

        MockMultipartFile firstFile = new MockMultipartFile("file", "src/test/resources/pic/register.jpg",
                MediaType.IMAGE_JPEG_VALUE, new FileInputStream(file));
        //构造请求
        request = multipart("/app/userface/search")
                .file(firstFile)
                .param("bid", bidAddress);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andDo(print());//打印结果
    }

    @Test
    public void testAddFaceBase64() throws Exception {
        RequestBuilder request = null;

        String img = ImgUtil.toBase64(ImgUtil.read(imagePath + "c.jpg"), ImgUtil.IMAGE_TYPE_PNG);

        //构造请求
        request = post("/app/userface/addFaceBase64")
                .param("image", img)
                .param("bid", bidAddress);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andDo(print());//打印结果
    }

    @Test
    public void testSearchBase64() throws Exception {
        RequestBuilder request = null;

        String img = ImgUtil.toBase64(ImgUtil.read(imagePath + "a.jpg"), ImgUtil.IMAGE_TYPE_PNG);

        //构造请求
        request = post("/app/userface/searchBase64")
                .param("image", img)
                .param("bid", bidAddress);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andDo(print());//打印结果
    }

    @Test
    public void testExist() throws Exception {
        RequestBuilder request = null;
        //构造请求
        request = post("/app/userface/exist")
                .param("bid", bidAddress);
        //执行请求
        mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andDo(print());//打印结果
    }
}