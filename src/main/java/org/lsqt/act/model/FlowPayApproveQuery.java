package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 付款审批业务表
 */
public class FlowPayApproveQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**发起人ID*/	
		private String applyUserId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**发起人名称*/	
		private String applyUserName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**发起时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date applyTime ;
		
		
		
		
		
		
		/**付款说明*/	
		private String payRemark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建者id*/		 
		
		
		
		
		private Long createby ;
		
		
		
		
		
		
		
		
		
		
		/**创建者名称*/	
		private String createbyName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**修改者id*/		 
		
		
		
		
		private Long updateby ;
		
		
		
		
		
		
		
		
		
		
		/**修改者名称*/	
		private String updatebyName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**修改时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date updateDate ;
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setApplyUserId (String applyUserId               ) {
				this.applyUserId = applyUserId;
			}
			
			public String   
			getApplyUserId() {
			return this.applyUserId;
			}
    
			public void setApplyUserName (String applyUserName               ) {
				this.applyUserName = applyUserName;
			}
			
			public String   
			getApplyUserName() {
			return this.applyUserName;
			}
    
			public void setApplyTime (        java.util.Date applyTime         ) {
				this.applyTime = applyTime;
			}
			
			public java.util.Date   
			getApplyTime() {
			return this.applyTime;
			}
    
			public void setPayRemark (String payRemark               ) {
				this.payRemark = payRemark;
			}
			
			public String   
			getPayRemark() {
			return this.payRemark;
			}
    
			public void setCreateby (    Long createby            ) {
				this.createby = createby;
			}
			
			public Long   
			getCreateby() {
			return this.createby;
			}
    
			public void setCreatebyName (String createbyName               ) {
				this.createbyName = createbyName;
			}
			
			public String   
			getCreatebyName() {
			return this.createbyName;
			}
    
			public void setUpdateby (    Long updateby            ) {
				this.updateby = updateby;
			}
			
			public Long   
			getUpdateby() {
			return this.updateby;
			}
    
			public void setUpdatebyName (String updatebyName               ) {
				this.updatebyName = updatebyName;
			}
			
			public String   
			getUpdatebyName() {
			return this.updatebyName;
			}
    
			public void setUpdateDate (        java.util.Date updateDate         ) {
				this.updateDate = updateDate;
			}
			
			public java.util.Date   
			getUpdateDate() {
			return this.updateDate;
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
