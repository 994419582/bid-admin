package cn.teleinfo.bidadmin.soybean.mysql.data.transfer.util;


import cn.teleinfo.bidadmin.soybean.mysql.data.transfer.entity.UserEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static<T> List<T> filePathToEntity(String filePath, Class<T> obj) {
        String s = FileUtils.ReadFile(filePath);
        JSONArray jsonArray = JSONArray.parseArray(s);

        List<T> list = new ArrayList<>();

        for (Object object : jsonArray) {
            JSONObject json = (JSONObject)object;
//            UserEntity user = json.getObject("userInfo",UserEntity.class);
            T o = JSONToObj(json.toString(), obj);
            list.add(o);
        }

        return list;
    }

    /**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public static<T> T JSONToObj(String jsonStr, Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
