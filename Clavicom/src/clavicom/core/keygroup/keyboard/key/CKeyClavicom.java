/*-----------------------------------------------------------------------------+

			Filename			: CKeyboardKeyClavicom.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard

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
import clavicom.core.listener.OnClickKeyClavicomListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;


public class CKeyClavicom extends CKeyOneLevel
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	

	TKeyClavicomActionType action;	// Action a faire
	
	protected EventListenerList listenerList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
		
	public CKeyClavicom(
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered , 
			boolean holdable,
			TPoint myPointMin, 
			TPoint myPointMax,
			TKeyClavicomActionType myAction,
			String myCaption)
	{
		super(myColorNormal,myColorClicked,myColorEntered,holdable,myPointMin,myPointMax,myCaption);
		
		action = myAction;
		listenerList = new EventListenerList();
	}
	
	/**
	 * Créé l'objet à partir d'elemeent
	 * @throws Exception 
	 */	
	public CKeyClavicom (Element eltKeyClavicom) throws Exception
	{
		// On appelle le chargement du père, qui récupèrera seulement les élements
		// qui le concerne.
		
		super(eltKeyClavicom);
		
		// Chargement de l'action
		Element eltAction = eltKeyClavicom.getChild(TXMLNames.KY_ELEMENT_CLAVICOM_ACTION);
		
		if(eltAction == null)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYCLAVICOM_MISSING_ACTION_1") +
									TXMLNames.KY_ELEMENT_CLAVICOM_ACTION + 
									UIString.getUIString("EX_KEYCLAVICOM_MISSING_ACTION_2")) ;		
		}
		
		action = TKeyClavicomActionType.getValue(eltAction.getText());
		if(action == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEYCLAVICOM_INVALID_ACTION_1") +
									TXMLNames.KY_ELEMENT_CLAVICOM_ACTION + 
									UIString.getUIString("EX_KEYCLAVICOM_INVALID_ACTION_2")) ;
		}
		
		listenerList = new EventListenerList();
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	
	// Listener ==============================================
	public void addOnClickKeyClavicomListener(OnClickKeyClavicomListener l)
	{
		this.listenerList.add(OnClickKeyClavicomListener.class, l);
	}

	public void removeOnClickKeyClavicomListener(OnClickKeyClavicomListener l)
	{
		this.listenerList.remove(OnClickKeyClavicomListener.class, l);
	}

	protected void fireOnClickKeyClavicom()
	{
		OnClickKeyClavicomListener[] listeners = (OnClickKeyClavicomListener[]) listenerList
				.getListeners(OnClickKeyClavicomListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyClavicom(action);
		}
	}
	// fin Listener ============================================
	
	
	/**
	 * Créé le noeud à partir de l'objet courant
	 * @return Noeud construit
	 * @throws Exception 
	 */
	public void completeNodeSpecific2(Element keyNode) throws Exception
	{	
		// Ajout des elements spécifiques
		Element eltAction = new Element(TXMLNames.KY_ELEMENT_CLAVICOM_ACTION);
		String strAction = TKeyClavicomActionType.getString(action);
		
		if (strAction.equals(""))
		{
			throw new Exception (UIString.getUIString("EX_KEYCLAVICOM_UNKNOWN_ACTION"));
		}
		
		eltAction.setText(TKeyClavicomActionType.getString(action));
		
		keyNode.addContent(eltAction);
	}
	
	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_CLAVICOM;
	}
	
	public TKeyClavicomActionType getAction()
	{
		return action;	
	}

	public void setAction(TKeyClavicomActionType action)
	{
		this.action = action;
	}

	@Override
	protected Boolean toBeSave()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void Click()
	{
		fireOnClickKeyClavicom();
	}
	
	@Override
	public String getCaption()
	{
		return CFilePaths.getPictures() + caption;
	}
	
	@Override
	public String toString()
	{
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_CLAVICOM") +
					" [" + action.toString() + "]");
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
