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

import java.io.FileOutputStream;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;
import clavicom.tools.XMLTools;


public class CProfil
{
	//--------------------------------------------------------- CONSTANTES --//
	private static CProfil profil;
	
	//---------------------------------------------------------- VARIABLES --//
	
	
	CCommandSetName commandSetName; // nom du fichier du commandSet
	CShortCutSetName shortcutSetName; // nom du fichier du shortcutSet
	CKeyboardColor defaultColor;	// Couleur par défaut du clavicom
	
	CKeyboard keyboard;				// Structure du clavicom
	CFont keyboardFont;				// Police de caractère utilisée pour le clavier
	CAdvancedOptions advancedOption;// option avancés du profil
	
	String profilFilePath;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	private CProfil( String myProfilFilePath )
	{
		profilFilePath = myProfilFilePath;
	}

	//----------------------------------------------------------- METHODES --//
	
	public void loadProfileCommandSetName() throws Exception
	{
		Element racine = XMLTools.openFile( profilFilePath );
		
		// ======================================================================
		// chargement du nom du fichier de commande set à utiliser
		// ======================================================================
		Element commandSet_elem = racine.getChild( TXMLNames.PR_ELEMENT_COMMANDSET_NAME );
		if( commandSet_elem == null )
		{
			throw new Exception("[Chargement du profil] : Impossible de trouver le noeud XML" + TXMLNames.PR_ELEMENT_COMMANDSET_NAME );
		}
		try
		{
			commandSetName = new CCommandSetName( commandSet_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[Chargement du profil]"  + ex.getMessage() );
		}
	}
	public void loadProfileShortCutName() throws Exception
	{
		Element racine = XMLTools.openFile( profilFilePath );
		
		// ======================================================================
		// chargement du nom du fichier de commande set à utiliser
		// ======================================================================
		Element shortCut_elem = racine.getChild( TXMLNames.PR_ELEMENT_SHORTCUTSET_NAME );
		if( shortCut_elem == null )
		{
			throw new Exception("[Chargement du profil] : Impossible de trouver le noeud XML " + TXMLNames.PR_ELEMENT_SHORTCUTSET_NAME );
		}
		try
		{
			shortcutSetName = new CShortCutSetName( shortCut_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[Chargement du profil]"  + ex.getMessage() );
		}
	}
	
	public static void createInstance(String profilFilePath)
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
	
	public CFont getKeyboardFont()
	{
		return keyboardFont;
	}
	
	public CShortCutSetName getShortcutSetName()
	{
		return shortcutSetName;
	}

	public void setShortcutSetName(CShortCutSetName shortcutSetName)
	{
		this.shortcutSetName = shortcutSetName;
	}

	public void saveProfil() throws Exception
	{
		saveProfilAs(profilFilePath);
	}
	
	public void saveProfilAs( 
			String profilFilePath) throws Exception
	{
		// ===============================================================
		// Création de la structure SAX
		// ===============================================================
		Element racine = new Element( TXMLNames.PR_ELEMENT_PROFIL );
		Document document = new Document( racine );
		
		// ===============================================================
		// Attachement du nom du fichier de commande set à utiliser
		// ===============================================================
		racine.addContent( commandSetName.buildNode() );
		
		// ===============================================================
		// Attachement du nom du fichier de shortcut set à utiliser
		// ===============================================================
		racine.addContent( shortcutSetName.buildNode() );
		
		// ===============================================================
		// Attachement de la couleur par defaut
		// ===============================================================
		racine.addContent( defaultColor.buildNode() );
		
		// ===============================================================
		// Attachement de la police
		// ===============================================================
		racine.addContent( keyboardFont.buildNode() );
		
		// ===============================================================
		// Attachement des option avancés
		// ===============================================================
		racine.addContent( advancedOption.buildNode() );
		
		// ===============================================================
		// Attachement du clavier
		// ===============================================================
		try
		{
			racine.addContent( keyboard.BuildNode() );
		}
		catch(Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString( "EX_PROFIL_SAVE_PROFIL" ) + "]" + ex.getMessage() );
		}
		


		
		// ===============================================================
		// Sauvegarde de la structre SAX dans le fichier XML
		// ===============================================================
		XMLOutputter fileOut = new XMLOutputter( Format.getPrettyFormat() );
		fileOut.output( document, new FileOutputStream( profilFilePath ));
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	public void loadDefaultColor (Element racine ) throws Exception
	{
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
	}
	
	public void loadFont (Element racine ) throws Exception
	{
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
	}	
	
	public void loadKeyboard (Element racine ) throws Exception
	{
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
	}
	
	
	public void loadAdvancedOptions(Element racine ) throws Exception
	{
		// ======================================================================
		// chargement des options avancés
		// ======================================================================
		Element advancedOption_elem = racine.getChild( TXMLNames.PR_ELEMENT_ADVANCED_OPTION );
		if( advancedOption_elem == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.PR_ELEMENT_ADVANCED_OPTION );
		}
		try
		{
			advancedOption = new CAdvancedOptions( advancedOption_elem );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_PROFIL" )+ "]"  + ex.getMessage() );
		}
	}
	
	public void loadProfile ( ) throws Exception
	{
		//On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = XMLTools.openFile( profilFilePath );
		
		loadDefaultColor( racine );
		loadFont( racine );
		loadKeyboard( racine );
		loadAdvancedOptions( racine );
	}

	public CAdvancedOptions getAdvancedOption()
	{
		return advancedOption;
	}
	public String getProfilFilePath()
	{
		return profilFilePath;
	}

	public void setProfilFilePath(String profilFilePath)
	{
		this.profilFilePath = profilFilePath;
	}

	public CKeyboard getKeyboard()
	{
		return keyboard;
	}

	public void setKeyboard(CKeyboard keyboard)
	{
		this.keyboard = keyboard;
	}

	public void setCommandSetName(CCommandSetName commandSetName)
	{
		this.commandSetName = commandSetName;
	}

	public void setDefaultColor(CKeyboardColor defaultColor)
	{
		this.defaultColor = defaultColor;
	}

	public void setKeyboardFont(CFont keyboardFont)
	{
		this.keyboardFont = keyboardFont;
	}

	public void setAdvancedOption(CAdvancedOptions advancedOption)
	{
		this.advancedOption = advancedOption;
	}

}
