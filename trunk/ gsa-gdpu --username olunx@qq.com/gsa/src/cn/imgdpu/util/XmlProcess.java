/*
 *@author olunx , Time:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This: JDOM读写XML数据
 */

package cn.imgdpu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XmlProcess {

	static int softNum = 0;

	// 要读取的数据文件路经，要和cn.igdpu.net.GetTimetable中的一致
	String filePath = cn.imgdpu.util.FileUrlConv.UrlConv("data/classtable_temp.xml");

	
	// 返回班级名称
	public ArrayList<String> ReadClassXml() {

		ArrayList<String> data = new ArrayList<String>();

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(filePath);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		for (int i = 0; i < root.getChildren().size(); i++) {
			Element element = (Element) root.getChildren().get(i);
			data.add(element.getChildText("classname"));

		}

		return data;
	}

	
	// 返回指定班级名称
	public String ReadOneClassXml(int itemNo) {

		String data = new String();

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(filePath);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(itemNo);
		data = element.getChildText("classname");

		return data;
	}

	
	// 返回指定索引的课表
	// 第一个参数为班级item索引值
	public ArrayList<String> getTableXML(int itemNo) {

		ArrayList<String> data = new ArrayList<String>();

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(filePath);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(itemNo);

		data.add(element.getChildText("classname"));
		data.add(element.getChildText("mon"));
		data.add(element.getChildText("tue"));
		data.add(element.getChildText("wed"));
		data.add(element.getChildText("thu"));
		data.add(element.getChildText("fri"));

		return data;
	}

	
	// 返回是否可用
	public static int isAct(String name) {

		int act;

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(cn.imgdpu.util.FileUrlConv.UrlConv("data/conf.xml"));
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(0);
		act = Integer.parseInt(element.getChildText(name));

		return act;
	}

	
	// 设置是否可用，1代表可用
	public static void setAct(String name, String act) {

		String inFile = cn.imgdpu.util.FileUrlConv.UrlConv("data/conf.xml");
		String outFile = cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\conf.xml");

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(inFile);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(0);
		Element conf = (Element) element.getChild(name);
		conf.setText(act);

		try {
			XMLOutputter outputter = new XMLOutputter();
			Format fmt = Format.getPrettyFormat();
			// 缩进的长度
			fmt.setIndent("	");
			outputter.setFormat(fmt);
			outputter.output(root.getDocument(), new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));

		}catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

	}

	
	// 修改CData区，传入参数为要写入的内容，在此即为修改后的课程表的内容，或者是借书的信息
	public static void setCdata(String name, String data) {

		String inFile = cn.imgdpu.util.FileUrlConv.UrlConv("data/conf.xml");
		String outFile = cn.imgdpu.util.FileUrlConv.UrlConvIo("data\\conf.xml");

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(inFile);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(1);

		Element conf = (Element) element.getChild(name);

		CDATA cdata = new CDATA(name);
		cdata.setText(data);

		conf.removeContent();
		conf.addContent(cdata);

		try {
			XMLOutputter outputter = new XMLOutputter();
			Format fmt = Format.getPrettyFormat();
			// 缩进的长度
			fmt.setIndent("	");
			outputter.setFormat(fmt);
			outputter.output(root.getDocument(), new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));

		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

	}

	
	// 得到指定内容，再此，返回课程表或借书信息
	public static String getCdata(String name) {

		String file = cn.imgdpu.util.FileUrlConv.UrlConv("data/conf.xml");

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(file);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		Element element = (Element) root.getChildren().get(1);

		return element.getChildText(name);
	}

	
	//返回新闻数据
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getNewsData(String fileName) {
		ArrayList<String> result = new ArrayList<String>();

		String inFile = null, shortFile = null;

		inFile = cn.imgdpu.util.FileUrlConv.UrlConv("data/" + fileName);
		shortFile = "data\\" + fileName;

		//检查文件是否存在
		isSearchAlive(shortFile);

		SAXBuilder builder = new SAXBuilder(false);
		Document document = null;

		try {
			document = builder.build(inFile);
		} catch (JDOMException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "JDOM异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Element root = document.getRootElement();

		List channel = root.getChildren("channel");
		
		Element channelChild = (Element)channel.get(0);

		List child = channelChild.getChildren("item");
		
		Element site = null;

			for (int i = 0; i < child.size(); i++) {

				//得到所有第一级子节点
				site = (Element) child.get(i);

				result.add(site.getChildText("title"));
				result.add(site.getChildText("link"));
				result.add(site.getChildText("description"));
				result.add(cn.imgdpu.util.GeneralMethod.getGeneralMethod().transRssDate(site.getChildText("pubDate")));
				result.add(site.getChildText("author"));

			}

		
		return result;
	}
	
	
	//配置文件丢失处理
	private static void isSearchAlive(String file) {

		File conf = new File(cn.imgdpu.util.FileUrlConv.UrlConvIo(file));
		File confPath = new File(cn.imgdpu.util.FileUrlConv.UrlConvIo("data"));
		if (!confPath.exists() && !confPath.isDirectory()) {
			confPath.mkdirs();
		}

		if (!conf.exists()) {

			StringBuilder s = new StringBuilder();

			s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss><channel></channel></rss>");

			Writer writeTable;
			try {
				writeTable = new OutputStreamWriter(new FileOutputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo(file)), "UTF-8");
				writeTable.write(s.toString());
				writeTable.close();

			} catch (UnsupportedEncodingException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "不支持的编码类型");
			} catch (FileNotFoundException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "找不到数据文件");
			} catch (IOException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
			}
		}

	}


}
