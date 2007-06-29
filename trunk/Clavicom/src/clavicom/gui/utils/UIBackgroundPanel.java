/*-----------------------------------------------------------------------------+

			Filename			: UIBackgrundPanel.java
			Creation date		: 27 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.utils

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

package clavicom.gui.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import clavicom.core.profil.CProfil;

public class UIBackgroundPanel extends UITranslucentPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	final int TAILLE_ARC = 25;					// Rayon de l'arrondi du fond
	final int TAILLE_CONTOUR = 3;				// Taille du contour
	final int TAILLE_ARC_CONTOUR = TAILLE_ARC - TAILLE_CONTOUR;

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//
	protected BufferedImage recreateBackground()
	{			
		// Variables
		Color bgdColor = CProfil.getInstance().getDefaultColor().getBackColor().getColor();
		Graphics2D buffer;
		BufferedImage image;
		
		// Création de l'image
		image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
		buffer = (Graphics2D) image.getGraphics();
		
		// Construction du buffer
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Création du Paint du premier calque
		Color vGradientStartColor, vGradientEndColor;
		vGradientStartColor =  bgdColor.brighter().brighter();
		vGradientEndColor = bgdColor;				

		Paint vPaint = new GradientPaint(	0, 
											0, 
											vGradientStartColor, 
											0, 
											getHeight(), 
											vGradientEndColor, 
											true);
		buffer.setPaint(vPaint);

		// Dessin du premier Paint
		buffer.fillRoundRect(0, 0, getWidth(), getHeight(), TAILLE_ARC, TAILLE_ARC);
		
		// Dessin du contour
		buffer.setColor(bgdColor.darker());
		buffer.setStroke(new BasicStroke(TAILLE_CONTOUR));
		
		buffer.drawRoundRect(	TAILLE_CONTOUR/2, 
								TAILLE_CONTOUR/2, 
								getWidth()-TAILLE_CONTOUR, 
								getHeight()-TAILLE_CONTOUR,
								TAILLE_ARC_CONTOUR,TAILLE_ARC_CONTOUR);
		
		// Retour
		return image;
	}
	
	public Shape getWindowShape()
	{		
		if(getWidth() > 0 && getHeight() > 0)
		{
			return new RoundRectangle2D.Float(	TAILLE_CONTOUR+2, 
												TAILLE_CONTOUR+2, 
												getWidth(), 
												getHeight(),
												TAILLE_ARC,TAILLE_ARC);
		}
		else
		{
			return null;
		}
	
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
