package gyqx.spdhdi.uploadData.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	/**
	 * jdk8新特性
	 * 线程安全
	 * @return
	 */
	public static String getStamp(){
		LocalDateTime now = LocalDateTime.now();
		String format = now.format(FORMATTER);
		return format;
	}
	
	/**
	 * 获取当前时间的字符串
	 * @return
	 */
	public static String getTimeStamp(){
		Date date =  new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	public static void main(String[] args) {
		log.info(getStamp());
	}
	
}
