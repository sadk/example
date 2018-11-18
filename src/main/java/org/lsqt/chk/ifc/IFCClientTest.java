package org.lsqt.chk.ifc;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class IFCClientTest {
	
	
	
    public static void main(String[] args) throws Exception {
        // appkey
        String appkey = "9BE5E637AD17C495EF646CAC53176D2E";
        // sit环境地址和端口
        String host = "http://10.83.36.90:8080";
        // 唯一标识
        String uniquenum = UUID.randomUUID().toString().replace("-", "");
        
        String params = "uniquenum="+1+"&userid=B00144123&username=zhangsan&name=袁明敏&certno=430408198410203012&date=20170413170400&appkey=" + appkey;
        // md5摘要
        byte[] md5Sign = CryptologyUtil.digestByMd5(StringUtil.getBytes(params));
        // 公钥加密
        byte[] pubKey = CryptologyUtil.getPublicKey("E:/workspace/example/src/main/java/org/lsqt/chk/ifc/1542527181176.cer").getEncoded();
        // 加密
        byte[] crypPub = CryptologyUtil
            .encryptByPublicKey(StringUtil.getBytes(params + "&sign=" + StringUtil.bytesToUpHex(md5Sign)), pubKey);
        // 编码
        String crypPubStr = StringUtil.bytesToUpHex(crypPub);
        // 拼接
        crypPubStr = appkey + '&' + crypPubStr;
        crypPubStr = StringUtil.bytesToUpHex(StringUtil.getBytes(crypPubStr));
        
        System.out.println("appkey：" + appkey);
        System.out.println("公钥：" + StringUtil.bytesToUpHex(pubKey));
        System.out.println("请求参数：" + params);
        System.out.println("请求host：" + host);
        System.out.println("data=" + crypPubStr);
        System.out.println("-----------------------------------------");
        // String postUrl = host + "/certification/identity/verifyIdentity";
        String postUrl = host + "/certification/identity/verifyIdentity";
        Map<String,String> param = new HashMap<>();
        param.put("data", crypPubStr);
        System.out.println("返回JSON：" + postHtml(postUrl, param));
        System.out.println("\n");
        
    }
    
    /**
     * POST请求 
     * @author quanyou.chen
     * @date: 2017年4月11日 下午12:46:21
     * @Title: postHtml    
     * @param url  http/https请求url
     * @param params  参数
     * @return
     * @throws IOException String 返回值
     */
    public static String postHtml(String url, Map<String,String> params) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // 设置POST方式
        con.setRequestMethod("POST");
        // 设置连接超时时间2秒
        con.setConnectTimeout(2000);
        // 设置读取返回结果超时时间10秒
        con.setReadTimeout(10000);
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
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuffer response = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }
    
}

