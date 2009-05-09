/*
 *@author olunx , Time:2009-3-15
 *
 *Website : http://www.olunx.com
 *
 *This:主tabItem点击事件处理
 */

package cn.imgdpu.event;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import cn.imgdpu.compo.NewsCampusCompo;
import cn.imgdpu.compo.CetCompo;
import cn.imgdpu.compo.FtpFilesCompo;
import cn.imgdpu.compo.MusicCompo;
import cn.imgdpu.compo.TimeTableCompo;

public class TabFolderListener implements Listener {
	public static NewsCampusCompo campusNewsCompo = null;
	TabFolder tabFolder;

	public TabFolderListener(TabFolder tabFolderP) {
		tabFolder = tabFolderP;
	}

	@Override
	public void handleEvent(Event e) {

		TabItem[] selectItem = tabFolder.getSelection();// 得到主TabFolder的选中项
		TabItem selectTabItem = selectItem[0];// 选中的tabItem

		Control control = selectTabItem.getControl();

		CTabFolder childCTabFolder = null;

		if (control instanceof CTabFolder) {
			childCTabFolder = (CTabFolder) control;
		}

		if (childCTabFolder.getItemCount() > 0) {
			CTabItem firstCItem = childCTabFolder.getItem(0);

			if (selectTabItem.getText().equals("课程表")) {

				if (firstCItem.getControl() == null) {
					final TimeTableCompo timeTable = new TimeTableCompo(childCTabFolder, SWT.NONE);
					firstCItem.setControl(timeTable);
					childCTabFolder.setSelection(firstCItem);
				}

			} else if (selectTabItem.getText().equals("四六英语")) {

				if (firstCItem.getControl() == null) {
					final CetCompo cet = new CetCompo(childCTabFolder, SWT.NONE);
					firstCItem.setControl(cet);
					childCTabFolder.setSelection(firstCItem);
				}

			} else if (selectTabItem.getText().equals("娱乐视听")) {

				if (firstCItem.getControl() == null) {
					final MusicCompo musicPlay = new MusicCompo(childCTabFolder, SWT.NONE);
					firstCItem.setControl(musicPlay);
					childCTabFolder.setSelection(firstCItem);
				}

			} else if (selectTabItem.getText().equals("文件搜索")) {

				if (firstCItem.getControl() == null) {
					final FtpFilesCompo ftpFile = new FtpFilesCompo(childCTabFolder, SWT.NONE);
					firstCItem.setControl(ftpFile);
					childCTabFolder.setSelection(firstCItem);
				}
			} else if (selectTabItem.getText().equals("校园新闻")) {

				if (firstCItem.getControl() == null) {
					campusNewsCompo = new NewsCampusCompo(childCTabFolder, SWT.NONE);
					firstCItem.setControl(campusNewsCompo);
					childCTabFolder.setSelection(firstCItem);
				}
			}

		}

	}

}
