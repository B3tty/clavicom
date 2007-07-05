/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationLangueUIName.java
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
import clavicom.core.profil.CLangueUIName;
import clavicom.gui.language.UIString;
import clavicom.tools.CFile;

public class PanelModificationProfilLangueUIName extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CLangueUIName langueUIName;
	JComboBox combo;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public PanelModificationProfilLangueUIName(CLangueUIName myLangueUIName)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_LANGUAGE") );
		
		langueUIName = myLangueUIName;
		
		LoadComponents();
		
		initValues();
	}
	
	private void LoadComponents()
	{
		setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		panel1.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_LANGUAGE_CHOOSE") ) );
		add( panel1, BorderLayout.NORTH );
		
		combo = new JComboBox();

		// chargement de la liste des fichiers de langue UI
		File langueUIDirectory = new File( CFilePaths.getLanguagesUIFolder() );

		if (langueUIDirectory.isDirectory())
		{
			File[] list = langueUIDirectory.listFiles();
			if (list != null)
			{
				for (int i = 0; i < list.length; i++)
				{
					// on ne met pas le .svn
					if( ! list[i].getName().equals( ".svn" ) )
					{
						CFile languageUIFile = new CFile( list[i].getAbsolutePath() );
						combo.addItem( languageUIFile );
					}
				}
			}
		}
		
		add( combo, BorderLayout.CENTER );
		
	}
	
	public void initValues()
	{
		File langueUIDirectory = new File( CFilePaths.getLanguagesUIFolder() );

		if (langueUIDirectory.isDirectory())
		{
			File[] list = langueUIDirectory.listFiles();
			if (list != null)
			{
				for (int i = 0; i < list.length; i++)
				{
					// on ne met pas le .svn
					if( ! list[i].getName().equals( ".svn" ) )
					{
						CFile languageUIFile = new CFile( list[i].getAbsolutePath() );
						
						// si le nom du languageUI est le même que celui en train d'être chargé, on le séléctione
						if( languageUIFile.toString().equals( 
								langueUIName.getLanguageFileName() ) )
						{
							combo.setSelectedItem( languageUIFile );
						}
					}
				}
			}
		}
	}

	

	//----------------------------------------------------------- METHODES --//
	
	@Override
	public boolean validateDataEntry()
	{
		return change( true );
	}

	@Override
	public boolean isChanged()
	{
		return change( false );
	}
	
	protected boolean change( boolean saveData )
	{
		// Si la langue de l'UI à changé, on la change le nom du fichier UI
		if( combo.getSelectedItem() != null )
		{
			Object object = combo.getSelectedItem();
			if( object instanceof CFile )
			{
				CFile langueUIFile = (CFile)object;
				if( langueUIFile != null )
				{
					// s'il à changé
					if( ! langueUIFile.toString().equals( 
							langueUIName.getLanguageFileName() ) )
					{
						if( saveData )
						{
							langueUIName.setLanguageFileName( langueUIFile.getName() );
						}
						
						return true;
					}
				}
			}
		}
		
		return false;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
