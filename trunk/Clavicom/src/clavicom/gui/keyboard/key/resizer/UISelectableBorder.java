/*-----------------------------------------------------------------------------+

			Filename			: UISelectableBorder.java
			Creation date		: 05 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard

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
    
    						-------------------------
    						
    		Adapted from		: MySwing Advanced Swing Utilites
    		Author				: Santhosh Kumar T
    		Copyright (C)		: (2005) Santhosh Kumar T

+-----------------------------------------------------------------------------*/

package clavicom.gui.keyboard.key.resizer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;



public class UISelectableBorder implements UIResizableBorder
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	private int dist = 10;
	private boolean isVisible;
	private boolean isSelected;

	int locations[] = { SwingConstants.NORTH, SwingConstants.SOUTH,
						SwingConstants.WEST, SwingConstants.EAST,
						SwingConstants.NORTH_WEST, SwingConstants.NORTH_EAST,
						SwingConstants.SOUTH_WEST, SwingConstants.SOUTH_EAST, 
						 0, // move
						-1, // no location
	};

	int cursors[] = { Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR,
			Cursor.W_RESIZE_CURSOR, Cursor.E_RESIZE_CURSOR,
			Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
			Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR,
			Cursor.MOVE_CURSOR, Cursor.DEFAULT_CURSOR, };

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UISelectableBorder(int dist)
	{
		this.dist = dist;
		isVisible = false;
		isSelected = false;
	}
	
	//----------------------------------------------------------- METHODES --//	
	public Insets getBorderInsets(Component component)
	{
		return new Insets(dist, dist, dist, dist);
	}

	public boolean isBorderOpaque()
	{
		return false;
	}

	public void paintBorder(Component component, Graphics g, int x, int y,
			int w, int h)
	{
		if (isVisible == true)
		{
			if(isSelected == true)
			{
				drawBorder(g, Color.RED, Color.RED.brighter().brighter().brighter(),Color.RED, x, y, w, h);
			}
			else
			{
				drawBorder(g, Color.BLACK, Color.WHITE, Color.BLACK, x, y, w, h);
			}
		}
	}
	
	public int getResizeCursor(MouseEvent me)
	{
		Component comp = me.getComponent();
		int w = comp.getWidth();
		int h = comp.getHeight();

		Rectangle bounds = new Rectangle(0, 0, w, h);

		if ( !bounds.contains(me.getPoint()) )
			return Cursor.DEFAULT_CURSOR;

		Rectangle actualBounds = new Rectangle(dist, dist, w - 2 * dist, h - 2
				* dist);
		if ( actualBounds.contains(me.getPoint()) )
			return Cursor.DEFAULT_CURSOR;

		for ( int i = 0; i < locations.length - 2; i++ )
		{
			Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
			if ( rect.contains(me.getPoint()) )
				return cursors[i];
		}
		
		return Cursor.MOVE_CURSOR;
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}
	
	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void setSelected(boolean myIsSelected)
	{
		isSelected = myIsSelected;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	private void drawBorder(Graphics g, Color borderDrawColor,
			Color cornerFillColor, Color cornerDrawColor, int x, int y, int w,
			int h)
	{
		g.setColor(borderDrawColor);
		g.drawRect(x + dist / 2, y + dist / 2, w - dist, h - dist);

		for ( int i = 0; i < locations.length - 2; i++ )
		{
			Rectangle rect = getRectangle(x, y, w, h, locations[i]);
			g.setColor(cornerFillColor);
			g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
			g.setColor(cornerDrawColor);
			g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		}
	}

	private Rectangle getRectangle(int x, int y, int w, int h, int location)
	{
		switch ( location )
		{
			case SwingConstants.NORTH :
				return new Rectangle(x + w / 2 - dist / 2, y, dist, dist);
			case SwingConstants.SOUTH :
				return new Rectangle(x + w / 2 - dist / 2, y + h - dist, dist,
						dist);
			case SwingConstants.WEST :
				return new Rectangle(x, y + h / 2 - dist / 2, dist, dist);
			case SwingConstants.EAST :
				return new Rectangle(x + w - dist, y + h / 2 - dist / 2, dist,
						dist);
			case SwingConstants.NORTH_WEST :
				return new Rectangle(x, y, dist, dist);
			case SwingConstants.NORTH_EAST :
				return new Rectangle(x + w - dist, y, dist, dist);
			case SwingConstants.SOUTH_WEST :
				return new Rectangle(x, y + h - dist, dist, dist);
			case SwingConstants.SOUTH_EAST :
				return new Rectangle(x + w - dist, y + h - dist, dist, dist);
		}
		return null;
	}
}