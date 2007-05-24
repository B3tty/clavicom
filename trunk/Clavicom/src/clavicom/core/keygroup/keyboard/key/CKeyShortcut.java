/*-----------------------------------------------------------------------------+

			Filename			: CKeyShortcut.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.key

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

package clavicom.core.keygroup.keyboard.key;

import java.awt.Color;

import org.jdom.Element;

import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyShortcut extends CKeyCommand
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyShortcut(Color myColor, TPoint myPointMin, TPoint myPointMax, CCommand myCommand)
	{
		super(myColor, myPointMin, myPointMax,  myCommand);
	}
	
	public CKeyShortcut(Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		super(myColor, myPointMin, myPointMax);
	}

	public CKeyShortcut(Element eltKeyCommand) throws Exception
	{	
		super(eltKeyCommand);
	}
	
	//----------------------------------------------------------- METHODES --//	
	protected CCommand getCommandFromCaption(String strCommand)
	{
		//CShortcutSet
		return null;
	}

	protected String getElementNameSpecific()
	{
		return null;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
