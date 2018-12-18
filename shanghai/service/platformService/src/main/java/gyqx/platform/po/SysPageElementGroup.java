
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
 * 页面元素组定义表
*/
@Table(name="sys_page_element_group")
public class SysPageElementGroup implements IBean
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
	@JoinColumn(name="page_id",table="sys_page",referencedColumnName="id")
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
	 * 分级编码
	*/
	@Size(max=36)
	@Column(name="code")
	@Title("编码")
	private String code ;
	/**
	 * 类型
	 * 
	 * [0 list,1 tree,3 free ]
	*/
	@Size(max=1)
	@Column(name="kind")
	@Title("类型")
	private String kind ;
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
	public String getKind ()
	{
		return kind ;
	}
	
	public void setKind (String kind )
	{
		this.kind = kind;
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
		return "SysPageElementGroup [" +" id=" +  id   +", pageId=" +  pageId   +", ename=" +  ename   +", code=" +  code   +", kind=" +  kind   +", uxid=" +  uxid   +", mt=" +  mt   +", version=" +  version   
			 + "]";
	}

}