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


import java.awt.Color;

import java.awt.Dimension;

import java.awt.Shape;

import java.awt.geom.Area;

import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.UIManager;

import com.sun.jna.examples.WindowUtils;

/** Example which uses the {@link WindowUtils} class.  Demonstrates the 
 * definition of a cross-platform library with several platform-specific
 * implementations based on JNA native library definitions.
 */
public class testTransparency 
{
    public static void main(String[] args) 
    {
        try 
        {
            System.setProperty("sun.java2d.noddraw", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) 
        {
        	e.printStackTrace();
        }
        
        final JFrame frame = new JFrame();
        
        //ClockFace face = new ClockFace(new Dimension(800, 800));
        JPanel test = new JPanel();
        test.setBackground(Color.BLUE);
        frame.setPreferredSize(new Dimension(200,200));
        frame.add(test);
        
        frame.pack();
        
        frame.setLocation(400,400);

        Shape mask = new Area(new Rectangle2D.Float(0, 0, 200, 100));
        
        WindowUtils.setWindowMask(frame, mask);
        WindowUtils.setWindowAlpha(frame, .8f);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

