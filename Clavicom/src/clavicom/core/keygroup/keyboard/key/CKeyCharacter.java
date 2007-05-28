/*-----------------------------------------------------------------------------+

			Filename			: CKeyCharacter.java
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

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyCharacter extends CKeyboardKey
{	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CCommand commandNormal;
	CCommand commandShift;
	CCommand commandAltGr;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyCharacter(
			CColor myColorNormal, 
			CColor myColorClicked , 
			CColor myColorEntered , 
			TPoint myPointMin, 
			TPoint myPointMax,
			CCommand myCommandNormal,
			CCommand myCommandShift,
			CCommand myCommandAltGr)
	{
		super(myColorNormal,myColorClicked,myColorEntered,myPointMin,myPointMax);
		commandNormal = myCommandNormal;
		commandShift = myCommandShift;
		commandAltGr = myCommandAltGr;
	}
	
	public CKeyCharacter(
			CColor myColorNormal, 
			CColor myColorClicked , 
			CColor myColorEntered , 
			TPoint myPointMin, 
			TPoint myPointMax)
	{
		super(myColorNormal,myColorClicked,myColorEntered,myPointMin,myPointMax);
	}

	public CKeyCharacter(Element eltKey) throws Exception
	{	
		super(eltKey);
		
		// Chargement du noeud Commands
		Element eltCommands = eltKey.getChild(TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS);

		if(eltCommands == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMANDS_1") +
									TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS + 
									UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMANDS_2")) ;		
		}
		
		// Vérification que le commandset est chargé
		if(CCommandSet.GetInstance() == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_COMMAND_SET_NOT_LOADED")) ;			
		}
		
		// ------ Chargement de la commande NORMAL
		Element eltCommandNormal = eltCommands.getChild(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_NORMAL);
		
		if(eltCommandNormal == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMAND_1") +
									TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_NORMAL + 
									UIString.getUIString("EX_KEYSHORTCUT_MISSING_ELEMENT_COMMAND2")) ;		
		}
		
		// Récupération de l'attribut id
		String strIdNormal = eltCommandNormal.getAttributeValue(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID);
		if((strIdNormal == null) || (strIdNormal.equals("")))
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_2")) ;			
		}
		
		// Transformation de la chaine en int
		int idNormal;
		try
		{
			idNormal = Integer.parseInt(strIdNormal);	
		}	
		catch (Exception e)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_1") +
									strIdNormal + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_2")) ;				
		}
		
		commandNormal = CCommandSet.GetInstance().GetCommande(idNormal);
		
		if(commandNormal == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_2")) ;	
		}
		
		// ------ Chargement de la commande SHIFT
		Element eltCommandShift = eltCommands.getChild(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_SHIFT);
		
		if(eltCommandShift == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMAND_1") +
									TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_SHIFT + 
									UIString.getUIString("EX_KEYSHORTCUT_MISSING_ELEMENT_COMMAND2")) ;		
		}
		
		// Récupération de l'attribut id
		String strIdShift = eltCommandShift.getAttributeValue(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID);
		if((strIdShift == null) || (strIdShift.equals("")))
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_2")) ;			
		}
		
		// Transformation de la chaine en int
		int idShift;
		try
		{
			idShift = Integer.parseInt(strIdShift);	
		}	
		catch (Exception e)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_1") +
									strIdShift + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_2")) ;				
		}
		
		commandShift = CCommandSet.GetInstance().GetCommande(idShift);
		
		if(commandShift == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_2")) ;	
		}
		
		// ------ Chargement de la commande ALTGR
		Element eltCommandAltGr = eltCommands.getChild(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_ALTGR);
		
		if(eltCommandAltGr == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMAND_1") +
									TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_ALTGR + 
									UIString.getUIString("EX_KEYSHORTCUT_MISSING_ELEMENT_COMMAND2")) ;		
		}
		
		// Récupération de l'attribut id
		String strIdAltGr = eltCommandAltGr.getAttributeValue(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID);
		if((strIdAltGr == null) || (strIdAltGr.equals("")))
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_2")) ;			
		}
		
		// Transformation de la chaine en int
		int idAltGr;
		try
		{
			idAltGr = Integer.parseInt(strIdAltGr);	
		}	
		catch (Exception e)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_1") +
									strIdAltGr + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_2")) ;				
		}
		
		commandAltGr = CCommandSet.GetInstance().GetCommande(idAltGr);
		
		if(commandAltGr == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCHARACTER_BAD_ID_1") +
									TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID + 
									UIString.getUIString("EX_KEYCHARACTER_BAD_ID_2")) ;	
		}
	}
	
	//----------------------------------------------------------- METHODES --//	
	public String getElementName()
	{
		return (TXMLNames.KY_ELEMENT_CHARACTER);
	}

	public CCommand getCommandAltGr()
	{
		return commandAltGr;
	}

	public void setCommandAltGr(CCommand commandAltGr)
	{
		this.commandAltGr = commandAltGr;
	}

	public CCommand getCommandNormal()
	{
		return commandNormal;
	}

	public void setCommandNormal(CCommand commandNormal)
	{
		this.commandNormal = commandNormal;
	}

	public CCommand getCommandShift()
	{
		return commandShift;
	}

	public void setCommandShift(CCommand commandShift)
	{
		this.commandShift = commandShift;
	}
	
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		// Ajout du noeud commands		
		Element eltCommands = new Element(TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS);
		
		// Ajout du noeud commande Normal
		Element eltCommandNormal = new Element(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_NORMAL);
		eltCommandNormal.setAttribute(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, String.valueOf(commandNormal.GetID()));
		eltCommands.addContent(eltCommandNormal);
		
		// Ajout du noeud commande Shift
		Element eltCommandShift = new Element(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_SHIFT);
		eltCommandShift.setAttribute(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, String.valueOf(commandShift.GetID()));
		eltCommands.addContent(eltCommandShift);
		
		// Ajout du noeud commande AltGr
		Element eltCommandAltGr = new Element(TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_ALTGR);
		eltCommandAltGr.setAttribute(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, String.valueOf(commandAltGr.GetID()));
		eltCommands.addContent(eltCommandAltGr);	
		
		// Ajout au noeud père
		eltKeyNode.addContent(eltCommands);
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
