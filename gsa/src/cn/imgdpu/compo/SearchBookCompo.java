/*
 *@author olunx , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This: 搜索书籍界面
 */

package cn.imgdpu.compo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.eclipse.swt.widgets.Text;

import cn.imgdpu.dialog.BookInfoDialog;
import cn.imgdpu.dialog.SendFetionDialog;
import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.net.SearchBook;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class SearchBookCompo extends Composite {

	private Text key3;
	private Text key2;
	private Text key1;
	private static Table bookList;
	private static CLabel pageStat;
	private Button frontBut, nextBut, searchBut;

	static int bCount;
	static int bNo = 1;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public SearchBookCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		bookList = new Table(this, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		bookList.setToolTipText("选中书籍双击可以查看出借信息");
		final FormData fd_bookList = new FormData();
		fd_bookList.bottom = new FormAttachment(91, 0);
		fd_bookList.left = new FormAttachment(0, 5);
		fd_bookList.top = new FormAttachment(0, 5);
		fd_bookList.right = new FormAttachment(100, -5);
		bookList.setLayoutData(fd_bookList);
		bookList.setLinesVisible(true);
		bookList.setHeaderVisible(true);

		final TableColumn bookNo = new TableColumn(bookList, SWT.NONE);
		bookNo.setWidth(30);
		bookNo.setText("序");

		final TableColumn bookLink = new TableColumn(bookList, SWT.NONE);
		bookLink.setText("链接");
		final TableColumn bookName = new TableColumn(bookList, SWT.NONE);
		bookName.setWidth(460);
		bookName.setText("书名/作者");
		final TableColumn bookDate = new TableColumn(bookList, SWT.NONE);
		bookDate.setWidth(60);
		bookDate.setText("出版年");
		final TableColumn bookID = new TableColumn(bookList, SWT.NONE);
		bookID.setWidth(106);
		bookID.setText("索书号");
		final TableColumn bookLocale = new TableColumn(bookList, SWT.NONE);
		bookLocale.setWidth(80);
		bookLocale.setText("所在位置");

		final CLabel label = new CLabel(this, SWT.CENTER);
		label.setImage(SWTResourceManager.getImage(SearchBookCompo.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 80);
		fd_label.top = new FormAttachment(100, -31);
		fd_label.left = new FormAttachment(0, 5);
		fd_label.bottom = new FormAttachment(100, -5);
		label.setLayoutData(fd_label);
		label.setText("关键词：");

		searchBut = new Button(this, SWT.NONE);
		searchBut.setToolTipText("搜索");
		searchBut.setImage(SWTResourceManager.getImage(SearchBookCompo.class, "/cn/imgdpu/ico/gotoobj_tsk.gif"));
		final FormData fd_searchBut = new FormData();
		fd_searchBut.right = new FormAttachment(64, 0);
		fd_searchBut.left = new FormAttachment(53, 0);
		fd_searchBut.bottom = new FormAttachment(100, -5);
		fd_searchBut.top = new FormAttachment(100, -31);
		searchBut.setLayoutData(fd_searchBut);
		searchBut.setText("搜索");

		key1 = new Text(this, SWT.BORDER);
		key1.setToolTipText("关键词1");
		final FormData fd_key1 = new FormData();
		fd_key1.right = new FormAttachment(23, 0);
		fd_key1.top = new FormAttachment(100, -31);
		fd_key1.left = new FormAttachment(0, 80);
		fd_key1.bottom = new FormAttachment(100, -5);
		key1.setLayoutData(fd_key1);

		key2 = new Text(this, SWT.BORDER);
		key2.setToolTipText("关键词2(可不填)");
		final FormData fd_key2 = new FormData();
		fd_key2.left = new FormAttachment(key1, 8, SWT.DEFAULT);
		fd_key2.right = new FormAttachment(38, 0);
		fd_key2.bottom = new FormAttachment(100, -5);
		fd_key2.top = new FormAttachment(100, -31);
		key2.setLayoutData(fd_key2);

		key3 = new Text(this, SWT.BORDER);
		key3.setToolTipText("关键词3(关键词越多，搜索精度越大)");
		final FormData fd_key3 = new FormData();
		fd_key3.left = new FormAttachment(key2, 8, SWT.DEFAULT);
		fd_key3.right = new FormAttachment(52, 0);
		fd_key3.bottom = new FormAttachment(100, -5);
		fd_key3.top = new FormAttachment(100, -31);
		key3.setLayoutData(fd_key3);

		nextBut = new Button(this, SWT.NONE);
		nextBut.setToolTipText("下一页");
		final FormData fd_nextBut = new FormData();
		fd_nextBut.left = new FormAttachment(100, -75);
		fd_nextBut.bottom = new FormAttachment(100, -5);
		fd_nextBut.top = new FormAttachment(100, -31);
		fd_nextBut.right = new FormAttachment(100, -5);
		nextBut.setLayoutData(fd_nextBut);
		nextBut.setText("下一页");

		frontBut = new Button(this, SWT.NONE);
		frontBut.setToolTipText("上一页");
		final FormData fd_frontBut = new FormData();
		fd_frontBut.right = new FormAttachment(100, -80);
		fd_frontBut.left = new FormAttachment(100, -150);
		fd_frontBut.bottom = new FormAttachment(100, -5);
		fd_frontBut.top = new FormAttachment(100, -31);
		frontBut.setLayoutData(fd_frontBut);
		frontBut.setText("上一页");

		pageStat = new CLabel(this, SWT.NONE);
		pageStat.setToolTipText("页码");
		final FormData fd_pageStat = new FormData();
		fd_pageStat.left = new FormAttachment(100, -265);
		fd_pageStat.top = new FormAttachment(100, -31);
		fd_pageStat.bottom = new FormAttachment(100, -5);
		fd_pageStat.right = new FormAttachment(100, -155);
		pageStat.setLayoutData(fd_pageStat);

		//

		// 设置swtItem的高度
		bookList.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(bookList));

		// 搜索按钮
		searchBut.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (key1.getText().trim().equals("") && key2.getText().trim().equals("") && key3.getText().trim().equals("")) {
					cn.imgdpu.GSAGUI.setStatus("书籍名称不能为空！");
				} else {
					cn.imgdpu.GSAGUI.setStatus("正在用力读取数据中...等等，再等等~(>_<)~");
					bNo = 1;
					readBookList();
				}

			}

		});

		// 双击事件
		bookList.addListener(SWT.DefaultSelection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				// 得到选中行，取出行号
				TableItem[] selectItem = bookList.getSelection();
				String itemLink = selectItem[0].getText(1);
				String itemBookName = selectItem[0].getText(2);
				new BookInfoDialog(cn.imgdpu.GSAGUI.shell).open(itemLink, itemBookName);
			}

		});

		nextBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				if (bCount > 1 && bNo < bCount) {
					bNo++;
					if (bNo <= bCount) {
						cn.imgdpu.GSAGUI.setStatus("正在读取下一页...^_^");
						readBookList();
					}

				} else {
					cn.imgdpu.GSAGUI.setStatus("已经是最后一页了，后面什么都没有了！~(>_<)~");

				}
			}

		});

		frontBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if (bCount > 1 && bNo > 1) {
					bNo--;
					cn.imgdpu.GSAGUI.setStatus("正在读取上一页...");
					readBookList();

				} else {
					cn.imgdpu.GSAGUI.setStatus("已经是第一页了，前面都没东西了！~(>_<)~");
				}

			}

		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// 读取书籍信息
	protected void readBookList() {

		final String key_1 = key1.getText().trim();
		final String key_2 = key2.getText().trim();
		final String key_3 = key3.getText().trim();
		final String no = String.valueOf(bNo);

		setEnable();

		new Thread() {

			public void run() {

				try {
					final ArrayList<String> books = new SearchBook(key_1, key_2, key_3, no).getSearchBook();
					cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
						public void run() {
							setBookList(books);
						}
					});
				} catch (NullPointerException e) {
					cn.imgdpu.util.CatException.getMethod().catException(e, "空指针");
				}
				setEnable();
			}
		}.start();
	}

	// 设置按钮状态
	void setEnable() {
		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (searchBut.isEnabled()) {
					searchBut.setEnabled(false);
				} else {
					searchBut.setEnabled(true);
				}

				if (frontBut.isEnabled()) {
					frontBut.setEnabled(false);
				} else {
					frontBut.setEnabled(true);
				}

				if (nextBut.isEnabled()) {
					nextBut.setEnabled(false);
				} else {
					nextBut.setEnabled(true);
				}
			}

		});
	}

	// 设置到课程表
	protected void setBookList(ArrayList<String> books) {
		bookList.removeAll();

		if (books.size() > 0) {
			bCount = Integer.parseInt(books.get(0));
			bNo = Integer.parseInt(books.get(1));

			for (int i = 2; i < books.size(); i += 6) {
				TableItem item = new TableItem(bookList, SWT.NONE);
				item.setText(new String[] { books.get(i), books.get(i + 1), books.get(i + 2), books.get(i + 3), books.get(i + 4), books.get(i + 5) });
			}

			// 添加右键菜单
			final Menu tableMenu = new Menu(bookList);
			bookList.setMenu(tableMenu);

			final MenuItem sendSms = new MenuItem(tableMenu, SWT.NONE);
			sendSms.setText("发送到手机");
			sendSms.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/cpyqual_menu.gif"));

			final MenuItem copyInfo = new MenuItem(tableMenu, SWT.NONE);
			copyInfo.setText("复制书籍信息");
			copyInfo.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

			final MenuItem goBook = new MenuItem(tableMenu, SWT.NONE);
			goBook.setText("预览此书内容");
			goBook.setImage(SWTResourceManager.getImage(HttpFilesCompo.class, "/cn/imgdpu/ico/cheatsheet_view.gif"));

			// 右键菜单事件
			copyInfo.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {

					// 得到选中行，取出相对链接
					TableItem[] selectItem = bookList.getSelection();

					if (selectItem.length >= 1) {
						// 获取位置判断
						String where = null;
						if (selectItem[0].getText(5) == "↑") {
							int id = bookList.getSelectionIndex();

							try {
								for (int i = id; i >= 0; i--) {
									if (bookList.getItem(i).getText(5) != selectItem[0].getText(5)) {
										where = bookList.getItem(i).getText(5);
										break;
									}
								}
							} catch (IllegalArgumentException ire) {
								cn.imgdpu.util.CatException.getMethod().catException(ire, "缺少参数");
							}

						} else {
							where = selectItem[0].getText(5);
						}

						// 复制书籍信息
						GeneralMethod.getGeneralMethod().copyToSystem(selectItem[0].getText(2) + "\n\n" + selectItem[0].getText(4) + "\n\n" + where);

						cn.imgdpu.GSAGUI.setStatusAsyn("复制书籍信息成功！");
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}
				}
			});

			sendSms.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {

					// 得到选中行，取出相对链接
					TableItem[] selectItem = bookList.getSelection();

					if (selectItem.length >= 1) {
						// 获取位置判断
						String where = null;
						if (selectItem[0].getText(5) == "↑") {

							// 当前行的行号，不要用获取界面上序号的方法，会出错，自己计算行号
							int id = bookList.getSelectionIndex();

							try {
								for (int i = id; i >= 0; i--) {
									if (bookList.getItem(i).getText(5) != selectItem[0].getText(5)) {
										where = bookList.getItem(i).getText(5);
										break;
									}
								}
							} catch (IllegalArgumentException ire) {
								cn.imgdpu.util.CatException.getMethod().catException(ire, "缺少参数");
							}

						} else {
							where = selectItem[0].getText(5);
						}

						// 设置发送内容
						String sms = selectItem[0].getText(2) + "\n\n" + selectItem[0].getText(4) + "\n\n" + where;

						// 发送短信对话框
						new SendFetionDialog(cn.imgdpu.GSAGUI.shell, sms).open();
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}

				}
			});

			goBook.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {

					// 得到选中行，取出相对链接
					TableItem[] selectItem = bookList.getSelection();

					if (selectItem.length >= 1) {
						String bookName = selectItem[0].getText(2);
						String url = null;

						try {
							bookName = bookName.substring(0, bookName.indexOf("／"));
							url = "http://books.google.cn/books?q=" + java.net.URLEncoder.encode(bookName, "UTF-8") + "";
						} catch (UnsupportedEncodingException e1) {
							cn.imgdpu.util.CatException.getMethod().catException(e1, "不支持编码类型");
						}

						try {
							Runtime.getRuntime().exec("explorer \"" + url + "\"");
						} catch (IOException e1) {
							cn.imgdpu.util.CatException.getMethod().catException(e1, "IO异常");
						}
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}
				}
			});

			pageStat.setText("第" + bNo + "页，" + "共" + bCount + "页");
			cn.imgdpu.GSAGUI.setStatus("读取数据完成!");
		} else {
			cn.imgdpu.GSAGUI.setStatus("没有任何相关的书籍!");
		}

	}// end of setBookList

}
