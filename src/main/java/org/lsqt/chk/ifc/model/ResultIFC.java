package org.lsqt.chk.ifc.model;

/**
 * 用户名与身份证是否匹配响应结果
 * @author mm
 *
 */
public class ResultIFC {
	public static final String CODE_OK="0000";
	
	public String code;
	public String desc;
	public Result result;
	
	public static class Result {
		public static final String RESULT_CODE_MATCH_OK="R000";
		
		public String uniquenum; // 业务实体唯一识别码(用于发送过去，再回传的值)
		public String resultCode;
		public String resultMsg;
		public String name; // 用户姓名
		public String certno; //身份证号
		public String policeadd; // 
		public String photo;
		public String sex;
		public String channel; // 渠道：IFC(佰仟金融IFC身份认证平台)
	}
}

