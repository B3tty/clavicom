/*-----------------------------------------------------------------------------+

			Filename			: CMouseKey.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.mouse

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

package clavicom.core.keygroup.mouse;

import java.awt.Color;

import clavicom.CFilePaths;
import clavicom.core.keygroup.CKey;

public abstract class CMouseKey extends CKey
{
	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String caption;


	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseKey( String myCaption, Color myNormal, Color myPressed, Color myEntered)
	{
		super(myNormal, myPressed, myEntered);
		
		caption = myCaption;
	}

	//----------------------------------------------------------- METHODES --//
	
	public String getCaption()
	{
		return CFilePaths.getPictures() + caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
