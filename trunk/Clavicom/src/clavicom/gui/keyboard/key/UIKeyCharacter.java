/*-----------------------------------------------------------------------------+

			Filename			: UIKeyKeyboardKey.java
			Creation date		: 6 juin 07
		
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


import java.awt.Rectangle;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;

public class UIKeyCharacter extends UIKeyThreeLevel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CKeyCharacter coreKey;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyCharacter(CKeyCharacter myCoreKey)
	{
		// Appel de la mère
		super();
		
		coreKey = myCoreKey;
		addListeners();
	}
	
	//----------------------------------------------------------- METHODES --//	
	// On redéfinie les setBounds pour limiter l'aire de la touche
	@Override
	public void setBounds(int x, int y, int w, int h)
	{
		if(boundsAreOk(x, y, w, h))
			super.setBounds(x, y, w, h);
	}
	
	@Override
	public void setBounds(Rectangle r) 
	{
		if (boundsAreOk(r.x, r.y, r.width, r.height))
			super.setBounds(r);
	}
	
	/**
	 * Vérifie si les bounds passées sont dans le cadre du père, entièrement
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	private boolean boundsAreOk(int x, int y, int w, int h)
	{
		// Aire trop elevée
		if(w*h > MAX_AUTHORIZED_AREA)
			return false;
		
		// Trop à gauche
		if(x < 0)
			return false;
		
		// Trop en haut
		if(y < 0)
			return false;
		
		if(getParent() != null)
		{
			// Trop à droite
			if((x + w) > (getParent().getWidth()))
				return false;
			
			// Trop en bas
			if((y + h) > (getParent().getHeight()))
				return false;				
		}
		
		return true;
	}
	
	@Override
	public CKey getCoreKey()
	{
		return coreKey;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
