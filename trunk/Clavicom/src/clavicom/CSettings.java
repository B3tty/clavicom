/*-----------------------------------------------------------------------------+

			Filename			: Settings.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom

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

package clavicom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CSettings
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	static String defaultProfilsPath;

	//------------------------------------------------------ CONSTRUCTEURS --//

	public static String getDefaultProfilsPath()
	{
		return defaultProfilsPath;
	}
	
	public static String getCommandSetsPath()
	{
		return "";
	}

	//----------------------------------------------------------- METHODES --//
	public static void LoadSettings( String configFilePath ) throws Exception
	{
		
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( configFilePath ));
		}
		catch(Exception e)
		{
			throw new Exception("[" + UIString.getUIString( "EX_SETTINGS" )+ "] : " + UIString.getUIString( "EX_SETTINGS_OPEN_FILE" ) + configFilePath + "\n" + e.getMessage());
		}

		//On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		
		// récupération de l'element defaultProfilsPath
		Element defaultProfilsPath_elem = racine.getChild( TXMLNames.SE_ELEMENT_DEFAULT_PROFIL );
		
		if( defaultProfilsPath_elem == null  )
		{
			throw new Exception("[" + UIString.getUIString( "EX_SETTINGS" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ));
		}
		
		defaultProfilsPath = defaultProfilsPath_elem.getText();
	}
	
	public static void SaveSettings( String configFilePath ) throws Exception
	{
		Element racine = new Element( TXMLNames.SE_ELEMENT_SETTINGS );
		
		Element defaultProfilsPath_elem = new Element( TXMLNames.SE_ELEMENT_DEFAULT_PROFIL );
		defaultProfilsPath_elem.setText( defaultProfilsPath );
		racine.addContent( defaultProfilsPath_elem );
		
		org.jdom.Document documentOut = new org.jdom.Document(racine);

		XMLOutputter sortie = new XMLOutputter( Format.getPrettyFormat() );
		
		sortie.output( documentOut,	new FileOutputStream( configFilePath ) );
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
