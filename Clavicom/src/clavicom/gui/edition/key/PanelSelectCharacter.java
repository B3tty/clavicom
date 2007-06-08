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

package clavicom.gui.edition.key;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import clavicom.core.keygroup.keyboard.command.CCommand;
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
			TLevelEnum myLevel,
			String type)
	{
		keyCharacter = myKeyCharacter;
		level = myLevel;
		
		setLayout( new BorderLayout() );
		
		// Ajout du libéllé
		JPanel p_caption = new JPanel( );
		JLabel label = new JLabel( UIString.getUIString("LB_KEYCHARACTER_CAPTION") );
		
		if( keyCharacter == null )
		{
			textField = new JTextField();
		}
		else
		{
			textField = new JTextField( myKeyCharacter.getCaption( level ) );
		}
		textField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0)
			{
				
			}

			public void keyReleased(KeyEvent arg0)
			{
				keyCharacter.setCaption( textField.getText(), level );				
			}

			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		textField.setPreferredSize( new Dimension( 80, 23 ) );
		
		p_caption.add( label );
		p_caption.add( textField );
		
		add ( p_caption, BorderLayout.NORTH ) ;
		
		// Ajout des box de séléction
		add ( CreateListViewSections( commandSet ), BorderLayout.CENTER ) ;
		
		if( keyCharacter != null )
		{
			if( keyCharacter.getCommand( level ) != null )
			{
				InitialiseCombo( commandSet, keyCharacter, level );
			}
		}
		
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), type ));
		
		// initialisation de la list
		if( keyCharacter.getCommand( level ) != null )
		{
			list.setSelectedValue( keyCharacter.getCommand( level ) , true);
		}
	}

	private void InitialiseCombo(CCommandSet commandSet, CKeyCharacter keyCharacter2, TLevelEnum level2)
	{
		// on trouve la section de la key
		CSection section = commandSet.getSectionCommand( keyCharacter2.getCommand( level2 ) );
		if( section != null )
		{
			comboSection.setSelectedItem( section );
		}
	}

	private JPanel CreateListViewSections( CCommandSet commandSet )
	{
		JPanel listViewPanel = new JPanel( );
		listViewPanel.setLayout( new BorderLayout() );
		JScrollPane listeAvecAscenseur;

		// Ajout de la comboBox
		comboSection = new JComboBox( commandSet.getSectionsList().values().toArray() );
		comboSection.addActionListener( this );
		listViewPanel.add( comboSection, BorderLayout.NORTH );
		
		// Ajout de la listView
		list = new JList( );
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent arg0)
			{
				if( keyCharacter != null )
				{
					Object object = list.getSelectedValue();
					if( object != null )
					{
						if( object instanceof CCommand )
						{
							keyCharacter.setCommand( (CCommand)object, level);
						}
					}
				}
			}
		});
		
		// simulation d'une selection pour l'initialisation
		actionPerformed( null );

		listeAvecAscenseur = new JScrollPane( list );
		
		listViewPanel.add( listeAvecAscenseur, BorderLayout.CENTER );

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

				Object[] tab = selectedSection.GetCommandMap().values().toArray();
				
				Arrays.sort(tab);
				
				list.setListData( tab ); 
			}
		}
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
