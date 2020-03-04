package cn.teleinfo.bidadmin.soybean.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.teleinfo.bidadmin.soybean.entity.Group;

import java.io.Serializable;

public class GroupExcelVo extends Group implements Serializable {

    private static final long serialVersionUID = 1235299034534839417L;

    @Excel(name = "组织名称")
    private String name;
    @Excel(name = "父组织名称")
    private String parentName;
    @Excel(name = "组织类型")
    private Integer groupType;
}
