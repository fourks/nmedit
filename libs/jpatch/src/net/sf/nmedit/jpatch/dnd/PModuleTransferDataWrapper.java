package net.sf.nmedit.jpatch.dnd;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;


import net.sf.nmedit.jpatch.CopyOperation;
import net.sf.nmedit.jpatch.MoveOperation;
import net.sf.nmedit.jpatch.PConnectionManager;
import net.sf.nmedit.jpatch.PModule;
import net.sf.nmedit.jpatch.PModuleContainer;
import net.sf.nmedit.jpatch.PPatch;

public class PModuleTransferDataWrapper implements PModuleTransferData {
    private PModuleContainer sourceContainer;
    
    ModulesBoundingBox boundingBox;

    public PModuleTransferDataWrapper(PModuleContainer delegate, Collection<? extends PModule> modules, Point dragStartLocation)
    {
        this.sourceContainer = delegate;
        this.boundingBox = new ModulesBoundingBox(modules, dragStartLocation);
    }

    public Point getDragStartLocation()
    {
        return boundingBox.getDragStartLocation();
    }

    public Collection<? extends PModule> getModules()
    {
        return boundingBox.getModules();
    }
    
    public PModuleContainer getSourceModuleContainer()
    {
        return sourceContainer;
    }
    
	public PPatch getSourcePatch() {
		return sourceContainer.getPatch();
	}


    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
    {
        if (!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);
        if (flavor.equals(PDragDrop.ModuleSelectionFlavor))
        	return this;
        if (flavor.equals(PDragDrop.PatchFileFlavor)) {
			try {
//				System.out.println("patch file flavor");
				PModuleContainer srcMc = sourceContainer;
				PPatch patch = srcMc.getPatch();
				PPatch newPatch = patch.createEmptyPatch();
				PModuleContainer dstMc = newPatch.getModuleContainer(0);
				
				CopyOperation copy = srcMc.createCopyOperation();
				copy.setDestination(dstMc);
	            for (PModule module: getModules()) {
	                copy.add(module);
	            }
	            copy.setScreenOffset(0, 0);
	            copy.copy();
	            
	            String str = newPatch.patchFileString();
	            return new ByteArrayInputStream(str.getBytes());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return null;
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        DataFlavor[] flavors = {PDragDrop.ModuleSelectionFlavor, PDragDrop.PatchFileFlavor};
        return flavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        for (DataFlavor f: getTransferDataFlavors())
            if (f.equals(flavor))
                return true;
        return false;
    }

	public Rectangle getBoundingBox() {
		return boundingBox.getBoundingBox();
	}

	public Rectangle getBoundingBox(Rectangle r) {
		return boundingBox.getBoundingBox(r);
	}

}