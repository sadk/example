package org.lsqt.sys.service.impl;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.File;
import org.lsqt.sys.model.FileQuery;
import org.lsqt.sys.service.FileService;


/**
 * 文件服务相关(FastDFS服务器)
 * @author mmyuan
 *
 */
@Service
public class FileServiceImpl implements FileService{
	@Inject private Db db;
	
	private String configPath ;

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
	
	public File getById(Long id) {
		return db.getById(File.class, id);
	}
	
	public File saveOrUpdate(File model) {
		if (model.getCreateTime() == null) {
			model.setCreateTime(new Date());
		}
		if (model.getUpdateTime() == null) {
			model.setUpdateTime(new Date());
		}

		if (model.getId() == null) {
			db.save(model);
		} else {
			db.update(model);
		}
		return model;
	}

	public int deleteById(Long... ids) {
		int cnt = 0;
		for (Long id : ids) {
			db.deleteById(File.class, id);
			cnt++;
		}
		return cnt;
	}
 
	
	public List<File> getAll(){
		  return db.queryForList("getAll", File.class);
	}
	
	public Page<File> queryForPage(FileQuery query) {
		Page<File> page = db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), File.class, query);
		return page;
	}
	
	public List<File> queryForList(FileQuery query) {
		return db.queryForList("queryForPage", File.class, query);
	}
	

	public String upload(String localFileName) throws IOException {
		FastDFSUtil.setConfigPath(configPath);
		return FastDFSUtil.upload(localFileName);
	}

	
	public byte[] download(Long id) throws IOException {
		File file = db.getById(File.class,id);
		if(file == null) return null;
		
		FastDFSUtil.setConfigPath(configPath);
		return FastDFSUtil.download(file.getPath());
	}
	
	public static void main(String[] args) throws IOException {
		
		byte [] content = FastDFSUtil.download("group2/M00/00/00/rB92mFi-5PSAGVqYAAACiycUaoQ681.txt");
		System.out.println(new String(content,"GBK"));
	}

	static final Executor executor = Executors.newSingleThreadExecutor();
	public Long saveAndUpload(File model,final UploadComplete uploaded,boolean ... isSynchronization) throws IOException{
		if(StringUtil.isBlank(model.getLocalFilePath())) {
			throw new java.io.FileNotFoundException("没有找到本地文件路径~!");
		}

		final File m = saveOrUpdate(model);
		
		if(isSynchronization!=null && isSynchronization.length>1) {
			throw new UnsupportedOperationException("非语义化的入参,文件上传不能即是同步上传且又是异步上传~!");
		}
		
		// 同步上传
		if(isSynchronization == null || isSynchronization.length == 0 || isSynchronization[0]) {
			String path = FastDFSUtil.upload(model.getLocalFilePath());
			model.setPath(path);
			model.setStatus(File.STATUS_OK);
			
			return m.getId();
		}
		
		if(!isSynchronization[0]) { 
			executor.execute(new Runnable() {
				public void run() {
					try {
						String path = FastDFSUtil.upload(m.getLocalFilePath());
						uploaded.doComplete(path);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
		}
		return model.getId();
	}
}

/**
 * 文件上传下载服务工具类
 * @author mmyuan
 *
 */
class FastDFSUtil {
	private static final String CONFIG_PATH_DEFAULT = "fast_client.conf";
	private static String CONFIG_PATH = null; // classpath下用户自己定义的配置文件路径
	
	private static boolean isInited = false;
	private static TrackerClient trackerClient = null;
	private static TrackerServer trackerServer = null;
	private static StorageServer storageServer = null;
	private static StorageClient1 storageClient1 = null;
	
	private static void init(String configPath) {
		try {
			ClientGlobal.init(configPath);
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageServer = trackerClient.getStoreStorage(trackerServer);
			storageClient1 = new StorageClient1(trackerServer, storageServer);
		} catch (Exception ex) {
			isInited = false;
		}
		isInited = true;
	}
	
	public static void setConfigPath(String configPath) {
		if (isInited == false) {
			CONFIG_PATH = configPath;
		}
	}
	
	/**
	 * 返回FastDFS的文件ID
	 * @param localFileName
	 * @return
	 * @throws IOException
	 */
	public static String upload(String localFileName) throws IOException{
		String configPath = getConfigPath();
    	
    	if(!isInited) {
			init(configPath);
		}
    	
		try {
			String result = storageClient1.upload_file1(localFileName, getExtFile(localFileName), null);
			return result;
		} catch (MyException e) {
			throw new IOException(e);
		}
	}


	/**
	 * 
	 * @param groupName FastDFS的文件组
	 * @param path FastDFS的文件路径
	 * @return 
	 * @throws IOException 
	 */
	public static byte[] download(String path) throws IOException {
		String configPath = getConfigPath();
    	
    	if(!isInited) {
			init(configPath);
		}
    	
		try {
			if (path.startsWith("/")) { // 文件路径是这样的格式（没有横扛！！！）：group2/M00/00/00/rB92mFi_I_SABgkwAAACiycUaoQ315.txt
				path = path.substring(1, path.length());
			}
			if (path.startsWith("\\\\")) { // 用户打两个 \\xxx\path.txt 路径
				path = path.substring(2, path.length());
			}
			if (path.startsWith("\\")) { // 用户打一个 \xxx\path.txt 路径
				path = path.substring(1, path.length());
			}
			
			return storageClient1.download_file1(path);
		} catch (MyException e) {
			throw new IOException(e);
		}
		
	}
	
	// --------------------------------- 辅助方法 -------------------------------------------
	
	private static String getConfigPath() {
		String configPath = null;
		if (StringUtil.isBlank(CONFIG_PATH)) {
			configPath = CONFIG_PATH_DEFAULT;
		}

    	try{
    		configPath = FastDFSUtil.class.getClassLoader().getResource(configPath).getFile();
    	}catch(Exception ex) {
    		configPath = null;
    		throw new RuntimeException(" --- 没有找到默认的文件上传配置文件(fast_client.conf),也没有找到自定的配置文件~!");
    	}
		return configPath;
	}
	
	/** 获取文件后缀名 **/
	static String getExtFile(String localFileName) {
		int idx = localFileName.lastIndexOf(".");
		if(idx!=-1) {
			return localFileName.substring(idx+1,localFileName.length());
		}
		return "";
	}
	
}