/*
 *@author olunx , Time:2009-3-26
 *
 *Website : http://www.olunx.com
 *
 *This: 天气更新
 */

package cn.imgdpu.util;

import cn.imgdpu.net.GetWeather;

public class UpdateWeather extends Thread {

	public static void main(String[] args) {
		new UpdateWeather().start();
	}

	public void run() {
		cn.imgdpu.GSAGUI.setStatusAsyn("正在用力更新天气信息中...不知道天气会怎样呢！~(>_<)~");

		GetWeather myWeather = new GetWeather();
		myWeather.getNowWeather();// 今天天气和未来五天预测
		myWeather.getNDayInfo(10);// 未来十天天气趋势预报
		myWeather.getZhishu();// 生活指数

		if (!cn.imgdpu.GSAGUI.shell.isDisposed()) {
			cn.imgdpu.GSAGUI.shell.getDisplay().asyncExec(new Runnable() {

				public void run() {
					cn.imgdpu.compo.WeatherCompo.setWeatherText();
					cn.imgdpu.GSAGUI.setStatus("天气信息更新成功了！O(∩_∩)O");
				}
			});
		}
	}
}
