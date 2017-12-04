package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 流程定义扩展信息表
 */
public class ReDefinitionQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**流程定义ID*/	
		private String definitionId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**节点定义全称*/	
		private String definitionName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**流程定义简称*/	
		private String definitionShortName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**节点定义Key*/	
		private String definitionKey ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**前置角本*/	
		private String beforeScript ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**1=url 2=javascript_code 3=javacode */	
		private String beforeScriptType ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**后置角本*/	
		private String afterScript ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**1=url 2=javascript_code 3=javacode */	
		private String afterScriptType ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**租户编码*/	
		private String appCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**排序*/		 
		
		
		
		private Integer sn ;
		
		
		
		
		
		
		
		
		
		
		
		/**备注*/	
		private String remark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setDefinitionId (String definitionId               ) {
				this.definitionId = definitionId;
			}
			
			public String   
			getDefinitionId() {
			return this.definitionId;
			}
    
			public void setDefinitionName (String definitionName               ) {
				this.definitionName = definitionName;
			}
			
			public String   
			getDefinitionName() {
			return this.definitionName;
			}
    
			public void setDefinitionShortName (String definitionShortName               ) {
				this.definitionShortName = definitionShortName;
			}
			
			public String   
			getDefinitionShortName() {
			return this.definitionShortName;
			}
    
			public void setDefinitionKey (String definitionKey               ) {
				this.definitionKey = definitionKey;
			}
			
			public String   
			getDefinitionKey() {
			return this.definitionKey;
			}
    
			public void setBeforeScript (String beforeScript               ) {
				this.beforeScript = beforeScript;
			}
			
			public String   
			getBeforeScript() {
			return this.beforeScript;
			}
    
			public void setBeforeScriptType (String beforeScriptType               ) {
				this.beforeScriptType = beforeScriptType;
			}
			
			public String   
			getBeforeScriptType() {
			return this.beforeScriptType;
			}
    
			public void setAfterScript (String afterScript               ) {
				this.afterScript = afterScript;
			}
			
			public String   
			getAfterScript() {
			return this.afterScript;
			}
    
			public void setAfterScriptType (String afterScriptType               ) {
				this.afterScriptType = afterScriptType;
			}
			
			public String   
			getAfterScriptType() {
			return this.afterScriptType;
			}
    
			public void setAppCode (String appCode               ) {
				this.appCode = appCode;
			}
			
			public String   
			getAppCode() {
			return this.appCode;
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
