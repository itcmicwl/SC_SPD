package gyqx.common.exception;

import common.exception.ParameterException;

public class NoUserException extends ParameterException 
{
	public NoUserException(String err)
	{
		super(err,-120);
	}

}
