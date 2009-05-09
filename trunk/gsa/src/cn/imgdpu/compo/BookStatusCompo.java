/*
 *@author olunx , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This: 借书状态界面
 */

package cn.imgdpu.compo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cn.imgdpu.dialog.BookInfoDialog;

import com.swtdesigner.SWTResourceManager;

public class BookStatusCompo extends Composite {

	private static Table table;
	static CLabel userStatus;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public BookStatusCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setToolTipText("当前用户的借书信息");
		final FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -40);
		fd_table.top = new FormAttachment(0, 35);
		fd_table.right = new FormAttachment(100, -5);
		fd_table.left = new FormAttachment(0, 5);
		table.setLayoutData(fd_table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		//设置swtItem的高度
		table.addListener(SWT.MeasureItem,new cn.imgdpu.event.SwtTableItemHeightListener(table));

		userStatus = new CLabel(this, SWT.NONE);
		userStatus.setImage(SWTResourceManager.getImage(BookStatusCompo.class, "/cn/imgdpu/ico/separator.gif"));
		userStatus.setToolTipText("当前用户的借书信息");

		final TableColumn deadTime = new TableColumn(table, SWT.NONE);
		deadTime.setToolTipText("还书截止日期");
		deadTime.setWidth(95);
		deadTime.setText("最迟应还期");

		final TableColumn link = new TableColumn(table, SWT.NONE);
		link.setText("链接");

		final TableColumn author = new TableColumn(table, SWT.NONE);
		author.setWidth(400);
		author.setText("书名/作者");

		final TableColumn bookType = new TableColumn(table, SWT.NONE);
		bookType.setWidth(100);
		bookType.setText("图书类型");

		final TableColumn loginID = new TableColumn(table, SWT.NONE);
		loginID.setWidth(73);
		loginID.setText("登录号");
		final FormData fd_userStatus = new FormData();
		fd_userStatus.bottom = new FormAttachment(table, -5, SWT.TOP);
		fd_userStatus.left = new FormAttachment(0, 5);
		fd_userStatus.right = new FormAttachment(100, -5);
		fd_userStatus.top = new FormAttachment(0, 5);
		userStatus.setLayoutData(fd_userStatus);
		userStatus.setText("用户信息：");

		CLabel label;
		label = new CLabel(this, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(BookStatusCompo.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(table, 5, SWT.BOTTOM);
		fd_label.left = new FormAttachment(0, 5);
		fd_label.right = new FormAttachment(0, 495);
		fd_label.bottom = new FormAttachment(100, -5);
		label.setLayoutData(fd_label);
		label.setText("注意：逾期不还，需缴纳不少的罚金哦！O(∩_∩)O （此功能有时候仅校园网用户可用）");

		//
		
		//双击事件
		table.addListener(SWT.DefaultSelection, new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				// 得到选中行，取出行号
				TableItem[] selectItem = table.getSelection();
				String itemLink = selectItem[0].getText(1);
				String itemBookName = selectItem[0].getText(2);
				new BookInfoDialog(cn.imgdpu.GSAGUI.shell).open(itemLink,itemBookName);
			}
			
		});

		setBookInfo();

	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	//显示到界面
	public static void setBookInfo() {
		// 判断课程表是否已经设置，0未设，1已设
		int isAct = cn.imgdpu.util.XmlProcess.isAct("bookact");

		if (isAct == 1) {
			String[] s = readUserInfo();

			table.setItemCount(0);
			for (int i = 0; i < s.length && s.length > 5; i += 5) {
				if (i < 5)
					userStatus.setText("用户信息：" + s[i] + "  " + s[i + 1] + "  " + s[i + 2] + "  " + s[i + 3] + "  " + s[i + 4]);
				if (i >= 5) {
					// 动态创建item项目
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] { s[i], s[i + 1], s[i + 2], s[i + 3], s[i + 4] });
				}

			}

		}
	}

	// 读取借书信息，返回字符串
	public static String[] readUserInfo() {

		String s = cn.imgdpu.util.XmlProcess.getCdata("mybook");

		String[] split = s.split("@@");// 按星期分开

		return split;

	}
	

}
