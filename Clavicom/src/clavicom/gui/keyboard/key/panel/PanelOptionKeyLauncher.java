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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyClavicomActionType;


public class PanelOptionKeyLauncher extends PanelOptionOneLevelKey implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyLauncher keyLauncher;
	JTextField textField ;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public PanelOptionKeyLauncher( CKeyLauncher myKeyLauncher )
	{
		super( myKeyLauncher );
		
		keyLauncher = myKeyLauncher;
		
		JPanel panel = new JPanel();
		panel.add( new JLabel( UIString.getUIString("LB_KEYLAUNCHER_APPLICATION") ) );

		if( keyLauncher != null )
		{
			textField = new JTextField( keyLauncher.getApplicationPath() );
		}
		else
		{
			textField = new JTextField( );
		}
		
		textField.setPreferredSize( new Dimension( 330, 23 ) );
		
		textField.addActionListener( this );
		
		panel.add( textField );
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser chooser = new JFileChooser();
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					textField.setText( chooser.getSelectedFile().getAbsolutePath() );
					
					keyLauncher.setApplicationPath( chooser.getSelectedFile().getAbsolutePath() );
				} 
			}
		});
		button.setPreferredSize( new Dimension( 20 , 22 ) );
		panel.add( button );
		
	
		add( panel, BorderLayout.CENTER );
		
	}
	//----------------------------------------------------------- METHODES --//	

	public void actionPerformed(ActionEvent arg0)
	{
		int i = 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
