package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 用户规则矩阵：流程启用设置
 */
public class UserRuleMatrixFlowQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**用户规则ID*/		 
		
		
		
		
		private Long userRuleId ;
		
		
		
		
		
		
		
		
		
		
		/**流程定义Id*/	
		private String definitionId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**流程节点*/	
		private String taskKey ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**流程节点名称*/	
		private String taskName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setUserRuleId (    Long userRuleId            ) {
				this.userRuleId = userRuleId;
			}
			
			public Long   
			getUserRuleId() {
			return this.userRuleId;
			}
    
			public void setDefinitionId (String definitionId               ) {
				this.definitionId = definitionId;
			}
			
			public String   
			getDefinitionId() {
			return this.definitionId;
			}
    
			public void setTaskKey (String taskKey               ) {
				this.taskKey = taskKey;
			}
			
			public String   
			getTaskKey() {
			return this.taskKey;
			}
    
			public void setTaskName (String taskName               ) {
				this.taskName = taskName;
			}
			
			public String   
			getTaskName() {
			return this.taskName;
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
