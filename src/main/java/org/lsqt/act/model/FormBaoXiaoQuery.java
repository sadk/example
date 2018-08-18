package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 报销管理表
 */
public class FormBaoXiaoQuery {
	private Integer pageIndex=Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize=Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**申请标题*/	
		private String titile ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**单据流水号*/	
		private String flowNo ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**填制人部门ID*/	
		private String createDeptId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**填制人部门*/	
		private String createDeptName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**报销金额*/		 
		
		
		
		
		
		
		private Double money ;
		
		
		
		
		
		
		
		
		/**审批的业务状态*/		 
		
		
		
		private Integer status ;
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String statusDesc ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**租户编码*/	
		private String appCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**排序*/		 
		
		
		
		private Integer sn ;
		
		
		
		
		
		
		
		
		
		
		
		/**备注*/	
		private String remark ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setTitile (String titile               ) {
				this.titile = titile;
			}
			
			public String   
			getTitile() {
			return this.titile;
			}
    
			public void setFlowNo (String flowNo               ) {
				this.flowNo = flowNo;
			}
			
			public String   
			getFlowNo() {
			return this.flowNo;
			}
    
			public void setCreateDeptId (String createDeptId               ) {
				this.createDeptId = createDeptId;
			}
			
			public String   
			getCreateDeptId() {
			return this.createDeptId;
			}
    
			public void setCreateDeptName (String createDeptName               ) {
				this.createDeptName = createDeptName;
			}
			
			public String   
			getCreateDeptName() {
			return this.createDeptName;
			}
    
			public void setMoney (      Double money           ) {
				this.money = money;
			}
			
			public Double   
			getMoney() {
			return this.money;
			}
    
			public void setStatus (   Integer status             ) {
				this.status = status;
			}
			
			public Integer   
			getStatus() {
			return this.status;
			}
    
			public void setStatusDesc (String statusDesc               ) {
				this.statusDesc = statusDesc;
			}
			
			public String   
			getStatusDesc() {
			return this.statusDesc;
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
