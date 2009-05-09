/*
 *@author fatkun , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This:借书状态
 */

package cn.imgdpu.net;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetBookInfo extends Thread {
	@Override
	public void run() {
		String[] params = {};
		PostDataThread postDataRun = new PostDataThread("opac.lib.gdpu.edu.cn", url, params);
		Thread postDataThread = new Thread(postDataRun);
		postDataThread.start();
		while (postDataThread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}
		String s = postDataRun.htmlData;

		boolean matchFlag = false;
		Pattern pattern = Pattern.compile("<tr><td>(.*?)</td><td.*?<td.*?<td.*?<td.*?<td align=\"center\">(.*?)</td>");
		Matcher matcher = pattern.matcher(s);
		ArrayList<String> book = new ArrayList<String>();
		while (matcher.find()) {
			book.add(matcher.group(1));// 收藏单位
			book.add(matcher.group(2));// 状态
			matchFlag = true;
		}
		if (!matchFlag) {
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}
		final ArrayList<String> book2 = new ArrayList<String>(book);
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				try {
					cn.imgdpu.dialog.BookInfoDialog.setBookInfo(book2);
				} catch (org.eclipse.swt.SWTException e) {
					cn.imgdpu.util.CatException.getMethod().catException(e, "SWT异常");
				}

			}

		});

	}

	String url;

	public GetBookInfo(String urlPar) {
		url = urlPar;
	}

	public static void main(String[] args) {
		GetBookInfo getBookInfo = new GetBookInfo("/cgi-win/tcgid.exe?s49273r50");
		getBookInfo.start();
	}

}
