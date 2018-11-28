package org.lsqt.components.wexin.bean;

/**
 * 回复视频消息
 * @author yuanmm
 *
 */
public class ReplyMessageVideo {
	public String toUserName;
	public String fromUserName;  
	public Long createTime;
	public String msgType="video";
	public String mediaId; //通过素材管理接口上传多媒体文件，得到的id
	public String title;  
	public Long description;
}
