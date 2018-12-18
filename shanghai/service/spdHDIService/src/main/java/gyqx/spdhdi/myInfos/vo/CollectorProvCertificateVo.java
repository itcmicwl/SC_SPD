
package gyqx.spdhdi.myInfos.vo;

import java.util.List;

import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;

/**
 * 我的供应商证件信息Vo
 */
public class CollectorProvCertificateVo extends CollectorProvCertificate {

	private String hcpId;

	private List<ProvCertificateImage> provCertificateImage;

	public String getHcpId() {
		return hcpId;
	}

	public void setHcpId(String hcpId) {
		this.hcpId = hcpId;
	}

	public List<ProvCertificateImage> getProvCertificateImage() {
		return provCertificateImage;
	}

	public void setProvCertificateImage(List<ProvCertificateImage> provCertificateImage) {
		this.provCertificateImage = provCertificateImage;
	}

}