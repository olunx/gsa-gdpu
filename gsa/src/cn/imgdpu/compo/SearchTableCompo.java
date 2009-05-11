/*
 *@author olunx , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This: 搜索课程表界面
 */

package cn.imgdpu.compo;

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

import cn.imgdpu.dialog.ViewTableInfoDialog;
import cn.imgdpu.event.SwtTableItemHeightListener;
import cn.imgdpu.net.GetTimetable;
import cn.imgdpu.util.XmlProcess;

import com.swtdesigner.SWTResourceManager;

public class SearchTableCompo extends Composite {

	private Text classNameText;
	private static Table showTimeTable;
	private static Button searchBut, setTableButton;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public SearchTableCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		showTimeTable = new Table(this, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		showTimeTable.setToolTipText("选择班级，单击右键添加");
		final FormData fd_showTimeTable = new FormData();
		fd_showTimeTable.bottom = new FormAttachment(100, -65);
		fd_showTimeTable.right = new FormAttachment(100, -5);
		fd_showTimeTable.top = new FormAttachment(0, 5);
		fd_showTimeTable.left = new FormAttachment(0, 5);
		showTimeTable.setLayoutData(fd_showTimeTable);
		showTimeTable.setLinesVisible(true);
		showTimeTable.setHeaderVisible(true);

		final TableColumn ID = new TableColumn(showTimeTable, SWT.NONE);
		ID.setWidth(72);
		ID.setText("序号");

		final TableColumn className = new TableColumn(showTimeTable, SWT.NONE);
		className.setWidth(410);
		className.setText("班级");

		final CLabel infoLabel = new CLabel(this, SWT.NONE);
		infoLabel.setImage(SWTResourceManager.getImage(SearchTableCompo.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_infoLabel = new FormData();
		fd_infoLabel.right = new FormAttachment(0, 582);
		fd_infoLabel.top = new FormAttachment(100, -60);
		fd_infoLabel.bottom = new FormAttachment(100, -35);
		fd_infoLabel.left = new FormAttachment(0, 5);
		infoLabel.setLayoutData(fd_infoLabel);
		infoLabel.setText("提示：例如“医药软件工程07”，只需输入“软件”进行搜索即可。(大学城课表处理较完善)");

		final CLabel classLabel = new CLabel(this, SWT.NONE);
		classLabel.setImage(SWTResourceManager.getImage(SearchTableCompo.class, "/cn/imgdpu/ico/title.gif"));
		final FormData fd_classLabel = new FormData();
		fd_classLabel.top = new FormAttachment(100, -30);
		fd_classLabel.bottom = new FormAttachment(100, -5);
		fd_classLabel.left = new FormAttachment(0, 5);
		fd_classLabel.right = new FormAttachment(0, 70);
		classLabel.setLayoutData(fd_classLabel);
		classLabel.setText("班级：");

		classNameText = new Text(this, SWT.BORDER);
		classNameText.setToolTipText("输入班级名称");
		final FormData fd_classNameText = new FormData();
		fd_classNameText.top = new FormAttachment(100, -30);
		fd_classNameText.bottom = new FormAttachment(100, -5);
		fd_classNameText.left = new FormAttachment(0, 70);
		fd_classNameText.right = new FormAttachment(0, 280);
		classNameText.setLayoutData(fd_classNameText);

		searchBut = new Button(this, SWT.NONE);
		searchBut.setToolTipText("点击进行搜索");
		searchBut.setImage(SWTResourceManager.getImage(SearchTableCompo.class, "/cn/imgdpu/ico/gotoobj_tsk.gif"));
		final FormData fd_searchBut = new FormData();
		fd_searchBut.right = new FormAttachment(0, 375);
		fd_searchBut.top = new FormAttachment(100, -30);
		fd_searchBut.bottom = new FormAttachment(100, -5);
		fd_searchBut.left = new FormAttachment(0, 285);
		searchBut.setLayoutData(fd_searchBut);
		searchBut.setText("搜索");

		setTableButton = new Button(this, SWT.NONE);
		setTableButton.setImage(SWTResourceManager.getImage(SearchTableCompo.class, "/cn/imgdpu/ico/add_action.gif"));
		final FormData fd_setTableButton = new FormData();
		fd_setTableButton.left = new FormAttachment(100, -112);
		fd_setTableButton.top = new FormAttachment(infoLabel, 5, SWT.BOTTOM);
		fd_setTableButton.right = new FormAttachment(100, -5);
		fd_setTableButton.bottom = new FormAttachment(100, -5);
		setTableButton.setLayoutData(fd_setTableButton);
		setTableButton.setText("查看课程表");
		//

		// 设置swtItem的高度
		showTimeTable.addListener(SWT.MeasureItem, new SwtTableItemHeightListener(showTimeTable));

		searchBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				if (classNameText.getText().trim().equals("")) {
					cn.imgdpu.GSAGUI.setStatus("班级名称不能为空！");
				} else {
					searchBut.setEnabled(false);
					cn.imgdpu.GSAGUI.setStatus("正在用力读取数据中...~(>_<)~");
					new ReadTableList(classNameText.getText().trim()).start();
				}

			}

		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public static void setTableList() {

		ArrayList<String> classNameTable = new XmlProcess().ReadClassXml();

		// 如果表中有数据就清空
		showTimeTable.removeAll();

		// 如果size等于一并且get(0)为空，则证明没有返回数据，也就是所没有这个班级的课程表
		if (classNameTable.size() == 1 && String.valueOf(classNameTable.get(0)).equals("null")) {
			cn.imgdpu.GSAGUI.setStatus("居然没有相关数据，你是不是输错名字了？~(>_<)~");
		} else {

			// 动态创建item项目
			for (int i = 0; i < classNameTable.size(); i++) {
				TableItem item = new TableItem(showTimeTable, SWT.NONE);
				item.setText(new String[] { String.valueOf(i), classNameTable.get(i) });
			}

			// 设置右键菜单
			final Menu tableMenu = new Menu(showTimeTable);
			showTimeTable.setMenu(tableMenu);

			final MenuItem viewInfo = new MenuItem(tableMenu, SWT.NONE);
			viewInfo.setText("查看详情");
			viewInfo.setImage(SWTResourceManager.getImage(SearchTableCompo.class, "/cn/imgdpu/ico/copy_edit.gif"));

			cn.imgdpu.GSAGUI.setStatus("获取课程表成功了!O(∩_∩)O");

			// 双击事件
			showTimeTable.addListener(SWT.DefaultSelection, new Listener() {
				@Override
				public void handleEvent(Event e) {
					// 得到选中行，取出行号
					TableItem[] selectItem = showTimeTable.getSelection();

					if (selectItem.length >= 1) {
						int itemNo = Integer.parseInt(selectItem[0].getText(0));

						// itemNo参数为要显示的班级item
						new ViewTableInfoDialog(cn.imgdpu.GSAGUI.shell).open(itemNo);
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}
				}
			});

			// 为“查看详情”添加事件处理
			viewInfo.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event e) {

					// 得到选中行，取出行号
					TableItem[] selectItem = showTimeTable.getSelection();

					if (selectItem.length >= 1) {
						int itemNo = Integer.parseInt(selectItem[0].getText(0));

						// itemNo参数为要显示的班级item
						new ViewTableInfoDialog(cn.imgdpu.GSAGUI.shell).open(itemNo);
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}
				}
			});

			setTableButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event e) {

					// 得到选中行，取出行号
					TableItem[] selectItem = showTimeTable.getSelection();

					if (selectItem.length >= 1) {
						int itemNo = Integer.parseInt(selectItem[0].getText(0));

						new ViewTableInfoDialog(cn.imgdpu.GSAGUI.shell).open(itemNo);
					} else {
						cn.imgdpu.GSAGUI.setStatusAsyn("你没有选中任何项目！~(>_<)~");
					}
				}

			});

		}

		searchBut.setEnabled(true);
	}

}

// 用线程读取课程表
class ReadTableList extends Thread {

	String className;

	ReadTableList(String s1) {
		className = s1;
	}

	public void run() {

		new GetTimetable().getClassTime(className);

		cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				cn.imgdpu.compo.SearchTableCompo.setTableList();
			}
		});
	}
}
