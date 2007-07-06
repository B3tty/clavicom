/*-----------------------------------------------------------------------------+

			Filename			: PanelTest.java
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

package paint;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class PanelParent extends JPanel implements MouseListener
{
	public PanelParent()
	{
		addMouseListener(this);
	}
	
	protected void paintComponent(Graphics arg0)
	{
		System.out.println("P");
		super.paintComponent(arg0);
	}
	public void repaint()
	{
		System.out.println("-P repaint");
		// TODO Auto-generated method stub
		super.repaint();
	}
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		repaint();
	}
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
}
