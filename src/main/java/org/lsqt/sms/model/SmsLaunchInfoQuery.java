package org.lsqt.sms.model;

import org.lsqt.components.db.Page;

/**
 * 短信投放表
 */
public class SmsLaunchInfoQuery {
	private Integer pageIndex= Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize= Page.DEFAULT_PAGE_SIZE;
	
	private String sortOrder;
	private String sortField;
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	
		/**投放ID*/	
		private String launchId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**上传包ID*/	
		private String packageId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**签名ID*/	
		private String signId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**文案ID*/	
		private String templId ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**投放状态：0 审核中 1 初始化   2 进行中 3 成功 4 失败 5 审核失败*/	
		private String launchStatus ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**计划投放数量*/		 
		
		
		
		
		private Long num ;
		
		
		
		
		
		
		
		
		
		
		/**计划投放时间*/		 
		
		
		
		
		
		
		
		
		private String bgnTime ;
		
		
		
		
		
		
		/**腾讯发送状态：0 审核中 1 初始化   2 进行中 3 成功 4 失败 5 审核失败*/	
		private String sendStatus ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**腾讯发送描述*/	
		private String sendMsg ;
	 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/**投放开始时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date launchBgnTime ;
		
		
		
		
		
		
		/**投放结束时间*/		 
		
		
		
		
		
		
		
		
		private java.util.Date launchEndTime ;
		
		
		
		
		
		
		/**投放成功数量*/		 
		
		
		
		
		private Long succNum ;
		
		
		
		
		
		
		
		
		
		
		/**投放失败数量*/		 
		
		
		
		
		private Long failNum ;
		
		
		
		
		
		
		
		
		
		
		/**用户接收成功数量*/		 
		
		
		
		
		private Long recvNum ;




		private String createTime;
		
		
		
		
		
		
	
	
	// getter、setter
    
			public void setLaunchId (String launchId               ) {
				this.launchId = launchId;
			}
			
			public String   
			getLaunchId() {
			return this.launchId;
			}
    
			public void setPackageId (String packageId               ) {
				this.packageId = packageId;
			}
			
			public String   
			getPackageId() {
			return this.packageId;
			}
    
			public void setSignId (String signId               ) {
				this.signId = signId;
			}
			
			public String   
			getSignId() {
			return this.signId;
			}
    
			public void setTemplId (String templId               ) {
				this.templId = templId;
			}
			
			public String   
			getTemplId() {
			return this.templId;
			}
    
			public void setLaunchStatus (String launchStatus               ) {
				this.launchStatus = launchStatus;
			}
			
			public String   
			getLaunchStatus() {
			return this.launchStatus;
			}
    
			public void setNum (    Long num            ) {
				this.num = num;
			}
			
			public Long   
			getNum() {
			return this.num;
			}

	public String getBgnTime() {
		return bgnTime;
	}

	public void setBgnTime(String bgnTime) {
		this.bgnTime = bgnTime;
	}

	public void setSendStatus (String sendStatus               ) {
				this.sendStatus = sendStatus;
			}
			
			public String   
			getSendStatus() {
			return this.sendStatus;
			}
    
			public void setSendMsg (String sendMsg               ) {
				this.sendMsg = sendMsg;
			}
			
			public String   
			getSendMsg() {
			return this.sendMsg;
			}
    
			public void setLaunchBgnTime (        java.util.Date launchBgnTime         ) {
				this.launchBgnTime = launchBgnTime;
			}
			
			public java.util.Date   
			getLaunchBgnTime() {
			return this.launchBgnTime;
			}
    
			public void setLaunchEndTime (        java.util.Date launchEndTime         ) {
				this.launchEndTime = launchEndTime;
			}
			
			public java.util.Date   
			getLaunchEndTime() {
			return this.launchEndTime;
			}
    
			public void setSuccNum (    Long succNum            ) {
				this.succNum = succNum;
			}
			
			public Long   
			getSuccNum() {
			return this.succNum;
			}
    
			public void setFailNum (    Long failNum            ) {
				this.failNum = failNum;
			}
			
			public Long   
			getFailNum() {
			return this.failNum;
			}
    
			public void setRecvNum (    Long recvNum            ) {
				this.recvNum = recvNum;
			}
			
			public Long   
			getRecvNum() {
			return this.recvNum;
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

	public String getCreateTime() {
		return createTime;
	}

	public SmsLaunchInfoQuery setCreateTime(String createTime) {
		this.createTime = createTime;
		return this;
	}
}
