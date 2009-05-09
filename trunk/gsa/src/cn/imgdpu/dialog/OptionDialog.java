/*
 *@author olunx , Time:2009-3-23
 *
 *Website : http://www.olunx.com
 *
 *This: 设置对话框
 */

package cn.imgdpu.dialog;

import java.io.UnsupportedEncodingException;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import cn.imgdpu.net.GetMyBookInfo;
import cn.imgdpu.util.AutoRun;
import cn.imgdpu.util.Base64;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class OptionDialog extends Dialog {

	private StyledText dayNoText;
	private Text pidPwdText;
	private StyledText pidText;
	private Text idPwdText;
	private StyledText idText;
	protected Object result;
	protected Shell shell;

	private Button icoCBut, trayCBut, midCBut, smsCBut, autoRunBut, miniRunBut;

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public OptionDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public OptionDialog(Shell parent) {
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
		shell = new Shell(getParent(), SWT.APPLICATION_MODAL | SWT.TITLE | SWT.BORDER | SWT.CLOSE);
		shell.setLayout(new FormLayout());
		shell.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/configure.gif"));
		GeneralMethod.getGeneralMethod().setDisLoc(shell, 404, 372);// 设置显示位置
		shell.setSize(404, 372);
		shell.setText("设置");

		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		final FormData fd_tabFolder = new FormData();
		fd_tabFolder.left = new FormAttachment(0, 5);
		fd_tabFolder.right = new FormAttachment(100, -5);
		fd_tabFolder.top = new FormAttachment(0, 5);
		tabFolder.setLayoutData(fd_tabFolder);

		final TabItem perTabItem = new TabItem(tabFolder, SWT.NONE);
		perTabItem.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/newint_wiz.gif"));
		perTabItem.setText("个人信息");

		final Composite infoCompo = new Composite(tabFolder, SWT.NONE);
		infoCompo.setLayout(new FormLayout());
		perTabItem.setControl(infoCompo);

		final Group bookGroup = new Group(infoCompo, SWT.NONE);
		bookGroup.setToolTipText("非校园网用户无法使用！");
		bookGroup.setText("借书状态");
		final FormData fd_bookGroup = new FormData();
		fd_bookGroup.left = new FormAttachment(0, 5);
		fd_bookGroup.top = new FormAttachment(0, 5);
		bookGroup.setLayoutData(fd_bookGroup);
		bookGroup.setLayout(new FormLayout());

		final CLabel idLabel = new CLabel(bookGroup, SWT.NONE);
		idLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/javabean.gif"));
		final FormData fd_idLabel = new FormData();
		fd_idLabel.bottom = new FormAttachment(0, 30);
		fd_idLabel.right = new FormAttachment(0, 105);
		fd_idLabel.top = new FormAttachment(0, 5);
		fd_idLabel.left = new FormAttachment(0, 5);
		idLabel.setLayoutData(fd_idLabel);
		idLabel.setText("学号：");

		final CLabel idPwsLabel = new CLabel(bookGroup, SWT.NONE);
		idPwsLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/javabean.gif"));
		final FormData fd_idPwsLabel = new FormData();
		fd_idPwsLabel.bottom = new FormAttachment(0, 60);
		fd_idPwsLabel.right = new FormAttachment(idLabel, 0, SWT.RIGHT);
		fd_idPwsLabel.top = new FormAttachment(idLabel, 5, SWT.BOTTOM);
		fd_idPwsLabel.left = new FormAttachment(idLabel, 0, SWT.LEFT);
		idPwsLabel.setLayoutData(fd_idPwsLabel);
		idPwsLabel.setText("借书密码：");

		idText = new StyledText(bookGroup, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER);
		final FormData fd_idText = new FormData();
		fd_idText.bottom = new FormAttachment(idLabel, 0, SWT.BOTTOM);
		fd_idText.right = new FormAttachment(100, -5);
		fd_idText.top = new FormAttachment(idLabel, 0, SWT.TOP);
		fd_idText.left = new FormAttachment(idLabel, 0, SWT.RIGHT);
		idText.setLayoutData(fd_idText);

		idPwdText = new Text(bookGroup, SWT.BORDER | SWT.PASSWORD);
		final FormData fd_idPwdText = new FormData();
		fd_idPwdText.bottom = new FormAttachment(idPwsLabel, 0, SWT.BOTTOM);
		fd_idPwdText.top = new FormAttachment(idText, 5, SWT.BOTTOM);
		fd_idPwdText.right = new FormAttachment(idText, 0, SWT.RIGHT);
		fd_idPwdText.left = new FormAttachment(idPwsLabel, 0, SWT.RIGHT);
		idPwdText.setLayoutData(fd_idPwdText);

		Group timeGroup;
		timeGroup = new Group(infoCompo, SWT.NONE);
		fd_bookGroup.right = new FormAttachment(timeGroup, 0, SWT.RIGHT);
		fd_bookGroup.bottom = new FormAttachment(timeGroup, -5, SWT.TOP);

		final CLabel netLabel = new CLabel(bookGroup, SWT.NONE);
		netLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_netLabel = new FormData();
		fd_netLabel.bottom = new FormAttachment(0, 90);
		fd_netLabel.right = new FormAttachment(idPwdText, -5, SWT.LEFT);
		fd_netLabel.top = new FormAttachment(idPwsLabel, 5, SWT.BOTTOM);
		fd_netLabel.left = new FormAttachment(idPwsLabel, 0, SWT.LEFT);
		netLabel.setLayoutData(fd_netLabel);
		netLabel.setText("网络类型：");

		final CLabel bookLabel = new CLabel(bookGroup, SWT.NONE);
		bookLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_bookLabel = new FormData();
		fd_bookLabel.bottom = new FormAttachment(100, -5);
		fd_bookLabel.right = new FormAttachment(idPwdText, 0, SWT.RIGHT);
		fd_bookLabel.top = new FormAttachment(netLabel, 5, SWT.BOTTOM);
		fd_bookLabel.left = new FormAttachment(netLabel, 0, SWT.LEFT);
		bookLabel.setLayoutData(fd_bookLabel);
		bookLabel.setText("非校园网用户无法使用[借书信息]功能！");

		final Button campRBut = new Button(bookGroup, SWT.RADIO);
		campRBut.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/jtypeassist_co.gif"));
		final FormData fd_campRBut = new FormData();
		fd_campRBut.right = new FormAttachment(0, 210);
		fd_campRBut.left = new FormAttachment(idPwsLabel, 5, SWT.RIGHT);
		fd_campRBut.bottom = new FormAttachment(netLabel, 0, SWT.BOTTOM);
		fd_campRBut.top = new FormAttachment(idPwsLabel, 5, SWT.BOTTOM);
		campRBut.setLayoutData(fd_campRBut);
		campRBut.setText("校园网");

		final Button adslRBut = new Button(bookGroup, SWT.RADIO);
		adslRBut.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/goto_definition.gif"));
		final FormData fd_adslRBut = new FormData();
		fd_adslRBut.right = new FormAttachment(0, 315);
		fd_adslRBut.left = new FormAttachment(campRBut, 5, SWT.RIGHT);
		fd_adslRBut.bottom = new FormAttachment(campRBut, 0, SWT.BOTTOM);
		fd_adslRBut.top = new FormAttachment(campRBut, 0, SWT.TOP);
		adslRBut.setLayoutData(fd_adslRBut);
		adslRBut.setText("非校园网");
		timeGroup.setText("短信发送");
		final FormData fd_timeGroup = new FormData();
		fd_timeGroup.left = new FormAttachment(0, 5);
		fd_timeGroup.bottom = new FormAttachment(100, -43);
		fd_timeGroup.top = new FormAttachment(0, 145);
		fd_timeGroup.right = new FormAttachment(100, -5);
		timeGroup.setLayoutData(fd_timeGroup);
		timeGroup.setLayout(new FormLayout());

		final CLabel pidLabel = new CLabel(timeGroup, SWT.NONE);
		pidLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/javabean.gif"));
		final FormData fd_pidLabel = new FormData();
		fd_pidLabel.bottom = new FormAttachment(0, 30);
		fd_pidLabel.right = new FormAttachment(0, 105);
		fd_pidLabel.top = new FormAttachment(0, 5);
		fd_pidLabel.left = new FormAttachment(0, 5);
		pidLabel.setLayoutData(fd_pidLabel);
		pidLabel.setText("手机：");

		final CLabel pidPwdLabel = new CLabel(timeGroup, SWT.NONE);
		pidPwdLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/javabean.gif"));
		final FormData fd_pidPwdLabel = new FormData();
		fd_pidPwdLabel.right = new FormAttachment(pidLabel, 0, SWT.RIGHT);
		fd_pidPwdLabel.top = new FormAttachment(pidLabel, 5, SWT.BOTTOM);
		fd_pidPwdLabel.left = new FormAttachment(pidLabel, 0, SWT.LEFT);
		pidPwdLabel.setLayoutData(fd_pidPwdLabel);
		pidPwdLabel.setText("飞信密码：");

		pidText = new StyledText(timeGroup, SWT.BORDER | SWT.SINGLE);
		final FormData fd_pidText = new FormData();
		fd_pidText.bottom = new FormAttachment(pidLabel, 0, SWT.BOTTOM);
		fd_pidText.right = new FormAttachment(100, -5);
		fd_pidText.top = new FormAttachment(pidLabel, 0, SWT.TOP);
		fd_pidText.left = new FormAttachment(pidLabel, 0, SWT.RIGHT);
		pidText.setLayoutData(fd_pidText);

		pidPwdText = new Text(timeGroup, SWT.BORDER | SWT.PASSWORD);
		final FormData fd_pidPwdText = new FormData();
		fd_pidPwdText.bottom = new FormAttachment(pidPwdLabel, 0, SWT.BOTTOM);
		fd_pidPwdText.left = new FormAttachment(pidPwdLabel, 0, SWT.RIGHT);
		fd_pidPwdText.right = new FormAttachment(pidText, 0, SWT.RIGHT);
		fd_pidPwdText.top = new FormAttachment(pidLabel, 5, SWT.BOTTOM);
		pidPwdText.setLayoutData(fd_pidPwdText);

		final CLabel warnLabel = new CLabel(infoCompo, SWT.NONE);
		warnLabel.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_warnLabel = new FormData();
		fd_warnLabel.left = new FormAttachment(timeGroup, 0, SWT.LEFT);
		fd_warnLabel.bottom = new FormAttachment(100, -8);
		fd_warnLabel.top = new FormAttachment(timeGroup, 5, SWT.BOTTOM);
		fd_warnLabel.right = new FormAttachment(timeGroup, 0, SWT.RIGHT);
		warnLabel.setLayoutData(fd_warnLabel);
		warnLabel.setText("所有密码都会经过加密保存！");

		final TabItem otherTabItem = new TabItem(tabFolder, SWT.NONE);
		otherTabItem.setText("其它设置");
		otherTabItem.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/newenum_wiz.gif"));

		final Composite otherCompo = new Composite(tabFolder, SWT.NONE);
		otherCompo.setLayout(new FormLayout());
		otherTabItem.setControl(otherCompo);

		final Group systemGroup = new Group(otherCompo, SWT.NONE);
		final FormData fd_systemGroup = new FormData();
		fd_systemGroup.bottom = new FormAttachment(0, 230);
		fd_systemGroup.right = new FormAttachment(100, -10);
		fd_systemGroup.left = new FormAttachment(0, 5);
		fd_systemGroup.top = new FormAttachment(0, 185);
		systemGroup.setLayoutData(fd_systemGroup);
		systemGroup.setLayout(new FormLayout());
		systemGroup.setText("系统设置");

		autoRunBut = new Button(systemGroup, SWT.CHECK);
		final FormData fd_autoRunBut = new FormData();
		fd_autoRunBut.bottom = new FormAttachment(100, -5);
		fd_autoRunBut.right = new FormAttachment(0, 155);
		fd_autoRunBut.top = new FormAttachment(0, 5);
		fd_autoRunBut.left = new FormAttachment(0, 5);
		autoRunBut.setLayoutData(fd_autoRunBut);
		autoRunBut.setText("开机自动运行程序");

		miniRunBut = new Button(systemGroup, SWT.CHECK);
		final FormData fd_miniRunBut = new FormData();
		fd_miniRunBut.bottom = new FormAttachment(100, -5);
		fd_miniRunBut.top = new FormAttachment(autoRunBut, -20, SWT.BOTTOM);
		fd_miniRunBut.right = new FormAttachment(0, 340);
		fd_miniRunBut.left = new FormAttachment(0, 190);
		miniRunBut.setLayoutData(fd_miniRunBut);
		miniRunBut.setText("启动后最小化");

		final Group ringGroup = new Group(otherCompo, SWT.NONE);
		ringGroup.setText("借书提醒设置");
		final FormData fd_ringGroup = new FormData();
		fd_ringGroup.top = new FormAttachment(0, 5);
		fd_ringGroup.right = new FormAttachment(systemGroup, 0, SWT.RIGHT);
		fd_ringGroup.left = new FormAttachment(0, 5);
		fd_ringGroup.bottom = new FormAttachment(systemGroup, -5, SWT.TOP);
		ringGroup.setLayoutData(fd_ringGroup);
		ringGroup.setLayout(new FormLayout());

		final CLabel label = new CLabel(ringGroup, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 145);
		fd_label.bottom = new FormAttachment(0, 25);
		fd_label.top = new FormAttachment(0, 5);
		fd_label.left = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("在[我借的书]到期前");

		dayNoText = new StyledText(ringGroup, SWT.BORDER);
		dayNoText.setText("3");
		final FormData fd_dayNoText = new FormData();
		fd_dayNoText.right = new FormAttachment(0, 165);
		fd_dayNoText.top = new FormAttachment(label, 0, SWT.TOP);
		fd_dayNoText.bottom = new FormAttachment(label, 0, SWT.BOTTOM);
		fd_dayNoText.left = new FormAttachment(label, 0, SWT.RIGHT);
		dayNoText.setLayoutData(fd_dayNoText);

		final CLabel label_1 = new CLabel(ringGroup, SWT.NONE);
		final FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(dayNoText, 5, SWT.RIGHT);
		fd_label_1.right = new FormAttachment(100, -5);
		fd_label_1.bottom = new FormAttachment(dayNoText, 0, SWT.BOTTOM);
		fd_label_1.top = new FormAttachment(dayNoText, 0, SWT.TOP);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("天开始提醒我。");

		final CLabel label_2 = new CLabel(ringGroup, SWT.NONE);
		label_2.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_label_2 = new FormData();
		fd_label_2.bottom = new FormAttachment(0, 50);
		fd_label_2.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_label_2.right = new FormAttachment(0, 95);
		fd_label_2.left = new FormAttachment(label, 0, SWT.LEFT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("提醒方式：");

		icoCBut = new Button(ringGroup, SWT.CHECK);
		final FormData fd_icoCBut = new FormData();
		fd_icoCBut.right = new FormAttachment(label_1, 0, SWT.RIGHT);
		fd_icoCBut.bottom = new FormAttachment(label_2, 0, SWT.BOTTOM);
		fd_icoCBut.top = new FormAttachment(label_2, 0, SWT.TOP);
		fd_icoCBut.left = new FormAttachment(label_2, 0, SWT.RIGHT);
		icoCBut.setLayoutData(fd_icoCBut);
		icoCBut.setText("静默提醒(更换托盘图标样式)");

		trayCBut = new Button(ringGroup, SWT.CHECK);
		final FormData fd_trayCBut = new FormData();
		fd_trayCBut.right = new FormAttachment(icoCBut, 0, SWT.RIGHT);
		fd_trayCBut.bottom = new FormAttachment(0, 70);
		fd_trayCBut.top = new FormAttachment(label_2, 0, SWT.BOTTOM);
		fd_trayCBut.left = new FormAttachment(label_2, 0, SWT.RIGHT);
		trayCBut.setLayoutData(fd_trayCBut);
		trayCBut.setText("弹窗提醒(在托盘图标处弹出提示窗口)");

		midCBut = new Button(ringGroup, SWT.CHECK);
		final FormData fd_midCBut = new FormData();
		fd_midCBut.right = new FormAttachment(trayCBut, 0, SWT.RIGHT);
		fd_midCBut.bottom = new FormAttachment(0, 90);
		fd_midCBut.top = new FormAttachment(trayCBut, 0, SWT.BOTTOM);
		fd_midCBut.left = new FormAttachment(trayCBut, 0, SWT.LEFT);
		midCBut.setLayoutData(fd_midCBut);
		midCBut.setText("声音提醒(播放系统提示声音)");

		smsCBut = new Button(ringGroup, SWT.CHECK);
		final FormData fd_smsCBut = new FormData();
		fd_smsCBut.right = new FormAttachment(midCBut, 0, SWT.RIGHT);
		fd_smsCBut.bottom = new FormAttachment(0, 110);
		fd_smsCBut.top = new FormAttachment(midCBut, 0, SWT.BOTTOM);
		fd_smsCBut.left = new FormAttachment(midCBut, 0, SWT.LEFT);
		smsCBut.setLayoutData(fd_smsCBut);
		smsCBut.setText("短信提醒(请确认飞信帐号密码填写正确)");

		final CLabel label_3 = new CLabel(ringGroup, SWT.NONE);
		label_3.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_label_3 = new FormData();
		fd_label_3.bottom = new FormAttachment(0, 130);
		fd_label_3.left = new FormAttachment(0, 30);
		fd_label_3.top = new FormAttachment(smsCBut, 0, SWT.BOTTOM);
		fd_label_3.right = new FormAttachment(smsCBut, 0, SWT.RIGHT);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("可多选，若不选择任何一项，将不进行任何提醒。");

		final CLabel label_4 = new CLabel(ringGroup, SWT.NONE);
		label_4.setText("要实现提醒功能，均需保证程序在运行状态。");
		label_4.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_label_4 = new FormData();
		fd_label_4.bottom = new FormAttachment(100, -5);
		fd_label_4.right = new FormAttachment(label_3, 0, SWT.RIGHT);
		fd_label_4.top = new FormAttachment(label_3, 5, SWT.BOTTOM);
		fd_label_4.left = new FormAttachment(label_2, 0, SWT.LEFT);
		label_4.setLayoutData(fd_label_4);

		final CLabel warnLabel_1 = new CLabel(otherCompo, SWT.NONE);
		warnLabel_1.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_warnLabel_1 = new FormData();
		fd_warnLabel_1.bottom = new FormAttachment(100, -5);
		fd_warnLabel_1.right = new FormAttachment(systemGroup, 0, SWT.RIGHT);
		fd_warnLabel_1.top = new FormAttachment(systemGroup, 5, SWT.BOTTOM);
		fd_warnLabel_1.left = new FormAttachment(systemGroup, 0, SWT.LEFT);
		warnLabel_1.setLayoutData(fd_warnLabel_1);
		warnLabel_1.setText("修改设置后请注意保存，不然你的设置就可能不起作用了。");

		final Button defaultBut = new Button(shell, SWT.NONE);
		defaultBut.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/refresh.gif"));
		final FormData fd_defaultBut = new FormData();
		fd_defaultBut.left = new FormAttachment(0, 10);
		fd_defaultBut.top = new FormAttachment(0, 305);
		fd_defaultBut.bottom = new FormAttachment(100, -5);
		fd_defaultBut.right = new FormAttachment(0, 125);
		defaultBut.setLayoutData(fd_defaultBut);
		defaultBut.setToolTipText("将会清空所有已经设置的信息");
		defaultBut.setText("还原默认设置");

		Button sureBut;
		sureBut = new Button(shell, SWT.NONE);
		fd_tabFolder.bottom = new FormAttachment(sureBut, -5, SWT.TOP);
		sureBut.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/saveas_edit.gif"));
		final FormData fd_sureBut = new FormData();
		fd_sureBut.left = new FormAttachment(0, 200);
		fd_sureBut.right = new FormAttachment(0, 290);
		fd_sureBut.bottom = new FormAttachment(defaultBut, 0, SWT.BOTTOM);
		fd_sureBut.top = new FormAttachment(defaultBut, 0, SWT.TOP);
		sureBut.setLayoutData(fd_sureBut);
		sureBut.setToolTipText("保存当前设置");
		sureBut.setText("保存");

		final Button cancelBut = new Button(shell, SWT.NONE);
		cancelBut.setImage(SWTResourceManager.getImage(OptionDialog.class, "/cn/imgdpu/ico/delete_edit.gif"));
		final FormData fd_cancelBut = new FormData();
		fd_cancelBut.right = new FormAttachment(0, 385);
		fd_cancelBut.bottom = new FormAttachment(100, -5);
		fd_cancelBut.left = new FormAttachment(sureBut, 5, SWT.RIGHT);
		fd_cancelBut.top = new FormAttachment(sureBut, 0, SWT.TOP);
		cancelBut.setLayoutData(fd_cancelBut);
		cancelBut.setToolTipText("关闭窗口");
		cancelBut.setText("关闭");
		//

		// 保存用户帐号密码
		isSaveYet();

		// 其它设置选项状况
		isSetYet();

		// 设置网络类型
		if (cn.imgdpu.util.XmlProcess.isAct("network") == 1) {
			if (cn.imgdpu.util.XmlProcess.getCdata("network").equals("1")) {
				campRBut.setSelection(true);
			} else {
				adslRBut.setSelection(true);
			}
		} else {
			campRBut.setSelection(true);
		}

		// ----------------------------------
		// 复位个人信息按钮
		defaultBut.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event e) {
				cn.imgdpu.util.XmlProcess.setAct("bookact", "0");
				cn.imgdpu.util.XmlProcess.setCdata("userinfo", "");
				cn.imgdpu.util.XmlProcess.setCdata("mybook", "");
				cn.imgdpu.util.XmlProcess.setAct("fetionact", "0");
				cn.imgdpu.util.XmlProcess.setCdata("fetion", "");
				cn.imgdpu.util.XmlProcess.setAct("tableact", "0");

				idText.setText("");
				idPwdText.setText("");
				pidText.setText("");
				pidPwdText.setText("");

				warnLabel.setText("用户信息已清空！");

				cn.imgdpu.util.XmlProcess.setAct("icoring", "1");
				cn.imgdpu.util.XmlProcess.setAct("trayring", "1");
				cn.imgdpu.util.XmlProcess.setAct("midring", "1");
				cn.imgdpu.util.XmlProcess.setAct("smsring", "0");
				cn.imgdpu.util.XmlProcess.setAct("autorun", "0");
				cn.imgdpu.util.XmlProcess.setAct("minirun", "0");

				icoCBut.setSelection(true);
				trayCBut.setSelection(true);
				midCBut.setSelection(true);
				smsCBut.setSelection(false);
				autoRunBut.setSelection(false);
				miniRunBut.setSelection(false);
				warnLabel_1.setText("已经还原为默认设置！");
			}
		});

		// 个人信息设置关闭按钮
		cancelBut.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				shell.dispose();
			}
		});

		// 个人信息设置确定按钮事件处理
		sureBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				boolean canClose = true;
				ArrayList<String> s = new ArrayList<String>(4);
				s.add(idText.getText().trim());
				s.add(idPwdText.getText().trim());
				s.add(pidText.getText().trim());
				s.add(pidPwdText.getText().trim());

				// 保存借书信息
				if (s.get(0) != "" && s.get(1) != "") {

					byte[] pwdCode = null;
					try {
						pwdCode = Base64.encode(s.get(1).getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e1) {
						cn.imgdpu.util.CatException.getMethod().catException(e1, "不支持的编码类型");
					}

					doSaveUserInfo(s.get(0) + "#" + new String(pwdCode));
					warnLabel.setText("设置保存成功！O(∩_∩)O");
				}
				if (campRBut.getSelection()) {

					// 1代表校园网，2代表非校园网
					cn.imgdpu.util.XmlProcess.setCdata("network", "1");
				} else if (adslRBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setCdata("network", "2");
					bookLabel.setText("你选择的是[非校园网]，你将无法使用[借书状态]功能！");
				}

				// 借书帐号密码为空
				if (s.get(0) == "" && s.get(1) == "") {
					cn.imgdpu.util.XmlProcess.setAct("bookact", "0");
					cn.imgdpu.util.XmlProcess.setCdata("userinfo", "");
					cn.imgdpu.util.XmlProcess.setCdata("mybook", "");
				}

				// 保存飞信
				if (s.get(2).length() == 11) {
					if (s.get(3) != "") {

						byte[] pwdCode = null;
						try {
							pwdCode = Base64.encode(s.get(3).getBytes("UTF-8"));
						} catch (UnsupportedEncodingException e1) {
							cn.imgdpu.util.CatException.getMethod().catException(e1, "不支持的编码类型");
						}

						cn.imgdpu.util.XmlProcess.setCdata("fetion", s.get(2) + "#" + new String(pwdCode));
						cn.imgdpu.util.XmlProcess.setAct("fetionact", "1");

						warnLabel.setText("设置保存成功！O(∩_∩)O");

					} else {
						canClose = false;
						warnLabel.setText("密码不能为空！~(>_<)~");
					}
				} else if (s.get(2).length() > 1 && s.get(2).length() < 11) {
					canClose = false;
					warnLabel.setText("手机号码填写不正确！~(>_<)~");
				} else if (s.get(2) == "" && s.get(3) == "") {
					cn.imgdpu.util.XmlProcess.setAct("fetionact", "0");
					cn.imgdpu.util.XmlProcess.setCdata("fetion", "");
				}

				// 其他信息
				if (dayNoText.getText() != "") {
					try {
						if (Integer.parseInt(dayNoText.getText()) > 99)
							dayNoText.setText("99");
					} catch (NumberFormatException e1) {
						dayNoText.setText("3");
						cn.imgdpu.util.CatException.getMethod().catException(e1, "数字格式不正确");
					}
					cn.imgdpu.util.XmlProcess.setCdata("ringday", dayNoText.getText());

				}

				if (icoCBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setAct("icoring", "1");
				} else {
					cn.imgdpu.util.XmlProcess.setAct("icoring", "0");
				}
				if (trayCBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setAct("trayring", "1");
				} else {
					cn.imgdpu.util.XmlProcess.setAct("trayring", "0");
				}
				if (midCBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setAct("midring", "1");
				} else {
					cn.imgdpu.util.XmlProcess.setAct("midring", "0");
				}
				if (smsCBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setAct("smsring", "1");
				} else {
					cn.imgdpu.util.XmlProcess.setAct("smsring", "0");
				}
				if (autoRunBut.getSelection()) {

					String osType = System.getProperty("user.dir").substring(0, 1);

					// 通过判断更目录名来获得当前系统类型
					if (osType.equals("/")) {

						MessageBox box = new MessageBox(cn.imgdpu.GSAGUI.shell, SWT.ICON_WORKING | SWT.OK);
						box.setText("提示");
						box.setMessage("你使用的是Linux类操作系统，GSA目前不支持Linux开机启动!");
						box.open();

					} else {
						cn.imgdpu.util.XmlProcess.setAct("autorun", "1");
						new AutoRun().regAdd("GSA", cn.imgdpu.util.FileUrlConv.UrlConvIo("gsa.exe"));// 添加
					}

				} else {
					cn.imgdpu.util.XmlProcess.setAct("autorun", "0");
					new AutoRun().regDelete("GSA");
				}
				if (miniRunBut.getSelection()) {
					cn.imgdpu.util.XmlProcess.setAct("minirun", "1");
				} else {
					cn.imgdpu.util.XmlProcess.setAct("minirun", "0");
				}

				warnLabel_1.setText("配置保存成功！O(∩_∩)O");
				if (canClose)
					shell.dispose();

			}// end of event
		});

	}

	// 保存学号和密码，设置借书状态为可读
	public void doSaveUserInfo(String s) {

		// 将传回的数据写入配置文件
		cn.imgdpu.util.XmlProcess.setCdata("userinfo", s);

		// 写入1，代表用户借书帐号和密码已输入
		cn.imgdpu.util.XmlProcess.setAct("bookact", "1");

		GetMyBookInfo userinfo = new GetMyBookInfo();
		userinfo.start();

	}

	// 判断是否已经输入个人信息，如果是则setText
	private void isSaveYet() {

		// 借书帐号密码
		if (cn.imgdpu.util.XmlProcess.isAct("bookact") == 1) {
			String s = cn.imgdpu.util.XmlProcess.getCdata("userinfo");
			String[] split = s.split("#");// 按星期分开
			if (split.length == 2) {
				idText.setText(split[0]);

				// 解密
				byte[] result;
				try {
					result = split[1].getBytes("UTF-8");
					split[1] = new String(Base64.decode(new String(result, "UTF-8")), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
				}

				idPwdText.setText(split[1]);

			}

		}

		// 飞信帐号密码
		if (cn.imgdpu.util.XmlProcess.isAct("fetionact") == 1) {

			String s = cn.imgdpu.util.XmlProcess.getCdata("fetion");
			String[] split = s.split("#");// 按星期分开
			if (split.length == 2) {
				pidText.setText(split[0]);

				// 解密
				byte[] result;
				try {
					result = split[1].getBytes("UTF-8");
					split[1] = new String(Base64.decode(new String(result, "UTF-8")), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
				}
				pidPwdText.setText(split[1]);
			}
		}

	}

	// 设置选中项的状态
	private void isSetYet() {

		if (!cn.imgdpu.util.XmlProcess.getCdata("ringday").equals("")) {
			dayNoText.setText(cn.imgdpu.util.XmlProcess.getCdata("ringday"));
		}
		if (cn.imgdpu.util.XmlProcess.isAct("icoring") == 1) {
			icoCBut.setSelection(true);
		}
		if (cn.imgdpu.util.XmlProcess.isAct("trayring") == 1) {
			trayCBut.setSelection(true);
		}
		if (cn.imgdpu.util.XmlProcess.isAct("midring") == 1) {
			midCBut.setSelection(true);
		}
		if (cn.imgdpu.util.XmlProcess.isAct("smsring") == 1) {
			smsCBut.setSelection(true);
		}
		if (cn.imgdpu.util.XmlProcess.isAct("autorun") == 1) {
			autoRunBut.setSelection(true);
		}
		if (cn.imgdpu.util.XmlProcess.isAct("minirun") == 1) {
			miniRunBut.setSelection(true);
		}
	}
}
