package org.lsqt.sms.model;

import org.lsqt.components.db.Page;

/**
 * 短信号码包上传表
 */
public class SmsMobileNoPackageQuery {
    private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
    private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

    private String sortOrder;
    private String sortField;

    private String key; // 关键字
    private String ids; // 用逗号分割的id字符

    /**
     * 包名
     */
    private String packageName;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 上传时间
     */
    private String createTime;


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


    // getter、setter

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


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
