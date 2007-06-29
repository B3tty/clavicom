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
				TEnumCreationKey.T_KEY_CHARACTER,
				UIString.getUIString("LB_KEYCREATION_KEYCHARACTER_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyLauncherPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLAUNCHER"),
				TEnumCreationKey.T_KEY_LAUNCHER,
				UIString.getUIString("LB_KEYCREATION_KEYLAUNCHER_TOOLTIP")
				);
	
		CreateKey(CFilePaths.getToolKeyLastWordPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLASTWORD"),
				TEnumCreationKey.T_KEY_LASTWORD,
				UIString.getUIString("LB_KEYCREATION_KEYLASTWORD_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyPredictionPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYPREDICTION"),
				TEnumCreationKey.T_KEY_PREDICTION,
				UIString.getUIString("LB_KEYCREATION_KEYPREDICTION_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyShortCutPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSHORTCUT"),
				TEnumCreationKey.T_KEY_SHORTCUT,
				UIString.getUIString("LB_KEYCREATION_KEYSHORTCUT_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyStringPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSTRING"),
				TEnumCreationKey.T_KEY_STRING,
				UIString.getUIString("LB_KEYCREATION_KEYSTRING_TOOLTIP")
				);

		CreateKey(CFilePaths.getToolKeyLevelSHIFTPicture(),
				UIString.getUIString("LB_KEYCREATION_SHIFT"),
				TEnumCreationKey.T_KEY_LEVEL_SHIFT,
				UIString.getUIString("LB_KEYCREATION_SHIFT_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyLevelALTGRPicture(),
				UIString.getUIString("LB_KEYCREATION_ALTGR"),
				TEnumCreationKey.T_KEY_LEVEL_ALTGR,
				UIString.getUIString("LB_KEYCREATION_KEYCHARACTER_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomClosePicture(),
				UIString.getUIString("LB_KEYCREATION_CLOSE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_CLOSE_APPLICATION,
				UIString.getUIString("LB_KEYCREATION_CLOSE_APPLICATION_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomConfigurationPicture(),
				UIString.getUIString("LB_KEYCREATION_CONFIGURE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_OPEN_CONFIGURATION,
				UIString.getUIString("LB_KEYCREATION_CONFIGURE_APPLICATION_TOOLTIP")
				);
		
		CreateKey(CFilePaths.getToolKeyClavicomSwitchSouricomPicture(),
				UIString.getUIString("LB_KEYCREATION_SWITCH_SOURICOM"),
				TEnumCreationKey.T_KEY_CLAVICOM_SWITCH_SOURICOM,
				UIString.getUIString("LB_KEYCREATION_SWITCH_SOURICOM_TOOLTIP")
				);
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected void CreateKey(	String filePath,
								String caption, 			// texte affiché en tip
								TEnumCreationKey type,		// Type de la key
								String tooltip) 			// Tooltip
	{
		// Création de l'objet du noyau
		CKeyCreation keyCreation = new CKeyCreation(
				COLOR_NORMAL,
				COLOR_ENTERED,
				COLOR_PRESSED,
				new TPoint(0,0),new TPoint(0,0),
				filePath,
				type,
				tooltip);
		
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

