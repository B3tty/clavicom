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

import clavicom.core.profil.CDictionaryName;
import clavicom.core.profil.CLangueUIName;
import clavicom.core.profil.CNavigation;
import clavicom.core.profil.CPreferedWords;
import clavicom.core.profil.CSound;
import clavicom.core.profil.CTransparency;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;
import clavicom.tools.XMLTools;

public class CSettings
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//
	static CLangueUIName langueUI;			// Langue de l'interface utilisateur

	static CDictionaryName dictionnaryName;	// nom du fichier de dictionnaire
	static CTransparency transparency;		// Transparence
	static CSound sound;					// Gestion du son
	static CNavigation navigation;			// Type de navigation de l'utilisateur
	static CPreferedWords preferedWords;	// liste des mots préférés de l'utilisateur
	
	static String lastProfilePath;
	static String defaultProfileName;
	
	static String settingFilePath;

	//------------------------------------------------------ CONSTRUCTEURS --//

	//----------------------------------------------------------- METHODES --//
	public static void loadSettings( String configFilePath ) throws Exception
	{
		settingFilePath = configFilePath;
		
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
		
		loadDictionnary(racine);
		loadTransparency( racine );
		loadSound( racine );
		loadNavigation( racine );
		loadPreferedWord( racine );
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
		
		// ===============================================================
		// Attachement de la langueUI
		// ===============================================================
		racine.addContent( langueUI.buildNode() );
		
		// ===============================================================
		// Attachement du dictionnaire
		// ===============================================================
		racine.addContent( dictionnaryName.buildNode() );
		
		// ===============================================================
		// Attachement de la transparence
		// ===============================================================
		racine.addContent( transparency.buildNode() );
		
		// ===============================================================
		// Attachement de la gestion du son
		// ===============================================================
		racine.addContent( sound.buildNode() );
		
		// ===============================================================
		// Attachement de la navigation
		// ===============================================================
		racine.addContent( navigation.buildNode() );
		
		// ===============================================================
		// Attachement des mots préférés de l'utilisateur
		// ===============================================================
		racine.addContent( preferedWords.buildNode() );
		
		
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
	
	public static void loadProfileLanguageUIName() throws Exception
	{
		
		Element racine = XMLTools.openFile( settingFilePath );
		
		// ======================================================================
		// chargement de la langueUI
		// ======================================================================
		Element langueUI_elem = racine.getChild( TXMLNames.PR_ELEMENT_LANGUAGE_UI );
		try
		{
			langueUI = new CLangueUIName( langueUI_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}
	
	public static void loadDictionnary (Element racine ) throws Exception
	{
		// ======================================================================
		// chargement du dictionnaire
		// ======================================================================
		Element dictionary_elem = racine.getChild( TXMLNames.PR_ELEMENT_DICTIONARY_NAME );
		if( dictionary_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_DICTIONARY_NAME );
		}
		try
		{
			dictionnaryName = new CDictionaryName( dictionary_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}
	
	public static void loadTransparency (Element racine ) throws Exception
	{
		// ======================================================================
		// chargement de la transparence
		// ======================================================================
		Element transparency_elem = racine.getChild( TXMLNames.PR_ELEMENT_TRANSPARENCY );
		if( transparency_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_TRANSPARENCY );
		}
		try
		{
			transparency = new CTransparency( transparency_elem );
		}
		catch(Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" ) + "]"  + ex.getMessage() );
		}
	}
	
	public static void loadSound (Element racine ) throws Exception
	{
		// ======================================================================
		// chargement de la gestion du son
		// ======================================================================
		Element sound_elem = racine.getChild( TXMLNames.PR_ELEMENT_SOUND );
		if( sound_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_SOUND );
		}
		try
		{
			sound = new CSound( sound_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}
	
	public static void loadNavigation (Element racine ) throws Exception
	{
		// ======================================================================
		// chargement de la navigation
		// ======================================================================
		Element navigation_elem = racine.getChild( TXMLNames.PR_ELEMENT_NAVIGATION );
		if( navigation_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_NAVIGATION );
		}
		try
		{
			navigation = new CNavigation( navigation_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}
	
	public static void loadPreferedWord (Element racine ) throws Exception
	{
		// ======================================================================
		// chargement des mots préférés de l'utilisateur
		// ======================================================================
		Element preferedWords_elem = racine.getChild( TXMLNames.PR_ELEMENT_PREFERED_WORDS );
		if( preferedWords_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_PREFERED_WORDS );
		}
		try
		{
			preferedWords = new CPreferedWords( preferedWords_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}

	public static CLangueUIName getLangueUI()
	{
		return langueUI;
	}

	public static void setLangueUI(CLangueUIName langueUI)
	{
		CSettings.langueUI = langueUI;
	}

	public static CDictionaryName getDictionnaryName()
	{
		return dictionnaryName;
	}

	public static void setDictionnaryName(CDictionaryName dictionnaryName)
	{
		CSettings.dictionnaryName = dictionnaryName;
	}

	public static CTransparency getTransparency()
	{
		return transparency;
	}

	public static void setTransparency(CTransparency transparency)
	{
		CSettings.transparency = transparency;
	}

	public static CSound getSound()
	{
		return sound;
	}

	public static void setSound(CSound sound)
	{
		CSettings.sound = sound;
	}

	public static CNavigation getNavigation()
	{
		return navigation;
	}

	public static void setNavigation(CNavigation navigation)
	{
		CSettings.navigation = navigation;
	}

	public static CPreferedWords getPreferedWords()
	{
		return preferedWords;
	}

	public static void setPreferedWords(CPreferedWords preferedWords)
	{
		CSettings.preferedWords = preferedWords;
	}
		

	//--------------------------------------------------- METHODES PRIVEES --//
}
