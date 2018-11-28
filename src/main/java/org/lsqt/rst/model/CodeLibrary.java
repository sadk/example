package org.lsqt.rst.model;


/**
 * 码表
 */
public class CodeLibrary {
	
		
		/**编码*/	
		private String codeNo ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**项目编号*/	
		private String itemNo ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**项目名称*/	
		private String itemName ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**创建时间*/	
	 
		
		
		
		
		
		
		
		
		private java.util.Date createTime ;
		
		
		
		
		
		
		
		/**创建人*/	
		private String createUser ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**更新时间*/	
	 
		
		
		
		
		
		
		
		
		private java.util.Date updateTime ;
		
		
		
		
		
		
		
		/**更新人*/	
		private String updateUser ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**状态(0:有效,1:无效)*/	
	 
		
		
		
		private Integer status ;
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	// getter、setter
		public void setCodeNo (String codeNo               ) {
			this.codeNo = codeNo;
		}
		
		public String   
		getCodeNo() {
		return this.codeNo;
    }
		public void setItemNo (String itemNo               ) {
			this.itemNo = itemNo;
		}
		
		public String   
		getItemNo() {
		return this.itemNo;
    }
		public void setItemName (String itemName               ) {
			this.itemName = itemName;
		}
		
		public String   
		getItemName() {
		return this.itemName;
    }
		public void setCreateTime (        java.util.Date createTime         ) {
			this.createTime = createTime;
		}
		
		public java.util.Date   
		getCreateTime() {
		return this.createTime;
    }
		public void setCreateUser (String createUser               ) {
			this.createUser = createUser;
		}
		
		public String   
		getCreateUser() {
		return this.createUser;
    }
		public void setUpdateTime (        java.util.Date updateTime         ) {
			this.updateTime = updateTime;
		}
		
		public java.util.Date   
		getUpdateTime() {
		return this.updateTime;
    }
		public void setUpdateUser (String updateUser               ) {
			this.updateUser = updateUser;
		}
		
		public String   
		getUpdateUser() {
		return this.updateUser;
    }
		public void setStatus (   Integer status             ) {
			this.status = status;
		}
		
		public Integer   
		getStatus() {
		return this.status;
    }
	
}
