/*-----------------------------------------------------------------------------+

			Filename			: CKeyDynamicString.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keygroup.keyboard.key

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
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import org.jdom.Element;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyDynamicString extends CKeyOneLevel
{
	//--------------------------------------------------------- CONSTANTES --//
	int order;
	
	protected EventListenerList listenerList;
	
	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyDynamicString(
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered , 
			TPoint myPointMin, 
			TPoint myPointMax)
	{
		this(	myColorNormal, 
				myColorClicked , 
				myColorEntered , 
				myPointMin, 
				myPointMax,
				"");
	}
	
	public CKeyDynamicString(
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered , 
			TPoint myPointMin, 
			TPoint myPointMax,
			String myCaption)
	{
		super (	myColorNormal, 
				myColorClicked , 
				myColorEntered , 
				myPointMin, 
				myPointMax,
				myCaption);
		
		listenerList = new EventListenerList();
	}
	
	public CKeyDynamicString(Element keyDynamicElement) throws Exception 
	{
		// On appelle le chargement du père, qui récupèrera seulement les élements
		// qui le concerne.
		super(keyDynamicElement);
		
		// Chargement de l'order
		String strOrder;
		try
		{
			strOrder = keyDynamicElement.getChildText(TXMLNames.KY_ELEMENT_DYNAMIC_ORDER);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_KEYDYNAMICSTRING_MISSING_ORDER_1") +
									TXMLNames.KY_ELEMENT_DYNAMIC_ORDER + 
									UIString.getUIString("EX_KEYDYNAMICSTRING_MISSING_ORDER_2")) ;			
		}

		// Cast de l'order en int
		try
		{
			order = Integer.parseInt(strOrder);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_KEYDYNAMICSTRING_BAS_ORDER_1") +
									strOrder + 
									UIString.getUIString("EX_KEYDYNAMICSTRING_BAS_ORDER_1")) ;			
		}
		
		listenerList = new EventListenerList();
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	// Listener ==============================================
	public void addOnClickKeyDynamicStringListener(OnClickKeyDynamicStringListener l)
	{
		this.listenerList.add(OnClickKeyDynamicStringListener.class, l);
	}

	public void removeOnClickKeyDynamicStringListener(OnClickKeyDynamicStringListener l)
	{
		this.listenerList.remove(OnClickKeyDynamicStringListener.class, l);
	}

	protected void fireOnClickKeyDynamicString()
	{
		OnClickKeyDynamicStringListener[] listeners = (OnClickKeyDynamicStringListener[]) listenerList
				.getListeners(OnClickKeyDynamicStringListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyDynamicString(this);
		}
	}
	// fin Listener ============================================
	
	
	
	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		// Ajout des informations spécifiques 
		try
		{
			completeNodeSpecific3(eltKeyNode);
		}
		catch (Exception ex)
		{ 
			throw new Exception (	UIString.getUIString("EX_KEYDYNAMICSTRING_BUILD") + 
								 	ex.getMessage());
		}
		
		// Stockage de l'order
		Element eltOrder = new Element(TXMLNames.KY_ELEMENT_DYNAMIC_ORDER);
		eltOrder.setText(String.valueOf(order));
		
		eltKeyNode.addContent(eltOrder);
	}
	
	public abstract String GetStringCommand();
	
	/**
	 * Permet de récupérer la liste de commandes correspondant à la chaîne courante
	 * @return La liste de commandes
	 */
	public List<CCommand> getCommands() throws Exception
	{
		// On vérifie que le CCommandSet est bien chargé
		if (CCommandSet.GetInstance() == null)
		{
			throw new Exception(UIString.getUIString("EX_KEYDYNAMICSTRING_COMMANDSET_NOT_LOADED"));
		}
		
		// On parcours la currentCaption et on récupère la commande 
		// à partir de chaque caractère
		char currentChar;
		CCommand currentCommand;
		List<CCommand> listCommands = new ArrayList<CCommand>();
		
		for(int i = 0 ; i < GetStringCommand().length() ; ++i )
		{
			// Récupération du caractère courant
			currentChar = GetStringCommand().charAt(i);
			
			// Recherche de la commande
			currentCommand = CCommandSet.GetInstance().GetCommand(String.valueOf(currentChar));
			
			if(currentCommand == null)
			{
				throw new Exception(	UIString.getUIString("EX_KEYDYNAMICSTRING_COMMAND_NOT_FOUND_1") +
										currentChar + 
										UIString.getUIString("EX_KEYDYNAMICSTRING_COMMAND_NOT_FOUND_2"));
			}
			
			listCommands.add(currentCommand);			
		}
		
		// Fin
		return listCommands;
	}
	
	public abstract void completeNodeSpecific3(Element eltKeyNode) throws Exception;
	
	@Override
	public void Click()
	{
		fireOnClickKeyDynamicString();		
	}

	public int getOrder()
	{
		return order;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
