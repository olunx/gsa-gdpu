/*
 *@author olunx , Time:2009-4-26
 *
 *Website : http://www.olunx.com
 *
 *This : 处理所有Exception事件
*/

package cn.imgdpu.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

public class CatException {

	/**
	 * @param args
	 */

	public static CatException catException;

	public static CatException getMethod() {
		if (CatException.catException == null) {
			catException = new CatException();
		}

		return catException;
	}

	public void catException(Exception e, String s) {

		String ex = e.getClass().toString().toLowerCase();
		String noTip = "filenotfound,numberformat,ioexception";
		
		if (!noTip.contains(ex)) {
			//提示
			cn.imgdpu.GSAGUI.setStatusAsyn("警告：" + s);

			//记录exception
			StringBuilder info = new StringBuilder();

			info.append("time: " + new Date());
			info.append("\n");
			info.append(e.getClass());
			info.append("\n");

			StackTraceElement[] ste = e.getStackTrace();
			for (int i = 0; i < ste.length; i++) {
				info.append("class: " + ste[i].getClassName() + "\tline: " + ste[i].getLineNumber());
				info.append("\n");
			}

			//输出exception
			Writer writeTable;
			try {
				writeTable = new OutputStreamWriter(new FileOutputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\exceptioninfo.txt")));
				writeTable.write(info.toString());
				writeTable.close();
			} catch (FileNotFoundException e1) {
			} catch (IOException e1) {
			}

		}

	}

}
