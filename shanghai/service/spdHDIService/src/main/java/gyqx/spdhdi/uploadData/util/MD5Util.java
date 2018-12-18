package gyqx.spdhdi.uploadData.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Util {
	
	/**
	 * md5加密
	 * @param timestamp
	 * @param data
	 * @param appPassword
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5(String timestamp, String data, String appPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		StringBuilder sb = new StringBuilder();
		sb.append(timestamp);
		sb.append(data);
		sb.append(appPassword);
        MessageDigest md5= MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(sb.toString().getBytes("utf-8"));
        String res = byteToHexString(digest);
		return res.toUpperCase();
	}
	
	/**
	 * 检验appkey是否合法
	 * @param timestamp
	 * @param data
	 * @param appPassword
	 * @param sign
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkSign(String timestamp, String data, String appPassword, String sign) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String md5 = getMD5(timestamp, data, appPassword);
		return sign.equalsIgnoreCase(md5);
	}
	
	/** 
     * 将指定byte数组转换成16进制字符串 
     * @param bytes 
     * @return 
     */  
    public static String byteToHexString(byte[] bytes) {  
        StringBuffer buffer = new StringBuffer();  
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);  
            if (hex.length() == 1) {  
            	buffer.append("0");  
            }  
            buffer.append(hex);  
        }  
        return buffer.toString();  
    } 
    
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes("utf-8"));//保证在不同环境下使用相同的字符编码,否则在不同环境下input.getBytes()的结果可能是不一样的。
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32
            // chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) throws Exception {
		String timestamp = DateUtil.getTimeStamp();
		String data = "{\"接受系统标识\": \"JDE\",\"接口类型\": \"SPD订单接口表\",\"接口数据\": {\"采购订单明细\": [{\"产地\": \"德国\",\"采购订单日期\": \"2017-09-12 00:00:00\",\"生产厂商\": \"杭州元力医疗器械有限公司\",\"指定供应商ERP编号\": \"00102\",\"指定供应商ERP名称\": \"国药集团联合医疗器械有限公司\",\"医疗机构申请单号\": \"20170912P63\",\"单价\": 100,\"规格\": \"0.25x2mm\",\"包装\": 1,\"采购订单行号\": 1,\"科室申请单号\": \"\",\"单位\": \"支\",\"数量\": 2,\"医药医材ERP编号\": \"10002491\",\"预计到货日期\": \"2017-09-15 00:00:00\",\"医药医材名称\": \"揿针\",\"医药医材编号\": \"10002491\"}],\"业务员ERP编号\": \"17000339\",\"配送单位ERP编号\": \"00102\",\"_date\": \"2017-09-12 20:00:00\",\"订单总行数\": 1,\"请购类型\": \"请购\",\"SPD系统标识\": \"CMIC-BJSPD\",\"要货医院名称\": \"北京市海淀区清河医院\",\"业务部门ERP编号\": \"102011\",\"要货医院ERP编号\": \"1001442\",\"配送单位系统标识\": \"JDE\",\"收货科室名称\": \"药剂室\",\"要货医院\": \"h0006\",\"要货科室名称\": \"药剂室\",\"业务部门名称\": \"国联医院项目部\",\"要货科室ERP编号\": \"6331\",\"收货科室ERP编号\": \"6331\",\"要货科室编号\": \"h0006:001\",\"配送单位ERP部门编号\": \"102011\",\"采购订单号\": \"20170912P63\",\"业务员名称\": \"李志刚\",\"配送单位名称\": \"国药集团联合医疗器械有限公司\",\"收货科室编号\": \"h0006:001\",\"配送单位\": \"p0005\"}}";
		String appPassword = "c84c86d167e2b25c";
		
		StringBuilder sb = new StringBuilder();
		sb.append(timestamp);
		sb.append(data);
		sb.append(appPassword);
		
		String md5 = getMD5(timestamp, data, appPassword);
		log.info(md5);
		
		String md52 = getMD5(sb.toString());
		log.info(md52);
		
	}
	
}
