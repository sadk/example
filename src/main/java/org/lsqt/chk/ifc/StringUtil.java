package org.lsqt.chk.ifc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串帮助工具
 * 
 * @author quanyou.chen
 *
 */
public class StringUtil {
    
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    
    /**
     * A的ASCII码
     */
    private static final char[] UPPER_HEX_CHAR =
        new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 字符串转换成byte[]
     * 
     * @param str
     * @return
     * @throws BaseException
     */
    public static byte[] getBytes(String str) throws Exception {
    	return str.getBytes("utf-8");
    }

    /**
     * bytes转换成16进制字符串（大写）
     * 
     * @param data
     * @return
     */
    public static String bytesToUpHex(byte[] data) {
        return bytesToHex(data, UPPER_HEX_CHAR);
    }
    
    /**
     * 16进制字符串（大写）转换成bytes
     * 
     * @param hexString
     * @return
     */
    public static byte[] upHexToBytes(String hexString) {
        return hexToBytes(hexString, 'A');
    }

    /**
     * 把byte[]转换成16进制字符串
     * 
     * @param data
     * @param charStart
     * @return
     */
    private static String bytesToHex(byte[] data, char[] char16) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(char16[0xf & data[i]]);
            sb.append(char16[0xf & (data[i] >> 4)]);
        }
        return sb.toString();
    }
    
    /**
     * 把16进制大写字符串转换成byte[]
     * 
     * @param hexString
     * @return
     */
    private static byte[] hexToBytes(String hexString, char charStart) {
        byte[] resultBytes = new byte[hexString.length() / 2];
        char[] hexChars = hexString.toCharArray();
        int pre = 0;
        int fix = 0;
        int startNum = charStart - 10;
        for (int i = 0; i < hexChars.length; i += 2) {
            pre = 0xf & (hexChars[i] < charStart ? hexChars[i] : (hexChars[i] - startNum));
            fix = 0xf & (hexChars[i + 1] < charStart ? hexChars[i + 1] : hexChars[i + 1] - startNum);
            resultBytes[i / 2] = (byte) (pre | (fix << 4));
        }
        return resultBytes;
    }
}
       
