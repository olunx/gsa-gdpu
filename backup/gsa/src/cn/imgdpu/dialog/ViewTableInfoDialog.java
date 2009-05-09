/*
 *@author olunx , Time:2009-3-16
 *
 *Website : http://www.olunx.com
 *
 *This: 查看课程表详细信息对话框
 */
package cn.imgdpu.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cn.imgdpu.compo.ViewTableInfoCompo;
import cn.imgdpu.util.GeneralMethod;

public class ViewTableInfoDialog extends Dialog {

	protected Object result;
	protected static Shell shell;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public ViewTableInfoDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public ViewTableInfoDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
	 * @return the result
	 * itemNo参数为要显示的班级item
	 */
	public Object open(int itemNo) {
		createContents(itemNo);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}
	public static void closeShell(){
		shell.dispose();
	}
	
	/**
	 * Create contents of the dialog
	 * itemNo参数为要显示的班级item
	 */
	protected void createContents(int itemNo) {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new FillLayout());
		GeneralMethod.getGeneralMethod().setDisLoc(shell , 680 , 280);//设置显示位置
		shell.setSize(680, 280);
		shell.setText("详细课表");

		new ViewTableInfoCompo(shell, SWT.NONE).setText(itemNo);
		//
	}

}
