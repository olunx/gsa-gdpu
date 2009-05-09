/*
 *@author fatkun , Time:2009-3-27
 *
 *Website : http://www.olunx.com
 *
 *This: 查询广东四六级成绩
 */

package cn.imgdpu.net;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetGdCetInfo extends Thread {
	String cetID;
	
	public GetGdCetInfo(String _cetID){
		cetID = _cetID;
	}
	
	@Override
	public void run() {
		ArrayList<String> myCetInfo = getCetInfo();

		final ArrayList<String> myCetInfo2 = new ArrayList<String>(myCetInfo);
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				cn.imgdpu.compo.CetCompo.setLabText(myCetInfo2);
			}
		});
	}
	
	public ArrayList<String> getCetInfo() {
		ArrayList<String> cetInfo = new ArrayList<String>();
		String[] par = { "permitcardid="+cetID, "verifyCode=aaaa", "examprogid=807" };
		String url = "http://service.eesc.com.cn/examscore/examscore.do?act=resultlist";

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

		Pattern pattern = Pattern.compile("准考证号码：(.*?)<p>[\\s\\S]+?&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;名：(.*?)<p>[\\s\\S]+?<td width=\"20%\" align=\"center\">(.*?)</td>[\\s\\S]+?<td width=\"20%\" align=\"center\">(.*?)</td>[\\s\\S]+?<td width=\"20%\" align=\"center\">(.*?)</td>[\\s\\S]+?<td width=\"20%\" align=\"center\">(.*?)</td>[\\s\\S]+?<td width=\"20%\" align=\"center\">(.*?)</td>");
		Matcher matcher = pattern.matcher(htmlData);
		if (matcher.find()){
			cetInfo.add(matcher.group(5));//听力
			cetInfo.add(matcher.group(6));//阅读
			cetInfo.add(matcher.group(4));//综合
			cetInfo.add(matcher.group(7));//写作
			cetInfo.add(matcher.group(3));//总分
			cetInfo.add("");//学校
			cetInfo.add(matcher.group(2));//姓名
			cetInfo.add(matcher.group(1));//准考证
		}else{
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}
		
		return cetInfo;
	}
	
}
