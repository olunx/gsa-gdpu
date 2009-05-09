/*
 *@author olunx , Time:2009-4-4
 *
 *Website : http://www.olunx.com
 *
 *This: FTP搜索界面
 */

package cn.imgdpu.compo;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class FtpFilesCompo extends Composite {

	private static CCombo inCombo;
	private Text sizeText;
	private CCombo sizeCombo;
	private CCombo typeCombo;
	private StyledText nameSText;
	private static Table listTable;
	private static Button loadBut, stopBut;
	private static Listener mySetDataListener = null;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public FtpFilesCompo(Composite parent, int style) {
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
		label.setImage(SWTResourceManager.getImage(FtpFilesCompo.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 95);
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.left = new FormAttachment(0, 5);
		fd_label.top = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("搜索网站：");

		loadBut = new Button(inputCompo, SWT.NONE);
		loadBut.setToolTipText("开始搜索指定数据");
		loadBut.setImage(SWTResourceManager.getImage(FtpFilesCompo.class, "/cn/imgdpu/ico/exportrunnablejar_wiz.gif"));
		final FormData fd_loadBut = new FormData();
		fd_loadBut.right = new FormAttachment(90, 0);
		fd_loadBut.top = new FormAttachment(0, 5);
		loadBut.setLayoutData(fd_loadBut);
		loadBut.setText("开始搜索");

		listTable = new Table(this, SWT.VIRTUAL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
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
		nameCTC.setWidth(250);
		nameCTC.setText("文件名");

		final TableColumn linkCTC = new TableColumn(listTable, SWT.NONE);
		linkCTC.setWidth(300);
		linkCTC.setText("所在目录");

		final TableColumn typeCTC = new TableColumn(listTable, SWT.NONE);
		typeCTC.setWidth(60);
		typeCTC.setText("类型");

		final CLabel warnLab = new CLabel(this, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(FtpFilesCompo.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.bottom = new FormAttachment(100, -5);
		fd_warnLab.right = new FormAttachment(listTable, 0, SWT.RIGHT);
		fd_warnLab.top = new FormAttachment(listTable, 5, SWT.BOTTOM);
		fd_warnLab.left = new FormAttachment(listTable, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("本功能支持FTP搜索文件！(双击选中项复制文件地址！)");

		stopBut = new Button(inputCompo, SWT.NONE);
		stopBut.setImage(SWTResourceManager.getImage(FtpFilesCompo.class, "/cn/imgdpu/ico/delete_edit.gif"));
		final FormData fd_stopBut = new FormData();
		fd_stopBut.right = new FormAttachment(100, -5);
		fd_stopBut.left = new FormAttachment(loadBut, 5, SWT.RIGHT);
		fd_stopBut.top = new FormAttachment(loadBut, 0, SWT.TOP);
		fd_stopBut.bottom = new FormAttachment(loadBut, 0, SWT.BOTTOM);
		stopBut.setLayoutData(fd_stopBut);
		stopBut.setText("终止");
		stopBut.setEnabled(false);

		nameSText = new StyledText(inputCompo, SWT.SINGLE | SWT.BORDER);
		final FormData fd_nameSText = new FormData();
		fd_nameSText.right = new FormAttachment(33, 0);
		fd_nameSText.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_nameSText.left = new FormAttachment(label, 5, SWT.RIGHT);
		nameSText.setLayoutData(fd_nameSText);

		CLabel label_3;
		label_3 = new CLabel(inputCompo, SWT.NONE);
		final FormData fd_label_3 = new FormData();
		fd_label_3.right = new FormAttachment(40, 0);
		fd_label_3.bottom = new FormAttachment(nameSText, 0, SWT.BOTTOM);
		fd_label_3.left = new FormAttachment(nameSText, 8, SWT.DEFAULT);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("类型：");

		typeCombo = new CCombo(inputCompo, SWT.BORDER);
		fd_label_3.top = new FormAttachment(typeCombo, 0, SWT.TOP);
		final FormData fd_typeCombo = new FormData();
		fd_typeCombo.left = new FormAttachment(label_3, 0, SWT.RIGHT);
		fd_typeCombo.right = new FormAttachment(50, 0);
		fd_typeCombo.top = new FormAttachment(nameSText, 0, SWT.TOP);
		fd_typeCombo.bottom = new FormAttachment(nameSText, 0, SWT.BOTTOM);
		typeCombo.setLayoutData(fd_typeCombo);

		sizeCombo = new CCombo(inputCompo, SWT.READ_ONLY | SWT.BORDER);
		sizeCombo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		sizeCombo.setItems(new String[] { "-", "大于", "小于" });
		final FormData fd_sizeCombo = new FormData();
		fd_sizeCombo.right = new FormAttachment(63, 0);
		fd_sizeCombo.bottom = new FormAttachment(nameSText, 0, SWT.BOTTOM);
		fd_sizeCombo.top = new FormAttachment(typeCombo, 0, SWT.TOP);
		sizeCombo.setLayoutData(fd_sizeCombo);

		sizeText = new Text(inputCompo, SWT.BORDER);
		final FormData fd_sizeText = new FormData();
		fd_sizeText.bottom = new FormAttachment(sizeCombo, 0, SWT.BOTTOM);
		fd_sizeText.left = new FormAttachment(sizeCombo, 5, SWT.RIGHT);
		fd_sizeText.right = new FormAttachment(70, 0);
		fd_sizeText.top = new FormAttachment(label, 5, SWT.BOTTOM);
		sizeText.setLayoutData(fd_sizeText);

		CLabel label_1;
		label_1 = new CLabel(inputCompo, SWT.NONE);
		fd_sizeCombo.left = new FormAttachment(label_1, 5, SWT.RIGHT);
		final FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(typeCombo, 5, SWT.RIGHT);
		fd_label_1.right = new FormAttachment(55, 0);
		fd_label_1.bottom = new FormAttachment(nameSText, 0, SWT.BOTTOM);
		fd_label_1.top = new FormAttachment(typeCombo, 0, SWT.TOP);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("大小：");

		CLabel mbLabel;
		mbLabel = new CLabel(inputCompo, SWT.NONE);
		fd_loadBut.left = new FormAttachment(mbLabel, 5, SWT.RIGHT);
		fd_loadBut.bottom = new FormAttachment(mbLabel, 0, SWT.BOTTOM);
		final FormData fd_mbLabel = new FormData();
		fd_mbLabel.left = new FormAttachment(sizeText, 0, SWT.RIGHT);
		fd_mbLabel.bottom = new FormAttachment(sizeText, 0, SWT.BOTTOM);
		mbLabel.setLayoutData(fd_mbLabel);
		mbLabel.setText("MB");

		CLabel label_4;
		label_4 = new CLabel(inputCompo, SWT.NONE);
		fd_nameSText.bottom = new FormAttachment(label_4, 0, SWT.BOTTOM);
		label_4.setImage(SWTResourceManager.getImage(FtpFilesCompo.class, "/cn/imgdpu/ico/editor_pane.gif"));
		final FormData fd_label_4 = new FormData();
		fd_label_4.right = new FormAttachment(0, 95);
		fd_label_4.bottom = new FormAttachment(0, 60);
		fd_label_4.top = new FormAttachment(0, 35);
		fd_label_4.left = new FormAttachment(label, 0, SWT.LEFT);
		label_4.setLayoutData(fd_label_4);
		label_4.setText("文件名称：");

		inCombo = new CCombo(inputCompo, SWT.READ_ONLY | SWT.BORDER);
		fd_mbLabel.right = new FormAttachment(inCombo, 0, SWT.RIGHT);
		fd_mbLabel.top = new FormAttachment(inCombo, 5, SWT.BOTTOM);
		inCombo.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final FormData fd_inCombo = new FormData();
		fd_inCombo.bottom = new FormAttachment(0, 30);
		fd_inCombo.right = new FormAttachment(73, 0);
		fd_inCombo.top = new FormAttachment(label, 0, SWT.TOP);
		fd_inCombo.left = new FormAttachment(label, 5, SWT.RIGHT);
		inCombo.setLayoutData(fd_inCombo);
		//

		// 设置行高
		listTable.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(listTable));

		final TableColumn sizeColumn = new TableColumn(listTable, SWT.NONE);
		sizeColumn.setWidth(70);
		sizeColumn.setText("大小");

		// 设置下拉列表
		setComboUrls();

		// 读取数据按钮
		loadBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				String ipStr = inCombo.getText().trim();
				if (ipStr.indexOf("(") == -1)
					ipStr = "";
				else
					ipStr = ipStr.substring(0, ipStr.indexOf("(")).trim();
				String name = nameSText.getText().trim();
				String type = typeCombo.getText().trim();
				String sizeComboStr = sizeCombo.getText().trim();
				String sizeStr = sizeText.getText().trim();
				Float fsize = 0f;
				try {
					if(!sizeText.getText().trim().equals("")) {
						fsize = Float.parseFloat(sizeStr);
						fsize = fsize * 1024 * 1024;
					}
				} catch (NumberFormatException e2) {
					fsize = (float) 0;
					cn.imgdpu.util.CatException.getMethod().catException(e2, "数字格式不正确");
				}
				StringBuffer sqlStr = new StringBuffer();
				if (!name.isEmpty() || !type.isEmpty() || (!sizeComboStr.equals("-") && fsize > 0))
					sqlStr.append(" where");
				if (!name.isEmpty())// 文件名
					sqlStr.append(" link like '%").append(name).append("%'");
				if (!type.isEmpty()) {// 类型
					if (!name.isEmpty())
						sqlStr.append(" and");
					sqlStr.append(" (type='").append(GeneralMethod.getGeneralMethod().typeConv(type)).append("')");
				}
				if (!sizeComboStr.equals("-") && fsize > 0) {// 大小
					if (!name.isEmpty() || !type.isEmpty())
						sqlStr.append(" and");
					if (sizeComboStr.equals("大于"))
						sqlStr.append(" size>").append(fsize);
					else if (sizeComboStr.equals("小于"))
						sqlStr.append(" size<").append(fsize);
				}

				ArrayList<String> list = new ArrayList<String>();
				ArrayList<String> filesList = new ArrayList<String>();
				setButEnable();
				if (ipStr.isEmpty()) {
					cn.imgdpu.GSAGUI.setStatus("你好像什么都没有选择或者输入！~(>_<)~");
				} else if (ipStr.equals("全部")) {
					list = cn.imgdpu.util.SqlProcess.getData("select filetable from ftpList where filetable!=''");
					if (!list.isEmpty()) {
						sqlStr.append(" order by link asc");
						String tableStr = null;
						filesList.clear();
						for (String s : list) {

							tableStr = "select name,link,type,size from " + s + " " + sqlStr.toString();
							filesList.addAll(cn.imgdpu.util.SqlProcess.getData(tableStr.toString()));// 查找
						}
						sqlStr.insert(0, "select name,link,type,size from " + tableStr + " ");
						setTableText(filesList);
					} else {
						cn.imgdpu.GSAGUI.setStatus("没有找到这个站点的任何数据,请先更新文件列表。~(>_<)~");
					}
				} else {
					list.clear();
					list = cn.imgdpu.util.SqlProcess.getData("select filetable from ftpList where ip='" + ipStr + "'");
					if (!list.get(0).isEmpty()) {
						String tableName = GeneralMethod.getGeneralMethod().getIpToTableName(ipStr);
						sqlStr.insert(0, "select name,link,type,size from " + tableName + " ");
						sqlStr.append(" order by link asc");
						filesList = cn.imgdpu.util.SqlProcess.getData(sqlStr.toString());// 查找
						setTableText(filesList);
					} else {
						cn.imgdpu.GSAGUI.setStatus("没有找到这个站点的任何数据,请先更新文件列表。~(>_<)~");
					}
				}
				setButEnable();
			}

		});

		// 停止按钮
		stopBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				cn.imgdpu.GSAGUI.setStatusAsyn("正在停止...");
				setButEnable();
			}

		});

		// 双击事件
		listTable.addListener(SWT.DefaultSelection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				// 得到选中行，取出相对链接
				TableItem[] selectItem = listTable.getSelection();
				// 获得系统剪切板
				Clipboard clipboard = new Clipboard(cn.imgdpu.GSAGUI.shell.getDisplay());
				clipboard.setContents(new String[] { selectItem[0].getText(2) }, new Transfer[] { TextTransfer.getInstance() });

				cn.imgdpu.GSAGUI.setStatusAsyn("成功复制文件链接！");
			}

		});

		// 添加右键菜单
		final Menu tableMenu = new Menu(listTable);
		listTable.setMenu(tableMenu);

		final MenuItem copyUrl = new MenuItem(tableMenu, SWT.PUSH);
		copyUrl.setText("复制文件链接");
		copyUrl.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

		final MenuItem copyPath = new MenuItem(tableMenu, SWT.NONE);

		copyPath.setText("复制目录");
		copyPath.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/cpyqual_menu.gif"));

		// 右键菜单事件
		copyUrl.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {

				// 得到选中行，取出相对链接
				TableItem[] selectItem = listTable.getSelection();

				if (selectItem.length > 0) {
					StringBuffer copys = new StringBuffer();
					for (int i = 0; i < selectItem.length; i++) {
						copys.append(selectItem[i].getText(5) + "\n");
					}
					// 复制到系统剪切板
					GeneralMethod.getGeneralMethod().copyToSystem(copys.toString());

					cn.imgdpu.GSAGUI.setStatusAsyn("复制文件链接成功！");
				}
			}
		});

		// 右键菜单事件
		copyPath.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {

				// 得到选中行，取出相对链接
				TableItem[] selectItem = listTable.getSelection();
				if (selectItem.length > 0) {
					StringBuffer copys = new StringBuffer();
					for (int i = 0; i < selectItem.length; i++) {
						String url = selectItem[i].getText(5);
						copys.append(url.substring(0, url.lastIndexOf("/") + 1) + "\n");
					}
					// 复制到系统剪切板
					GeneralMethod.getGeneralMethod().copyToSystem(copys.toString());

					cn.imgdpu.GSAGUI.setStatusAsyn("复制文件夹链接成功！");
				}
			}
		});

		final TableColumn linkColumn = new TableColumn(listTable, SWT.NONE);
		linkColumn.setText("链接");

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

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 数据显示到界面，并添加事件
	public static void setTableText(final ArrayList<String> files) {
		class setDataListener implements Listener {
			@Override
			public void handleEvent(Event e) {
				TableItem item = (TableItem) e.item;
				int index = e.index * 4;
				double fsize = Double.parseDouble(files.get(index + 3));
				String fsizeStr = GeneralMethod.getGeneralMethod().formatFilesize(fsize);
				String furl = null;

				furl = files.get(index + 1);
				String fdir = GeneralMethod.getGeneralMethod().getFileParentDir((files.get(index + 1)), "ftp://");
				item.setText(new String[] { String.valueOf(e.index), files.get(index), fdir, files.get(index + 2), fsizeStr, furl });
			}
		}
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				listTable.removeAll();
				if (mySetDataListener != null)
					listTable.removeListener(SWT.SetData, mySetDataListener);
				mySetDataListener = new setDataListener();
				listTable.addListener(SWT.SetData, mySetDataListener);
				listTable.setItemCount(files.size() / 4);
			}

		});

	}

	// 设置按钮状态
	private static void setButEnable() {
		if (loadBut.isEnabled())
			loadBut.setEnabled(false);
		else
			loadBut.setEnabled(true);

		if (stopBut.isEnabled())
			stopBut.setEnabled(false);
		else
			stopBut.setEnabled(true);

	}

	// 设置网址Combo的内容
	public static void setComboUrls() {
		cn.imgdpu.util.SqlProcess
				.setData("create table if not exists ftpList(ip varchar(16), user varchar(16), pwd varchar(8), info varchar(16), updatetime varchar(8), filetable varchar(8))");
		ArrayList<String> site = cn.imgdpu.util.SqlProcess.getData("select ip,info from ftplist");

		String[] s = new String[(site.size() / 2) + 1];
		int i, j;
		for (i = 0, j = 0; i < site.size(); i += 2, j++) {
			s[j] = site.get(i) + "  (" + site.get(i + 1) + ")";
		}
		s[j] = "全部  (搜索所有已经更新文件列表的站点)";
		inCombo.setItems(s);

	}

}
