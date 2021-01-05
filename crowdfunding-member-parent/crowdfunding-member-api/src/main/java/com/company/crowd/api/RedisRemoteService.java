package com.company.crowd.api;

import com.company.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author: yaochunguang
 * @date: 2021-01-05 19:18
 * @description: Redis远程服务
 **/
@FeignClient("crowd-redis")
public interface RedisRemoteService {

    /**
     * 设置redis键值
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value);

    /**
     * 设置redis键值，带超时时间
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(@RequestParam("key") String key, @RequestParam("value") String value,
                                                           @RequestParam("time") long time, @RequestParam("timeUnit") TimeUnit timeUnit);

    /**
     * 通过键来获取redis的值
     *
     * @param key
     * @return
     */
    @RequestMapping("/get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);

    /**
     * 通过键来删除redis的值
     *
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);

}
