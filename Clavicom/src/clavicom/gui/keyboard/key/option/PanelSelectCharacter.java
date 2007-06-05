/*-----------------------------------------------------------------------------+

			Filename			: PanelSelectCharacter.java
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

package clavicom.gui.keyboard.key.option;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.commandSet.CSection;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.gui.language.UIString;

public class PanelSelectCharacter extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKeyCharacter keyCharacter;
	JTextField textField;
	List< JList > jlists;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelSelectCharacter( 
			CKeyCharacter myKeyCharacter, 
			CCommandSet commandSet )
	{
		keyCharacter = myKeyCharacter;
		
		// Ajout du libéllé
		JPanel p_caption = new JPanel();
		JLabel label = new JLabel( UIString.getUIString("LB_KEYCHARACTER_CAPTION") );
		textField = new JTextField();
		
		p_caption.add( label );
		p_caption.add( textField );
		
		add ( p_caption ) ;
		
		// Ajout des box de séléction
		add ( CreateListViewSections( commandSet ) ) ;
	}

	private JPanel CreateListViewSections( CCommandSet commandSet )
	{
		JPanel listViewPanel = new JPanel();
		jlists = new ArrayList<JList>();
		
		// pour chaques sections
		
		for( CSection section : commandSet.getSectionsList().values() )
		{
			JPanel p_section = new JPanel();
			p_section.add( new JLabel( section.GetName() ) );
			
			// création de la list
			//JList ---------- IICICICICICICICICIICICICICCI 
		}
		
		return listViewPanel;
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
