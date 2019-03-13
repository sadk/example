package org.lsqt.sms.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sms.model.Result;
import org.lsqt.sms.model.SmsMobileNoPackage;
import org.lsqt.sms.model.SmsMobileNoPackageQuery;
import org.lsqt.sms.service.SmsMobileNoPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;

import static org.lsqt.sms.util.CommonUtils.resp;


@Controller(mapping={"/smsMobileNoPackage"})
public class SmsMobileNoPackageController {

	private static final Logger log = LoggerFactory.getLogger(SmsMobileNoPackageController.class);
	private String url ;
	private String appId;
	private String appKey;

	@Inject
    private SmsMobileNoPackageService smsMobileNoPackageService;
	
	@Inject
    private Db db;
	
	@RequestMapping(mapping = { "/page", "/m/page" })
	public Page<SmsMobileNoPackage> queryForPage(SmsMobileNoPackageQuery query) throws IOException {
		return smsMobileNoPackageService.queryForPage(query); //  
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" },text="1.号码包上传", excludeTransaction = true)
	public Result<Map<String,Object>> upload() throws Exception {
			HttpServletRequest request = ContextUtil.getRequest();

			String filePath = request.getServletContext().getRealPath("/") + "/upload";
			log.debug("输出存放上传文件所到的路径   {}",filePath);

			File uploadPath = new File(filePath);
			// 检查文件夹是否存在 不存在 创建一个
			if (!uploadPath.exists()) {
				uploadPath.mkdir();
			}

		int fileSize = 200;//文件最大允许200M
		int fileLineNums = 0;
		//String savePath = "xxx";//文件的保存目录

		FileRenamePolicy fileNamePolicy = new FileRenamePolicy() {
			public File rename(File file) {
				String body = "";
				String ext = "";
				String headStr = "";
				int pot = file.getName().lastIndexOf(".");
				if (pot != -1) {
					ext = file.getName().substring(pot);
					headStr = file.getName().substring(0, pot);
				} else {
					ext = "";
				}
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_S");
				//Date date = new Date();
				//body = sdf.format(date) + "";
				body = headStr + "_"+String.valueOf(new Random().nextInt(1000000));
				String newName = body + ext;
				file = new File(file.getParent(), newName);
				return file;
			}
		};

		MultipartRequest mulit = new MultipartRequest(ContextUtil.getRequest(), filePath, fileSize * 1024 * 1024, "UTF-8",fileNamePolicy);
		String resultMsg = null;

		//String userName = mulit.getParameter("userName");
		//System.out.println(userName);

		int cnt = 0;
		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); //原始文件名
			if (fileName != null) {
				cnt++;
			}
			File uploadFile = mulit.getFile(id);
			fileLineNums = getFileLineNumber(uploadFile);
			System.out.println("fileName:"+fileName);
			if(!fileName.endsWith(".txt")){
				return Result.fail("只能上传txt格式");
			}
			if(fileLineNums==0){
				return Result.fail("号码包内容不能为空");
			}
			resultMsg = smsMobileNoPackageService.uploadMobilePackage(uploadFile, fileLineNums);

			log.debug("文件名：" + fileName + " 文件类型： " + contentType+" 原始文件名："+originalFileName);
		}
		System.out.println("上传文件行数："+fileLineNums);
		log.debug("共上传" + cnt + "个文件！");
		if(StringUtil.isBlank(resultMsg)){
			return Result.ok("上传成功!");
		}
		return Result.fail(resultMsg);
	}


	/**
	 * 获取文件行数
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private int getFileLineNumber(File file) throws IOException {
		LineNumberReader lnr = new LineNumberReader(new FileReader(file));
		lnr.skip(Long.MAX_VALUE);
		int lineNo = lnr.getLineNumber() + 1;
		lnr.close();
		return lineNo;
	}

    /**
	 * 更新号码包状态
	 * @param ids
     * @return
     */
	@RequestMapping(mapping = { "/updatePackageStatus", "/m/updatePackageStatus" })
	public Result<Map<String,Object>> updatePackageStatus(String ids){
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		String resultMsg = smsMobileNoPackageService.updatePackageStatus(list.get(0));
		if(StringUtil.isBlank(resultMsg)){
			return Result.ok("更新状态成功!");
		}
		return Result.fail(resultMsg);
	}

	@RequestMapping(mapping = { "/all", "/m/all" })
	public Collection<SmsMobileNoPackage> getAll() {
		return smsMobileNoPackageService.getAll();
	}

	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" })
	public SmsMobileNoPackage saveOrUpdate(SmsMobileNoPackage form) {
		return smsMobileNoPackageService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" })
	public Result<Map<String, Object>> delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return resp(smsMobileNoPackageService.deleteById(list.toArray(new Long[list.size()])));
	}
	
	
	/** 暂时注释，未能提供通用实现！！！
	@RequestMapping(mapping = { "/export", "/m/export" })
	public void export(SmsMobileNoPackageQuery query, String exportFileType, String exportDataType) {
		Page<SmsMobileNoPackage> page = db.getEmptyPage();
		
		// 1.导出excel
		if (Dictionary.EXPORT_FILE_TYPE_EXCEL.equals(exportFileType)) {
			
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				
				page.setData(smsMobileNoPackageService.getAll());
				ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
				return ;
			} 
			
			page = smsMobileNoPackageService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.xls", "page", page);
			
			return;
		}

		// 2.导出文本文件
		if (Dictionary.EXPORT_FILE_TYPE_TXT.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsMobileNoPackageService.getAll());
				ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
				return;
			}

			page = smsMobileNoPackageService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.txt", "page", page);
			return;
		}
		
		// 3.导出doc文件
		if (Dictionary.EXPORT_FILE_TYPE_DOC.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsMobileNoPackageService.getAll());
				ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
				return ;
			}
			
			page = smsMobileNoPackageService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.doc", "page", page);
			return;
		}

		// 4.导出pdf文件
		if (Dictionary.EXPORT_FILE_TYPE_PDF.equals(exportFileType)) {
			if (Dictionary.EXPORT_DATA_TYPE_全部数据.equals(exportDataType)) {
				page.setData(smsMobileNoPackageService.getAll());
				ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
				return ;
			}
			
			page = smsMobileNoPackageService.queryForPage(query);
			ContextUtil.file.put("/template/应用列表数据.pdf", "page", page);
			return;
		}
		
	}
	**/
}
