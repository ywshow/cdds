/**
 * project name:cdds
 * file name:RedisClient
 * package name:com.cdkj.util
 * date:2018/7/18 0:05
 * author:ywshow
 * Copyright (c) CD Technology Co.,Ltd. All rights reserved.
 */
package com.cdkj.common.redis;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * description: redis工具类 <br>
 * date: 2018/7/18 0:05
 *
 * @author ywshow
 * @version 1.0
 * @since JDK 1.8
 */
public class RedisClient {

    private JedisClusterFactory jedisClusterFactory;
    protected Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public void setJedisClusterFactory(JedisClusterFactory jedisClusterFactory) {
        this.jedisClusterFactory = jedisClusterFactory;
    }

    public RedisClient() {
    }

    public long expire(String key, int seconds) {
        if (key != null && !key.equals("")) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.expire(key, seconds).longValue();
            } catch (Exception var5) {
                this.logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + var5.getMessage(), var5);
                this.logger.error("redisClient.expire.error:" + var5.getMessage() + "-----key=" + key + "-----seconds=" + seconds);
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public long expireAt(String key, int unixTimestamp) {
        if (key != null && !key.equals("")) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.expireAt(key, (long)unixTimestamp).longValue();
            } catch (Exception var5) {
                this.logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + unixTimestamp + "]" + var5.getMessage(), var5);
                this.logger.error("redisClient.expireAt.error:" + var5.getMessage() + "-----key=" + key + "-----unixTimestamp=" + unixTimestamp);
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public String trimList(String key, long start, long end) {
        if (key != null && !key.equals("")) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.ltrim(key, start, end);
            } catch (Exception var8) {
                this.logger.error("LTRIM 出错[key=" + key + " start=" + start + " end=" + end + "]" + var8.getMessage(), var8);
                return "-";
            }
        } else {
            return "-";
        }
    }

    public long countSet(String key) {
        if (key == null) {
            return 0L;
        } else {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.scard(key).longValue();
            } catch (Exception var4) {
                this.logger.error("countSet error.", var4);
                return 0L;
            }
        }
    }

    public boolean addSet(String key, int seconds, String... value) {
        boolean result = this.addSet(key, value);
        if (result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    public boolean addSet(String key, String... value) {
        if (key != null && value != null) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                jedisCluster.sadd(key, value);
                return true;
            } catch (Exception var5) {
                this.logger.error("setList error.", var5);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean containsInSet(String key, String value) {
        if (key != null && value != null) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.sismember(key, value).booleanValue();
            } catch (Exception var5) {
                this.logger.error("setList error.", var5);
                return false;
            }
        } else {
            return false;
        }
    }

    public Set<String> getSet(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.smembers(key);
        } catch (Exception var4) {
            this.logger.error("getList error.", var4);
            return null;
        }
    }

    public boolean removeSetValue(String key, String... value) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.srem(key, value);
            return true;
        } catch (Exception var5) {
            this.logger.error("getList error.", var5);
            return false;
        }
    }

    public int removeListValue(String key, List<String> values) {
        return this.removeListValue(key, 1L, values);
    }

    public int removeListValue(String key, long count, List<String> values) {
        int result = 0;
        if (values != null && values.size() > 0) {
            Iterator var6 = values.iterator();

            while(var6.hasNext()) {
                String value = (String)var6.next();
                if (this.removeListValue(key, count, value)) {
                    ++result;
                }
            }
        }

        return result;
    }

    public boolean removeListValue(String key, long count, String value) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.lrem(key, count, value);
            return true;
        } catch (Exception var7) {
            this.logger.error("getList error.", var7);
            return false;
        }
    }

    public List<String> rangeList(String key, long start, long end) {
        if (key != null && !key.equals("")) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.lrange(key, start, end);
            } catch (Exception var8) {
                this.logger.error("rangeList 出错[key=" + key + " start=" + start + " end=" + end + "]" + var8.getMessage(), var8);
                return null;
            }
        } else {
            return null;
        }
    }

    public long countList(String key) {
        if (key == null) {
            return 0L;
        } else {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                return jedisCluster.llen(key).longValue();
            } catch (Exception var4) {
                this.logger.error("countList error.", var4);
                return 0L;
            }
        }
    }

    public boolean addList(String key, int seconds, String... value) {
        boolean result = this.addList(key, value);
        if (result) {
            long i = this.expire(key, seconds);
            return i == 1L;
        } else {
            return false;
        }
    }

    public boolean addList(String key, String... value) {
        if (key != null && value != null) {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                jedisCluster.lpush(key, value);
                return true;
            } catch (Exception var5) {
                this.logger.error("setList error.", var5);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean addList(String key, List<String> list) {
        if (key != null && list != null && list.size() != 0) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                String value = (String)var3.next();
                this.addList(key, value);
            }

            return true;
        } else {
            return false;
        }
    }

    public List<String> getList(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.lrange(key, 0L, -1L);
        } catch (Exception var4) {
            this.logger.error("getList error.", var4);
            return null;
        }
    }

    public boolean setHSet(String domain, String key, String value) {
        if (value == null) {
            return false;
        } else {
            JedisCluster jedisCluster = null;

            try {
                jedisCluster = this.jedisClusterFactory.getObject();
                jedisCluster.hset(domain, key, value);
                return true;
            } catch (Exception var6) {
                this.logger.error("setHSet error.", var6);
                return false;
            }
        }
    }

    public String getHSet(String domain, String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.hget(domain, key);
        } catch (Exception var5) {
            this.logger.error("getHSet error.", var5);
            return null;
        }
    }

    public Map<String, String> getHSetAll(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.hgetAll(key);
        } catch (Exception var4) {
            this.logger.error("getHSet error.", var4);
            return null;
        }
    }

    public long delHSet(String domain, String key) {
        JedisCluster jedisCluster = null;
        long count = 0L;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            count = jedisCluster.hdel(domain, new String[]{key}).longValue();
        } catch (Exception var7) {
            this.logger.error("delHSet error.", var7);
        }

        return count;
    }

    public long delHSet(String domain, String... key) {
        JedisCluster jedisCluster = null;
        long count = 0L;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            count = jedisCluster.hdel(domain, key).longValue();
        } catch (Exception var7) {
            this.logger.error("delHSet error.", var7);
        }

        return count;
    }

    public boolean existsHSet(String domain, String key) {
        JedisCluster jedisCluster = null;
        boolean isExist = false;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            isExist = jedisCluster.hexists(domain, key).booleanValue();
        } catch (Exception var6) {
            this.logger.error("existsHSet error.", var6);
        }

        return isExist;
    }

    public List<String> hvals(String domain) {
        JedisCluster jedisCluster = null;
        List retList = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            retList = jedisCluster.hvals(domain);
        } catch (Exception var5) {
            this.logger.error("hvals error.", var5);
        }

        return retList;
    }

    public Set<String> hkeys(String domain) {
        JedisCluster jedisCluster = null;
        Set retList = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            retList = jedisCluster.hkeys(domain);
        } catch (Exception var5) {
            this.logger.error("hkeys error.", var5);
        }

        return retList;
    }

    public long lenHset(String domain) {
        JedisCluster jedisCluster = null;
        long retList = 0L;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            retList = jedisCluster.hlen(domain).longValue();
        } catch (Exception var6) {
            this.logger.error("hkeys error.", var6);
        }

        return retList;
    }

    public boolean setSortedSet(String key, long score, String value) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.zadd(key, (double)score, value);
            return true;
        } catch (Exception var7) {
            this.logger.error("setSortedSet error.", var7);
            return false;
        }
    }

    public Set<String> getSoredSet(String key, long startScore, long endScore, boolean orderByDesc) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return orderByDesc ? jedisCluster.zrevrangeByScore(key, (double)endScore, (double)startScore) : jedisCluster.zrangeByScore(key, (double)startScore, (double)endScore);
        } catch (Exception var9) {
            this.logger.error("getSoredSet error.", var9);
            return null;
        }
    }

    public long countSoredSet(String key, long startScore, long endScore) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            Long count = jedisCluster.zcount(key, (double)startScore, (double)endScore);
            return count == null ? 0L : count.longValue();
        } catch (Exception var8) {
            this.logger.error("countSoredSet error.", var8);
            return 0L;
        }
    }

    public boolean delSortedSet(String key, String value) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            long count = jedisCluster.zrem(key, new String[]{value}).longValue();
            return count > 0L;
        } catch (Exception var6) {
            this.logger.error("delSortedSet error.", var6);
            return false;
        }
    }

    public Set<String> getSoredSetByRange(String key, int startRange, int endRange, boolean orderByDesc) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return orderByDesc ? jedisCluster.zrevrange(key, (long)startRange, (long)endRange) : jedisCluster.zrange(key, (long)startRange, (long)endRange);
        } catch (Exception var7) {
            this.logger.error("getSoredSetByRange error.", var7);
            return null;
        }
    }

    public Double getScore(String key, String member) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.zscore(key, member);
        } catch (Exception var5) {
            this.logger.error("getSoredSet error.", var5);
            return null;
        }
    }

    public boolean set(String key, String value, int second) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.setex(key, second, value);
            return true;
        } catch (Exception var6) {
            this.logger.error("set error.", var6);
            this.logger.error("redisClient.set.error:" + var6.getMessage() + "-----key=" + key + "-----value=" + value + "-----second=" + second);
            return false;
        }
    }

    public boolean set(String key, String value) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.set(key, value);
            return true;
        } catch (Exception var5) {
            this.logger.error("set error.", var5);
            this.logger.error("redisClient.set.error:" + var5.getMessage() + "-----key=" + key + "-----value=" + value);
            return false;
        }
    }

    public String get(String key, String defaultValue) {
        String result = this.get(key);
        return result == null ? defaultValue : result;
    }

    public String get(String key) {
        this.logger.debug("RedisClient.key={}", key);
        long startTime = System.currentTimeMillis();
        String value = null;
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            value = jedisCluster.get(key);
        } catch (Exception var8) {
            this.logger.error("RedisClient.get error.", var8);
            this.logger.error("redisClient.get.error:" + var8.getMessage() + "-----key=" + key);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("RedisClient.value={}", value);
            long endTime = System.currentTimeMillis();
            this.logger.debug("RedisClient.get.time={}", endTime - startTime);
        }

        return value;
    }

    public boolean del(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            jedisCluster.del(key);
            return true;
        } catch (Exception var4) {
            this.logger.error("del error.", var4);
            this.logger.error("redisClient.del.error:" + var4.getMessage() + "-----key=" + key);
            return false;
        }
    }

    public long incr(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.incr(key).longValue();
        } catch (Exception var4) {
            this.logger.error("incr error.", var4);
            this.logger.error("redisClient.incr.error:" + var4.getMessage() + "-----key=" + key);
            return 0L;
        }
    }

    public long decr(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.decr(key).longValue();
        } catch (Exception var4) {
            this.logger.error("incr error.", var4);
            return 0L;
        }
    }

    public boolean object2Redis(T model, String key) {
        boolean flag = true;
        Field[] field = model.getClass().getDeclaredFields();
        String keyValue = "";

        String name;
        try {
            Field keyField = model.getClass().getDeclaredField(key);
            name = keyField.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            keyValue = this.fieldValue(model, keyField.getGenericType().toString(), name);
        } catch (NoSuchFieldException var9) {
            this.logger.error("run error", var9);
        }

        for(int j = 0; j < field.length; ++j) {
            name = field[j].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String filedValue = this.fieldValue(model, field[j].getGenericType().toString(), name);
            if (null != filedValue && !"".equals(filedValue) && !filedValue.equals("field-error")) {
                this.setHSet(keyValue, name, filedValue);
            }

            if (null != filedValue && !"".equals(filedValue) && filedValue.equals("field-error")) {
                flag = false;
            } else if (flag) {
                flag = true;
            }
        }

        return flag;
    }

    private String fieldValue(T model, String type, String name) {
        String res = "";

        try {
            Method m;
            if (type.equals("class java.lang.String")) {
                m = model.getClass().getMethod("get" + name);
                res = (String)m.invoke(model);
            }

            if (type.equals("class java.lang.Integer")) {
                m = model.getClass().getMethod("get" + name);
                res = String.valueOf((Integer)m.invoke(model));
            }

            if (type.equals("class java.lang.Boolean")) {
                m = model.getClass().getMethod("get" + name);
                res = String.valueOf((Boolean)m.invoke(model));
            }

            if (type.equals("class java.util.Date")) {
                m = model.getClass().getMethod("get" + name);
                res = String.valueOf((Date)m.invoke(model));
            }

            if (type.equals("class java.math.BigDecimal")) {
                m = model.getClass().getMethod("get" + name);
                res = String.valueOf((BigDecimal)m.invoke(model));
            }
        } catch (NoSuchMethodException var6) {
            res = "field-error";
            this.logger.error("RedisSetError:" + model.getClass() + "-NoSuchMethodException", var6);
        } catch (SecurityException var7) {
            res = "field-error";
            this.logger.error("RedisSetError:" + model.getClass() + "-SecurityException", var7);
        } catch (IllegalAccessException var8) {
            res = "field-error";
            this.logger.error("RedisSetError:" + model.getClass() + "-IllegalAccessException", var8);
        } catch (IllegalArgumentException var9) {
            res = "field-error";
            this.logger.error("RedisSetError:" + model.getClass() + "-IllegalArgumentException", var9);
        } catch (InvocationTargetException var10) {
            res = "field-error";
            this.logger.error("RedisSetError:" + model.getClass() + "-InvocationTargetException", var10);
        }

        return res;
    }

    public boolean delRedisObject(T model, String key) {
        boolean flag = true;
        Field[] field = model.getClass().getDeclaredFields();
        String keyValue = "";

        String name;
        try {
            Field keyField = model.getClass().getDeclaredField(key);
            name = keyField.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            keyValue = this.fieldValue(model, keyField.getGenericType().toString(), name);
        } catch (NoSuchFieldException var9) {
            this.logger.error("run error", var9);
        }

        for(int j = 0; j < field.length; ++j) {
            name = field[j].getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String filedValue = this.fieldValue(model, field[j].getGenericType().toString(), name);
            if (null != filedValue && !"".equals(filedValue) && !filedValue.equals("field-error")) {
                this.delHSet(keyValue, name);
            }

            if (null != filedValue && !"".equals(filedValue) && filedValue.equals("field-error")) {
                flag = false;
            } else if (flag) {
                flag = true;
            }
        }

        return flag;
    }

    public boolean tryLock(String key) {
        return this.tryLock(key, 0L, (TimeUnit)null);
    }

    public boolean tryLock(String key, long timeout, TimeUnit unit) {
        JedisCluster jedis = null;

        try {
            jedis = this.jedisClusterFactory.getObject();
            long nano = System.nanoTime();

            while(true) {
                this.logger.debug("try lock key: " + key);
                Long i = jedis.setnx(key, key);
                if (i.longValue() == 1L) {
                    jedis.expire(key, 3);
                    this.logger.debug("get lock, key: " + key + " , expire in " + 3 + " seconds.");
                    return Boolean.TRUE.booleanValue();
                }

                if (this.logger.isDebugEnabled()) {
                    String desc = jedis.get(key);
                    this.logger.debug("key: " + key + " locked by another business：" + desc);
                }

                if (timeout != 0L) {
                    Thread.sleep(300L);
                    if (System.nanoTime() - nano < unit.toNanos(timeout)) {
                        continue;
                    }
                }

                return Boolean.FALSE.booleanValue();
            }
        } catch (JedisConnectionException var10) {
            this.logger.error(var10.getMessage(), var10);
            this.logger.error("redisClient.tryLock.JedisConnectionException.error:" + var10.getMessage() + "-----key=" + key + "-----timeout=" + timeout + "-----unit=" + unit);
        } catch (Exception var11) {
            this.logger.error(var11.getMessage(), var11);
            this.logger.error("redisClient.tryLock.error:" + var11.getMessage() + "-----key=" + key + "-----timeout=" + timeout + "-----unit=" + unit);
        }

        return Boolean.FALSE.booleanValue();
    }

    public void lock(String key) {
        long startTime = (new Date()).getTime();
        JedisCluster jedis = null;

        try {
            jedis = this.jedisClusterFactory.getObject();

            while(true) {
                this.logger.debug("lock key: " + key);
                Long i = jedis.setnx(key, key);
                if (i.longValue() == 1L) {
                    jedis.expire(key, 3);
                    this.logger.debug("get lock, key: " + key + " , expire in " + 3 + " seconds.");
                    long endTime = (new Date()).getTime();
                    this.logger.debug("llock.time={}", endTime - startTime);
                    return;
                }

                if (this.logger.isDebugEnabled()) {
                    String desc = jedis.get(key);
                    this.logger.debug("key: " + key + " locked by another business：" + desc);
                }

                Thread.sleep(100L);
            }
        } catch (JedisConnectionException var8) {
            this.logger.error(var8.getMessage(), var8);
        } catch (Exception var9) {
            this.logger.error(var9.getMessage(), var9);
        }

    }

    public void unLock(String key) {
        long startTime = (new Date()).getTime();
        List<String> list = new ArrayList();
        list.add(key);
        this.unLock((List)list);
        long endTime = (new Date()).getTime();
        this.logger.debug("unLock.time={}", endTime - startTime);
    }

    public void unLock(List<String> keys) {
        JedisCluster jedis = null;

        try {
            jedis = this.jedisClusterFactory.getObject();
            Iterator var3 = keys.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                jedis.del(key);
            }

            this.logger.debug("release lock, keys :" + keys);
        } catch (JedisConnectionException var5) {
            this.logger.error(var5.getMessage(), var5);
            this.logger.error("redisClient.unLock.JedisConnectionException:" + var5.getMessage() + "-----keys=" + keys);
        } catch (Exception var6) {
            this.logger.error(var6.getMessage(), var6);
            this.logger.error("redisClient.unLock.error:" + var6.getMessage() + "-----keys=" + keys);
        }

    }

    public String lpop(String key) {
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            return jedisCluster.lpop(key);
        } catch (Exception var4) {
            this.logger.error("lpop error.", var4);
            return null;
        }
    }

    public long decrBy(String key, long number) {
        long len = -9999L;
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            len = jedisCluster.decrBy(key, number).longValue();
        } catch (Exception var8) {
            this.logger.error("redisClient.decrBy.error:" + var8.getMessage() + "-----key=" + key);
        }

        return len;
    }

    public long incrBy(String key, long number) {
        long len = -9999L;
        JedisCluster jedisCluster = null;

        try {
            jedisCluster = this.jedisClusterFactory.getObject();
            len = jedisCluster.incrBy(key, number).longValue();
        } catch (Exception var8) {
            this.logger.error("redisClient.incrBy.error:" + var8.getMessage() + "-----key=" + key);
        }

        return len;
    }
}