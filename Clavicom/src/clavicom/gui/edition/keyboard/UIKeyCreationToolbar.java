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

import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.key.CKeyCreation;
import clavicom.gui.keyboard.key.UIKeyCreation;
import clavicom.gui.language.UIString;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TPoint;

public class UIKeyCreationToolbar extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	Color COLOR_NORMAL = new Color(230,230,230);
	Color COLOR_PRESSED = new Color(100,200,220);
	Color COLOR_ENTERED = new Color(22,90,107);
	
	//---------------------------------------------------------- VARIABLES --//
	UIKeyCreation uiKeyCharacter;
	UIKeyCreation uiKeyLastWord;
	UIKeyCreation uiKeyLauncher;
	UIKeyCreation uiKeyPrediction;
	UIKeyCreation uiKeyShortCut;
	UIKeyCreation uiKeyString;
	
	// KeyLevel
	UIKeyCreation uiKeyLevelSHIFT;
	UIKeyCreation uiKeyLevelALTGR;
	
	// KeyClavicom
	UIKeyCreation uiKeyClavicomClose;
	UIKeyCreation uiKeyClavicomConfiguration;
	UIKeyCreation uiKeyClavicomSwitchSouricom;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIKeyCreationToolbar(
			Color myColorNormal,
			Color myColorEntered,
			Color myColorPressed)
	{
		setLayout( new GridLayout(2, 6, 2, 2) );
		
		// ===================================================================
		// création de toutes les touches
		// ===================================================================
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyCharacterPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYCHARACTER"),
				TEnumCreationKey.T_KEY_CHARACTER
				);
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyLauncherPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLAUNCHER"),
				TEnumCreationKey.T_KEY_LAUNCHER
				);
		
	
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyLastWordPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYLASTWORD"),
				TEnumCreationKey.T_KEY_LASTWORD
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyPredictionPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYPREDICTION"),
				TEnumCreationKey.T_KEY_PREDICTION
				);
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyShortCutPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSHORTCUT"),
				TEnumCreationKey.T_KEY_SHORTCUT
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyStringPicture(),
				UIString.getUIString("LB_KEYCREATION_KEYSTRING"),
				TEnumCreationKey.T_KEY_STRING
				);
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyLevelSHIFTPicture(),
				UIString.getUIString("LB_KEYCREATION_SHIFT"),
				TEnumCreationKey.T_KEY_LEVEL_SHIFT
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyLevelALTGRPicture(),
				UIString.getUIString("LB_KEYCREATION_ALTGR"),
				TEnumCreationKey.T_KEY_LEVEL_ALTGR
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyClavicomClosePicture(),
				UIString.getUIString("LB_KEYCREATION_CLOSE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_CLOSE_APPLICATION
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyClavicomConfigurationPicture(),
				UIString.getUIString("LB_KEYCREATION_CONFIGURE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_OPEN_CONFIGURATION
				);
		
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				CFilePaths.getToolKeyClavicomSwitchSouricomPicture(),
				UIString.getUIString("LB_KEYCREATION_SWITCH_SOURICOM"),
				TEnumCreationKey.T_KEY_CLAVICOM_SWITCH_SOURICOM
				);
		
		
		
		
		// ===================================================================
		// Placement de toutes les touches
		// ===================================================================
	
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected void CreateKey(	Color myColorNormal,
								Color myColorEntered,
								Color myColorPressed,
								String filePath,
								String caption, 			// texte affiché en tip
								TEnumCreationKey type) 		// Type de la key
	{
		// Création de l'objet du noyau
		CKeyCreation keyLauncher = new CKeyCreation(
				COLOR_NORMAL,
				COLOR_ENTERED,
				COLOR_PRESSED,
				new TPoint(0,0),new TPoint(0,0),
				filePath,
				type);
		
		keyLauncher.setCaptionImage( true );
		
		// Création de l'objet UI
		uiKeyLauncher = new UIKeyCreation( keyLauncher );
		add( uiKeyLauncher, BorderLayout.CENTER );
	}
}

