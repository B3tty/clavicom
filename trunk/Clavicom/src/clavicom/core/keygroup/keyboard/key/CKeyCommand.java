/*-----------------------------------------------------------------------------+

			Filename			: CKeyCommand.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.key.keyboard.key

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

public abstract class CKeyCommand extends CKeyboardKey
{	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CCommand command;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyCommand(Color myColor, TPoint myPointMin, TPoint myPointMax, CCommand myCommand)
	{
		super(myColor, myPointMin, myPointMax);
		command = myCommand;
	}
	
	public CKeyCommand(Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		super(myColor, myPointMin, myPointMax);
	}

	public CKeyCommand(Element eltKeyCommand) throws Exception
	{	
		super(eltKeyCommand);
		
		// Chargement de la commande
		Element eltCommand = eltKeyCommand.getChild(TXMLNames.KY_ELEMENT_COMMAND_COMMAND);
		
		if(eltCommand == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCOMMAND_MISSING_ELEMENT_COMMAND_1") +
									TXMLNames.KY_ELEMENT_CLAVICOM_ACTION + 
									UIString.getUIString("EX_KEYCOMMAND_MISSING_ELEMENT_COMMAND_2")) ;		
		}
		
		// Récupération de l'attribut caption
		String strCaption = eltCommand.getAttributeValue(TXMLNames.KY_ATTRIBUTE_COMMAND_CAPTION);
		if((strCaption == null) || (strCaption == ""))
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCOMMAND_MISSING_CAPTION_1") +
									TXMLNames.KY_ATTRIBUTE_COMMAND_CAPTION + 
									UIString.getUIString("EX_KEYCOMMAND_MISSING_CAPTION_2")) ;			
		}
		
		// Récupération de la commande correspondant à la caption
		// Selon le type réel (la classe fille), celà appelera ira chercher la commande
		// dans le bon set.		
		command = getCommandFromCaption(strCaption);
	}
	
	//----------------------------------------------------------- METHODES --//	
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		// Ajout de la commande
		Element eltCommand = new Element(TXMLNames.KY_ELEMENT_COMMAND_COMMAND);
		eltCommand.setAttribute(TXMLNames.KY_ATTRIBUTE_COMMAND_CAPTION,command.GetCaption());
		
		eltKeyNode.addContent(eltCommand);
	}

	public String getElementName()
	{
		return getElementNameSpecific();
	}
	
	public CCommand getCommand()
	{
		return command;
	}

	public void setCommand(CCommand command)
	{
		this.command = command;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	protected abstract String getElementNameSpecific();
	protected abstract CCommand getCommandFromCaption(String strCommand);
	
}
