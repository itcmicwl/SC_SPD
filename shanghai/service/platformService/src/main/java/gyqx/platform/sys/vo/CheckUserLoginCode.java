package gyqx.platform.sys.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CheckUserLoginCode 
{
	@NotEmpty(message = "{user.code.null}")  
	@Length(min = 5, max = 20, message = "{user.code.length.illegal}")  
	private String userLoginCode;

	public String getUserLoginCode() {
		return userLoginCode;
	}

	public void setUserLoginCode(String userLoginCode) {
		this.userLoginCode = userLoginCode;
	}

}
