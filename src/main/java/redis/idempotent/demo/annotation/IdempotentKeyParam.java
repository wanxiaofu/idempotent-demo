package redis.idempotent.demo.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 幂等redis key 参数注解
 * </p>
 *
 * @author wanxf
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IdempotentKeyParam {

}
