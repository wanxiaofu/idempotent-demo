package redis.idempotent.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wanxf
 * redisson 配置
 * https://github.com/redisson/redisson/wiki/2.-配置方法#23-常用设置
 */
@Slf4j
@Configuration
public class RedissonSpringDataConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress("redis://" + host + ":" + port);
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        config.setTransportMode(TransportMode.NIO);
        log.info("------------- redisson -----------------------");
        log.info(config.getTransportMode().toString());
        return Redisson.create(config);
    }

}