/*
 *@author olunx , 创建时间:2009-4-18
 *
 *Website : http://www.olunx.com
 */

package cn.imgdpu.compo;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.util.GeneralMethod;
import cn.imgdpu.util.UpdateCampusNews;

import com.swtdesigner.SWTResourceManager;

public class NewsCampusCompo extends Composite {

	private Table listTable;
	private static Listener mySetDataListener = null;
	private Button updateBut;
	// 保存数据的文件
	String fileName = "newscampus.xml";

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public NewsCampusCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		listTable = new Table(this, SWT.VIRTUAL | SWT.FULL_SELECTION | SWT.BORDER);
		final FormData fd_listTable = new FormData();
		fd_listTable.right = new FormAttachment(100, -5);
		fd_listTable.left = new FormAttachment(0, 5);
		listTable.setLayoutData(fd_listTable);
		listTable.setLinesVisible(true);
		listTable.setHeaderVisible(true);

		final TableColumn id = new TableColumn(listTable, SWT.NONE);
		id.setWidth(43);
		id.setText("序号");

		final TableColumn title = new TableColumn(listTable, SWT.NONE);
		title.setWidth(459);
		title.setText("标题");

		final TableColumn date = new TableColumn(listTable, SWT.NONE);
		date.setWidth(110);
		date.setText("日期");

		final TableColumn author = new TableColumn(listTable, SWT.NONE);
		author.setWidth(100);
		author.setText("发布者");

		Composite composite;
		composite = new Composite(this, SWT.NONE);
		fd_listTable.bottom = new FormAttachment(composite, -5, SWT.TOP);

		final TableColumn newColumnTableColumn = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn.setText("链接");
		final FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(100, -40);
		fd_composite.bottom = new FormAttachment(100, -5);
		fd_composite.right = new FormAttachment(100, -5);
		fd_composite.left = new FormAttachment(listTable, 0, SWT.LEFT);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());

		updateBut = new Button(composite, SWT.NONE);
		updateBut.setImage(SWTResourceManager.getImage(NewsCampusCompo.class, "/cn/imgdpu/ico/refresh.gif"));
		final FormData fd_updateBut = new FormData();
		fd_updateBut.top = new FormAttachment(100, -30);
		fd_updateBut.left = new FormAttachment(100, -135);
		fd_updateBut.bottom = new FormAttachment(100, -5);
		fd_updateBut.right = new FormAttachment(100, -5);
		updateBut.setLayoutData(fd_updateBut);
		updateBut.setText("更新数据");

		final CLabel warnLab = new CLabel(composite, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(NewsCampusCompo.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -5);
		fd_warnLab.right = new FormAttachment(0, 525);
		fd_warnLab.top = new FormAttachment(updateBut, 0, SWT.TOP);
		fd_warnLab.left = new FormAttachment(0, 5);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("所有数据均来源于[广药校园网]！小贴士：双击选中项浏览详细内容！O(∩_∩)O");

		CLabel label;
		label = new CLabel(this, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(NewsCampusCompo.class, "/cn/imgdpu/ico/template_obj.gif"));
		fd_listTable.top = new FormAttachment(label, 5, SWT.BOTTOM);
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(listTable, 0, SWT.RIGHT);
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.top = new FormAttachment(0, 5);
		fd_label.left = new FormAttachment(listTable, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		label.setText("广药校园新闻");
		//

		// 设置行高
		listTable.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(listTable));

		// 读取本地数据并显示
		readRssData();

		// 更新数据按钮
		updateBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				getRssData();
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void getRssData() {

		// 设置按钮状态
		updateBut.setEnabled(false);
		cn.imgdpu.GSAGUI.setStatusAsyn("正在用力读取数据中...~(>_<)~");

		// 更新线程
		new UpdateCampusNews().start();
	}

	// 读取本地数据并显示
	public void readRssData() {

		// 读取保存在本地的数据
		ArrayList<String> data = cn.imgdpu.util.XmlProcess.getNewsData(fileName);

		// 显示到界面
		setTableText(data);

	}

	// 数据显示到界面，并添加事件
	void setTableText(final ArrayList<String> files) {
		class setDataListener implements Listener {

			int index;
			String title, link, date, author;

			@Override
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;

				// 自增
				index = e.index * 5;

				// 修改
				title = files.get(index);
				link = files.get(index + 1);
				date = files.get(index + 3);
				author = files.get(index + 4);

				// 设置表中数据
				item.setText(new String[] { String.valueOf(e.index), title, date, author, link });

			}

		}

		// 显示
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {

				listTable.removeAll();

				// 设置内容事件
				if (mySetDataListener != null) {
					listTable.removeListener(SWT.SetData, mySetDataListener);
				}
				mySetDataListener = new setDataListener();
				listTable.addListener(SWT.SetData, mySetDataListener);

				// 设置项目数
				listTable.setItemCount(files.size() / 5);

				// 小于等于0表示没有数据，或者因为网络连接不上
				if (listTable.getItems().length > 0) {
					// 双击事件，复制链接
					listTable.addListener(SWT.DefaultSelection, new Listener() {

						@Override
						public void handleEvent(Event e) {
							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();
							String url = selectItem[0].getText(4);

							try {
								Runtime.getRuntime().exec("explorer \"" + url + "\"");
							} catch (IOException e1) {
								cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
							}
						}

					});

					// 添加右键菜单
					final Menu tableMenu = new Menu(listTable);
					listTable.setMenu(tableMenu);

					final MenuItem copyUrl = new MenuItem(tableMenu, SWT.NONE);
					copyUrl.setText("复制文章链接");
					copyUrl.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

					final MenuItem copyPath = new MenuItem(tableMenu, SWT.NONE);
					copyPath.setText("在浏览器中查看");
					copyPath.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/cpyqual_menu.gif"));

					// 右键菜单事件，复制链接
					copyUrl.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {

							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();

							StringBuffer copys = new StringBuffer();

							for (int i = 0; i < selectItem.length; i++) {
								copys.append(selectItem[i].getText(4) + "\n");
							}

							GeneralMethod.getGeneralMethod().copyToSystem(copys.toString());

							cn.imgdpu.GSAGUI.setStatusAsyn("复制文章链接成功！");
						}
					});

					// 复制目录
					copyPath.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {

							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();
							String url = selectItem[0].getText(4);

							try {
								Runtime.getRuntime().exec("explorer \"" + url + "\"");
							} catch (IOException e1) {
								cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
							}

							cn.imgdpu.GSAGUI.setStatusAsyn("打开浏览器！");
						}
					});
				}

				// 设置按钮状态
				updateBut.setEnabled(true);
			}
		});

		// files.removeAll(files);
	}
}
