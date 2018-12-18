package gyqx.platform.permission.vo;

import java.util.List;

import gyqx.platform.po.SysPageview;
import gyqx.platform.po.SysPageviewElement;

/**
 * 菜单关联的界面及功能按钮信息
 * @author Administrator
 *
 */
public class MenuPageviewPermission 
{
	private String id;
	private SysPageview pageview;
	
	private List<SysPageviewElement> elements;	
	
	public SysPageview getPageview() {
		return pageview;
	}

	public void setPageview(SysPageview pageview) {
		this.pageview = pageview;
	}

	public List<SysPageviewElement> getElements() {
		return elements;
	}
	public void setElements(List<SysPageviewElement> elements) {
		this.elements = elements;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static class MenuPermissionSet
	{
		private String menuId;
		private List<String> elementIds;
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
}


