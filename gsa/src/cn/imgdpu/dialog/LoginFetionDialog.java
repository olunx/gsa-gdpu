/*
 *@author olunx , 创建时间:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This:登陆窗口，填写学号，密码
*/

package cn.imgdpu.dialog;

import java.io.UnsupportedEncodingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
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
import org.eclipse.swt.widgets.Text;

import cn.imgdpu.util.Base64;
import cn.imgdpu.util.GeneralMethod;

import com.swtdesigner.SWTResourceManager;

public class LoginFetionDialog extends Dialog {

	private Text pwdText;
	private Text idText;
	protected Object result;
	protected Shell shell;
	public static CLabel label;

	/**
	 * Create the dialog
	 * @param parent
	 * @param style
	 */
	public LoginFetionDialog(Shell parent, int style) {
		super(parent, style);
	}

	/**
	 * Create the dialog
	 * @param parent
	 */
	public LoginFetionDialog(Shell parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Open the dialog
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
		GeneralMethod.getGeneralMethod().setDisLoc(shell , 259 , 186);//设置显示位置
		shell.setSize(259, 186);
		shell.setText("飞信");

		final Group idGroup = new Group(shell, SWT.NONE);
		idGroup.setLayout(new FillLayout());
		final FormData fd_idGroup = new FormData();
		fd_idGroup.bottom = new FormAttachment(0, 50);
		fd_idGroup.top = new FormAttachment(0, 10);
		fd_idGroup.right = new FormAttachment(0, 244);
		fd_idGroup.left = new FormAttachment(0, 10);
		idGroup.setLayoutData(fd_idGroup);
		idGroup.setText("手机号码");

		idText = new Text(idGroup, SWT.BORDER);

		final Group pwdGroup = new Group(shell, SWT.NONE);
		pwdGroup.setLayout(new FillLayout());
		final FormData fd_pwdGroup = new FormData();
		fd_pwdGroup.bottom = new FormAttachment(0, 95);
		fd_pwdGroup.top = new FormAttachment(0, 55);
		fd_pwdGroup.right = new FormAttachment(0, 244);
		fd_pwdGroup.left = new FormAttachment(0, 10);
		pwdGroup.setLayoutData(fd_pwdGroup);
		pwdGroup.setText("密码");

		pwdText = new Text(pwdGroup, SWT.BORDER|SWT.PASSWORD);

		final Button sureBut = new Button(shell, SWT.NONE);
		final FormData fd_sureBut = new FormData();
		fd_sureBut.bottom = new FormAttachment(0, 129);
		fd_sureBut.top = new FormAttachment(0, 101);
		fd_sureBut.right = new FormAttachment(0, 123);
		fd_sureBut.left = new FormAttachment(0, 10);
		sureBut.setLayoutData(fd_sureBut);
		sureBut.setText("确定");

		final Button cancelBut = new Button(shell, SWT.NONE);
		final FormData fd_cancelBut = new FormData();
		fd_cancelBut.bottom = new FormAttachment(0, 129);
		fd_cancelBut.top = new FormAttachment(0, 101);
		fd_cancelBut.right = new FormAttachment(0, 242);
		fd_cancelBut.left = new FormAttachment(0, 129);
		cancelBut.setLayoutData(fd_cancelBut);
		cancelBut.setText("取消");
		
		label = new CLabel(shell, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(LoginFetionDialog.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_label = new FormData();
		fd_label.bottom = new FormAttachment(100, -5);
		fd_label.left = new FormAttachment(sureBut, 0, SWT.LEFT);
		fd_label.right = new FormAttachment(cancelBut, 0, SWT.RIGHT);
		fd_label.top = new FormAttachment(sureBut, 5, SWT.BOTTOM);
		label.setLayoutData(fd_label);
		label.setText("第一次使用需要输入账号密码!^_^");
		//
		
		//上方为自动生成，下面添加事件处理
		cancelBut.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				shell.dispose();
			}});
		
		sureBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				if(idText.getText().equals("") || pwdText.getText().equals("")) {
					label.setText("你是否漏了点什么？~(>_<)~");
				}else {
					
					//加密，然后存入xml文件中
					byte[] pwdCode = null;
					try {
						pwdCode = Base64.encode(pwdText.getText().getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e1) {
						cn.imgdpu.util.CatException.getMethod().catException(e1, "不支持的编码类型");
					}
					String s = idText.getText() +"#"+ new String(pwdCode);
					cn.imgdpu.util.XmlProcess.setCdata("fetion", s);
					cn.imgdpu.util.XmlProcess.setAct("fetionact", "1");
					shell.dispose();
					
				}
				
				
				
			}
			
		});

	}

}
