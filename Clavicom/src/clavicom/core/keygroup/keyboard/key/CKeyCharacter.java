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

import java.awt.Color;

import javax.swing.event.EventListenerList;
import org.jdom.Element;

import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyCharacter extends CKeyThreeLevel
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	CCommand commandNormal;

	CCommand commandShift;

	CCommand commandAltGr;

	protected EventListenerList listenerList;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	public CKeyCharacter(Color myColorNormal, Color myColorClicked,
						 Color myColorEntered, boolean holdable,
						 TPoint myPointMin, TPoint myPointMax)
	{
		this(	myColorNormal, 
				myColorClicked,
				myColorEntered, 
				holdable,
				myPointMin, 
				myPointMax, 
				"", "", "", 
				null, null, null);
	}
	
	public CKeyCharacter(	Color myColorNormal, Color myColorClicked,
							Color myColorEntered, boolean holdable,
							TPoint myPointMin, TPoint myPointMax,
							String captionLeve1, String captionLeve2, String captionLeve3,
							CCommand myCommandNormal, 
							CCommand myCommandShift,
							CCommand myCommandAltGr)
	{
		super(myColorNormal, myColorClicked, myColorEntered, holdable, myPointMin,
				myPointMax, captionLeve1, captionLeve2, captionLeve3);
		commandNormal = myCommandNormal;
		commandShift = myCommandShift;
		commandAltGr = myCommandAltGr;

		this.listenerList = new EventListenerList();
	}

	public CKeyCharacter(Color myColorNormal, Color myColorClicked,
			Color myColorEntered, boolean holdable, 
			TPoint myPointMin, TPoint myPointMax,
			String captionLeve1, String captionLeve2, String captionLeve3)
	{
		super(myColorNormal, myColorClicked, myColorEntered, holdable, myPointMin,
				myPointMax, captionLeve1, captionLeve2, captionLeve3);

		this.listenerList = new EventListenerList();
	}

	public CKeyCharacter(Element eltKey) throws Exception
	{
		super(eltKey);

		// Chargement du noeud Commands
		Element eltCommands = eltKey
				.getChild(TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS);

		if ( eltCommands == null )
		{
			throw new Exception(
					UIString
							.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMANDS_1")
							+ TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS
							+ UIString
									.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMANDS_2"));
		}

		// Vérification que le commandset est chargé
		if ( CCommandSet.GetInstance() == null )
		{
			throw new Exception(UIString
					.getUIString("EX_KEYCHARACTER_COMMAND_SET_NOT_LOADED"));
		}
		
		// Chargement des commandes de chaque niveau
		commandNormal = getCommand(eltCommands,TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_NORMAL, captionNormal);
		commandShift = getCommand(eltCommands,TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_SHIFT, captionShift);
		commandAltGr = getCommand(eltCommands,TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_ALTGR, captionAltGr);

		this.listenerList = new EventListenerList();
	}

	// ----------------------------------------------------------- METHODES --//

	// Listener ==============================================
	public void addOnClickKeyCharacterListener(OnClickKeyCharacterListener l)
	{
		this.listenerList.add(OnClickKeyCharacterListener.class, l);
	}

	public void removeOnClickKeyCharacterListener(OnClickKeyCharacterListener l)
	{
		this.listenerList.remove(OnClickKeyCharacterListener.class, l);
	}

	protected void fireOnClickKeyCharacter()
	{
		OnClickKeyCharacterListener[] listeners = (OnClickKeyCharacterListener[]) listenerList
				.getListeners(OnClickKeyCharacterListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyCharacter(this);
		}
	}

	// fin Listener ============================================

	public String getElementName()
	{
		return (TXMLNames.KY_ELEMENT_CHARACTER);
	}

	public CCommand getCommand(TLevelEnum level)
	{
		if ( level == TLevelEnum.NORMAL )
		{
			return commandNormal;
		}
		else if ( level == TLevelEnum.SHIFT )
		{
			return commandShift;
		}
		else if ( level == TLevelEnum.ALT_GR )
		{
			return commandAltGr;
		}

		return null;
	}

	public void setCommand(CCommand command, TLevelEnum level)
	{
		if ( level == TLevelEnum.NORMAL )
		{
			commandNormal = command;
		}
		else if ( level == TLevelEnum.SHIFT )
		{
			commandShift = command;
		}
		else if ( level == TLevelEnum.ALT_GR )
		{
			commandAltGr = command;
		}
	}

	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		// Ajout du noeud commands
		Element eltCommands = new Element(
				TXMLNames.KY_ELEMENT_CHARACTER_COMMANDS);

		// Ajout du noeud commande Normal
		Element eltCommandNormal = new Element(
				TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_NORMAL);
		String cmdNormalID = "";
		if( commandNormal != null )
		{
			cmdNormalID = String.valueOf(commandNormal.GetID());
		}
		eltCommandNormal.setAttribute(
				TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, cmdNormalID);
		eltCommands.addContent(eltCommandNormal);

		// Ajout du noeud commande Shift
		Element eltCommandShift = new Element(
				TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_SHIFT);
		String cmdShiftID = "";
		if( commandShift != null )
		{
			cmdShiftID = String.valueOf(commandShift.GetID());
		}
		eltCommandShift.setAttribute(
				TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, cmdShiftID);
		eltCommands.addContent(eltCommandShift);

		// Ajout du noeud commande AltGr
		Element eltCommandAltGr = new Element(
				TXMLNames.KY_ELEMENT_CHARACTER_COMMAND_ALTGR);
		String cmdAltGrID = "";
		if( commandAltGr != null )
		{
			cmdAltGrID = String.valueOf(commandAltGr.GetID());
		}
		eltCommandAltGr.setAttribute(
				TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID, cmdAltGrID);
		eltCommands.addContent(eltCommandAltGr);

		// Ajout au noeud père
		eltKeyNode.addContent(eltCommands);
	}

	public void Click()
	{
		fireOnClickKeyCharacter();
	}
	
	public String getCaption( TLevelEnum level )
	{		
		String myCaption = "";
		if( level == TLevelEnum.NORMAL )
		{
			myCaption = captionNormal;
		}
		else if( level == TLevelEnum.SHIFT )
		{
			myCaption = captionShift;
		}
		else if( level == TLevelEnum.ALT_GR )
		{
			myCaption = captionAltGr;
		}
		
		if(isCaptionImage())
			myCaption = CFilePaths.getUserPicturesFolder() + myCaption;
		
		return myCaption;
	}
	
	@Override
	public String toString()
	{
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_CHARACTER") +
					" [" + captionNormal + "]");
	}

	// --------------------------------------------------- METHODES PRIVEES --//
	private CCommand getCommand(Element eltCommands, String XMLNodeName, String caption) throws Exception{
		
		Element eltCommand = eltCommands.getChild(XMLNodeName);
		CCommand resCommand = null;
		
		if ( eltCommand == null )
		{
			throw new Exception(UIString.getUIString("EX_KEYCHARACTER_MISSING_ELEMENT_COMMAND_1")
								+ XMLNodeName
								+ UIString.getUIString("EX_KEYSHORTCUT_MISSING_ELEMENT_COMMAND2"));
		}
		
		// Récupération de l'attribut id
		String strId = eltCommand.getAttributeValue(TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID);
		if ( strId == null )
		{
			throw new Exception(UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_1")
								+ TXMLNames.KY_ATTRIBUTE_CHARACTER_COMMAND_ID
								+ UIString.getUIString("EX_KEYCHARACTER_MISSING_ID_2"));
		}
		
		if ( strId.equals("") )
		{
			eltCommands = null;
		}
		else
		{
			// Transformation de la chaine en int
			int id;
			try
			{
				id = Integer.parseInt(strId);
			}
			catch ( Exception e )
			{
				throw new Exception(UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_1")
									+ strId
									+ UIString.getUIString("EX_KEYCHARACTER_BAD_ID_NOT_INT_2"));
			}
		
			// On essaye de récupérer la commande par id
			resCommand = CCommandSet.GetInstance().GetCommand(id);
			if ( resCommand == null )
			{
				// Affectation d'une commande vide qui génèrera un message d'erreur
				resCommand = new CCommand();
			}
		}
		return resCommand;
	}
	
	public void flush()
	{
		commandNormal = new CCommand();
		commandShift = new CCommand();
		commandAltGr = new CCommand();
	}

}
