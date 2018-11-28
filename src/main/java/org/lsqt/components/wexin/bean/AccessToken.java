package org.lsqt.components.wexin.bean;

import java.io.IOException;
import java.util.Map;

import org.lsqt.components.util.http.HttpClientUtil;

import com.alibaba.fastjson.JSON;

public class AccessToken {
	// request
	public String grantType;
	public String appId ;
	public String secret;
	
	// response
	public String accessToken;
	public String expiresIn;
	
	public String errcode;
	public String errmsg;
	
	@SuppressWarnings("rawtypes")
	public static void main(String ...args) throws IOException {
		final String appId = "wx5364f45b2780898a";
		final String appSecret = "e7a302267942321f974ddc9b6e4a2c19";

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;

		String json = HttpClientUtil.execute(url);
		Map map = JSON.parseObject(json,Map.class);
		
		String ipUrl= "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+map.get("access_token");
		HttpClientUtil.execute(ipUrl);
	}
}
