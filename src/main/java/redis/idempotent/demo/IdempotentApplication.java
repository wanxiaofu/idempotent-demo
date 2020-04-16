package redis.idempotent.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class IdempotentApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdempotentApplication.class, args);
    }
}
