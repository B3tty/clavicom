/*-----------------------------------------------------------------------------+

			Filename			: UITranslucentFrame.java
			Creation date		: 14 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.windows

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

package clavicom.gui.windows;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import com.sun.jna.examples.WindowUtils;

public class UITranslucentFrame extends JFrame
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UITranslucentFrame()
	{
		// Appel au père
		super();
		
    	System.setProperty("sun.java2d.noddraw", "true");
        Shape mask = new Area(new Rectangle2D.Float(0, 0, 100, 100));
//        
        WindowUtils.setWindowMask(this, mask);
    	WindowUtils.setWindowAlpha(this, .5f);
    	
    	setUndecorated(true);

        //setUndecorated(true);
        pack();
        
	}
	//----------------------------------------------------------- METHODES --//	
	@Override
	public void repaint()
	{
		// TODO Auto-generated method stub
		super.repaint();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
