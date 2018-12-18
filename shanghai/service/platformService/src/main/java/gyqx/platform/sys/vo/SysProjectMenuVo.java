
package gyqx.platform.sys.vo;

import java.util.List;

import gyqx.platform.po.SysProjectMenu;

/**
 * 子系统菜单表
*/
public class SysProjectMenuVo extends SysProjectMenu 
{		
	/**
	 * 
	 */
	private static final long serialVersionUID = -2519157963772807285L;
	private String projectName;
	private String pName;
	private List<SysProjectMenuVo> children;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public List<SysProjectMenuVo> getChildren() {
		return children;
	}
	public void setChildren(List<SysProjectMenuVo> children) {
		this.children = children;
	}
}