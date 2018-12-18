
package gyqx.platform.sys.vo;

import common.db.IBean;

/**
 * 项目系统描述表
 * 只有两级，系统、子系统

*/
public class SysProjectVo implements IBean
{
	/**
	 * ID
	*/
	private String id ;
	/**
	 * 上级ID
	*/
	private String pid ;
	/**
	 * 编码
	*/
	private String code ;
	/**
	 * 名称
	*/
	private String ename ;
	/**
	 * 别名
	 * 中文列名
	*/
	private String cname ;
	/**
	 * 描述
	*/
	private String remark ;
	/**
	 * 操作员ID
	 * 新增或修改的操作员ID
	*/
	private String uid ;
	/**
	 * 修改时间
	*/
	private java.util.Date mt ;
	/**
	 * 并发控制
	*/
	private int version ;
	private String pName;
	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getPid ()
	{
		return pid ;
	}
	
	public void setPid (String pid )
	{
		this.pid = pid;
	}
	public String getCode ()
	{
		return code ;
	}
	
	public void setCode (String code )
	{
		this.code = code;
	}
	public String getEname ()
	{
		return ename ;
	}
	
	public void setEname (String name )
	{
		this.ename = name;
	}
	public String getCname ()
	{
		return cname ;
	}
	
	public void setCname (String cname )
	{
		this.cname = cname;
	}
	public String getRemark ()
	{
		return remark ;
	}
	
	public void setRemark (String remark )
	{
		this.remark = remark;
	}
	public String getUid ()
	{
		return uid ;
	}
	
	public void setUid (String uid )
	{
		this.uid = uid;
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
	@Override
	public String toString() {
		return "SysProject ["  +", id=" +  id    +", pid=" +  pid    +", code=" +  code    +", name=" +  ename    +", cname=" +  cname    +", remark=" +  remark    +", uid=" +  uid    +", mt=" +  mt    +", version=" +  version   
			 + "]";
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

}