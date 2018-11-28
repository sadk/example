package org.lsqt.sys.service;

import java.io.IOException;
import java.util.List;

import org.lsqt.components.db.Page;
import org.lsqt.sys.model.File;
import org.lsqt.sys.model.FileQuery;


/**
 * 文件服务相关
 * @author mmyuan
 *
 */
public interface FileService {
	
	interface UploadComplete {
		void doComplete(String path);
	}
	
	File getById(Long id);
	
	File saveOrUpdate(File model);

	int deleteById(Long... ids);
	
	List<File> getAll();
	
	Page<File> queryForPage(FileQuery query);
	
	List<File> queryForList(FileQuery query);
	
	
	// ------------------------ 上传下载服务 ---------------------------
	/**
	 * 设置文件服务器的IP端口等配置文件信息(默认文件名称是: fast_client.conf)
	 * 
	 * @param path 相对于classPath下的文件路径(含文件名)
	 */
	void setConfigPath(String path);
	
	/**
	 * 保存文件信息入库，指定参数可异步上传到文件服务器，默认是同步方式
	 * @param model 
	 * @param uploaded 上传后的回调，多用于异步上传后，改变文件对象的Db状态
	 * @param isSynchronization 等于true表示同步执行，false表示异步上传
	 * @return
	 */
	Long saveAndUpload(File model,UploadComplete uploaded,boolean ... isSynchronization) throws IOException;
	
	/**
	 * 
	 * @param localFileName 文件的全路径地址
	 * @return
	 * @throws IOException
	 */
	String upload(String localFileName) throws IOException;
	
	/**
	 * 
	 * @param groupName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	byte[] download(Long id) throws IOException;
}
