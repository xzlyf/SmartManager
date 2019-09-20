package com.xz.sm.entity;

public class KdList {


    private String ShipperName;//快递名字
    private String ShipperCode;//公司代号
    private String ShipperNo;//快递单号

    public KdList(String name,String code,String shipperNo) {
        this.ShipperName = name;
        this.ShipperCode = code;
        this.ShipperNo = shipperNo;
    }

    public String getShipperNo() {
        return ShipperNo;
    }

    public void setShipperNo(String shipperNo) {
        ShipperNo = shipperNo;
    }

    public String getShipperName() {
        return ShipperName;
    }

    public void setShipperName(String ShipperName) {
        this.ShipperName = ShipperName;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

}
