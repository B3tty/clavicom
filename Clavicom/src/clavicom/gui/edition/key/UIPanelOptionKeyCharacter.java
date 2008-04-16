/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionKeyCharacter.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key.option

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

package clavicom.gui.edition.key;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;

public class UIPanelOptionKeyCharacter extends UIPanelOptionThreeLevelKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyCharacter keyCharacter;
	UIPanelSelectCharacter selectCharacterNormal;
	UIPanelSelectCharacter selectCharacterShift;
	UIPanelSelectCharacter selectCharacterAltGr;
	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyCharacter( )
	{

		super();

		CCommandSet commandSet = CCommandSet.GetInstance();
		
		JPanel characters = new JPanel();
		characters.setLayout(new BoxLayout(characters, BoxLayout.X_AXIS));
		
		JPanel panellevelNormal = new JPanel(new BorderLayout());
		selectCharacterNormal = new UIPanelSelectCharacter( commandSet,  UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_NORMAL"), TLevelEnum.NORMAL );
		panellevelNormal.add( selectCharacterNormal, BorderLayout.CENTER);
		characters.add( panellevelNormal );
		
		JPanel panellevelShift = new JPanel(new BorderLayout());
		selectCharacterShift = new UIPanelSelectCharacter( commandSet,  UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_SHIFT"), TLevelEnum.SHIFT );
		panellevelShift.add( selectCharacterShift, BorderLayout.CENTER  );
		characters.add( panellevelShift  );
		
		JPanel panellevelAltGr = new JPanel(new BorderLayout());
		selectCharacterAltGr = new UIPanelSelectCharacter( commandSet,  UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_ALTGR"), TLevelEnum.ALT_GR ); 
		panellevelAltGr.add( selectCharacterAltGr, BorderLayout.CENTER );
		characters.add( panellevelAltGr  );
		
		// Ajout du titre
		characters.setBorder( 	BorderFactory.createTitledBorder( 
								BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("LB_KEYCHARACTER_BORDER")) );
		
		add( characters);
	}
	//----------------------------------------------------------- METHODES --//	
	
	public void setValuesKeyCharacter( CKeyCharacter myKeyCharacter)
	{
		// Appel au père
		setValuesKeyThreeLevel(myKeyCharacter);
		
		keyCharacter = myKeyCharacter;
		selectCharacterNormal.setValues(myKeyCharacter);
		selectCharacterShift.setValues(myKeyCharacter);
		selectCharacterAltGr.setValues(myKeyCharacter);
		
		
	}


	//--------------------------------------------------- METHODES PRIVEES --//
}
