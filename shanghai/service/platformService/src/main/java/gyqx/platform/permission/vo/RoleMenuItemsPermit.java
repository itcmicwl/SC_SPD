package gyqx.platform.permission.vo;

import java.util.List;

public class RoleMenuItemsPermit 
{
	private String roleId;
	private String menuId;
	private List<String> elementIds;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public List<String> getElementIds() {
		return elementIds;
	}
	public void setElementIds(List<String> elementIds) {
		this.elementIds = elementIds;
	}

}
