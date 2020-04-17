package redis.idempotent.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.idempotent.demo.common.Response;
import redis.idempotent.demo.exception.CommonException;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public Response handleCommonException(CommonException e) {
        printLog(e);
        Response response = new Response();
        response.setMessage(e.getMessage());
        response.setCode(e.getCode());
        return response;
    }

    private void printLog(Exception e) {
        log.error(e.getMessage());
    }

}
