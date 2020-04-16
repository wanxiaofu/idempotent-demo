package redis.idempotent.demo.common;

/**
 * <p>
 *
 * </p>
 *
 * @author wanxf
 * @date 2020/04/16
 */
public enum ReturnCode {
    /**
     * 成功
     */
    SUCCESS("1000", "接口调用成功"),

    /**
     * 业务处理失败
     */
    BIZ_FAIL("4000","业务处理失败"),
    /**
     * 1001
     */
    RESUBMIT("2001", "请勿重复提交");
    private String code;
    private String message;

    ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessageFromCode(String code) {
        for (ReturnCode value : ReturnCode.values()) {
            if (code.equals(value.getCode())) {
                return value.getMessage();
            }
        }
        return null;
    }
}
