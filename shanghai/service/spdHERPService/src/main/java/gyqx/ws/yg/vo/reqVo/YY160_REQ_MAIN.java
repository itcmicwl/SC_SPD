package gyqx.ws.yg.vo.reqVo;

import javax.xml.bind.annotation.*;

/**
 * 耗材发票状态获取 YY160
 * 
 * @author LIWENKANG
 *
 */
@XmlType(propOrder = { "qsrq", "jzrq", "fpdm", "fph", "fpbhcxtj" })
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "MAIN")
public class YY160_REQ_MAIN {

	/**
	 * 起始日期
	 */
	@XmlElement(name = "QSRQ")
	private String qsrq;
	/**
	 * 截止日期
	 */
	@XmlElement(name = "JZRQ")
	private String jzrq;
	/**
	 * 发票代码
	 */
	@XmlElement(name = "FPDM")
	private String fpdm;
	/**
	 * 发票号
	 */
	@XmlElement(name = "FPH")
	private String fph;
	/**
	 * 发票编号查询条件
	 */
	@XmlElement(name = "FPBHCXTJ")
	private String fpbhcxtj;

	public String getQsrq() {
		return qsrq;
	}

	public void setQsrq(String qsrq) {
		this.qsrq = qsrq;
	}

	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	public String getFpdm() {
		return fpdm;
	}

	public void setFpdm(String fpdm) {
		this.fpdm = fpdm;
	}

	public String getFph() {
		return fph;
	}

	public void setFph(String fph) {
		this.fph = fph;
	}

	public String getFpbhcxtj() {
		return fpbhcxtj;
	}

	public void setFpbhcxtj(String fpbhcxtj) {
		this.fpbhcxtj = fpbhcxtj;
	}

}