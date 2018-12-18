package gyqx.spdhdi.uploadData.excepiton;

import common.exception.ParameterException;

/**
 * 连接JDE成功后，调用方法失败异常
 * 
 * @author LIWENKANG
 *
 */
public class UploadDataException extends ParameterException {

	private static final long serialVersionUID = 1071049669822996931L;

	public UploadDataException(String err) {
		super(err, -9998);
	}

}