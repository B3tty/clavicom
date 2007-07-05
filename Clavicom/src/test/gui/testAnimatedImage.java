/*-----------------------------------------------------------------------------+

			Filename			: Application.java
			Creation date		: 4 juil. 07
		
			Project				: Test
			Package				: paint

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

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.UIManager;

import clavicom.gui.utils.UIAnimatedImage;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TSwingUtils;

import com.sun.jna.examples.WindowUtils;

public class testAnimatedImage
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{		
		System.setProperty("sun.java2d.noddraw", "true");
		try
		{
			UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel"  );
		}
		catch (Exception ex)
		{
			
		}
		
		// TODO Auto-generated method stub
		UITranslucentFrame mainFrame = new UITranslucentFrame();
		
		UIAnimatedImage image = null;
		
		try
		{
			image = new UIAnimatedImage("Ressources/Application/Pictures/loading.gif");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	
		mainFrame.add(image);
		
		//image.setBackground(Color.WHITE);
		
		mainFrame.setSize(new Dimension(200,200));
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        Shape mask = new Area(new Ellipse2D.Float(0, 0, 32, 32));
        
        mainFrame.setAlwaysOnTop(true);
        
        WindowUtils.setWindowMask(mainFrame, mask);
        WindowUtils.setWindowAlpha(mainFrame, .8f);
        
        TSwingUtils.centerComponentToScreen(mainFrame);
		mainFrame.setVisible(true);
	}

}
