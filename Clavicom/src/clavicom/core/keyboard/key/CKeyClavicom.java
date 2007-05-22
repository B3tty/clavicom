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

package clavicom.core.keyboard.key;

import java.awt.Color;

import org.jdom.Element;

import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;


public class CKeyClavicom extends CKeyboardKey
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	

	TKeyClavicomActionType action;	// Action a faire
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyClavicom (Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		super(myColor,myPointMin,myPointMax);
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
		Element eltAction = eltKeyClavicom.getChild(TXMLNames.KY_CLAVICOM_ELEMENT_ACTION);
		
		if(eltAction == null)
		{
			throw new Exception (	"[Chargement d'une touche clavicom] : Element " + 
					TXMLNames.KY_CLAVICOM_ELEMENT_ACTION + " attendu manquant") ;		
		}
		
		action = TKeyClavicomActionType.getValue(eltAction.getText());
		if(action == null)
		{
			throw new Exception (	"[Chargement d'une touche clavicom] : Element " + 
					TXMLNames.KY_CLAVICOM_ELEMENT_ACTION + " invalide") ;
		}
	}
	
	//----------------------------------------------------------- METHODES --//	

	public TKeyClavicomActionType getAction()
	{
		return action;	
	}

	public void setAction(TKeyClavicomActionType action)
	{
		this.action = action;
	}
	
	/**
	 * Créé le noeud à partir de l'objet courant
	 * @return Noeud construit
	 * @throws Exception 
	 */
	public Element buildNode() throws Exception
	{
		// Construction de l'élement
		Element eltKey = new Element(TXMLNames.KY_CLAVICOM_ELEMENT);
		
		// Ajout des elements père
		completeNode(eltKey);
		
		// Ajout des elements spécifiques
		Element eltAction = new Element(TXMLNames.KY_CLAVICOM_ELEMENT_ACTION);
		String strAction = TKeyClavicomActionType.getString(action);
		
		if (strAction.equals(""))
		{
			throw new Exception ("[Création d'un noeud touche clavicom] : action inconnue");
		}
		
		eltAction.setText(TKeyClavicomActionType.getString(action));
		
		eltKey.addContent(eltAction);
		
		return eltKey;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
