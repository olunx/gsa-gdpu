/*
 *@author olunx , Time:2009-5-3
 *
 *Website : http://www.olunx.com
 *
 *This : 
 *
 */

package cn.imgdpu.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadWordbyStream {
	public static void main(String[] args) throws IOException {
		String rowContent = new String();
		String content = new String();
		BufferedReader in = new BufferedReader(new FileReader("E:\\olunx\\个人简介.doc"));
		while ((rowContent = in.readLine()) != null) {
			content = content + rowContent + "\n";
		}
		System.out.println(content.getBytes());
		System.out.println(new String(content.getBytes(), "utf-8"));// 因为编码方式不同，不容易解析
		in.close();
	}

}
