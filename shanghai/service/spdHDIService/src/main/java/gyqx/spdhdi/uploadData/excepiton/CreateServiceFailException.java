package gyqx.spdhdi.uploadData.excepiton;

import common.exception.ParameterException;

/**
 * 连接JDE接口失败
 * 
 * @author LIWENKANG
 *
 */
public class CreateServiceFailException extends ParameterException {

	private static final long serialVersionUID = -327799553526531773L;

	public CreateServiceFailException(String error) {
		super(error, -9999);
	}

}
