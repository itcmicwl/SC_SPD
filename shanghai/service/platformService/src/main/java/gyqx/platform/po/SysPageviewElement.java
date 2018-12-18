
package gyqx.platform.po;

import java.util.*;
import common.db.IBean;
import common.db.meta.Title;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.*;
/**
 * 页面元素定义表
*/
@Table(name="sys_pageview_element")
public class SysPageviewElement implements IBean
{
	/**
	 * ID
	*/
	@Id
	@Size(max=36)
	@Column(name="id")
	@Title("ID")
	private String id ;
	/**
	 * 页面ID
	*/
	@Size(max=36)
	@JoinColumn(name="page_id",table="sys_pageview",referencedColumnName="id")
	@Title("页面ID")
	private String pageId ;
	/**
	 * 名称
	*/
	@Size(max=36)
	@Column(name="ename")
	@Title("名称")
	private String ename ;
	/**
	 * 编码
	 * 元素分级编码
	*/
	@Size(max=36)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 操作URLs
	 * sys_module_function_design.id 的多个id，用豆号分隔。
	*/
	@Size(max=1024)
	@Column(name="func_ids")
	@Title("操作URLs")
	private String funcIds ;
	/**
	 * 说明
	*/
	@Size(max=256)
	@Column(name="remark")
	@Title("说明")
	private String remark ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	@Size(max=36)
	@Column(name="uxid")
	@Title("操作员ID")
	private String uxid ;
	/**
	 * 修改时间
	*/
	@Column(name="mt")
	@Title("修改时间")
	private java.util.Date mt ;
	/**
	 * 并发控制
	*/
	@Column(name="version")
	@Title("并发控制")
	private int version ;

	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getPageId ()
	{
		return pageId ;
	}
	
	public void setPageId (String pageId )
	{
		this.pageId = pageId;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String ename )
	{
		this.ename = ename;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getFuncIds ()
	{
		return funcIds ;
	}
	
	public void setFuncIds (String funcIds )
	{
		this.funcIds = funcIds;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public String getUxid ()
	{
		return uxid ;
	}
	
	public void setUxid (String uxid )
	{
		this.uxid = uxid;
	}
	public java.util.Date getMt ()
	{
		return mt ;
	}
	
	public void setMt (java.util.Date mt )
	{
		this.mt = mt;
	}
	public int getVersion ()
	{
		return version ;
	}
	
	public void setVersion (int version )
	{
		this.version = version;
	}

	public String getUid ()
	{
		return uxid ;
	}
	
	public void setUid (String uxid )
	{
		this.uxid = uxid;
	}

	@Override
	public String toString() {
		return "SysPageviewElement [" +" id=" +  id   +", pageId=" +  pageId   +", ename=" +  ename   +", code=" +  code   +", funcIds=" +  funcIds   +", remark=" +  remark   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}