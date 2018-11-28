package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 厂区与门店关系表
 */
public class StoreCompanyQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**厂区id*/	
		private String companyCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**厂区名称*/	
		private String companyName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**门店id*/	
		private String storeCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**门店名称*/	
		private String storeName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date createTime ;
		
		
		
		
		
		
		/**修改时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date updateTime ;
		
		
		
		
		
		
		/**租户编码*/	
		private String tenantCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setCompanyCode (String companyCode               ) {
				this.companyCode = companyCode;
			}
			
			public String   
			getCompanyCode() {
			return this.companyCode;
			}
    
			public void setCompanyName (String companyName               ) {
				this.companyName = companyName;
			}
			
			public String   
			getCompanyName() {
			return this.companyName;
			}
    
			public void setStoreCode (String storeCode               ) {
				this.storeCode = storeCode;
			}
			
			public String   
			getStoreCode() {
			return this.storeCode;
			}
    
			public void setStoreName (String storeName               ) {
				this.storeName = storeName;
			}
			
			public String   
			getStoreName() {
			return this.storeName;
			}
    
			public void setCreateTime (        java.util.Date createTime         ) {
				this.createTime = createTime;
			}
			
			public java.util.Date   
			getCreateTime() {
			return this.createTime;
			}
    
			public void setUpdateTime (        java.util.Date updateTime         ) {
				this.updateTime = updateTime;
			}
			
			public java.util.Date   
			getUpdateTime() {
			return this.updateTime;
			}
    
			public void setTenantCode (String tenantCode               ) {
				this.tenantCode = tenantCode;
			}
			
			public String   
			getTenantCode() {
			return this.tenantCode;
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
