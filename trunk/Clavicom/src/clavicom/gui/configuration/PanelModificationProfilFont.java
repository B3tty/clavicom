/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationFont.java
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
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import clavicom.core.profil.CFont;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class PanelModificationProfilFont extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CFont font;
	JList list;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilFont(CFont myFont)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT") );
		
		font = myFont;
		
		LoadComponents();
	}
	
	private void LoadComponents()
	{
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.add(new JLabel(UIString
				.getUIString("LB_CONFPROFIL_PANNEL_FONT")));
		add(panel, BorderLayout.NORTH);
		
		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String [] polices = ge.getAvailableFontFamilyNames();
	    CFont[] cfonts = new CFont[ polices.length ];
	    CFont cFont;
	    Font font;
	    CFont selectedCFont = null;
	    CProfil profil = CProfil.getInstance();
	    
	    for ( int i = 0 ; i < polices.length ; i++ )
	    {
	    	font = new Font( polices[i], Font.PLAIN, 12 );
	    	cFont = new CFont( font );
	    	
	    	cfonts[ i ] = cFont;
	    	
	    	// on recherche la cFont séléctionné
	    	if( cFont.toString().equals( profil.getKeyboardFont().toString() ) )
	    	{
	    		selectedCFont = cFont;
	    	}
	    }
	    
	    list = new JList( cfonts );
	    
	    JScrollPane sc = new JScrollPane( list );
	    
	    add( sc, BorderLayout.CENTER  );
	    
	    
	    
	    // séléction de la police du profil
	    if( selectedCFont != null )
	    {
	    	list.setSelectedValue(selectedCFont, true);
	    }
	}

	

	//----------------------------------------------------------- METHODES --//
	
	@Override
	public int validateDataEntry()
	{
		// si la police a changé, on la met dans le font
		
		return 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
