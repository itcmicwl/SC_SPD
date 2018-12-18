package gyqx.platform.sys.vo;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordParam 
{
	@NotEmpty(message = "{user.code.null}")  
	@Length(min = 5, max = 20, message = "{user.code.length.illegal}")
	private String userLoginCode;
	
	private String hash;
	private String rcode;
	
	private String newHash;
	private String newSalt;
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
	public String getNewHash() {
		return newHash;
	}
	public void setNewHash(String newHash) {
		this.newHash = newHash;
	}
	public String getNewSalt() {
		return newSalt;
	}
	public void setNewSalt(String newSalt) {
		this.newSalt = newSalt;
	}
	

}
