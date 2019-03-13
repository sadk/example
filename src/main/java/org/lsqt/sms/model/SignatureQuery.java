package org.lsqt.sms.model;


import org.lsqt.components.db.Page;

public class SignatureQuery {

    private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
    private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

    private String sortOrder;
    private String sortField;


    private String id;
    private String name;
    private String createTime;
    private Long signatureId;
    private String status;
    private String useStatus;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public String getSortField() {
        return sortField;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public SignatureQuery setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public SignatureQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public SignatureQuery setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public SignatureQuery setSortField(String sortField) {
        this.sortField = sortField;
        return this;
    }

    public SignatureQuery setId(String id) {
        this.id = id;
        return this;
    }

    public SignatureQuery setName(String name) {
        this.name = name;
        return this;
    }

    public SignatureQuery setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getSignatureId() {
        return signatureId;
    }

    public SignatureQuery setSignatureId(Long signatureId) {
        this.signatureId = signatureId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SignatureQuery setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public SignatureQuery setUseStatus(String useStatus) {
        this.useStatus = useStatus;
        return this;
    }
}
