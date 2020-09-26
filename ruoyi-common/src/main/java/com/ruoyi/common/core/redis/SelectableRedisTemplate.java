package com.ruoyi.common.core.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *redis切换连接
 *
 * @author dyh
 **/

public class SelectableRedisTemplate extends RedisTemplate
{
    @Override
    protected RedisConnection createRedisConnectionProxy(RedisConnection pm) {
        return super.createRedisConnectionProxy(pm);
    }

    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        Integer db;
        if((db = RedisSelectSupport.getSelect()) != null){
            connection.select(db);
        }
        return super.preProcessConnection(connection, existingConnection);
    }
}
