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
 * Created on Oct 29, 2006
 */
package net.sf.nmedit.nomad.core.swing.explorer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalTreeUI;
import javax.swing.tree.TreePath;

import net.sf.nmedit.nomad.core.swing.explorer.helpers.ExplorerCellRenderer;
import net.sf.nmedit.nomad.core.swing.explorer.helpers.TreeDynamicTreeExpansion;
import net.sf.nmedit.nomad.core.swing.explorer.ExplorerTree;

public class ExplorerTreeUI extends MetalTreeUI
{
    private static Icon getIcon(String name)
    {
        return new ImageIcon(ExplorerTreeUI.class.getClassLoader().getResource(
                "swing/browser/"+name));
    }

    static Icon openIcon = getIcon("node-state-opened.png");
    static Icon closedIcon = getIcon("node-state-closed.png");
    static Icon openIconHov = getIcon("node-state-opened-hovered.png");
    static Icon closedIconHov = getIcon("node-state-closed-hovered.png");

    public static final Icon DefaultFolderIcon =
        getIcon("fldr_obj.gif");
    public static final Icon DefaultFileIcon =
        getIcon("file_obj.gif");
    //Icon leafIcon = new ImageIcon("net/sf/nmedit/nomad/cbrowser/images/...");
     
    public final static Color defaultSelectionBackground = Color.decode("#A8A8A8");
    private Color backgroundSelectionColor = null;
    private SelectionFix sf = new SelectionFix();

    public void installUI( JComponent c ) 
    {
        JTree tree = (JTree) c;
        tree.putClientProperty("JTree.lineStyle", "None");
        super.installUI( c );
        tree.setRootVisible(false);
        ExplorerCellRenderer tcr = new ExplorerCellRenderer();
        tree.setCellRenderer(tcr);
        tcr.setOpenIcon(DefaultFolderIcon);
        tcr.setClosedIcon(DefaultFolderIcon);
        tcr.setLeafIcon(DefaultFileIcon);
        tree.addTreeExpansionListener(new TreeDynamicTreeExpansion(tree));
        tree.setShowsRootHandles(true);
        tree.setScrollsOnExpand(false);
        
    }
    protected void installDefaults() 
    {
        //tree.putClientProperty("Tree.selectionBackground", defaultSelectionBackground);
        super.installDefaults();
        backgroundSelectionColor = defaultSelectionBackground;//UIManager.getColor("Tree.selectionBackground");
        setExpandedIcon(openIcon);
        setCollapsedIcon(closedIcon);
        
    }

    protected void paintRow(Graphics g, Rectangle clipBounds,
                Insets insets, Rectangle bounds, TreePath path,
                int row, boolean isExpanded,
                boolean hasBeenExpanded, boolean isLeaf) 
    {
        // Don't paint the renderer if editing this row.
        if(editingComponent != null && editingRow == row)
            return;
    
        if (tree.isRowSelected(row))
        {
            int h = tree.getRowHeight();
            g.setColor(backgroundSelectionColor);
            g.fillRect(clipBounds.x, h*row, clipBounds.width, h );
            

            /*if(shouldPaintExpandControl(path, row, isExpanded,
                        hasBeenExpanded, isLeaf)) {
                        */
            paintExpandControl(g, bounds, insets, bounds,
                       path, row, isExpanded,
                       hasBeenExpanded, isLeaf);
  //          }
        }
        
        super.paintRow(g, clipBounds, insets, bounds, path, row, isExpanded,
                hasBeenExpanded, isLeaf);
    }

    private MouseListener eventRedirector = new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount()==2)
            {
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path != null)
                {
                    Object tnode = path.getLastPathComponent();
                    if (tnode instanceof ETreeNode)
                    {
                        ETreeNode etn = (ETreeNode) tnode;
                        ContextEvent event = new ContextEvent((ExplorerTree)tree, ExplorerTree.ACTION_OPEN, etn);
                        etn.processEvent(event);
                    }
                }
            }
        }
    };
    
    ExpandControlHoverEffect eche = new ExpandControlHoverEffect();
    protected void installListeners() 
    {
        super.installListeners();
        tree.addMouseListener(eventRedirector);
        tree.addMouseListener(sf);
        tree.addMouseMotionListener(eche);
    }

    protected void uninstallListeners() 
    {
        tree.removeMouseListener(eventRedirector);
        tree.removeMouseMotionListener(eche);
        tree.removeMouseListener(sf);
        super.uninstallListeners();
    }

    protected void paintExpandControl(Graphics g,
                      Rectangle clipBounds, Insets insets,
                      Rectangle bounds, TreePath path,
                      int row, boolean isExpanded,
                      boolean hasBeenExpanded,
                      boolean isLeaf) {
        Object       value = path.getLastPathComponent();
    
        // Draw icons if not a leaf and either hasn't been loaded,
        // or the model child count is > 0.
        //if (!isLeaf && (treeModel.getChildCount(value) > 0)) 
        {
            if (hoveredRow==row)
            {
                setExpandedIcon(openIconHov);
                setCollapsedIcon(closedIconHov);
            }
            else
            {
                setExpandedIcon(openIcon);
                setCollapsedIcon(closedIcon);
            }
            
            super.paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded,
                    hasBeenExpanded, isLeaf);
        }/*
        else {
            super.paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded,
                    hasBeenExpanded, isLeaf);
        }*/
    }

    int hoveredRow = -1;
    int hovx = 0;
    int hovy = 0;
    private class ExpandControlHoverEffect extends MouseMotionAdapter
    {
        public void mouseMoved(MouseEvent e)
        {
            int lastRow = hoveredRow;
            TreePath tp = 
                getClosestPathForLocation(tree, e.getX(), e.getY());
            if (tp == null)
                return ;
            
            int row = tree.getUI().getRowForPath(tree,tp);
            if (ExplorerTreeUI.this.isLocationInExpandControl(
                    row, tp.getPathCount()-1, e.getX(), e.getY()
            ))
            {
                hoveredRow = row;
            }
            else
            {
                hoveredRow=-1;
            }

            if (hoveredRow!=lastRow)
            {
                tree.repaint(hovx-15, hovy-15,30,30);
                tree.repaint(e.getX()-15, e.getY()-15,30,30);

                hovx = e.getX();
                hovy = e.getY();
            }
        }
    }
    
    private static class SelectionFix extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            Component c = e.getComponent();
            if (c instanceof JTree)
            {
                JTree tree = (JTree)c;
                
                
                if (tree.getPathForLocation(e.getX(), e.getY())==null)
                {
                    TreePath tp = tree.getUI().getClosestPathForLocation(tree, e.getX(), e.getY());
                    if (tp!=null)
                    {
                        Rectangle bounds = tree.getPathBounds(tp);
                        if (e.getY()<bounds.y+bounds.height)
                        {
                            tree.setSelectionPath(tp);
    
                            if (e.getClickCount()>=2)
                            {
                                if (tree.isExpanded(tp))
                                    tree.collapsePath(tp);
                                else
                                    tree.expandPath(tp);
                            }
                        }
                    }
                }
            }   
        }
    }


}
