package com.hoomsun.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class JedisFactory {

    private JedisPoolConfig jedisPoolConfig;

    private JedisPool jedisPool;

    public JedisFactory(JedisPoolConfig jedisPoolConfig) {
        super();
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public Jedis getJedisInstance(String host) {
        return getJedisPool(host, Protocol.DEFAULT_PORT).getResource();
    }

    public Jedis getJedisInstance(String host, int port) {
        return getJedisPool(host, port).getResource();
    }

    public JedisPool getJedisPool(String host) {
        return getJedisPool(host, Protocol.DEFAULT_PORT);
    }

    public JedisPool getJedisPool(String host, int port) {
        if (jedisPool == null) {
            jedisPool = new JedisPool(jedisPoolConfig, host, port);
        }
        return jedisPool;
    }

    /**
     * 配合使用getJedisInstance方法后将jedis对象释放回连接池中
     * 
     * @param jedis
     *            使用完毕的Jedis对象
     * @return true 释放成功；否则返回false
     */
    public boolean release(Jedis jedis) {
        if (jedisPool != null && jedis != null) {
            jedisPool.returnResource(jedis);
            return true;
        }
        return false;
    }

}