package com.ruoyi.yh.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.yh.domain.YhBlog;
import com.ruoyi.yh.domain.YhBlogContent;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 博客redis 工具类
 */
public class BlogRedisUtils {

    /**
     * 清空缓存
     */
    public static void clearBlogCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.Blog_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    public static void clearBlogCacheByKey(String Key)
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(Key));
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.Blog_KEY + configKey;
    }

    /**
     * 设置缓存
     *
     * @param key 参数键
     * @param blog 博客
     */
    public static void setBlogCache(String key, Map<String,Object> blog)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), blog);
    }

    /**
     * 设置4分钟过期的空值
     * @param key
     */
    public static void setBlogNullCache(String key)
    {
        Map<String,Object> map = null;
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), map,4, TimeUnit.MINUTES);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static Map<String,Object> getBlogCache(String key)
    {
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            Map<String,Object> temp = StringUtils.cast(cacheObj);
            return temp;
        }
        return null;
    }

    /**
     * 设置博客归档数据缓存
     * @param blog
     */
    public static void setBlogTimeLineCache(List<Map<String,Object>> blog)
    {
        SpringUtils.getBean(RedisCache.class).setCacheList(Constants.Blog_TimeLine_KEY, blog);
    }


    /**
     * 获取博客归档缓存
     * @return
     */
    public static List<Map<String,Object>> getBlogTimeLineCache(){
        Object cacheObj = SpringUtils.getBean(RedisCache.class).getCacheList(Constants.Blog_TimeLine_KEY);
        if (StringUtils.isNotNull(cacheObj))
        {
            List<Map<String,Object>> temp = StringUtils.cast(cacheObj);
            return temp;
        }
        return null;
    }

    /**
     * 清空博客归档缓存
     */
    public static void clearBlogTimeLineCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.Blog_TimeLine_KEY);
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }
}