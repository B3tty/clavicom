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

package clavicom.gui.keyboard.key.resizer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


public abstract class UIJResizer extends JComponent
{
	//--------------------------------------------------------- CONSTANTES --//
	final int MINIMUM_HEIGHT = 10;
	final int MINIMUM_WIDTH = 10;
	
	//---------------------------------------------------------- VARIABLES --//	
	private UISelectableBorder resizableBorder;	// Bordure lors du resize
	private boolean changeSelection;
	private boolean nowResizing;			

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIJResizer()
	{
		setLayout(new BorderLayout());
		resizableBorder = new UISelectableBorder(6);
		resizableBorder.setVisible(false);
	}
	
	//----------------------------------------------------------- METHODES --//	
	public boolean isEditable()
	{
		return resizableBorder.isVisible();
	}
	
	public void setEditable(boolean myIsResizable)
	{
		if(myIsResizable == true)
		{
			addMouseListener(resizeListener);
			addMouseMotionListener(resizeListener);
			
			super.setBorder(resizableBorder);	
		}
		else
		{
			removeMouseListener(resizeListener);
			removeMouseMotionListener(resizeListener);
		}
	}
	
	public void setSelected(boolean myIsSelected)
	{
		resizableBorder.setSelected(myIsSelected);
		invalidate();
	}
	
	public boolean isSelected()
	{
		return resizableBorder.isSelected();
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private void didResized()
	{
		if ( getParent() != null )
		{
			invalidate();
		}
	}
	
	protected abstract void onBoundsChanged();

	MouseInputListener resizeListener = new MouseInputAdapter()
	{
		public void mouseEntered(MouseEvent me)
		{
			resizableBorder.setVisible(true);
		}
		
		public void mouseMoved(MouseEvent me)
		{
			UIResizableBorder border = (UIResizableBorder) getBorder();
			setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me)));
		}

		public void mouseExited(MouseEvent mouseEvent)
		{
			setCursor(Cursor.getDefaultCursor());
			
			// On cache le border que s'il n'est pas selectionné
			if (resizableBorder.isSelected() == false)
			{
				resizableBorder.setVisible(false);
			}
		}

		private int cursor;

		private Point startPos = null;

		public void mousePressed(MouseEvent me)
		{
			UIResizableBorder border = (UIResizableBorder) getBorder();
			cursor = border.getResizeCursor(me);
			startPos = me.getPoint();
			
			changeSelection = true;
		}

		public void mouseDragged(MouseEvent me)
		{
			if ( startPos != null )
			{				
				int dx = me.getX() - startPos.x;
				int dy = me.getY() - startPos.y;
				
				Rectangle newBounds = new Rectangle(); 
				
				switch ( cursor )
				{
					case Cursor.N_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY() + dy, getWidth(), getHeight()- dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.S_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY(), getWidth(), getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.W_RESIZE_CURSOR :
						newBounds = new Rectangle(getX() + dx, getY(), getWidth() - dx, getHeight());
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.E_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY(), getWidth() + dx, getHeight());
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.NW_RESIZE_CURSOR :
						newBounds = new Rectangle(getX() + dx, getY() + dy, getWidth() - dx, getHeight() - dy);
						tryResize(newBounds);
						didResized();
						break;
						
					case Cursor.NE_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY() + dy, getWidth() + dx, getHeight() - dy);
						tryResize(newBounds);
						startPos = new Point(me.getX(), startPos.y);
						didResized();
						break;
						
					case Cursor.SW_RESIZE_CURSOR :
						newBounds = new Rectangle(getX() + dx, getY(), getWidth() - dx, getHeight() + dy);
						tryResize(newBounds);
						startPos = new Point(startPos.x, me.getY());
						didResized();
						break;
						
					case Cursor.SE_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY(), getWidth() + dx, getHeight() + dy);
						tryResize(newBounds);
						startPos = me.getPoint();
						didResized();
						break;
						
					case Cursor.MOVE_CURSOR :
						Rectangle bounds = getBounds();
						bounds.translate(dx, dy);
						setBounds(bounds);
						didResized();
				}
				
				// cursor shouldn't change while dragging
				setCursor(Cursor.getPredefinedCursor(cursor));
			}
			
			// On indique qu'on fait un drag et qu'on ne changera pas la selection
			// au relachement
			changeSelection = false;
		}
		
		/**
		 * Vérifie que la nouvelle taille est "visible"
		 * @param newSize
		 */
		public void tryResize(Rectangle newSize)
		{
			if ((newSize.getWidth() > MINIMUM_WIDTH) && (newSize.getHeight() > MINIMUM_HEIGHT))
			{
				setBounds(newSize);
			}
		}

		public void mouseReleased(MouseEvent mouseEvent)
		{
			startPos = null;
			
			// On indique que les dimensions ont changées
			onBoundsChanged();
			
			if (changeSelection == true)
			{
				setSelected(!isSelected());
			}
		}
	};
}
