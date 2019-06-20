package com.cd.tools.enums;

/**
 * @author ChuD
 * @date 2019-05-09 20:04
 */
public enum  RegionEnum {
    HEFEI("3401", "合肥"),
    WUHU("3402", "芜湖"),
    BENGBU("3403", "蚌埠"),
    HUAINAN("3404", "淮南"),
    MAANSHAN("3405", "马鞍山"),
    HUAIBEI("3406", "淮北"),
    TONGLING("3407", "铜陵"),
    ANQING("3408", "安庆"),
    HUANGSHANG("3410", "黄山"),
    CHUZHOU("3411", "滁州"),
    FUYANG("3412", "阜阳"),
    SUZHOU("3413", "宿州"),
    LUAN("3415", "六安"),
    BAZHOU("3416", "亳州"),
    CHIZHOU("3417", "池州"),
    XUANCHENG("3418", "宣城");

    private String id;
    private String name;

    RegionEnum(String id, String name) {
        this.id = id; this.name = name;
    }
}
