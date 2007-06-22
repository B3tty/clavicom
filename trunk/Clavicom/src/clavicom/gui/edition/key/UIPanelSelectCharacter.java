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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.commandSet.CSection;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;

public class UIPanelSelectCharacter extends JPanel implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKeyCharacter keyCharacter;
	TLevelEnum level;
	JComboBox comboSection;
	JList list;
	CCommandSet commandSet;
	
	JCheckBox chkNoAction;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIPanelSelectCharacter(CCommandSet myCommandSet, String type, TLevelEnum myLevel)
	{
		level = myLevel;
		commandSet = myCommandSet;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Création de la combo
		chkNoAction = new JCheckBox();
		chkNoAction.setText(UIString.getUIString("LB_KEYCHARACTER_CHECKBOX_NOACTION"));
		chkNoAction.addActionListener(new AbstractAction() {
											public void actionPerformed(ActionEvent arg0)
											{
												chkNoActionChanged();
											}
			});
		
		add(chkNoAction);
		
		// Ajout du libéllé
		JPanel p_caption = new JPanel( );
		
		add ( p_caption) ;
		
		// Ajout des box de séléction
		add ( CreateListViewSections( commandSet )) ;
		
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), type ));
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
	public void setValues( CKeyCharacter myKeyCharacter)
	{
		keyCharacter = myKeyCharacter;
		
		if( keyCharacter != null )
		{
			if( keyCharacter.getCommand( level ) != null )
			{
				InitialiseCombo( commandSet, keyCharacter, level );
			}
		}
		
		// initialisation de la list
		if( keyCharacter.getCommand( level ) != null )
		{
			list.setSelectedValue( keyCharacter.getCommand( level ) , true);
			
			// On décoche la checkbox
			chkNoAction.setSelected(false);
			
			// On dégrise la selection d'action
			list.setEnabled(true);
			comboSection.setEnabled(true);	
		}
		else
		{
			// On coche la checkbox
			chkNoAction.setSelected(true);
			
			// On grise la selection d'action
			list.setEnabled(false);
			comboSection.setEnabled(false);
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	protected void updateCaption(String caption)
	{
		if((keyCharacter != null) && (level != null))
			keyCharacter.setCaption( caption, level, true );
	}
	
	protected void chkNoActionChanged()
	{
		if(chkNoAction.isSelected() == true)
		{
			// On grise la selection d'action
			list.setEnabled(false);
			comboSection.setEnabled(false);
			
			// On met une commande nulle dans le noyau
			keyCharacter.setCommand(null, level);
		}
		else
		{
			// On dégrise la selection d'action
			list.setEnabled(true);
			comboSection.setEnabled(true);	
		}
	}
}
