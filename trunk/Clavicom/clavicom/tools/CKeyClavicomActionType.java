/*-----------------------------------------------------------------------------+

			Filename			: CKeyClavicomType.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

public final class CKeyClavicomActionType 
{
    private CKeyClavicomActionType() 
    {
    	// Rien à faire
    }
    
    // Liste des valeurs
    public final static CKeyClavicomActionType NONE					 	= new CKeyClavicomActionType();
    public final static CKeyClavicomActionType SWITCH_MOUSE_KEYBOARD 	= new CKeyClavicomActionType();
    public final static CKeyClavicomActionType CLOSE_APPLICATION 		= new CKeyClavicomActionType();
    public final static CKeyClavicomActionType OPEN_CONFIGURATION		= new CKeyClavicomActionType();
} 
