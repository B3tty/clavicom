/*-----------------------------------------------------------------------------+

			Filename			: UIJResizer.java
			Creation date		: 05 mai 07
		
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

package clavicom.gui.utils;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import clavicom.gui.keyboard.key.resizer.UIInvisibleBorder;
import clavicom.gui.keyboard.key.resizer.UIResizableBorder;


public class UIMovingPanel extends UITranslucentPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	final int MINIMUM_HEIGHT = 10;
	final int MINIMUM_WIDTH = 10;
	final int INSET_SIZE = 5;
	
	//---------------------------------------------------------- VARIABLES --//	
	protected UIInvisibleBorder resizableBorder;	// Bordure lors du resize
	protected boolean changeSelection;		
	
	protected JFrame parentFrame;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIMovingPanel(JFrame myParentFrame)
	{
		parentFrame = myParentFrame;
		resizableBorder = new UIInvisibleBorder(10);
	}
	
	//----------------------------------------------------------- METHODES --//	
	//-----------------------------------------------------------------------
	// Divers
	//-----------------------------------------------------------------------  	
	public void setEditable(boolean myIsResizable)
	{	
		if(myIsResizable == true)
		{
			// On enlève, au cas où
			removeMouseListener(resizeMouseListener);
			removeMouseMotionListener(resizeMouseListener);
			
			// On ajoute les listeners
			addMouseListener(resizeMouseListener);
			addMouseMotionListener(resizeMouseListener);
			
			super.setBorder(resizableBorder);	
		}
		else
		{
			removeMouseListener(resizeMouseListener);
			removeMouseMotionListener(resizeMouseListener);
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private void didResized()
	{
		if ( getParent() != null )
		{
			invalidate();
		}
	}
	
	MouseInputListener resizeMouseListener = new MouseInputAdapter()
	{
		public void mouseEntered(MouseEvent me)
		{
			// Rien à faire
			//resizableBorder.setVisible(true);
		}
		
		public void mouseMoved(MouseEvent me)
		{
			UIResizableBorder border = (UIResizableBorder) getBorder();
			setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me)));
		}

		public void mouseExited(MouseEvent mouseEvent)
		{
			setCursor(Cursor.getDefaultCursor());
			//resizableBorder.setVisible(false);
			
			//repaint();
		}

		private int cursor;

		private Point startPos = null;

		public void mousePressed(MouseEvent me)
		{
			// Si c'est pas le clic gauche, on annule
			if (!SwingUtilities.isLeftMouseButton(me))
            {
                return;
            }
			
			UIResizableBorder border = (UIResizableBorder) getBorder();
			cursor = border.getResizeCursor(me);
			startPos = me.getPoint();
			
			changeSelection = true;
		}

		public void mouseDragged(MouseEvent me)
		{
            if (!SwingUtilities.isLeftMouseButton(me))
            {
                return;
            }
            
			if ( startPos != null )
			{
				Point framePos = parentFrame.getLocation();
				
				int dx = me.getX() - startPos.x;
				int dy = me.getY() - startPos.y;
				
				Rectangle newBounds = new Rectangle(); 
				
				switch ( cursor )
				{
					case Cursor.N_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY() + dy, 
													parentFrame.getWidth(), 
													parentFrame.getHeight()- dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.S_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentFrame.getWidth(), 
													parentFrame.getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.W_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx,
													(int)framePos.getY(), 
													parentFrame.getWidth() - dx, 
													parentFrame.getHeight());
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.E_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentFrame.getWidth() + dx, 
													parentFrame.getHeight());
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.NW_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx, 
													(int)framePos.getY() + dy, 
													parentFrame.getWidth() - dx, 
													parentFrame.getHeight() - dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.NE_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY() + dy, 
													parentFrame.getWidth() + dx, 
													parentFrame.getHeight() - dy);
						tryResize(newBounds);
						startPos = new Point(me.getX(), startPos.y);
						didResized();
						break;
						
					case Cursor.SW_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx, 
													(int)framePos.getY(), 
													parentFrame.getWidth() - dx, 
													parentFrame.getHeight() + dy);
						tryResize(newBounds);
						startPos = new Point(startPos.x, me.getY());
						didResized();
						break;
						
					case Cursor.SE_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentFrame.getWidth() + dx, 
													parentFrame.getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.MOVE_CURSOR :						
		                Point loc = parentFrame.getLocationOnScreen();
		                loc.translate(dx, dy);
		                parentFrame.setLocation(loc.x, loc.y);
			            
						didResized();
				}
				
				// cursor shouldn't change while dragging
				setCursor(Cursor.getPredefinedCursor(cursor));
			}
		}
		
		/**
		 * Vérifie que la nouvelle taille est "visible"
		 * @param newSize
		 */
		public void tryResize(Rectangle newSize)
		{
			if ((newSize.getWidth() > MINIMUM_WIDTH) && (newSize.getHeight() > MINIMUM_HEIGHT))
			{
				parentFrame.setBounds(newSize);
			}
		}

		public void mouseReleased(MouseEvent mouseEvent)
		{			
			startPos = null;
		}
	};
	
	//protected abstract void boundChanged();
	
}
