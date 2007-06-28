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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
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
	JTextField textWrite;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionKeyString( )
	{
		super( );

		JPanel panel = new JPanel( new BorderLayout() );
		
		JPanel panelWrite = new JPanel();
		
		JLabel ltextWrite = new JLabel( UIString.getUIString("LB_KEYSTRING_TEXTWRITE") );
		textWrite = new JTextField();
		textWrite.setPreferredSize( new Dimension( 200,23 ) );
		textWrite.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent arg0)
			{
				// Rien à faire
			}

			public void keyReleased(KeyEvent arg0)
			{
				updateBaseString( textWrite.getText() );				
			}

			public void keyTyped(KeyEvent arg0)
			{
				// Rien à faire
			}
		});
		
		panelWrite.add( ltextWrite );
		panelWrite.add( textWrite );
		panel.add( panelWrite ); 
		
		// Ajout du titre
		panel.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_KEYSTRING_BORDER")) );
		
		add( panel);
		
	}
	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyString( CKeyString myKeyString )
	{
		// Appel au père
		setValuesKeyOneLevel(myKeyString);
		
		keyString = myKeyString;
		
		textWrite.setText( keyString.getBaseString() );
	}
	//--------------------------------------------------- METHODES PRIVEES --//
	protected void updateCaption(String caption)
	{
		if (keyString != null)
			keyString.setCaption( caption  );
	}
	
	protected void updateBaseString(String baseString)
	{
		if (keyString != null)
			keyString.setBaseString(baseString);
	}
}
