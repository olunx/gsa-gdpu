/*
 *@author olunx , Time:2009-3-6
 *
 *Website: http://www.olunx.com
 *
 *This: 读取rss新闻
 */

package cn.imgdpu.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

public class ReadRssNews {

	public void getData(String domain, String target, String fileName) {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		HttpProtocolParams.setUserAgent(params, "Mozilla/4.0");
		HttpProtocolParams.setUseExpectContinue(params, true);

		BasicHttpProcessor httpproc = new BasicHttpProcessor();
		// Required protocol interceptors
		httpproc.addInterceptor(new RequestContent());
		httpproc.addInterceptor(new RequestTargetHost());
		// Recommended protocol interceptors
		httpproc.addInterceptor(new RequestConnControl());
		httpproc.addInterceptor(new RequestUserAgent());
		httpproc.addInterceptor(new RequestExpectContinue());

		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

		HttpContext context = new BasicHttpContext(null);
		HttpHost host = new HttpHost(domain, 80);
		//HttpHost host = new HttpHost("branch.gdpu.edu.cn", 80);

		DefaultHttpClientConnection conn = new DefaultHttpClientConnection();
		ConnectionReuseStrategy connStrategy = new DefaultConnectionReuseStrategy();

		context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
		context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);

		try {

			//String[] targets = { "/swxsy/admin/gsa/rss.asp" };
			String[] targets = { target };

			for (int i = 0; i < targets.length; i++) {
				if (!conn.isOpen()) {
					Socket socket = new Socket(host.getHostName(), host.getPort());
					conn.bind(socket, params);
				}
				BasicHttpRequest request = new BasicHttpRequest("GET", targets[i]);
				System.out.println(">> Request URI: " + request.getRequestLine().getUri());

				request.setParams(params);
				
				httpexecutor.preProcess(request, httpproc, context);
				HttpResponse response = httpexecutor.execute(request, conn, context);
				response.setParams(params);
				httpexecutor.postProcess(response, httpproc, context);

				Header[] hs = response.getAllHeaders();
				for (Header h : hs) {
					System.out.println(h.getName() + ":" + h.getValue());
				}

				System.out.println("<< Response: " + response.getStatusLine());
				//System.out.println(EntityUtils.toString(response.getEntity()));

				String out = EntityUtils.toString(response.getEntity());
				byte[] bytes = out.getBytes("ISO-8859-1");
				String s = new String(bytes, "GB2312");
				System.out.println(s);
				
//				byte[] bytes = out.getBytes("UTF-8");
//				String s = new String(bytes, "UTF-8");
//				System.out.println(s);

				 Writer ow = new OutputStreamWriter(new FileOutputStream("data\\" + fileName), "UTF-8");
				 ow.write(out);
				 ow.close();

				System.out.println("==============");
				if (!connStrategy.keepAlive(response, context)) {
					conn.close();
				} else {
					System.out.println("Connection kept alive...");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

		//guangyaoshelian.52gdpu.cn
		String domain = "guangyaoshelian.52gdpu.cn";
		String target = "/feed.asp";
		
		new ReadRssNews().getData(domain, target, "newdata.xml");
	}

}
