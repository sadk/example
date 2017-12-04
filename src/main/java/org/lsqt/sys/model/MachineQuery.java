package org.lsqt.sys.model;

import org.lsqt.components.db.Page;

/**
 * 服务器管理
 */
public class MachineQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**服务器编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String appCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**服务器名称*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**登陆名称*/	
		private String userName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**登陆密码*/	
		private String userPassword ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**链接url*/	
		private String url ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**数据源状态：1=可用 0=不可用*/		 
		
		
		
		private Integer status ;
		
		
		
		
		
		
		
		
		
		
		
		/**地址,可以是ip也可以是域名*/	
		private String address ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**端口*/	
		private String port ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**排序*/		 
		
		
		
		private Integer sn ;
		
		
		
		
		
		
		
		
		
		
		
		/**备注*/	
		private String remark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setCode (String code               ) {
				this.code = code;
			}
			
			public String   
			getCode() {
			return this.code;
			}
    
			public void setAppCode (String appCode               ) {
				this.appCode = appCode;
			}
			
			public String   
			getAppCode() {
			return this.appCode;
			}
    
			public void setName (String name               ) {
				this.name = name;
			}
			
			public String   
			getName() {
			return this.name;
			}
    
			public void setUserName (String userName               ) {
				this.userName = userName;
			}
			
			public String   
			getUserName() {
			return this.userName;
			}
    
			public void setUserPassword (String userPassword               ) {
				this.userPassword = userPassword;
			}
			
			public String   
			getUserPassword() {
			return this.userPassword;
			}
    
			public void setUrl (String url               ) {
				this.url = url;
			}
			
			public String   
			getUrl() {
			return this.url;
			}
    
			public void setStatus (   Integer status             ) {
				this.status = status;
			}
			
			public Integer   
			getStatus() {
			return this.status;
			}
    
			public void setAddress (String address               ) {
				this.address = address;
			}
			
			public String   
			getAddress() {
			return this.address;
			}
    
			public void setPort (String port               ) {
				this.port = port;
			}
			
			public String   
			getPort() {
			return this.port;
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
