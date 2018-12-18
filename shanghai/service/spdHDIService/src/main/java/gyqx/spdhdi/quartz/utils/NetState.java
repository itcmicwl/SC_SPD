package gyqx.spdhdi.quartz.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Slf4j
public class NetState {

	public static boolean isConnect(String ip) {
		boolean connect = false;
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec("ping " + ip);

			InputStream is = process.getInputStream();

			InputStreamReader isr = new InputStreamReader(is, Charset.forName("GBK"));
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			log.info("返回值为:" + sb.toString());
			is.close();
			isr.close();
			br.close();

			if (null != sb && !sb.toString().equals("")) {
				if (sb.toString().indexOf("TTL") > 0) {
					// 网络畅通
					connect = true;
				} else {
					// 网络不畅通
					connect = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connect;
	}
}
