package org.lsqt.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;


public class Util4Download {
	
	/**
	 * 
	 * @param serverPath 服务器端文件路径,如： /upload/data/xxx.xlsx
	 * @param name 要下载的文件名
	 * @throws Exception 
	 */
	public static void download(String serverPath,String displayName) throws Exception {
		HttpServletRequest request = ContextUtil.getRequest();
		HttpServletResponse response = ContextUtil.getResponse();
		
		String root = request.getServletContext().getRealPath("/");
        File file = new File(root+"/"+serverPath);    
        String ext = file.getName().substring(file.getName().lastIndexOf("."),file.getName().length());
       
		try (FileInputStream input = new FileInputStream(file);
				ServletOutputStream output = response.getOutputStream()) {

			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode((displayName+ext),"utf-8"));
			response.addHeader("Content-Length", "" + file.length());
			//response.setContentType("application/vnd.ms-excel;charset=utf8");
			
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			
			while ((len = input.read(buffer)) > 0) { // 循环将输入流中的内容读取到缓冲区当中
				
				output.write(buffer, 0, len);// 输出缓冲区的内容到浏览器，实现文件下载
			} 
        } catch(Exception e) {
        	throw e;
        }
		//input.close();
		//input.close();
        
	}
}
