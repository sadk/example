package org.lsqt.rst.model;


/**
 * 合同模板管理
 */
public class ContractTemplate {
	
		
		/***/	
	 
		
		
		
		
		private Long id ;
		
		
		
		
		
		
		
		
		
		
		
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
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/***/	
		private String gid ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建日期*/	
	 
		
		
		
		
		
		
		
		
		private java.util.Date createTime ;
		
		
		
		
		
		
		
		/***/	
	 
		
		
		
		
		
		
		
		
		private java.util.Date updateTime ;
		
		
		
		
		
		
	
	
	
	// getter、setter
		public void setId (    Long id            ) {
			this.id = id;
		}
		
		public Long   
		getId() {
		return this.id;
    }
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
		public void setGid (String gid               ) {
			this.gid = gid;
		}
		
		public String   
		getGid() {
		return this.gid;
    }
		public void setCreateTime (        java.util.Date createTime         ) {
			this.createTime = createTime;
		}
		
		public java.util.Date   
		getCreateTime() {
		return this.createTime;
    }
		public void setUpdateTime (        java.util.Date updateTime         ) {
			this.updateTime = updateTime;
		}
		
		public java.util.Date   
		getUpdateTime() {
		return this.updateTime;
    }
	
}
