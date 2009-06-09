/**
 *@author fatkun , 创建时间:2009-4-28
 *
 *This:定时自动更新(testsvn)
 */
package cn.imgdpu.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.imgdpu.net.GetMyBookInfo;

class AutoUpdateArr {
	Thread thread;
	Calendar date;
	int interval = 0;
}

public class AutoUpdate extends Thread {
	public boolean cancel = false;
	List<AutoUpdateArr> updateList = null;

	// firstRunTime是现对于现在延时几秒运行,nextRunTime是运行后间隔几秒再次运行
	public void addAutoUpdate(Thread thread, int firstRunTime, int nextRunTime) {
		if (updateList == null)
			updateList = new ArrayList<AutoUpdateArr>();

		AutoUpdateArr autoUpdateArr = new AutoUpdateArr();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, firstRunTime);

		autoUpdateArr.thread = thread;
		autoUpdateArr.date = cal;
		autoUpdateArr.interval = nextRunTime;
		updateList.add(autoUpdateArr);
	}

	// 部分定时更新
	public AutoUpdate addStartAutoUpdate() {
		if (cn.imgdpu.util.XmlProcess.isAct("bookact") == 1) {
			addAutoUpdate(new GetMyBookInfo(), 0, 0);// 借书
		}
		addAutoUpdate(new UpdateWeather(), 2, 60 * 30);// 天气
		addAutoUpdate(new TimerWorker(), 3, 0);// 借书提醒
		addAutoUpdate(new UpdateCampusNews(), 1, 60 * 60);// 广药新闻
		return this;
	}

	// 只更新一次("更新"按钮)
	public AutoUpdate addOnceUpdate() {
		if (cn.imgdpu.util.XmlProcess.isAct("bookact") == 1) {
			addAutoUpdate(new GetMyBookInfo(), 0, 0);// 借书
		}
		addAutoUpdate(new UpdateWeather(), 1, 0);// 天气
		// addAutoUpdate(new TimerWorker(), 3, 0);//借书提醒
		addAutoUpdate(new UpdateCampusNews(), 5, 0);// 广药新闻
		return this;
	}

	@Override
	public void run() {
		Thread lastThread = null;
		while (!cancel) {
			try {
				if (!updateList.isEmpty()) {
					for (int i = 0; i < updateList.size(); i++) {
						AutoUpdateArr element = updateList.get(i);
						if (Calendar.getInstance().compareTo(element.date) >= 0) {// 如果现在时间比参数的大
							lastThread = new Thread(element.thread);
							lastThread.start();
							if (element.interval == 0)// 循环时间是否为零(零则删除,非零则修改下次执行时间)
							{
								lastThread = null;
								updateList.remove(i);
							} else {
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.SECOND, element.interval);
								element.date = cal;
							}
						}
					}
				}
				sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		if (cancel) {
			if (lastThread != null && lastThread.isAlive()) {
				lastThread.interrupt();
			}
		}
		// System.out.println("自动运行线程结束");
	}

}
