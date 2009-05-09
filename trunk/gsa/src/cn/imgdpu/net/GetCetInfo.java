/*
 *@author fatkun , Time:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This: 四六级查询
 */

package cn.imgdpu.net;

import java.util.ArrayList;

public class GetCetInfo extends Thread {

	String cetType = "4";// 四级或六级
	String cetID, cetIDNear;
	String classroom;
	String name;
	GetCetInfoThread mythread;

	public GetCetInfo(String _cetType, String _cetID) {
		cetType = _cetType;
		cetID = _cetID;

	}

	public GetCetInfo(String _cetType, String _cetID, String _classroom, String _name) {
		cetType = _cetType;
		cetIDNear = _cetID;
		classroom = _classroom;
		name = _name;
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
		String[] params = {};
		String url = "http://cet.99sushe.com/cetscore_99sushe0902.html?t=" + cetType + "&id=" + cetID;
		PostDataThread postDataRun = new PostDataThread(url, params);
		postDataRun.encode = "utf-8";
		postDataRun.referer = "http://cet.99sushe.com/";
		Thread postDataThread = new Thread(postDataRun);
		postDataThread.start();
		while (postDataThread.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}

		String result = postDataRun.htmlData;
		if (result.equals(""))
			return cetInfo;
		for (String s : result.split(",")) {
			cetInfo.add(s);
		}
		return cetInfo;
	}

	String getNum(int num) {
		if (num < 10)
			return "0" + String.valueOf(num);
		else
			return String.valueOf(num);
	}

	public GetCetInfoThread getCetInfoNear(GetCetInfo _cetInfo) {
		mythread = new GetCetInfoThread();
		// mythread.getCetInfoPar = _cetInfo;
		mythread.cancel = false;
		mythread.start();
		return mythread;
	}

	public void stopGetCetInfoNear() {
		mythread.cancel = true;
		// thread.interrupt();
	}

	class GetCetInfoThread extends Thread {
		public boolean cancel = false;

		public void run() {
			ArrayList<String> cetInfo = new ArrayList<String>();
			String cetIDStart = cetIDNear.substring(0, 10);
			String sName = null;
			int count = 1;
			do {
				if (cancel)
					break;// 如果取消就退出循环
				cetID = cetIDStart + classroom + getNum(count);
				cn.imgdpu.GSAGUI.setStatusAsyn("正在查询准考证:" + cetID);

				cetInfo = getCetInfo();// 查询
				if (cetInfo.size() > 1)
					sName = cetInfo.get(6);

				if (cetInfo.size() > 1 && sName.equals(name)) {
					break;
				}
				count++;
			} while (count < 31);
			final ArrayList<String> cetInfo2 = new ArrayList<String>(cetInfo);
			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					cn.imgdpu.compo.CetCompo.setLabText(cetInfo2);
				}
			});

		}

	}

}
