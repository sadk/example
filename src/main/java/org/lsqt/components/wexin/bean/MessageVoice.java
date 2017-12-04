package org.lsqt.components.wexin.bean;

/**
 * 回复语音消息
 * @author yuanmm
 *
 */
public class MessageVoice {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType="voice";
	public String mediaId;
}
