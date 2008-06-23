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
import java.io.FileOutputStream;
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
	static String lastProfilePath;
	static String defaultProfileName;
	static String defaultLanguageFileName;

	//------------------------------------------------------ CONSTRUCTEURS --//

	//----------------------------------------------------------- METHODES --//
	public static void loadSettings( String configFilePath ) throws Exception
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
		
		// récupération de l'element lastProfilePath
		Element lastProfilePath_elem = racine.getChild( TXMLNames.SE_ELEMENT_LAST_PROFILE );
		
		if( lastProfilePath_elem == null  )
		{
			throw new Exception("[" + UIString.getUIString( "EX_SETTINGS" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ));
		}
		
		lastProfilePath = lastProfilePath_elem.getText();
		
		// récupération de l'element defaultProfilePath
		Element defaultProfileName_elem = racine.getChild( TXMLNames.SE_ELEMENT_DEFAULT_PROFILE );
		
		if( defaultProfileName_elem == null  )
		{
			throw new Exception("[" + UIString.getUIString( "EX_SETTINGS" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ));
		}
		
		defaultProfileName = defaultProfileName_elem.getText();
		
		// Récupération du fichier de langue par défaut
		Element defaultLanguageFileName_elem = racine.getChild( TXMLNames.SE_ELEMENT_DEFAULT_LANGUAGE_UI );
		
		if( defaultLanguageFileName_elem != null  )
		{
			defaultLanguageFileName = defaultLanguageFileName_elem.getText();
		}
	}
	
	public static void saveSettings( String configFilePath ) throws Exception
	{
		Element racine = new Element( TXMLNames.SE_ELEMENT_SETTINGS );
		
		// Enregistrement du lastProfile
		Element lastProfilePath_elem = new Element( TXMLNames.SE_ELEMENT_LAST_PROFILE );
		lastProfilePath_elem.setText( lastProfilePath );
		racine.addContent( lastProfilePath_elem );
		
		// Enregistrement du defaultProfile
		Element defaultProfileName_elem = new Element( TXMLNames.SE_ELEMENT_DEFAULT_PROFILE );
		defaultProfileName_elem.setText( defaultProfileName );
		racine.addContent( defaultProfileName_elem );
		
		// Enregistrement de la langue par défaut
		Element defaultLanguageFileName_elem = new Element( TXMLNames.SE_ELEMENT_DEFAULT_LANGUAGE_UI );
		defaultLanguageFileName_elem.setText( defaultLanguageFileName );
		racine.addContent( defaultLanguageFileName_elem );
		
		// Sauvegarde
		
		org.jdom.Document documentOut = new org.jdom.Document(racine);

		XMLOutputter sortie = new XMLOutputter( Format.getPrettyFormat() );
		
		sortie.output( documentOut,	new FileOutputStream( configFilePath ) );
		
	}

	public static String getLastProfilePath()
	{
		return lastProfilePath;
	}

	public static void setLastProfilePath(String lastofilePath)
	{
		CSettings.lastProfilePath = lastofilePath;
	}

	public static String getDefaultProfileName()
	{
		return defaultProfileName;
	}

	public static void setDefaultProfileName(String defaultProfileName)
	{
		CSettings.defaultProfileName = defaultProfileName;
	}

	public static String getDefaultLanguageFileName()
	{
		return defaultLanguageFileName;
	}

	public static void setDefaultLanguageFileName(String defaultLanguageFileName)
	{
		CSettings.defaultLanguageFileName = defaultLanguageFileName;
	}
		

	//--------------------------------------------------- METHODES PRIVEES --//
}
