/*-----------------------------------------------------------------------------+

			Filename			: MouseMoveEngine.java
			Creation date		: 6 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.mouse

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

package clavicom.gui.mouse;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import clavicom.gui.listener.MouseToMoveListener;

public class MouseMoveEngine implements MouseToMoveListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	UIMouse uiMouse;
	UIMouseFrame uiMouseFrame;
	
	static MouseMoveEngine instance;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public MouseMoveEngine( UIMouse myUIMouse, UIMouseFrame myUIMouseFrame )
	{
		uiMouse = myUIMouse;
		uiMouseFrame = myUIMouseFrame;
	}
	
	public static void createInstance( UIMouse myUIMouse, UIMouseFrame myUIMouseFrame )
	{
		instance = new MouseMoveEngine( myUIMouse, myUIMouseFrame );
	}
	
	public static MouseMoveEngine getInstance()
	{
		return instance;
	}

	//----------------------------------------------------------- METHODES --//	
	
	public void listen()
	{
		uiMouse.removeMouseToMoveListener( this ); // par sécurité
		uiMouse.addMouseToMoveListener( this );
	}
	
	public void unListen()
	{
		uiMouse.removeMouseToMoveListener( this );
	}

	public void mouseToMove()
	{
		// on déplace la Mouseframe pour qu'elle ne cache pas la souris
		
		
	    Dimension screenSz = Toolkit.getDefaultToolkit().getScreenSize();
	    Point leftPoint = new Point(0, (int)uiMouseFrame.getLocation().getY() );
	    Point rightPoint = new Point( (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - uiMouseFrame.getWidth() ), (int)uiMouseFrame.getLocation().getY() );
	    
	    // si la souricom est du coté gauche, on la met dans le coin droit,
	    // sinon, on la met dans le coin gauche
	    if( uiMouseFrame.getLocation().getX() < (screenSz.getWidth() / 2) )
	    {
	    	uiMouseFrame.setLocation( rightPoint );
	    }
	    else
	    {
	    	uiMouseFrame.setLocation( leftPoint );
	    }
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
