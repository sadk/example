package org.lsqt.chk.mq;

import org.lsqt.components.db.orm.ORMappingIdGenerator;

/**
 * 用户犯罪记录请求参数模型
 * @author mm
 *
 */
public class UserCrimeRequest {
	public String business_id = new ORMappingIdGenerator().getUUID58().toString();
	public String channel_type = "JLKJ";
	public String name; // 客户姓名
	public String id_no; // 客户身份证号
	public String mobile_no;// 客户手机号

}

