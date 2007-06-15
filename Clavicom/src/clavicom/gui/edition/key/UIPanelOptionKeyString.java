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
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.gui.language.UIString;


public class UIPanelOptionKeyString extends UIPanelOptionOneLevelKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	CKeyString keyString;
	JTextField textDisplay;
	JTextField textWrite;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyString( CKeyString myKeyString )
	{
		super( myKeyString );

		keyString = myKeyString;

		JPanel panel = new JPanel( new BorderLayout() );
		
		JPanel panelDisplay = new JPanel();
		
		JLabel ltextDisplay = new JLabel( UIString.getUIString("LB_KEYSTRING_TEXTDISPLAY") );
		textDisplay = new JTextField();
		textDisplay.setPreferredSize( new Dimension( 200,23 ) );
		textDisplay.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0)
			{
				
			}

			public void keyReleased(KeyEvent arg0)
			{
				keyString.setCaption( textDisplay.getText() );				
			}

			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		panelDisplay.add( ltextDisplay );
		panelDisplay.add( textDisplay );
		
		
		
		JPanel panelWrite = new JPanel();
		
		JLabel ltextWrite = new JLabel( UIString.getUIString("LB_KEYSTRING_TEXTWRITE") );
		textWrite = new JTextField();
		textWrite.setPreferredSize( new Dimension( 200,23 ) );
		textWrite.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0)
			{
				
			}

			public void keyReleased(KeyEvent arg0)
			{
				keyString.setBaseString( textWrite.getText() );				
			}

			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		panelWrite.add( ltextWrite );
		panelWrite.add( textWrite );
		
		
		panel.add( panelDisplay, BorderLayout.NORTH ); 
		panel.add( panelWrite ); 
		
		add( panel, BorderLayout.CENTER );
		
	}
	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
