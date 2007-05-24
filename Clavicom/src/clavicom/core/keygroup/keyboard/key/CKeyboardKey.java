/*-----------------------------------------------------------------------------+

			Filename			: CKeyboardKey.java
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
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyboardKey extends CKey
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	TPoint pointMin;	// Point supérieur gauche
	TPoint pointMax;	// Point inférieur droit
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyboardKey(
			CColor myColorNormal, 
			CColor myColorClicked , 
			CColor myColorEntered , 
			TPoint myPointMin, 
			TPoint myPointMax)
	{
		super (myColorNormal, myColorClicked, myColorEntered );
		
		pointMin = myPointMin;
		pointMax = myPointMax;
	}
	
	/**
	 * Permet de construire l'objet courant à partir d'un noeud XML
	 * @param eltKey : element contenant les informations à charger
	 */
	public CKeyboardKey(Element eltKey) throws Exception
	{
		super( eltKey );
		
		// On vérifie que l'element existe
		if(eltKey == null)
		{
			throw new Exception (UIString.getUIString("EX_KEYBOARDKEY_EMPTY_NODE"));
			
		}
		
		// Récupération des coordonnées
		Element eltCoord = eltKey.getChild(TXMLNames.KY_ELEMENT_COORDINATES);
		if(eltCoord == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_MISSING_ATTRIBUTE_COORDINATE_1") + 
									TXMLNames.KY_ELEMENT_COORDINATES + UIString.getUIString("EX_KEYBOARDKEY_MISSING_ATTRIBUTE_COORDINATE_2")) ;	
		}
		
		// Récupération des deux points
		List elements =  eltCoord.getChildren(TXMLNames.PT_ELEMENT_POINT);
		
		if (elements.size() != 2)
		{
			throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_BAD_NUMBER_OF_POINT") + 
									TXMLNames.PT_ELEMENT_POINT) ;				
		}
		
		// Récupération du Min et du Max
		boolean maxFound = false; 
		boolean minFound = false;
		
		Element eltCourant;
		
		for (Object objCourant : elements)
		{
			if ( objCourant instanceof Element )
			{
				eltCourant = (Element) objCourant;
			}
			else
			{
				throw new Exception (	
						UIString.getUIString("EX_KEYBOARDKEY_UNVALID_COORDINATE_POS_1") + 
						TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + 
						UIString.getUIString("EX_KEYBOARDKEY_UNVALID_COORDINATE_POS_2")) ;	
			}
			
			if (eltCourant.getAttributeValue(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS).equals(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX))
			{
				if(maxFound == false)
				{
					try
					{
						pointMax = new TPoint(eltCourant);
						maxFound = true;
					}
					catch (Exception e)
					{
						throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_BAD_MAX_POINT")
												+ e.getMessage()) ;	
					}
				}
				else
				{
					throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MAX_POINT_1") + 
											TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + 
											UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MAX_POINT_2") + 
											TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX + 
											UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MAX_POINT_3")) ;									
				}
					
			}
			else if (eltCourant.getAttributeValue(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS).equals(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN))
			{
				if(minFound == false)
				{
					try
					{
						pointMin = new TPoint(eltCourant);
						minFound = true;
					}
					catch (Exception e)
					{
						throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_BAD_MIN_POINT") + 
												e.getMessage()) ;	
					}
				}
				else
				{
					throw new Exception (	UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MIN_POINT_1") + 
											TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + 
											UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MIN_POINT_2") + 
											TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN + 
											UIString.getUIString("EX_KEYBOARDKEY_SURNUMEROUS_MIN_POINT_3")) ;								
				}
			}
		}
						
	}
	
	//----------------------------------------------------------- METHODES --//	
	public Element buildNode(int order) throws Exception
	{
		// Création de l'élement
		Element eltKeyNode = new Element(getElementName());
		
		// Ajout des informations génériques
		completeNodeGlobal(eltKeyNode,order);
		
		// Ajout des informations spécifiques 
		completeNodeSpecific(eltKeyNode);
		
		return eltKeyNode;
	}
	
	public abstract void completeNodeSpecific(Element eltKeyNode) throws Exception;
	
	public abstract String getElementName();
	

	public TPoint getPointMax()
	{
		return pointMax;
	}

	public void setPointMax(TPoint pointMax)
	{
		this.pointMax = pointMax;
	}

	public TPoint getPointMin()
	{
		return pointMin;
	}

	public void setPointMin(TPoint pointMin)
	{
		this.pointMin = pointMin;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Ajoute les attributs de la classe au noeud construit par les fils
	 * @param Noeud à compléter
	 */
	private void completeNodeGlobal(Element myNode, int order)
	{	
		// Ordre
		myNode.setAttribute(TXMLNames.KY_ATTRIBUTE_ORDER,String.valueOf(order));		
		
		// Coordonnées
		Element eltCoord = new Element( TXMLNames.KY_ELEMENT_COORDINATES );
		
		// pointMin
		Element eltPointMin = pointMin.buildNode();
		Attribute attPointMin = new Attribute(	TXMLNames.KY_ATTRIBUTE_COORDINATE_POS,
												TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN );
		eltPointMin.setAttribute(attPointMin);
		
		// pointMax
		Element eltPointMax = pointMax.buildNode();
		Attribute attPointMax = new Attribute(	TXMLNames.KY_ATTRIBUTE_COORDINATE_POS,
												TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX );
		eltPointMax.setAttribute(attPointMax);
		
		eltCoord.addContent(eltPointMin);
		eltCoord.addContent(eltPointMax);
		
		// Ajout des noeuds
		myNode.addContent(eltCoord);
	}
}

