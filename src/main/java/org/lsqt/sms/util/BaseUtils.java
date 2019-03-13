package org.lsqt.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 基础功能工具类。
 */
public class BaseUtils {

	/**
	 * 获取: 32位小写MD5。
	 */
	public static String parseStrToMd5L32(String str) {
		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bytes) {
				int bt = b & 0xff;
				if (bt < 16) {
					stringBuffer.append(0);
				}
				stringBuffer.append(Integer.toHexString(bt));
			}
			reStr = stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return reStr;
	}

	/**
	 * 获取精确到秒的时间戳,13位长度long，最后三位为0。
	 */
	public static long getTimeStampFmt3() {
		return System.currentTimeMillis() / 1000 * 1000;
	}

	/**
	 * 将Map的内容拼接成Get请求中Url后面的参数字符串返回。比如：?A=1&B=2&C=xx
	 */
	public static String joinGetParameters(Map<String, Object> parameterMap) {
		StringBuilder arg = new StringBuilder();
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			arg.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return "?" + arg.substring(0, arg.length() - 1);
	}
}
