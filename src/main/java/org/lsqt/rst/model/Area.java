package org.lsqt.rst.model;


/**
 * 城市区域元数据
 */
public class Area {
	
		
		/***/	
	 
		
		
		
		
		private Long id ;
		
		
		
		
		
		
		
		
		
		
		
		/**城市编码*/	
		private String code ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**城市名称*/	
		private String name ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**父级城市编码*/	
		private String parentCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**数据更新日期*/	
	 
		
		
		
		
		
		
		
		
		private java.util.Date updateDate ;
		
		
		
		
		
		
		
		/**租户编码*/	
		private String tenantCode ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	// getter、setter
		public void setId (    Long id            ) {
			this.id = id;
		}
		
		public Long   
		getId() {
		return this.id;
    }
		public void setCode (String code               ) {
			this.code = code;
		}
		
		public String   
		getCode() {
		return this.code;
    }
		public void setName (String name               ) {
			this.name = name;
		}
		
		public String   
		getName() {
		return this.name;
    }
		public void setParentCode (String parentCode               ) {
			this.parentCode = parentCode;
		}
		
		public String   
		getParentCode() {
		return this.parentCode;
    }
		public void setUpdateDate (        java.util.Date updateDate         ) {
			this.updateDate = updateDate;
		}
		
		public java.util.Date   
		getUpdateDate() {
		return this.updateDate;
    }
		public void setTenantCode (String tenantCode               ) {
			this.tenantCode = tenantCode;
		}
		
		public String   
		getTenantCode() {
		return this.tenantCode;
    }
	
}
