package gyqx.platform.permission.vo;

import java.util.List;

public class MenuConfig 
{
	private String projectCode;
	private List<String> menuIds;
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public List<String> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

}
