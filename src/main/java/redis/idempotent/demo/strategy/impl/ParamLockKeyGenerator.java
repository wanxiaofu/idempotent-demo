package redis.idempotent.demo.strategy.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import redis.idempotent.demo.annotation.Idempotent;
import redis.idempotent.demo.annotation.IdempotentKeyParam;
import redis.idempotent.demo.strategy.CacheKeyGenerator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
@Component
public class ParamLockKeyGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        //下面两个数组中，参数值和参数名的个数和位置是一一对应的。
        //获取方法参数名
        Parameter[] parameters = method.getParameters();
        //获取连接点参数值
        Object[] args = joinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            IdempotentKeyParam annotation = parameters[i].getAnnotation(IdempotentKeyParam.class);
            if (annotation != null) {
                stringBuilder.append(idempotent.delimiter()).append(args[i]);
            }
        }
        return idempotent.prefix() + stringBuilder.toString();
    }
}
