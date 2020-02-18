package cn.teleinfo.bidadmin.app.util;



import jar.org.apache.http.HttpEntity;
import jar.org.apache.http.client.methods.CloseableHttpResponse;
import jar.org.apache.http.client.methods.HttpPost;
import jar.org.apache.http.entity.ContentType;
import jar.org.apache.http.entity.StringEntity;
import jar.org.apache.http.util.EntityUtils;
import net.sf.json.JSON;

import java.io.IOException;

/**
 * Created by on 2018/3/21.
 */
public class HttpSend {
    int threads = 5;

    final HttpConnectionPool hcm = new HttpConnectionPool(threads);

    public String HttpResponse(String postUrl, JSON jsonObj) throws IOException {
        HttpPost post = new HttpPost(postUrl);
        StringEntity myEntity = new StringEntity(jsonObj.toString(), ContentType.APPLICATION_JSON);
        System.out.println("请求地址:"+post);
        post.setEntity(myEntity);

        CloseableHttpResponse response = hcm.getClient().execute(post);
        HttpEntity entity = response.getEntity();
//        System.out.println("返回:"+response.toString());
        if (response.getStatusLine().getStatusCode()==200){
            HttpEntity entity1 = response.getEntity();
            String responContent = EntityUtils.toString(entity,"utf-8");
            return responContent;
        }
        return "失败";
    }
}
