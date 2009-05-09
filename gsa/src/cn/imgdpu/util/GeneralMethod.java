/*
 *@author olunx , Time:2009-4-2
 *
 *Website : http://www.olunx.com
 *
 *This: 通用方法
 */

package cn.imgdpu.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GeneralMethod {

	public static GeneralMethod generalMethod;

	public static GeneralMethod getGeneralMethod() {
		if (GeneralMethod.generalMethod == null) {
			generalMethod = new GeneralMethod();
		}
		return generalMethod;
	}

	// 根据ip得到表名
	public String getIpToTableName(String ip) {
		return new StringBuffer("ftp_").append(ip.replace(".", "_")).toString();
	}

	// 文件大小转换
	public String formatFilesize(double size) {
		String result = "0";
		size = size / 1024.0;
		if (size < 1024)
			result = new DecimalFormat("0.0").format(size) + " K";
		else if (size >= 1024 && size < 1048576)
			result = new DecimalFormat("0.0").format(size / 1024.0) + " M";
		else if (size >= 1048576)
			result = new DecimalFormat("0.0").format(size / 1048576.0) + " G";
		return result;
	}

	// 文件类型转换
	public String typeConv(String type) {

		if (type.equalsIgnoreCase("video")) {
			type = "rmvb' or type='rm' or type='mpeg' or type='mpg' or type='avi' or type='3gp' or type='mp4' or type='mkv";
		} else if (type.equalsIgnoreCase("audio")) {
			type = "mp3' or type='wma' or type='wav' or type='aac' or type='flac' or type='cue";
		} else if (type.equalsIgnoreCase("document")) {
			type = "doc' or type='ppt' or type='xls' or type='pdf' or type='chm";
		}

		return type;
	}

	// 传入路径(path)和根目录地址(baseUrl)得到文件所在目录
	public String getFileParentDir(String path, String baseUrl) {
		String result = null;
		result = path.substring(baseUrl.length());
		if (result.lastIndexOf("/") == -1)
			return "/";
		else {
			result = result.substring(0, result.lastIndexOf("/"));
			result = result + "/";
			if (result.indexOf("/") == -1)
				result = "/";
			else {
				if (path.startsWith("ftp://"))
					result = result.substring(result.indexOf("/"));
			}
		}
		if (!result.startsWith("/"))
			result = "/" + result;
		return result;
	}

	// 传入路径得到文件扩展名
	public String getExtension(String path) {
		if ((path != null) && (path.length() > 0)) {
			int i = path.lastIndexOf('.');
			int j = path.lastIndexOf('/');

			if ((i > -1) && (i < (path.length() - 1)) && (i > j)) {
				return path.substring(i + 1).toLowerCase();
			}
		}
		return "-";
	}

	// 传入要复制的内容：string[]
	public void copyToSystem(String copys) {

		Clipboard clipboard = new Clipboard(cn.imgdpu.GSAGUI.shell.getDisplay());
		clipboard.setContents(new String[] { copys }, new Transfer[] { TextTransfer.getInstance() });

	}

	// 返回当前时间：2009-04-10
	public String getNowTime() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00")).getTime();

		return bartDateFormat.format(date);
	}

	// 处理rss的日期格式
	public String transRssDate(String from) {

		from = from.replaceAll(" ", "");
		// <pubDate>Thu,02 Apr 2009 19:32:55 +0800</pubDate>
		SimpleDateFormat dateFrom = new SimpleDateFormat("EEE,ddMMMyyyykk:mm:ssZ", Locale.US);

		SimpleDateFormat dateTo = new SimpleDateFormat("yyyy年MM月dd日");

		Date date = null;
		try {
			date = dateFrom.parse(from);
		} catch (ParseException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "未知异常");
		}

		return dateTo.format(date);
	}

	// 返回屏幕纵向分辨率
	public int getDisHeight() {
		// 获取屏幕分辨率
		Rectangle size = Display.getDefault().getClientArea();
		return size.height;
	}

	// 返回屏幕横向分辨率
	public int getDisWidth() {
		// 获取屏幕分辨率
		Rectangle size = Display.getDefault().getClientArea();
		return size.width;
	}

	// 设置主界面相对位置
	public void setMainDisLoc(Shell shell, int width, int height) {

		// 800x600为主界面大小
		int w = (GeneralMethod.getGeneralMethod().getDisWidth() - width) / 2;
		int h = (GeneralMethod.getGeneralMethod().getDisHeight() - height) / 2;

		shell.setBounds(w, h, 0, 0);
	}

	// 设置对话框相对位置
	public void setDisLoc(Shell shell, int width, int height) {

		// 800x600为主界面大小
		int w = (getDisWidth() - 800) / 2 + (800 - width) / 2;
		int h = (getDisHeight() - 600) / 2 + (600 - height) / 2;

		shell.setBounds(w, h, 0, 0);
	}

}
