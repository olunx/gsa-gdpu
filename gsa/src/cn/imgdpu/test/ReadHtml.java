/*
 *@author olunx , 创建时间:2009-4-4
 *
 *Website : http://www.olunx.com
 */

package cn.imgdpu.test;

import org.htmlparser.Node;
import org.htmlparser.beans.FilterBean;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.ObjectFindingVisitor;

public class ReadHtml {

	static String webUrl = "http://branch.gdpu.edu.cn/jwc/";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadHtml rh = new ReadHtml();
		rh.testLinkVisitor(webUrl);
	}

	// 第一个参数为班级item索引值
	public void getHtml() {

	}

	public void testLinkVisitor(String url) {

		try {

			FilterBean filterBean = new FilterBean();
			filterBean.setURL(url);

			NodeList list = filterBean.getNodes();

			ObjectFindingVisitor visitor = new ObjectFindingVisitor(TableTag.class);

			list.visitAllNodesWith(visitor);

			TableTag textLink = null;
			Node[] nodes = visitor.getTags();

			for (int i = 0; i < nodes.length; i++) {
				textLink = (TableTag) nodes[i];

				if (String.valueOf(textLink.getAttribute("height")).equalsIgnoreCase("528")) {
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
