/*
 *@author olunx , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This: 台风信息界面
 */

package cn.imgdpu.compo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.swtdesigner.SWTResourceManager;

public class TyphoonCompo extends Composite {

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public TyphoonCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		final Composite typCompo = new Composite(this, SWT.NONE);
		typCompo.setLayout(new FillLayout());
		final FormData fd_typCompo = new FormData();
		fd_typCompo.right = new FormAttachment(100, -160);
		fd_typCompo.bottom = new FormAttachment(100, -5);
		fd_typCompo.top = new FormAttachment(0, 5);
		fd_typCompo.left = new FormAttachment(0, 5);
		typCompo.setLayoutData(fd_typCompo);

		// final FlashPlayer typPlayer = new FlashPlayer(typCompo);
		final Browser typPlayer = new Browser(typCompo, SWT.None);
		typPlayer.setVisible(false);

		final Group warnGroup = new Group(this, SWT.NONE);
		warnGroup.setLayout(new FillLayout());
		final FormData fd_warnGroup = new FormData();
		fd_warnGroup.bottom = new FormAttachment(typCompo, 0, SWT.BOTTOM);
		fd_warnGroup.right = new FormAttachment(100, -5);
		fd_warnGroup.left = new FormAttachment(typCompo, 5, SWT.RIGHT);
		warnGroup.setLayoutData(fd_warnGroup);

		final Label label = new Label(warnGroup, SWT.WRAP);
		label.setText("声明：\n    [台风消息]功能的所有数据均来自中央气象台，版权归中央气象台所有。\n\n    [台风消息]的所有数据数据均和中央气象台官方数据同步，无需进行手动的更新。"
				+ "\n\n提示：\n    此功能比较耗费系统资源，在每次查看后均可关闭此功能，下次程序重启时就会还原默认。\n\n    欲浏览更多天气信息，请登陆中央气象台的网站：http://www.weather.com.cn/");

		final Button loadBut = new Button(this, SWT.NONE);
		loadBut.setImage(SWTResourceManager.getImage(TyphoonCompo.class, "/cn/imgdpu/ico/icon.gif"));
		final FormData fd_loadBut = new FormData();
		fd_loadBut.bottom = new FormAttachment(0, 30);
		fd_loadBut.right = new FormAttachment(warnGroup, 0, SWT.RIGHT);
		fd_loadBut.top = new FormAttachment(typCompo, 0, SWT.TOP);
		fd_loadBut.left = new FormAttachment(typCompo, 5, SWT.RIGHT);
		loadBut.setLayoutData(fd_loadBut);
		loadBut.setText("加载数据");

		final Button disBut;
		disBut = new Button(this, SWT.NONE);
		fd_warnGroup.top = new FormAttachment(disBut, 5, SWT.BOTTOM);
		disBut.setImage(SWTResourceManager.getImage(TyphoonCompo.class, "/cn/imgdpu/ico/delete_layout.gif"));
		final FormData fd_disBut = new FormData();
		fd_disBut.bottom = new FormAttachment(0, 60);
		fd_disBut.top = new FormAttachment(loadBut, 5, SWT.DEFAULT);
		fd_disBut.right = new FormAttachment(warnGroup, 0, SWT.RIGHT);
		fd_disBut.left = new FormAttachment(loadBut, 0, SWT.LEFT);
		disBut.setLayoutData(fd_disBut);
		disBut.setText("关闭");

		//

		// 加载flash按钮
		loadBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				// Browser musicPlayer = new Browser(musicCompo , SWT.NONE);
				typPlayer.setUrl("http://flash.weather.com.cn/typhoon/taifeng.swf");
				typPlayer.setVisible(true);
				loadBut.setEnabled(false);
				disBut.setEnabled(true);

			}

		});

		// 销毁flash按钮
		disBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				typPlayer.setUrl("");
				typPlayer.setVisible(false);
				disBut.setEnabled(false);
				loadBut.setEnabled(true);

			}

		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
