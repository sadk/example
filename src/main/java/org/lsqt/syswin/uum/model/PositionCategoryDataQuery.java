package org.lsqt.syswin.uum.model;

import org.lsqt.components.db.Page;

/**
 * 岗位归类数据表
 */
public class PositionCategoryDataQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**岗位分类（维度）ID,引用t_duties表id*/		 
		
		
		
		
		private Long positCategoryId ;
		
		
		
		
		
		
		
		
		
		
		/**岗位ID*/		 
		
		
		
		
		private Long positionId ;
		
		
		
		
		
		
		
		
		
		
		/**岗位所在的组织(冗余字段)*/		 
		
		
		
		
		private Long orgId ;
		
		
		
		
		
		
		
		
		
		
		/**(冗余的)组织名称*/	
		private String orgName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**(冗余的)组织节点路径*/	
		private String orgNodePath ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setPositCategoryId (    Long positCategoryId            ) {
				this.positCategoryId = positCategoryId;
			}
			
			public Long   
			getPositCategoryId() {
			return this.positCategoryId;
			}
    
			public void setPositionId (    Long positionId            ) {
				this.positionId = positionId;
			}
			
			public Long   
			getPositionId() {
			return this.positionId;
			}
    
			public void setOrgId (    Long orgId            ) {
				this.orgId = orgId;
			}
			
			public Long   
			getOrgId() {
			return this.orgId;
			}
    
			public void setOrgName (String orgName               ) {
				this.orgName = orgName;
			}
			
			public String   
			getOrgName() {
			return this.orgName;
			}
    
			public void setOrgNodePath (String orgNodePath               ) {
				this.orgNodePath = orgNodePath;
			}
			
			public String   
			getOrgNodePath() {
			return this.orgNodePath;
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
