package gyqx.txp.message;

import java.util.Map;

public class ByteArrayBody implements java.io.Serializable
{

	private static final long serialVersionUID = -2110599176125521128L;
	
	private Map<String,Object> props;	
	
	private byte[] data;	

	public Map<String,Object> getProps() {
		return props;
	}

	public void setProps(Map<String,Object> props) {
		this.props = props;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	

}
