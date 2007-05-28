/*-----------------------------------------------------------------------------+

			Filename			: CDictionnaryName.java
			Creation date		: 28 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.profil

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

package clavicom.core.profil;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CCommandSetName
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String commandSetName;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CCommandSetName( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_COMMANDSET_NAME") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		commandSetName = node.getText();
		
		if( commandSetName.equals( "" ))
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_COMMANDSET_NAME") + "] : " + UIString.getUIString("EX_PROFIL_COMMANDSET_NAME_MISSING") );
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	public String getcommandSetName()
	{
		return commandSetName;
	}

	public void setcommandSetName(String commandSetName)
	{
		this.commandSetName = commandSetName;
	}
	
	
	public Element buildNode()
	{
		Element commandSetName_elem = new Element( TXMLNames.PR_ELEMENT_COMMANDSET_NAME );
		
		commandSetName_elem.setText( commandSetName );
		
		return commandSetName_elem;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
