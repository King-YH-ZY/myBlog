package com.ruoyi.yh.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.yh.domain.YhIndexPic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 轮播图redis 工具类
 */
public class IndexPicRedisUtils {


    /**
     * 清空缓存
     */
    public static void clearCache()
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey());
    }

    /**
     * 设置cache key
     * @return
     */
    public static String getCacheKey()
    {
        return Constants.Index_Pic_KEY;
    }


    /**
     * 设置轮播图数据缓存
     * @param a
     */
    public static void setIndexPicCache(List<YhIndexPic> a)
    {
        SpringUtils.getBean(RedisCache.class).setCacheList(getCacheKey(), a);
    }


    /**
     * 获取轮播图数据缓存
     * @return
     */
    public static List<Map<String,Object>> getIndexPicCache(){
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheList(getCacheKey());
        if (StringUtils.isNotNull(cacheObj))
        {
            List<Map<String,Object>> temp = StringUtils.cast(cacheObj);
            return temp;
        }
        return null;
    }

}