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

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import clavicom.gui.keyboard.key.resizer.UIInvisibleBorder;
import clavicom.gui.keyboard.key.resizer.UIResizableBorder;


public class UIMovingPanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	final int MINIMUM_HEIGHT = 10;
	final int MINIMUM_WIDTH = 10;
	final int INSET_SIZE = 5;
	final int BORDER_INSET = 5;
	
	//---------------------------------------------------------- VARIABLES --//	
	protected UIInvisibleBorder resizableBorder;	// Bordure lors du resize
	protected boolean changeSelection;		
	
	protected Component parentComponent;
	protected MouseInputAdapter otherAdapter;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIMovingPanel(Component myParentComponent, MouseInputAdapter otherAdapter)
	{
		parentComponent = myParentComponent;
		this.otherAdapter = otherAdapter;
		resizableBorder = new UIInvisibleBorder(BORDER_INSET);
	}
	
	public UIMovingPanel(Component myParentComponent)
	{
		this(myParentComponent, null);
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
		
		public void mouseMoved(MouseEvent me)
		{
			UIResizableBorder border = (UIResizableBorder) getBorder();
			setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me)));
			
			if(otherAdapter != null)
				otherAdapter.mouseMoved(me);
		}

		public void mouseExited(MouseEvent mouseEvent)
		{
			setCursor(Cursor.getDefaultCursor());
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
			
			if(otherAdapter != null)
				otherAdapter.mousePressed(me);
		}

		public void mouseDragged(MouseEvent me)
		{
            if (!SwingUtilities.isLeftMouseButton(me))
            {
                return;
            }
            
			if ( startPos != null )
			{
				Point framePos = parentComponent.getLocation();
				
				int dx = me.getX() - startPos.x;
				int dy = me.getY() - startPos.y;
				
				Rectangle newBounds = new Rectangle(); 
				
				switch ( cursor )
				{
					case Cursor.N_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY() + dy, 
													parentComponent.getWidth(), 
													parentComponent.getHeight()- dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.S_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentComponent.getWidth(), 
													parentComponent.getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.W_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx,
													(int)framePos.getY(), 
													parentComponent.getWidth() - dx, 
													parentComponent.getHeight());
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.E_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentComponent.getWidth() + dx, 
													parentComponent.getHeight());
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.NW_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx, 
													(int)framePos.getY() + dy, 
													parentComponent.getWidth() - dx, 
													parentComponent.getHeight() - dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.NE_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY() + dy, 
													parentComponent.getWidth() + dx, 
													parentComponent.getHeight() - dy);
						tryResize(newBounds);
						startPos = new Point(me.getX(), startPos.y);
						didResized();
						break;
						
					case Cursor.SW_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX() + dx, 
													(int)framePos.getY(), 
													parentComponent.getWidth() - dx, 
													parentComponent.getHeight() + dy);
						tryResize(newBounds);
						startPos = new Point(startPos.x, me.getY());
						didResized();
						break;
						
					case Cursor.SE_RESIZE_CURSOR :
						newBounds = new Rectangle(	(int)framePos.getX(), 
													(int)framePos.getY(), 
													parentComponent.getWidth() + dx, 
													parentComponent.getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.MOVE_CURSOR :						
		                Point loc = parentComponent.getLocationOnScreen();
		                loc.translate(dx, dy);
		                parentComponent.setLocation(loc.x, loc.y);
			            
						didResized();
				}
				
				// cursor shouldn't change while dragging
				setCursor(Cursor.getPredefinedCursor(cursor));
			}
			if(otherAdapter != null)
				otherAdapter.mouseDragged(me);
		}
		
		/**
		 * Vérifie que la nouvelle taille est "visible"
		 * @param newSize
		 */
		public void tryResize(Rectangle newSize)
		{
			if ((newSize.getWidth() > MINIMUM_WIDTH) && (newSize.getHeight() > MINIMUM_HEIGHT))
			{
				parentComponent.setBounds(newSize);
			}
		}

		public void mouseReleased(MouseEvent mouseEvent)
		{			
			startPos = null;
			
			if(otherAdapter != null)
				otherAdapter.mouseReleased(mouseEvent);
		}
		
		public void mouseEntered(MouseEvent arg0)
		{
			if(otherAdapter != null)
				otherAdapter.mouseEntered(arg0);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			if(otherAdapter != null)
				otherAdapter.mouseClicked(arg0);
		}
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0)
		{
			if(otherAdapter != null)
				otherAdapter.mouseWheelMoved(arg0);
		}
		
	};	
}
