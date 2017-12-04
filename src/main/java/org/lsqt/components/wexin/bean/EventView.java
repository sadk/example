package org.lsqt.components.wexin.bean;

/**
 * 点击菜单跳转链接时的事件推送
 * @author yuanmm
 *
 */
public class EventView {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="event";
	public String event="VIEW";  
	public String eventKey ; // 事件KEY值，设置的跳转URL
}
