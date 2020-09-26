package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * redis的database切换注解
 *
 * @author dyh
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisSelect
{
    /**
     * redis database
     */
    int value() default 0;
}
