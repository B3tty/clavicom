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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;


public class PanelOptionKeyLevel extends PanelOptionOneLevelKey implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyLevel keyLevel;
	JComboBox combo;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public PanelOptionKeyLevel( CKeyLevel myKeyLevel )
	{
		super( myKeyLevel );

		keyLevel = myKeyLevel;

		JPanel panel = new JPanel();
		
		combo = new JComboBox();
		combo.addItem( TLevelEnum.SHIFT );
		combo.addItem( TLevelEnum.ALT_GR );
		
		combo.addActionListener( this );
		
		combo.setSelectedItem( keyLevel.GetLevel() );
		
		panel.add( new JLabel( UIString.getUIString("LB_KEYLEVEL_LEVEL") ) );
		panel.add( combo );

		add( panel, BorderLayout.CENTER );
		
	}
	//----------------------------------------------------------- METHODES --//
	
	public void actionPerformed(ActionEvent arg0)
	{
		if( keyLevel != null )
		{
			Object object = combo.getSelectedItem();
			if( object != null )
			{
				if ( object instanceof TLevelEnum )
				{
					keyLevel.setLevel( (TLevelEnum)object );
				}
			}
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
