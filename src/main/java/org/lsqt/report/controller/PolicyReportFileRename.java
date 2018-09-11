package org.lsqt.report.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * 报表Excel文件上传到服务器，重命名策略
 * @author mingmin.yuan
 *
 */
public class PolicyReportFileRename implements FileRenamePolicy{
	
		/** 文件上传改名策略 **/
		public File rename(File file) {
			String body = "";
			String ext = "";
			int pot = file.getName().lastIndexOf(".");

			if (pot != -1) {
				ext = file.getName().substring(pot);
			} else {
				ext = "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			body = "rpt_"+sdf.format(date) + "";

			String newName = body + ext;
			file = new File(file.getParent(), newName);
			return file;
		}
}
