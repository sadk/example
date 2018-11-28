package org.lsqt.components.wexin.bean;

/**
 * 扫描带参数二维码事件
 * @author yuanmm
 *
 */
public class EventSubscribeScan {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="event";
	
	/**
	 * 1.用户未关注时：event=subscribe
	 * 2.用户已关注时：event=SCAN
	 */
	public String event; 
	public String event_未关注="subscribe";
	public String event_已关注="SCAN";
	
	/**
	 * 1.用户未关注时：事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 * 2.用户已关注时：事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 **/
	public String eventKey ;
	
	
	public String ticket;  // 二维码的ticket，可用来换取二维码图片
	
	
}
