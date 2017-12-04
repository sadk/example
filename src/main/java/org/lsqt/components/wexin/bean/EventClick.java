package org.lsqt.components.wexin.bean;

/**
 * 自定义菜单事件
 * <pre>
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报。
 * </pre>
 * @author yuanmm
 *
 */
public class EventClick {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="event";
	public String event="CLICK";  
	public String eventKey ; // 事件KEY值，与自定义菜单接口中KEY值对应
}
