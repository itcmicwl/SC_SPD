package gyqx.platform.sys.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginParam 
{
	@NotEmpty(message = "{user.code.null}")  
	@Length(min = 5, max = 20, message = "{user.code.length.illegal}")
	private String userLoginCode;
	
	private String hash;
	private String rcode;
	public String getUserLoginCode() {
		return userLoginCode;
	}
	public void setUserLoginCode(String userLoginCode) {
		this.userLoginCode = userLoginCode;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getRcode() {
		return rcode;
	}
	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

}
