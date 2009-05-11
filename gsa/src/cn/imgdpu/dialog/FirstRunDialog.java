/*
 *@author olunx , Time:2009-3-23
 *
 *Website : http://www.olunx.com
 *
 *This: 欢迎对话框
 */

package cn.imgdpu.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class FirstRunDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public FirstRunDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public FirstRunDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.CLOSE);
		shell.setLayout(new FormLayout());
		shell.setImage(SWTResourceManager.getImage(FirstRunDialog.class, "/cn/imgdpu/ico/local.gif"));
		GeneralMethod.getGeneralMethod().setDisLoc(shell, 370, 427);// 设置显示位置
		shell.setSize(370, 427);
		shell.setText("GSA Welcome");

		final Group group = new Group(shell, SWT.NONE);
		final FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(100, -5);
		fd_group.right = new FormAttachment(100, -5);
		fd_group.top = new FormAttachment(0, 5);
		fd_group.left = new FormAttachment(0, 5);
		group.setLayoutData(fd_group);
		group.setLayout(new FormLayout());

		final CLabel label = new CLabel(group, SWT.CENTER);
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(100, -5);
		fd_label.left = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("欢迎使用 GSA\n首次使用，请填写相关信息，以方便日后的使用！O(∩_∩)O");

		Button startBut;
		startBut = new Button(group, SWT.NONE);
		fd_label.top = new FormAttachment(startBut, -45, SWT.TOP);
		fd_label.bottom = new FormAttachment(startBut, -5, SWT.TOP);
		startBut.setImage(SWTResourceManager.getImage(FirstRunDialog.class, "/cn/imgdpu/ico/home_nav.gif"));
		final FormData fd_startBut = new FormData();
		fd_startBut.right = new FormAttachment(0, 265);
		fd_startBut.left = new FormAttachment(0, 70);
		fd_startBut.top = new FormAttachment(100, -43);
		fd_startBut.bottom = new FormAttachment(100, -5);
		startBut.setLayoutData(fd_startBut);
		startBut.setText("马上开始");
		//

		startBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				shell.setVisible(false);
				new OptionDialog(shell).open();
				shell.dispose();
			}

		});

		final CLabel label_1 = new CLabel(group, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage(FirstRunDialog.class, "/cn/imgdpu/ico/school256.png"));
		final FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(label, -277, SWT.TOP);
		fd_label_1.bottom = new FormAttachment(label, 0, SWT.TOP);
		fd_label_1.right = new FormAttachment(0, 310);
		fd_label_1.left = new FormAttachment(0, 45);
		label_1.setLayoutData(fd_label_1);
	}

}
