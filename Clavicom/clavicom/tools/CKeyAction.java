/*-----------------------------------------------------------------------------+

			Filename			: KeyAction.java
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

public final class CKeyAction 
{
	//--------------------------------------------------------- CONSTANTES --//
	//	 Liste des valeurs
    public final static CKeyAction PRESSED	= new CKeyAction();
    public final static CKeyAction RELEASED	= new CKeyAction();
    
    
    public static String CS_STRING_ENUM_PRESSED		= "pressed";
	public static String CS_STRING_ENUM_RELEASED	= "released";
   
    //---------------------------------------------------------- VARIABLES --//	
	
	//------------------------------------------------------ CONSTRUCTEURS --//
    private CKeyAction()
    {
    	
    }
	
	//----------------------------------------------------------- METHODES --//	
	
	//--------------------------------------------------- METHODES PRIVEES --//	
}
