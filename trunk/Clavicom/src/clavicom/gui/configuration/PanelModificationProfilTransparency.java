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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import clavicom.core.profil.CTransparency;
import clavicom.gui.language.UIString;

public class PanelModificationProfilTransparency extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CTransparency transparency;
	JSlider keyboardTransparency;
	JSlider keysTransparency;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilTransparency(CTransparency myTransparency)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_TRANSPARENCY") );
		
		transparency = myTransparency;
		
		LoadComponents();
	}
	
	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		JPanel panelGlobal = new JPanel( new BorderLayout() );
		
		JPanel keyboardT = new JPanel(new BorderLayout());
		
		JPanel p2 = new JPanel();
		p2.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_TRANSPARENCY_KEYBOARD") ));
		keyboardT.add(p2, BorderLayout.NORTH );
		
		keyboardTransparency = new JSlider();
		keyboardTransparency.setMaximum( 100 );
		keyboardTransparency.setMinimum( 0 );
		keyboardTransparency.setMajorTickSpacing(10);
		keyboardTransparency.setMinorTickSpacing(10);
		keyboardTransparency.setPaintTicks(true);
		keyboardTransparency.setPaintLabels(true);
		keyboardTransparency.setValue( transparency.getKeyboardTransparencyPourcent() );
		keyboardT.add(keyboardTransparency, BorderLayout.CENTER);
		panelGlobal.add(keyboardT, BorderLayout.NORTH);
		
		JPanel keysT = new JPanel(new BorderLayout());
		
		JPanel p3 = new JPanel();
		p3.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_TRANSPARENCY_KEYS") ));
		keysT.add(p3, BorderLayout.NORTH );
		
		keysTransparency = new JSlider();
		keysTransparency.setMaximum( 100 );
		keysTransparency.setMinimum( 0 );
		keysTransparency.setMajorTickSpacing(10);
		keysTransparency.setMinorTickSpacing(10);
		keysTransparency.setPaintTicks(true);
		keysTransparency.setPaintLabels(true);
		keysTransparency.setValue( transparency.getKeyTransparencyPourcent() );
		keysT.add(keysTransparency, BorderLayout.CENTER);
		panelGlobal.add(keysT, BorderLayout.SOUTH);
		
		add( panelGlobal, BorderLayout.CENTER );
	}

	

	//----------------------------------------------------------- METHODES --//	
	
	@Override
	public int validateDataEntry()
	{
		// Si la gestion de la transparence a changé, on la change dans le profil
		int retour = 0;
		if( keyboardTransparency.getValue() != transparency.getKeyboardTransparencyPourcent() )
		{
			transparency.setKeyboardTrancparencyPourcent( keyboardTransparency.getValue() );
			retour = 1;
		}
		if( keysTransparency.getValue() != transparency.getKeyTransparencyPourcent() );
		{
			transparency.setKeyTransparencyPourcent( keysTransparency.getValue() );
			retour = 1;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
