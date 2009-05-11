/*
 *@author olunx , Time:2009-3-24
 *
 *Website : http://www.olunx.com
 *
 *This: 提醒事件处理
 */

package cn.imgdpu.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import com.swtdesigner.SWTResourceManager;

import cn.imgdpu.SystemTray;
import cn.imgdpu.fetion.Fetion;

public class TimerWorker extends Thread {

	/**
	 * @param args
	 */
	int year = Calendar.getInstance().get(Calendar.YEAR);
	int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
	int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	public static void main(String[] args) {
		TimerWorker w = new TimerWorker();
		w.start();
	}

	@Override
	public void run() {

		// 判断课程表是否已经设置，0未设，1已设
		if (cn.imgdpu.util.XmlProcess.isAct("bookact") == 1) {
			String[] s = readUserInfo();
			ArrayList<String> book = new ArrayList<String>();

			Calendar date = Calendar.getInstance();
			Calendar date_now = Calendar.getInstance();
			date_now.add(Calendar.DAY_OF_MONTH, Integer.parseInt(cn.imgdpu.util.XmlProcess.getCdata("ringday")));
			for (int i = 0; i < s.length && s.length > 5; i += 5) {
				if (i >= 5) {

					String[] timeStr = s[i].split("\\.");

					if (timeStr.length == 3) {

						date.set(Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]) - 1, Integer.parseInt(timeStr[2]));

						if (date_now.compareTo(date) >= 0) {
							book.add(s[i + 2]);// 书名
							book.add(s[i]);// 归还日期
						}

					}
				}
			}// end of for
			actRing(book);

			// 循环执行
			// cn.igdpu.GSAGUI.shell.getDisplay().timerExec(1000 * 60 * 30,
			// this);
		}

	}// end of run

	// 读取借书信息，返回字符串
	private String[] readUserInfo() {

		String s = cn.imgdpu.util.XmlProcess.getCdata("mybook");

		String[] split = s.split("@@");// 按星期分开

		return split;

	}

	// 执行定时提醒事件
	private void actRing(ArrayList<String> books) {
		if (books.size() == 0)
			return;
		final StringBuilder s = new StringBuilder();
		s.append("GSA提提你：\n");
		for (int i = 0; i < books.size(); i += 2) {
			s.append("《" + books.get(i).substring(0, 8) + "》将于：" + books.get(i + 1) + " 到期。\n");
		}

		if (cn.imgdpu.util.XmlProcess.isAct("icoring") == 1) {
			if (!cn.imgdpu.GSAGUI.shell.isDisposed())
				cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

					public void run() {

						cn.imgdpu.SystemTray.trayItem.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/ihigh_obj.gif"));
					}
				});
		}
		if (cn.imgdpu.util.XmlProcess.isAct("trayring") == 1) {
			if (!cn.imgdpu.GSAGUI.shell.isDisposed())
				cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						new PopupWindow(s.toString()).start();
						cn.imgdpu.SystemTray.itemBookMenu.setEnabled(true);
					}
				});
		}
		if (cn.imgdpu.util.XmlProcess.isAct("midring") == 1) {

		}
		if (cn.imgdpu.util.XmlProcess.isAct("smsring") == 1) {

			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

				public void run() {
					if (cn.imgdpu.util.XmlProcess.isAct("fetionact") == 1) {
						String s = cn.imgdpu.util.XmlProcess.getCdata("fetion");

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

							new Fetion(split[0], split[1], s.toString()).start();
						}
					}
				}
			});
		}// end of smsring

	}

}
