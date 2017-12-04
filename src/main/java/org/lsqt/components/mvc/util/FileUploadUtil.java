package org.lsqt.components.mvc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
/**/
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;

public class FileUploadUtil {
	private  static final Logger log = Logger.getLogger(FileUploadUtil.class.getName());
	
	public void upload(HttpServletRequest request) throws Exception{
		/*
		//servlet 3.0 api
		Collection<Part> collection = request.getParts();
		for(Part p : collection) {
			p.write(p.getName()+System.currentTimeMillis());
		}*/
		
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		/* 	if(!isMultipart) throw new RuntimeException("not multipart/form-data!");
		
		//String tmpDir = System.getProperty("java.io.tmpdir");
		File repository = (File) request.getServletContext().getAttribute(ServletContext.TEMPDIR);
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(100*1024); //上传文件时内存使用的阈值100M
		factory.setRepository(repository); //默认的临时目录
 
	
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(20*1024); // 设置整体请求大小约束
		
		
		// 显示进度
		ProgressListener progressListener = new ProgressListener(){
		   public void update(long pBytesRead, long pContentLength, int pItems) {
		       System.out.println("We are currently reading item " + pItems);
		       if (pContentLength == -1) {
		    	   log.info("So far, " + pBytesRead + " bytes have been read.");
		       } else {
		    	   log.info("So far, " + pBytesRead + " of " + pContentLength   + " bytes have been read.");
		       }
		   }
		};
		upload.setProgressListener(progressListener);

		Map<String,Item> fileMap = new LinkedHashMap<String,Item>();
		// Parse the request
		FileItemIterator iter = upload.getItemIterator(request);
		while (iter.hasNext()) {
		    FileItemStream item = iter.next();
		    String name = item.getFieldName();
		    InputStream stream = item.openStream();
		    if (item.isFormField()) {
		    	//log.info("Form field " + name + " with value "  + Streams.asString(stream) + " detected.");
		    	IOUtils.closeQuietly(stream);
		    	continue ;
		    }
		   
		    String originName = item.getName();
		    String endFix = "";
		    if(originName.lastIndexOf(".")!=-1){
		    	endFix = originName.substring(originName.lastIndexOf("."), originName.length());
		    }
		    
	        File file = new File(repository.getAbsolutePath()+File.separator+System.nanoTime()+endFix);
	        if(file.exists()) {
	        	log.info("file exists , override it!!! "+file);
	        }
	        
			FileOutputStream os = null;
			try {
				os = new FileOutputStream(file);
				int cnt = IOUtils.copy(stream, os);
				log.info( cnt +" bytes");
			} catch(IOException ioe){
				throw ioe;
			}finally {
				IOUtils.closeQuietly(os);
				IOUtils.closeQuietly(stream);
			}
		    
			//fileMap.put(name, new Item(name,item.getName(),))
		}*/
	}
	
	public static class Item {
		public String reqName;
		public String sourceName;
		public List<File> files;
		public Item(String reqName,String sourceName,List<File> files) {
			this.reqName = reqName;
			this.sourceName = sourceName;
			this.files = files ;
		}
	}
}
