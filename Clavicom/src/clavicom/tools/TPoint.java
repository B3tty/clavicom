/*-----------------------------------------------------------------------------+

			Filename			: CPoint.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

import org.jdom.Element;

public class TPoint 
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	float x,y;	// Coordonnées relatives du point (pourcentage)
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public TPoint (float myX, float myY)
	{		
		x = myX;
		y = myY;
	}
	
	/**
	 * Construit l'objet à partir du noeud XML
	 * @param pointElement
	 * @throws Exception
	 */
	public TPoint (Element pointElement) throws Exception
	{
		String strX,strY;
		Element eltX, eltY;
		
		// Récupération des noeuds fils
		eltX = pointElement.getChild(TXMLNames.PT_ELEMENT_X);
		eltY = pointElement.getChild(TXMLNames.PT_ELEMENT_Y);

		// Test des chaines récupérées
		if ((eltX == null) || (eltY == null))
		{
			throw new Exception ("[Point] Chargement du noeud point : element manquant");
		}
		
		// Récupération des chaînes
		strX = eltX.getText();
		strY = eltY.getText();
		
		// Test des chaines récupérées
		if ((strX == null) || (strY == null))
		{
			throw new Exception ("[Point] Chargement du noeud point : valeur manquante");
		}
		else if ((strX.equals("")) || (strY.equals("")))
		{
			throw new Exception ("[Point] Chargement du noeud point : element vide");
		}
		
		// Construction X
		try
		{
			x = Float.parseFloat(strX);
		}
		catch (Exception e)
		{
			throw new Exception ("[Point] Chargement du noeud point : la chaine \"" + strX + "\" ne peut être convertie en entier");
		}
		
		// Construction Y
		try
		{
			y = Float.parseFloat(strY);
		}
		catch (Exception e)
		{
			throw new Exception ("[Point] Chargement du noeud point : la chaine \"" + strY + "\" ne peut être convertie en entier");
		}
		
	}
	
	public Element buildNode()
	{
		Element eltPoint = new Element(TXMLNames.PT_ELEMENT_POINT);
		Element eltX;
		Element eltY;
		
		// X
		eltX = new Element (TXMLNames.PT_ELEMENT_X);
		eltX.setText(String.valueOf(x));
		eltPoint.addContent(eltX);
		
		// Y
		eltY = new Element (TXMLNames.PT_ELEMENT_Y);
		eltY.setText(String.valueOf(y));
		eltPoint.addContent(eltY);		
		
		return eltPoint;
	}
	
	//----------------------------------------------------------- METHODES --//	
	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}	
	
	//--------------------------------------------------- METHODES PRIVEES --//	
}
