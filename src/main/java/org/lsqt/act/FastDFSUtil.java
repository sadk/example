package org.lsqt.act;

import java.io.IOException;
import java.net.URLDecoder;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.lsqt.components.util.lang.StringUtil;

/**
 * 文件上传下载服务工具类
 * @author mmyuan
 *
 */
public class FastDFSUtil {
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
			if(storageClient1 == null) {
				init(configPath);
			}
			String result = storageClient1.upload_file1(localFileName, getExtFile(localFileName), null);
			return result;
		} catch (MyException e) {
			throw new IOException(e);
		}
	}

	/**
	 * 返回FastDFS的文件ID
	 * @param localFileName
	 * @return
	 * @throws IOException
	 */
	public static String upload(byte [] fileBuff,String fileExtName) throws IOException{
		String configPath = getConfigPath();
    	
    	if(!isInited) {
			init(configPath);
		}
    	
		try {
			String result = storageClient1.upload_file1(fileBuff,fileExtName,null);
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
			
			if(storageClient1 == null) {
				init(configPath);
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
    		
    		configPath = URLDecoder.decode(FastDFSUtil.class.getClassLoader().getResource(configPath).getFile(),"UTF-8");
    	}catch(Exception ex) {
    		configPath = null;
    		throw new RuntimeException(" --- 没有找到默认的文件上传配置文件(fast_client.conf),也没有找到自定的配置文件~!");
    	}
		return configPath;
	}
	

	/** 获取文件后缀名 **/
	public static String getExtFile(String localFileName) {
		int idx = localFileName.lastIndexOf(".");
		if(idx!=-1) {
			return localFileName.substring(idx+1,localFileName.length());
		}
		return "";
	}
	
	// --------------------------------     -------------------------------------------------------------
	
public static void main(String[] args) throws IOException {
		
		byte [] content = FastDFSUtil.download("group2/M00/00/00/rB92mFi-5PSAGVqYAAACiycUaoQ681.txt");
		System.out.println(new String(content,"GBK"));
	}
	
}
