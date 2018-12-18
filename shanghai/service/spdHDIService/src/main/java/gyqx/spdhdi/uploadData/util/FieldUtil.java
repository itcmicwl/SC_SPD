package gyqx.spdhdi.uploadData.util;

import java.lang.reflect.Field;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class FieldUtil {

	/**
	 * 判断字段是否为空
	 * 
	 * @param obj 需要判断的对象
	 * @param fieldNames 不需要判断的字段
	 * @return
	 */
	public static boolean checkFieldValueNull(Object obj, List<String> fieldNames) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				Object object = f.get(obj);
				if (fieldNames != null && fieldNames.contains(f.getName())) {
					continue;
				}
				if (object == null) {
					return false;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 针对注解属性值 <br>
	 * 判断所需字段是否为空
	 * 
	 * @param obj 判断对象
	 * @param fieldNames 所需字段
	 * @return
	 */
	public static boolean checkFieldValueNotNull(Object obj, List<String> fieldNames) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			boolean isAnno = f.isAnnotationPresent(JSONField.class);
			if (isAnno) {
				JSONField anno = f.getAnnotation(JSONField.class);
				String name = anno.name();
				if (fieldNames != null && fieldNames.contains(name)) {
					try {
						Object object = f.get(obj);
						if (object == null) {
							return false;
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	
}
