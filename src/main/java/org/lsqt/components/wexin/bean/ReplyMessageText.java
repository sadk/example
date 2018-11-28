package org.lsqt.components.wexin.bean;

/**
 * 回复文本消息
 * @author yuanmm
 *
 */
public class ReplyMessageText{
	public String toUserName; // 接收方帐号（收到的OpenID）
	public String fromUserName; // 开发者微信号
	public Long createTime;
	public String msgType="text";
	public String content; //回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
}
