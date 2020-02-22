package cn.teleinfo.bidadmin.soybean.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ClockInAndQuarantionVO {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户主键")
    private Integer userId;
    @ApiModelProperty(value = "打卡地址")
    private String address;
    @ApiModelProperty(value = "健康状态")
    private Integer healthy;
    @ApiModelProperty(value = "是否有就诊入院")
    private Integer hospital;
    @ApiModelProperty(value = "是否接触过武汉人同或经过武汉午")
    private Integer wuhan;
    @ApiModelProperty(value = "计划反京时间")
    private String gobacktime;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "是否在隔离器")
    private Integer quarantine;
    @ApiModelProperty(value = "未返京原因")
    private String reason;
    @ApiModelProperty(value = "是否从其他城市返回")
    private Integer otherCity;
    @ApiModelProperty(value = "开始观察时间")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "体温")
    private Integer temperature;
    @ApiModelProperty(value = "发热")
    private Integer fever;
    @ApiModelProperty(value = "乏力")
    private Integer fatigue;
    @ApiModelProperty(value = "干咳")
    private Integer hoose;
    @ApiModelProperty(value = "呼吸困难")
    private Integer dyspnea;
    @ApiModelProperty(value = "腹泻")
    private Integer diarrhea;
    @ApiModelProperty(value = "肌肉酸疼")
    private Integer muscle;
    @ApiModelProperty(value = "其他不适症状")
    private String other;
    @ApiModelProperty(value = "隔离备注")
    private String quarantionRemarks;
    @ApiModelProperty(value = "行程列表")
    private List<QuarantineTripVO> quarantineTrips;

}
