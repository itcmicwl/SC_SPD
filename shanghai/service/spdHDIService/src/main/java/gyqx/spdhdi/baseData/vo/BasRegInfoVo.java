
package gyqx.spdhdi.baseData.vo;

import gyqx.spdhdi.po.BasRegistrationInfo;

/**
 * 注册证基本信息
 */
public class BasRegInfoVo extends BasRegistrationInfo {
	private static final long serialVersionUID = 1L;
	/**
     * 生产厂家名称
     */
    private String mfrsName;

	public String getMfrsName() {
		return mfrsName;
	}

	public void setMfrsName(String mfrsName) {
		this.mfrsName = mfrsName;
	}
    
    
   
}