package gyqx.ws.yg.vo.reqVo;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import gyqx.ws.yg.vo.ReqHead;

/**
 * 耗材发票信息获取 YY157
 * 
 * @author LIWENKANG
 *
 */
@XmlType(propOrder = { "reqHead", "main" })
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "XMLDATA")
public class YY157_REQ_XML {

	/**
	 * 消息头
	 */
	@XmlElement(name = "HEAD")
	private ReqHead reqHead;
	/**
	 * 消息主条目
	 */
	@XmlElement(name = "MAIN")
	private YY157_REQ_MAIN main;

	public ReqHead getReqHead() {
		return reqHead;
	}

	public void setReqHead(ReqHead reqHead) {
		this.reqHead = reqHead;
	}

	public YY157_REQ_MAIN getMain() {
		return main;
	}

	public void setMain(YY157_REQ_MAIN main) {
		this.main = main;
	}

}