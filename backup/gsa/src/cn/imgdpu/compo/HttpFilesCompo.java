/*
 *@author olunx , Time:2009-4-4
 *
 *Website : http://www.olunx.com
 *
 *This: HTTP搜索界面
 */

package cn.imgdpu.compo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
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

import cn.imgdpu.dialog.SetHttpListDialog;
import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.net.ReadHtmlFiles;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class HttpFilesCompo extends Composite {

	private static CCombo typeCombo;
	private static StyledText nameSText;
	private static CCombo inCombo;
	private static Table listTable;
	private static Button loadBut, stopBut;
	private Thread loadFile;
	private static Listener mySetDataListener = null;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public HttpFilesCompo(final Composite parent, int style) {
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
		label.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 95);
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.left = new FormAttachment(0, 5);
		fd_label.top = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("搜索网站：");

		loadBut = new Button(inputCompo, SWT.NONE);
		loadBut.setToolTipText("开始搜索指定数据");
		loadBut.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/exportrunnablejar_wiz.gif"));
		final FormData fd_loadBut = new FormData();
		fd_loadBut.right = new FormAttachment(90, 0);
		loadBut.setLayoutData(fd_loadBut);
		loadBut.setText("开始搜索");

		inCombo = new CCombo(inputCompo, SWT.BORDER);
		fd_loadBut.left = new FormAttachment(inCombo, 5, SWT.RIGHT);
		fd_loadBut.top = new FormAttachment(inCombo, 0, SWT.TOP);
		inCombo.setToolTipText("可以选择已有的，也可以自己输入新的！");
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
		nameCTC.setWidth(350);
		nameCTC.setText("文件名");

		final TableColumn linkCTC = new TableColumn(listTable, SWT.NONE);
		linkCTC.setWidth(250);
		linkCTC.setText("所在目录");

		final TableColumn typeCTC = new TableColumn(listTable, SWT.NONE);
		typeCTC.setWidth(60);
		typeCTC.setText("类型");

		final CLabel warnLab = new CLabel(this, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -5);
		fd_warnLab.right = new FormAttachment(listTable, 0, SWT.RIGHT);
		fd_warnLab.top = new FormAttachment(listTable, 5, SWT.BOTTOM);
		fd_warnLab.left = new FormAttachment(listTable, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("本功能仅支持Http协议的文件下载网站！判断是否该类型的网站，可以参考选项框中已有的网址。(双击选中项复制文件地址！)");

		final CLabel label_1 = new CLabel(inputCompo, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/editor_pane.gif"));
		final FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(0, 95);
		fd_label_1.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("文件名称：");

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
		stopBut.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/delete_edit.gif"));
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
		label_2.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/welcome_editor.gif"));
		fd_typeCombo.top = new FormAttachment(label_2, 0, SWT.TOP);
		final FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(53, 0);
		fd_label_2.left = new FormAttachment(nameSText, 5, SWT.RIGHT);
		fd_label_2.top = new FormAttachment(nameSText, 0, SWT.TOP);
		fd_label_2.bottom = new FormAttachment(nameSText, 25, SWT.TOP);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("文件类型：");
		//

		// 设置行高
		listTable.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(listTable));

		final TableColumn newColumnTableColumn = new TableColumn(listTable, SWT.NONE);
		newColumnTableColumn.setText("链接地址");

		// 设置下拉列表
		setComboUrls();
		inCombo.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if (inCombo.getText().contains("添加一个网址")) {
					new SetHttpListDialog(parent.getShell()).open();
					inCombo.setText("");
					setComboUrls();
				} else if (inCombo.getText().contains("    ---")) {
					// String text = inCombo.getText();
					Pattern pattern = Pattern.compile("^http://.*\\s");

					Matcher matcher = pattern.matcher(inCombo.getText());
					String s = "";
					while (matcher.find()) {
						s = matcher.group(0).trim();
					}
					inCombo.setText(s);
				}

			}
		});

		// 文件类型下拉列表
		typeCombo.setItems(new String[] { "视频类 -rmvb,rm,avi,mpeg,mp4,3gp", "音频类-mp3,wma,wav,cflac,cue", "文档类-doc,ppt,xls,pdf,chm" });
		typeCombo.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event e) {
				if (typeCombo.getText().startsWith("视频类")) {
					typeCombo.setText("video");
				} else if (typeCombo.getText().startsWith("音频类")) {
					typeCombo.setText("audio");
				} else if (typeCombo.getText().startsWith("文档类")) {
					typeCombo.setText("document");
				}
			}
		});

		// 读取数据按钮
		loadBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if ((inCombo.getText().startsWith("http://") || inCombo.getText().contains("所有网站")) && inCombo.getText().trim().endsWith("/")) {
					String tType = null;

					// 判断类型
					if (!typeCombo.getText().trim().isEmpty()) {
						tType = GeneralMethod.getGeneralMethod().typeConv(typeCombo.getText().trim().toLowerCase());
					} else {
						tType = "null";
					}

					// 判断文件名
					String tName = nameSText.getText().trim().toLowerCase();
					if (tName.isEmpty()) {
						tName = "null";
					}

					final String name = tName;
					final String type = tType;
					final String site = inCombo.getText().trim().toLowerCase();

					loadFile = new Thread() {
						public void run() {
							// 设置按钮状态
							setButEnable();
							cn.imgdpu.GSAGUI.setStatusAsyn("正在用力的搜索数据中...~(>_<)~");
							// sql执行
							String sql;

							// 存放要查询的table
							StringBuffer table = new StringBuffer();

							// 存放要更新的表名
							ArrayList<String> fileTable;

							if (site.contains("所有网站")) {
								fileTable = cn.imgdpu.util.SqlProcess.getData("select filetable from httplist");

								// 添加要处理的表代替符
								table.append("sqlreplaceme");

							} else {
								// 取出对应网址的文件表名
								fileTable = cn.imgdpu.util.SqlProcess.getData("select filetable from httplist where ip = '" + site + "'");

								// 如果大于零则代表有记录，否则添加一个
								if (fileTable.size() > 0) {
									table.append(fileTable.get(0));
								} else {
									// 添加临时表
									cn.imgdpu.util.SqlProcess.setData("insert into httplist values('tempsite','temp','temp','tempsite')");
									table.append("tempsite");

									// 查询临时表的数据
									ArrayList<String> result = new ReadHtmlFiles().readFiles(site);
									cn.imgdpu.util.SqlProcess.setFileList("tempsite", result);
								}

							}

							// 判断搜索条件
							if (name.equals("null") && type.equals("null"))
								sql = "select name,link,type from " + table + ";";
							else if (!name.equals("null") && !type.equals("null")) {
								sql = "select name,link,type from " + table + " where link like '%" + name + "%' and (type = '" + type + "');";
							} else {
								if (!name.equals("null")) {
									sql = "select name,link,type from " + table + " where link like '%" + name + "%';";
								} else {
									sql = "select name,link,type from " + table + " where type = '" + type + "';";
								}
							}

							// 保存查询结果
							ArrayList<String> result = new ArrayList<String>();

							// 处理查询所有网站的查询结果
							if (site.contains("所有网站")) {

								for (int i = 0; i < fileTable.size(); i++) {
									result.addAll(cn.imgdpu.util.SqlProcess.getData(sql.replaceAll("sqlreplaceme", fileTable.get(i))));
								}

							} else {
								result = cn.imgdpu.util.SqlProcess.getData(sql);
							}

							// 读取并显示
							setTableText(result);

							if (fileTable.size() < 1) {
								// 删除临时表
								cn.imgdpu.util.SqlProcess.setData("drop table if exists tempsite");
								cn.imgdpu.util.SqlProcess.setData("delete from httplist where filetable = 'tempsite'");
							}

							// 设置按钮状态
							setButEnable();
							cn.imgdpu.GSAGUI.setStatusAsyn("搜索数据成功！O(∩_∩)O");
						}
					};

					loadFile.start();
				} else {
					warnLab.setText("你输入的网址格式好像不正确，我只支持Http开头的而且需要\"/\"结尾的！~(>_<)~");
				}

			}

		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 设置按钮状态
	private static void setButEnable() {

		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (nameSText.isEnabled())
					nameSText.setEnabled(false);
				else
					nameSText.setEnabled(true);

				if (inCombo.isEnabled())
					inCombo.setEnabled(false);
				else
					inCombo.setEnabled(true);

				if (typeCombo.isEnabled())
					typeCombo.setEnabled(false);
				else
					typeCombo.setEnabled(true);

				if (loadBut.isEnabled())
					loadBut.setEnabled(false);
				else
					loadBut.setEnabled(true);

				if (stopBut.isEnabled())
					stopBut.setEnabled(false);
				else
					stopBut.setEnabled(true);
			}

		});

	}

	// 数据显示到界面，并添加事件
	public static void setTableText(final ArrayList<String> files) {
		class setDataListener implements Listener {

			// 取出搜索网址
			String baseUrl = inCombo.getText();

			int index;
			String filePath, fileName, fileType;

			@Override
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;

				// 自增
				index = e.index * 3;

				// 修改
				fileType = files.get(index + 2);
				fileName = files.get(index);
				filePath = files.get(index + 1);
				if (baseUrl.startsWith("http://")) {
					filePath = cn.imgdpu.util.GeneralMethod.getGeneralMethod().getFileParentDir(filePath, baseUrl);
				}

				// 设置表中数据
				item.setText(new String[] { String.valueOf(e.index), fileName, filePath, fileType, files.get(index + 1) });

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
				listTable.setItemCount(files.size() / 3);

				// 取出搜索网址
				final String baseUrl = inCombo.getText();

				// 小于等于0表示没有数据，或者因为网络连接不上
				if (listTable.getItems().length > 0) {

					// 双击事件，复制链接
					listTable.addListener(SWT.DefaultSelection, new Listener() {

						@Override
						public void handleEvent(Event e) {
							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();

							StringBuffer copys = new StringBuffer();

							for (int i = 0; i < selectItem.length; i++) {
								copys.append(selectItem[i].getText(4) + "\n");
							}

							GeneralMethod.getGeneralMethod().copyToSystem(copys.toString());

							cn.imgdpu.GSAGUI.setStatusAsyn("复制文件链接成功！");
						}

					});

					// 添加右键菜单
					final Menu tableMenu = new Menu(listTable);
					listTable.setMenu(tableMenu);

					final MenuItem copyUrl = new MenuItem(tableMenu, SWT.NONE);
					copyUrl.setText("复制文件链接");
					copyUrl.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

					final MenuItem copyPath = new MenuItem(tableMenu, SWT.NONE);
					copyPath.setText("复制目录");
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

							cn.imgdpu.GSAGUI.setStatusAsyn("复制文件链接成功！");
						}
					});

					// 复制目录
					copyPath.addListener(SWT.Selection, new Listener() {
						@Override
						public void handleEvent(Event e) {

							// 得到选中行，取出相对链接
							TableItem[] selectItem = listTable.getSelection();
							StringBuffer copys = new StringBuffer();

							for (int i = 0; i < selectItem.length; i++) {
								copys.append(baseUrl + selectItem[0].getText(2));
							}

							GeneralMethod.getGeneralMethod().copyToSystem(copys.toString());

							cn.imgdpu.GSAGUI.setStatusAsyn("复制文件目录成功！");
						}
					});
				}

			}
		});

		// files.removeAll(files);
	}

	// 设置网址Combo的内容
	private void setComboUrls() {

		// 检查httplist是否存在，不存在则创建
		cn.imgdpu.util.SqlProcess
				.setData("create table if not exists httpList(ip varchar(32),info varchar(32),updatetime varchar(16),filetable varchar(32))");

		// 得到所有的网站列表
		ArrayList<String> site = cn.imgdpu.util.SqlProcess.getData("select ip,info from httplist");

		// 网站列表数目
		int siteLength = site.size();

		String[] item = new String[siteLength / 2 + 2];

		for (int i = 0, j = 0; i < siteLength; i += 2, j++) {
			item[j] = site.get(i) + "    ---" + site.get(i + 1);
		}

		item[siteLength / 2] = "搜索所有网站";
		item[siteLength / 2 + 1] = "添加一个网址(可以保存数据)";

		// 删除原有的
		inCombo.removeAll();
		// 添加新建的项目
		inCombo.setItems(item);

	}

}
