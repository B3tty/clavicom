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

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;


public class CProfil
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	CLangueUIName langueUI;			// Langue de l'interface utilisateur
	CDictionnaryName dictionnary;	// dictionnaire
	CKeyboardColor defaultColor;	// Couleur par défaut du clavicom
	CTransparency transparency;		// Transparence
	CSound sound;					// Gestion du son
	CNavigation navigation;			// Type de navigation de l'utilisateur
	CKeyboard keyboard;				// Structure du clavicom
	CPreferedWords preferedWords;	// liste des mots préférés de l'utilisateur
	String commandSet;				// nom du fichier de commandeSet de l'utilisateur
									// (pour vérifier que c'est bien le bon commandSet que l'on charge)
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CProfil( String profilFilePath ) throws Exception
	{
		// chargement du fichier de profil
		LoadProfilFile( profilFilePath );
	}

	//----------------------------------------------------------- METHODES --//	
	
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
		// chargement du nom du commande set à utiliser
		// ======================================================================
		
		
		// ======================================================================
		// chargement du commandeSet
		// ======================================================================
		
		// ======================================================================
		// chargement du shortcutSet
		// ======================================================================
		
		// ======================================================================
		// chargement du dictionnaire
		// ======================================================================
		
		// ======================================================================
		// chargement de la couleur par defaut
		// ======================================================================
		
		// ======================================================================
		// chargement de la transparence
		// ======================================================================
		
		// ======================================================================
		// chargement de la gestion du son
		// ======================================================================
		
		// ======================================================================
		// chargement de la navigation
		// ======================================================================
		
		// ======================================================================
		// chargement du clavier
		// ======================================================================

		// ======================================================================
		// chargement des mots préférés del'utilisateur
		// ======================================================================
		
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
