package gyqx.spdhdi.mycerts.vo;

import gyqx.spdhdi.po.ProvJxsqsHos;

public class JxsqsToHosVo extends ProvJxsqsHos {
	private static final long serialVersionUID = 1L;

	private String cname;// 授权对象

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
