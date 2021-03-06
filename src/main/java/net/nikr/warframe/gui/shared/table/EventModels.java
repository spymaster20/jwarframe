/*
 * Copyright 2014-2015 Niklas Kyster Rasmussen
 *
 * This file is part of jWarframe.
 *
 * Original code from jEveAssets (https://code.google.com/p/jeveassets/)
 *
 * jWarframe is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * jWarframe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jWarframe; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package net.nikr.warframe.gui.shared.table;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.DefaultEventTableModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;


public class EventModels {

	//EventModels.createTableModel
	public static <E> DefaultEventTableModel<E> createTableModel(EventList<E> source, TableFormat<E> tableFormat) {
		return new DefaultEventTableModel<E>(createSwingThreadProxyList(source), tableFormat);
	}

	//EventModels.createSelectionModel
	public static <E> DefaultEventSelectionModel<E> createSelectionModel(EventList<E> source) {
		return new DefaultEventSelectionModel<E>(createSwingThreadProxyList(source));
	}

	private static <E> EventList<E> createSwingThreadProxyList(EventList<E> source) {
		final EventList<E> result;
		source.getReadWriteLock().readLock().lock();
		try {
			result = GlazedListsSwing.swingThreadProxyList(source);
		} finally {
			source.getReadWriteLock().readLock().unlock();
		}
		return result;
	}
}
