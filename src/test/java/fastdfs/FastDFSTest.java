package fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSTest {
	 private static TrackerClient trackerClient = null;
	 private static TrackerServer trackerServer = null;
	 private static StorageServer storageServer = null;
	 
	 //使用StorageClient1进行上传
	 private static StorageClient1 storageClient1 = null;
	    
    public static void main(String[] args) throws Exception {
        //不要带classpath
        FastDFSClient client = new FastDFSClient("fast_client.conf");
        String result = client.uploadFile("D:\\Documents\\Pictures\\text.txt", "txt");
        System.out.println(result);
        
        testDownload(result);
        
        
       
    }
    
    
    public static void testDownload(String fastDfsFileId) throws FileNotFoundException, IOException, MyException {
        //获取classpath路径下配置文件"fdfs_client.conf"的路径
        //conf直接写相对于classpath的位置，不需要写classpath:
        String configPath = FastDFSTest.class.getClassLoader().getResource("fast_client.conf").getFile();
        System.out.println(configPath);
        ClientGlobal.init(configPath);

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = trackerClient.getStoreStorage(trackerServer);
        storageClient1 = new StorageClient1(trackerServer, storageServer);
        
        String txtFileContent = new String(storageClient1.download_file1(fastDfsFileId),"GBK");
        System.out.println(txtFileContent);
    }
}
