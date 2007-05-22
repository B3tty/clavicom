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
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyboardKey(Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		color = myColor;
		pointMin = myPointMin;
		pointMax = myPointMax;
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
		
		// Récupération des coordonnées
		Element eltCoord = eltKey.getChild(TXMLNames.KY_ELEMENT_COORDINATES);
		if(eltCoord == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
									TXMLNames.KY_ELEMENT_COORDINATES + " attendu manquant") ;	
		}
		
		// Récupération du Max
		Element eltMax = eltCoord.getChild(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX);
		if(eltMax == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX + " attendu manquant") ;				
		}
		
		// Chargement du Max
		try
		{
			pointMax = new TPoint(eltMax);
		}
		catch (Exception e)
		{
			throw new Exception ("[Chargement d'une touche] : Point invalide " + e.getMessage() ) ;
		}

		// Récupération du Min
		Element eltMin = eltCoord.getChild(TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN);
		if(eltMax == null)
		{
			throw new Exception (	"[Chargement d'une touche] : Element " + 
					TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN + " attendu manquant") ;				
		}
		
		// Chargement du Min
		try
		{
			pointMin = new TPoint(eltMin);
		}
		catch (Exception e)
		{
			throw new Exception ("[Chargement d'une touche] : Point invalide " + e.getMessage() ) ;
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
		
		int R;
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
		
		int G;
		try
		{
			G = Integer.parseInt(eltR.getText());
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
		
		int B;
		try
		{
			B = Integer.parseInt(eltR.getText());
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
	
	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Ajoute les attributs de la classe au noeud construit par les fils
	 * @param Noeud à compléter
	 */
	protected void completeNode(Element myNode)
	{	
		// Coordonnées
		Element eltCoord = new Element( TXMLNames.KY_ELEMENT_COORDINATES );
		
		// pointMin
		Element eltPointMin = pointMin.buildNode();
		Attribute attPointMin = new Attribute(	TXMLNames.KY_ATTRIBUTE_COORDINATE_POS,
												TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MIN );
		eltPointMin.setAttribute(attPointMin);
		
		// pointMax
		Element eltPointMax = pointMin.buildNode();
		Attribute attPointMax = new Attribute(	TXMLNames.KY_ATTRIBUTE_COORDINATE_POS,
												TXMLNames.KY_ATTRIBUTE_COORDINATE_POS_MAX );
		eltPointMin.setAttribute(attPointMax);
		
		eltCoord.addContent(eltPointMin);
		eltCoord.addContent(eltPointMax);
		
		// Couleur
		Element eltColor = new Element( TXMLNames.KY_ELEMENT_COLOR );
		
		Element eltColorR = new Element (TXMLNames.KY_ELEMENT_COLOR_R);
		eltColorR.setText(String.valueOf(color.getRed()));
		eltColor.addContent(eltColorR);
		
		Element eltColorG = new Element (TXMLNames.KY_ELEMENT_COLOR_G);
		eltColorR.setText(String.valueOf(color.getGreen()));
		eltColor.addContent(eltColorG);
		
		Element eltColorB = new Element (TXMLNames.KY_ELEMENT_COLOR_B);
		eltColorR.setText(String.valueOf(color.getBlue()));
		eltColor.addContent(eltColorB);
		
		// Ajout des noeuds
		myNode.addContent(eltCoord);
		myNode.addContent(eltColor);
	}
}

