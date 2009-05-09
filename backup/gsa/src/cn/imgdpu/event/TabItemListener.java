/*
 *@author olunx , Time:2009-3-14
 *
 *Website : http://www.olunx.com
 *
 *This:子CtabItem点击事件处理
 */

package cn.imgdpu.event;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import cn.imgdpu.compo.FtpFilesUpdateCompo;
import cn.imgdpu.compo.HttpFilesCompo;
import cn.imgdpu.compo.NewsEduCompo;
import cn.imgdpu.compo.SearchBookCompo;
import cn.imgdpu.compo.SearchTableCompo;
import cn.imgdpu.compo.SearchTextCompo;
import cn.imgdpu.compo.TyphoonCompo;
import cn.imgdpu.compo.NewsUcityCompo;
import cn.imgdpu.compo.NewsUnionCompo;
import cn.imgdpu.compo.RssUniverCompo;
import cn.imgdpu.compo.YigeMusicCompo;

public class TabItemListener implements Listener {

	CTabFolder tabFolder;

	public TabItemListener(CTabFolder tabFolderP) {
		tabFolder = tabFolderP;
	}

	@Override
	public void handleEvent(Event e) {

		CTabItem selectItem = tabFolder.getSelection();

		// 一般是在点击CTabItem的第二项后，动态new出相应的composite，第一项有数据的也要处理
		if (selectItem.getText().equals("搜索书籍")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final SearchBookCompo searchBook = new SearchBookCompo(tabFolder, SWT.NONE);
				selectItem.setControl(searchBook);
			}

		} else if (selectItem.getText().equals("搜索课程表")) {

			// 如果还没有创建控件，则添加控件
			if (selectItem.getControl() == null) {
				final SearchTableCompo searchTime = new SearchTableCompo(tabFolder, SWT.NONE);
				selectItem.setControl(searchTime);
			}

		} else if (selectItem.getText().equals("我的课程表")) {

			cn.imgdpu.compo.TimeTableCompo.setTableText();

		} else if (selectItem.getText().equals("台风消息")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final TyphoonCompo typhoon = new TyphoonCompo(tabFolder, SWT.NONE);
				selectItem.setControl(typhoon);
			}
			
		} else if (selectItem.getText().equals("社团动态")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final NewsUnionCompo unionNews = new NewsUnionCompo(tabFolder, SWT.NONE);
				selectItem.setControl(unionNews);
			}
			
		} else if (selectItem.getText().equals("大学城新闻")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final NewsUcityCompo ucityNews = new NewsUcityCompo(tabFolder, SWT.NONE);
				selectItem.setControl(ucityNews);
			}
			
		} else if (selectItem.getText().equals("中国教育新闻")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final NewsEduCompo eduNews = new NewsEduCompo(tabFolder, SWT.NONE);
				selectItem.setControl(eduNews);
			}
			
		} else if (selectItem.getText().equals("Http搜索")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final HttpFilesCompo httpFile = new HttpFilesCompo(tabFolder, SWT.NONE);
				selectItem.setControl(httpFile);
			}
			
		} else if (selectItem.getText().equals("Ftp搜索")) {
			
			cn.imgdpu.compo.FtpFilesCompo.setComboUrls();
			
		} else if (selectItem.getText().equals("内容搜索")) {
			
			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				SearchTextCompo textSearch = new SearchTextCompo(tabFolder, SWT.NONE);
				selectItem.setControl(textSearch);
			}
			
		} else if (selectItem.getText().equals("Ftp网站列表")) {
			
			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final FtpFilesUpdateCompo ftpUpdate = new FtpFilesUpdateCompo(tabFolder, SWT.NONE);
				selectItem.setControl(ftpUpdate);
			}
		} else if (selectItem.getText().equals("大学城网站")) {

			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final RssUniverCompo university = new RssUniverCompo(tabFolder, SWT.NONE);
				selectItem.setControl(university);
			}
			
		} else if (selectItem.getText().equals("随便听听(非校园网)")) {
			
			// 如果还没有创建控件，则添加控件
			if (String.valueOf(selectItem.getControl()) == "null") {
				final YigeMusicCompo yigeMusic = new YigeMusicCompo(tabFolder, SWT.NONE);
				selectItem.setControl(yigeMusic);
			}
			
		}

	}

}
