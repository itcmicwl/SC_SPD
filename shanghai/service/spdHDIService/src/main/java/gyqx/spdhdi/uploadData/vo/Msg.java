package gyqx.spdhdi.uploadData.vo;
/**
 * @description
 * 返回结果类
 * 
 * @date 2017年9月18日 上午11:28:35
 */
public class Msg {

	private boolean success;
	private String code;
	private String message;

	public Msg() {
		super();
	}

	public Msg(boolean success, String code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	@Override
	public String toString() {
		return "Msg [success=" + success + ", code=" + code + ", message=" + message + "]";
	}

}
