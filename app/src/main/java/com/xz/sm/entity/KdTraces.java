package com.xz.sm.entity;

import java.util.ArrayList;
import java.util.List;

public class KdTraces {

    /**
     * LogisticCode : 4301040730105
     * ShipperCode : YD
     * Traces : [{"AcceptStation":"【广州市】  广东主城区公司广州番禺石基镇服务部-黄祖乾(13078800231) 已揽收","AcceptTime":"2019-09-17 22:14:02"},{"AcceptStation":"【佛山市】 已到达  广东佛山分拨中心","AcceptTime":"2019-09-18 04:07:50"},{"AcceptStation":"【佛山市】 已离开  广东佛山分拨中心 发往 深中市内包","AcceptTime":"2019-09-18 04:50:15"},{"AcceptStation":"【佛山市】 已离开  广东佛山分拨中心 发往 广东深圳公司","AcceptTime":"2019-09-18 04:56:59"},{"AcceptStation":"【深圳市】 已到达  广东深圳公司","AcceptTime":"2019-09-18 10:35:15"},{"AcceptStation":"【深圳市】 已离开  广东深圳公司 发往  广东深圳公司龙岗区观澜艺术村分部(18033428082)","AcceptTime":"2019-09-18 13:06:12"},{"AcceptStation":"【深圳市】  广东深圳公司龙岗区观澜艺术村分部 派件员 吴丹(13554738456)正在为您派送","AcceptTime":"2019-09-19 10:13:00"}]
     * State : 2
     * EBusinessID : 1581240
     * Success : true
     */

    private String LogisticCode;//物流单号
    private String ShipperCode;//	快递公司编码
    private String State;//物流状态：2-在途中,3-签收,4-问题件
    private String EBusinessID;
    private boolean Success;//	成功与否
    private List<KdAccept> Traces = new ArrayList<>();

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<KdAccept> getTraces() {
        return Traces;
    }

    public void setTraces(List<KdAccept> Traces) {
        this.Traces = Traces;
    }

}
