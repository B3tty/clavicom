/*-----------------------------------------------------------------------------+

			Filename			: testTransparency.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: test.gui

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

package test.gui;


/* Copyright (c) 2007 Timothy Wall, All Rights Reserved
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.  
 */


import javax.swing.JFrame;

import com.sun.jna.examples.WindowUtils;

/** Example which uses the {@link WindowUtils} class.  Demonstrates the 
 * definition of a cross-platform library with several platform-specific
 * implementations based on JNA native library definitions.
 */
public class testTransparency2 {
    
    static JFrame frame;
    
    public static void main(String[] args) 
    {
    	System.setProperty("sun.java2d.noddraw", "true");
    	frame = new JFrame();
		
        //frame.setUndecorated(true);
        frame.setFocusableWindowState(false);
        frame.setSize(500,500);

        WindowUtils.setWindowAlpha(frame, 1f);
        
        //frame.setExtendedState( Frame.MAXIMIZED_BOTH );
        frame.setVisible(true);
        frame.requestFocus();

    }
}

