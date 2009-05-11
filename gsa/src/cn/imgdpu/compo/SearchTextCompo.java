/*
 *@author olunx , Time:2009-4-30
 *
 *Website : http://www.olunx.com
 *
 *This: 内容搜索界面
 */

package cn.imgdpu.compo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cn.imgdpu.util.ListDirFiles;
import cn.imgdpu.util.LuneceProcess;

import com.swtdesigner.SWTResourceManager;

public class SearchTextCompo extends Composite {

	private static CCombo typeCombo;
	private static StyledText nameSText;
	private static CCombo inCombo;
	private static Table listTable;
	private static Button loadBut, stopBut;

	private static Listener mySetDataListener = null;
	Thread searchThread;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public SearchTextCompo(final Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		final Composite inputCompo = new Composite(this, SWT.NONE);
		final FormData fd_inputCompo = new FormData();
		fd_inputCompo.bottom = new FormAttachment(0, 70);
		fd_inputCompo.right = new FormAttachment(100, -5);
		fd_inputCompo.top = new FormAttachment(0, 5);
		fd_inputCompo.left = new FormAttachment(0, 5);
		inputCompo.setLayoutData(fd_inputCompo);
		inputCompo.setLayout(new FormLayout());

		final CLabel label = new CLabel(inputCompo, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 95);
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.left = new FormAttachment(0, 5);
		fd_label.top = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("搜索目录：");

		loadBut = new Button(inputCompo, SWT.NONE);
		loadBut.setToolTipText("开始搜索指定数据");
		loadBut.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/exportrunnablejar_wiz.gif"));
		final FormData fd_loadBut = new FormData();
		fd_loadBut.right = new FormAttachment(90, 0);
		loadBut.setLayoutData(fd_loadBut);
		loadBut.setText("开始搜索");

		inCombo = new CCombo(inputCompo, SWT.READ_ONLY | SWT.BORDER);
		inCombo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		fd_loadBut.left = new FormAttachment(inCombo, 5, SWT.RIGHT);
		fd_loadBut.top = new FormAttachment(inCombo, 0, SWT.TOP);
		inCombo.setToolTipText("选择要查找的目录");
		final FormData fd_inCombo = new FormData();
		fd_inCombo.right = new FormAttachment(73, 0);
		fd_inCombo.left = new FormAttachment(label, 5, SWT.DEFAULT);
		fd_inCombo.bottom = new FormAttachment(0, 30);
		fd_inCombo.top = new FormAttachment(label, 0, SWT.TOP);
		inCombo.setLayoutData(fd_inCombo);

		listTable = new Table(this, SWT.VIRTUAL | SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);
		final FormData fd_listTable = new FormData();
		fd_listTable.top = new FormAttachment(inputCompo, 5, SWT.BOTTOM);
		fd_listTable.bottom = new FormAttachment(100, -40);
		fd_listTable.right = new FormAttachment(inputCompo, 0, SWT.RIGHT);
		fd_listTable.left = new FormAttachment(inputCompo, 0, SWT.LEFT);
		listTable.setLayoutData(fd_listTable);
		listTable.setLinesVisible(true);
		listTable.setHeaderVisible(true);

		final TableColumn idCTC = new TableColumn(listTable, SWT.NONE);
		idCTC.setWidth(40);
		idCTC.setText("序号");

		final TableColumn nameCTC = new TableColumn(listTable, SWT.NONE);
		nameCTC.setWidth(201);
		nameCTC.setText("文件名");

		final TableColumn linkCTC = new TableColumn(listTable, SWT.NONE);
		linkCTC.setWidth(364);
		linkCTC.setText("所在目录");

		final TableColumn typeCTC = new TableColumn(listTable, SWT.NONE);
		typeCTC.setWidth(60);
		typeCTC.setText("类型");

		final CLabel warnLab = new CLabel(this, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -5);
		fd_warnLab.right = new FormAttachment(listTable, 0, SWT.RIGHT);
		fd_warnLab.top = new FormAttachment(listTable, 5, SWT.BOTTOM);
		fd_warnLab.left = new FormAttachment(listTable, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("本功能仅支持Http协议的文件下载网站！判断是否该类型的网站，可以参考选项框中已有的网址。(双击选中项复制文件地址！)");

		final CLabel label_1 = new CLabel(inputCompo, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/editor_pane.gif"));
		final FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(0, 95);
		fd_label_1.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("关键词：");

		nameSText = new StyledText(inputCompo, SWT.SINGLE | SWT.BORDER);
		fd_label_1.bottom = new FormAttachment(nameSText, 0, SWT.BOTTOM);
		final FormData fd_nameSText = new FormData();
		fd_nameSText.right = new FormAttachment(40, 0);
		fd_nameSText.bottom = new FormAttachment(100, -5);
		fd_nameSText.top = new FormAttachment(inCombo, 5, SWT.BOTTOM);
		fd_nameSText.left = new FormAttachment(label_1, 5, SWT.RIGHT);
		nameSText.setLayoutData(fd_nameSText);

		typeCombo = new CCombo(inputCompo, SWT.BORDER);
		fd_loadBut.bottom = new FormAttachment(typeCombo, 0, SWT.BOTTOM);
		final FormData fd_typeCombo = new FormData();
		fd_typeCombo.right = new FormAttachment(73, 0);
		typeCombo.setLayoutData(fd_typeCombo);

		stopBut = new Button(inputCompo, SWT.NONE);
		stopBut.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/delete_edit.gif"));
		final FormData fd_stopBut = new FormData();
		fd_stopBut.top = new FormAttachment(loadBut, 0, SWT.TOP);
		fd_stopBut.bottom = new FormAttachment(loadBut, 0, SWT.BOTTOM);
		fd_stopBut.right = new FormAttachment(100, -5);
		fd_stopBut.left = new FormAttachment(loadBut, 5, SWT.RIGHT);
		stopBut.setLayoutData(fd_stopBut);
		stopBut.setText("终止");
		stopBut.setEnabled(false);

		CLabel label_2;
		label_2 = new CLabel(inputCompo, SWT.NONE);
		fd_typeCombo.left = new FormAttachment(label_2, 5, SWT.RIGHT);
		fd_typeCombo.bottom = new FormAttachment(label_2, 0, SWT.BOTTOM);
		label_2.setImage(SWTResourceManager.getImage(SearchTextCompo.class, "/cn/imgdpu/ico/welcome_editor.gif"));
		fd_typeCombo.top = new FormAttachment(label_2, 0, SWT.TOP);
		final FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(53, 0);
		fd_label_2.left = new FormAttachment(nameSText, 5, SWT.RIGHT);
		fd_label_2.top = new FormAttachment(nameSText, 0, SWT.TOP);
		fd_label_2.bottom = new FormAttachment(nameSText, 25, SWT.TOP);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("文件类型：");
		//

		// 文件类型
		typeCombo.setItems(new String[] { "DOC  -Word文档", "PPT -PowerPoint文档", "XLS -Excel文档", "TXT -文本文档" });
		inCombo.setItems(new String[] { "选择文件夹" });

		loadBut.setEnabled(false);

		inCombo.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				DirectoryDialog file = new DirectoryDialog(parent.getShell(), SWT.OK);
				file.open();
				inCombo.setText(file.getFilterPath());
				if (!file.getFilterPath().toString().equals("")) {
					loadBut.setEnabled(true);
				}
			}

		});

		typeCombo.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				String s = typeCombo.getText().toLowerCase();

				if (s.contains("word")) {
					s = "doc";
				} else if (s.contains("power")) {
					s = "ppt";
				} else if (s.contains("excel")) {
					s = "xls";
				} else if (s.contains("文本文档")) {
					s = "txt";
				}

				typeCombo.setText(s);
			}

		});

		loadBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if (!inCombo.getText().equals("") && !typeCombo.getText().equals("") && !nameSText.getText().equals("")) {

					// 支持的文件类型
					String supportType = "doc,ppt,xls,txt,asp,php,jsp,java,cpp,c,py,rb,html,";
					String type = typeCombo.getText().toLowerCase();

					if (supportType.contains(type)) {
						// 要搜索的目录
						final String searchPath = inCombo.getText();

						// 要搜索的文件类型
						final String fileType = typeCombo.getText().trim().toLowerCase();

						// 搜索关键词
						final String queryStr = nameSText.getText().trim().toLowerCase();

						// 索引的存放目录
						final String indexDir = cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\index");

						searchThread = new Thread() {

							public void run() {
								// 显示提示
								cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										warnLab.setText("搜索时间长短取决于你的电脑配置和搜索的文件多少！请耐心等待...^_^");
										loadBut.setEnabled(false);
										// stopBut.setEnabled(true);
									}
								});

								// 如果出错了，就删除索引加锁文件
								File lock = new File(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\index"));
								if (lock.exists()) {
									File[] file = lock.listFiles();
									for (int i = 0; i < file.length; i++) {
										file[i].delete();
										//System.out.println(file[i].getName());
									}
								}

								// 建立索引
								createIndex(searchPath, fileType, indexDir);

								// 搜索内容
								ArrayList<String> resultFiles = searchResult(indexDir, queryStr);

								// 显示结果
								setTableText(resultFiles);

								// 显示提示
								cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										loadBut.setEnabled(true);
										// stopBut.setEnabled(false);
										warnLab.setText("搜索完成，在以上 " + listTable.getItemCount() + " 个文件中找到相关词条！O(∩_∩)O");
									}
								});

							}
						};

						searchThread.start();

					} else {
						warnLab.setText("不支持该文件类型！");
					}

				} else {
					warnLab.setText("输入信息不完整！");
				}
			}

		});

		// // 停止按钮
		// stopBut.addListener(SWT.Selection, new Listener() {
		//
		// public void handleEvent(Event e) {
		// if (searchThread.isAlive()) {
		//
		// //searchThread = null;
		// loadBut.setEnabled(true);
		// stopBut.setEnabled(false);
		// }
		// }
		//
		// });

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 建立索引，参数：搜索目录、文件类型、索引文件保存目录
	void createIndex(String searchPath, String fileType, String indexDir) {

		// 遍历出所要的文件
		ListDirFiles ldf = new ListDirFiles();
		ldf.filesInfo = new ArrayList<String>();
		ldf.fileType = "." + fileType;
		ldf.listDirFiles(searchPath);

		LuneceProcess searchEng = new LuneceProcess();

		// 开始建立索引
		searchEng.indexFiles(ldf.filesInfo, indexDir);

	}

	// 搜索，参数：索引文件所在目录、关键词
	ArrayList<String> searchResult(String indexDir, String queryStr) {

		LuneceProcess searchEng = new LuneceProcess();

		// 开始搜索
		return searchEng.searchData(indexDir, queryStr);
	}

	// 数据显示到界面，并添加事件
	public void setTableText(final ArrayList<String> filesPath) {
		class setDataListener implements Listener {

			int index;
			String fileName, fileType;

			@Override
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;

				// 自增
				index = e.index;

				// 修改
				String file = filesPath.get(index);
				fileType = file.substring(file.lastIndexOf(".") + 1, file.length());
				fileName = file.substring(file.lastIndexOf("\\") + 1, file.lastIndexOf("."));

				// 设置表中数据
				item.setText(new String[] { String.valueOf(e.index), fileName, file, fileType });

			}

		}// end of setDataListner

		// 显示
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {

				// 清空所有
				listTable.removeAll();

				// 设置内容事件
				if (mySetDataListener != null) {
					listTable.removeListener(SWT.SetData, mySetDataListener);
				}
				mySetDataListener = new setDataListener();
				listTable.addListener(SWT.SetData, mySetDataListener);

				// 设置项目数
				listTable.setItemCount(filesPath.size());

				// 小于等于0表示没有数据，或者因为网络连接不上
				if (listTable.getItems().length >= 1) {

					// 双击事件，打开文件
					listTable.addListener(SWT.DefaultSelection, new Listener() {

						@Override
						public void handleEvent(Event e) {
							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();
							try {
								if (selectItem.length >= 1) {
									Runtime.getRuntime().exec("Explorer \"" + selectItem[0].getText(2) + "\"");
								} else {
									cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
								}
							} catch (IOException e1) {
								cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
							}
						}

					});

					// 添加右键菜单
					final Menu tableMenu = new Menu(listTable);
					listTable.setMenu(tableMenu);

					final MenuItem openFile = new MenuItem(tableMenu, SWT.NONE);
					openFile.setText("打开文件");
					openFile.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

					// 右键菜单事件，打开文件
					openFile.addListener(SWT.Selection, new Listener() {

						@Override
						public void handleEvent(Event e) {

							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();

							try {
								if (selectItem.length >= 1) {
									Runtime.getRuntime().exec("Explorer \"" + selectItem[0].getText(2) + "\"");
								} else {
									cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
								}
							} catch (IOException e1) {
								cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
							}
						}
					});

				}

			}
		});// end of runnable

	}
}
