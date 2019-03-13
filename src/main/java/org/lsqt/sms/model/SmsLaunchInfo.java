package org.lsqt.sms.model;


import org.lsqt.components.context.annotation.model.Pattern;
import org.lsqt.components.db.annotation.Column;

/**
 * 短信投放表
 */
public class SmsLaunchInfo {


    /***/


    private Long id;


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
    private Long signId;


    /**
     * 文案ID
     */
    private Long templId;


    /**
     * 投放状态：0 审核中 1 初始化   2 进行中 3 成功 4 失败 5 审核失败
     */
    private String launchStatus;

    @Column(isVirtual = true)
    private String launchStatusTransfered;


    /**
     * 计划投放数量
     */


    private Long num;


    /**
     * 计划投放时间
     */

    @Pattern("yyyy-MM-dd HH:mm")
    private java.util.Date bgnTime;


    /**
     * 腾讯发送状态：0 审核中 1 初始化   2 进行中 3 成功 4 失败 5 审核失败
     */
    private String sendStatus;

    @Column(isVirtual = true)
    private String sendStatusTransfered;


    /**
     * 腾讯发送描述
     */
    private String sendMsg;


    /**
     * 投放开始时间
     */


    private java.util.Date launchBgnTime;


    /**
     * 投放结束时间
     */


    private java.util.Date launchEndTime;


    /**
     * 投放成功数量
     */


    private Long succNum;


    /**
     * 投放失败数量
     */


    private Long failNum;


    /**
     * 用户接收成功数量
     */


    private Long recvNum;


    /**
     * 创建时间
     */


    private java.util.Date createTime;


    /**
     * 更新时间
     */


    private java.util.Date updateTime;


    // getter、setter
    public void setId(Long id) {
        this.id = id;
    }

    public Long
    getId() {
        return this.id;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public String
    getLaunchId() {
        return this.launchId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String
    getPackageId() {
        return this.packageId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long
    getSignId() {
        return this.signId;
    }

    public void setTemplId(Long templId) {
        this.templId = templId;
    }

    public Long
    getTemplId() {
        return this.templId;
    }

    public void setLaunchStatus(String launchStatus) {
        this.launchStatus = launchStatus;
    }

    public String
    getLaunchStatus() {
        return this.launchStatus;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long
    getNum() {
        return this.num;
    }

    public void setBgnTime(java.util.Date bgnTime) {
        this.bgnTime = bgnTime;
    }

    public java.util.Date
    getBgnTime() {
        return this.bgnTime;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String
    getSendStatus() {
        return this.sendStatus;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public String
    getSendMsg() {
        return this.sendMsg;
    }

    public void setLaunchBgnTime(java.util.Date launchBgnTime) {
        this.launchBgnTime = launchBgnTime;
    }

    public java.util.Date
    getLaunchBgnTime() {
        return this.launchBgnTime;
    }

    public void setLaunchEndTime(java.util.Date launchEndTime) {
        this.launchEndTime = launchEndTime;
    }

    public java.util.Date
    getLaunchEndTime() {
        return this.launchEndTime;
    }

    public void setSuccNum(Long succNum) {
        this.succNum = succNum;
    }

    public Long
    getSuccNum() {
        return this.succNum;
    }

    public void setFailNum(Long failNum) {
        this.failNum = failNum;
    }

    public Long
    getFailNum() {
        return this.failNum;
    }

    public void setRecvNum(Long recvNum) {
        this.recvNum = recvNum;
    }

    public Long
    getRecvNum() {
        return this.recvNum;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date
    getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public java.util.Date
    getUpdateTime() {
        return this.updateTime;
    }

    public String getLaunchStatusTransfered() {
        return launchStatusTransfered;
    }

    public String getSendStatusTransfered() {
        return sendStatusTransfered;
    }

    public SmsLaunchInfo setLaunchStatusTransfered(String launchStatusTransfered) {
        this.launchStatusTransfered = launchStatusTransfered;
        return this;
    }

    public SmsLaunchInfo setSendStatusTransfered(String sendStatusTransfered) {
        this.sendStatusTransfered = sendStatusTransfered;
        return this;
    }
}
