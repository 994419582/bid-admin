package cn.teleinfo.bidadmin.app.api;

import cn.hutool.core.img.ImgUtil;
import cn.teleinfo.bidadmin.app.config.ApiConfig;
import cn.teleinfo.bidadmin.app.config.ApiConstant;
import cn.teleinfo.bidadmin.app.entity.FaceInfo;
import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class BidFaceApi {

    @Autowired
    private ApiConfig apiConfig;

    /**
     * 人脸检测：检测图片中的人脸
     * @param image
     * @return
     */
    public JSONObject detect(String image) {
        AipFace client = apiConfig.getAipFaceClient();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("face_field", "quality");
        options.put("max_face_num", "1");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "NORMAL");

        return client.detect(image, ApiConstant.IMAGE_TYPE_BASE64, options);
    }

    /**
     * 身份验证，通过百度提供的接口做身份验证，我们也有自己公司的验证接口：cn.teleinfo.bidadmin.app.util.simpleVerify(String name, String code, String photoData)
     * @param image
     * @param idCardNumber
     * @param name
     * @return
     */
    public boolean personVerify(String image, String idCardNumber, String name) {
        AipFace client = apiConfig.getAipFaceClient();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");

        JSONObject res = client.personVerify(image, ApiConstant.IMAGE_TYPE_BASE64, idCardNumber, name, options);

        Object obj = res.get("result");

        if (null != obj && "null".equals(obj.toString())) {
            return false;
        } else {
            JSONObject r = (JSONObject)obj;
            return r.getInt("score") > 80;
        }

    }

    /**
     * 1：N人脸认证：基于uid维度的1：N识别，由于uid已经锁定固定数量的人脸，所以检索范围更聚焦；
     * @param bidAddress
     * @param image
     * @return
     */
    public boolean search(String bidAddress, String image) {
        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "1");
        options.put("match_threshold", "80");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        options.put("user_id", bidAddress);
        options.put("max_user_num", "1");

        JSONObject res = client.search(image, ApiConstant.IMAGE_TYPE_BASE64, ApiConstant.FACE_API_GROUP_ID, options);
        Object obj = res.get("result");

        if (null != obj && "null".equals(obj.toString())) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * 人脸注册,如果用户已注册过，则追加
     * @param bidAddress
     * @return
     */
    public R addUser(String bidAddress, String image) {
        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        options.put("action_type", "APPEND");

        JSONObject res = client.addUser(image, ApiConstant.IMAGE_TYPE_BASE64, ApiConstant.FACE_API_GROUP_ID, bidAddress, options);

        Object obj = res.get("result");

        if (null != obj && "null".equals(obj.toString())) {
            return R.fail(res.getString("error_msg"));
        } else {
            JSONObject r = (JSONObject)obj;
            return R.success(r.getString("face_token"));
        }
    }

    /**
     * 人脸更新,会把用户的人脸库删除，替换成当前的人脸
     * @param bidAddress
     * @param image
     * @return
     */
    public R updateUser(String bidAddress, String image) {
        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "NORMAL");
        options.put("action_type", "REPLACE");

        JSONObject res = client.updateUser(image, ApiConstant.IMAGE_TYPE_BASE64, ApiConstant.FACE_API_GROUP_ID, bidAddress, options);

        Object obj = res.get("result");

        if (null != obj && "null".equals(obj.toString())) {
            return R.fail(res.getString("error_msg"));
        } else {
            JSONObject r = (JSONObject)obj;
            return R.success(r.getString("face_token"));
        }
    }

    /**
     * 人脸删除
     * @param bidAddress
     * @param faceToken
     * @return
     */
    public R faceDelete(String bidAddress, String faceToken) {
        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        JSONObject res = client.faceDelete(bidAddress, ApiConstant.FACE_API_GROUP_ID, faceToken, null);

        int code = res.getInt("error_code");

        if (code != 0) {
            return R.fail(res.getString("error_msg"));
        } else {
            return R.success("success");
        }
    }

    /**
     * 用户删除
     * @param bidAddress
     * @return
     */
    public R deleteUser(String bidAddress) {
        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        JSONObject res = client.deleteUser(bidAddress, ApiConstant.FACE_API_GROUP_ID, null);

        int code = res.getInt("error_code");

        if (code != 0) {
            return R.fail(res.getString("error_msg"));
        } else {
            return R.success("success");
        }
    }

    /**
     * 获取用户人脸列表
     * @param bidAddress
     * @return
     */
    public List<FaceInfo> faceGetlist(String bidAddress) {
        List<FaceInfo> infos = null;

        AipFace client = apiConfig.getAipFaceClient();

        bidAddress = bidAddress.replaceAll(":", "_");

        JSONObject res = client.faceGetlist(bidAddress, ApiConstant.FACE_API_GROUP_ID, null);

        Object obj = res.get("result");

        if (null != obj && !"null".equals(obj.toString())) {
            JSONObject r = (JSONObject)obj;
            JSONArray faceList = r.getJSONArray("face_list");
            if (null != faceList) {
                infos = new ArrayList<>(faceList.length());

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for (Object o : faceList) {
                    JSONObject j = (JSONObject)o;
                    FaceInfo f = new FaceInfo();
                    f.setFaceToken(j.getString("face_token"));
                    f.setCreateTime(
                        LocalDateTime.parse(j.getString("ctime"),df)
                    );
                    infos.add(f);
                }
            }
        }
        return infos;
    }

    /**
     * 获取用户列表
     * @param start
     * @param length
     * @return
     */
    public List<String> getGroupUsers(int start, int length) {
        List<String> users = null;

        AipFace client = apiConfig.getAipFaceClient();

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("start", String.valueOf(start));
        options.put("length", String.valueOf(length));

        JSONObject res = client.getGroupUsers(ApiConstant.FACE_API_GROUP_ID, options);

        Object obj = res.get("result");

        if (null != obj && !"null".equals(obj.toString())) {
            JSONObject r = (JSONObject)obj;
            JSONArray userList = r.getJSONArray("user_id_list");
            if (null != userList) {
                users = new ArrayList<>(userList.length());
                for (Object o : userList) {
                    users.add(o.toString());
                }
            }
        }
        return users;
    }

    public String multipartFileToString(MultipartFile file){
        String image="";

        int n=0;
        File f = new File(file.getOriginalFilename());
        try (InputStream in  = file.getInputStream(); OutputStream os = new FileOutputStream(f)){
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer,0,4096)) != -1){
                os.write(buffer,0,n);
            }
            if (file.getContentType().contains("png")){
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_PNG);
            }else if(file.getContentType().contains("gif")){
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_GIF);
            }else if(file.getContentType().contains("jpg")){
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_JPG);
            }else if(file.getContentType().contains("jpeg")){
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_JPEG);
            }else if(file.getContentType().contains("bmp")){
                image = ImgUtil.toBase64(ImgUtil.read(f), ImgUtil.IMAGE_TYPE_BMP);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }
}
