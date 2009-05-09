/*
 *@author olunx , Time:2009-3-6
 *
 *Website: http://www.olunx.com
 *
 *This: 读取rss新闻
 */

package cn.imgdpu.net;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class ReadRssNews {
	
	public String encode = "UTF-8";

	public void getData(String domain, String target, String fileName) {

		PostDataThread post = new PostDataThread(domain, target, new String[] {});
		post.encode = encode;
		Thread t = new Thread(post);
		t.start();

		while (t.isAlive());

		String out = post.htmlData;

		out = out.replaceFirst("encoding=\"gbk\"", "encoding=\"utf-8\"");
		out = out.replaceAll("pubdate", "pubDate");
		
		try {

			Writer ow = new OutputStreamWriter(new FileOutputStream("data\\" + fileName), "UTF-8");
			ow.write(out);
			ow.close();
		} catch (UnsupportedEncodingException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "找不到数据文件");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

	}

	public static void main(String[] args) throws Exception {

		String domain = "bbs.gzuc.net";
		String target = "/rss.php?fid=59";

		ReadRssNews rn = new ReadRssNews();
		rn.encode = "GBK";
		rn.getData(domain, target, "newdata.xml");
	}

}
