/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationKeyboardColor.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

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

package clavicom.gui.configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import clavicom.core.profil.CKeyboardColor;
import clavicom.gui.language.UIString;

public class PanelModificationProfilKeyboardColor extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKeyboardColor keyboardColor;
	JButton buttonBackColor;
	JButton buttonNormal;
	JButton buttonEnteredColor;
	JButton buttonClickedColor;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilKeyboardColor(CKeyboardColor myKeyboardColor)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR") );
		
		keyboardColor = myKeyboardColor;
		
		LoadComponents();
	}
	
	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		JPanel panel = new JPanel( new GridLayout(1,4) );
		
		
		buttonBackColor = new JButton();
		buttonBackColor.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), keyboardColor.getBackColor().getColor() );
				
				if( newColor != null )
				{
					if( newColor != keyboardColor.getBackColor().getColor() )
					{
						// la couleur à changé
						keyboardColor.getBackColor().setColor( newColor );

						buttonBackColor.setBackground( newColor );
					}
				}
			}
		});
		panel.add
		( 
			DisplayColor
			(
				buttonBackColor,
				keyboardColor.getBackColor().getColor(),
				UIString.getUIString( "LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_BACKCOLOR" )
			)
		);
		
		
		buttonNormal = new JButton();
		buttonNormal.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), keyboardColor.getDefaultKeyNormal().getColor() );
				
				if( newColor != null )
				{
					if( newColor != keyboardColor.getDefaultKeyNormal().getColor() )
					{
						// la couleur à changé
						keyboardColor.getDefaultKeyNormal().setColor( newColor );

						buttonNormal.setBackground( newColor );
					}
				}
			}
		});
		panel.add
		( 
				DisplayColor
				(
					buttonNormal,
					keyboardColor.getDefaultKeyNormal().getColor(),
					UIString.getUIString( "LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_NORMALCOLOR" )
				)
		);
		
		buttonEnteredColor = new JButton();
		buttonEnteredColor.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), keyboardColor.getDefaultKeyEntered().getColor() );
				
				if( newColor != null )
				{
					if( newColor != keyboardColor.getDefaultKeyEntered().getColor() )
					{
						// la couleur à changé
						keyboardColor.getDefaultKeyEntered().setColor( newColor );

						buttonEnteredColor.setBackground( newColor );
					}
				}
			}
		});
		panel.add
		(
				DisplayColor
				(
					buttonEnteredColor,
					keyboardColor.getDefaultKeyEntered().getColor(),
					UIString.getUIString( "LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_ENTEREDCOLOR" )
				)
		);
		
		buttonClickedColor = new JButton();
		buttonClickedColor.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), keyboardColor.getDefaultKeyClicked().getColor() );
				
				if( newColor != null )
				{
					if( newColor != keyboardColor.getDefaultKeyClicked().getColor() )
					{
						// la couleur à changé
						keyboardColor.getDefaultKeyClicked().setColor( newColor );

						buttonClickedColor.setBackground( newColor );
					}
				}
			}
		});
		panel.add
		(
				DisplayColor
				(
					buttonClickedColor,
					keyboardColor.getDefaultKeyClicked().getColor(),
					UIString.getUIString( "LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_PRESSEDCOLOR" )
				)
		);
		
		
		add( panel, BorderLayout.CENTER );
	}

	

	//----------------------------------------------------------- METHODES --//	
	
	@Override
	public int validateDataEntry()
	{
		// Si la keyboardColor a changé, on la met dans le profil
		int retour = 0;
		
		if( buttonBackColor.getBackground() != keyboardColor.getBackColor().getColor() )
		{
			keyboardColor.getBackColor().setColor( buttonBackColor.getBackground() );
			retour = 1;
		}
		if( buttonClickedColor.getBackground() != keyboardColor.getDefaultKeyClicked().getColor() )
		{
			keyboardColor.getDefaultKeyClicked().setColor( buttonClickedColor.getBackground() );
			retour = 1;
		}
		if( buttonEnteredColor.getBackground() != keyboardColor.getDefaultKeyEntered().getColor() )
		{
			keyboardColor.getDefaultKeyEntered().setColor( buttonEnteredColor.getBackground() );
			retour = 1;
		}
		if( buttonNormal.getBackground() != keyboardColor.getDefaultKeyNormal().getColor() )
		{
			keyboardColor.getDefaultKeyNormal().setColor( buttonNormal.getBackground() );
			retour = 1;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected JPanel DisplayColor( 
			JButton buttonColor,
			Color defautColor,
			String typeColor)
	{
		JPanel panel = new JPanel( new BorderLayout() );
		
		JPanel panel1 = new JPanel();
		panel1.add( new JLabel(typeColor) );
		panel.add( panel1, BorderLayout.NORTH );
		
		buttonColor.setBackground( defautColor );
		buttonColor.setPreferredSize( new Dimension( 30, 20 ) );
		JPanel panel2 = new JPanel();
		panel2.add( buttonColor );
		
		panel.add( panel2, BorderLayout.CENTER );
		

		return panel;
	}
}
