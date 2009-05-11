/*
 *@author fatkun , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This:提交到网页的处理
 */

package cn.imgdpu.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PostDataThread implements Runnable {
	public String url;
	public String[] sendData;
	public String encode = "gb2312";// 编码
	public String referer = "";// 来源
	public String htmlData;// 返回源码变量

	public PostDataThread(String _url, String[] _sendData) {
		url = _url;
		sendData = _sendData;
	}

	public PostDataThread(String _domain, String _path, String[] _sendData) {
		url = "http://" + _domain + _path;
		sendData = _sendData;
	}

	@Override
	public void run() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);

		// 添加需要POST的数据到nvps
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (int i = 0; i < sendData.length; i++) {
			if (sendData[i].split("=").length < 2)
				continue;
			nvps.add(new BasicNameValuePair(sendData[i].split("=")[0], sendData[i].split("=")[1]));
		}

		StringEntity myen;
		try {
			myen = new UrlEncodedFormEntity(nvps, encode);// 设置POST的编码并URLENCODE
			httpost.setEntity(myen);// 设置POST内容
			httpost.setHeader("referer", referer);

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			String out = EntityUtils.toString(entity);

			if (encode.toLowerCase() == "null" || encode.equalsIgnoreCase("utf-8"))
				htmlData = out;
			else
				htmlData = new String(out.getBytes(HTTP.ISO_8859_1), encode);// 编码

			if (entity != null) {
				entity.consumeContent();
			}

		} catch (UnsupportedEncodingException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
		} catch (ParseException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "未知异常");
		} catch (java.net.ConnectException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "网络连接超时");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}
	}

}
