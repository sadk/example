package org.lsqt.uum.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.lsqt.components.util.file.IOUtil;
import org.lsqt.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeUtil {
	private static final Logger  log = LoggerFactory.getLogger(CodeUtil.class);
	
	public static String UID="uid";
	
	/**
	 * 转化为16进制字符串
	 * 
	 * @param b 字符串的字节码数组
	 * @return
	 */
	public static String byte2hex(byte[] b) {
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
	
	/**
	 * 16进制字符串转化为字符串
	 * @param text 16进制的字符串
	 * @return
	 */
	public static byte[] hex2byte(String text) {
		if (text == null || text.equals("")) {
			return null;
		}
		text = text.replace(" ", "");
		byte[] baKeyword = new byte[text.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(text.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		return baKeyword;
	}
	
	// -------------------------------------------------------- 加密、解密-------------------------------------------
	
	/**
	 * 密码加密（不可逆）
	 * @param text 密码明文
	 * @return
	 */
	public static String passwodEncrypt(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] b = md.digest(text.getBytes());
			return byte2hex(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static SecretKeySpec keySpec;
	static {
		try{
			 //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKye = keyGenerator.generateKey();
            byte[] keyBytes =  secretKye.getEncoded();
            //转换key
            keySpec= new SecretKeySpec(keyBytes, "AES");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 对称加密
	 * @param text 明文
	 * @return 加密后的字符串（16进制表示）
	 */
	public static String encode2(String text) {
		try {
			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] result = cipher.doFinal(text.getBytes());

			return byte2hex(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对称解密
	 * @param text 密文
	 * @return
	 */
	public static String decode2(String str) {
		byte[] text = hex2byte(str);
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			// 解密
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] raw = cipher.doFinal(text);
			return new String(raw);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}


	/**
	 * 简单加密
	 * @param text
	 * @return
	 */
	public static String simpleEncode(String text) {
		try {
			return URLEncoder.encode(Base64.getEncoder().encodeToString(text.getBytes("utf8")), "utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 简单解密
	 * @param text
	 * @return
	 */
	public static String simpleDecode(String text) {
		try{
			return new String(Base64.getDecoder().decode(URLDecoder.decode(text, "utf8")),"utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		System.out.println(passwodEncrypt("123456"+User.PWD_SALT));
		if(true)return ;
		String json = IOUtil.getFileContent(new File("D:\\workspace\\Activiti_5_19_0\\modules\\activiti-webapp-explorer2-ext\\src\\main\\java\\org\\lsqt\\syswin\\uum\\controller\\json.txt"), "utf-8");
		
		String convert1 = URLEncoder.encode(Base64.getEncoder().encodeToString(json.getBytes("utf8")),"utf8");
		
		
		System.out.println(convert1.length()+"---"+convert1);
		
		String rs = new String(Base64.getDecoder().decode(URLDecoder.decode(convert1, "utf8")),"utf8");
		System.out.println(rs);
		
		//ltYnVyc2VtZW50In0%3D
		//ltYnVyc2VtZW50In0%3D
	}

}
