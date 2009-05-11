/*
 *@author olunx , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This:班级课程表查询，将返回的数据写入xml文件
 */

package cn.imgdpu.net;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class GetTimetable {

	public boolean flagInfo = false;

	public void getClassTime(String className) {

		String[] params = { "classname=" + className };

		String url = "http://www.olunx.com/gsa/ke.asp";

		PostDataThread postDataRun = new PostDataThread(url, params);

		postDataRun.encode = "utf-8";// 设置编码（默认gb2312）

		Thread postDataThread = new Thread(postDataRun);
		postDataThread.start();
		while (postDataThread.isAlive()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "线程中断异常");
			}
		}
		doGetClassTime(postDataRun.htmlData);
	}

	public void doGetClassTime(String s) {

		// 将传回的数据写入xml文件
		Writer writeTable;
		try {
			writeTable = new OutputStreamWriter(new FileOutputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\classtable_temp.xml")), "UTF-8");
			writeTable.write(s);
			writeTable.close();
		} catch (UnsupportedEncodingException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "找不到数据文件");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		flagInfo = true;
	}

}
