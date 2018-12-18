package gyqx.txp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

public class UdpClientTest 
{
	@Test
	public void test()
	{
		DatagramSocket socket = null;
		InetAddress server = null;
		int port =12722;
		
		try {
			server = Inet4Address.getByName("10.17.65.21");//220.249.121.11
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}
		try {
			socket = new DatagramSocket(); // 注意此处要先在配置文件里设置权限,否则会抛权限不足的异常
			socket.setSoTimeout(0);

		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		byte[] data = new byte[1400];
		for(int i = 0; i< data.length;i++)
		{
			data[i] = (byte) (i%256);
		}
		DatagramPacket sPacket = new DatagramPacket(data,data.length,server,port);
		long t = System.currentTimeMillis();
		for(int i = 0; i< 1024*1024;i++){
			try {
				socket.send(sPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i);
		}
		System.out.println("ms=" +(System.currentTimeMillis() - t));
	}

}
