/*-----------------------------------------------------------------------------+

			Filename			: GlobalProfilModification.java
			Creation date		: 12 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2007) Centre ICOM'

							-------------------------

	This program is free software. You can redistribute it and/or modify it 
 	under the terms of the GNU Lesser General Public License as published by 
	the Free Software Foundation. Either version 2.1 of the License, or (at your 
    option) any later version.

	This program is distributed in the hope that it will be useful, but WITHOUT 
	ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
	FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
    more details.

+-----------------------------------------------------------------------------*/

package clavicom.gui.configuration;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class GlobalProfilModification extends JTabbedPane
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	PanelModificationProfilCommandSetName panelCommandSetName;
	PanelModificationProfilDictionaryName panelDictionaryName;
	PanelModificationProfilFont panelFont;
	PanelModificationProfilKeyboardColor panelKeyboardColor;
	PanelModificationProfilNavigation panelNavigation;
	PanelModificationProfilPreferedWords panelPreferedWords;
	PanelModificationProfilSound panelSound;
	PanelModificationProfilTransparency panelTransparency;
	PanelModificationProfilLangueUIName panelLangueUI;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public GlobalProfilModification( CProfil profil )
	{
		// panel global de l'application
		
		
		// panel des command Set
		JPanel panel = new JPanel(); 
		panelCommandSetName = new PanelModificationProfilCommandSetName( profil.getCommandSetName() );
		panel.add( panelCommandSetName );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_COMMANDSET"), panel);
		
		// panel des dictionary name
		panel = new JPanel(); 
		panelDictionaryName = new PanelModificationProfilDictionaryName( profil.getDictionnaryName() );
		panel.add( panelDictionaryName );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_DICTIONARY"), panel);
		
		// panel des fonts
		panel = new JPanel(); 
		panelFont = new PanelModificationProfilFont( profil.getKeyboardFont() );
		panel.add( panelFont );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT"), panel);
		
		// panel des kayboard color
		panel = new JPanel(); 
		panelKeyboardColor = new PanelModificationProfilKeyboardColor( profil.getDefaultColor() );
		panel.add( panelKeyboardColor );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR"), panel);
		
		// panel des languesUI
		panel = new JPanel(); 
		panelLangueUI = new PanelModificationProfilLangueUIName( profil.getLangueUI() );
		panel.add( panelLangueUI );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_LANGUAGE"), panel);
		
		// panel de navigation
		panel = new JPanel( new BorderLayout() ); 
		panelNavigation = new PanelModificationProfilNavigation( profil.getNavigation() );
		panel.add( panelNavigation, BorderLayout.CENTER );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION"), panel);
		
		// panel des preferedWords
		panel = new JPanel(); 
		panelPreferedWords = new PanelModificationProfilPreferedWords( profil.getPreferedWords() );
		panel.add( panelPreferedWords );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS"), panel);
		
		// panel des sound
		panel = new JPanel(); 
		panelSound = new PanelModificationProfilSound( profil.getSound() );
		panel.add( panelSound );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_SOUND"), panel);
		
		// panel de la transparence
		panel = new JPanel(); 
		panelTransparency = new PanelModificationProfilTransparency( profil.getTransparency() );
		panel.add( panelTransparency );
		addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_TRANSPARENCY"), panel);
		
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
