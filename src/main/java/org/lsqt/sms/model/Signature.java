package org.lsqt.sms.model;


public class Signature {

    private Long id;

    private Long signatureId;

    private String name;

    private int status;

    private String statusTransfered;

    private String createTime;

    private String updateTime;

    private String useStatus;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Signature setId(Long id) {
        this.id = id;
        return this;
    }

    public Signature setName(String name) {
        this.name = name;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Signature setStatus(int status) {
        this.status = status;
        return this;
    }

    public Long getSignatureId() {
        return signatureId;
    }

    public Signature setSignatureId(Long signatureId) {
        this.signatureId = signatureId;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Signature setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Signature setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getStatusTransfered() {
        return statusTransfered;
    }

    public Signature setStatusTransfered(String statusTransfered) {
        this.statusTransfered = statusTransfered;
        return this;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public Signature setUseStatus(String useStatus) {
        this.useStatus = useStatus;
        return this;
    }
}
