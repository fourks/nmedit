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
 * Created on Feb 23, 2006
 */
package net.sf.nmedit.nomad.main.action;

import java.awt.event.ActionEvent;

import net.sf.nmedit.nomad.main.Nomad;
import net.sf.nmedit.nomad.main.dialog.NomadAboutDialog;
import net.sf.nmedit.nomad.main.resources.AppIcons;


public class AboutDialogAction extends NomadAction {

	public AboutDialogAction(Nomad nomad) {
		super(nomad);
		
		final String description = "Nomad About-Dialog";

		putValue(NAME, "About Nomad");
		putValue(SMALL_ICON, AppIcons.IC_APP_ABOUT);
	    putValue(SHORT_DESCRIPTION, description);
	    putValue(LONG_DESCRIPTION, 	description);
	    //putValue(ACCELERATOR_KEY, 	KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		
	}

	public void actionPerformed(ActionEvent event) {
        NomadAboutDialog dlg = new NomadAboutDialog();
        dlg.invoke();
	}

}