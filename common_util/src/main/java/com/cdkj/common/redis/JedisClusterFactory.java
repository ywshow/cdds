/**
 * project name:distribution
 * file name:JedisClusterFactory
 * package name:com.cdkj.redis
 * date:2016/7/13 11:15
 * author:bovine
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.common.redis;

import com.cdkj.util.PropUtils;
import com.cdkj.util.StringUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * description: redis集群客户端工厂类 <br>
 * date: 2016/7/13 11:15
 *
 * @author bovine
 * @version 1.0
 * @since JDK 1.8
 */
public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Resource addressConfig;
    private String hostPrefix;
    private String portPrefix;
    private String passPrefix;
    private String password;

    private JedisCluster jedisCluster;
    private Integer timeout;
    private Integer maxRedirections;
    private GenericObjectPoolConfig genericObjectPoolConfig;

    private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

    @Override
    public JedisCluster getObject() throws Exception {
        return jedisCluster;
    }

    @Override
    public Class<? extends JedisCluster> getObjectType() {
        return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Set<HostAndPort> parseHostAndPort() throws Exception {
        try {
            //为了兼容部分项目不通过PropUtils进行装载，读取本地文件调整

            Properties redisProp = null;
            try {
                redisProp = PropertiesLoaderUtils.loadAllProperties("conf/redis.properties");
            } catch (Exception e) {
                logger.error("JedisClusterFactory.parseHostAndPort.error:{}", e);
            }
            Properties prop = new Properties();
            prop.load(this.addressConfig.getInputStream());
            Set<HostAndPort> haps = new HashSet<HostAndPort>();
            for (Object key : prop.keySet()) {

                if (!((String) key).startsWith(hostPrefix)) {
                    continue;
                }
                //获取key 的最后一个数字
                String index = "0";
                index = key.toString().substring(key.toString().length() - 1, key.toString().length());
                String host = PropUtils.getString((String) key);
                String prot = PropUtils.getString(portPrefix + index);
                password = PropUtils.getString(passPrefix + index);
                if (StringUtil.isEmpty(host)) {
                    host = redisProp.getProperty((String) key, "");
                    prot = redisProp.getProperty(portPrefix + index);
                    password = redisProp.getProperty(passPrefix + index);
                }
                if ("".equals(password)) {
                    password = null;
                }
                int ipAndPort = Integer.valueOf(prot);
                HostAndPort hap = new HostAndPort(host, ipAndPort);
                haps.add(hap);
            }
            return haps;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new Exception("解析 jedis 配置文件失败", ex);
        }
    }

    /**
     * description: 初始化数据，解析ip:端口 <br>
     *
     * @param
     * @return java.util.Set<redis.clients.jedis.HostAndPort>
     */
    private Set<HostAndPort> initHostAndPort() throws Exception {
        Properties prop = new Properties();
        prop.load(this.addressConfig.getInputStream());
        Set<HostAndPort> hapSet = new HashSet<>();
        if (StringUtil.isNotEmpty(hostPrefix)) {
            String[] hosts = hostPrefix.split(",");
            for (String host : hosts) {
                String[] ipAndPortArray = host.split(":");
                String ip = ipAndPortArray[0];
                String port = ipAndPortArray[1];
                int ipAndPort = Integer.valueOf(port);
                HostAndPort hap = new HostAndPort(ip, ipAndPort);
                hapSet.add(hap);
            }
            if (StringUtil.isEmpty(password)) {
                password = null;
            }
        }
        return hapSet;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        Set<HostAndPort> haps = this.parseHostAndPort();
        Set<HostAndPort> haps = this.initHostAndPort();
        jedisCluster = new JedisCluster(haps, timeout, 1000, maxRedirections, password, genericObjectPoolConfig);
    }

    public void setAddressConfig(Resource addressConfig) {
        this.addressConfig = addressConfig;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public void setHostPrefix(String hostPrefix) {
        this.hostPrefix = hostPrefix;
    }

    public void setPortPrefix(String portPrefix) {
        this.portPrefix = portPrefix;
    }

    public void setPassPrefix(String passPrefix) {
        this.passPrefix = passPrefix;
    }

    public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
        this.genericObjectPoolConfig = genericObjectPoolConfig;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}