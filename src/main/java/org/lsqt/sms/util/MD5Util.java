package org.lsqt.sms.util;

import com.alibaba.fastjson.JSON;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class MD5Util {

    private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    /**
     * MD5加密
     * @param origin 字符
     * @param charsetname 编码
     * @return
     */
    public static String MD5Encode(String origin, String charsetname){
        String resultString = null;
        try{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(null == charsetname || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }catch (Exception e){
        }
        return resultString;
    }


    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();
        for(int i = 0; i < b.length; i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

    public static void main(String[] args) {
    	Map<String,Object> map = new HashMap<>();
    	java.util.List <Long> list = new ArrayList<>();
    	list.add(169849L);
    	map.put("sign_id", list);
		System.out.println(MD5Encode("2TXB3EU2YQWN4OVXZA4N4J6P4WUOQXZX1539158999000sms-query-sign".concat(JSON.toJSONString(map) ), "utf-8"));
	}
}
 

