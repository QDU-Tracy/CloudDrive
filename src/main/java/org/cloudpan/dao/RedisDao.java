package org.cloudpan.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisDao {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }
    public String Test(){
        String a="";
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.sadd("user", "1");
                jedis.sadd("pic", "www.baidu.com","www.google.com");

            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return a;
    }
}
