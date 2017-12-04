package org.lsqt.components.wexin.bean;

/**
 * 文本消息
 * @author yuanmm
 *
 */
public class MessageText {
	public String toUserName; // 开发者微信号
	public String fromUserName; // 发送方帐号（一个OpenID）
	public Long createTime;
	public String msgType="text";
	public String content;
	public Long msgId;
}
