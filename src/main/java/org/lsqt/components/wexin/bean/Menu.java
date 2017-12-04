package org.lsqt.components.wexin.bean;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	
	public static final String ERRCODE_菜单创建_成功="0";
	
	// request
	public Button button;
 
	
	//response
	public String errcode;
	public String errmsg;
	
	
	// 菜单按钮类型
	public static final String TYPE_CLICK = "click";
	public static final String TYPE_VIEW = "view";
	public static final String TYPE_SCANCODE_PUSH = "scancode_push";
	public static final String TYPE_SCANCODE_WAITMSG = "scancode_waitmsg";
	public static final String TYPE_PIC_SYSPHOTO = "pic_sysphoto";
	public static final String TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
	public static final String TYPE_PIC_WEIXIN = "pic_weixin";
	public static final String TYPE_LOCATION_SELECT = "location_select";
	public static final String TYPE_MEDIA_ID = "media_id";
	public static final String TYPE_VIEW_LIMITED = "view_limited";
	
	public static class Button {
		public String type;
		public String name;
		public String key;
		
		public String url;
		public String mediaId;
		
		public List<Button> subButton = new ArrayList<>();
		
	}
	
}
