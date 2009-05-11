/*
 *@author fatkun , Time:2009-3-7
 *
 *Website : http://www.olunx.com
 *
 *This: 更改table高度
 */

package cn.imgdpu.event;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;

public class SwtTableItemHeightListener implements Listener {
	Table table;

	public SwtTableItemHeightListener(Table _table) {
		table = _table;
	}

	@Override
	public void handleEvent(Event event) {
		int clientWidth = table.getClientArea().width;
		event.height = event.gc.getFontMetrics().getHeight() * 2;
		event.width = clientWidth * 2;
	}

}
