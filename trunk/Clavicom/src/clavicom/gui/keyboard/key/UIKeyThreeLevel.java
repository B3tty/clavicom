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

import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.key.CKeyThreeLevel;
import clavicom.tools.TLevelEnum;

public abstract class UIKeyThreeLevel extends UIKeyKeyboard
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//

	
	protected BufferedImage captionImageNormal;
	protected BufferedImage captionImageShift;
	protected BufferedImage captionImageAltGr;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIKeyThreeLevel()
	{
		// Appel à la mère
		super();

	}
	
	//----------------------------------------------------------- METHODES --//
	protected String getCaptionText()
	{
		// Retoure la chaîne correspondant au niveau en cours
		
		return ((CKeyThreeLevel)getCoreKey()).getCaption(CLevelEngine.getInstance().getCurrentLevel());
	}
	
	public BufferedImage getCaptionImage()
	{		
		if(getCoreKey().isCaptionImage() == false)
			return null;
		
		if (reloadImage() == true)
		{
			setReloadImage(true);
			
			//  Cast de l'objet
			CKeyThreeLevel coreKey = (CKeyThreeLevel)getCoreKey();
			
			// Création des images
			captionImageNormal = loadCaptionImage(coreKey.getCaption(TLevelEnum.NORMAL));
			captionImageShift = loadCaptionImage(coreKey.getCaption(TLevelEnum.SHIFT));
			captionImageAltGr = loadCaptionImage(coreKey.getCaption(TLevelEnum.ALT_GR));
		}		
		
		if(CLevelEngine.getInstance().getCurrentLevel() == TLevelEnum.NORMAL)
		{
			return captionImageNormal;
		}
		else if(CLevelEngine.getInstance().getCurrentLevel() == TLevelEnum.SHIFT)
		{
			return captionImageShift;
		}
		else if(CLevelEngine.getInstance().getCurrentLevel() == TLevelEnum.ALT_GR)
		{
			return captionImageAltGr;
		}
		else
		{
			return null;
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
