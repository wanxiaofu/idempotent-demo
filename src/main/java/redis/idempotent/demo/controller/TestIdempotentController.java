package redis.idempotent.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.idempotent.demo.annotation.Idempotent;
import redis.idempotent.demo.common.Response;
import redis.idempotent.demo.dto.InsertDTO;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/17
 */
@RestController
@RequestMapping("/idempotent")
public class TestIdempotentController {

    @Idempotent(prefix = "pay")
    @PostMapping("/test")
    public Response testIdempotent(@RequestBody InsertDTO dto) throws Exception{
        System.out.println("dto = " + dto);
        Thread.sleep(2000);
        return Response.ok();
    }

}
