/*-----------------------------------------------------------------------------+

			Filename			: tesCommandSet.java
			Creation date		: 22 mai 07
		
			Project				: Clavicom
			Package				: test

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

package test;

import clavicom.core.key.keyboard.command.CCode;
import clavicom.core.key.keyboard.command.CCommand;
import clavicom.core.key.keyboard.command.commandSet.CCommandSet;
import clavicom.core.key.keyboard.command.commandSet.CSection;

public class testCommandSet
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	
	public static void main(String[] args)
	{
		String chemin = "C:\\Workspace\\Clavicom\\clavicom\\Ressources\\Application\\CommandSets\\francais.ccs";
		try
		{
			CCommandSet commandSet = new CCommandSet( chemin );
			
			CSection section = commandSet.GetSection("Spéciaux");

			
			
			CCommand command = section.GetCommand( "&" );
			
			for( int i = 0 ; i < command.Size() ; ++i )
			{
				CCode code = command.GetCode(i);
				System.out.println(code.GetKeyEvent() + " " + code.GetKeyAction());
			}
			
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
