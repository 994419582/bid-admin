package cn.teleinfo.bidadmin.soybean.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自动填充配置
 */
@Component
public class AutoFillConfiguration implements MetaObjectHandler {

    /**
     * 新增时填充日期
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createTime", new Date(), metaObject);
    }

    /**
     * 更新时填充日期
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateTime", new Date(), metaObject);
    }
}