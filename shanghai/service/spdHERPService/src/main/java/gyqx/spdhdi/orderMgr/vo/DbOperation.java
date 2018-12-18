package gyqx.spdhdi.orderMgr.vo;

import java.util.List;

public class DbOperation {

	private String expression;
	private List<Object> vals;
	public String getExpression() {
		return expression;
	}
	public List<Object> getVals() {
		return vals;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public void setVals(List<Object> vals) {
		this.vals = vals;
	} 
}
