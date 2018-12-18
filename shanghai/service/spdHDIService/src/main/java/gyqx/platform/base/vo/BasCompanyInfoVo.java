package gyqx.platform.base.vo;

import java.util.List;

import gyqx.spdhdi.po.BasCompanyInfo;

/**
 * 我的供应商列表VO
 * 
 * @author xuLiqiang
 */
public class BasCompanyInfoVo extends BasCompanyInfo {

	private String collectorId;
	private String hosId;
	private String hosName;
	private String hcpId;

	private List<CollectorProvCertificateVo> provCertificates;

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public String getHosId() {
		return hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public List<CollectorProvCertificateVo> getProvCertificates() {
		return provCertificates;
	}

	public String getHcpId() {
		return hcpId;
	}

	public void setHcpId(String hcpId) {
		this.hcpId = hcpId;
	}

	public void setProvCertificates(List<CollectorProvCertificateVo> provCertificates) {
		this.provCertificates = provCertificates;
	}

}
