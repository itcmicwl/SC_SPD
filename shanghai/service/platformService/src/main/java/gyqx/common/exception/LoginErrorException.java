package gyqx.common.exception;

import common.exception.ParameterException;

public class LoginErrorException extends ParameterException 
{
	public LoginErrorException(String err)
	{
		super(err,-110);
	}

}
