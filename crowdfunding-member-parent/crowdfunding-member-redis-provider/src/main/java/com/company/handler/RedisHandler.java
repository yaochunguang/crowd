package com.company.handler;

import com.company.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: yaochunguang
 * @date: 2021-01-05 19:26
 * @description: redis的handler
 **/
@RestController
public class RedisHandler {

    private Logger logger = LoggerFactory.getLogger(RedisHandler.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置redis键值
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 设置redis键值，带超时时间
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(@RequestParam("key") String key, @RequestParam("value") String value,
                                                           @RequestParam("time") long time, @RequestParam("timeUnit") TimeUnit timeUnit){
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value, time, timeUnit);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 通过键来获取redis的值
     *
     * @param key
     * @return
     */
    @RequestMapping("/get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key){
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String value = operations.get(key);
            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 通过键来删除redis的值
     *
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key){
        try {
            redisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

}
