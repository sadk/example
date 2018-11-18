package org.lsqt.chk.ifc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IFCClient {
	private static final Logger log = LoggerFactory.getLogger(IFCClient.class);
	/**
	 * 匹配用户名和身份证号是否正常
	 * @param name 用户姓名,如“张三”
	 * @param idCard 身份证号
	 * @return
	 * @throws Exception
	 */
	public static String getIdCardInfo(String name,String idCard) throws Throwable {
        String appkey = UserCrimeIFCConfig.getAppKey();
        String host = UserCrimeIFCConfig.getApiUrlBase(); // sit环境地址和端口,如: HTTP://IP:PORT
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String params = "uniquenum="+System.currentTimeMillis()+"&userid=B00144123&username=zhangsan&name="+name+"&certno="+idCard+"&date="+date+"&appkey=" + appkey;
       
        byte[] md5Sign = CryptologyUtil.digestByMd5(StringUtil.getBytes(params)); // md5摘要
       
        byte[] pubKey = CryptologyUtil.getPublicKey(UserCrimeIFCConfig.getCerPath()).getEncoded(); // 公钥加密
       
        byte[] crypPub = CryptologyUtil .encryptByPublicKey(StringUtil.getBytes(params + "&sign=" + StringUtil.bytesToUpHex(md5Sign)), pubKey); // 加密
        
        String crypPubStr = StringUtil.bytesToUpHex(crypPub);// 编码
        
        crypPubStr = appkey + '&' + crypPubStr; // 拼接
        crypPubStr = StringUtil.bytesToUpHex(StringUtil.getBytes(crypPubStr));
        
		log.info("appkey：{}", appkey);
		log.info("公钥：{}", StringUtil.bytesToUpHex(pubKey));
		log.info("请求参数：{}", params);
		log.info("请求host：{}", host);
		log.info("data={}", crypPubStr);

		String postUrl = host + "/certification/identity/verifyIdentity";
		Map<String, String> param = new HashMap<>();
		param.put("data", crypPubStr);

		String json = post(postUrl, param);
		log.info("返回JSON：" + json);
		return json;
	}
    
	
    /**
     * 
     * @param url 接口地址(不带参数)
     * @param params 请求参数
     * @return 返回响应字符
     * @throws Throwable
     */
    private static String post(String url, Map<String,String> params) throws Throwable {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestMethod("POST");// 设置POST方式
        con.setConnectTimeout(2000);// 设置连接超时时间2秒
        con.setReadTimeout(10000);// 设置读取返回结果超时时间10秒
        con.setDoOutput(true);
        
        // 拼凑参数
        StringBuffer paramsStr = new StringBuffer();
        for (Entry<String,String> param : params.entrySet()) {
            paramsStr.append(param.getKey());
            paramsStr.append("=");
            paramsStr.append(param.getValue());
            paramsStr.append("&");
            
        }
        paramsStr.setLength(paramsStr.length() - 1);
        byte[] bypes = paramsStr.toString().getBytes();
        con.getOutputStream().write(bypes); // 发送请求
        
		// 获取请求结果
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (Exception e) {
			if (e.getCause() != null) {
				throw e.getCause();
			} else {
				throw e;
			}
		}
    }
    
}

