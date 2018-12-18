package gyqx.spdhdi.collector4orderMgr.vo;

import java.io.Serializable;

import gyqx.spdherp.po.HosCollectorProv;

public class HosCollectorProvVo extends HosCollectorProv implements Serializable{

	private static final long serialVersionUID = 6861549523271864604L;
	private String hosName;
	private String collectorName;
	private String provName;
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHosName() {
		return hosName;
	}
	public String getCollectorName() {
		return collectorName;
	}
	public String getProvName() {
		return provName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
}
