package com.xz.sm.entity;

public class KdList {


    private String ShipperName;
    private String ShipperCode;

    public KdList(String name,String code) {
        this.ShipperName = name;
        this.ShipperCode = code;
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
