package cn.teleinfo.bidadmin.soybean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "群管理员vo对象",description = "群管理员vo对象")
public class GroupManagerVo implements Serializable {
    private static final long serialVersionUID = -7215969783237590594L;

    @ApiModelProperty(value = "机构ID")
    private Integer id;

    @ApiModelProperty(value = "管理员集合")
    private List<Integer> managers = new ArrayList();

    @ApiModelProperty(value = "统计管理员集合")
    private List<Integer> dataManagers = new ArrayList<>();
}
