package org.lsqt.syswin.authority.model;

import org.lsqt.components.db.Page;

/**
 * 基本数据表
 */
public class PositionPermitConfigQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**岗位id*/		 
		
		
		
		
		private Long positionId ;
		
		
		
		
		
		
		
		
		
		
		/**父岗位id*/		 
		
		
		
		
		private Long positionIdParent ;
		
		
		
		
		
		
		
		
		
		
		/**节点路径*/	
		private String nodePath ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**权限数据ID（组织结点ID）*/	
		private String orgIds ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setPositionId (    Long positionId            ) {
				this.positionId = positionId;
			}
			
			public Long   
			getPositionId() {
			return this.positionId;
			}
    
			public void setPositionIdParent (    Long positionIdParent            ) {
				this.positionIdParent = positionIdParent;
			}
			
			public Long   
			getPositionIdParent() {
			return this.positionIdParent;
			}
    
			public void setNodePath (String nodePath               ) {
				this.nodePath = nodePath;
			}
			
			public String   
			getNodePath() {
			return this.nodePath;
			}
    
			public void setOrgIds (String orgIds               ) {
				this.orgIds = orgIds;
			}
			
			public String   
			getOrgIds() {
			return this.orgIds;
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
