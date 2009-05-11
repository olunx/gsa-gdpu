/*
 *@author olunx , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This: 音乐播放界面
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

public class MusicCompo extends Composite {

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public MusicCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		final Composite musicCompo = new Composite(this, SWT.NONE);
		musicCompo.setLayout(new FillLayout());
		final FormData fd_musicCompo = new FormData();
		fd_musicCompo.right = new FormAttachment(100, -160);
		fd_musicCompo.bottom = new FormAttachment(100, -5);
		fd_musicCompo.top = new FormAttachment(0, 5);
		fd_musicCompo.left = new FormAttachment(0, 5);
		musicCompo.setLayoutData(fd_musicCompo);

		final Browser musicPlayer = new Browser(musicCompo, SWT.NONE);
		musicPlayer.setVisible(false);

		final Group warnGroup = new Group(this, SWT.NONE);
		warnGroup.setLayout(new FillLayout());
		warnGroup.setText("提示");
		final FormData fd_warnGroup = new FormData();
		fd_warnGroup.bottom = new FormAttachment(musicCompo, 0, SWT.BOTTOM);
		fd_warnGroup.right = new FormAttachment(100, -5);
		fd_warnGroup.left = new FormAttachment(musicCompo, 5, SWT.RIGHT);
		warnGroup.setLayoutData(fd_warnGroup);

		final Label label = new Label(warnGroup, SWT.WRAP);
		label.setText("声明：\n    [随便听听(校园网)]功能的所有数据均来自中diggcd.com，版权归diggcd.com所有。\n\n    此功能针对教育网网络，校园网用户能得到更好的速度体验。"
				+ "\n\n提示：\n    此功能比较耗费系统资源，在每次查看后均可关闭此功能，下次程序重启时就会还原默认。\n\n    欲浏览更多音乐信息，请登陆diggcd的网站：http://diggcd.com/");

		final Button loadBut;
		loadBut = new Button(this, SWT.NONE);
		loadBut.setToolTipText("加载Flash音乐播放器");
		loadBut.setImage(SWTResourceManager.getImage(MusicCompo.class, "/cn/imgdpu/ico/icon.gif"));
		final FormData fd_loadBut = new FormData();
		fd_loadBut.bottom = new FormAttachment(0, 30);
		fd_loadBut.right = new FormAttachment(warnGroup, 0, SWT.RIGHT);
		fd_loadBut.top = new FormAttachment(musicCompo, 0, SWT.TOP);
		fd_loadBut.left = new FormAttachment(musicCompo, 5, SWT.RIGHT);
		loadBut.setLayoutData(fd_loadBut);
		loadBut.setText("加载播放器");

		final Button disBut;
		disBut = new Button(this, SWT.NONE);
		fd_warnGroup.top = new FormAttachment(disBut, 5, SWT.BOTTOM);
		disBut.setToolTipText("关闭Flash音乐播放器");
		disBut.setImage(SWTResourceManager.getImage(MusicCompo.class, "/cn/imgdpu/ico/delete_layout.gif"));
		final FormData fd_disBut = new FormData();
		fd_disBut.bottom = new FormAttachment(0, 60);
		fd_disBut.top = new FormAttachment(loadBut, 5, SWT.DEFAULT);
		fd_disBut.right = new FormAttachment(warnGroup, 0, SWT.RIGHT);
		fd_disBut.left = new FormAttachment(loadBut, 0, SWT.LEFT);
		disBut.setLayoutData(fd_disBut);
		disBut.setText("关闭播放器");
		disBut.setEnabled(false);

		//

		// 加载flash按钮
		loadBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				// Browser musicPlayer = new Browser(musicCompo , SWT.NONE);
				musicPlayer.setUrl("http://www.diggcd.com/Player_v20.swf");
				musicPlayer.setVisible(true);
				loadBut.setEnabled(false);
				disBut.setEnabled(true);

			}

		});

		// 销毁flash按钮
		disBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {

				musicPlayer.setUrl("");
				musicPlayer.setVisible(false);
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
