package com.cdkj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 系统启动时，会加载一次所有的配置文件，并将配置文件里面的配置属性放到这个类的静态Properties里面
 * Created by liuhaiyin on 2016/6/28.
 */
public class PropUtils {

    protected static Logger logger = LoggerFactory.getLogger(PropUtils.class);
    private static Properties properties = new Properties();

    /**
     * 系统启动时DataSourceUtil类会调用这个方法初始化properties
     *
     * @param key
     * @param value
     */
    public static void addProperties(String key, String value) {
        properties.setProperty(key, value);
    }
    /**
     * 系统启动时DataSourceUtil类会调用这个方法初始化properties
     * @param properties
     */
    public static void addProperties(Properties properties) {
        properties.putAll(properties);
    }

    /*static {
        try {
            if (properties == null) {
                properties = new Properties();
                Properties system = PropertiesLoaderUtils.loadAllProperties("conf/system.properties");
                Properties weChat = PropertiesLoaderUtils.loadAllProperties("conf/wechat.properties");
                Properties mail = PropertiesLoaderUtils.loadAllProperties("conf/mail.properties");
                Properties fdfs = PropertiesLoaderUtils.loadAllProperties("conf/fdfs.properties");
                properties.putAll(system);
                properties.putAll(weChat);
                properties.putAll(mail);
                properties.putAll(fdfs);
                logger.debug("-------------------init properties------------------");
            }
        } catch (IOException e) {
            logger.error("PropUtils.run error", e);
        }
    }*/

    public static String getString(String str) {
        return properties.getProperty(str);
    }

    public static String getString(String str, String defStr) {
        return properties.getProperty(str, defStr);
    }

    public static int getInt(String str) {
        String prop = properties.getProperty(str);
        if (StringUtil.isNotEmpty(prop)) {
            return Integer.parseInt(prop);
        }
        return 0;
    }
    public static int getInt(String str, int defaultInt) {
        String prop = properties.getProperty(str);
        if (StringUtil.isNotEmpty(prop)) {
            return Integer.parseInt(prop);
        }
        return defaultInt;
    }
    public static boolean getBoolean(String str) {
        String prop = properties.getProperty(str);
        if ("yes".equalsIgnoreCase(prop)) {
            return true;
        }
        return false;
    }


}
