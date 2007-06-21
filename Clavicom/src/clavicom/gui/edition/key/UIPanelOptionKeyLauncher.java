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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.gui.language.UIString;


public class UIPanelOptionKeyLauncher extends UIPanelOptionOneLevelKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyLauncher keyLauncher;
	JTextField textFieldPath ;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyLauncher( )
	{
		super( );
		
		
		JPanel panelGlobal = new JPanel( new BorderLayout() );
		
		
		JPanel panelDisplay = new JPanel();
		
		panelDisplay.add( new JLabel( UIString.getUIString("LB_KEYSTRING_TEXTDISPLAY") ) );

		
		panelGlobal.add( panelDisplay, BorderLayout.NORTH );
		
		
		
		
		JPanel panel = new JPanel();
		panel.add( new JLabel( UIString.getUIString("LB_KEYLAUNCHER_APPLICATION") ) );


		textFieldPath = new JTextField( );
		
		textFieldPath.setPreferredSize( new Dimension( 330, 23 ) );
		textFieldPath.setEditable( false );
		
		panel.add( textFieldPath );
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser chooser = new JFileChooser();
				
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					textFieldPath.setText( chooser.getSelectedFile().getAbsolutePath() );
					
					keyLauncher.setApplicationPath( chooser.getSelectedFile().getAbsolutePath() );
				} 
			}
		});
		button.setPreferredSize( new Dimension( 20 , 22 ) );
		panel.add( button );
		
	
		panelGlobal.add( panel, BorderLayout.CENTER );
		
		// Ajout du titre
		panelGlobal.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_KEYLAUNCHER_BORDER")) );
		
		add( panelGlobal);
	}
	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyLauncher( CKeyLauncher myKeyLauncher)
	{
		// Appel au père
		setValuesKeyOneLevel(myKeyLauncher);
		
		keyLauncher = myKeyLauncher;
		
		if( keyLauncher != null )
		{
			textFieldPath.setText(keyLauncher.getApplicationPath() );
		}
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
