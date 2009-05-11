/*
 *@author olunx , Time:2009-3-277
 *
 *Website : http://www.olunx.com
 *
 *This: 系统托盘事件
 */

package cn.imgdpu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import cn.imgdpu.dialog.AboutDialog;
import cn.imgdpu.dialog.OptionDialog;
import cn.imgdpu.event.MainShellListener;
import cn.imgdpu.util.TimerWorker;

import com.swtdesigner.SWTResourceManager;

public class SystemTray {

	public static TrayItem trayItem;
	public static MenuItem itemBookMenu;

	static Shell shell = cn.imgdpu.GSAGUI.shell;

	public static void createSystemTray() {
		// 下面两句的效果是：在任务栏不显示
		shell.addShellListener(new MainShellListener(shell));

		Display display = shell.getDisplay();
		final Tray tray = display.getSystemTray();
		trayItem = new TrayItem(tray, SWT.NONE);
		trayItem.setToolTipText("GSA(广药学生助手)\n单击 最小化/还原");

		// 托盘显示事件
		trayItem.addListener(SWT.Show, new TrayItemListener());
		// 托盘隐藏时间
		trayItem.addListener(SWT.Hide, new TrayItemListener());
		// 托盘单击事件
		trayItem.addListener(SWT.Selection, new TrayItemListener());
		// 托盘双击事件
		trayItem.addListener(SWT.DefaultSelection, new TrayItemListener());

		// 添加托盘右键菜单
		final Menu trayMenu = new Menu(shell, SWT.POP_UP);

		MenuItem menuItemMain = new MenuItem(trayMenu, SWT.PUSH);
		menuItemMain.setText("主界面");
		menuItemMain.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/home_nav.gif"));
		menuItemMain.addSelectionListener(new MenuItemListener());

		// 查看提醒
		itemBookMenu = new MenuItem(trayMenu, SWT.CASCADE);
		itemBookMenu.setText("查看提醒");
		itemBookMenu.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/info.gif"));
		itemBookMenu.setEnabled(false);
		itemBookMenu.addSelectionListener(new MenuItemListener());

		new MenuItem(trayMenu, SWT.SEPARATOR);// 分割条

		MenuItem menuItemOption = new MenuItem(trayMenu, SWT.PUSH);// 设置
		menuItemOption.setText("详细设置");
		menuItemOption.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/configure.gif"));
		menuItemOption.addSelectionListener(new MenuItemListener());

		MenuItem menuItemAbout = new MenuItem(trayMenu, SWT.PUSH);// 关于
		menuItemAbout.setText("关于GSA");
		menuItemAbout.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/index_co.gif"));
		menuItemAbout.addSelectionListener(new MenuItemListener());

		new MenuItem(trayMenu, SWT.SEPARATOR);// 分割条

		MenuItem menuItemMini = new MenuItem(trayMenu, SWT.PUSH);// 最小化菜单
		menuItemMini.setText("最小化");
		menuItemMini.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/folder.gif"));
		menuItemMini.addSelectionListener(new MenuItemListener());

		MenuItem menuItemClose = new MenuItem(trayMenu, SWT.PUSH);// 关闭菜单
		menuItemClose.setText("退出");
		menuItemClose.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/run_exec.gif"));
		menuItemClose.addSelectionListener(new MenuItemListener());

		// 单击托盘右键
		trayItem.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				trayMenu.setVisible(true);
			}
		});

		trayItem.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/local.gif"));

	}

}

class TrayItemListener implements Listener {

	Shell shell = cn.imgdpu.GSAGUI.shell;

	public void handleEvent(Event e) {

		if (e.type == SWT.Selection) {

			if (shell.isVisible()) {
				shell.setVisible(false);
			} else {
				shell.setVisible(true);
				shell.setMinimized(false);
				cn.imgdpu.SystemTray.trayItem.setImage(SWTResourceManager.getImage(SystemTray.class, "ico/local.gif"));
			}
		}

	}

}

class MenuItemListener implements SelectionListener {

	Shell shell = cn.imgdpu.GSAGUI.shell;

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		String eventName = e.getSource().toString().substring(e.getSource().toString().indexOf("{") + 1, e.getSource().toString().indexOf("}"));

		if (eventName.equals("最小化")) {

			shell.setMinimized(true);

		} else if (eventName.equals("退出")) {

			// 下面处理，退出时清空托盘图标
			Display display = shell.getDisplay();
			final Tray tray = display.getSystemTray();
			final TrayItem trayItem = tray.getItem(0);
			trayItem.setVisible(false);

			shell.setVisible(false);
			shell.close();
		} else if (eventName.equals("主界面")) {
			shell.setVisible(true);
			shell.setMinimized(false);
		} else if (eventName.equals("详细设置")) {
			new OptionDialog(shell).open();
		} else if (eventName.equals("关于GSA")) {
			new AboutDialog(cn.imgdpu.GSAGUI.shell).open();
		} else if (eventName.equals("查看提醒")) {
			cn.imgdpu.GSAGUI.shell.getDisplay().timerExec(0, new TimerWorker());
		}

	}

}
