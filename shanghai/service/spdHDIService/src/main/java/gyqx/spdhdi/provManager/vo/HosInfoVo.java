
package gyqx.spdhdi.provManager.vo;

/**
 * 医院信息Vo
 */
public class HosInfoVo {
	/**
	 * ID
	 */
	private String id;
	/**
	 * 医院名称
	 */
	private String cname;
	/**
	 * 联系人
	 */
	private String linkman;
	/**
	 * 联系电话
	 */
	private String tel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}