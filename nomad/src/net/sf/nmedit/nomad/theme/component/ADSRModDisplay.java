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
 * Created on Jul 26, 2006
 */
package net.sf.nmedit.nomad.theme.component;

import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.Module;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.Parameter;
import net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.event.ParameterEvent;
import net.sf.nmedit.nomad.theme.property.ParameterProperty;
import net.sf.nmedit.nomad.theme.property.PropertySet;

public class ADSRModDisplay extends ADDisplay
{

    public ADSRModDisplay()
    {
        configureADSR();
    }
    private Parameter parS = null;
    private Parameter parR = null;
    private Parameter parInv = null;

    public final static String IS = "parameter#2";
    public final static String IR = "parameter#3";
    public final static String IINV = "parameter#4";
    
    public void registerProperties(PropertySet set) {
        super.registerProperties(set);
        set.add(new ParameterProperty(2));
        set.add(new ParameterProperty(3));
        set.add(new ParameterProperty(4));
    }

    public void link(Module module) 
    {
        parS = module.getParameter(getParameterInfo(IS).getContextId());
        if (parS!=null) parS.addListener(this);
        parR = module.getParameter(getParameterInfo(IR).getContextId());
        if (parR!=null) parR.addListener(this);
        parInv = module.getParameter(getParameterInfo(IINV).getContextId());
        if (parInv!=null) parInv.addListener(this);
        super.link(module);
    }

    public void unlink() {
        if (parS!=null) parS.removeListener(this);
        if (parR!=null) parR.removeListener(this);
        if (parInv!=null) parInv.removeListener(this);
        parS = null;
        parR = null;
        parInv = null;
        super.unlink();
    }

    public void event(ParameterEvent event)
    {
        super.event(event);
        
        Parameter p = event.getParameter();
        
        if (event.getID()==ParameterEvent.PARAMETER_VALUE_CHANGED)
        {
            if (parS==p) setSustain(getDoubleValue(p));
            else if (parR==p) setRelease(getDoubleValue(p));
            else if (parInv==p) setInverse(parInv.getValue()==1);
        }
    }
    
    protected void updateValues()
    {
        if (parS!=null) setSustain(getDoubleValue(parS));
        if (parR!=null) setRelease(getDoubleValue(parR));
        if (parInv!=null) setInverse(parInv.getValue()==1);
        super.updateValues();
    }
    

}
