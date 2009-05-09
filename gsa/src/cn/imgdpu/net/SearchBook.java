/*
 *@author fatkun , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This: 搜索书籍
 */

package cn.imgdpu.net;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchBook {
	String word1 = "", word2 = "", word3 = "", pageno;
	String oldStr;

	public SearchBook(String word1Par, String word2Par, String word3Par, String pagenoPar) {
		word1 = word1Par;
		word2 = word2Par;
		word3 = word3Par;
		pageno = pagenoPar;
	}

	public ArrayList<String> getSearchBook() {

		String domain = "opac.lib.gdpu.edu.cn";
		String path = "/cgi-win/s3trs.exe?w1=" + word1 + "&w2=" + word2 + "&w3=" + word3 + "&pageno=" + pageno + "";
		GetDataThread getDataRun = new GetDataThread(domain, path);
		getDataRun.encode = "gb2312";// 设置编码（默认gb2312）
		Thread thread = new Thread(getDataRun);
		thread.start();
		while (thread.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}

		return doSearchBook(getDataRun.htmlData);
	}

	public ArrayList<String> doSearchBook(String s) {

		ArrayList<String> books = new ArrayList<String>();

		Pattern pattern = Pattern.compile("总页数: ([\\d]+)页\\| 当前页: No\\.([\\d]+) ");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {

			books.add(matcher.group(1));// 总页数
			books.add(matcher.group(2));// 当前页

		} else {
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}
		// <tr><td>1</td><td><a href=/cgi-win/tcgid.exe?s557r50>大学英语四级考试题集
		// [汇编]／邢贵,韩志明主编</a></td><td>1995.3</td><td>H31-44/9503</td>

		boolean matchFlag = false;
		pattern = Pattern.compile("<tr><td>(\\d+)</td><td><a href=([^>]*?)>(.*?)</a></td><td>(.*?)</td><td>(.*?)</td>");
		matcher = pattern.matcher(s);
		while (matcher.find()) {

			books.add(matcher.group(1));// 序号
			books.add(matcher.group(2));// 链接
			books.add(matcher.group(3));// 书名 / 作者
			books.add(matcher.group(4));// 出版年
			books.add(matcher.group(5));// 索取号
			books.add(idToFloor(matcher.group(5)));
			matchFlag = true;
		}
		if (!matchFlag) {
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}

		return books;
	}

	public String idToFloor(String id) {
		String result = "";
		String idStart = id.substring(0, 1);
		if ("ABCDEFGH".indexOf(idStart) != -1)
			result = "三楼电梯旁";
		else if ("IJK".indexOf(idStart) != -1)
			result = "四楼电梯旁";
		else if ("R".indexOf(idStart) != -1)
			result = "五楼电梯旁";
		else if ("LMNOPQSTUVWXYZ".indexOf(idStart) != -1)
			result = "五楼左边";
		else
			result = "-";
		if (result == oldStr)
			return "↑";
		oldStr = result;
		return result;
	}

}
