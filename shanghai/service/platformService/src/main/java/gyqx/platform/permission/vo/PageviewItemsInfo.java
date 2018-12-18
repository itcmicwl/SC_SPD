package gyqx.platform.permission.vo;

import java.util.List;

public class PageviewItemsInfo 
{
	public static class ItemModuleInfo
	{
		private String pageViewItemId;
		private String moduleId;
		private String funcId;
		private String pageViewItemName;
		private String moduleName;
		private String moduleFunctionName;
		private int pageViewItemVersion;
		public String getPageViewItemId() {
			return pageViewItemId;
		}
		public void setPageViewItemId(String pageViewItemId) {
			this.pageViewItemId = pageViewItemId;
		}
		public String getModuleId() {
			return moduleId;
		}
		public void setModuleId(String moduleId) {
			this.moduleId = moduleId;
		}
		public String getFuncId() {
			return funcId;
		}
		public void setFuncId(String funcId) {
			this.funcId = funcId;
		}
		public String getPageViewItemName() {
			return pageViewItemName;
		}
		public void setPageViewItemName(String pageViewItemName) {
			this.pageViewItemName = pageViewItemName;
		}
		public String getModuleName() {
			return moduleName;
		}
		public void setModuleName(String moduleName) {
			this.moduleName = moduleName;
		}
		public String getModuleFunctionName() {
			return moduleFunctionName;
		}
		public void setModuleFunctionName(String moduleFunctionName) {
			this.moduleFunctionName = moduleFunctionName;
		}
		public int getPageViewItemVersion() {
			return pageViewItemVersion;
		}
		public void setPageViewItemVersion(int pageViewItemVersion) {
			this.pageViewItemVersion = pageViewItemVersion;
		}
		
	}
	
	private String pageviewId;
	private String pageviewName;
	private List<ItemModuleInfo> items;
	public String getPageviewId() {
		return pageviewId;
	}
	public void setPageviewId(String pageviewId) {
		this.pageviewId = pageviewId;
	}
	public String getPageviewName() {
		return pageviewName;
	}
	public void setPageviewName(String pageviewName) {
		this.pageviewName = pageviewName;
	}
	public List<ItemModuleInfo> getItems() {
		return items;
	}
	public void setItems(List<ItemModuleInfo> items) {
		this.items = items;
	}

}
