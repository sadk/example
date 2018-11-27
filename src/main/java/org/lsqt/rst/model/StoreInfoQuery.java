package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 门店信息表
 */
public class StoreInfoQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**门店编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**门店名称*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**所属区域*/	
		private String belongRegion ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date createDate ;
		
		
		
		
		
		
		/**修改时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date updateDate ;
		
		
		
		
		
		
		/**租户编码*/	
		private String tenantCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setCode (String code               ) {
				this.code = code;
			}
			
			public String   
			getCode() {
			return this.code;
			}
    
			public void setName (String name               ) {
				this.name = name;
			}
			
			public String   
			getName() {
			return this.name;
			}
    
			public void setBelongRegion (String belongRegion               ) {
				this.belongRegion = belongRegion;
			}
			
			public String   
			getBelongRegion() {
			return this.belongRegion;
			}
    
			public void setCreateDate (        java.util.Date createDate         ) {
				this.createDate = createDate;
			}
			
			public java.util.Date   
			getCreateDate() {
			return this.createDate;
			}
    
			public void setUpdateDate (        java.util.Date updateDate         ) {
				this.updateDate = updateDate;
			}
			
			public java.util.Date   
			getUpdateDate() {
			return this.updateDate;
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
