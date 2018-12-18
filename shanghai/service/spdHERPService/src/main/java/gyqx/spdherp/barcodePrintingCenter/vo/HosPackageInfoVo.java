package gyqx.spdherp.barcodePrintingCenter.vo;

import gyqx.spdherp.po.HosPackageBatchDetail;
import gyqx.spdherp.po.HosPackageInfo;

public class HosPackageInfoVo extends HosPackageInfo {

	private static final long serialVersionUID = 1L;

	private HosPackageBatchDetail hosPackageBatchDetail;

	public HosPackageBatchDetail getHosPackageBatchDetails() {
		return hosPackageBatchDetail;
	}

	public void setHosPackageBatchDetails(HosPackageBatchDetail hosPackageBatchDetail) {
		this.hosPackageBatchDetail = hosPackageBatchDetail;
	}

}
