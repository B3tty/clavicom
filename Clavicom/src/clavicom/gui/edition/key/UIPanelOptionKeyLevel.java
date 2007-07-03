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

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.gui.language.UIString;


public class UIPanelOptionKeyLevel extends UIPanelOptionOneLevelKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyLevel keyLevel;
	JCheckBox alwaysHoldable;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyLevel()
	{
		super( );

		JPanel panel = new JPanel();
		
		alwaysHoldable = new JCheckBox( UIString.getUIString("LB_KEYLEVEL_ALWAYHOLDABLE"), false );
		panel.add( alwaysHoldable );
		
		// Ajout du titre
		panel.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_KEYLAUNCHER_BORDER")) );

		add( panel);
		

		
	}
	//----------------------------------------------------------- METHODES --//
	public void setValuesKeyLevel( CKeyLevel myKeyLevel )
	{
		// Appel au père
		setValuesKeyOneLevel(myKeyLevel);
		
		keyLevel = myKeyLevel;
		
		alwaysHoldable.setSelected( myKeyLevel.isAlwaysHoldable() );
	}
	
	

	//--------------------------------------------------- METHODES PRIVEES --//
}
