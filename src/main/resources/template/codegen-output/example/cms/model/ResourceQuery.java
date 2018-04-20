package org.lsqt.cms.model;

import org.lsqt.components.db.Page;

/**
 * 站点资源
 */
public class ResourceQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/***/		 
		
		
		
		
		private Long pid ;
		
		
		
		
		
		
		
		
		
		
		/**资源名称*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**资源值*/	
		private String value ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**资源编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**数据类型：10=目录 20=文件 */	
		private String type ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**是否启用: 1=启用  0=禁用*/	
		private String enable ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**系统编码*/	
		private String appCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**排序*/		 
		
		
		
		private Integer sn ;
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String nodePath ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**备注*/	
		private String remark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setPid (    Long pid            ) {
				this.pid = pid;
			}
			
			public Long   
			getPid() {
			return this.pid;
			}
    
			public void setName (String name               ) {
				this.name = name;
			}
			
			public String   
			getName() {
			return this.name;
			}
    
			public void setValue (String value               ) {
				this.value = value;
			}
			
			public String   
			getValue() {
			return this.value;
			}
    
			public void setCode (String code               ) {
				this.code = code;
			}
			
			public String   
			getCode() {
			return this.code;
			}
    
			public void setType (String type               ) {
				this.type = type;
			}
			
			public String   
			getType() {
			return this.type;
			}
    
			public void setEnable (String enable               ) {
				this.enable = enable;
			}
			
			public String   
			getEnable() {
			return this.enable;
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
    
			public void setNodePath (String nodePath               ) {
				this.nodePath = nodePath;
			}
			
			public String   
			getNodePath() {
			return this.nodePath;
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
