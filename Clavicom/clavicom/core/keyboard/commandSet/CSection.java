/*-----------------------------------------------------------------------------+

			Filename			: CSection.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.commandSet

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

package clavicom.core.keyboard.commandSet;

import java.util.HashMap;

public class CSection
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String name;	// nom de la section
	HashMap<String, CCommand> commandMap;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CSection( String myName )
	{
		name = myName;
		commandMap = new HashMap<String, CCommand>();
	}

	//----------------------------------------------------------- METHODES --//
	/**
	 * Méthode permettant d'ajouter une commande à une section
	 * Lance une exception si ce n'est pas possible
	 */
	public void AddCommand( CCommand command )
	{
		commandMap.put( command.GetCaption(), command );
	}
	
	/**
	 * Retourn la command correspondante au name passé en parametre
	 * Retourne null si la commande n'éxiste pas
	 * @param name
	 * @return
	 */
	public CCommand GetCommand( String name )
	{
		return commandMap.get( name );
	}
	
	public String GetName(){return name;}

	//--------------------------------------------------- METHODES PRIVEES --//
}
