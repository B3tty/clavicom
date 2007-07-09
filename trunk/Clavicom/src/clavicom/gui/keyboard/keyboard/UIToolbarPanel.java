/*-----------------------------------------------------------------------------+

			Filename			: UIToolbarPanel.java
			Creation date		: 9 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.keyboard

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

package clavicom.gui.keyboard.keyboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class UIToolbarPanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	protected final int TAILLE_CONTOUR = 3;				// Taille du contour
	
	protected final int DEMI_TAILLE_CONTOUR = Math.round((float)TAILLE_CONTOUR/2f);
	//---------------------------------------------------------- VARIABLES --//	
	
	protected Color bgColor;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIToolbarPanel(Color bgColor)
	{
		this.bgColor = bgColor;
	}
	//----------------------------------------------------------- METHODES --//	


	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//


	//----------------------------------------------------------- METHODES --//
	
	@Override
	protected void paintComponent(Graphics arg0)
	{
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
		
		Graphics2D buffer = (Graphics2D) arg0;
		
		buffer.drawImage(recreateBackground(), 0, 0, null);
	}
	
	protected BufferedImage recreateBackground()
	{			
		// Variables
		Graphics2D buffer;
		BufferedImage image;
		
		// Création de l'image
		image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
		buffer = (Graphics2D) image.getGraphics();
		
		// Construction du buffer
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Création du Paint du premier calque
		Color vGradientStartColor, vGradientEndColor;
		vGradientStartColor =  bgColor.brighter().brighter();
		vGradientEndColor = bgColor;				

		Paint vPaint = new GradientPaint(	0, 
											0, 
											vGradientStartColor, 
											0, 
											getHeight(), 
											vGradientEndColor, 
											true);
		buffer.setPaint(vPaint);

		// Dessin du premier Paint
		buffer.fillRect(0, 0, getWidth(), getHeight());
		
		// Dessin du contour
		buffer.setColor(bgColor.darker());
		buffer.setStroke(new BasicStroke(TAILLE_CONTOUR));
		
		buffer.drawRect(	DEMI_TAILLE_CONTOUR-1, 
							DEMI_TAILLE_CONTOUR-1, 
							getWidth()-TAILLE_CONTOUR, 
							getHeight()-TAILLE_CONTOUR);
		// Retour
		return image;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
