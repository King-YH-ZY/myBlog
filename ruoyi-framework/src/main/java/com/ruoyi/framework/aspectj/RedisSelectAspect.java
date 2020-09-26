package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.RedisSelect;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisSelectSupport;
import com.ruoyi.common.core.redis.SelectableRedisTemplate;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.web.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Redis切面处理类
 *
 * @author ruoyi
 */
@Aspect
@Component
public class RedisSelectAspect
{
    private static final Logger log = LoggerFactory.getLogger(RedisSelectAspect.class);

    @Value("${spring.redis.database}")
    private int defaultDataBase;


    @Around("execution(* com.ruoyi.common.core.redis.RedisCache.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try{
            result = point.proceed();
        }catch (Exception e){
            log.error("redis error", e);
            throw new UtilException("Redis服务异常");
        }
        return result;
    }

    @Around("@annotation(com.ruoyi.common.annotation.RedisSelect)")
    @ConditionalOnBean(SelectableRedisTemplate.class)
    public Object configRedis(ProceedingJoinPoint point) throws Throwable{
        int db = defaultDataBase;
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();

            RedisSelect config = method.getAnnotation(RedisSelect.class);
            if(config != null){
                db = config.value();
            }
            RedisSelectSupport.select(db);
            return point.proceed();
        } finally {
            RedisSelectSupport.select(defaultDataBase);
            log.debug("redis reset {} to {}", db, defaultDataBase);
        }
    }
}
