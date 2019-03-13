package org.lsqt.sms.model;


public class LaunchMarketingInfo {

    private String launchId;

    private String sendSuccNum;

    private String recvSuccNum;

    private String pv;

    private String uv;

    private String bgnTime;

    private String endTime;

    public String getLaunchId() {
        return launchId;
    }

    public String getSendSuccNum() {
        return sendSuccNum;
    }

    public String getRecvSuccNum() {
        return recvSuccNum;
    }

    public String getPv() {
        return pv;
    }

    public String getUv() {
        return uv;
    }

    public String getBgnTime() {
        return bgnTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public LaunchMarketingInfo setLaunchId(String launchId) {
        this.launchId = launchId;
        return this;
    }

    public LaunchMarketingInfo setSendSuccNum(String sendSuccNum) {
        this.sendSuccNum = sendSuccNum;
        return this;
    }

    public LaunchMarketingInfo setRecvSuccNum(String recvSuccNum) {
        this.recvSuccNum = recvSuccNum;
        return this;
    }

    public LaunchMarketingInfo setPv(String pv) {
        this.pv = pv;
        return this;
    }

    public LaunchMarketingInfo setUv(String uv) {
        this.uv = uv;
        return this;
    }

    public LaunchMarketingInfo setBgnTime(String bgnTime) {
        this.bgnTime = bgnTime;
        return this;
    }

    public LaunchMarketingInfo setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}
