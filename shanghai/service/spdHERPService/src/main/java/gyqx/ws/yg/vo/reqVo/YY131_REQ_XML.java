package gyqx.ws.yg.vo.reqVo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import gyqx.ws.yg.vo.ReqHead;

/**
 * 耗材配送验收确认 YY131
 * 
 * @author LIWENKANG
 *
 */
@XmlType(propOrder = { "reqHead", "main", "detail" })
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "XMLDATA")
public class YY131_REQ_XML {

	/**
	 * 消息头
	 */
	@XmlElement(name = "HEAD")
	private ReqHead reqHead;
	/**
	 * 消息主条目
	 */
	@XmlElement(name = "MAIN")
	private YY131_REQ_MAIN main;
	/**
	 * 消息明细条目
	 */
	@XmlElementWrapper(name = "DETAIL")
	@XmlElement(name = "STRUCT")
	private List<YY131_REQ_DETAIL> detail;

	public ReqHead getReqHead() {
		return reqHead;
	}

	public void setReqHead(ReqHead reqHead) {
		this.reqHead = reqHead;
	}

	public YY131_REQ_MAIN getMain() {
		return main;
	}

	public void setMain(YY131_REQ_MAIN main) {
		this.main = main;
	}

	public List<YY131_REQ_DETAIL> getDetail() {
		return detail;
	}

	public void setDetail(List<YY131_REQ_DETAIL> detail) {
		this.detail = detail;
	}

}
