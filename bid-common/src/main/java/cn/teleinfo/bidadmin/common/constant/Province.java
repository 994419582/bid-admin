package cn.teleinfo.bidadmin.common.constant;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Province {
    private String name;
    private String fullName;
    public Province(String name , String fullName){
        this.name=name;
        this.fullName=fullName;
    }
    public static  List<Province> getPrivince(){
        List<Province> provinces= new ArrayList<>();
        provinces.add(new Province("北京","北京市"));
        provinces.add(new Province("天津","天津市"));
        provinces.add(new Province("河北","河北省"));
        provinces.add(new Province("广东","广东省"));
        provinces.add(new Province("山东","山东省"));
        provinces.add(new Province("河南","河南省"));
        provinces.add(new Province("江苏","江苏省"));
        provinces.add(new Province("上海","上海市"));
        provinces.add(new Province("浙江","浙江省"));
        provinces.add(new Province("香港","香港特别行政区"));
        provinces.add(new Province("陕西","陕西省"));
        provinces.add(new Province("湖南","湖南省"));
        provinces.add(new Province("重庆","重庆市"));
        provinces.add(new Province("福建","福建省"));
        provinces.add(new Province("云南","云南省"));
        provinces.add(new Province("四川","四川省"));
        provinces.add(new Province("广西","广西壮族自治区"));
        provinces.add(new Province("安徽","安徽省"));
        provinces.add(new Province("海南","海南省"));
        provinces.add(new Province("江西","江西省"));
        provinces.add(new Province("山西","山西省"));
        provinces.add(new Province("辽宁","辽宁省"));
        provinces.add(new Province("台湾","台湾省"));
        provinces.add(new Province("黑龙江","黑龙江省"));
        provinces.add(new Province("澳门","澳门特别行政区"));
        provinces.add(new Province("贵州","贵州省"));
        provinces.add(new Province("甘肃","甘肃省"));
        provinces.add(new Province("青海","青海省"));
        provinces.add(new Province("新疆","新疆维吾尔自治区"));
        provinces.add(new Province("西藏","西藏藏族自治区"));
        provinces.add(new Province("吉林","吉林省"));
        provinces.add(new Province("宁夏","宁夏回族自治区"));
        return provinces;
    }


//    public static void main(String[] args) {
//        System.out.println(Province.getPrivince());
//    }
}
