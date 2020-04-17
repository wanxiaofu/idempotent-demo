package redis.idempotent.demo.dto;

import lombok.Data;
import redis.idempotent.demo.annotation.IdempotentKeyParam;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/17
 */
@Data
public class InsertDTO {

    @IdempotentKeyParam
    private Long chargeId;

    private BigDecimal total;
}
