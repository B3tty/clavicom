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

package clavicom.core.keyboard;

import java.awt.Color;

import org.jdom.Element;

import clavicom.tools.CPoint;

public abstract class CKeyboardKey
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	Color color;		// Couleur de la touche
	CPoint pointMin;	// Point supérieur gauche
	CPoint pointMax;	// Point inférieur droit
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyboardKey(Color myColor, CPoint myPointMin, CPoint myPointMax)
	{
		color = myColor;
		pointMin = myPointMin;
		pointMax = myPointMax;
	}
	
	public abstract Element buildNode();	// Retourne le noeud XML correspondant
	
	//----------------------------------------------------------- METHODES --//	
	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public CPoint getPointMax()
	{
		return pointMax;
	}

	public void setPointMax(CPoint pointMax)
	{
		this.pointMax = pointMax;
	}

	public CPoint getPointMin()
	{
		return pointMin;
	}

	public void setPointMin(CPoint pointMin)
	{
		this.pointMin = pointMin;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}

