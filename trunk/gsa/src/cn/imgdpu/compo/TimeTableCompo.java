/*
/*
 *@author olunx , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This: 我的课程表界面
 */

package cn.imgdpu.compo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import cn.imgdpu.dialog.SendFetionDialog;

import com.swtdesigner.SWTResourceManager;

public class TimeTableCompo extends Composite {

	private CCombo weekCombo;
	private static StyledText friNT, thuNT, wenNT, tueNT, monNT, friDT, thuDT, wenDT, tueDT, monDT;
	static Button editTimeBut, sendTimeBut;

	private static CLabel classLabel;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeTableCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		classLabel = new CLabel(this, SWT.NONE);
		classLabel.setImage(SWTResourceManager.getImage(TimeTableCompo.class, "/cn/imgdpu/ico/menu_bar.gif"));
		final FormData fd_classLabel = new FormData();
		fd_classLabel.right = new FormAttachment(100, -5);
		fd_classLabel.top = new FormAttachment(0, 5);
		fd_classLabel.left = new FormAttachment(0, 5);
		classLabel.setLayoutData(fd_classLabel);
		classLabel.setText("当前班级：");

		Composite dayCompo;
		dayCompo = new Composite(this, SWT.NONE);
		fd_classLabel.bottom = new FormAttachment(dayCompo, -5, SWT.TOP);
		dayCompo.setLayout(new FillLayout());
		final FormData fd_dayCompo = new FormData();
		fd_dayCompo.top = new FormAttachment(0, 35);
		fd_dayCompo.bottom = new FormAttachment(55, 0);
		fd_dayCompo.left = new FormAttachment(0, 5);
		fd_dayCompo.right = new FormAttachment(100, -5);
		dayCompo.setLayoutData(fd_dayCompo);

		final Group monDayGro = new Group(dayCompo, SWT.NONE);
		monDayGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		monDayGro.setText("星期一");
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 2;
		fillLayout.marginHeight = 2;
		monDayGro.setLayout(fillLayout);

		monDT = new StyledText(monDayGro, SWT.WRAP);
		monDT.setToolTipText("可修改");

		final Group tueDayGro = new Group(dayCompo, SWT.NONE);
		tueDayGro.setBackgroundMode(SWT.INHERIT_FORCE);
		tueDayGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		tueDayGro.setText("星期二");
		final FillLayout fillLayout_1 = new FillLayout();
		fillLayout_1.marginWidth = 2;
		fillLayout_1.marginHeight = 2;
		tueDayGro.setLayout(fillLayout_1);

		tueDT = new StyledText(tueDayGro, SWT.WRAP);
		tueDT.setToolTipText("可修改");

		final Group wenDayGro = new Group(dayCompo, SWT.NONE);
		wenDayGro.setBackgroundMode(SWT.INHERIT_DEFAULT);
		wenDayGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		wenDayGro.setText("星期三");
		final FillLayout fillLayout_2 = new FillLayout();
		fillLayout_2.marginWidth = 2;
		fillLayout_2.marginHeight = 2;
		wenDayGro.setLayout(fillLayout_2);

		wenDT = new StyledText(wenDayGro, SWT.WRAP);
		wenDT.setToolTipText("可修改");

		final Group thuDayGro = new Group(dayCompo, SWT.NONE);
		thuDayGro.setBackgroundMode(SWT.INHERIT_DEFAULT);
		thuDayGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		thuDayGro.setText("星期四");
		final FillLayout fillLayout_3 = new FillLayout();
		fillLayout_3.marginWidth = 2;
		fillLayout_3.marginHeight = 2;
		thuDayGro.setLayout(fillLayout_3);

		thuDT = new StyledText(thuDayGro, SWT.WRAP);
		thuDT.setToolTipText("可修改");

		final Group firDayGro = new Group(dayCompo, SWT.NONE);
		firDayGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		firDayGro.setText("星期五");
		final FillLayout fillLayout_4 = new FillLayout();
		fillLayout_4.marginWidth = 2;
		fillLayout_4.marginHeight = 2;
		firDayGro.setLayout(fillLayout_4);

		friDT = new StyledText(firDayGro, SWT.WRAP);
		friDT.setToolTipText("可修改");

		Composite nightCompo;
		nightCompo = new Composite(this, SWT.NONE);
		nightCompo.setLayout(new FillLayout());
		final FormData fd_nightCompo = new FormData();
		fd_nightCompo.top = new FormAttachment(56, 0);
		fd_nightCompo.bottom = new FormAttachment(100, -71);
		fd_nightCompo.right = new FormAttachment(dayCompo, 0, SWT.RIGHT);
		fd_nightCompo.left = new FormAttachment(dayCompo, 0, SWT.LEFT);
		nightCompo.setLayoutData(fd_nightCompo);

		final Group monNiGro = new Group(nightCompo, SWT.NONE);
		monNiGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		monNiGro.setText("选修一");
		final FillLayout fillLayout_5 = new FillLayout();
		fillLayout_5.marginWidth = 2;
		fillLayout_5.marginHeight = 2;
		monNiGro.setLayout(fillLayout_5);

		monNT = new StyledText(monNiGro, SWT.WRAP);
		monNT.setToolTipText("星期一选修课");

		final Group tueNiGro = new Group(nightCompo, SWT.NONE);
		tueNiGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		tueNiGro.setText("选修二");
		final FillLayout fillLayout_6 = new FillLayout();
		fillLayout_6.marginWidth = 2;
		fillLayout_6.marginHeight = 2;
		tueNiGro.setLayout(fillLayout_6);

		tueNT = new StyledText(tueNiGro, SWT.WRAP);
		tueNT.setToolTipText("星期二选修课");

		final Group wenNiGro = new Group(nightCompo, SWT.NONE);
		wenNiGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		wenNiGro.setText("选修三");
		final FillLayout fillLayout_7 = new FillLayout();
		fillLayout_7.marginWidth = 2;
		fillLayout_7.marginHeight = 2;
		wenNiGro.setLayout(fillLayout_7);

		wenNT = new StyledText(wenNiGro, SWT.WRAP);
		wenNT.setToolTipText("星期三选修课");

		final Group thuNiGro = new Group(nightCompo, SWT.NONE);
		thuNiGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		thuNiGro.setText("选修四");
		final FillLayout fillLayout_8 = new FillLayout();
		fillLayout_8.marginWidth = 2;
		fillLayout_8.marginHeight = 2;
		thuNiGro.setLayout(fillLayout_8);

		thuNT = new StyledText(thuNiGro, SWT.WRAP);
		thuNT.setToolTipText("星期四选修课");

		final Group firNiGro = new Group(nightCompo, SWT.NONE);
		firNiGro.setForeground(SWTResourceManager.getColor(0, 64, 128));
		firNiGro.setText("选修五");
		final FillLayout fillLayout_9 = new FillLayout();
		fillLayout_9.marginWidth = 2;
		fillLayout_9.marginHeight = 2;
		firNiGro.setLayout(fillLayout_9);

		friNT = new StyledText(firNiGro, SWT.WRAP);
		friNT.setToolTipText("星期五选修课");

		editTimeBut = new Button(this, SWT.NONE);
		editTimeBut.setToolTipText("添加或修改课表后点击我");
		editTimeBut.setImage(SWTResourceManager.getImage(TimeTableCompo.class, "/cn/imgdpu/ico/editor_pane.gif"));
		final FormData fd_saveTimeBut = new FormData();
		fd_saveTimeBut.left = new FormAttachment(0, 5);
		fd_saveTimeBut.top = new FormAttachment(100, -30);
		fd_saveTimeBut.right = new FormAttachment(0, 100);
		editTimeBut.setLayoutData(fd_saveTimeBut);
		editTimeBut.setText("保存修改");
		editTimeBut.setEnabled(false);

		CLabel msmLabel;
		msmLabel = new CLabel(this, SWT.NONE);
		fd_saveTimeBut.bottom = new FormAttachment(msmLabel, 0, SWT.BOTTOM);
		final FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(100, -281);
		fd_label_1.top = new FormAttachment(100, -30);
		fd_label_1.bottom = new FormAttachment(100, -5);
		fd_label_1.right = new FormAttachment(100, -5);
		msmLabel.setLayoutData(fd_label_1);
		msmLabel.setText("的课程表到我的手机上。(此功能基于飞信，免费)");

		weekCombo = new CCombo(this, SWT.BORDER | SWT.READ_ONLY);
		weekCombo.setToolTipText("选择要发送的课表");
		final FormData fd_weekCombo = new FormData();
		fd_weekCombo.top = new FormAttachment(100, -30);
		fd_weekCombo.bottom = new FormAttachment(100, -5);
		fd_weekCombo.left = new FormAttachment(100, -376);
		fd_weekCombo.right = new FormAttachment(100, -286);
		weekCombo.setLayoutData(fd_weekCombo);
		weekCombo.setItems(new String[] { "星期一", "星期二", "星期三", "星期四", "星期五" });
		weekCombo.setText("请选择");

		sendTimeBut = new Button(this, SWT.NONE);
		sendTimeBut.setToolTipText("发送包括选修的课表");
		sendTimeBut.setImage(SWTResourceManager.getImage(TimeTableCompo.class, "/cn/imgdpu/ico/filter.gif"));
		final FormData fd_sendTimeBut = new FormData();
		fd_sendTimeBut.left = new FormAttachment(100, -471);
		fd_sendTimeBut.bottom = new FormAttachment(100, -5);
		fd_sendTimeBut.top = new FormAttachment(100, -30);
		fd_sendTimeBut.right = new FormAttachment(100, -381);
		sendTimeBut.setLayoutData(fd_sendTimeBut);
		sendTimeBut.setText("发送");
		sendTimeBut.setEnabled(false);

		final CLabel label = new CLabel(this, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(TimeTableCompo.class, "/cn/imgdpu/ico/ihigh_obj.gif"));
		final FormData fd_label = new FormData();
		fd_label.bottom = new FormAttachment(msmLabel, -5, SWT.TOP);
		fd_label.top = new FormAttachment(nightCompo, 5, SWT.BOTTOM);
		fd_label.right = new FormAttachment(msmLabel, 0, SWT.RIGHT);
		fd_label.left = new FormAttachment(nightCompo, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		label.setText("可以对自己的课程表进行任意的修改，修改后记得保存就是了。O(∩_∩)O");
		
		//以上生成界面

		//读取课程表，显示出来
		setTableText();
		
		//修改课程表按钮
		editTimeBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				cn.imgdpu.util.XmlProcess.setCdata("classname", classLabel.getText());
				
				cn.imgdpu.util.XmlProcess.setCdata("monday", monDT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("tueday", tueDT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("wenday", wenDT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("thuday", thuDT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("friday", friDT.getText());
				
				cn.imgdpu.util.XmlProcess.setCdata("monnig", monNT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("tuenig", tueNT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("wennig", wenNT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("thunig", thuNT.getText());
				cn.imgdpu.util.XmlProcess.setCdata("frinig", friNT.getText());
				
				MessageBox box = new MessageBox(cn.imgdpu.GSAGUI.shell, SWT.ICON_WORKING
						| SWT.OK);
				box.setText("保存");
				box.setMessage("修改成功!");
				box.open();
			}
			
		});

		//发送课程表按钮
		sendTimeBut.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				
				//得到选择框的内容
				String day = weekCombo.getText();
				
				final StringBuilder sms = new StringBuilder();
				
				if (day.equals("请选择"))
					cn.imgdpu.GSAGUI.setStatus("你好像还没有选择要发送星期几的课表？~(>_<)~");
				else {

					if (day.equals("星期一"))
						sms.append("星期一\n" + monDT.getText() +"\n\n"+ monNT.getText());
					if (day.equals("星期二"))
						sms.append("星期二\n" + tueDT.getText() +"\n\n"+ tueNT.getText());
					if (day.equals("星期三"))
						sms.append("星期三\n" + wenDT.getText() +"\n\n"+ wenNT.getText());
					if (day.equals("星期四"))
						sms.append("星期四\n" + thuDT.getText() +"\n\n"+ thuNT.getText());
					if (day.equals("星期五"))
						sms.append("星期五\n" + friDT.getText() +"\n\n"+ friNT.getText());
					
					cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							new SendFetionDialog(cn.imgdpu.GSAGUI.shell, sms.toString()).open();
						}
						
					});
			}
			
			}
		});
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	//设置文本框内容的方法
	public static void setTableText() {

		//判断课程表是否已经设置，0未设，1已设	
		if (cn.imgdpu.util.XmlProcess.isAct("tableact") == 1) {

				editTimeBut.setEnabled(true);
				sendTimeBut.setEnabled(true);

			//读取已保存的课程表信息

				classLabel.setText(cn.imgdpu.util.XmlProcess.getCdata("classname"));
				monDT.setText(cn.imgdpu.util.XmlProcess.getCdata("monday"));
				tueDT.setText(cn.imgdpu.util.XmlProcess.getCdata("tueday"));
				wenDT.setText(cn.imgdpu.util.XmlProcess.getCdata("wenday"));
				thuDT.setText(cn.imgdpu.util.XmlProcess.getCdata("thuday"));
				friDT.setText(cn.imgdpu.util.XmlProcess.getCdata("friday"));

				monNT.setText(cn.imgdpu.util.XmlProcess.getCdata("monnig"));
				tueNT.setText(cn.imgdpu.util.XmlProcess.getCdata("tuenig"));
				wenNT.setText(cn.imgdpu.util.XmlProcess.getCdata("wennig"));
				thuNT.setText(cn.imgdpu.util.XmlProcess.getCdata("thunig"));
				friNT.setText(cn.imgdpu.util.XmlProcess.getCdata("frinig"));

		}

	}


}
