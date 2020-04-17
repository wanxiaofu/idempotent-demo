package redis.idempotent.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.idempotent.demo.annotation.Idempotent;
import redis.idempotent.demo.common.ReturnCode;
import redis.idempotent.demo.exception.CommonException;
import redis.idempotent.demo.strategy.CacheKeyGenerator;
import redis.idempotent.demo.utils.RedLockUtils;
import redis.idempotent.demo.utils.RedisLockHelper;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
@Aspect
@Configuration
public class IdempotentAspect {

    private CacheKeyGenerator cacheKeyGenerator;
    private RedisLockHelper redisLockHelper;

    @Autowired
    public IdempotentAspect(CacheKeyGenerator cacheKeyGenerator, RedisLockHelper redisLockHelper) {
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.redisLockHelper = redisLockHelper;
    }

    @Pointcut("@annotation(redis.idempotent.demo.annotation.Idempotent)")
    public void idempotentPointcut() {
    }

    @Around("idempotentPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        if (StringUtils.isBlank(annotation.prefix())) {
            return new RuntimeException("lock key cant be null");
        }
        String lockKey = cacheKeyGenerator.getLockKey(joinPoint);
        String value = UUID.randomUUID().toString();
        try {
            boolean lock = redisLockHelper.lock(lockKey, value, annotation.expire(), annotation.timeUnit());
            if (!lock) {
                throw new CommonException(ReturnCode.RESUBMIT);
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new CommonException(ReturnCode.BIZ_FAIL);
            }
        } finally {
            redisLockHelper.unlock(lockKey, value);
        }
    }
}
