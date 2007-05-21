/*-----------------------------------------------------------------------------+

			Filename			: CCommand.java
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

import java.util.ArrayList;
import java.util.List;

public class CCommand
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String caption;
	List<CCode> CodeList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CCommand( String myCaption )
	{
		caption = myCaption;
		CodeList = new ArrayList<CCode>();
	}

	//----------------------------------------------------------- METHODES --//
	/**
	 * Ajoute un code à la liste des codes
	 * Lance une exception si l'order n'est pas bon
	 */
	public void AddCode( int order, CCode code )
	{
		CodeList.add(order, code);
	}
	
	public String GetCaption(){return caption;}
	
	/**
	 * Donne le code correspondant à l'order donné
	 * Lance une exception si l'order n'est pas bon
	 * @param order
	 * @return
	 */
	public CCode GetCode( int order )
	{
		return CodeList.get( order );
	}
	
	public int Size(){return CodeList.size();}

	//--------------------------------------------------- METHODES PRIVEES --//
}
