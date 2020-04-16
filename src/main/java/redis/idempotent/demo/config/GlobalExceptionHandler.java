package redis.idempotent.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import redis.idempotent.demo.common.CommonException;
import redis.idempotent.demo.common.Response;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public Response handleCommonException(CommonException e){
        Response response = new Response();
        response.setMessage(e.getMessage());
        response.setCode(e.getCode());
        return response;
    }

}
