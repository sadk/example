package org.lsqt.cms.model;


/**
 * 站点资源
 */
public class Resource {
	
		
		/***/	
	 
		
		
		
		
		private Long id ;
		
		
		
		
		
		
		
		
		
		
		
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
