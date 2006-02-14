/* Copyright (C) 2006 Christian Schneider
 * 
 * This file is part of Nomad.
 * 
 * Nomad is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Nomad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Nomad; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Created on Feb 9, 2006
 */
package org.nomad.util.iterate;

import java.awt.Component;
import java.awt.Container;


public class ComponentIterator<S extends Component> extends DelegateIterator<Component, S> {

	private Container container = null;
	
	public ComponentIterator(Class<S> clazz, Component[] items) {
		super(clazz, items);
	}
	
	public ComponentIterator(Class<S> clazz, Container container) {
		super(clazz, container.getComponents());
		this.container = container;
	}

	public void remove() {
		if (container==null)
			super.remove();
		else
			container.remove(current);
	}
	
}
