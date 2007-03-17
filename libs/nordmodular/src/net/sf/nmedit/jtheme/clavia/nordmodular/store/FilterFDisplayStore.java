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
package net.sf.nmedit.jtheme.clavia.nordmodular.store;

import net.sf.nmedit.jpatch.Module;
import net.sf.nmedit.jpatch.Parameter;
import net.sf.nmedit.jtheme.JTContext;
import net.sf.nmedit.jtheme.JTException;
import net.sf.nmedit.jtheme.clavia.nordmodular.JTFilterFDisplay;
import net.sf.nmedit.jtheme.component.JTComponent;
import net.sf.nmedit.jtheme.component.JTParameterControlAdapter;
import net.sf.nmedit.jtheme.store.ControlStore;
import net.sf.nmedit.jtheme.store.StorageContext;
import net.sf.nmedit.jtheme.store.Store;
import net.sf.nmedit.jtheme.store.helpers.ParameterDescriptorHelper;

import org.jdom.Element;

public class FilterFDisplayStore extends ControlStore
{

    protected ParameterDescriptorHelper resonanceParameterHelper;
    protected ParameterDescriptorHelper slopeParameterHelper;
    
    protected FilterFDisplayStore(Element element)
    {
        super(element);
    }

    public static Store create(StorageContext context, Element element)
    {
        return new FilterFDisplayStore(element);
    }

    protected void initDescriptors(Element element)
    {
        parameterDescriptorHelper = ParameterDescriptorHelper.createHelper(element.getChild("cutoff"));
        resonanceParameterHelper = ParameterDescriptorHelper.createHelper(element.getChild("resonance"));
        slopeParameterHelper = ParameterDescriptorHelper.createHelper(element.getChild("slope"));
    }

    @Override
    public JTComponent createComponent(JTContext context) throws JTException
    {
        JTComponent component = context.createComponentInstance(JTFilterFDisplay.class);
        applyLocation(component);
        applySize(component);
        return component;
    }

    protected void link(JTContext context, JTComponent component, Module module)
      throws JTException
    {
        Parameter cutoff = parameterDescriptorHelper.lookup(module);
        Parameter resonance = resonanceParameterHelper.lookup(module);
        Parameter slope = slopeParameterHelper.lookup(module);
        
        JTFilterFDisplay disp = (JTFilterFDisplay) component;
        
        if (cutoff != null)
            disp.setCutoffAdapter(new JTParameterControlAdapter(cutoff));
        if (resonance != null)
            disp.setResonanceAdapter(new JTParameterControlAdapter(resonance));
        if (slope != null)
            disp.setSlopeAdapter(new JTParameterControlAdapter(slope));
    }
    
    protected void link2(JTContext context, JTComponent component, Module module, Parameter parameter)
    {
        throw new UnsupportedOperationException();
    }
    
}