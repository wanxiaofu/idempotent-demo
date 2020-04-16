package redis.idempotent.demo.strategy;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>
 * redis缓存key生成策略
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
public interface CacheKeyGenerator {

    /**
     * redis缓存key生成策略
     *
     * @param joinPoint 切入点
     * @return key
     */
    String getLockKey(ProceedingJoinPoint joinPoint);

}
