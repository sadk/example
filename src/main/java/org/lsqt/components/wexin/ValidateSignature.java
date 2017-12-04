package org.lsqt.components.wexin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 验证消息签名 .  Sky
 *
 */
public class ValidateSignature {
	/**
	 * 验证事件消息签名
	 * @param signature
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public boolean validateSignature(String signature,String token, String timestamp,String nonce) {
		String[] array = new String[]{token,timestamp,nonce};
		Arrays.sort(array);
		StringBuffer s=new StringBuffer();
		for(String e:array){
			s.append(e);
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1"); 
			byte[] b = md.digest(s.toString().getBytes());
			String bsignature = byte2hex(b);
			return bsignature.equals(signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 转化为16进制字符串
	 * 
	 * @param digest
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
}
