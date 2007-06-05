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

package clavicom.gui.keyboard.key.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.commandSet.CSection;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;

public class PanelSelectCharacter extends JPanel implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKeyCharacter keyCharacter;
	JTextField textField;
	TLevelEnum level;
	JComboBox comboSection;
	JList list;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelSelectCharacter( 
			CKeyCharacter myKeyCharacter, 
			CCommandSet commandSet,
			TLevelEnum myLevel)
	{
		keyCharacter = myKeyCharacter;
		level = myLevel;
		
		// Ajout du libéllé
		JPanel p_caption = new JPanel();
		JLabel label = new JLabel( UIString.getUIString("LB_KEYCHARACTER_CAPTION") );
		
		if( keyCharacter == null )
		{
			textField = new JTextField();
		}
		else
		{
			textField = new JTextField( myKeyCharacter.getCaption( level ) );
		}
		
		p_caption.add( label );
		p_caption.add( textField );
		
		add ( p_caption ) ;
		
		// Ajout des box de séléction
		add ( CreateListViewSections( commandSet ) ) ;
	}

	private JPanel CreateListViewSections( CCommandSet commandSet )
	{
		JPanel listViewPanel = new JPanel();
		JPanel p_section;
		JScrollPane listeAvecAscenseur;

		// Ajout de la comboBox
		comboSection = new JComboBox( commandSet.getSectionsList().values().toArray() );
		comboSection.addActionListener( this );
		listViewPanel.add( comboSection );
		
		// Ajout de la listView
		list = new JList( );
		listeAvecAscenseur = new JScrollPane( list );
		
		listViewPanel.add( listeAvecAscenseur );
		
		// simulation d'une selection pour l'initialisation
		actionPerformed( null );
		
		return listViewPanel;
	}

	public void actionPerformed(ActionEvent arg0)
	{
		Object object = comboSection.getSelectedItem();
		if( object != null )
		{
			if( object instanceof CSection )
			{
				CSection selectedSection = (CSection)object;

				list.setListData( selectedSection.GetCommandMap().values().toArray() ); 
			}
		}
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
