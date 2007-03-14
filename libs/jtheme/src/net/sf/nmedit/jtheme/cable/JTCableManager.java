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
package net.sf.nmedit.jtheme.cable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Iterator;

import javax.swing.JComponent;

import net.sf.nmedit.jtheme.component.JTConnector;

public interface JTCableManager extends Iterable<Cable>
{

    void add(Cable cable);
    void remove(Cable cable);
    
    void markDirty(Cable cable);
    void markCompletelyDirty();
    void update(Cable cable);
    
    int size();
    void clear();
    
    boolean hasDirtyRegion();

    Iterator<Cable> getVisible();
    Iterator<Cable> getCables();

    Rectangle getCoveredArea();
    Rectangle getCoveredArea(Rectangle r);
    
    void setVisibleRegion(int x, int y, int width, int height);
    void setVisibleRegion(Rectangle r);
    
    Rectangle getVisibleRegion();
    Rectangle getVisibleRegion(Rectangle r);

    void paintCables(Graphics2D g2, CableRenderer cableRenderer);
    void paintCables(Graphics2D g2);
    
    void setCableRenderer(CableRenderer cr);
    CableRenderer getCableRenderer();
    
    void notifyRepaintManager();

    void setView(JComponent view);
    JComponent getView();

    Cable createCable(JTConnector source, JTConnector destination);
    
}
