package gyqx.spdhdi.baseData.vo;

public class UUIDandPassword {

	private String uuid;
	private String password;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UUIDandPassword [uuid=" + uuid + ", password=" + password + "]";
	}

}
