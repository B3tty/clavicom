/*-----------------------------------------------------------------------------+

			Filename			: CMouseKeyMove.java
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

import clavicom.core.keygroup.CColor;
import clavicom.tools.TMouseKeyMoveEnum;

public class CMouseKeyMove extends CMouseKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TMouseKeyMoveEnum direction;
	
	static int speedLevel; // vitesse du mouvement

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseKeyMove( 
			TMouseKeyMoveEnum mydirection,
			String caption,
			CColor myColorNormal, 
			CColor myColorClicked , 
			CColor myColorEntered )
	{
		super( caption, myColorNormal, myColorClicked, myColorEntered );
		direction = mydirection;
	}

	//----------------------------------------------------------- METHODES --//	
	
	public TMouseKeyMoveEnum GetDirection(){ return direction; }
	
	public static int GetSpeedLevel(){ return speedLevel; }
	public static void SetSpeedLevel( int mySpeedLevel ){speedLevel = mySpeedLevel; }
	//--------------------------------------------------- METHODES PRIVEES --//

	@Override
	public void Click()
	{
		// fire
	}
}
