package redis.idempotent.demo.common;

import lombok.Data;

/**
 * <p>
 *     Response
 * </p>
 * @author wanxf
 */
@Data
public class Response<T> {
	
	private String code;
	private String message;
	private T data;
	private Object extendOne;
	private Object extendTwo;
	private Object extendThree;
	
	public Response() {
	}

	public Response(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Response(String code, String message, T data, Object extendOne, Object extendTwo, Object extendThree) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.extendOne = extendOne;
		this.extendTwo = extendTwo;
		this.extendThree = extendThree;
	}

	public Boolean isSuccess(){
		if (ReturnCode.SUCCESS.getCode().equals(code)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	public static<E> Response ok(E data){
		Response<E> res = new Response<E>();
		res.setCode(ReturnCode.SUCCESS.getCode());
		res.setData(data);
		res.setMessage(ReturnCode.SUCCESS.getMessage());
		return res;
	}
	
	public static<E> Response ok(E data, Object extendOne, Object extendTwo, Object extendThree){
		Response<E> res = new Response<>();
		res.setCode(ReturnCode.SUCCESS.getCode());
		res.setData(data);
		res.setMessage(ReturnCode.SUCCESS.getMessage());
		res.setExtendOne(extendOne);
		res.setExtendTwo(extendTwo);
		res.setExtendThree(extendThree);
		return res;
	}
	
	public static Response fail(){
		Response res = new Response();
		res.setCode(ReturnCode.BIZ_FAIL.getCode());
		res.setMessage(ReturnCode.BIZ_FAIL.getMessage());
		return res;
	}
	
	public static Response returnCode(ReturnCode returnCode){
		Response res = new Response();
		res.setCode(returnCode.getCode());
		res.setMessage(returnCode.getMessage());
		return res;
	}
	
	public static Response returnCode(String code, String msg){
		Response res = new Response();
		res.setCode(code);
		res.setMessage(msg);
		return res;
	}
	public static<E> Response returnCodeAndData(String code, String msg,E data){
		Response<E> res = new Response<>();
		res.setCode(code);
		res.setMessage(msg);
		res.setData(data);
		return res;
	}
	
	public static Response ok(){
		Response res = new Response();
		res.setCode(ReturnCode.SUCCESS.getCode());
		res.setMessage(ReturnCode.SUCCESS.getMessage());
		return res;
	}

}