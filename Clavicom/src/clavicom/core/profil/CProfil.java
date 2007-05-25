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

import clavicom.core.keygroup.keyboard.key.CKeyboardKey;

public class CProfil
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CLangueUI langueUI;				// langue de l'interface utilisateur
	CDictionnary dictionnary;		// dictionnaire
	CKeyboardColor defultColor;		// Couleur par défaut du clavicom
	CTransparency transparency;		// Transparence
	CSound sound;					// Gestion du son
	CNavigation navigation;			// Type de navigation de l'utilisateur
	CKeyboardKey keyboard;			// Structure du clavicom
	CMouse mouse;					// Souricom
	CPreferedWords preferedWords;	// liste des mots préférés de l'utilisateur
	String commandSet;				// nom du fichier de commandeSet de l'utilisateur
									// (pour vérifier que c'est bien le bon commandSet que l'on charge)

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
