package gyqx.spdhdi.uploadData.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import gyqx.spdhdi.uploadData.vo.Msg;

public class JsonUtil {
	
	/**
	 * 禁止循环引用检测
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		return JSON.toJSONString(object, 
				SerializerFeature.DisableCircularReferenceDetect, 
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 返回结果
	 * @param msg
	 * @return
	 */
	public static String write_json(Msg msg){
		return toJsonString(msg);
	}

}
