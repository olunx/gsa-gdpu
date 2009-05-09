/*
 *@author olunx , Time:2009-4-29
 *
 *Website : http://www.olunx.com
 *
 *This : 
 */

package cn.imgdpu.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class ReadOfficeFiles {

	StringBuilder readfiles(String filePath) {

		// 用于保存返回的数据
		StringBuilder result = new StringBuilder();

		if (filePath.toLowerCase().endsWith(".doc")) {
			result = readWord(filePath);
		} else if (filePath.toLowerCase().endsWith(".ppt")) {
			result = readPpt(filePath);
		} else if (filePath.toLowerCase().endsWith(".xls")) {
			result = readExcel(filePath);
		} else {
			result = readText(filePath);
		}

		return result;
	}

	// 读取Word的内容，参数：文件路径
	private StringBuilder readWord(String filePath) {

		// 用于保存返回的数据
		StringBuilder result = new StringBuilder();

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "指定文件不存在");
		}

		WordExtractor we = null;
		try {
			we = new WordExtractor(in);
			result.append(we.getText());
		} catch (EncryptedDocumentException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "EncryptedDocument异常");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		} catch (StringIndexOutOfBoundsException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "字符串越界");
		} catch (NullPointerException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "空指针异常");
		} catch (ArrayIndexOutOfBoundsException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "数组越界");
		} catch (IllegalStateException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "未知");
		}

		return result;
	}

	// 读取Excel的内容，参数：文件路径
	private StringBuilder readExcel(String filePath) {

		// 用于保存返回的数据
		StringBuilder result = new StringBuilder();

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "指定文件不存在");
		}

		ExcelExtractor ee = null;
		try {
			ee = new ExcelExtractor(new HSSFWorkbook(in));
			result.append(ee.getText());
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		} catch (NullPointerException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "空指针异常");
		}

		return result;
	}

	// 读取PPT的内容，参数：文件路径
	private StringBuilder readPpt(String filePath) {

		// 用于保存返回的数据
		StringBuilder result = new StringBuilder();

		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "指定文件不存在");
		}

		SlideShow ee = null;
		try {
			ee = new SlideShow(new HSLFSlideShow(in));
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		Slide[] slides = ee.getSlides();

		for (int i = 0; i < slides.length; i++) {
			// 为了取得幻灯片的文字内容，建立TextRun
			TextRun[] t = slides[i].getTextRuns();

			// 取出每一页的内容
			for (int j = 0; j < t.length; j++) {
				// System.out.println(t[j].getText());
				result.append(t[j].getText());
			}
		}

		return result;
	}

	// 读取文本文档类型的文件
	private StringBuilder readText(String filePath) {

		// 用于保存返回的数据
		StringBuilder result = new StringBuilder();

		FileReader in = null;
		int b = 0;
		try {
			in = new FileReader(new File(filePath));
			while ((b = in.read()) != -1) {
				result.append((char) b);
			}
		} catch (FileNotFoundException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "指定文件不存在");
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "IO异常");
		}

		return result;
	}
}
