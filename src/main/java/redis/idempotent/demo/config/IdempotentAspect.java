package redis.idempotent.demo.config;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Aspect
@Configuration
public class IdempotentAspect {

    private CacheKeyGenerator cacheKeyGenerator;
    private RedLockUtils redLockUtils;

    @Autowired
    public IdempotentAspect(CacheKeyGenerator cacheKeyGenerator, RedLockUtils redLockUtils) {
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.redLockUtils = redLockUtils;
    }

    @Pointcut("@annotation(redis.idempotent.demo.annotation.Idempotent)")
    public void idempotentPointcut() {
    }

    @Around("idempotentPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        String lockKey = cacheKeyGenerator.getLockKey(joinPoint);
//        System.out.println("lockKey = " + lockKey);
        try {
            boolean lock = redLockUtils.tryLockTimeout(lockKey, -1, annotation.expire(), annotation.timeUnit());
            if (!lock) {
                throw new CommonException(ReturnCode.RESUBMIT);
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                log.error("{}方法执行失败，原因：{}", method.getName(), throwable.getMessage(), throwable);
                throw new CommonException(ReturnCode.BIZ_FAIL);
            }
        } catch (InterruptedException e) {
            throw new CommonException(ReturnCode.SERVER_EXCEPTION);
        } finally {
            redLockUtils.unLock(lockKey);
        }
    }
}
