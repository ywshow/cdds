package com.cdkj.fastdfs.pool;

import com.cdkj.util.PropUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class FastdfsPoolConfig extends GenericObjectPoolConfig {

    public FastdfsPoolConfig() {
        super();
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
        setMaxTotal(PropUtils.getInt("fdfs.maxTotal"));
        setMaxIdle(PropUtils.getInt("fdfs.maxIdle"));
    }

}
