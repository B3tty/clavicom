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

package clavicom.core.keyboard.key;

import java.awt.Color;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;

import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyboardKey
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	Color color;		// Couleur de la touche
	TPoint pointMin;	// Point supérieur gauche
	TPoint pointMax;	// Point inférieur droit
	int order;			// Ordre de la touche dans le niveau 3 du clavier
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyboardKey(Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		color = myColor;
		pointMin = myPointMin;
		pointMax = myPointMax;
		order = -1;
	}
	
	/**
	 * Permet de construire l'objet courant à partir d'un noeud XML
	 * @param eltKey : element contenant les informations à charger
	 */
	public CKeyboardKey(Element eltKey) throws Exception
	{
		// On vérifie que l'element existe
		if(eltKey == null)
		{
			throw new Exception ("[Chargement d'une touche] : Noeud vide");
		}
		
		// Récupération de l'ordre
		String strOrder = eltKey.getAttributeValue(TXMLNames.KY_ATTRIBUT_ORDER);
		
		if (strOrder == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Attribut \"" + 
					TXMLNames.KY_ATTRIBUT_ORDER + "\" attendu manquant") ;			
		}
		
		try
		{
			order = Integer.parseInt(strOrder);
		}
		catch (Exception e)
		{
			throw new Exception (	"[Chargement d'une touche] : Attribut \"" + 
					TXMLNames.KY_ATTRIBUT_ORDER + "\", la chaîne \"" + strOrder + 
					"\" ne peut être convertie en entier") ;			
		}
		
		// Récupération des coordonnées
		Element eltCoord = eltKey.getChild(TXMLNames.KY_ELEMENT_COORDINATES);
		if(eltCoord == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
									TXMLNames.KY_ELEMENT_COORDINATES + " attendu manquant") ;	
		}
		
		// Récupération des deux points
		List elements =  eltCoord.getChildren(TXMLNames.PT_ELEMENT_POINT);
		
		if (elements.size() != 2)
		{
			throw new Exception (	"[Chargement d'une touche] : Mauvais nombre de " + 
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
				throw new Exception (	"[Chargement d'une touche] : Element " + 
						TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + " n'est pas un noeud valide") ;	
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
						throw new Exception (	"[Chargement d'une touche] : chargement du point max " + e.getMessage()) ;	
					}
				}
				else
				{
					throw new Exception (	"[Chargement d'une touche] : Element " + 
							TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + " d'attribut " + 
							TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX + " trouvé deux fois") ;									
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
						throw new Exception (	"[Chargement d'une touche] : chargement du point min" + e.getMessage()) ;	
					}
				}
				else
				{
					throw new Exception (	"[Chargement d'une touche] : Element " + 
							TXMLNames.KY_ATTRIBUTE_COORDINATE_POS + " d'attribut " + 
							TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN + " trouvé deux fois") ;									
				}
			}
		}
				
		// Récupération de la couleur
		Element eltColor = eltKey.getChild(TXMLNames.KY_ELEMENT_COLOR);
		if(eltColor == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR + " attendu manquant") ;				
		}
		
		// Récupération de la couleur R
		Element eltR = eltColor.getChild(TXMLNames.KY_ELEMENT_COLOR_R);
		if(eltR == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_R + " attendu manquant") ;				
		}	
		
		int R = 0;
		try
		{
			R = Integer.parseInt(eltR.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_R + " n'est pas un entier") ;					
		}
		
		// Récupération de la couleur G
		Element eltG = eltColor.getChild(TXMLNames.KY_ELEMENT_COLOR_G);
		if(eltG == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_G + " attendu manquant") ;			
		}	
		
		int G = 0;
		try
		{
			G = Integer.parseInt(eltG.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_G + " n'est pas un entier") ;					
		}
		
		// Récupération de la couleur B
		Element eltB = eltColor.getChild(TXMLNames.KY_ELEMENT_COLOR_B);
		if(eltB == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_B + " attendu manquant") ;			
		}	
		
		int B = 0;
		try
		{
			B = Integer.parseInt(eltB.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ELEMENT_COLOR_B + " n'est pas un entier") ;					
		}
		
		color = new Color(R,G,B);		
	}
	
	//----------------------------------------------------------- METHODES --//	
	public abstract Element buildNode() throws Exception;
	
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

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
	
	public int getOrder()
	{
		return order;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Ajoute les attributs de la classe au noeud construit par les fils
	 * @param Noeud à compléter
	 */
	protected void completeNode(Element myNode)
	{	
		// Coordonnées
		Element eltCoord = new Element( TXMLNames.KY_ELEMENT_COORDINATES );
		
		// Ordre
		Attribute attOrder = new Attribute (TXMLNames.KY_ATTRIBUT_ORDER, String.valueOf(order));
		
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
		
		// Couleur
		Element eltColor = new Element( TXMLNames.KY_ELEMENT_COLOR );
		
		Element eltColorR = new Element (TXMLNames.KY_ELEMENT_COLOR_R);
		eltColorR.setText(String.valueOf(color.getRed()));
		eltColor.addContent(eltColorR);
		
		Element eltColorG = new Element (TXMLNames.KY_ELEMENT_COLOR_G);
		eltColorG.setText(String.valueOf(color.getGreen()));
		eltColor.addContent(eltColorG);
		
		Element eltColorB = new Element (TXMLNames.KY_ELEMENT_COLOR_B);
		eltColorB.setText(String.valueOf(color.getBlue()));
		eltColor.addContent(eltColorB);
		
		// Ajout des noeuds
		myNode.addContent(eltCoord);
		myNode.addContent(eltColor);
		myNode.setAttribute(attOrder);
	}
}

