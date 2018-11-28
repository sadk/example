package org.lsqt.components.wexin.bean;

/**
 * 上报地址位置事件
 * <pre>
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。
 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL
 * </pre>
 * @author yuanmm
 *
 */
public class EventLocation {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="event";
	public String event="LOCATION";  
	
	public Double latitude ; //地理位置纬度
	public Double longitude ;//地理位置经度
	public Double precision; //	地理位置精度
} 
