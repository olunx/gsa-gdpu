/*
 *@author olunx , Time:2009-3-17
 *
 *Website : http://www.olunx.com
 *
 *This: 得到借书信息
 */

package cn.imgdpu.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import cn.imgdpu.util.Base64;

public class GetMyBookInfo extends Thread {

	public void run() {
		cn.imgdpu.GSAGUI.setStatusAsyn("开始更新借书信息...");

		String[] user = readUserInfo();

		try {
			ArrayList<String> info = new MyBookInfo(user[0], user[1]).getMyBook();

			// 如果第二元素为空，则证明密码错误
			if (info.get(1).equals("")) {

				cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

					public void run() {
						MessageBox box = new MessageBox(cn.imgdpu.GSAGUI.shell, SWT.ICON_WARNING | SWT.OK);
						box.setText("提示");
						box.setMessage("学号或密码错误!");
						box.open();
					}
				});

			} else {
				String s = new String();

				for (int i = 0; i < info.size(); i++) {
					s += info.get(i) + "@@";
				}

				cn.imgdpu.util.XmlProcess.setCdata("mybook", s);
				if (!cn.imgdpu.GSAGUI.shell.isDisposed())
					cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							cn.imgdpu.compo.BookStatusCompo.setBookInfo();
							cn.imgdpu.GSAGUI.setStatus("借书信息更新成功！");
						}

					});

				/*
				 * cn.igdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable(){
				 * public void run() { MessageBox box = new
				 * MessageBox(cn.igdpu.GSAGUI.shell, SWT.ICON_WORKING | SWT.OK);
				 * box.setText("提示"); box.setMessage("[借书状态]信息读取成功！");
				 * box.open(); }});
				 */
			}
		} catch (NullPointerException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "空指针异常，获取数据失败。");
		}

	}

	// 读取学号密码，返回字符串
	public String[] readUserInfo() {

		String s = cn.imgdpu.util.XmlProcess.getCdata("userinfo");

		String[] split = s.split("#");// 按星期分开

		if (split.length > 1) {
			// 解密
			byte[] result;
			try {
				result = split[1].getBytes("UTF-8");
				split[1] = new String(Base64.decode(new String(result, "UTF-8")), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
			}
		}

		return split;

	}

}

class MyBookInfo {

	String cardno, pass;

	public MyBookInfo(String cardnoPar, String passPar) {
		cardno = cardnoPar;
		pass = passPar;
	}

	public ArrayList<String> getMyBook() {

		String[] params = { "cardno=" + cardno, "pass=" + pass, "query=query" };
		PostDataThread postDataRun = new PostDataThread("opac.lib.gdpu.edu.cn", "/cgi-win/service.exe", params);
		Thread postDataThread = new Thread(postDataRun);
		postDataThread.start();
		while (postDataThread.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}

		return doMyBook(postDataRun.htmlData);
	}

	public ArrayList<String> doMyBook(String s) {

		ArrayList<String> info = new ArrayList<String>();

		boolean matchFlag = false;

		Pattern pattern = Pattern.compile("证号：<u>(.*?)</u>.*?姓名：<u>(.*?)</u>.*?类型：<u>(.*?)</u>.*?单位：<u>(.*?)</u>.*?当前状态：<u>(.*?)</u>");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			info.add(matcher.group(1));// 学号
			info.add(matcher.group(2));// 名字
			info.add(matcher.group(3));// 类型：学生
			info.add(matcher.group(4));// 班级
			info.add(matcher.group(5));// 账号状态：正常
			matchFlag = true;
		} else {
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}

		pattern = Pattern
				.compile("<td width=\"36\" align=\"center\">.*?<td width=\"96\" align=\"center\">&nbsp;(<font color=\"#ff0000\">)?([\\d\\.]+?)(</font>)?&nbsp;<td><a href=\"(.*?)\">(.*?)</a>.*?<td width=\"96\" align=\"center\">(.*?)<td width=\"96\" align=\"center\">(.*?)<td width=\"96\" align=\"center\">([^<]*?)");
		matcher = pattern.matcher(s);
		while (matcher.find()) {
			info.add(matcher.group(2));// 最迟应还期
			info.add(matcher.group(4));// 链接"/cgi-win/tcgid.exe?s163299r20"
			info.add(matcher.group(5));// 题名／著者
			info.add(matcher.group(6));// 图书类型
			info.add(matcher.group(7));// 登录号
		}
		if (!matchFlag) {
			cn.imgdpu.GSAGUI.setStatusAsyn("没有数据");
		}

		return info;
	}

}
