package cn.teleinfo.bidadmin.app.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FaceInfo对象", description = "人脸信息，包括人脸face_token和人脸创建时间")
public class FaceInfo extends BaseEntity {

    private String faceToken;

    private LocalDateTime createTime;
}
