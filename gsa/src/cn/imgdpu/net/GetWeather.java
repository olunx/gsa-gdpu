/*
 *@author fakun , Time:2009-3-17
 *
 *Website : http://www.olunx.com
 *
 *This: 获取天气预报
 */

package cn.imgdpu.net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetWeather {
	boolean cancel = false;

	// 获取今天的天气预报
	public void getNowWeather() {
		if (!cancel) {
			String[] params = {};

			String url = "/weather/weather.aspx?c=CHXX0037&l=zh-CN&p=MSN&a=0&u=C&s=2&m=0&x=1&d=10&fc=&bgc=&bc=";
			PostDataThread postDataRun = new PostDataThread("www.thinkpage.cn", url, params);
			postDataRun.encode = "utf-8";
			Thread postDataThread = new Thread(postDataRun);
			postDataThread.start();
			while (postDataThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					cancel = true;
					// cn.imgdpu.util.CatException.getMethod().catException(e,
					// "线程中断异常");
				}
			}
//			doNowWeather(postDataRun.htmlData);
		}
	}

	public void doNowWeather(String s) {

		StringBuilder text = new StringBuilder();

		Pattern pattern = Pattern
				.compile("天气预报\r\n更新时间: (.*?)\".*? src=\"(.*?)\"[\\s\\S]*id=\"ctl00_ltlText\">(.*?)</span>[\\s\\S]*气温: <span class='temp'>(.*?)</span>[\\s\\S]*<span id=\"ctl00_ltlSpeed\" title=\"(.*?)\">风力: (.*?)</span>[\\s\\S]*<span id=\"ctl00_ltlHumidity\">湿度: (.*?)</span>");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {

			for (int i = 1; i <= matcher.groupCount(); i++) {

				if (i != 2) {
					text.append(matcher.group(i) + "|");
				} else {
					text.append(getImageName(matcher.group(i)) + "|");
				}
				// 更新时间
				// 当前天气图片
				// (晴)
				// 当前气温
				// 风力(4km/h)
				// 风力（2级）
				// 湿度
			}
		}
		pattern = Pattern
				.compile("<div style=\"font-weight:bold;\">(.*)</div>[\\s\\S]*?<img src=\"(.*?)\".*title=\"(.*?)\"[\\s\\S]*?降水概率:(.*?)\"><span class='temp'>(.*?)</span>/<span class='temp'>(.*?)</span>");
		matcher = pattern.matcher(s);
		while (matcher.find()) {

			for (int i = 1; i <= matcher.groupCount(); i++) {

				if (i != 2) {
					text.append(matcher.group(i) + "|");

				} else {
					text.append(getImageName(matcher.group(i)) + "|");

				}
				// 五天
				// 预测天气图片
				// 预测天气（多云）
				// 预测天气降水概率
				// 预测天气温度HIGH
				// 预测天气温度low
			}
		}

		// 删除最后一个"|"号
		text.deleteCharAt(text.lastIndexOf("|"));

		// 写入文件
		cn.imgdpu.util.XmlProcess.setCdata("tdweather", text.toString());

		cn.imgdpu.util.XmlProcess.setAct("tdweather", "1");

	}

	// 未来三天或十天的形势分析
	// day的可选值只是“3”和“10”
	public void getNDayInfo(int day) {
		if (!cancel) {
			String[] params = {};
			String url = "http://wap.weather.com.cn/wap/reports/" + day + "d/";
			PostDataThread postDataRun = new PostDataThread(url, params);
			postDataRun.encode = "utf-8";
			Thread postDataThread = new Thread(postDataRun);
			postDataThread.start();
			while (postDataThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					cancel = true;
					// cn.imgdpu.util.CatException.getMethod().catException(e,
					// "线程中断异常");
				}
			}

			StringBuilder text = new StringBuilder();
			Pattern pattern = Pattern.compile("<br /><br />([\\s\\S]*?)<br />");
			Matcher matcher = pattern.matcher(postDataRun.htmlData);
			if (matcher.find()) {
				text.append(matcher.group(1).trim());

			}

			// 输出结果
			cn.imgdpu.util.XmlProcess.setCdata("tenweather", text.toString());
			cn.imgdpu.util.XmlProcess.setAct("tenweather", "1");

		}
	}

	// 返回天气图片文件名
	public String getImageName(String url) {
		Pattern pattern = Pattern.compile("http://mat1.qq.com/www/images/200801/wealth/(.*)");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find())
			url = matcher.group(1);
		return url;
	}

	public void getZhishu() {
		if (!cancel) {
			String[] params = {};
			String url = "http://weather.news.qq.com/inc/07_zs292.htm";
			PostDataThread postDataRun = new PostDataThread(url, params);
			postDataRun.encode = "utf-8";
			Thread postDataThread = new Thread(postDataRun);
			postDataThread.start();
			while (postDataThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					cancel = true;
					// cn.imgdpu.util.CatException.getMethod().catException(e,
					// "线程中断异常");
				}
			}
			StringBuilder text = new StringBuilder();
			Pattern pattern = Pattern.compile("<strong>(.*)</strong>:<span class=\"tred\">(.*)</span>[\\s\\S]+?width=\"180\">(.*)</td>");
			Matcher matcher = pattern.matcher(postDataRun.htmlData);

			while (matcher.find()) {
				for (int i = 1; i <= 3; i++)
					text.append(matcher.group(i) + "|");
			}

			text.deleteCharAt(text.lastIndexOf("|"));
			cn.imgdpu.util.XmlProcess.setCdata("tdlive", text.toString());
			cn.imgdpu.util.XmlProcess.setAct("tdlive", "1");

		}
	}

	public static void main(String[] args) {
		GetWeather myWeather = new GetWeather();
		myWeather.getNowWeather();// 今天天气和未来五天预测
		myWeather.getNDayInfo(10);// 未来十天天气趋势预报
		myWeather.getZhishu();// 生活指数
		// cn.igdpu.util.XmlProcess.setAct("weather", "1");

	}
}
