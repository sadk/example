package org.lsqt.syswin.uum.model;

import org.lsqt.components.db.Page;

/**
 * 岗位分类表
 */
public class PositionCategoryQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**岗位分类名称*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**岗位分类编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**父分类id*/		 
		
		
		
		
		private Long pid ;
		
		
		
		
		
		
		
		
		
		
		/**结点路径*/	
		private String nodePath ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建者id*/		 
		
		
		
		
		private Long createUserId ;
		
		
		
		
		
		
		
		
		
		
		/**创建者名称*/	
		private String createUserName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**修改者id*/		 
		
		
		
		
		private Long updateUserId ;
		
		
		
		
		
		
		
		
		
		
		/**修改者名称*/	
		private String updateUserName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**修改时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date updateTime ;
		
		
		
		
		
		
	
	
	// getter、setter
    
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
    
			public void setPid (    Long pid            ) {
				this.pid = pid;
			}
			
			public Long   
			getPid() {
			return this.pid;
			}
    
			public void setNodePath (String nodePath               ) {
				this.nodePath = nodePath;
			}
			
			public String   
			getNodePath() {
			return this.nodePath;
			}
    
			public void setCreateUserId (    Long createUserId            ) {
				this.createUserId = createUserId;
			}
			
			public Long   
			getCreateUserId() {
			return this.createUserId;
			}
    
			public void setCreateUserName (String createUserName               ) {
				this.createUserName = createUserName;
			}
			
			public String   
			getCreateUserName() {
			return this.createUserName;
			}
    
    
			public void setUpdateUserId (    Long updateUserId            ) {
				this.updateUserId = updateUserId;
			}
			
			public Long   
			getUpdateUserId() {
			return this.updateUserId;
			}
    
			public void setUpdateUserName (String updateUserName               ) {
				this.updateUserName = updateUserName;
			}
			
			public String   
			getUpdateUserName() {
			return this.updateUserName;
			}
    
			public void setUpdateTime (        java.util.Date updateTime         ) {
				this.updateTime = updateTime;
			}
			
			public java.util.Date   
			getUpdateTime() {
			return this.updateTime;
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
