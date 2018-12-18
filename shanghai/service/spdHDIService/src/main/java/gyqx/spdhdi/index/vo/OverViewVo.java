package gyqx.spdhdi.index.vo;

import java.io.Serializable;

public class OverViewVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3870169746162057000L;
	private Integer unSubmitPur;
	private Integer unConfirm;
	private Integer unRecive;
	public Integer getUnSubmitPur() {
		return unSubmitPur;
	}
	public void setUnSubmitPur(Integer unSubmitPur) {
		this.unSubmitPur = unSubmitPur;
	}
	public Integer getUnConfirm() {
		return unConfirm;
	}
	public void setUnConfirm(Integer unConfirm) {
		this.unConfirm = unConfirm;
	}
	public Integer getUnRecive() {
		return unRecive;
	}
	public void setUnRecive(Integer unRecive) {
		this.unRecive = unRecive;
	}
}
