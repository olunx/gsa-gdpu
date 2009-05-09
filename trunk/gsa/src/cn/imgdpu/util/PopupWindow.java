/*
 *@author olunx , Time:2009-3-28
 *
 *Website : http://www.olunx.com
 *
 *This: 弹出窗口
 */

package cn.imgdpu.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PopupWindow extends Thread {

	public static Shell shell;

	protected int moveStep = 6; // 每次移动的pixel
	protected int upPosition; // 能移动到的最上面坐标
	protected int downPosition; // 当前popup的边框坐标
	protected int leftPosition; // popup左边边框坐标

	public PopupWindow(final String message) {

		shell = new Shell(SWT.ON_TOP);

		final Label closeBtn = new Label(shell, SWT.NONE);
		closeBtn.setAlignment(SWT.CENTER);
		closeBtn.setText("X");
		closeBtn.setBounds(232, 4, 15, 15);
		closeBtn.addMouseListener(new MouseAdapter() {
			public void mouseDown(final MouseEvent arg0) {
				shell.close();
			}
		});
		Text text = new Text(shell, SWT.MULTI | SWT.WRAP);
		text.setBounds(10, 10, 228, 128);
		text.setBackground(shell.getBackground());
		text.setText(message);

		// 取屏幕分辨率大小
		Rectangle area = Display.getDefault().getClientArea();

		upPosition = area.height - 150;// 计算出popup界面在屏幕显示的最高位置
		downPosition = area.height + 150;// 计算出popup界面的初始位置
		leftPosition = area.width - 252;

		shell.setSize(250, 150);

		// 初始化popup位置
		shell.setLocation(leftPosition, downPosition);
		shell.open();
	}

	public void run() {

		Display display = shell.getDisplay();
		boolean canstop = false;

		//flags用来控制线程的关闭
		while (!canstop) {
			try {
				Thread.sleep(10);

				// 判断当前位置是否小于能出现的最高位置，小于的话就说明还可以向上移动。
				if ((downPosition - moveStep) > upPosition) {
					display.asyncExec(new Runnable() {
						public void run() {
							if (!shell.isDisposed())
								shell.setLocation(leftPosition, downPosition - moveStep);
							downPosition -= moveStep;
						}
					});
				} else {
					canstop = true;
					// 此时已经移动到了最高位置
				}
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}
		try {
			sleep(1000 * 15);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.asyncExec(new Runnable() {
			public void run() {
				System.out.print(" run in?");
				shell.close();
			}
		});
	}
}