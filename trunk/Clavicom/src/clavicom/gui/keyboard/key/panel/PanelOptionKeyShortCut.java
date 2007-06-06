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

package clavicom.gui.keyboard.key.panel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.gui.language.UIString;

public class PanelOptionKeyShortCut extends PanelOptionOneLevelKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyShortcut keyShortCut;
	JList list;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public PanelOptionKeyShortCut( CKeyShortcut myKeyShortCut, CShortcutSet shortCutSet )
	{
		super( myKeyShortCut );

		keyShortCut = myKeyShortCut;

		JPanel panel = new JPanel(  );
		JPanel panel2 = new JPanel( new BorderLayout() );
		
		panel2.add( new JLabel( UIString.getUIString("LB_KEYSHORCUT_SHORTCUT")), BorderLayout.NORTH );
		
		list = new JList( shortCutSet.getValues().toArray() );
		
		if( keyShortCut != null )
		{
			if( keyShortCut.getCommand() != null )
			{
				list.setSelectedValue( keyShortCut.getCommand(), true);
			}
		}
		
		list.addListSelectionListener( new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent arg0)
			{
				if( keyShortCut != null )
				{
					Object object = list.getSelectedValue();
					if( object instanceof CCommand )
					{
						keyShortCut.setCommand( (CCommand) object);
					}
				}
			}
		});

		JScrollPane sp = new JScrollPane( list );
		
		panel2.add( sp, BorderLayout.CENTER );
		panel.add( panel2 );
		add( panel, BorderLayout.CENTER );
		
	}
	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
