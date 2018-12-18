package gyqx.common.exception;

import common.exception.ParameterException;

public class LoginPasswordException extends ParameterException 
{
	public LoginPasswordException(String err)
	{
		super(err,-111);
	}
}
