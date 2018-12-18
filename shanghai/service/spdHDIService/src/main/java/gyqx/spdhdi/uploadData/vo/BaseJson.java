package gyqx.spdhdi.uploadData.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseJson<T> {
	@JSONField(name = "接口类型")
	private String type;
	@JSONField(name = "接收系统标识")
	private String flag;
	@JSONField(name = "接口数据")
	private T obj;

	public String getType() {
		return type;
	}

	public String getFlag() {
		return flag;
	}

	public T getObj() {
		return obj;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseJson [type=");
		builder.append(type);
		builder.append(", flag=");
		builder.append(flag);
		builder.append(", obj=");
		builder.append(obj);
		builder.append("]");
		return builder.toString();
	}

}
