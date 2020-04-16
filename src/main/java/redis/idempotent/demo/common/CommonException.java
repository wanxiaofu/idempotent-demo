package redis.idempotent.demo.common;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
public class CommonException extends RuntimeException{

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommonException(String message, String code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CommonException() {
        super();
    }

    public CommonException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public CommonException(ReturnCode returnCode) {
        super(returnCode.getMessage());
        this.code = returnCode.getCode();
        this.message = returnCode.getMessage();
    }
}
