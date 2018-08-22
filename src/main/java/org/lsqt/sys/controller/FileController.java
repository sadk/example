package org.lsqt.sys.controller;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.sys.model.File;
import org.lsqt.sys.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 文件
 */
@Controller(mapping={"/file"})
public class FileController {
	private static final Logger  log = LoggerFactory.getLogger(FileController.class);
	@Inject private FileService fileService;


	@RequestMapping(mapping={"/download","/m/download"})
	public void download(Long id,HttpServletResponse response) {
		try{
			File file = fileService.getById(id);
			if(file!=null) {
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getOriginalName(), "UTF-8"));
				response.setContentType("application/octet-stream; charset=UTF8");
			    
			    OutputStream os = response.getOutputStream();
			    os.write(fileService.download(id));
			    os.flush();
			    os.close();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
