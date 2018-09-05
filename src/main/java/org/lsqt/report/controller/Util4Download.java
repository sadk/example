package org.lsqt.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.context.ContextUtil;

public class Util4Download {
	
	/**
	 * 
	 * @param serverPath 服务器端文件路径,如： /upload
	 * @param name 要下载的文件名
	 * @throws Exception 
	 */
	public static void download(String serverPath,String name) throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		HttpServletResponse response = ContextUtil.getResponse();
		
		String root = request.getServletContext().getRealPath("/");
        File file = new File(root+"/"+serverPath);    
        String ext = file.getName().substring(file.getName().lastIndexOf("."),file.getName().length());
       
        FileInputStream  input = new FileInputStream(file);  
        ServletOutputStream output = response.getOutputStream();
        
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode((name+ext),"utf-8"));
		response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/vnd.ms-excel;charset=gbk2312");
		
       // response.setContentType("application/x-download"); //application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
       // response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(name+ext, "UTF-8"));
        
        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input,output);
        output.flush();
        input.close();
        output.close(); 
	}
}
