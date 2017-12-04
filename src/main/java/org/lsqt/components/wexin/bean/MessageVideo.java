package org.lsqt.components.wexin.bean;

/**
 * 视频消息
 * @author yuanmm
 *
 */
public class MessageVideo {
	public String toUserName;
	public String fromUserName; //发送方帐号（一个OpenID）
	public Long createTime;
	public String msgType="video";
	public String mediaId; //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	public String thumbMediaId; //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	public Long msgId;
}
