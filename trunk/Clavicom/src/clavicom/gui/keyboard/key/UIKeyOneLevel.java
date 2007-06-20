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

import java.awt.image.BufferedImage;

import clavicom.core.keygroup.keyboard.key.CKeyOneLevel;

public abstract class UIKeyOneLevel extends UIKeyKeyboard
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	private BufferedImage captionImage;
	
	boolean reloadImage;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyOneLevel()
	{
		// Appel à la mère
		super();
		
		reloadImage = true;
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	public BufferedImage getCaptionImage()
	{		
		if (getCoreKey().isCaptionImage() == false)
			return null;
		
		if (reloadImage == true)
		{
			// Création de la captionImage
			captionImage = loadCaptionImage(getCaptionText());
		}
		
		return captionImage;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//

	@Override
	protected String getCaptionText()
	{
		String caption = "";
		
		if ( getCoreKey() instanceof CKeyOneLevel )
		{
			caption = ((CKeyOneLevel)getCoreKey()).getCaption();
		}
		
		return caption;
	}
}
