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
 * Created on Feb 22, 2006
 */
package org.nomad.theme.property;

import java.util.Map;

import org.nomad.xml.XMLFileWriter;
import org.nomad.xml.dom.theme.ComponentNode;

public class PropertyUtils {

	public static void exportToDOM(ComponentNode node, Map<String, Property> map) {
		for (Property p : map.values()) {
			if (p.isExportable() && (!p.isInDefaultState())) {
				node.createPropertyNode(p.getName()).setValue(p.getValue());
			}
		}
	}

	public static void exportToXml(XMLFileWriter xml, Map<String, Property> map) {
		xml.beginTag("properties", true);
		for (Property p : map.values())
			p.exportToXml(xml);
		xml.endTag();
	}

}