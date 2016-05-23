/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.castafiore.community.ui.membership;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.DefaultCellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class MembershipCellRenderer extends DefaultCellRenderer {

	public Container getComponentAt(int row, int column, int page, TableModel model, EXTable table) {
		Object value = model.getValueAt(column, row, page);
		EXContainer span = new EXContainer("", "span");
		if (value == null) {
			span.setText(" ");
		} else {
			span.setText(value.toString());
		}
		return span;
	}

	public void onChangePage(Container component, int row, int column, int page, TableModel model, EXTable table) {
		Object newValue = model.getValueAt(column, row, page);
		EXContainer span = (EXContainer) component;
		if (newValue == null) {
			span.setText(" ");
		} else {
			span.setText(newValue.toString());
		}
		span.setRendered(false);
	}

}
