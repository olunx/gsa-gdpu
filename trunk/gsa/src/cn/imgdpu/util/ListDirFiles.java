/*
 *@author olunx , Time:2009-4-30
 *
 *Website : http://www.olunx.com
 *
 *This : 递归遍历文件目录
 *
 */

package cn.imgdpu.util;

import java.io.File;
import java.util.ArrayList;

public class ListDirFiles {
	
	//需要的文件类型
	public String fileType;
	
	//用于保存文件路径信息
	public ArrayList<String> filesInfo;

	//参数：要遍历的目录
	public void listDirFiles(String dirPath) {
		
		//初始化
		File dir = new File(dirPath);
		File[] files = dir.listFiles();

		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				listDirFiles(files[i].getAbsolutePath());
			} else {
				
				//取出指定类型的文件
				if(files[i].getName().toLowerCase().endsWith(fileType)) {
					String strFileName = files[i].getAbsolutePath();
					//保存文件路径到ArrayList中
					filesInfo.add(strFileName);
				}
				
			}
		}
	}//end of listDirFiles


}
