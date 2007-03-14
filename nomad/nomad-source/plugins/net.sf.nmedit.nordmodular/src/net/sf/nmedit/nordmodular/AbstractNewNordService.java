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
package net.sf.nmedit.nordmodular;

import net.sf.nmedit.jpatch.clavia.nordmodular.NM1ModuleDescriptions;
import net.sf.nmedit.jsynth.clavia.nordmodular.NordModular;
import net.sf.nmedit.jsynth.nomad.SynthDeviceContext;
import net.sf.nmedit.nomad.core.Nomad;
import net.sf.nmedit.nomad.core.service.Service;
import net.sf.nmedit.nomad.core.service.synthService.NewSynthService;
import net.sf.nmedit.nomad.core.swing.explorer.ExplorerTree;

public abstract class AbstractNewNordService
    implements NewSynthService
{

    public String getSynthDescription()
    {
        return "http://nmedit.sf.net";
    }

    public abstract String getSynthName();

    public String getSynthVendor()
    {
        return "Clavia (http://www.clavia.se)";
    }

    public String getSynthVersion()
    {
        return "OS 3.03";
    }

    public boolean isNewSynthAvailable()
    {
        return true;
    }

    public abstract void newSynth();

    public Class<? extends Service> getServiceClass()
    {
        return NewSynthService.class;
    }
    
    protected void newNordModular(boolean microModular)
    {
        NordModular nm = new NordModular(Nordmodular.sharedContext().getModuleDescriptions());
        nm.setMaxSlotCount(microModular ? 1 : 4);
        
        newContext(nm);
    }

    private void newContext(NordModular nm)
    {
        ExplorerTree et =
            Nomad.sharedInstance().getExplorer();

        SynthDeviceContext sdc = new NMSynthDeviceContext(et, nm,
               getSynthName());
        
        et.getRoot().add(sdc);
        et.fireRootChanged();
    }
    
    protected void newSynth(int maxSlotCount)
    {
        ExplorerTree etree = Nomad.sharedInstance().getExplorer();
        NM1ModuleDescriptions d = Nordmodular.sharedContext().getModuleDescriptions();        
        NordModular nm = new NordModular(d);
        nm.setMaxSlotCount(maxSlotCount);
        etree.addRootNode(new NMSynthDeviceContext(etree, nm, getSynthName()));
    }
    
}
