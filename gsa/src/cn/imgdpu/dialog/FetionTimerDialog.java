/*
 *@author olunx , Time:2009-5-3
 *
 *Website : http://www.olunx.com
 *
 *This : 
 *
 */

package cn.imgdpu.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class FetionTimerDialog extends Dialog {

	private Table table;
	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public FetionTimerDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public FetionTimerDialog(Shell parent) {
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/local.gif"));
		shell.setLayout(new FormLayout());
		GeneralMethod.getGeneralMethod().setDisLoc(shell, 480, 320);// 设置显示位置
		shell.setSize(480, 320);
		shell.setText("定时发送管理");

		final ToolBar toolBar = new ToolBar(shell, SWT.NONE);
		final FormData fd_toolBar = new FormData();
		fd_toolBar.right = new FormAttachment(100, -5);
		fd_toolBar.top = new FormAttachment(0, 5);
		fd_toolBar.left = new FormAttachment(0, 5);
		toolBar.setLayoutData(fd_toolBar);

		final ToolItem addBut = new ToolItem(toolBar, SWT.PUSH);
		addBut.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/add_action.gif"));
		addBut.setText("添加");

		final ToolItem allBut = new ToolItem(toolBar, SWT.PUSH);
		allBut.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/open_action.gif"));
		allBut.setText("全选");

		final ToolItem refBut = new ToolItem(toolBar, SWT.PUSH);
		refBut.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/refresh.gif"));
		refBut.setText("刷新");

		final ToolItem delBut = new ToolItem(toolBar, SWT.PUSH);
		delBut.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/delete_edit.gif"));
		delBut.setText("删除");

		table = new Table(shell, SWT.FULL_SELECTION | SWT.BORDER);
		fd_toolBar.bottom = new FormAttachment(table, -5, SWT.TOP);
		final FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 50);
		fd_table.bottom = new FormAttachment(0, 250);
		fd_table.right = new FormAttachment(100, -5);
		fd_table.left = new FormAttachment(toolBar, 0, SWT.LEFT);
		table.setLayoutData(fd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		final TableColumn checkColumn = new TableColumn(table, SWT.NONE);
		checkColumn.setWidth(45);
		checkColumn.setText("选中");

		final TableColumn timeColumn = new TableColumn(table, SWT.NONE);
		timeColumn.setWidth(147);
		timeColumn.setText("时间");

		final TableColumn content = new TableColumn(table, SWT.NONE);
		content.setWidth(252);
		content.setText("内容");

		final TableItem newItemTableItem = new TableItem(table, SWT.BORDER);
		newItemTableItem.setText(2, "参加编程比赛");
		newItemTableItem.setText(1, "2009-05-07 18:00");

		final CLabel warnLab = new CLabel(shell, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(FetionTimerDialog.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -5);
		fd_warnLab.right = new FormAttachment(100, -5);
		fd_warnLab.top = new FormAttachment(table, 5, SWT.BOTTOM);
		fd_warnLab.left = new FormAttachment(table, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("自动发送短信！O(∩_∩)O");
		//

		// 设置swtItem的高度
		table.addListener(SWT.MeasureItem, new cn.imgdpu.event.SwtTableItemHeightListener(table));
	}

}
