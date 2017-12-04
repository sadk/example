package org.lsqt.components.wexin.bean;

/**
 * 图片消息
 * 
 * @author yuanmm
 *
 */
public class MessagePic {
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType = "image";
	public String picUrl;
	public String mediaId;
	public Long msgId;
}
