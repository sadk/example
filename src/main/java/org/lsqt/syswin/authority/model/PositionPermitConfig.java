package org.lsqt.syswin.authority.model;


/**
 * 基本数据表
 */
public class PositionPermitConfig {
	
		
		/**pk*/	
	 
		
		
		
		
		private Long id ;
		
		
		
		
		
		
		
		
		
		
		
		/**岗位id*/	
	 
		
		
		
		
		private Long positionId ;
		
		
		
		
		
		
		
		
		
		
		
		/**父岗位id*/	
	 
		
		
		
		
		private Long positionIdParent ;
		
		
		
		
		
		
		
		
		
		
		
		/**节点路径*/	
		private String nodePath ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**权限数据ID（组织结点ID）*/	
		private String orgIds ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	// getter、setter
		public void setId (    Long id            ) {
			this.id = id;
		}
		
		public Long   
		getId() {
		return this.id;
    }
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
	
}