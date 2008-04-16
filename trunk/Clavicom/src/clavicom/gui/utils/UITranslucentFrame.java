/*-----------------------------------------------------------------------------+

			Filename			: UITranslucentFrame.java
			Creation date		: 14 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.windows

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

package clavicom.gui.utils;

import javax.swing.JRootPane;

import clavicom.tools.OSTypeEnum;

import com.sun.jna.examples.WindowUtils;

public class UITranslucentFrame extends UIClavicomFrame
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	float transparency;			// Transparence de la fenêtre
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UITranslucentFrame()
	{
		this(1f);
	}
	
	public UITranslucentFrame(float myTransparency)
	{
		// Appel au père
		super();
	
		// On enleve la barre de fenêtre
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        // On pack
        pack();
        
        // On applique la transparence
        setTransparency(myTransparency);
	}
	
	//----------------------------------------------------------- METHODES --//	
	public void setTransparency(float newTransparency)
	{
		transparency = newTransparency;
		
		if(newTransparency > 0)
		{
			if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.WINDOWS )
			{
				WindowUtils.setWindowAlpha(this, transparency);
			}else if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.LINUX )
			{
				// TODO
			}else if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.MAC )
			{
				// TODO
			}else if ( OSTypeEnum.getCurrentOSType() == OSTypeEnum.UNKNOWN )
			{
				// TODO
			}
		}
	}
	
	public float getTransparency()
	{
		return transparency;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
