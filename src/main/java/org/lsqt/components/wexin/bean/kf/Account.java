package org.lsqt.components.wexin.bean.kf;

/**
 * 客服账号
 * @author yuanmm
 *
 */
public class Account {
	public String account; //完整客服账号，格式为：账号前缀@公众号微信号
	public String nick; //	客服昵称
	public String id; //	客服工号
	public String nickName; //客服昵称，最长6个汉字或12个英文字符
	
	public String password; // 客服账号登录密码，格式为密码明文的32位加密MD5值。该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
	
	public String media; //	该参数仅在设置客服头像时出现，是form-data中媒体文件标识，有filename、filelength、content-type等信息
	
}
