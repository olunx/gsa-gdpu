/*
 *@author olunx , Time:2009-4-6
 *
 *Website : http://www.olunx.com
 *
 *This: FTP网站列表界面
 */

package cn.imgdpu.compo;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cn.imgdpu.net.GetFtpFileList;
import cn.imgdpu.util.SqlProcess;

import com.swtdesigner.SWTResourceManager;

public class FtpFilesUpdateCompo extends Composite {

	private Text beizhuText, passText, userText, ipText;
	private static Table table;
	static Button updateBtn, stopBtn, addSiteBtn;
	final MenuItem delSiteMenu;
	static GetFtpFileList getFtpFileList;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public FtpFilesUpdateCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		table = new Table(this, SWT.FULL_SELECTION | SWT.CHECK | SWT.BORDER);
		final FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -67);
		fd_table.top = new FormAttachment(0, 5);
		fd_table.right = new FormAttachment(100, -5);
		fd_table.left = new FormAttachment(0, 5);
		table.setLayoutData(fd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		// 设置swtItem的高度
		table.addListener(SWT.MeasureItem, new cn.imgdpu.event.SwtTableItemHeightListener(table));

		final TableColumn newColumnTableColumn_1 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_1.setWidth(100);
		newColumnTableColumn_1.setText("地址");

		final TableColumn newColumnTableColumn_2 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_2.setWidth(100);
		newColumnTableColumn_2.setText("用户名");

		final TableColumn newColumnTableColumn_3 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_3.setWidth(100);
		newColumnTableColumn_3.setText("密码");

		final TableColumn newColumnTableColumn_4 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_4.setWidth(100);
		newColumnTableColumn_4.setText("上次更新时间");

		final TableColumn newColumnTableColumn = new TableColumn(table, SWT.NONE);
		newColumnTableColumn.setWidth(200);
		newColumnTableColumn.setText("备注");

		updateBtn = new Button(this, SWT.NONE);
		final FormData fd_button = new FormData();
		fd_button.top = new FormAttachment(100, -62);
		fd_button.right = new FormAttachment(100, -5);
		fd_button.bottom = new FormAttachment(100, -5);
		updateBtn.setLayoutData(fd_button);

		updateBtn.setText("更新文件列表");

		ipText = new Text(this, SWT.BORDER);
		final FormData fd_ipText = new FormData();
		fd_ipText.top = new FormAttachment(100, -62);
		fd_ipText.bottom = new FormAttachment(100, -37);
		fd_ipText.right = new FormAttachment(63, 0);
		ipText.setLayoutData(fd_ipText);

		userText = new Text(this, SWT.BORDER);
		userText.setText("anonymous");
		final FormData fd_userText = new FormData();
		fd_userText.top = new FormAttachment(100, -32);
		fd_userText.right = new FormAttachment(21, 0);
		fd_userText.left = new FormAttachment(ipText, 0, SWT.LEFT);
		userText.setLayoutData(fd_userText);

		CLabel label;
		label = new CLabel(this, SWT.NONE);
		fd_ipText.left = new FormAttachment(label, 5, SWT.RIGHT);
		final FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(table, 0, SWT.LEFT);
		fd_label.right = new FormAttachment(0, 70);
		fd_label.top = new FormAttachment(100, -60);
		label.setLayoutData(fd_label);
		label.setText("FTP地址：");

		CLabel label_1;
		label_1 = new CLabel(this, SWT.NONE);
		fd_label.bottom = new FormAttachment(label_1, -5, SWT.TOP);
		final FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(100, -32);
		fd_label_1.right = new FormAttachment(0, 70);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("用户名：");

		final CLabel label_2 = new CLabel(this, SWT.NONE);
		final FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(100, -32);
		fd_label_2.right = new FormAttachment(27, 0);
		fd_label_2.left = new FormAttachment(userText, 5, SWT.RIGHT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("密码：");

		passText = new Text(this, SWT.BORDER);
		fd_label_2.bottom = new FormAttachment(passText, 0, SWT.BOTTOM);
		fd_userText.bottom = new FormAttachment(passText, 0, SWT.BOTTOM);
		final FormData fd_passText = new FormData();
		fd_passText.top = new FormAttachment(100, -32);
		fd_passText.right = new FormAttachment(38, 0);
		fd_passText.left = new FormAttachment(label_2, 5, SWT.RIGHT);
		passText.setLayoutData(fd_passText);

		beizhuText = new Text(this, SWT.BORDER);
		fd_passText.bottom = new FormAttachment(beizhuText, 0, SWT.BOTTOM);
		final FormData fd_beizhuText = new FormData();
		fd_beizhuText.top = new FormAttachment(100, -32);
		fd_beizhuText.right = new FormAttachment(63, 0);
		fd_beizhuText.bottom = new FormAttachment(100, -5);
		beizhuText.setLayoutData(fd_beizhuText);

		CLabel label_2_1;
		label_2_1 = new CLabel(this, SWT.NONE);
		fd_beizhuText.left = new FormAttachment(label_2_1, 5, SWT.RIGHT);
		final FormData fd_label_2_1 = new FormData();
		fd_label_2_1.top = new FormAttachment(100, -32);
		fd_label_2_1.right = new FormAttachment(45, 0);
		fd_label_2_1.left = new FormAttachment(passText, 5, SWT.RIGHT);
		fd_label_2_1.bottom = new FormAttachment(beizhuText, 0, SWT.BOTTOM);
		label_2_1.setLayoutData(fd_label_2_1);
		label_2_1.setText("备注：");

		addSiteBtn = new Button(this, SWT.NONE);
		fd_label_1.bottom = new FormAttachment(addSiteBtn, 0, SWT.BOTTOM);

		final FormData fd_button_2 = new FormData();
		fd_button_2.top = new FormAttachment(100, -62);
		fd_button_2.right = new FormAttachment(78, 0);
		fd_button_2.left = new FormAttachment(beizhuText, 5, SWT.RIGHT);
		fd_button_2.bottom = new FormAttachment(100, -5);
		addSiteBtn.setLayoutData(fd_button_2);
		addSiteBtn.setText("添加");

		stopBtn = new Button(this, SWT.NONE);
		fd_button.left = new FormAttachment(stopBtn, 5, SWT.RIGHT);
		stopBtn.setEnabled(false);

		final FormData fd_stopBtn = new FormData();
		fd_stopBtn.top = new FormAttachment(100, -62);
		fd_stopBtn.right = new FormAttachment(85, 0);
		fd_stopBtn.left = new FormAttachment(addSiteBtn, 5, SWT.RIGHT);
		fd_stopBtn.bottom = new FormAttachment(addSiteBtn, 0, SWT.BOTTOM);
		stopBtn.setLayoutData(fd_stopBtn);
		stopBtn.setText("停止");

		final TableColumn newColumnTableColumn_5 = new TableColumn(table, SWT.NONE);
		newColumnTableColumn_5.setWidth(100);
		newColumnTableColumn_5.setText("数据库表名");

		readSite();// 读取站点列表

		// 设置右键菜单
		final Menu tableMenu = new Menu(table);
		table.setMenu(tableMenu);

		delSiteMenu = new MenuItem(tableMenu, SWT.NONE);
		delSiteMenu.setText("删除站点");
		delSiteMenu.setImage(SWTResourceManager.getImage(FtpFilesUpdateCompo.class, "/cn/imgdpu/ico/delete_edit.gif"));

		final MenuItem delbufMenu = new MenuItem(tableMenu, SWT.NONE);
		delbufMenu.setImage(SWTResourceManager.getImage(FtpFilesUpdateCompo.class, "/cn/imgdpu/ico/delete_layout.gif"));
		delbufMenu.setText("删除此站缓存");

		// 添加到FTP站点
		addSiteBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				Pattern pattern = Pattern.compile("^ftp://(.*?):(.*?)@([^/]*)/?|^ftp://([^/]*)/?|([^ftp:][^/]*)/?");
				Matcher matcher = pattern.matcher(ipText.getText().trim());
				if (matcher.find()) {
					if (matcher.group(5) != null)
						ipText.setText(matcher.group(5));
					else if (matcher.group(4) != null)
						ipText.setText(matcher.group(4));
					else if (matcher.group(3) != null) {
						ipText.setText(matcher.group(3));
						userText.setText(matcher.group(1));
						passText.setText(matcher.group(2));
					}
				} else {
					cn.imgdpu.GSAGUI.setStatus("FTP地址不正确,请重新输入(例如:ftp://10.50.25.1或10.50.25.1)");
					arg0.doit = false;
				}
				String ipTextStr = ipText.getText().trim();
				String userTextStr = userText.getText().trim();
				String passTextStr = passText.getText().trim();
				String beizhuTextStr = beizhuText.getText().trim();
				if (!ipTextStr.equals("")) {
					new TableItem(table, SWT.BORDER).setText(new String[] { ipTextStr, userTextStr, passTextStr, "-", beizhuTextStr });
					saveSite();
				} else {
					cn.imgdpu.GSAGUI.setStatus("资料填写不完整!请重新填写...");
				}
			}
		});

		// 删除站点
		delSiteMenu.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				int itemno = table.getSelectionIndex();
				if (itemno != -1) {
					TableItem[] selectItem = table.getSelection();
					String filetable = selectItem[0].getText(5);
					if (!filetable.isEmpty())
						SqlProcess.setData("drop table if exists " + filetable + "");
					table.remove(itemno);
					saveSite();
				}

			}
		});

		// 删除站点缓存
		delbufMenu.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				int itemno = table.getSelectionIndex();
				if (itemno != -1) {
					TableItem[] selectItem = table.getSelection();
					String ip = selectItem[0].getText(0);
					deleteFile(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\" + ip + ".buf"));
					deleteFile(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\" + ip + ".que"));
					cn.imgdpu.GSAGUI.setStatus("删除缓存成功!");
				}
			}
		});

		// 更新列表
		updateBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				boolean flag = false;
				ArrayList<String> site = new ArrayList<String>();
				for (int i = 0; i < table.getItems().length; i++) {
					if (table.getItems()[i].getChecked()) {
						flag = true;
						site.add(table.getItems()[i].getText(0));
						site.add(table.getItems()[i].getText(1));
						site.add(table.getItems()[i].getText(2));
					}
				}
				if (flag) {
					stopBtn.setEnabled(true);
					updateBtn.setEnabled(false);
					cn.imgdpu.GSAGUI.setStatus("开始更新FTP列表...");
					getFtpFileList = new GetFtpFileList(site);
					getFtpFileList.start();
				} else {
					cn.imgdpu.GSAGUI.setStatus("请选择需要更新列表的服务器.");
				}
			}
		});

		stopBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent arg0) {
				stopThread();
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 删除文件
	public boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	public static void setOK() {
		stopBtn.setEnabled(false);
		updateBtn.setEnabled(true);
	}

	// 停止线程
	public static void stopThread() {
		if (getFtpFileList != null) {
			getFtpFileList.cancel = true;
			getFtpFileList.interrupt();
			stopBtn.setEnabled(false);
			cn.imgdpu.GSAGUI.setStatus("用户中止更新!等待线程结束...");
		}
	}

	// 保存站点
	public static void saveSite() {
		ArrayList<String> siteArr = new ArrayList<String>();
		for (int i = 0; i < table.getItemCount(); i++) {
			TableItem tabItem = table.getItem(i);
			siteArr.add(tabItem.getText(0));
			siteArr.add(tabItem.getText(1));
			siteArr.add(tabItem.getText(2));
			siteArr.add(tabItem.getText(4));
			siteArr.add(tabItem.getText(3));
			siteArr.add(tabItem.getText(5));
		}

		cn.imgdpu.util.SqlProcess.setSiteList("ftp", siteArr);
	}

	// 读取站点
	public static void readSite() {
		if (!cn.imgdpu.GSAGUI.shell.isDisposed())
			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					table.removeAll();
					ArrayList<String> siteArr = new ArrayList<String>();
					siteArr = cn.imgdpu.util.SqlProcess.getData("select ip,user,pwd,updatetime,info,filetable from ftplist");
					if (siteArr.size() > 0)
						for (int i = 0; i < siteArr.size(); i += 6) {
							new TableItem(table, SWT.BORDER).setText(new String[] { siteArr.get(i), siteArr.get(i + 1), siteArr.get(i + 2),
									siteArr.get(i + 3), siteArr.get(i + 4), siteArr.get(i + 5) });
						}

				}

			});
	}

}
