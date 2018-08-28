package com.cdkj.fastdfs.pool;

import com.cdkj.util.PropUtils;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016-05-14.
 */
public class FastDfsConfig {

    protected static Logger logger = LoggerFactory.getLogger(FastDfsConfig.class);

    /**
     * 懒汉式单例类.在第一次调用的时候实例化自己
     */
    private FastDfsConfig() {
        init();
    }

    private static int fileUpPool = PropUtils.getInt("fdfs.fileUpPool");

    public static final String PROTOCOL = "http://";
    public static final String SEPARATOR = "/";
    public static final String TRACKER_NGNIX_PORT = "8080";

    public static TrackerClient trackerClient;
    public static TrackerServer trackerServer;
    public static StorageServer storageServer;
    public static StorageClient storageClient;

    public static FastdfsPool pool;

    public void init() {
        try {
            //连接池
            pool = new FastdfsPool(new FastdfsPoolConfig(), fileUpPool);
            logger.debug("FastDfsConfig init finished!~");
        } catch (Exception e) {
            logger.error("FastDfsConfig.init Exception:{}", e);
        }
    }

    private static FastDfsConfig config = null;

    /**
     * description: 静态工厂方法 <br>
     *
     * @param
     * @return com.cdkj.fastdfs.pool.FastDfsConfig
     */
    public static FastDfsConfig getInstance() {
        if (pool == null) {
            try {
                //连接池
                pool = new FastdfsPool(new FastdfsPoolConfig(), fileUpPool);
                logger.debug("FastDfsConfig getInstance Init finished!~");
            } catch (Exception e) {
                logger.error("FastDfsConfig.getInstance Exception:{}", e);
            }
        }
        if (config == null) {
            config = new FastDfsConfig();
        }
        return config;
    }

    public static FastdfsPool getPool(){
        return pool;
    }

    public static void returnResource(FastdfsClient fastdfsClient) {
        pool.returnResource(fastdfsClient);
    }

}