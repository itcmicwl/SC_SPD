
package gyqx.platform.sys.vo;

/**
 * 系统模块功能描述表

*/
public class SysModuleFunctionVo
{
	private String id ;
	private String moduleId ;
	private String code ;
	private String ename ;
	private String cname ;
	private String remark ;
	private String uid ;
	private java.util.Date mt ;
	private int version ;
	private String mName;
	private String gId;
	private String gName;
	private String spCode;
	private String spName;
	public String getId ()
	{
		return id ;
	}
	
	public void setId (String id )
	{
		this.id = id;
	}
	public String getModuleId ()
	{
		return moduleId ;
	}
	
	public void setModuleId (String moduleId )
	{
		this.moduleId = moduleId;
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
	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	@Override
	public String toString() {
		return "SysModuleFunction ["  +", id=" +  id    +", moduleId=" +  moduleId    +", code=" +  code    +", name=" +  ename    +", cname=" +  cname    +", remark=" +  remark    +", uid=" +  uid    +", mt=" +  mt    +", version=" +  version   
			 + "]";
	}

}