package org.lsqt.sms.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.model.Request;
import org.lsqt.sms.model.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

public class CommonUtils {

    public static Long[] split2Array(String ids) {
        String[] strs = ids.split(",");
        Long[] longs = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            longs[i] = Long.parseLong(strs[i]);
        }
        return longs;
    }

    public static Result<Map<String, Object>> resp(String resultMsg) {
        return StringUtil.isBlank(resultMsg) ? Result.ok("操作成功") : Result.fail(resultMsg);
    }

    public static String valueOf(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    public static JSONObject sendRequest(String url, String serviceId, Map<String, Object> params) {
        String content = Request.newInstance().api(url).serivceId(serviceId).
                params(params).execute();
        return JSON.parseObject(content);
    }

    public static void fileDownload(String fileAbsolutePath, String fileName, String suffix, boolean deleteAfterDownload) throws UnsupportedEncodingException {
        if (StringUtil.isBlank(fileAbsolutePath)) {
            throw new RuntimeException("文件路径不可为空");
        }

        HttpServletResponse response = ContextUtil.getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + "." + suffix);
        response.setContentType("application/octet-stream");
        try (OutputStream os = new BufferedOutputStream(response.getOutputStream());
             InputStream is = new BufferedInputStream(new FileInputStream(fileAbsolutePath))
        ) {
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败", e.getCause());
        } finally {
            File fileForDownload = new File(fileAbsolutePath);
            if (deleteAfterDownload && fileForDownload.exists()) {
                fileForDownload.delete();
            }
        }

    }

}
