/**
 *@author fatkun , 创建时间:2009-4-27
 *
 *This:
 */

package cn.imgdpu.util;

import cn.imgdpu.net.ReadRssNews;

public class UpdateCampusNews extends Thread {
	String fileName = "newscampus.xml";

	public void run() {

		// 定义获取rss的来源地址
		String domain = "fat.90wap.com";
		String target = "/gsa/rss.asp";

		// 开始获取数据，并保存到本地
		new ReadRssNews().getData(domain, target, fileName);

		// 读取本地数据并显示
		if (cn.imgdpu.event.TabFolderListener.campusNewsCompo != null)
			if (!cn.imgdpu.GSAGUI.shell.isDisposed())
				cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						cn.imgdpu.event.TabFolderListener.campusNewsCompo.readRssData();
					}
				});

		cn.imgdpu.GSAGUI.setStatusAsyn("更新校园新闻成功了！O(∩_∩)O");
	}
}
