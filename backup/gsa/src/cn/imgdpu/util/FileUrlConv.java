/*
 *@author olunx , Time:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This: 文件路径转换
 */

package cn.imgdpu.util;

public class FileUrlConv {
	
	public static String UrlConv(String relUrl) {
		
		//得到当前工作目录
		//String url = System.getProperty("user.dir") + "/" + relUrl;

		// 处理文件路径问题
		String url = FileUrlConv.class.getClass().getResource("/").getPath()
				.substring(1);
		if (url.endsWith("bin/")) {
			url = url.replaceAll("/bin/", "/");
		}
		
		String osType = System.getProperty("user.dir").substring(0, 1);
		
		//通过判断更目录名来获得当前系统类型
		if(osType.equals("/")) {
			url = "/"  + url;
		}
		
		return url + relUrl;
	}
	
	public static String UrlConvIo(String relUrl) {
		
		String url;
		
		String osType = System.getProperty("user.dir").substring(0, 1);
		
		//通过判断更目录名来获得当前系统类型
		if(osType.equals("/")) {
			url = System.getProperty("user.dir") + "\\" + relUrl;
			url = url.replaceAll("\\\\", "/");
		}else {
			url = System.getProperty("user.dir") + "\\" + relUrl;
		}
		
		return url;
	}
	
}
