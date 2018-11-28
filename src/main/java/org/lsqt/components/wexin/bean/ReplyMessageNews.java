package org.lsqt.components.wexin.bean;

/**
 * 回复图文消息  
 * 
 * @author yuanmm
 *
 */
public class ReplyMessageNews{
	public String toUserName;
	public String fromUserName;
	public Long createTime;
	public String msgType = "news";
	public Integer articleCount ; //	图文消息个数，限制为10条以内
	public String articles ;  // 	多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	public String title; 
	public String description ;
	public String picUrl; //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	public String url; //	点击图文消息跳转链接
}
