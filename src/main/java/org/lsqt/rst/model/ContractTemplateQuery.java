package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 合同模板管理
 */
public class ContractTemplateQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**工厂ID*/	
		private String companyId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**模板名*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**模板编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**是否启用: 1=启用  0=禁用*/		 
		
		
		
		private Integer enable ;
		
		
		
		
		
		
		
		
		
		
		
		/**模板内容*/	
		private String content ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***/		 
		
		
		
		private Integer sn ;
		
		
		
		
		
		
		
		
		
		
		
		/**用户备注*/	
		private String remark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String appCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String tenantCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setCompanyId (String companyId               ) {
				this.companyId = companyId;
			}
			
			public String   
			getCompanyId() {
			return this.companyId;
			}
    
			public void setName (String name               ) {
				this.name = name;
			}
			
			public String   
			getName() {
			return this.name;
			}
    
			public void setCode (String code               ) {
				this.code = code;
			}
			
			public String   
			getCode() {
			return this.code;
			}
    
			public void setEnable (   Integer enable             ) {
				this.enable = enable;
			}
			
			public Integer   
			getEnable() {
			return this.enable;
			}
    
			public void setContent (String content               ) {
				this.content = content;
			}
			
			public String   
			getContent() {
			return this.content;
			}
    
			public void setSn (   Integer sn             ) {
				this.sn = sn;
			}
			
			public Integer   
			getSn() {
			return this.sn;
			}
    
			public void setRemark (String remark               ) {
				this.remark = remark;
			}
			
			public String   
			getRemark() {
			return this.remark;
			}
    
			public void setAppCode (String appCode               ) {
				this.appCode = appCode;
			}
			
			public String   
			getAppCode() {
			return this.appCode;
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
