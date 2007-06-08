/*-----------------------------------------------------------------------------+

			Filename			: CMouseKeyClick.java
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
import clavicom.tools.TMouseKeyClickEnum;

public class CMouseKeyClick extends CMouseKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TMouseKeyClickEnum click;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseKeyClick( 
			TMouseKeyClickEnum myClick,
			String caption,
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered)
	{
		super( caption, myColorNormal, myColorClicked, myColorEntered );
		click = myClick;
	}

	//----------------------------------------------------------- METHODES --//	
	
	public TMouseKeyClickEnum GetClick(){return click;}

	@Override
	public void Click()
	{
		// fire...
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
