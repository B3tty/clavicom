/*-----------------------------------------------------------------------------+

 Filename			: PanelModificationCommandSetName.java
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
import clavicom.core.profil.CCommandSetName;
import clavicom.gui.language.UIString;
import clavicom.tools.CFile;

public class PanelModificationProfilCommandSetName extends
		PanelModificationProfil
{

	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	CCommandSetName commandSetName;
	JComboBox combo;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilCommandSetName(
			CCommandSetName myCommandSetName)
	{
		super(UIString.getUIString("LB_CONFPROFIL_PANNEL_COMMANDSET"));

		commandSetName = myCommandSetName;

		LoadComponents();
	}

	private void LoadComponents()
	{
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.add(new JLabel(UIString
				.getUIString("LB_CONFPROFIL_PANNEL_COMMANDSET")));
		add(panel, BorderLayout.NORTH);

		combo = new JComboBox();

		// chargement de la liste des fichiers de commandSet
		File commandSetDirectory = new File( CFilePaths.getCommandSetsFolder() );

		if (commandSetDirectory.isDirectory())
		{
			File[] list = commandSetDirectory.listFiles();
			if (list != null)
			{
				for (int i = 0; i < list.length; i++)
				{
					// on ne met pas le .svn
					if( ! list[i].getName().equals( ".svn" ) )
					{
						CFile commandSetFile = new CFile( list[i].getAbsolutePath() );
						combo.addItem( commandSetFile );
						
						// si le nom du commandSet est le même que celui en train d'être chargé, on le séléctione
						if( commandSetFile.toString().equals( 
								commandSetName.getcommandSetName() ) )
						{
							combo.setSelectedItem( commandSetFile );
						}
					}
				}
			}
		}
		
		add( combo, BorderLayout.CENTER );
	}

	// ----------------------------------------------------------- METHODES --//

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
		if( combo.getSelectedItem() != null )
		{
			Object object = combo.getSelectedItem();
			if( object instanceof CFile )
			{
				CFile commandSetFile = (CFile)object;
				if( commandSetFile != null )
				{
					// s'il à changé
					if( ! commandSetFile.toString().equals( 
							commandSetName.getcommandSetName() ) )
					{
						if ( saveData )
							commandSetName.setcommandSetName( commandSetFile.getName() );
						
						return true;
					}
				}
			}
		}

		return false;
	}

	// --------------------------------------------------- METHODES PRIVEES --//
}