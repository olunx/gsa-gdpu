/*
 *@author olunx , Time:2009-4-5
 *
 *Website : http://www.olunx.com
 *
 *This:获取HTTP目录文件
 */

package cn.imgdpu.net;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.visitors.ObjectFindingVisitor;

public class ReadHtmlFiles {

	String href, link;
	ArrayList<String> files;;

	public ArrayList<String> readFiles(String site) {

		files = new ArrayList<String>();

		testLinkVisitor(site);

		return files;
	}

	public void testLinkVisitor(String url) {

		try {

			LinkTag textLink;
			ObjectFindingVisitor visitor = new ObjectFindingVisitor(LinkTag.class);
			Parser parser = new Parser();
			parser.setURL(url);
			parser.setEncoding(parser.getEncoding());
			parser.visitAllNodesWith(visitor);
			Node[] nodes = visitor.getTags();

			for (int i = 0; i < nodes.length; i++) {

				textLink = (LinkTag) nodes[i];

				// 取出href内容
				href = textLink.getAttribute("href");

				// 取出链接
				link = textLink.getLink();

				// 如果以/结尾，则认为是目录
				if (href.endsWith("/")) {

					// 如果以/或者http开头，则不处理，否则递归遍历
					if (!href.startsWith("/") && !href.startsWith("http") && !href.startsWith("../")) {
						testLinkVisitor(url + href);
					}

				} else {

					if (!href.startsWith("http") && !textLink.getLinkText().trim().equals("Name")
							&& !textLink.getLinkText().trim().equals("Last modified") && !textLink.getLinkText().trim().equals("Size")
							&& !textLink.getLinkText().trim().equals("Description")) {
						href = java.net.URLDecoder.decode(href, "UTF-8").replaceAll("&amp;", "&");
						files.add(href);

						link = java.net.URLDecoder.decode(link, "UTF-8").replaceAll("&amp;", "&");
						files.add(link);
						files.add(href.substring(href.lastIndexOf(".") + 1, href.length()));
						files.add("-");
					}

				}
			}
		} catch (Exception e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "未知异常");
		}

	}

}
