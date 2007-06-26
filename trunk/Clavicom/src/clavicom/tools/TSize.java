/*-----------------------------------------------------------------------------+

			Filename			: TSize.java
			Creation date		: 25 juin 07
		
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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

import clavicom.core.profil.CFramePosition;


public class TSize
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	
	static public Rectangle getRectangleBound( TPoint leftUp, TPoint rightDown )
	{
		Rectangle rectangle = new Rectangle();
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		int x,y,width,height;
		width = (int)((double)screenDimension.getWidth() * ( rightDown.getX() - leftUp.getX() ) );
		height = (int)((double)screenDimension.getHeight() * ( rightDown.getY() - leftUp.getY() ) );
		
		x = (int)( (double)screenDimension.getWidth() * leftUp.getX() );
		y = (int)( (double)screenDimension.getHeight() * leftUp.getY() );
		
		
		rectangle.setLocation(x, y);
		rectangle.setSize(width, height);
		
		return rectangle;
	}
	
	static public CFramePosition getFramePosition ( JFrame frame )
	{
		CFramePosition framePosition = new CFramePosition();
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		double frame_lu_x = frame.getX();
		double frame_lu_y = frame.getY();
		double frame_rd_x = frame.getX() + frame.getSize().getWidth();
		double frame_rd_y = frame.getY() + frame.getSize().getHeight();
		
		float lu_x = (float)( frame_lu_x / screenDimension.getWidth() );
		float lu_y = (float)( frame_lu_y / screenDimension.getHeight() );
		float rd_x = (float)( frame_rd_x / screenDimension.getWidth() );
		float rd_y = (float)( frame_rd_y / screenDimension.getHeight() );
		
		TPoint leftUp = new TPoint( lu_x, lu_y );
		TPoint rightDown = new TPoint( rd_x, rd_y );
		
		framePosition.setLeftUp( leftUp );
		framePosition.setRightDown( rightDown );
		
		
		return framePosition;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}

