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
 * Created on Jun 29, 2006
 */
package net.sf.nmedit.nomad.core.main.ui.sidebar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ModulePreview extends ImagePreview
{
    /*
        private DModule preview = null;
        private UIContext context;

        public void setUIContext(UIContext context)
        {
            this.context = context;
        }
        
        public BufferedImage getImageFor(DModule module)
        {

            BufferedImage image = null;
            
            UIContext builder = context;
            NModule ui = null;
            if (builder != null) 
            {
                ui = builder.buildModule(module);
                if (ui!=null) 
                {
                    ui.setMinimumSize(ui.getSize());
                    ui.setMaximumSize(ui.getSize());
                    ui.setPreferredSize(ui.getSize());
                    
                    image = GraphicsToolkit.createCompatibleBuffer(ui.getWidth(), ui.getHeight(), Transparency.TRANSLUCENT);
                    Graphics2D g2 = image.createGraphics();
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0,0,ui.getWidth(), ui.getHeight());
                    try
                    {
                        ui.setModule( new Module(module) );
                        ui.paint(g2);

                    } 
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        // image = null;
                    }
                    finally
                    {
                        if (g2!=null) g2.dispose();
                    }
                }
            }
            
            return image;
        }
        
        // TODO rename: setModule
        public void setPreview(DModule preview)
        {
            if (this.preview!=preview)
            {
                this.preview = preview;
                setPreviewImage(preview==null ? null : getImageFor(preview));
            }
        }
        */
    }