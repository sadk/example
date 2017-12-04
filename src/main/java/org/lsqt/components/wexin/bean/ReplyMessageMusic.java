package org.lsqt.components.wexin.bean;

/**
 * 回复音乐消息
 * @author yuanmm
 *
 */
public class ReplyMessageMusic {
	public String toUserName;
	public String fromUserName; //发送方帐号（一个OpenID）
	public Long createTime;
	public String msgType="music";
	public String title; // 
	public String description; // 
	public String musicURL; // 	音乐链接
	public String hqMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	public String thumbMediaId; //缩略图的媒体id，通过素材管理接口上传多媒体文件，得到的id
	
}
