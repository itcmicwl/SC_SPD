package gyqx.platform.permission.vo;

public class UserOrg
{
	private String id;
	private String pid;
	private String corpId;
	private String ename;
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "UserOrg [id=" + id + ", pid=" + pid + ", corpId=" + corpId + ", ename=" + ename + "]";
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	

}
