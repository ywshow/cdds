package com.cdkj.fastdfs;

import com.cdkj.fastdfs.model.pojo.FastDfsFile;
import com.cdkj.fastdfs.pool.FastDfsConfig;
import com.cdkj.fastdfs.pool.FastdfsClient;
import com.cdkj.fastdfs.pool.FastdfsPool;
import com.cdkj.util.PropUtils;
import com.cdkj.util.StringUtil;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Descript:文件控制器 <br/>
 * version 1.0
 */
public class FileManager {
    private static final long serialVersionUID = -6668105387763824260L;

    protected static Logger logger = LoggerFactory.getLogger(FileManager.class);

    public static String upload(FastDfsFile file) {
        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("width", file.getWidth());
        metaList[1] = new NameValuePair("height", file.getHeight());
        metaList[2] = new NameValuePair("author", file.getAuthor());
        metaList[3] = new NameValuePair("suffix", file.getExt());


        FastDfsConfig.getInstance();
        FastdfsPool pool = FastDfsConfig.getPool();
        FastdfsClient fastdfsClient = pool.getResource();
        String[] uploadResults;
        try {
            uploadResults = fastdfsClient.upload_file(file.getContent(), file.getExt(), metaList);
        } catch (Exception e) {
            logger.error("upload file fail, error:{}", e);
            return null;
        }
        if (uploadResults == null) {
            logger.debug("upload file fail, error code: " + fastdfsClient.getErrorCode());
            return null;
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        long startTime = System.currentTimeMillis();
        String hostName = fastdfsClient.getStorageServer().getInetSocketAddress().getHostName();
        long endTime = System.currentTimeMillis();
        logger.debug("file upload  get StorageServer host name time:{}", (endTime - startTime));
        String fileAbsolutePath;
        //替换域名信息
        String domain = PropUtils.getString("fdfs.domainUrl", "");
        logger.debug("file domain url:}", domain);
        if (StringUtil.isNotEmpty(domain)) {
            fileAbsolutePath = FastDfsConfig.PROTOCOL + domain
                    + FastDfsConfig.SEPARATOR
                    + groupName
                    + FastDfsConfig.SEPARATOR
                    + remoteFileName;
        } else {
            fileAbsolutePath = FastDfsConfig.PROTOCOL + hostName
                    + ":"
                    + FastDfsConfig.TRACKER_NGNIX_PORT
                    + FastDfsConfig.SEPARATOR
                    + groupName
                    + FastDfsConfig.SEPARATOR
                    + remoteFileName;
        }
        logger.debug("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:"
                + " " + remoteFileName);
        try {
            //M00/0A/1C/cjcqOFc-s6eAXWIcAAEOFgxyE2Q442.jpg//链接池回收 --重要动作，切勿注释
            FastDfsConfig.returnResource(fastdfsClient);
        } catch (Exception e) {
            logger.error("file return resource error:", e);
        }
        logger.debug("file absolute path:{}", fileAbsolutePath);
        return fileAbsolutePath;
    }

    /**
     * 指定groupName 上传
     *
     * @param file      文件内容
     * @param groupName 组名称
     * @return
     */
    public static String upload(FastDfsFile file, String groupName) {
        NameValuePair[] metaList = new NameValuePair[4];
        metaList[0] = new NameValuePair("width", file.getWidth());
        metaList[1] = new NameValuePair("height", file.getHeight());
        metaList[2] = new NameValuePair("author", file.getAuthor());
        metaList[3] = new NameValuePair("suffix", file.getExt());


        FastDfsConfig.getInstance();
        FastdfsPool pool = FastDfsConfig.getPool();
        FastdfsClient fastdfsClient = pool.getResource();
        String[] uploadResults ;
        try {
            uploadResults = fastdfsClient.upload_file(groupName, file.getContent(), file.getExt(), metaList);
        } catch (Exception e) {
            logger.error("upload file fail, error:{}", e);
            return null;
        }
        if (uploadResults == null) {
            logger.debug("upload file fail, error code: " + fastdfsClient.getErrorCode());
            return null;
        }
        String group = uploadResults[0];
        String remoteFileName = uploadResults[1];
        long startTime = System.currentTimeMillis();
        String hostName = fastdfsClient.getStorageServer().getInetSocketAddress().getHostName();
        long endTime = System.currentTimeMillis();
        logger.debug("file upload  get StorageServer host name time:{}", (endTime - startTime));
        String fileAbsolutePath;
        //替换域名信息
        String domain = PropUtils.getString("fdfs.domainUrl", "");
        logger.debug("file domain url:}", domain);
        if (StringUtil.isNotEmpty(domain)) {
            fileAbsolutePath = FastDfsConfig.PROTOCOL + domain
                    + FastDfsConfig.SEPARATOR
                    + groupName
                    + FastDfsConfig.SEPARATOR
                    + remoteFileName;
        } else {
            fileAbsolutePath = FastDfsConfig.PROTOCOL + hostName
                    + ":"
                    + FastDfsConfig.TRACKER_NGNIX_PORT
                    + FastDfsConfig.SEPARATOR
                    + groupName
                    + FastDfsConfig.SEPARATOR
                    + remoteFileName;
        }
        logger.debug("upload file successfully!!!  " + "group_name: " + group + ", remoteFileName:"
                + " " + remoteFileName);
        try {
            //链接池回收
            FastDfsConfig.returnResource(fastdfsClient);
        } catch (Exception e) {
            logger.error("file return resource error:", e);
        }
        logger.debug("file absolute path:{}", fileAbsolutePath);
        return fileAbsolutePath;
    }

    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            return FastDfsConfig.storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int deleteFile(String groupName, String remoteFileName) {
        FastDfsConfig.getInstance();
        FastdfsPool pool = FastDfsConfig.getPool();
        FastdfsClient fastdfsClient = pool.getResource();
        int rs = -1001;
        try {
            rs = fastdfsClient.delete_file(groupName, remoteFileName);
        } catch (IOException | MyException e) {
            logger.error("file error:", e);
        } finally {
            FastDfsConfig.returnResource(fastdfsClient);
        }
        return rs;
    }

    public static StorageServer[] getStoreStorages(String groupName) throws IOException {
        return FastDfsConfig.trackerClient.getStoreStorages(FastDfsConfig.trackerServer, groupName);
    }

    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) throws IOException {
        return FastDfsConfig.trackerClient.getFetchStorages(FastDfsConfig.trackerServer, groupName, remoteFileName);
    }
}
