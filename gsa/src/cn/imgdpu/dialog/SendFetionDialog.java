/*
 *@author olunx , Time:2009-4-3
 *
 *Website : http://www.olunx.com
 *
 *This: 发送短信对话框
 */

package cn.imgdpu.dialog;

import java.io.UnsupportedEncodingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import cn.imgdpu.fetion.Fetion;
import cn.imgdpu.util.Base64;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class SendFetionDialog extends Dialog {

	private StyledText timeSText;
	private static StyledText smsSText;
	private StyledText phoneSText;
	protected Object result;
	public static Shell shell;
	static CLabel warnLab;
	static Button sendSmsBut;
	protected String sms = new String();

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public SendFetionDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public SendFetionDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	public SendFetionDialog(Shell parent, String s) {
		this(parent, SWT.NONE);
		sms = s;
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new FormLayout());
		shell.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/local.gif"));
		GeneralMethod.getGeneralMethod().setDisLoc(shell, 350, 297);// 设置显示位置
		shell.setSize(350, 333);
		shell.setText("发短信给自己");

		final Group group = new Group(shell, SWT.NONE);
		group.setText("手机");
		group.setLayout(new FormLayout());
		final FormData fd_group = new FormData();
		fd_group.right = new FormAttachment(100, -5);
		fd_group.top = new FormAttachment(0, 5);
		fd_group.left = new FormAttachment(0, 5);
		group.setLayoutData(fd_group);

		final CLabel label = new CLabel(group, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/classf_obj.gif"));
		final FormData fd_label = new FormData();
		fd_label.bottom = new FormAttachment(0, 30);
		fd_label.top = new FormAttachment(0, 5);
		fd_label.right = new FormAttachment(0, 100);
		fd_label.left = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("我的手机：");

		phoneSText = new StyledText(group, SWT.FULL_SELECTION | SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		phoneSText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		phoneSText.setEnabled(false);
		phoneSText.setToolTipText("我的手机号码");
		final FormData fd_phoneSText = new FormData();
		fd_phoneSText.left = new FormAttachment(label, 5, SWT.RIGHT);
		fd_phoneSText.bottom = new FormAttachment(label, 0, SWT.BOTTOM);
		fd_phoneSText.top = new FormAttachment(label, 0, SWT.TOP);
		fd_phoneSText.right = new FormAttachment(0, 220);
		phoneSText.setLayoutData(fd_phoneSText);

		final Button editPhoneBut = new Button(group, SWT.NONE);
		editPhoneBut.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/editor_pane.gif"));
		final FormData fd_editPhoneBut = new FormData();
		fd_editPhoneBut.bottom = new FormAttachment(phoneSText, 0, SWT.BOTTOM);
		fd_editPhoneBut.top = new FormAttachment(0, 5);
		fd_editPhoneBut.right = new FormAttachment(100, -5);
		fd_editPhoneBut.left = new FormAttachment(phoneSText, 5, SWT.RIGHT);
		editPhoneBut.setLayoutData(fd_editPhoneBut);
		editPhoneBut.setText("修改号码");

		Group conGroup;
		conGroup = new Group(shell, SWT.NONE);
		fd_group.bottom = new FormAttachment(conGroup, -5, SWT.TOP);
		conGroup.setText("短信内容预览");
		final FormData fd_conGroup = new FormData();
		fd_conGroup.top = new FormAttachment(0, 95);
		fd_conGroup.bottom = new FormAttachment(100, -5);
		fd_conGroup.right = new FormAttachment(100, -5);
		fd_conGroup.left = new FormAttachment(group, 0, SWT.LEFT);
		conGroup.setLayoutData(fd_conGroup);
		conGroup.setLayout(new FormLayout());

		smsSText = new StyledText(conGroup, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		smsSText.setToolTipText("输入要发送的内容");
		smsSText.setTextLimit(180);// 设置180字限制
		final FormData fd_smsSText = new FormData();
		fd_smsSText.bottom = new FormAttachment(0, 125);
		fd_smsSText.right = new FormAttachment(100, -5);
		fd_smsSText.top = new FormAttachment(0, 5);
		fd_smsSText.left = new FormAttachment(0, 5);
		smsSText.setLayoutData(fd_smsSText);

		warnLab = new CLabel(conGroup, SWT.NONE);
		warnLab.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/warning_obj.gif"));
		final FormData fd_warnLab = new FormData();
		fd_warnLab.top = new FormAttachment(smsSText, 5, SWT.BOTTOM);
		fd_warnLab.right = new FormAttachment(100, -5);
		fd_warnLab.left = new FormAttachment(smsSText, 0, SWT.LEFT);
		warnLab.setLayoutData(fd_warnLab);
		warnLab.setText("每次只能发送180个字符,包括中文。");

		sendSmsBut = new Button(conGroup, SWT.NONE);
		fd_warnLab.bottom = new FormAttachment(sendSmsBut, -5, SWT.TOP);
		sendSmsBut.setToolTipText("发送短信息。");
		sendSmsBut.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/newclass_wiz.gif"));
		final FormData fd_sendSmsBut = new FormData();
		fd_sendSmsBut.top = new FormAttachment(0, 160);
		fd_sendSmsBut.bottom = new FormAttachment(100, -5);
		fd_sendSmsBut.right = new FormAttachment(smsSText, 0, SWT.RIGHT);
		fd_sendSmsBut.left = new FormAttachment(smsSText, 0, SWT.LEFT);
		sendSmsBut.setLayoutData(fd_sendSmsBut);
		sendSmsBut.setText("开始发送");

		final CLabel label_1 = new CLabel(group, SWT.NONE);
		label_1.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/text_field.gif"));
		final FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(100, -5);
		fd_label_1.top = new FormAttachment(label, 5, SWT.BOTTOM);
		fd_label_1.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_label_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("发送时间：");

		timeSText = new StyledText(group, SWT.FULL_SELECTION | SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		timeSText.setText("即时");
		timeSText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		final FormData fd_timeSText = new FormData();
		fd_timeSText.bottom = new FormAttachment(label_1, 0, SWT.BOTTOM);
		fd_timeSText.right = new FormAttachment(phoneSText, 0, SWT.RIGHT);
		fd_timeSText.left = new FormAttachment(label_1, 5, SWT.RIGHT);
		fd_timeSText.top = new FormAttachment(phoneSText, 5, SWT.BOTTOM);
		timeSText.setLayoutData(fd_timeSText);

		final Button timerBut = new Button(group, SWT.NONE);
		timerBut.setImage(SWTResourceManager.getImage(SendFetionDialog.class, "/cn/imgdpu/ico/option_pane.gif"));
		final FormData fd_timerBut = new FormData();
		fd_timerBut.bottom = new FormAttachment(timeSText, 0, SWT.BOTTOM);
		fd_timerBut.right = new FormAttachment(editPhoneBut, 0, SWT.RIGHT);
		fd_timerBut.top = new FormAttachment(editPhoneBut, 5, SWT.BOTTOM);
		fd_timerBut.left = new FormAttachment(timeSText, 5, SWT.RIGHT);
		timerBut.setLayoutData(fd_timerBut);
		timerBut.setText("定时发送");
		//

		// 计算输入的字符个数
		smsSText.addListener(SWT.Modify, new Listener() {

			int left;

			@Override
			public void handleEvent(Event e) {
				left = 180 - smsSText.getText().length();
				if (left >= 0) {
					warnLab.setText("还可以输入 " + left + " 个字符！");
				} else {
					smsSText.setText(smsSText.getText().substring(0, 180));
					warnLab.setText("已经超过了一次所能发送的字符数！");
				}

			}

		});

		// 读取用户手机号码
		final String[] userInfo = readUserInfo();
		phoneSText.setText(userInfo[0]);

		// 发送Text中的数据
		sendSmsBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				String[] userInfo = readUserInfo();

				if (smsSText.getText().length() != 0) {
					if (userInfo.length == 2) {
						sendSmsBut.setEnabled(false);
						new Fetion(userInfo[0], userInfo[1], smsSText.getText().trim()).start();
						warnLab.setText("短信已经开始发送，请稍等！");

					} else {
						warnLab.setText("还没有填写手机号码信息！");
					}
				} else {
					warnLab.setText("你什么东西都没有输入！~(>_<)~");
				}

			}

		});

		// 修改飞信帐号密码
		editPhoneBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				new OptionDialog(shell).open();
				// 读取用户手机号码
				final String[] s = readUserInfo();
				phoneSText.setText(s[0]);
			}

		});

		// 定时发送按钮
		timerBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				new FetionTimerDialog(shell).open();
			}

		});

		// 设置文本框内容
		setSmsText();
	}

	// 读取用户信息，返回字符串
	public String[] readUserInfo() {

		String s = cn.imgdpu.util.XmlProcess.getCdata("fetion");

		String[] split = s.split("#");

		if (split.length > 1) {
			// 解密
			byte[] result;
			try {
				result = split[1].getBytes("UTF-8");
				split[1] = new String(Base64.decode(new String(result, "UTF-8")), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
			}
		}

		return split;

	}

	// 设置Text中的数据
	private void setSmsText() {
		if (!sms.isEmpty()) {
			smsSText.setText(sms);
		}
	}

	// 异步设置状态的内容
	public static void setStatusAsyn(final String s) {
		if (!shell.isDisposed()) {
			shell.getDisplay().asyncExec(new Runnable() {
				public void run() {
					warnLab.setText(s);
					sendSmsBut.setEnabled(true);
				}
			});
		}
	}
}
