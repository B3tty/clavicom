/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationTransparency.java
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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import clavicom.core.profil.CTransparency;
import clavicom.gui.language.UIString;
import clavicom.tools.OSTypeEnum;

public class PanelModificationProfilTransparency extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CTransparency transparency;
	JSlider keyboardTransparency;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilTransparency(CTransparency myTransparency)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANEL_TRANSPARENCY") );

		transparency = myTransparency;
		
		LoadComponents();
		
		initValues();
	}
	
	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		JPanel panelGlobal = new JPanel( new BorderLayout() );
		
		JPanel keyboardT = new JPanel(new BorderLayout());
		
		JPanel p2 = new JPanel();
		p2.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANEL_TRANSPARENCY_KEYBOARD") ));
		keyboardT.add(p2, BorderLayout.NORTH );
		
		keyboardTransparency = new JSlider();
		keyboardTransparency.setMaximum( 100 );
		keyboardTransparency.setMinimum( 0 );
		keyboardTransparency.setMajorTickSpacing(10);
		keyboardTransparency.setMinorTickSpacing(10);
		keyboardTransparency.setPaintTicks(true);
		keyboardTransparency.setPaintLabels(true);
		
		keyboardT.add(keyboardTransparency, BorderLayout.CENTER);
		panelGlobal.add(keyboardT, BorderLayout.NORTH);
		
		// ========================================================================
		// Check OS type
		// ========================================================================
		JLabel lb_OSREstriction = new JLabel( );
		if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.WINDOWS )
		{
			// nothing to do
		} else if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.LINUX )
		{
			lb_OSREstriction.setText( OSTypeEnum.getMessageOSREstriction() + " Linux" );
			lb_OSREstriction.setForeground(Color.red);
			
			keyboardTransparency.setEnabled( false );
			
		} else if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.MAC )
		{
			lb_OSREstriction.setText( OSTypeEnum.getMessageOSREstriction() + " Mac" );
			lb_OSREstriction.setForeground(Color.red);
			
			keyboardTransparency.setEnabled( false );
			
		} else
		{
			lb_OSREstriction.setText( OSTypeEnum.getMessageOSREstriction() );
			lb_OSREstriction.setForeground(Color.red);
			
			keyboardTransparency.setEnabled( false );
		}
		panelGlobal.add(lb_OSREstriction, BorderLayout.SOUTH);
		
		add( panelGlobal, BorderLayout.CENTER );
	}

	

	//----------------------------------------------------------- METHODES --//	

	public void initValues()
	{
		keyboardTransparency.setValue( (int)(( 1 - transparency.getKeyboardTransparency())*100) );
	}
	
	@Override
	public boolean validateDataEntry()
	{
		return change(true);
	}
	
	@Override
	public boolean isChanged()
	{
		return change(false);
	}
	
	protected boolean change( boolean saveData )
	{
		// Si la gestion de la transparence a changé, on la change dans le profil
		boolean retour = false;
		if( (1-((float)keyboardTransparency.getValue()/100)) != transparency.getKeyboardTransparency() )
		{
			if( saveData )
			{
				transparency.setKeyboardTrancparency( 1-((float)keyboardTransparency.getValue()/100) );
			}
			
			retour = true;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
