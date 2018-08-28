package com.cdkj.fastdfs.pool;

import com.cdkj.fastdfs.FileManager;
import com.cdkj.util.PropUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Title: FastdfsPooledObjectFactory
 * @date: 2016年1月18日
 */
public class FastdfsPooledObjectFactory implements PooledObjectFactory<FastdfsClient> {

    public static final String CLIENT_CONFIG_FILE = "fdfs.properties";

    private final Integer objMaxActive;

    public FastdfsPooledObjectFactory(String confPath, Integer objMaxActive) throws FileNotFoundException, IOException, MyException {
        super();

        this.objMaxActive = objMaxActive;
        File pathFile = new File(FileManager.class.getResource("/").getFile());
        String classPath = pathFile.getCanonicalPath();
        String configFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
        ClientGlobal.init(configFilePath);
    }

    public FastdfsPooledObjectFactory(Integer objMaxActive) throws FileNotFoundException, IOException, MyException {
        super();

        this.objMaxActive = objMaxActive;
//		File pathFile = new File(FileManager.class.getResource("/").getFile());
//		String classPath = pathFile.getCanonicalPath();
//		String configFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
//		ClientGlobal.init(configFilePath);
        //直接传入参数进行初始化
        String[] trackerServers = PropUtils.getString("fdfs.tracker_server").split(",");
        ClientGlobal.init(PropUtils.getInt("fdfs.connect_timeout"), PropUtils.getInt("fdfs.network_timeout"), PropUtils.getString("fdfs.charset"), trackerServers, PropUtils.getInt("fdfs.http_tracker_http_port"),
                PropUtils.getBoolean("fdfs.http.anti_steal_token"), PropUtils.getString("fdfs.http_secret_key"));
    }

    @Override
    public PooledObject<FastdfsClient> makeObject() throws Exception {

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = tracker.getStoreStorage(trackerServer);
        FastdfsClient client = new FastdfsClient(trackerServer, storageServer, objMaxActive);
        return new DefaultPooledObject<FastdfsClient>(client);
    }

    @Override
    public void destroyObject(PooledObject<FastdfsClient> p) throws Exception {
        if (p == null) {
            return;
        }
        if (p.getObject() == null) {
            return;
        }
        FastdfsClient client = p.getObject();
        client.reset();

    }

    @Override
    public boolean validateObject(PooledObject<FastdfsClient> p) {
        try {
            return ProtoCommon.activeTest(p.getObject().getStorageServer().getSocket());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void activateObject(PooledObject<FastdfsClient> p) throws Exception {
        if (p == null) {
            return;
        }
        if (p.getObject() == null) {
            return;
        }
        FastdfsClient client = p.getObject();
        client.tryReset();
    }

    @Override
    public void passivateObject(PooledObject<FastdfsClient> p) throws Exception {
    }

}
