/*-----------------------------------------------------------------------------+

			Filename			: PanelKeyCreation.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.edition.keyboard

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

package clavicom.gui.edition.keyboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.key.CKeyCreation;
import clavicom.gui.engine.UIKeyCreationEngine;
import clavicom.gui.keyboard.key.UIKeyCreation;
import clavicom.gui.language.UIString;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TPoint;

public class UIKeyCreationToolbar extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	private final Color COLOR_NORMAL = new Color(180,180,180);
	private final Color COLOR_PRESSED = new Color(100,200,220);
	private final Color COLOR_ENTERED = new Color(22,90,107);
	
	private final int SPACE_BETWEEN_KEYS = 2;
	
	//---------------------------------------------------------- VARIABLES --//
	private UIKeyCreation uiKeyLauncher;
	protected final EventListenerList listeners = new EventListenerList();	// Listeners de création de touche
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIKeyCreationToolbar(
			Color myColorNormal,
			Color myColorEntered,
			Color myColorPressed)
	{
		setLayout( new GridLayout(1, 11, SPACE_BETWEEN_KEYS, SPACE_BETWEEN_KEYS) );
		
		// ===================================================================
		// création de toutes les touches
		// ===================================================================
		CreateKey(CFilePaths.getToolKeyCharacterPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYCHARACTER"),
				TEnumCreationKey.T_KEY_CHARACTER
				);
		
		CreateKey(CFilePaths.getToolKeyLauncherPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLAUNCHER"),
				TEnumCreationKey.T_KEY_LAUNCHER
				);
	
		CreateKey(CFilePaths.getToolKeyLastWordPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLASTWORD"),
				TEnumCreationKey.T_KEY_LASTWORD
				);
		
		CreateKey(CFilePaths.getToolKeyPredictionPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYPREDICTION"),
				TEnumCreationKey.T_KEY_PREDICTION
				);
		
		CreateKey(CFilePaths.getToolKeyShortCutPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSHORTCUT"),
				TEnumCreationKey.T_KEY_SHORTCUT
				);
		
		CreateKey(CFilePaths.getToolKeyStringPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSTRING"),
				TEnumCreationKey.T_KEY_STRING
				);

		CreateKey(CFilePaths.getToolKeyLevelSHIFTPicture(),
				UIString.getUIString("LB_KEYCREATION_SHIFT"),
				TEnumCreationKey.T_KEY_LEVEL_SHIFT
				);
		
		CreateKey(CFilePaths.getToolKeyLevelALTGRPicture(),
				UIString.getUIString("LB_KEYCREATION_ALTGR"),
				TEnumCreationKey.T_KEY_LEVEL_ALTGR
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomClosePicture(),
				UIString.getUIString("LB_KEYCREATION_CLOSE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_CLOSE_APPLICATION
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomConfigurationPicture(),
				UIString.getUIString("LB_KEYCREATION_CONFIGURE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_OPEN_CONFIGURATION
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomSwitchSouricomPicture(),
				UIString.getUIString("LB_KEYCREATION_SWITCH_SOURICOM"),
				TEnumCreationKey.T_KEY_CLAVICOM_SWITCH_SOURICOM
				);
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected void CreateKey(	String filePath,
								String caption, 			// texte affiché en tip
								TEnumCreationKey type) 		// Type de la key
	{
		// Création de l'objet du noyau
		CKeyCreation keyCreation = new CKeyCreation(
				COLOR_NORMAL,
				COLOR_ENTERED,
				COLOR_PRESSED,
				new TPoint(0,0),new TPoint(0,0),
				filePath,
				type);
		
		keyCreation.setCaptionImage( true );
		
		// Abonnement du UIKeyCreationEngine sur cette key
		keyCreation.addOnClickKeyCreationListener(UIKeyCreationEngine.getInstance());		
		
		// Création de l'objet UI
		uiKeyLauncher = new UIKeyCreation( keyCreation );
		
		// On désactive la transparence
		uiKeyLauncher.setTransparent(false);
		
		add( uiKeyLauncher, BorderLayout.CENTER );
	}
}

