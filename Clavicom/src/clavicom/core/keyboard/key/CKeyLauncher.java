/*-----------------------------------------------------------------------------+

			Filename			: CKeyboardKeyClavicom.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard

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

package clavicom.core.keyboard.key;

import java.awt.Color;

import org.jdom.Element;

import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TPoint;


public class CKeyLauncher extends CKeyboardKey
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	

	TKeyClavicomActionType action;	// Action a faire
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLauncher (Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		super(myColor,myPointMin,myPointMax);
	}
	
	//----------------------------------------------------------- METHODES --//	

	public TKeyClavicomActionType getAction()
	{
		return action;	
	}

	public void setAction(TKeyClavicomActionType action)
	{
		this.action = action;
	}
	
	public Element buildNode()
	{
		return null;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
