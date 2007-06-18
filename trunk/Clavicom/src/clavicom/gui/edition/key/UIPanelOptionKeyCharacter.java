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
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
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
		
		JPanel panellevelNormal = new JPanel();
		panellevelNormal.add( new UIPanelSelectCharacter( commandSet,  TLevelEnum.getString( TLevelEnum.NORMAL ), TLevelEnum.NORMAL ) );
		characters.add( panellevelNormal );
		
		JPanel panellevelShift = new JPanel();
		panellevelShift.add( new UIPanelSelectCharacter( commandSet,  TLevelEnum.getString( TLevelEnum.SHIFT ), TLevelEnum.SHIFT ) );
		characters.add( panellevelShift  );
		
		JPanel panellevelAltGr = new JPanel();
		panellevelAltGr.add( new UIPanelSelectCharacter( commandSet,  TLevelEnum.getString( TLevelEnum.ALT_GR), TLevelEnum.ALT_GR ) );
		characters.add( panellevelAltGr  );
		
		add( characters, BorderLayout.CENTER );
	}
	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyCharacter( CKeyCharacter myKeyCharacter, CCommandSet commandSet)
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
