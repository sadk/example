package org.lsqt.components.wexin.bean;

/**
 * 链接消息
 * @author yuanmm
 *
 */
public class MessageLink {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="link";
	public String title;
	public String description;
	public String url;
	public Long msgId;
}
