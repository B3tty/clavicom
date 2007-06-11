/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationDictionaryName.java
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
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clavicom.CFilePaths;
import clavicom.core.profil.CDictionaryName;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.tools.CFile;

public class PanelModificationProfilDictionaryName extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CDictionaryName dictionaryName;
	
	JComboBox combo;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilDictionaryName(CDictionaryName myDictionaryName)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_DICTIONARY") );
		
		dictionaryName = myDictionaryName;
		
		LoadComponents();
	}
	
	private void LoadComponents()
	{
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.add(new JLabel(UIString
				.getUIString("LB_CONFPROFIL_PANNEL_DICTIONARY")));
		add(panel, BorderLayout.NORTH);

		combo = new JComboBox();

		// chargement de la liste des fichiers de commandSet
		File commandSetDirectory = new File( CFilePaths.getDictionaries() );

		if (commandSetDirectory.isDirectory())
		{
			File[] list = commandSetDirectory.listFiles();
			if (list != null)
			{
				CProfil profil = CProfil.getInstance();
				for (int i = 0; i < list.length; i++)
				{
					// on ne met pas le .svn
					if( ! list[i].getName().equals( ".svn" ) )
					{
						CFile dictionnaryFile = new CFile( list[i].getAbsolutePath() );
						combo.addItem( dictionnaryFile );
						
						// si le nom du dictionaire est le même que celui en train d'être chargé, on le séléctione
						if( dictionnaryFile.toString().equals( 
								profil.getDictionnaryName().getDictionaryName().substring
									(0, profil.getDictionnaryName().getDictionaryName().length()-4) ) )
						{
							combo.setSelectedItem( dictionnaryFile );
						}
					}
				}
			}
		}

		
		add( combo, BorderLayout.CENTER );
	}

	

	//----------------------------------------------------------- METHODES --//
	
	@Override
	public int validateDataEntry()
	{
		// Si le dictionnaire a été changé, on change son nom
		
		return 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
