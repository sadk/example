package org.lsqt.components.wexin.bean;

/**
 * 回复图片消息
 * 
 * @author yuanmm
 *
 */
public class ReplyMessagePic{
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType = "image";
	public String mediaId; //通过素材管理接口上传多媒体文件，得到的id。
}
