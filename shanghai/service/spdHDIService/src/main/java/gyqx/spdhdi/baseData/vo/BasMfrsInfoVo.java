package gyqx.spdhdi.baseData.vo;

import gyqx.spdhdi.po.BasMfrsInfo;

/**
 * Created by liangwu on 17-6-7.
 */
public class BasMfrsInfoVo extends BasMfrsInfo {
	
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private String id;

	/**
	 * 厂家名称
	 */
	private String mfrsName;
	/**
	 * 操作员名称
	 * add by xuliqiang 2017-09-08
	 */
	private String ename;
	/**
	 * 操作员名称
	 * add by xuliqiang 2017-09-08
	 */
	private String cname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMfrsName() {
		return mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "BasMfrsInfoVo [id=" + id + ", mfrsName=" + mfrsName + ", ename=" + ename + ", cname=" + cname + "]";
	}

}
