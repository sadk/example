package org.lsqt.rst.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.lsqt.components.context.annotation.Component;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.OnStarted;
import org.lsqt.components.db.Db;
import org.lsqt.rst.util.AliyunOssUtils;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Machine;
import org.lsqt.sys.model.MachineQuery;
import org.lsqt.sys.model.Property;
import org.lsqt.sys.model.PropertyQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阿里云上传配置
 * @author mm
 *
 */
@Component
public class UploadConfigerOSS {
	private static final Logger log = LoggerFactory.getLogger(UploadConfigerOSS.class);
	
	@Inject private Db db;
	
	public static String aliUrl = "https://rst-sit-oss.oss-cn-shenzhen.aliyuncs.com/"; // 不知道这个地址是干什么的
	public static String endPoint = "https://oss-cn-shenzhen-internal.aliyuncs.com";// endpoint 访问OSS的域名
	public static String accessKeyId = "LTAIcuauDFcgjLjf"; // accessKeyId和accessKeySecret OSS的访问密钥
	public static String accessKeySecret = "uIAFNo4rDl3iP8Bt8HuU96aRADIXST";
	public static String bucketName = "rst-sit-oss";  // Bucket 用来管理所存储Object的存储空间
    
    @OnStarted(order=20)
    public void initOSSConfig() throws Exception {
    	db.executePlan(false, ()->{
    		MachineQuery query = new MachineQuery();
    		query.setCode("AliYun_OSS_FILE");
    		Machine model = db.queryForObject("queryForPage", Machine.class, query);
    		if (model == null) {
    			log.warn("机器: AliYun_OSS_FILE 没有配置");
    			return ;
    		}
    		
    		if(model.getStatus() ==null) {
    			log.warn("机器: AliYun_OSS_FILE 启用状态没有设置");
    			return ;
    		}
    		
    		if (Dictionary.ENABLE_启用 != model.getStatus()) {
    			log.warn("机器: AliYun_OSS_FILE 状态没有启用");
    			return ;
    		}
    		
    		PropertyQuery pq = new PropertyQuery();
    		pq.setParentCode(model.getCode());
    		List<Property> list = db.queryForList("queryForPage", Property.class, pq);
    		for (Property p: list) {
    			if("aliUrl".equals(p.getName())) {
    				aliUrl = p.getValue();
    			}
    			if("endPoint".equals(p.getName())) {
    				endPoint = p.getValue();
    			}
    			if("accessKeyId".equals(p.getName())) {
    				accessKeyId = p.getValue();
    			}
    			if("accessKeySecret".equals(p.getName())) {
    				accessKeySecret = p.getValue();
    			}
    			if("bucketName".equals(p.getName())) {
    				bucketName = p.getValue();
    			}
    		}
    		
    		log.info("######################################################################################");
    		log.info("#    阿里云文件上传服务器配置");
    		log.info("#    aliUrl:\t {}",aliUrl);
    		log.info("#    endPoint:\t {}",endPoint);
    		log.info("#    accessKeyId:\t {}",accessKeyId);
    		log.info("#    accessKeySecret:{}",accessKeySecret);
    		log.info("#    bucketName:\t {}",bucketName);
    		log.info("######################################################################################");
    	});
    }
    
    

	/**
	 * 同步上传到阿里云OSS
	 * @param objectName  http路径前段名称标识
	 * @param fileFullPath 附件的全种径
	 * @return
	 */
	public static String uploadAliyunOSS(String objectName, String fileFullPath) {
		try (AliyunOssUtils util = new AliyunOssUtils(UploadConfigerOSS.endPoint, UploadConfigerOSS.accessKeyId,
				UploadConfigerOSS.accessKeySecret)) {
			
			util.withBucket(UploadConfigerOSS.bucketName);
			util.createBucketIfExists(UploadConfigerOSS.bucketName);

			StringBuilder result = new StringBuilder(UploadConfigerOSS.aliUrl);
			result.append(util.uploadInputStream(objectName, new FileInputStream(new File(fileFullPath))));
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

