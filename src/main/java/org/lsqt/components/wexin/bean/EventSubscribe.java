package org.lsqt.components.wexin.bean;

/**
 * 关注/取消事件
 * @author yuanmm
 *
 */
public class EventSubscribe {
	public String event_取消订阅="unsubscribe";
	public String event_订阅="subscribe";
	
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="event";
	public String event; // 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	
	
}
