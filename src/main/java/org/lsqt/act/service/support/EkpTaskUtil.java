package org.lsqt.act.service.support;

import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lsqt.act.model.RunTask;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.file.PropertiesUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class EkpTaskUtil {
	private static final Logger  log = LoggerFactory.getLogger(EkpTaskUtil.class);
	/**
	 * 删除EKP系统待办数据
	 * @param inputTaskId
	 */
	public static void exeEkpDeleteTask(String inputTaskId) {
		String isEnable = PropertiesUtil.getValue("app.config.xuhui.task.push.isEnable");
		String url = PropertiesUtil.getValue("app.config.xuhui.task.push.url");
		
		if(StringUtil.isNotBlank(isEnable) && Boolean.valueOf(isEnable)) {
			String msg = HttpClientUtil.doGet(url+"/api/task_service/exe_ekp_delete_task?taskId="+inputTaskId);
			log.debug("删除待办返回：" + msg);
		}
	}
	
	/**
	 * 发送待办数据到EKP系统
	 * @param data
	 */
	public static void exeEkpSendTask(List<RunTask> data) {
		if (ArrayUtil.isBlank(data)) {
			return;
		}

		String isEnable = PropertiesUtil.getValue("app.config.xuhui.task.push.isEnable");
		String url = PropertiesUtil.getValue("app.config.xuhui.task.push.url");
		
		if (StringUtil.isNotBlank(isEnable) && Boolean.valueOf(isEnable)) {
			NameValuePair param = new BasicNameValuePair("data", JSON.toJSONString(data));
			String msg = HttpClientUtil.doPost(url + "/api/task_service/exe_ekp_send_task", Arrays.asList(param));
			log.debug("发送待办返回：" + msg);
		}
	}
	
}
