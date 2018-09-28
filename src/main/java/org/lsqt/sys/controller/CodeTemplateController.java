package org.lsqt.sys.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.file.FileUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.CodeContent;
import org.lsqt.sys.model.CodeTemplate;
import org.lsqt.sys.model.CodeTemplateQuery;
import org.lsqt.sys.model.Project;
import org.lsqt.sys.model.ProjectQuery;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.model.TableSub;
import org.lsqt.sys.model.Variable;
import org.lsqt.sys.model.VariableQuery;
import org.lsqt.sys.service.CodeTemplateService;
import org.lsqt.sys.service.impl.CodeGenForSpring3Mybatis3Impl;

import com.alibaba.fastjson.JSON;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Controller(mapping={"/code_template"})
public class CodeTemplateController {
	
	@Inject private CodeTemplateService codeTemplateService; 
	
	@Inject private Db db; 
	
	
	@RequestMapping(mapping = { "/page", "/m/page" }, view = View.JSON)
	public Page<CodeTemplate> queryForPage(CodeTemplateQuery query) throws IOException {
		return codeTemplateService.queryForPage(query);
	}
	
	@RequestMapping(mapping = { "/all", "/m/all" },view = View.JSON)
	public Collection<CodeTemplate> getAll() {
		return codeTemplateService.getAll();
	}
	
	@RequestMapping(mapping = { "/save_or_update", "/m/save_or_update" }, view = View.JSON)
	public CodeTemplate saveOrUpdate(CodeTemplate form) {
		 
		return codeTemplateService.saveOrUpdate(form);
	}
	
	@RequestMapping(mapping = { "/delete", "/m/delete" },view = View.JSON)
	public int delete(String ids) {
		List<Long> list = StringUtil.split(Long.class, ids, ",");
		return codeTemplateService.deleteById(list.toArray(new Long[list.size()]));
	}


	
	@RequestMapping(mapping = { "/codegen", "/m/codegen" })
	public String codegen(Long tableId,String groupId,String modules,String entityName,String codegenType,String isCreateZip) throws Exception{
		// 暂只生成单表，
		String dir = codeTemplateService.codegenForSingle(codegenType, tableId, groupId, modules, entityName);
		
		if ("true".equals(isCreateZip)) {
			// 创建压缩zip
			String tmpDir = System.getProperty("java.io.tmpdir");
			String codeDir = (tmpDir + "codegen" + File.separator);
			FileUtil.deleteDir(new File(codeDir));

			String fileFullPath = CompressUtil.zip(dir, codeDir + System.currentTimeMillis() + ".zip", null);
			download(fileFullPath);
		}

		return dir;
	}
	
	private void download(String fileFullPath) {
		File zip = new File(fileFullPath);
		
		HttpServletResponse response = ContextUtil.getResponse();

		InputStream is = null;
		OutputStream os = null;
		try {
			String contentType = "application/x-zip-compressed; charset=UTF8";
			response.setContentType(contentType);
			response.setHeader("Content-disposition", "attachment;filename=\"" + URLEncoder.encode(zip.getName(), "UTF-8") + "\""); // 设置response的头信息
			
			is = new BufferedInputStream(new FileInputStream(zip));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 定义输出字节流
			
			os = new BufferedOutputStream(response.getOutputStream()); // 定义response的输出流

			byte[] buffer = new byte[4 * 1024]; // 4k Buffer
			int read = 0;
			while ((read = is.read(buffer)) != -1) { // 从文件中读入数据并写到输出字节流中
				baos.write(buffer, 0, read);
			}

			// 将输出字节流写到response的输出流中
			os.write(baos.toByteArray());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(is);
		}
	}
	
	private void close(Closeable ... obj) {
		for(Closeable e: obj) {
			try{
				e.close();
			}catch(Exception ex) {
				//ignore...
			}
		}
	}
	
	/**
	 * 主子表配置
	 * @param projectCode
	 * @param tableId
	 * @param subTableJson
	 */
	@RequestMapping(mapping = { "/save_or_update_main_sub", "/m/save_or_update_main_sub" })
	public void saveOrUpdateMainSub(String projectCode,Long id,String subTableJson) throws Exception{
		if(StringUtil.isBlank(projectCode) || id ==null || StringUtil.isBlank(subTableJson)) {
			throw new Exception("参数不能为空");
		}
		List<TableSub> list = JSON.parseArray(subTableJson,TableSub.class);
		
		ProjectQuery query = new ProjectQuery();
		query.setCode(projectCode);
		Project propject = db.queryForObject("queryForPage", Project.class, query);
		if(propject!=null) {
			
			Table table = db.getById(Table.class, id);
			
			db.executeUpdate("delete from "+db.getFullTable(TableSub.class)+" where main_table_id=?", id);
			
			for(TableSub e: list) {
				e.setAppCode(table.getAppCode());
				e.setDatasourceCode(table.getDataSourceCode());
				e.setMainTableId(table.getId());
				e.setMainTableName(table.getTableName());
				e.setVersion(table.getVersion());
				e.setProjectCode(propject.getCode());
				e.setSn(0);
				db.saveOrUpdate(e);
			}
		}
	}
	
	
	@RequestMapping(mapping = { "/do_codegen", "/m/do_codegen" },text="生成ORO、Model、Dao、Service、Controller、Page的其中一种代码")
	public CodeContent doCodegen(Long tmplId,Long tableId) {
		
		return codeTemplateService.codegenForTemplate(tmplId, tableId);
	}
	
	@RequestMapping(mapping = { "/save_template_content", "/m/save_template_content" },text="快速保存模板内容")
	public CodeTemplate saveTemplateContent(Long id,String content) {
		if(id==null || StringUtil.isBlank(content)) return null;
		
		CodeTemplate tmpl = db.getById(CodeTemplate.class, id);
		if(tmpl!=null) {
			tmpl.setContent(content);
			db.update(tmpl, "content");
		}
		return tmpl;
	}
}

/** 
 * ZIP压缩文件操作工具类 
 * 支持密码 
 * 依赖zip4j开源项目(http://www.lingala.net/zip4j/) 
 * 版本1.3.1 
 */
class CompressUtil {  
      
    /** 
     * 使用给定密码解压指定的ZIP压缩文件到指定目录 
     * <p> 
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出 
     * @param zip 指定的ZIP压缩文件 
     * @param dest 解压目录 
     * @param passwd ZIP文件的密码 
     * @return 解压后文件数组 
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出 
     */  
    public static File [] unzip(String zip, String dest, String passwd) throws ZipException {  
        File zipFile = new File(zip);  
        return unzip(zipFile, dest, passwd);  
    }  
      
    /** 
     * 使用给定密码解压指定的ZIP压缩文件到当前目录 
     * @param zip 指定的ZIP压缩文件 
     * @param passwd ZIP文件的密码 
     * @return  解压后文件数组 
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出 
     */  
    public static File [] unzip(String zip, String passwd) throws ZipException {  
        File zipFile = new File(zip);  
        File parentDir = zipFile.getParentFile();  
        return unzip(zipFile, parentDir.getAbsolutePath(), passwd);  
    }  
      
    /** 
     * 使用给定密码解压指定的ZIP压缩文件到指定目录 
     * <p> 
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出 
     * @param zip 指定的ZIP压缩文件 
     * @param dest 解压目录 
     * @param passwd ZIP文件的密码 
     * @return  解压后文件数组 
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出 
     */  
    public static File [] unzip(File zipFile, String dest, String passwd) throws ZipException {  
        ZipFile zFile = new ZipFile(zipFile);  
        zFile.setFileNameCharset("GBK");  
        if (!zFile.isValidZipFile()) {  
            throw new ZipException("压缩文件不合法,可能被损坏.");  
        }  
        File destDir = new File(dest);  
        if (destDir.isDirectory() && !destDir.exists()) {  
            destDir.mkdir();  
        }  
        if (zFile.isEncrypted()) {  
            zFile.setPassword(passwd.toCharArray());  
        }  
        zFile.extractAll(dest);  
          
        List<FileHeader> headerList = zFile.getFileHeaders();  
        List<File> extractedFileList = new ArrayList<File>();  
        for(FileHeader fileHeader : headerList) {  
            if (!fileHeader.isDirectory()) {  
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));  
            }  
        }  
        File [] extractedFiles = new File[extractedFileList.size()];  
        extractedFileList.toArray(extractedFiles);  
        return extractedFiles;  
    }  
      
    /** 
     * 压缩指定文件到当前文件夹 
     * @param src 要压缩的指定文件 
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败. 
     */  
    public static String zip(String src) {  
        return zip(src,null);  
    }  
      
    /** 
     * 使用给定密码压缩指定文件或文件夹到当前目录 
     * @param src 要压缩的文件 
     * @param passwd 压缩使用的密码 
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败. 
     */  
    public static String zip(String src, String passwd) {  
        return zip(src, null, passwd);  
    }  
      
    /** 
     * 使用给定密码压缩指定文件或文件夹到当前目录 
     * @param src 要压缩的文件 
     * @param dest 压缩文件存放路径 
     * @param passwd 压缩使用的密码 
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败. 
     */  
    public static String zip(String src, String dest, String passwd) {  
        return zip(src, dest, true, passwd);  
    }  
      
    /** 
     * 使用给定密码压缩指定文件或文件夹到指定位置. 
     * <p> 
     * dest可传最终压缩文件存放的绝对路径,也可以传存放目录,也可以传null或者"".<br /> 
     * 如果传null或者""则将压缩文件存放在当前目录,即跟源文件同目录,压缩文件名取源文件名,以.zip为后缀;<br /> 
     * 如果以路径分隔符(File.separator)结尾,则视为目录,压缩文件名取源文件名,以.zip为后缀,否则视为文件名. 
     * @param src 要压缩的文件或文件夹路径 
     * @param dest 压缩文件存放路径 
     * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.<br /> 
     * 如果为false,将直接压缩目录下文件到压缩文件. 
     * @param passwd 压缩使用的密码 
     * @return 最终的压缩文件存放的绝对路径,如果为null则说明压缩失败. 
     */  
    public static String zip(String src, String dest, boolean isCreateDir, String passwd) {  
        File srcFile = new File(src);  
        dest = buildDestinationZipFilePath(srcFile, dest);  
        ZipParameters parameters = new ZipParameters();  
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式  
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别  
        if (passwd!=null && !"".equals(passwd.trim())) {  
            parameters.setEncryptFiles(true);  
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式  
            parameters.setPassword(passwd.toCharArray());  
        }  
        try {  
            ZipFile zipFile = new ZipFile(dest);  
            if (srcFile.isDirectory()) {  
                // 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构  
                if (!isCreateDir) {  
                    File [] subFiles = srcFile.listFiles();  
                    ArrayList<File> temp = new ArrayList<File>();  
                    Collections.addAll(temp, subFiles);  
                    zipFile.addFiles(temp, parameters);  
                    return dest;  
                }  
                zipFile.addFolder(srcFile, parameters);  
            } else {  
                zipFile.addFile(srcFile, parameters);  
            }  
            return dest;  
        } catch (ZipException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * 构建压缩文件存放路径,如果不存在将会创建 
     * 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径 
     * @param srcFile 源文件 
     * @param destParam 压缩目标路径 
     * @return 正确的压缩文件存放路径 
     */  
    private static String buildDestinationZipFilePath(File srcFile,String destParam) {  
        if (destParam==null || "".equals(destParam)) {  
            if (srcFile.isDirectory()) {  
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";  
            } else {  
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));  
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";  
            }  
        } else {  
            createDestDirectoryIfNecessary(destParam);  // 在指定路径不存在的情况下将其创建出来  
            if (destParam.endsWith(File.separator)) {  
                String fileName = "";  
                if (srcFile.isDirectory()) {  
                    fileName = srcFile.getName();  
                } else {  
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));  
                }  
                destParam += fileName + ".zip";  
            }  
        }  
        return destParam;  
    }  
      
    /** 
     * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建 
     * @param destParam 指定的存放路径,有可能该路径并没有被创建 
     */  
    private static void createDestDirectoryIfNecessary(String destParam) {  
        File destDir = null;  
        if (destParam.endsWith(File.separator)) {  
            destDir = new File(destParam);  
        } else {  
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));  
        }  
        if (!destDir.exists()) {  
            destDir.mkdirs();  
        }  
    }  
  
    public static void main(String[] args) {  
        zip("E:\\workspace\\Test", "E:\\workspace\\Test\\Test.zip",null);
//      try {  
//          File[] files = unzip("d:\\test\\汉字.zip", "aa");  
//          for (int i = 0; i < files.length; i++) {  
//              System.out.println(files[i]);  
//          }  
//      } catch (ZipException e) {  
//          e.printStackTrace();  
//      }  
    }  

}