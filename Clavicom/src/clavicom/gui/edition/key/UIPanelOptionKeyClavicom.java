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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyClavicomActionType;


public class UIPanelOptionKeyClavicom extends UIPanelOptionOneLevelKey implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyClavicom keyClavicom;
	JComboBox comboBox;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyClavicom( )
	{
		super( );
		
		comboBox = new JComboBox();
		comboBox.addItem( TKeyClavicomActionType.NONE );
		comboBox.addItem( TKeyClavicomActionType.OPEN_CONFIGURATION );
		comboBox.addItem( TKeyClavicomActionType.CLOSE_APPLICATION );
		comboBox.addItem( TKeyClavicomActionType.SWITCH_KEYBOARD_MOUSE );
		
		comboBox.addActionListener( this );
		
		JPanel panel = new JPanel();
		
		panel.add( new JLabel( UIString.getUIString("LB_KEYCLAVICOM_ACTION") ) );
		panel.add( comboBox );
		
		// Ajout du titre
		panel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_KEYCLAVICOM_BORDER")) );
		
		add( panel);
		
		
		
	}
	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyClavicom(CKeyClavicom myKeyClavicom )
	{
		// Appel au père
		setValuesKeyOneLevel(myKeyClavicom);
		
		keyClavicom = myKeyClavicom;
		
		if( (keyClavicom != null) )
		{
			if( keyClavicom.getAction() != null )
			{
				comboBox.setSelectedItem( TKeyClavicomActionType.getString( keyClavicom.getAction() ) );
			}
		}
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		// si une action a été séléctionnée
		Object object = comboBox.getSelectedItem();
		if( object != null )
		{
			if( object instanceof TKeyClavicomActionType )
			{
				TKeyClavicomActionType action = (TKeyClavicomActionType)object;
				
				// si l'action est différente de l'ancienne
				if( action != keyClavicom.getAction() )
				{
					keyClavicom.setAction( action );
				}
			} 
		}
		
	}



	//--------------------------------------------------- METHODES PRIVEES --//
}
