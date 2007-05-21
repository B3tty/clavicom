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

import org.jdom.Attribute;
import org.jdom.Element;

public class CPoint 
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	float x,y;	// Coordonnées relatives du point (pourcentage)
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CPoint (float myX, float myY) throws Exception
	{
		if((x < 0) || (x > 100) || (x < 0) || (y > 100))
		{
			throw new Exception ("Point en dehors des bornes [0;100]");
		}
		
		x = myX;
		y = myY;
	}
	
	public CPoint (Element pointElement) throws Exception
	{
		String strX,strY;
		Element eltX, eltY;
		
		// Récupération des noeuds fils
		eltX = pointElement.getChild("x");
		eltY = pointElement.getChild("y");

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
		
		// Construction des valeurs
		try
		{
			x = Float.parseFloat(strX);
			y = Float.parseFloat(strY);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception ("[Point] Chargement du noeud point : chaine invalide");
		}
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
