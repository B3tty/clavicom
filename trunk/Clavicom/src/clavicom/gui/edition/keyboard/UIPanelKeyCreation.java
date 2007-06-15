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
import clavicom.core.keygroup.keyboard.key.CKeyCreation;
import clavicom.gui.keyboard.key.UIKeyCreation;
import clavicom.gui.language.UIString;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TPoint;

public class UIPanelKeyCreation extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

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
	public UIPanelKeyCreation(
			Color myColorNormal,
			Color myColorEntered,
			Color myColorPressed)
	{
		
		GridLayout grdiLayout = new GridLayout( 3,6 );
		grdiLayout.setHgap(2);
		grdiLayout.setVgap(2);
		setLayout( grdiLayout );
		
		// ===================================================================
		// création de toutes les touches
		// ===================================================================
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYCHARACTER"),
				TEnumCreationKey.T_KEY_CHARACTER,
				false
				);
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYLAUNCHER"),
				TEnumCreationKey.T_KEY_LAUNCHER,
				false
				);
		
	
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYLASTWORD"),
				TEnumCreationKey.T_KEY_LASTWORD,
				false
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYPREDICTION"),
				TEnumCreationKey.T_KEY_PREDICTION,
				false
				);
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYSHORTCUT"),
				TEnumCreationKey.T_KEY_SHORTCUT,
				false
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_KEYSTRING"),
				TEnumCreationKey.T_KEY_STRING,
				false
				);
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_SHIFT"),
				TEnumCreationKey.T_KEY_LEVEL_SHIFT,
				false
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_ALTGR"),
				TEnumCreationKey.T_KEY_LEVEL_ALTGR,
				false
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_CLOSE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_CLOSE_APPLICATION,
				false /* TODO - true */
				);
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_CONFIGURE_APPLICATION"),
				TEnumCreationKey.T_KEY_CLAVICOM_OPEN_CONFIGURATION,
				false /* TODO - true */
				);
		
		
		
		
		CreateKey(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				UIString.getUIString("LB_KEYCREATION_SWITCH_SOURICOM"),
				TEnumCreationKey.T_KEY_CLAVICOM_SWITCH_SOURICOM,
				false /* TODO - true */
				);
		
		
		
		
		// ===================================================================
		// Placement de toutes les touches
		// ===================================================================
	
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected void CreateKey(
			Color myColorNormal,
			Color myColorEntered,
			Color myColorPressed,
			String caption, // texte affiché au dessus du boutton
			TEnumCreationKey type, // type de la key
			boolean isImage) // si c'est une image ou non
							// ATTENTION : si c'est une image, il faut mettre le chemin d'une image valide
	{

		JPanel panel = new JPanel( new BorderLayout() );
		
		CKeyCreation keyLauncher = new CKeyCreation(
				myColorNormal,
				myColorEntered,
				myColorPressed,
				new TPoint(0,0),new TPoint(0,0),
				caption,
				type);
		
		keyLauncher.setCaptionImage( isImage );
		
		uiKeyLauncher = new UIKeyCreation( keyLauncher );
		panel.add( uiKeyLauncher, BorderLayout.CENTER );
		
		add( panel );
	}
}

