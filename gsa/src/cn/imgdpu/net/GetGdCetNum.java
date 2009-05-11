/*
 *@author fatkun , Time:2009-3-27
 *
 *Website : http://www.olunx.com
 *
 *This: 获取广东四六级考号
 */

package cn.imgdpu.net;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.imgdpu.net.PostDataThread;

public class GetGdCetNum extends Thread {
	String examid, permitcardid, idcard;

	public GetGdCetNum(String examid, String permitcardid, String idcard) {
		this.examid = examid;
		this.permitcardid = permitcardid;
		this.idcard = idcard;
	}

	@Override
	public void run() {
		getCetNum();
	}

	public void getCetNum() {
		String[] par = { "examid=" + examid, "permitcardid=" + permitcardid, "idcard=" + idcard, "verifyCode=aaaa", "examprogid=782" };
		String url = "http://service.eesc.com.cn/english/cetInfo.do";

		cn.imgdpu.GSAGUI.setStatusAsyn("开始查询准考证...");

		PostDataThread postDataRun = new PostDataThread(url, par);
		postDataRun.encode = "utf-8";
		Thread postDataThread = new Thread(postDataRun);
		postDataThread.start();
		while (postDataThread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}

		String htmlData = postDataRun.htmlData;

		Pattern pattern = Pattern
				.compile("<strong> 查询结果：</strong>[\\s\\S]+?<tr bgcolor=\"#FFFFFF\">[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>[\\s\\S]+?<td>(.*?)</td>");
		Matcher matcher = pattern.matcher(htmlData);
		if (matcher.find()) {

			cn.imgdpu.GSAGUI.setStatusAsyn("查询准考证成功！开始查询成绩...");
			new GetGdCetInfo(matcher.group(2)).start();
		} else {

			final ArrayList<String> myCetInfo2 = new ArrayList<String>();
			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					cn.imgdpu.compo.CetCompo.setLabText(myCetInfo2);
				}
			});
		}
	}

}
