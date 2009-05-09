/*
 *@author olunx , Time:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This: 主文件
 */

package cn.imgdpu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.swtdesigner.SWTResourceManager;

import cn.imgdpu.compo.BookStatusCompo;
import cn.imgdpu.compo.WeatherCompo;
import cn.imgdpu.dialog.FirstRunDialog;
import cn.imgdpu.event.MainButListener;
import cn.imgdpu.event.TabFolderListener;
import cn.imgdpu.event.TabItemListener;
import cn.imgdpu.util.AutoUpdate;
import cn.imgdpu.util.GeneralMethod;

public class GSAGUI {

	public static Shell shell;
	public static CLabel statuLabel;
	public static CLabel weather;
	public static AutoUpdate autoUpdate;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GSAGUI window = new GSAGUI();
		window.open();
	}

	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();

		// 判断配置文件是否存在
		isConfAlive();

		createContents();
		shell.open();
		shell.layout();

		// 最小化运行
		isMiniRun();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FormLayout());
		shell.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/local.gif"));
		GeneralMethod.getGeneralMethod().setMainDisLoc(shell, 800, 600);// 设置显示位置
		shell.setSize(800, 600);
		shell.setText("GSA");

		final Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		final MenuItem fileMenu = new MenuItem(menu, SWT.CASCADE);
		fileMenu.setText("文件");

		final Menu fileM = new Menu(fileMenu);
		fileMenu.setMenu(fileM);

		final MenuItem updateMI = new MenuItem(fileM, SWT.NONE);
		updateMI.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/jtypeassist_co.gif"));
		updateMI.setText("更新");

		final MenuItem exitMI = new MenuItem(fileM, SWT.NONE);
		exitMI.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/run_exec.gif"));
		exitMI.setText("退出");

		final MenuItem optionMenu = new MenuItem(menu, SWT.CASCADE);
		optionMenu.setText("工具");

		final Menu toolM = new Menu(optionMenu);
		optionMenu.setMenu(toolM);

		final MenuItem optionMI = new MenuItem(toolM, SWT.NONE);
		optionMI.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/configure.gif"));
		optionMI.setText("设置");

		final MenuItem helpMenu = new MenuItem(menu, SWT.CASCADE);
		helpMenu.setText("帮助");

		final Menu helpM = new Menu(helpMenu);
		helpMenu.setMenu(helpM);

		final MenuItem helpMI = new MenuItem(helpM, SWT.NONE);
		helpMI.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/template_obj.gif"));
		helpMI.setText("使用说明");

		final MenuItem aboutMI = new MenuItem(helpM, SWT.NONE);
		aboutMI.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/index_co.gif"));
		aboutMI.setText("关于GSA");

		final ToolBar toolBar = new ToolBar(shell, SWT.NONE);
		final FormData fd_toolBar = new FormData();
		fd_toolBar.right = new FormAttachment(100, -133);
		fd_toolBar.top = new FormAttachment(0, 5);
		fd_toolBar.left = new FormAttachment(0, 5);
		toolBar.setLayoutData(fd_toolBar);

		final ToolItem updateTool = new ToolItem(toolBar, SWT.PUSH);
		updateTool.setToolTipText("更新所有数据\n本软件所有数据均为自动更新");
		updateTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/jtypeassist_co.gif"));
		updateTool.setText("更新");

		final ToolItem optionTool = new ToolItem(toolBar, SWT.PUSH);
		optionTool.setToolTipText("详细设置");
		optionTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/configure.gif"));
		optionTool.setText("设置");

		final ToolItem fetionTool = new ToolItem(toolBar, SWT.PUSH);
		fetionTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/editor_pane.gif"));
		fetionTool.setText("短信");

		final ToolItem helpTool = new ToolItem(toolBar, SWT.PUSH);
		helpTool.setToolTipText("使用教程");
		helpTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/template_obj.gif"));
		helpTool.setText("帮助");

		final ToolItem aboutTool = new ToolItem(toolBar, SWT.PUSH);
		aboutTool.setToolTipText("关于GSA");
		aboutTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/index_co.gif"));
		aboutTool.setText("关于");

		final ToolItem exitTool = new ToolItem(toolBar, SWT.PUSH);
		exitTool.setToolTipText("关闭软件");
		exitTool.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/run_exec.gif"));
		exitTool.setText("退出");

		weather = new CLabel(shell, SWT.NONE);
		weather.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/home_nav.gif"));
		final FormData fd_weather = new FormData();
		fd_weather.left = new FormAttachment(100, -133);
		fd_weather.bottom = new FormAttachment(toolBar, 0, SWT.BOTTOM);
		fd_weather.right = new FormAttachment(100, -5);
		fd_weather.top = new FormAttachment(toolBar, 0, SWT.TOP);
		weather.setLayoutData(fd_weather);
		weather.setText("天气");

		TabFolder mainTabFolder;
		mainTabFolder = new TabFolder(shell, SWT.NONE);
		fd_toolBar.bottom = new FormAttachment(mainTabFolder, 0, SWT.TOP);
		final FormData fd_mainTabFolder = new FormData();
		fd_mainTabFolder.bottom = new FormAttachment(100, -22);
		fd_mainTabFolder.left = new FormAttachment(0, 5);
		fd_mainTabFolder.top = new FormAttachment(0, 45);
		fd_mainTabFolder.right = new FormAttachment(100, -5);
		mainTabFolder.setLayoutData(fd_mainTabFolder);

		final TabItem weatherMan = new TabItem(mainTabFolder, SWT.NONE);
		weatherMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/internal_browser.gif"));
		weatherMan.setText("天气预报");

		final CTabFolder weatherTabFolder = new CTabFolder(mainTabFolder, SWT.BOTTOM);
		weatherMan.setControl(weatherTabFolder);

		final CTabItem dayTabItem = new CTabItem(weatherTabFolder, SWT.NONE);
		dayTabItem.setText("天气");

		final WeatherCompo weatherCompo = new WeatherCompo(weatherTabFolder, SWT.NONE);
		dayTabItem.setControl(weatherCompo);
		weatherTabFolder.setSelection(dayTabItem);

		final CTabItem tpTabItem = new CTabItem(weatherTabFolder, SWT.NONE);
		tpTabItem.setText("台风消息");
		tpTabItem.setToolTipText("台风实时路径发布系统");

		final TabItem newsMan = new TabItem(mainTabFolder, SWT.NONE);
		newsMan.setText("校园新闻");
		newsMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "/cn/imgdpu/ico/menu_item.gif"));

		final CTabFolder newsTabFolder = new CTabFolder(mainTabFolder, SWT.NONE);
		newsTabFolder.setTabPosition(SWT.BOTTOM);
		newsMan.setControl(newsTabFolder);

		final CTabItem camTabItem = new CTabItem(newsTabFolder, SWT.NONE);
		camTabItem.setToolTipText("广药校园网新闻");
		camTabItem.setText("校园网新闻");

		final CTabItem unionTabItem = new CTabItem(newsTabFolder, SWT.NONE);
		unionTabItem.setToolTipText("广药社联新闻");
		unionTabItem.setText("社团动态");

		final CTabItem universityTabItem = new CTabItem(newsTabFolder, SWT.NONE);
		universityTabItem.setToolTipText("大学城的相关新闻");
		universityTabItem.setText("大学城新闻");

		final CTabItem tabItem_4 = new CTabItem(newsTabFolder, SWT.NONE);
		tabItem_4.setText("中国教育新闻");

		final TabItem timeMan = new TabItem(mainTabFolder, SWT.NONE);
		timeMan.setToolTipText("每天的课程信息");
		timeMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/table.gif"));
		timeMan.setText("课程表");

		final CTabFolder timeTabFolder = new CTabFolder(mainTabFolder, SWT.BOTTOM);
		timeMan.setControl(timeTabFolder);

		final CTabItem timeTableTabItem = new CTabItem(timeTabFolder, SWT.NONE);
		timeTableTabItem.setText("我的课程表");

		final CTabItem spTimetableTabItem = new CTabItem(timeTabFolder, SWT.NONE);
		spTimetableTabItem.setToolTipText("搜索广药课程表");
		spTimetableTabItem.setText("搜索课程表");

		final TabItem libMan = new TabItem(mainTabFolder, SWT.NONE);
		libMan.setToolTipText("图书馆助手");
		libMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/cheatsheet_view.gif"));
		libMan.setText("借书管理");
		// 修改setText内容，也要修改TabFolderListener类的相应值

		final CTabFolder bookTabFolder = new CTabFolder(mainTabFolder, SWT.NONE);
		bookTabFolder.setTabPosition(SWT.BOTTOM);
		libMan.setControl(bookTabFolder);

		final CTabItem bookStatusTabItem = new CTabItem(bookTabFolder, SWT.NONE);
		bookStatusTabItem.setText("借书状态");

		final BookStatusCompo bookStatusCompo = new BookStatusCompo(bookTabFolder, SWT.NONE);
		bookStatusTabItem.setControl(bookStatusCompo);
		bookTabFolder.setSelection(bookStatusTabItem);

		final CTabItem soBookTabItem = new CTabItem(bookTabFolder, SWT.NONE);
		soBookTabItem.setToolTipText("本校图书馆查询");
		soBookTabItem.setText("搜索书籍");
		// 修改setText内容，也要修改TabItemListener类的相应值

		final TabItem cetMan = new TabItem(mainTabFolder, SWT.NONE);
		cetMan.setToolTipText("大学四六级英语");
		cetMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/javadoc.gif"));
		cetMan.setText("四六英语");

		final TabItem fileMan = new TabItem(mainTabFolder, SWT.NONE);
		fileMan.setToolTipText("列出所要查看的目录或网站的所有文件");
		fileMan.setText("文件搜索");
		fileMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/list.gif"));

		final CTabFolder fileTabFolder = new CTabFolder(mainTabFolder, SWT.BOTTOM);
		fileMan.setControl(fileTabFolder);

		final CTabItem httpTabItem = new CTabItem(fileTabFolder, SWT.NONE);
		httpTabItem.setText("Ftp搜索");

		final CTabItem ftpFileTabItem = new CTabItem(fileTabFolder, SWT.NONE);
		ftpFileTabItem.setText("Ftp网站列表");

		final CTabItem ftpUpTabItem = new CTabItem(fileTabFolder, SWT.NONE);
		ftpUpTabItem.setText("Http搜索");

		final CTabItem tabItem_1 = new CTabItem(fileTabFolder, SWT.NONE);
		tabItem_1.setText("内容搜索");

		final TabItem funMan = new TabItem(mainTabFolder, SWT.NONE);
		funMan.setText("娱乐视听");
		funMan.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/slider.gif"));

		final CTabFolder funTabFolder = new CTabFolder(mainTabFolder, SWT.BOTTOM);
		funMan.setControl(funTabFolder);

		final CTabItem musicTabItem = new CTabItem(funTabFolder, SWT.NONE);
		musicTabItem.setToolTipText("教育网用户速度较快");
		musicTabItem.setText("随便听听(校园网)");

		final CTabItem tabItem = new CTabItem(funTabFolder, SWT.NONE);
		tabItem.setToolTipText("非校园网用户速度较快");
		tabItem.setText("随便听听(非校园网)");

		final CTabFolder cetTabFolder = new CTabFolder(mainTabFolder, SWT.BOTTOM);
		cetMan.setControl(cetTabFolder);

		final CTabItem engTabItem = new CTabItem(cetTabFolder, SWT.NONE);
		engTabItem.setText("成绩查询");

		final CTabItem tabItem_3 = new CTabItem(cetTabFolder, SWT.NONE);
		tabItem_3.setText("学习资料");

		statuLabel = new CLabel(shell, SWT.NONE);
		statuLabel.setImage(SWTResourceManager.getImage(GSAGUI.class, "ico/info.gif"));
		final FormData fd_statuLabel = new FormData();
		fd_statuLabel.bottom = new FormAttachment(100, -2);
		fd_statuLabel.top = new FormAttachment(100, -22);
		fd_statuLabel.right = new FormAttachment(0, 687);
		fd_statuLabel.left = new FormAttachment(0, 5);
		statuLabel.setLayoutData(fd_statuLabel);
		statuLabel.setText("状态栏");
		//

		// 点击TabItem时，触发生成所要的Composite
		mainTabFolder.addListener(SWT.Selection, new TabFolderListener(mainTabFolder));

		timeTabFolder.addListener(SWT.Selection, new TabItemListener(timeTabFolder));
		bookTabFolder.addListener(SWT.Selection, new TabItemListener(bookTabFolder));
		weatherTabFolder.addListener(SWT.Selection, new TabItemListener(weatherTabFolder));
		fileTabFolder.addListener(SWT.Selection, new TabItemListener(fileTabFolder));
		funTabFolder.addListener(SWT.Selection, new TabItemListener(funTabFolder));
		newsTabFolder.addListener(SWT.Selection, new TabItemListener(newsTabFolder));

		final CTabItem tabItem_2 = new CTabItem(newsTabFolder, SWT.NONE);
		tabItem_2.setText("大学城网站");

		// 设置按钮事件
		optionTool.addSelectionListener(new MainButListener());
		optionMI.addSelectionListener(new MainButListener());

		// 退出按钮
		exitTool.addSelectionListener(new MainButListener());
		exitMI.addSelectionListener(new MainButListener());

		// 关于按钮
		aboutTool.addSelectionListener(new MainButListener());
		aboutMI.addSelectionListener(new MainButListener());

		// 更新数据按钮
		updateMI.addSelectionListener(new MainButListener());
		updateTool.addSelectionListener(new MainButListener());

		// 帮助按钮
		helpMI.addSelectionListener(new MainButListener());
		helpTool.addSelectionListener(new MainButListener());

		// 短信按钮
		fetionTool.addSelectionListener(new MainButListener());

		// 创建系统托盘图标
		cn.imgdpu.SystemTray.createSystemTray();

		// 是否第一次运行，弹出配置界面
		isFirstRun();

		// 自动更新
		autoUpdate = new AutoUpdate().addStartAutoUpdate();
		autoUpdate.start();

	}

	// 设置状态栏的内容
	public static void setStatus(String s) {
		if (!shell.isDisposed()) {
			statuLabel.setText(s);
			statuLabel.update();
		}
	}

	// 异步设置状态栏的内容
	public static void setStatusAsyn(final String s) {
		if (!shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					setStatus(s);
				}
			});
		}
	}

	// 判断是否第一次运行程序
	private void isFirstRun() {

		if (cn.imgdpu.util.XmlProcess.isAct("firstrun") == 1) {
			new FirstRunDialog(shell).open();

			cn.imgdpu.util.XmlProcess.setAct("firstrun", "0");
		}
	}

	// 设置-其它设置-启动最小化
	private void isMiniRun() {

		if (cn.imgdpu.util.XmlProcess.isAct("minirun") == 1) {
			// shell.setMinimized(true);
		}
	}

	// 配置文件丢失处理
	private void isConfAlive() {

		File conf = new File(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\conf.xml"));
		File confPath = new File(cn.imgdpu.util.FileUrlConv.UrlConvIo("data"));
		if (!confPath.exists() && !confPath.isDirectory()) {
			confPath.mkdirs();
		}

		if (!conf.exists()) {

			StringBuilder s = new StringBuilder();

			s
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><conf><set><tableact>0</tableact><bookact>0</bookact><fetionact>0</fetionact><firstrun>1</firstrun><network>1</network><tdweather>0</tdweather><tenweather>0</tenweather><tdlive>0</tdlive><autorun>0</autorun><minirun>0</minirun><icoring>1</icoring><trayring>1</trayring><midring>1</midring><smsring>0</smsring></set><set><classname /><monday /><tueday /><wenday /><thuday /><friday /><monnig /><tuenig /><wennig /><thunig /><frinig /><tdweather /><tenweather /><tdlive /><mybook /><userinfo /><fetion /><network /><ringday /></set></conf>");

			Writer writeTable;
			try {
				writeTable = new OutputStreamWriter(new FileOutputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\conf.xml")), "UTF-8");
				writeTable.write(s.toString());
				writeTable.close();
			} catch (UnsupportedEncodingException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "不支持编码类型");
			} catch (FileNotFoundException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "找不到配置文件");
			} catch (IOException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "IO流错误");
			}
		}

	}

}
