package org.lsqt.sms.model;


/**
 * 短信号码包上传表
 */
public class SmsMobileNoPackage {


    /***/


    private Long id;


    /**
     * 包名
     */
    private String packageName;


    /**
     * 上传包号
     */
    private String packageNo;


    /**
     * 状态：0 提取中 1 成功   2 失败 3 不可用
     */
    private String packageStatus;


    /**
     * 包内号码数量
     */


    private Long packageCounts;

    /**
     * 上傳 條數
     */
    private Long lineNum;


    /**
     * 创建（上传）时间
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

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String
    getPackageName() {
        return this.packageName;
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }

    public String
    getPackageNo() {
        return this.packageNo;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    public String
    getPackageStatus() {
        return this.packageStatus;
    }

    public void setPackageCounts(Long packageCounts) {
        this.packageCounts = packageCounts;
    }

    public Long
    getPackageCounts() {
        return this.packageCounts;
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

    public Long getLineNum() {
        return lineNum;
    }

    public void setLineNum(Long lineNum) {
        this.lineNum = lineNum;
    }
}
