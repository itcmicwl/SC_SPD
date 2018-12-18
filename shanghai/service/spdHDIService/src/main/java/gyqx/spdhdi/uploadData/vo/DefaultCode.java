package gyqx.spdhdi.uploadData.vo;

public interface DefaultCode {
	
	public static final String SUCCESS = "0000";			// 成功	
	public static final String ILLEGAL_USER = "1001";		// 非法调用(Appkey不合法或签名不合法,时间戳与当前服务器时间差异过大)
	public static final String NOT_SUPPORT = "1002";		// 不支持的接口类型
	public static final String UNKNOWN_SYSTEM = "1003";		// 未知的接收系统
	public static final String RECEIVE_FAILED = "1004";		// 数据接收失败
	public static final String FORMAT_MISMATCH = "1005";	// 数据格式错误,解析失败
	public static final String CUSTOM = "9999";				// 自定义错误描述
	
}
