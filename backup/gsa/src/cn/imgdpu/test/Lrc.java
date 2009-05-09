/*
 *@author olunx , 创建时间:2009-4-18
 *
 *Website : http://www.olunx.com
*/

package cn.imgdpu.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Lrc {

	protected Shell shell;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Lrc window = new Lrc();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FormLayout());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		shell.setSize(500, 375);
		shell.setText("SWT Application");
		shell.setAlpha(100);
		//
		//shell.setFullScreen(true);
		
	}

}
