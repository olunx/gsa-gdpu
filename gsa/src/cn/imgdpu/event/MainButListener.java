/*
 *@author olunx , Time:2009-3-27
 *
 *Website : http://www.olunx.com
 *
 *This: 主界面按钮事件
 */

package cn.imgdpu.event;

import java.io.IOException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import cn.imgdpu.dialog.AboutDialog;
import cn.imgdpu.dialog.OptionDialog;
import cn.imgdpu.dialog.SendFetionDialog;
import cn.imgdpu.util.AutoUpdate;

public class MainButListener implements SelectionListener {

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		String eName = e.getSource().toString().substring(e.getSource().toString().indexOf("{") + 1, e.getSource().toString().indexOf("}"));

		if (eName.equals("设置")) {

			new OptionDialog(cn.imgdpu.GSAGUI.shell).open();

		} else if (eName.equals("退出")) {

			Display display = cn.imgdpu.GSAGUI.shell.getDisplay();
			final Tray tray = display.getSystemTray();
			final TrayItem trayItem = tray.getItem(0);
			trayItem.setVisible(false);
			cn.imgdpu.GSAGUI.shell.setVisible(false);
			cn.imgdpu.GSAGUI.shell.close();
			// cn.imgdpu.GSAGUI.shell.dispose();
		} else if (eName.equals("关于") || eName.equals("关于GSA")) {
			new AboutDialog(cn.imgdpu.GSAGUI.shell).open();
		} else if (eName.equals("更新")) {
			// 更新
			new AutoUpdate().addOnceUpdate().start();

			cn.imgdpu.GSAGUI.setStatus("数据已经开始更新，预计将会在几秒钟时间内完成。");

		} else if (eName.equals("帮助") || eName.equals("使用说明")) {

			try {
				Runtime.getRuntime().exec("Explorer \"http://www.olunx.com/gsa/\"");
			} catch (IOException e1) {
				cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
			}

		} else if (eName.equals("短信")) {

			new SendFetionDialog(cn.imgdpu.GSAGUI.shell).open();

		}

	}

}
