/*-----------------------------------------------------------------------------+

			Filename			: UIKeyOneLevel.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key

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

package clavicom.gui.keyboard.key;

import org.jdom.Element;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.profil.CProfil;

public class _UIKeyOneLevel extends UIKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	static CKeyString key;		// Objet du noyau
	
	//----------------------------------------------------------- METHODES --//	
	public _UIKeyOneLevel()
	{
		// Appel à la mère
		super();
		
		// Création de la touche
		
		
		// Ajout des liseners
		addToAllListeners();
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Ajout en tant que listener
	 */
	protected void addToAllListeners()
	{
		// Abonnement à la coreKey
		getCoreKey().addColorListener(this);
	}
	
	/**
	 * Retourne l'objet du noyau
	 */
	protected CKey getCoreKey()
	{
		// TODO ---> MODIFIER
		if (key != null)
		{
			return key;
		}
		
		try
		{		
			key = (CKeyString) CProfil.getInstance().getKeyboard().getKeyGroup(0).GetkeyList(0).GetKeyboardKey(2);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		System.out.println("ok");
		return key;
	}
	
	protected String getCaption()
	{
		return key.getCaption();
	}
}
