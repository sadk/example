package org.lsqt.sms.model;

public class SmsLaunchInfoExport {


    /**
     * 投放ID
     */
    private String launchId;


    /**
     * 上传包ID
     */
    private String packageId;


    /**
     * 签名ID
     */
    private String signId;


    /**
     * 文案ID
     */
    private String templId;


    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 投放状态：0 审核中 1 初始化   2 进行中 3 成功 4 失败 5 审核失败
     */
    private String launchStatus;


    /**
     * 投放成功数量
     */
    private String succNum;

    /**
     * 投放失败数量
     */
    private String failNum;


    /**
     * 用户接收成功数量
     */
    private String recvNum;

    public String getLaunchId() {
        return launchId;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getSignId() {
        return signId;
    }

    public String getTemplId() {
        return templId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getLaunchStatus() {
        return launchStatus;
    }

    public String getSuccNum() {
        return succNum;
    }

    public String getFailNum() {
        return failNum;
    }

    public String getRecvNum() {
        return recvNum;
    }

    public SmsLaunchInfoExport setLaunchId(String launchId) {
        this.launchId = launchId;
        return this;
    }

    public SmsLaunchInfoExport setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public SmsLaunchInfoExport setSignId(String signId) {
        this.signId = signId;
        return this;
    }

    public SmsLaunchInfoExport setTemplId(String templId) {
        this.templId = templId;
        return this;
    }

    public SmsLaunchInfoExport setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public SmsLaunchInfoExport setLaunchStatus(String launchStatus) {
        this.launchStatus = launchStatus;
        return this;
    }

    public SmsLaunchInfoExport setSuccNum(String succNum) {
        this.succNum = succNum;
        return this;
    }

    public SmsLaunchInfoExport setFailNum(String failNum) {
        this.failNum = failNum;
        return this;
    }

    public SmsLaunchInfoExport setRecvNum(String recvNum) {
        this.recvNum = recvNum;
        return this;
    }
}
