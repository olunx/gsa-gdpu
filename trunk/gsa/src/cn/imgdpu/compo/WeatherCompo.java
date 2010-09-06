/*
 *@author olunx , Time:2009-3-25
 *
 *Website : http://www.olunx.com
 *
 *This: 天气预报界面
 */

package cn.imgdpu.compo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import cn.imgdpu.GSAGUI;

import com.swtdesigner.SWTResourceManager;

public class WeatherCompo extends Composite {

	static CLabel tdWeLab, tdTemLab, tdWin1Lab, tdWin2Lab, tdWedLab, timeLab, tdUpdateLab;
	static CLabel tdLab, tdText, tm1Lab, tm1Text, tm2Lab, tm2Text, tm3Lab, tm3Text, tm4Lab, tm4Text;
	static CLabel clothLab, clothText, rayLab, rayText, sickLab, sickText, rainLab, rainText, sunLab, sunText, outLab, outText;
	static Label tenLabel;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public WeatherCompo(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		final Group nowGroup = new Group(this, SWT.NONE);
		nowGroup.setText("实时天气");
		final FormData fd_nowGroup = new FormData();
		fd_nowGroup.bottom = new FormAttachment(0, 270);
		fd_nowGroup.right = new FormAttachment(0, 150);
		fd_nowGroup.top = new FormAttachment(0, 5);
		fd_nowGroup.left = new FormAttachment(0, 5);
		nowGroup.setLayoutData(fd_nowGroup);
		nowGroup.setLayout(new FormLayout());

		final CLabel locLab = new CLabel(nowGroup, SWT.NONE);
		final FormData fd_locLab = new FormData();
		fd_locLab.right = new FormAttachment(100, -5);
		fd_locLab.bottom = new FormAttachment(0, 25);
		fd_locLab.top = new FormAttachment(0, 5);
		fd_locLab.left = new FormAttachment(0, 5);
		locLab.setLayoutData(fd_locLab);
		locLab.setText("广州：");

		tdWeLab = new CLabel(nowGroup, SWT.CENTER);
		tdWeLab.setText("　");
		final FormData fd_tdWeLab = new FormData();
		fd_tdWeLab.right = new FormAttachment(locLab, 0, SWT.RIGHT);
		fd_tdWeLab.top = new FormAttachment(locLab, 0, SWT.BOTTOM);
		fd_tdWeLab.left = new FormAttachment(locLab, 0, SWT.LEFT);
		tdWeLab.setLayoutData(fd_tdWeLab);

		tdTemLab = new CLabel(nowGroup, SWT.NONE);
		fd_tdWeLab.bottom = new FormAttachment(tdTemLab, -5, SWT.TOP);
		final FormData fd_tdTemLab = new FormData();
		fd_tdTemLab.top = new FormAttachment(0, 95);
		fd_tdTemLab.right = new FormAttachment(100, -5);
		fd_tdTemLab.left = new FormAttachment(tdWeLab, 0, SWT.LEFT);
		tdTemLab.setLayoutData(fd_tdTemLab);
		tdTemLab.setText("气温：");

		tdWin1Lab = new CLabel(nowGroup, SWT.NONE);
		fd_tdTemLab.bottom = new FormAttachment(tdWin1Lab, 0, SWT.TOP);
		final FormData fd_tdWin1Lab = new FormData();
		fd_tdWin1Lab.top = new FormAttachment(0, 120);
		fd_tdWin1Lab.right = new FormAttachment(100, -5);
		fd_tdWin1Lab.left = new FormAttachment(tdTemLab, 0, SWT.LEFT);
		tdWin1Lab.setLayoutData(fd_tdWin1Lab);
		tdWin1Lab.setText("风速：");

		tdWin2Lab = new CLabel(nowGroup, SWT.NONE);
		fd_tdWin1Lab.bottom = new FormAttachment(tdWin2Lab, 0, SWT.TOP);
		final FormData fd_tdWin2Lab = new FormData();
		fd_tdWin2Lab.top = new FormAttachment(0, 145);
		fd_tdWin2Lab.right = new FormAttachment(tdWin1Lab, 0, SWT.RIGHT);
		fd_tdWin2Lab.left = new FormAttachment(tdWin1Lab, 0, SWT.LEFT);
		tdWin2Lab.setLayoutData(fd_tdWin2Lab);
		tdWin2Lab.setText("风力：");

		tdWedLab = new CLabel(nowGroup, SWT.NONE);
		fd_tdWin2Lab.bottom = new FormAttachment(tdWedLab, 0, SWT.TOP);
		final FormData fd_tdWedLab = new FormData();
		fd_tdWedLab.top = new FormAttachment(0, 170);
		fd_tdWedLab.right = new FormAttachment(tdWin2Lab, 0, SWT.RIGHT);
		fd_tdWedLab.left = new FormAttachment(tdWin2Lab, 0, SWT.LEFT);
		tdWedLab.setLayoutData(fd_tdWedLab);
		tdWedLab.setText("湿度：");

		timeLab = new CLabel(nowGroup, SWT.NONE);
		fd_tdWedLab.bottom = new FormAttachment(timeLab, 0, SWT.TOP);
		final FormData fd_timeLab = new FormData();
		fd_timeLab.top = new FormAttachment(0, 195);
		fd_timeLab.right = new FormAttachment(tdWedLab, 0, SWT.RIGHT);
		fd_timeLab.left = new FormAttachment(tdWedLab, 0, SWT.LEFT);
		timeLab.setLayoutData(fd_timeLab);
		timeLab.setText("更新时间：");

		tdUpdateLab = new CLabel(nowGroup, SWT.CENTER);
		tdUpdateLab.setText("　");
		fd_timeLab.bottom = new FormAttachment(tdUpdateLab, 0, SWT.TOP);
		final FormData fd_tdUpdateLab = new FormData();
		fd_tdUpdateLab.top = new FormAttachment(0, 220);
		fd_tdUpdateLab.bottom = new FormAttachment(100, -5);
		fd_tdUpdateLab.right = new FormAttachment(100, -5);
		fd_tdUpdateLab.left = new FormAttachment(timeLab, 0, SWT.LEFT);
		tdUpdateLab.setLayoutData(fd_tdUpdateLab);

		final Group tdGroup = new Group(this, SWT.NONE);
		tdGroup.setText("今天生活指数");
		final FormData fd_tdGroup = new FormData();
		fd_tdGroup.bottom = new FormAttachment(0, 270);
		fd_tdGroup.right = new FormAttachment(100, -5);
		fd_tdGroup.top = new FormAttachment(nowGroup, 0, SWT.TOP);
		fd_tdGroup.left = new FormAttachment(nowGroup, 5, SWT.RIGHT);
		tdGroup.setLayoutData(fd_tdGroup);
		tdGroup.setLayout(new FormLayout());

		clothLab = new CLabel(tdGroup, SWT.NONE);
		clothLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_clothLab = new FormData();
		fd_clothLab.bottom = new FormAttachment(0, 25);
		fd_clothLab.right = new FormAttachment(100, -5);
		fd_clothLab.top = new FormAttachment(0, 5);
		fd_clothLab.left = new FormAttachment(0, 5);
		clothLab.setLayoutData(fd_clothLab);
		clothLab.setText("穿衣指数");

		clothText = new CLabel(tdGroup, SWT.NONE);
		clothText.setText("　");
		final FormData fd_clothText = new FormData();
		fd_clothText.left = new FormAttachment(0, 25);
		fd_clothText.bottom = new FormAttachment(0, 45);
		fd_clothText.right = new FormAttachment(clothLab, 0, SWT.RIGHT);
		fd_clothText.top = new FormAttachment(clothLab, 0, SWT.BOTTOM);
		clothText.setLayoutData(fd_clothText);

		sickLab = new CLabel(tdGroup, SWT.NONE);
		sickLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_sickLab = new FormData();
		fd_sickLab.left = new FormAttachment(clothLab, 0, SWT.LEFT);
		fd_sickLab.bottom = new FormAttachment(0, 65);
		fd_sickLab.right = new FormAttachment(100, -5);
		fd_sickLab.top = new FormAttachment(clothText, 0, SWT.BOTTOM);
		sickLab.setLayoutData(fd_sickLab);
		sickLab.setText("感冒指数");

		sickText = new CLabel(tdGroup, SWT.NONE);
		sickText.setText("　");
		final FormData fd_sickText = new FormData();
		fd_sickText.left = new FormAttachment(clothText, 0, SWT.LEFT);
		fd_sickText.bottom = new FormAttachment(0, 85);
		fd_sickText.right = new FormAttachment(100, -5);
		fd_sickText.top = new FormAttachment(sickLab, 0, SWT.BOTTOM);
		sickText.setLayoutData(fd_sickText);

		rainLab = new CLabel(tdGroup, SWT.NONE);
		rainLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_rainLab = new FormData();
		fd_rainLab.left = new FormAttachment(sickLab, 0, SWT.LEFT);
		fd_rainLab.bottom = new FormAttachment(0, 105);
		fd_rainLab.right = new FormAttachment(100, -5);
		fd_rainLab.top = new FormAttachment(sickText, 0, SWT.BOTTOM);
		rainLab.setLayoutData(fd_rainLab);
		rainLab.setText("雨伞指数");

		rainText = new CLabel(tdGroup, SWT.NONE);
		final FormData fd_rainText = new FormData();
		fd_rainText.left = new FormAttachment(sickText, 0, SWT.LEFT);
		fd_rainText.bottom = new FormAttachment(0, 125);
		fd_rainText.right = new FormAttachment(100, -5);
		fd_rainText.top = new FormAttachment(rainLab, 0, SWT.BOTTOM);
		rainText.setLayoutData(fd_rainText);
		rainText.setText("　");

		sunLab = new CLabel(tdGroup, SWT.NONE);
		sunLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_sunLab = new FormData();
		fd_sunLab.left = new FormAttachment(rainLab, 0, SWT.LEFT);
		fd_sunLab.bottom = new FormAttachment(0, 145);
		fd_sunLab.right = new FormAttachment(100, -5);
		fd_sunLab.top = new FormAttachment(rainText, 0, SWT.BOTTOM);
		sunLab.setLayoutData(fd_sunLab);
		sunLab.setText("晾晒指数");

		sunText = new CLabel(tdGroup, SWT.NONE);
		final FormData fd_sunText = new FormData();
		fd_sunText.left = new FormAttachment(rainText, 0, SWT.LEFT);
		fd_sunText.bottom = new FormAttachment(0, 165);
		fd_sunText.right = new FormAttachment(100, -5);
		fd_sunText.top = new FormAttachment(sunLab, 0, SWT.BOTTOM);
		sunText.setLayoutData(fd_sunText);
		sunText.setText("　");

		outLab = new CLabel(tdGroup, SWT.NONE);
		outLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_outLab = new FormData();
		fd_outLab.left = new FormAttachment(sunLab, 0, SWT.LEFT);
		fd_outLab.bottom = new FormAttachment(0, 185);
		fd_outLab.right = new FormAttachment(100, -5);
		fd_outLab.top = new FormAttachment(sunText, 0, SWT.BOTTOM);
		outLab.setLayoutData(fd_outLab);
		outLab.setText("逛街指数");

		outText = new CLabel(tdGroup, SWT.NONE);
		final FormData fd_outText = new FormData();
		fd_outText.left = new FormAttachment(sunText, 0, SWT.LEFT);
		fd_outText.bottom = new FormAttachment(0, 205);
		fd_outText.right = new FormAttachment(100, -5);
		fd_outText.top = new FormAttachment(outLab, 0, SWT.BOTTOM);
		outText.setLayoutData(fd_outText);
		outText.setText("　");

		rayLab = new CLabel(tdGroup, SWT.NONE);
		rayLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/ico/info.gif"));
		final FormData fd_rayLab = new FormData();
		fd_rayLab.left = new FormAttachment(outLab, 0, SWT.LEFT);
		fd_rayLab.bottom = new FormAttachment(0, 225);
		fd_rayLab.right = new FormAttachment(100, -5);
		fd_rayLab.top = new FormAttachment(outText, 0, SWT.BOTTOM);
		rayLab.setLayoutData(fd_rayLab);
		rayLab.setText("紫外线指数");

		rayText = new CLabel(tdGroup, SWT.NONE);
		final FormData fd_rayText = new FormData();
		fd_rayText.left = new FormAttachment(outText, 0, SWT.LEFT);
		fd_rayText.bottom = new FormAttachment(0, 245);
		fd_rayText.right = new FormAttachment(100, -5);
		fd_rayText.top = new FormAttachment(rayLab, 0, SWT.BOTTOM);
		rayText.setLayoutData(fd_rayText);
		rayText.setText("　");

		final Group featureGroup = new Group(this, SWT.NONE);
		featureGroup.setText("天气预测");
		final FormData fd_featureGroup = new FormData();
		fd_featureGroup.bottom = new FormAttachment(100, -5);
		fd_featureGroup.right = new FormAttachment(100, -5);
		fd_featureGroup.top = new FormAttachment(nowGroup, 5, SWT.BOTTOM);
		fd_featureGroup.left = new FormAttachment(nowGroup, 0, SWT.LEFT);
		featureGroup.setLayoutData(fd_featureGroup);
		featureGroup.setLayout(new FormLayout());

		tdText = new CLabel(featureGroup, SWT.CENTER);
		tdText.setText("　");
		final FormData fd_tdText = new FormData();
		fd_tdText.bottom = new FormAttachment(0, 75);
		fd_tdText.right = new FormAttachment(0, 145);
		fd_tdText.top = new FormAttachment(0, 25);
		fd_tdText.left = new FormAttachment(0, 5);
		tdText.setLayoutData(fd_tdText);

		tdLab = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tdLab = new FormData();
		fd_tdLab.right = new FormAttachment(0, 145);
		fd_tdLab.left = new FormAttachment(tdText, 0, SWT.LEFT);
		fd_tdLab.bottom = new FormAttachment(tdText, 0, SWT.TOP);
		tdLab.setLayoutData(fd_tdLab);
		tdLab.setText("今天");

		tm1Lab = new CLabel(featureGroup, SWT.CENTER);
		fd_tdLab.top = new FormAttachment(tm1Lab, 0, SWT.TOP);
		final FormData fd_tm1Lab = new FormData();
		fd_tm1Lab.right = new FormAttachment(0, 290);
		fd_tm1Lab.left = new FormAttachment(tdLab, 5, SWT.RIGHT);
		fd_tm1Lab.top = new FormAttachment(0, 5);
		fd_tm1Lab.bottom = new FormAttachment(tdText, 0, SWT.TOP);
		tm1Lab.setLayoutData(fd_tm1Lab);
		tm1Lab.setText("明天");

		tm2Lab = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm2Lab = new FormData();
		fd_tm2Lab.right = new FormAttachment(0, 435);
		fd_tm2Lab.left = new FormAttachment(tm1Lab, 5, SWT.RIGHT);
		fd_tm2Lab.top = new FormAttachment(0, 5);
		fd_tm2Lab.bottom = new FormAttachment(tm1Lab, 0, SWT.BOTTOM);
		tm2Lab.setLayoutData(fd_tm2Lab);
		tm2Lab.setText("周五");

		tm3Lab = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm3Lab = new FormData();
		fd_tm3Lab.right = new FormAttachment(0, 580);
		fd_tm3Lab.left = new FormAttachment(tm2Lab, 5, SWT.RIGHT);
		fd_tm3Lab.top = new FormAttachment(0, 5);
		fd_tm3Lab.bottom = new FormAttachment(tm2Lab, 0, SWT.BOTTOM);
		tm3Lab.setLayoutData(fd_tm3Lab);
		tm3Lab.setText("周日");

		tm4Lab = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm4Lab = new FormData();
		fd_tm4Lab.right = new FormAttachment(0, 725);
		fd_tm4Lab.left = new FormAttachment(tm3Lab, 5, SWT.RIGHT);
		fd_tm4Lab.bottom = new FormAttachment(tm3Lab, 0, SWT.BOTTOM);
		fd_tm4Lab.top = new FormAttachment(tm3Lab, 0, SWT.TOP);
		tm4Lab.setLayoutData(fd_tm4Lab);
		tm4Lab.setText("周日");

		tm1Text = new CLabel(featureGroup, SWT.CENTER);
		tm1Text.setText("　");
		final FormData fd_tm1Text = new FormData();
		fd_tm1Text.right = new FormAttachment(tm1Lab, 0, SWT.RIGHT);
		fd_tm1Text.left = new FormAttachment(tdText, 5, SWT.RIGHT);
		fd_tm1Text.bottom = new FormAttachment(tdText, 0, SWT.BOTTOM);
		fd_tm1Text.top = new FormAttachment(tm1Lab, 0, SWT.BOTTOM);
		tm1Text.setLayoutData(fd_tm1Text);

		tm2Text = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm2Text = new FormData();
		fd_tm2Text.bottom = new FormAttachment(tm1Text, 0, SWT.BOTTOM);
		fd_tm2Text.right = new FormAttachment(tm2Lab, 0, SWT.RIGHT);
		fd_tm2Text.top = new FormAttachment(tm2Lab, 0, SWT.BOTTOM);
		fd_tm2Text.left = new FormAttachment(tm1Lab, 5, SWT.RIGHT);
		tm2Text.setLayoutData(fd_tm2Text);
		tm2Text.setText("　");

		tm3Text = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm3Text = new FormData();
		fd_tm3Text.top = new FormAttachment(tm3Lab, 0, SWT.BOTTOM);
		fd_tm3Text.bottom = new FormAttachment(tm2Text, 0, SWT.BOTTOM);
		fd_tm3Text.right = new FormAttachment(tm4Lab, 0, SWT.LEFT);
		fd_tm3Text.left = new FormAttachment(tm2Lab, 5, SWT.RIGHT);
		tm3Text.setLayoutData(fd_tm3Text);
		tm3Text.setText("　");

		tm4Text = new CLabel(featureGroup, SWT.CENTER);
		final FormData fd_tm4Text = new FormData();
		fd_tm4Text.bottom = new FormAttachment(tm3Text, 0, SWT.BOTTOM);
		fd_tm4Text.right = new FormAttachment(tm4Lab, 0, SWT.RIGHT);
		fd_tm4Text.top = new FormAttachment(tm4Lab, 0, SWT.BOTTOM);
		fd_tm4Text.left = new FormAttachment(tm3Text, 5, SWT.RIGHT);
		tm4Text.setLayoutData(fd_tm4Text);
		tm4Text.setText("　");

		tenLabel = new Label(featureGroup, SWT.WRAP);
		tenLabel.setText("　");
		final FormData fd_tenLabel = new FormData();
		fd_tenLabel.top = new FormAttachment(tm1Text, 5, SWT.BOTTOM);
		fd_tenLabel.bottom = new FormAttachment(100, -5);
		fd_tenLabel.right = new FormAttachment(100, -5);
		fd_tenLabel.left = new FormAttachment(tdText, 0, SWT.LEFT);
		tenLabel.setLayoutData(fd_tenLabel);
		//

//		setWeatherText();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public static void setWeatherText() {

		// 读取天气预测
		if (cn.imgdpu.util.XmlProcess.isAct("tdweather") == 1) {

			String[] td = cn.imgdpu.util.XmlProcess.getCdata("tdweather").split("\\|");

			for (int i = 0; i < td.length; i++) {
				if (td.length >= 36) {
					tdUpdateLab.setText(td[0]);
					tdWeLab.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[1]));
					tdWeLab.setText(td[2]);
					tdTemLab.setText("气温：" + td[3] + "℃");
					tdWin1Lab.setText("风速：" + td[4]);
					tdWin2Lab.setText("风力：" + td[5]);
					tdWedLab.setText("湿度：" + td[6]);

					tdLab.setText(td[7]);
					tdText.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[8]));
					tdText.setToolTipText("可能性：" + td[10]);
					tdText.setText(td[9] + "\n\n" + td[12] + "-" + td[11] + "℃");

					tm1Lab.setText(td[13]);
					tm1Text.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[14]));
					tm1Text.setToolTipText("可能性：" + td[16]);
					tm1Text.setText(td[15] + "\n\n" + td[18] + "-" + td[17] + "℃");

					tm2Lab.setText(td[19]);
					tm2Text.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[20]));
					tm2Text.setToolTipText("可能性：" + td[22]);
					tm2Text.setText(td[21] + "\n\n" + td[24] + "-" + td[23] + "℃");

					tm3Lab.setText(td[25]);
					tm3Text.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[26]));
					tm3Text.setToolTipText("可能性：" + td[28]);
					tm3Text.setText(td[27] + "\n\n" + td[30] + "-" + td[29] + "℃");

					tm4Lab.setText(td[31]);
					tm4Text.setImage(SWTResourceManager.getImage(WeatherCompo.class, "/cn/imgdpu/wico/" + td[32]));
					tm4Text.setToolTipText("可能性：" + td[34]);
					tm4Text.setText(td[33] + "\n\n" + td[36] + "-" + td[35] + "℃");

					// 顶部天气
					cn.imgdpu.GSAGUI.weather.setImage(SWTResourceManager.getImage(GSAGUI.class, "wico/" + td[1]));
					cn.imgdpu.GSAGUI.weather.setText("广州\n" + td[3] + "℃");
					cn.imgdpu.GSAGUI.weather.setToolTipText(td[2]);
					cn.imgdpu.GSAGUI.weather.update();
				}
			}
		}// edn of if 天气预测

		// 读取生活指数
		if (cn.imgdpu.util.XmlProcess.isAct("tdlive") == 1) {
			String[] lv = cn.imgdpu.util.XmlProcess.getCdata("tdlive").split("\\|");
			for (int i = 0; i < lv.length; i++) {
				if (lv.length > 38) {
					clothLab.setText(lv[0] + "：" + lv[1]);
					clothText.setText(lv[2]);

					sickLab.setText(lv[9] + "：" + lv[10]);
					sickText.setText(lv[11]);

					rainLab.setText(lv[18] + "：" + lv[19]);
					rainText.setText(lv[20]);

					sunLab.setText(lv[27] + "：" + lv[28]);
					sunText.setText(lv[29]);

					outLab.setText(lv[33] + "：" + lv[34]);
					outText.setText(lv[35]);

					rayLab.setText(lv[36] + "：" + lv[37]);
					rayText.setText(lv[38]);

				}
			}
		}// end of if 生活指数

		// 十天预测评说
		if (cn.imgdpu.util.XmlProcess.isAct("tenweather") == 1) {
			String ten = cn.imgdpu.util.XmlProcess.getCdata("tenweather");
			if (ten != "") {
				tenLabel.setText("    " + ten);
			}
		}

	}

}
