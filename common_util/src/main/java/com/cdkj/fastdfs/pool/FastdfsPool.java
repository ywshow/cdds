package com.cdkj.fastdfs.pool;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;

/**
 * @description: 文件服务器连接池
 */
public class FastdfsPool extends Pool<FastdfsClient> {

    public FastdfsPool(GenericObjectPoolConfig poolConfig, String confPath, Integer objMaxActive) throws FileNotFoundException, IOException, MyException {
        super(poolConfig, new FastdfsPooledObjectFactory(confPath, objMaxActive));

    }

    public FastdfsPool(GenericObjectPoolConfig poolConfig, Integer objMaxActive) throws IOException, MyException {
        super(poolConfig, new FastdfsPooledObjectFactory(objMaxActive));

    }

    @Override
    public void returnBrokenResource(final FastdfsClient resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    @Override
    public void returnResource(final FastdfsClient resource) {
        if (resource != null) {
            returnResourceObject(resource);
        }
    }

}
