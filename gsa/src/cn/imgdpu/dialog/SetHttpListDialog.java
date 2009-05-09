/*
 *@author olunx , Time:2009-4-12
 *
 *Website : http://www.olunx.com
 *
 *This: 设置HTTP网址对话框
 */

package cn.imgdpu.dialog;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.net.ReadHtmlFiles;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class SetHttpListDialog extends Dialog {

	private StyledText urlSText;
	private StyledText nameSText;
	private Table listTable;
	protected Object result;
	protected Shell shell;
	Button addBut, delBut, updateBut;

	protected CLabel warnLab;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public SetHttpListDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public SetHttpListDialog(Shell parent) {
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
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.CLOSE);
		shell.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/checkbox_table_viewer.gif"));
		shell.setLayout(new FormLayout());
		GeneralMethod.getGeneralMethod().setDisLoc(shell, 700, 334);// 设置显示位置
		shell.setSize(700, 334);
		shell.setText("网站列表");

		Composite composite;
		composite = new Composite(shell, SWT.NONE);
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		final FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(100, -70);
		fd_composite.right = new FormAttachment(100, -5);
		fd_composite.left = new FormAttachment(0, 5);
		fd_composite.bottom = new FormAttachment(100, -5);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());

		final CLabel label = new CLabel(composite, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 75);
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.top = new FormAttachment(0, 5);
		fd_label.left = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("名称：");

		final CLabel label_1 = new CLabel(composite, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/text_field.gif"));
		final FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(0, 75);
		fd_label_1.bottom = new FormAttachment(100, -5);
		fd_label_1.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("网址：");

		nameSText = new StyledText(composite, SWT.SINGLE | SWT.BORDER);
		final FormData fd_nameSText = new FormData();
		fd_nameSText.right = new FormAttachment(63, 0);
		fd_nameSText.top = new FormAttachment(label, 0, SWT.TOP);
		fd_nameSText.left = new FormAttachment(label, 5, SWT.RIGHT);
		nameSText.setLayoutData(fd_nameSText);

		urlSText = new StyledText(composite, SWT.SINGLE | SWT.BORDER);
		fd_nameSText.bottom = new FormAttachment(urlSText, -5, SWT.TOP);
		final FormData fd_siteSText = new FormData();
		fd_siteSText.right = new FormAttachment(63, 0);
		fd_siteSText.bottom = new FormAttachment(100, -5);
		fd_siteSText.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_siteSText.left = new FormAttachment(label_1, 5, SWT.RIGHT);
		urlSText.setLayoutData(fd_siteSText);

		addBut = new Button(composite, SWT.NONE);
		addBut.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/add_action.gif"));
		final FormData fd_addBut = new FormData();
		fd_addBut.right = new FormAttachment(74, 0);
		fd_addBut.bottom = new FormAttachment(urlSText, 0, SWT.BOTTOM);
		fd_addBut.top = new FormAttachment(nameSText, 0, SWT.TOP);
		fd_addBut.left = new FormAttachment(nameSText, 5, SWT.RIGHT);
		addBut.setLayoutData(fd_addBut);
		addBut.setText("添加");

		delBut = new Button(composite, SWT.NONE);
		delBut.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/delete_edit.gif"));
		final FormData fd_delBut = new FormData();
		fd_delBut.right = new FormAttachment(84, 0);
		fd_delBut.bottom = new FormAttachment(addBut, 0, SWT.BOTTOM);
		fd_delBut.top = new FormAttachment(addBut, 0, SWT.TOP);
		fd_delBut.left = new FormAttachment(addBut, 5, SWT.RIGHT);
		delBut.setLayoutData(fd_delBut);
		delBut.setText("删除");

		updateBut = new Button(composite, SWT.NONE);
		updateBut.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/refresh.gif"));
		final FormData fd_updateBut = new FormData();
		fd_updateBut.left = new FormAttachment(delBut, 8, SWT.DEFAULT);
		fd_updateBut.bottom = new FormAttachment(delBut, 0, SWT.BOTTOM);
		fd_updateBut.right = new FormAttachment(100, -5);
		fd_updateBut.top = new FormAttachment(delBut, 0, SWT.TOP);
		updateBut.setLayoutData(fd_updateBut);
		updateBut.setText("更新数据");

		listTable = new Table(shell, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);
		final FormData fd_listTable = new FormData();
		fd_listTable.bottom = new FormAttachment(65, 0);
		fd_listTable.right = new FormAttachment(composite, 0, SWT.RIGHT);
		fd_listTable.top = new FormAttachment(0, 5);
		fd_listTable.left = new FormAttachment(composite, 0, SWT.LEFT);
		listTable.setLayoutData(fd_listTable);
		listTable.setLinesVisible(true);
		listTable.setHeaderVisible(true);

		warnLab = new CLabel(shell, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(SetHttpListDialog.class, "/cn/imgdpu/ico/ihigh_obj.gif"));

		final TableColumn newColumnTableColumn = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn.setWidth(144);
		newColumnTableColumn.setText("名称");

		final TableColumn newColumnTableColumn_1 = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn_1.setWidth(299);
		newColumnTableColumn_1.setText("网址");

		final TableColumn newColumnTableColumn_2 = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("更新时间");

		final TableColumn newColumnTableColumn_3 = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn_3.setWidth(100);
		newColumnTableColumn_3.setText("数据库表名");
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -75);
		fd_warnLab.top = new FormAttachment(listTable, 5, SWT.BOTTOM);
		fd_warnLab.right = new FormAttachment(listTable, -6, SWT.RIGHT);
		fd_warnLab.left = new FormAttachment(listTable, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("添加新的网址后，一定要更新，不然我就找不到你要的东西哦！^_^ 提示：按住[Ctrl]可选中多个！");
		//

		// 设置表格数据
		setTableList();

		// 更新选中的网站数据
		updateBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				// 取出选中的项目
				TableItem[] ti = listTable.getSelection();

				if (ti.length > 0) {
					setButEnble();
					warnLab.setText("正在使劲的更新数据中...也许你去泡杯茶吃个包再回来看看我吧！O(∩_∩)O(可以关闭窗口)");

					// 用来保存要选中的网站地址
					final ArrayList<String> updates = new ArrayList<String>();

					for (int i = 0; i < ti.length; i++) {
						updates.add(ti[i].getText(1));
						updates.add(ti[i].getText(3));
					}

					Thread update = new Thread() {
						public void run() {
							for (int i = 0; i < updates.size() && updates.size() >= 2; i += 2) {
								ArrayList<String> result = new ReadHtmlFiles().readFiles(updates.get(i));
								cn.imgdpu.util.SqlProcess.setFileList(updates.get(i + 1), result);

								// 添加更新时间
								String time = cn.imgdpu.util.GeneralMethod.getGeneralMethod().getNowTime();

								String sql = "update httplist set updatetime = '" + time + "' where filetable = '" + updates.get(i + 1) + "'";
								cn.imgdpu.util.SqlProcess.setData(sql);

								// 更新表中信息
								setTableList();
							}

							// 如果窗口没有关闭
							if (!shell.isDisposed()) {
								shell.getDisplay().asyncExec(new Runnable() {

									@Override
									public void run() {
										setButEnble();
										warnLab.setText("嘿嘿，网站数据更新完成了！O(∩_∩)O");
									}

								});
							} else {
								cn.imgdpu.GSAGUI.setStatusAsyn("嘿嘿，网站数据更新完成了！O(∩_∩)O");
							}

						}
					};

					update.start();
				} else {
					warnLab.setText("呃，你都没有选中要更新的网址，我也不知道你要更新什么！~(>_<)~ ");
				}

			}

		});

		// 删除按钮
		delBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				// 得到选中的项目
				TableItem[] select = listTable.getSelection();
				if (select.length > 0) {

					MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					mb.setText("警告");
					mb.setMessage("你确定要删除吗？\n删除后可就找不回了！~(>_<)~");

					// 如果选择是则删除
					if (mb.open() == 64) {
						// 删除选中的每一个项目
						for (int i = 0; i < select.length; i++) {

							String del = select[i].getText(3);

							// 删除表
							String delTable = "drop table if exists " + del;
							cn.imgdpu.util.SqlProcess.setData(delTable);

							// 删除网站列表
							String delList = "delete from httplist where filetable = '" + del + "'";
							cn.imgdpu.util.SqlProcess.setData(delList);

							select[i].dispose();

						}

						warnLab.setText("嗯，删除成功了！^_^");
					}

				} else {
					warnLab.setText("什么都没选中，你想删除什么？~(>_<)~");
				}

			}

		});

		// 添加按钮
		addBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				String name = nameSText.getText();
				String url = urlSText.getText();
				String ip = url;

				if (name.isEmpty() || url.isEmpty()) {
					warnLab.setText("你好像有什么东西忘了填是吧？~(>_<)~");
				} else {
					if (url.startsWith("www") || url.startsWith("http://")) {
						if (url.startsWith("www")) {
							url = "http://" + url;
						}
						String table = url.replaceAll("://", "");
						table = table.replaceAll("\\.", "_").replaceAll("/", "_").replaceAll("-", "_");

						cn.imgdpu.util.SqlProcess.setData("insert into httplist values('" + ip + "','" + name + "','-','" + table + "')");

						TableItem item = new TableItem(listTable, SWT.NONE);
						item.setText(new String[] { name, ip, "-", table });

						warnLab.setText("嘿嘿，添加网址成功了，现在你首先需要做的，也许是更新它的数据！O(∩_∩)O");
					} else {
						warnLab.setText("你输入的网址合适我不认识？~(>_<)~");
					}
				}

			}

		});

	}

	// 设置表格项目
	private void setTableList() {

		shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				// 清空已有项目，更新后需要
				listTable.removeAll();

				ArrayList<String> s = cn.imgdpu.util.SqlProcess.getData("select ip,info,updatetime,filetable from httplist");

				for (int i = 0; i < s.size() && s.size() >= 4; i += 4) {
					TableItem item = new TableItem(listTable, SWT.NONE);
					item.setText(new String[] { s.get(i + 1), s.get(i), s.get(i + 2), s.get(i + 3) });
				}

				// 设置行高
				listTable.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(listTable));

			}

		});
	}

	// 设置按钮状态
	private void setButEnble() {

		if (addBut.isEnabled()) {
			addBut.setEnabled(false);
		} else {
			addBut.setEnabled(true);
		}

		if (delBut.isEnabled()) {
			delBut.setEnabled(false);
		} else {
			delBut.setEnabled(true);
		}

		if (updateBut.isEnabled()) {
			updateBut.setEnabled(false);
		} else {
			updateBut.setEnabled(true);
		}

	}
}
