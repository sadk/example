package org.lsqt.rst.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ListBucketsRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class AliyunOssUtils {
	private static final Logger log = LoggerFactory.getLogger(AliyunOssUtils.class);
	
    private OSS ossClient;
    private String bucketName;
    private String endPonit;
    private String protocol = "https";

    public AliyunOssUtils(String endPoint, String accessKeyId, String accessKeySecret) {
        this.ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }

    public AliyunOssUtils withBucket(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public AliyunOssUtils withProtocol(String protocol) {
        this.protocol = StringUtils.hasText(protocol) ? protocol : "https";
        return this;
    }

	public void createBucketIfExists(String bucketName) {
		if (!ossClient.doesBucketExist(bucketName)) {
			ossClient.createBucket(bucketName);
		}
	}
    
    /**
     * 列出所有bucket
     */
    public List<Bucket> listAllBuckets() {
        return ossClient.listBuckets();
    }

    /**
     * 判断指定bucket是否存在
     */
    public boolean doesBucketExist(String bucketName) {
        return ossClient.doesBucketExist(bucketName);
    }

    /**
     * 查询指定bucket
     *
     * @param bucketName
     */
    public List<Bucket> queryBuckets(String bucketName) {
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setPrefix(bucketName);

        BucketList buckets = ossClient.listBuckets(listBucketsRequest);
        return buckets.getBucketList();
    }


    /**
     * 列出该目录下所有文件、目录（包含子目录内的文件）
     *
     * @param folderName
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<OSSObjectSummary> listAllFiles(final String folderName) {
        String folderNameTemp = processFolderSuffix(folderName);

        ObjectListing objectListing = ossClient.listObjects(this.bucketName, folderNameTemp);
        if (null == objectListing) {
            return Collections.EMPTY_LIST;
        }
        return objectListing.getObjectSummaries();
    }

    /**
     * 列出该目录下的文件、目录（不含子目录内的文件）
     *
     * @param folderName
     * @return
     */
    public ObjectListing listFiles(final String folderName) {
        String folderNameTemp = processFolderSuffix(folderName);

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(this.bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(folderNameTemp);
        return ossClient.listObjects(listObjectsRequest);
    }

    /**
     * 判断文件是否存在
     * 坑：当为文件夹时，只能判断最后一级目录是否存在，
     * e.g: 对于hcs-admin/portal/module/，无法判断hcs-admin/portal/是否存在。
     *
     * @param objectName
     * @return
     */
    public boolean doesObjectExist(final String objectName) {
        return ossClient.doesObjectExist(this.bucketName, objectName);
    }

    /**
     * 创建目录
     *
     * @param folderName
     */
    public void mkdir(final String folderName) {
        String folderNameTemp = processFolderSuffix(folderName);

        log.info("start to create folder [ {} ]", folderNameTemp);

        ossClient.putObject(this.bucketName, folderNameTemp, new ByteArrayInputStream(new byte[0]));
    }

    /**
     * 创建目录
     *
     * @param folderStr
     */
    public void mkdirs(final String folderStr) {
        String folderStrTemp = processFolderSuffix(folderStr);
        String[] folderArr = folderStrTemp.split("/");
        String folderStrToCreate = "";

        log.info("start to create folders [ {} ]", folderStrTemp);

        for (String folderItemStr : folderArr) {
            folderStrToCreate = folderStrToCreate + folderItemStr + "/";
            if (!doesObjectExist(folderStrToCreate)) {
                mkdir(folderStrToCreate);
            }
        }
    }

    /**
     * 删除目录
     *
     * @param folderName
     * @param includeSubFiles 是否删除目录内的子文件
     */
    public void deleteFolder(final String folderName, boolean includeSubFiles) {
        String folderStrTemp = processFolderSuffix(folderName);

        if (includeSubFiles) {
            List<OSSObjectSummary> sums = listAllFiles(folderStrTemp);
            List<String> keysToDelete = new ArrayList<>(sums.size());

            for (OSSObjectSummary summary : sums) {
                keysToDelete.add(summary.getKey());
            }

            if (keysToDelete.size() > 0) {
                deleteObjects(keysToDelete);
            }
        } else {
            deleteObject(folderName);
        }
    }

    /**
     * 删除指定对象
     *
     * @param objectName
     */
    public void deleteObject(String objectName) {
        if (doesObjectExist(objectName)) {
            log.info("start to delete object [ {} ]", objectName);
            ossClient.deleteObject(this.bucketName, objectName);
        }
    }

    /**
     * 批量删除对象
     *
     * @param keysToDelete
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<String> deleteObjects(List<String> keysToDelete) {
        log.info("start to delete objects [ {} ]", JSON.toJSONString(keysToDelete));

        if (CollectionUtils.isEmpty(keysToDelete)) {
            return Collections.EMPTY_LIST;
        }

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(
                new DeleteObjectsRequest(this.bucketName).withQuiet(true).withKeys(keysToDelete));
        return deleteObjectsResult.getDeletedObjects();
    }

    /**
     * 获取指定对象的http get url
     *
     * @param objectName
     * @return
     */
    public String getFileUrlWithExpire(String objectName) {
        if (objectName.endsWith("/")) {
            throw new RuntimeException("非法objectName.");
        }

        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        if (doesObjectExist(objectName)) {
            URL url = ossClient.generatePresignedUrl(this.bucketName, objectName, expiration);
            if (null != url) {
                String httpUrl = url.toString();
                log.info("get http url of oss's object [expire], objectName: {}, http url: {}", objectName, httpUrl);
                return httpUrl;
            }
        }

        return "";
    }

    /**
     * 获取文件http URL
     * @param objectName
     * @return
     */
    public String getFileUrl(String objectName) {
        if (objectName.endsWith("/")) {
            throw new RuntimeException("非法objectName.");
        }

        if (doesObjectExist(objectName)) {
            try {
                objectName = processFolderPrefix(objectName);
                URL url = new URL(this.protocol, getAliyunOssHost(), objectName);
                String httpUrl = url.toString();

                log.info("get http url of oss's object, objectName: {}, http url: {}", objectName, httpUrl);
                return httpUrl;
            } catch (MalformedURLException e) {
                return "";
            }
        }

        return "";
    }

    /**
     * 上传文件流
     *
     * @param objectName
     * @param inputStream
     */
    public String uploadInputStream(String objectName, InputStream inputStream) throws UnsupportedEncodingException {
        if (!StringUtils.hasText(objectName) || objectName.endsWith("/")) {
            throw new RuntimeException("本地文件上传失败，objectName无法解析!");
        }

        log.info("start to upload the inputstream, with objectName [ {} ]", objectName);

        // create folders
        int lastIndex = objectName.lastIndexOf("/");
        if (lastIndex > 0) {
            mkdirs(objectName.substring(0, lastIndex));
        }

        ossClient.putObject(this.bucketName, objectName, inputStream);

        return objectName;
    }

    /**
     * 上传本地文件
     *
     * @param objectName
     * @param filePath
     */
    public void uploadFile(String objectName, String filePath) {
        if (!StringUtils.hasText(objectName) || objectName.endsWith("/")) {
            throw new RuntimeException("本地文件上传失败，objectName无法解析!");
        }

        File fileToUpload = new File(filePath);
        if (!fileToUpload.exists() || !fileToUpload.isFile()) {
            throw new RuntimeException("本地文件上传失败，指定文件不存在或无法解析！");
        }

        log.info("start to upload the file [ {} ], with objectName [ {} ]", filePath, objectName);

        // create folders
        int lastIndex = objectName.lastIndexOf("/");
        if (lastIndex > 0) {
            mkdirs(objectName.substring(0, lastIndex));
        }

        ossClient.putObject(this.bucketName, objectName, fileToUpload);
    }

    private String processFolderSuffix(final String folderName) {
        String folderStr = folderName;
        if (null != folderName && !folderStr.endsWith("/")) {
            folderStr = folderStr + "/";
        }
        return folderStr;
    }

    private String processFolderPrefix(final String folderName) {
        String folderStr = folderName;
        if (null != folderName && !folderStr.startsWith("/")) {
            folderStr = "/" + folderStr;
        }
        return folderStr;
    }

    private String getAliyunOssHost() {
        return this.bucketName + "." + endPonit;
    }

    
    public void shutdown() {
    	ossClient.shutdown();
    }
}
