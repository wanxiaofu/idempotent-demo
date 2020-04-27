package redis.idempotent.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 幂等注解
 * </p>
 *
 * @author wanxf
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Idempotent {

    /**
     * redis锁 key的前缀
     *
     * @return prefix
     */
    String prefix();

    /**
     * 锁过期时间 默认值5
     *
     * @return expire
     */
    int expire() default 5;

    /**
     * 过期时间单位 默认秒
     *
     * @return timeUnit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * key的分隔符 默认":"
     *
     * @return delimiter
     */
    String delimiter() default ":";
}
