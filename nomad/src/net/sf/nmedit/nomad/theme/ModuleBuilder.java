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
 * Created on Jan 12, 2006
 */
package net.sf.nmedit.nomad.theme;

import java.awt.Component;

import javax.swing.JComponent;

import net.sf.nmedit.jmisc.xml.XMLFileWriter;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.Module;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.spec.DModule;
import net.sf.nmedit.nomad.core.nomad.NomadEnvironment;
import net.sf.nmedit.nomad.patch.ui.ModuleSectionUI;
import net.sf.nmedit.nomad.patch.ui.ModuleUI;
import net.sf.nmedit.nomad.theme.component.NomadComponent;
import net.sf.nmedit.nomad.theme.xml.dom.ComponentNode;
import net.sf.nmedit.nomad.theme.xml.dom.ModuleNode;
import net.sf.nmedit.nomad.theme.xml.dom.ThemeNode;


public class ModuleBuilder
{

    private ThemeNode               nomadDom  = null;

    private UIFactory               uifactory = null;

    private NomadEnvironment        env       = null;

    public NomadEnvironment getEnvironment()
    {
        return env;
    }

    public ModuleBuilder( NomadEnvironment env )
    {
        this.env = env;
    }

    public void rewriteDOM( NomadComponent moduleContainer, DModule info )
    {

        if (nomadDom == null) throw new NullPointerException( "No Dom" );

        ModuleNode node = nomadDom.getModuleNode( info.getModuleID() );
        if (node != null)
        {
            node.clear();
        }
        else
        {
            nomadDom.putModuleNode( node = new ModuleNode( info.getModuleID() ) );
        }

        for (Component c : moduleContainer.getComponents())
        {
            if (c instanceof NomadComponent)
            {
                NomadComponent comp = (NomadComponent) c;

                ComponentNode compNode = new ComponentNode( uifactory
                        .getAlias( comp.getClass() ) );

                node.addComponentNode( compNode );

                /* FIXME:
                PropertyUtils.exportToDOM( compNode,  uifactory.getProperties( comp ), comp ); */
            }
        }
    }

    public void exportDom( XMLFileWriter out )
    {
        out.beginTag( "theme", true );

        for (ModuleNode mod : nomadDom)
        {
            out.beginTagStart( "module" );
            out.addAttribute( "id", "" + mod.getId() );
            out.beginTagFinish( true );

            for (ComponentNode compNode : mod)
            {
                out.beginTagStart( "component" );
                out.addAttribute( "name", compNode.getName() ); // TODO use
                // associations
                // i.e.
                // 'button',
                // 'knob'
                out.beginTagFinish( true );

                for (String name : compNode)
                {
                    out.beginTagStart( "property" );
                    out.addAttribute( "name", name );
                    out.addAttribute( "value", compNode.getProperty( name ) );
                    out.beginTagFinish( false );
                }

                out.endTag();
            }
            out.endTag();
        }
        out.endTag();
    }

    public void load()
    {
        nomadDom = uifactory.getThemeSetup();
    }

    public void setUIFactory( UIFactory uifactory )
    {
        this.uifactory = uifactory;
        load();
    }

    public ThemeNode getDom()
    {
        return nomadDom;
    }

    public ModuleUI compose( Module module, ModuleSectionUI moduleSection )
    {
        ModuleUI moduleGUI = compose( module.getDefinition(), moduleSection );
        moduleGUI.setModule( module );
        return moduleGUI;
    }

    // TODO remove components border if cache is used

    public ModuleUI compose( DModule module, ModuleSectionUI moduleSection )
    {
        ModuleUI moduleGUI = uifactory.getModuleGUI( module );
        moduleGUI.setModuleSectionUI( moduleSection );
        createGUIComponents( moduleGUI, module );
        return moduleGUI;
    }

    public ModuleUI compose( DModule module )
    {
        ModuleUI moduleGUI = uifactory.getModuleGUI( module );
        createGUIComponents( moduleGUI, module );
        return moduleGUI;
    }

    public void createGUIComponents( JComponent modulePane,
            DModule moduleInfo )
    {
        createGUIComponentsNoCaching( modulePane, moduleInfo );
    }

    public void createGUIComponentsNoCaching( JComponent modulePane,
            DModule moduleInfo )
    {
        // get module ui information
        ModuleNode domModule = nomadDom
                .getModuleNode( moduleInfo.getModuleID() );

        for (ComponentNode compNode : domModule)
        {
            String compName = compNode.getName();
            Class<? extends NomadComponent> compClass = uifactory
                    .getNomadComponentClass( compName );

            if (compClass == null)
            {
                System.err.println( "Cannot create component with name '"
                        + compName + "'." );
            }
            else
            {
                NomadComponent comp = uifactory
                        .newComponentInstanceByClass( compClass );

                // setup component
                
                compNode.assignProperties( this.uifactory, comp );
                modulePane.add( comp );
            }
        }
    }

}
