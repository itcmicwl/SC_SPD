package gyqx.platform.sys.vo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class SysDictVo {
	
	/**
	 * 查询参数
	 * 通过字典名和别名进行查询
	 */
	private String queryParam;

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	
	
}
