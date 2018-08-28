package com.cdkj.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 */
public class IdGenerator {

    protected static Logger logger = LoggerFactory.getLogger(IdGenerator.class);
    //单例模式
    private volatile static IdGenerator self;
    // 时间戳基数, 此值越大, 生成的ID越小
    private final static long twepoch = 9999999999999L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static long lastTimestamp = -1L;

    //当前序列
    private long sequence = 0L;
    //当前机器ID
    private long workerId = 1;
    //当前数据中心ID
    private long datacenterId = 1;

    private IdGenerator() {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }
    }

    /**
     * 实例化方法
     */
    public static IdGenerator getInstance() {
        if (self == null) {
            synchronized (IdGenerator.class) {
                self = new IdGenerator();
            }
        }
        return self;
    }

    /**
     * 供其他业务静态使用
     */
    public static Long NextID() {
        return getInstance().nextid();
    }

    /**
     * 获取下一个序列值
     */
    private synchronized long nextid() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                logger.error("run error", e);
            }
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    /**
     * 获取下一个毫秒值
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(IdGenerator.NextID());
        }
    }
}
