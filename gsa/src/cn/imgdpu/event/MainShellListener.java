/*
 *@author olunx , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This:主界面事件监听处理
 */

package cn.imgdpu.event;

import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class MainShellListener implements ShellListener {

	Shell shell;

	public static boolean flags = true;

	public MainShellListener(Shell parentShell) {
		shell = parentShell;
	}

	@Override
	public void shellActivated(ShellEvent arg0) {

	}

	@Override
	public void shellClosed(ShellEvent e) {
		// 关闭提醒线程
		flags = false;

		// 下面处理，退出时清空托盘图标
		Display display = shell.getDisplay();
		final Tray tray = display.getSystemTray();
		final TrayItem trayItem = tray.getItem(0);
		trayItem.setVisible(false);

		//关闭更新
		cn.imgdpu.GSAGUI.autoUpdate.cancel = true;
		
		if(cn.imgdpu.util.PopupWindow.shell != null) {
			if (!cn.imgdpu.util.PopupWindow.shell.isDisposed())
				cn.imgdpu.util.PopupWindow.shell.close();
		}
		
		cn.imgdpu.compo.FtpFilesUpdateCompo.stopThread();// 停止FTP列文件
	}

	@Override
	public void shellDeactivated(ShellEvent arg0) {
	}

	@Override
	public void shellDeiconified(ShellEvent arg0) {
	}

	@Override
	public void shellIconified(ShellEvent arg0) {

		// 最小化时不显示在任务栏
		shell.setVisible(false);

	}

}
