/*
 *@author fatkun , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This: 处理换取数据线程
 */

package cn.imgdpu.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class GetDataThread implements Runnable {
	public String domain, path;
	public String encode = "gb2312";
	public String htmlData;

	public GetDataThread(String _domain, String _path) {
		domain = _domain;
		path = _path.trim();
	}

	@Override
	public void run() {
		try {
			// 创建连接
			InetAddress addr = InetAddress.getByName(domain);
			int port = 80;
			Socket socket = new Socket(addr, port);
			// 写入数据
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), encode));
			wr.write("GET " + path + " HTTP/1.1\r\n");
			wr.write("Host: " + domain + "\r\n");
			wr.write("\r\n");
			wr.flush();
			// 创建读取数据的Reader,里面指定了需要的编码类型。
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(), encode));
			// 读取每一行的数据.注意大部分端口操作都需要交互数据。
			String str;
			while ((str = rd.readLine()) != null) {
				htmlData += str + "\r\n";
			}
			rd.close();
		} catch (SocketException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "网络连接异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}
	}

}
