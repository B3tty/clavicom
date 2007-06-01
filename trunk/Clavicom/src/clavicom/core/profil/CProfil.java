/*-----------------------------------------------------------------------------+

			Filename			: Profil.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.profil

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

package clavicom.core.profil;

import java.io.File;
import java.io.FileOutputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;


public class CProfil
{
	//--------------------------------------------------------- CONSTANTES --//
	private static CProfil profil;
	
	//---------------------------------------------------------- VARIABLES --//
	
	CLangueUIName langueUI;			// Langue de l'interface utilisateur
	CCommandSetName commandSetName; // nom du fichier du commandSet
	CDictionnaryName dictionnaryName;	// nom du fichier de dictionnaire
	CKeyboardColor defaultColor;	// Couleur par défaut du clavicom
	CTransparency transparency;		// Transparence
	CSound sound;					// Gestion du son
	CNavigation navigation;			// Type de navigation de l'utilisateur
	CKeyboard keyboard;				// Structure du clavicom
	CPreferedWords preferedWords;	// liste des mots préférés de l'utilisateur
	CFont keyboardFont;				// Police de caractère utilisée pour le clavier
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	private CProfil( String profilFilePath ) throws Exception
	{
		// chargement du fichier de profil
		LoadProfilFile( profilFilePath );
	}

	//----------------------------------------------------------- METHODES --//
	public static void createInstance(String profilFilePath) throws Exception 
	{
		profil = new CProfil(profilFilePath);
	}
	
	public static CProfil getInstance() 
	{
		return profil;
	}
	
	public CCommandSetName getCommandSetName()
	{
		return commandSetName;
	}

	public CKeyboardColor getDefaultColor()
	{
		return defaultColor;
	}

	public CDictionnaryName getDictionnaryName()
	{
		return dictionnaryName;
	}

	public CKeyboard getKeyboard()
	{
		return keyboard;
	}

	public CLangueUIName getLangueUI()
	{
		return langueUI;
	}

	public CNavigation getNavigation()
	{
		return navigation;
	}

	public CPreferedWords getPreferedWords()
	{
		return preferedWords;
	}

	public CSound getSound()
	{
		return sound;
	}

	public CTransparency getTransparency()
	{
		return transparency;
	}
	
	public CFont getKeyboardFont()
	{
		return keyboardFont;
	}
	
	public void SaveProfil( String profilFilePath ) throws Exception
	{
		// ===============================================================
		// Création de la structure SAX
		// ===============================================================
		Element racine = new Element( TXMLNames.PR_ELEMENT_PROFIL );
		Document document = new Document( racine );		
		
		
		
		// ===============================================================
		// Attachement de la langueUI
		// ===============================================================
		racine.addContent( langueUI.buildNode() );
		
		// ===============================================================
		// Attachement du nom du fichier de commande set à utiliser
		// ===============================================================
		racine.addContent( commandSetName.buildNode() );

		// ===============================================================
		// Attachement du dictionnaire
		// ===============================================================
		racine.addContent( dictionnaryName.buildNode() );
		
		// ===============================================================
		// Attachement de la couleur par defaut
		// ===============================================================
		racine.addContent( defaultColor.buildNode() );
		
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
		// Attachement de la police
		// ===============================================================
		racine.addContent( keyboardFont.buildNode() );
		
		// ===============================================================
		// Attachement du clavier
		// ===============================================================
		try
		{
			racine.addContent( keyboard.BuildNode() );
		}
		catch(Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" ) + "]" + ex.getMessage() );
		}
		
		// ===============================================================
		// Attachement des mots préférés de l'utilisateur
		// ===============================================================
		racine.addContent( preferedWords.buildNode() );

		
		// ===============================================================
		// Sauvegarde de la structre SAX dans le fichier XML
		// ===============================================================
		XMLOutputter fileOut = new XMLOutputter( Format.getPrettyFormat() );
		fileOut.output( document, new FileOutputStream( profilFilePath ));
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	private void LoadProfilFile ( String profilFilePath ) throws Exception
	{
		// ======================================================================
		// chargement du fichier de profil
		// ======================================================================
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( profilFilePath ));
		}
		catch(Exception e)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_COMMANDSET_OPEN_FILE" ) + profilFilePath + "\n" + e.getMessage());
		}

		//On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		
		
		// ======================================================================
		// chargement de la langueUI
		// ======================================================================
		Element langueUI_elem = racine.getChild( TXMLNames.PR_ELEMENT_LANGUAGE_UI );
		if( langueUI_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_LANGUAGE_UI );
		}
		try
		{
			langueUI = new CLangueUIName( langueUI_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
		
		// ======================================================================
		// chargement du nom du fichier de commande set à utiliser
		// ======================================================================
		Element commandSet_elem = racine.getChild( TXMLNames.PR_ELEMENT_COMMANDSET_NAME );
		if( commandSet_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_COMMANDSET_NAME );
		}
		try
		{
			commandSetName = new CCommandSetName( commandSet_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}

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
			dictionnaryName = new CDictionnaryName( dictionary_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
		
		// ======================================================================
		// chargement de la couleur par defaut
		// ======================================================================
		Element default_color_elem = racine.getChild( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR );
		if( default_color_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR );
		}
		try
		{
			defaultColor = new CKeyboardColor( default_color_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
		
		// ======================================================================
		// chargement de la police
		// ======================================================================
		Element font_eleme = racine.getChild( TXMLNames.PR_ELEMENT_FONT );
		if( font_eleme == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_FONT );
		}
		try
		{
			keyboardFont = new CFont( font_eleme );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
		
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
		
		// ======================================================================
		// chargement du clavier
		// ======================================================================
		Element keyboard_elem = racine.getChild( TXMLNames.PR_ELEMENT_KEYBOARD );
		if( keyboard_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_KEYBOARD );
		}
		try
		{
			keyboard = new CKeyboard( keyboard_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}

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

}
