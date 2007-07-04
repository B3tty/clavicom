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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import clavicom.gui.keyboard.keyboard.UIMagnetGrid;
import clavicom.gui.listener.UIKeySelectionListener;


public abstract class UIJResizer extends JComponent
{
	//--------------------------------------------------------- CONSTANTES --//
	final int MINIMUM_HEIGHT = 10;
	final int MINIMUM_WIDTH = 10;
	final int INSET_SIZE = 5;
	
	//---------------------------------------------------------- VARIABLES --//	
	protected UISelectableBorder resizableBorder;	// Bordure lors du resize
	protected boolean changeSelection;		
	protected EventListenerList listeners;
	
	protected UIMagnetGrid magnetGrid;				// Grille aimantée 

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIJResizer()
	{
		setLayout(new BorderLayout());
		resizableBorder = new UISelectableBorder(INSET_SIZE);
		resizableBorder.setVisible(false);
		
		listeners = new EventListenerList();
	}
	
	/**
	 * Affecte la magnetGrid
	 * @param magnetGrid
	 */
	public void setMagnetGrid(UIMagnetGrid magnetGrid)
	{
		this.magnetGrid = magnetGrid; 
	}
	
	public Point getNearestCorner (Point actualPoint)
	{
		// Création de la liste
		List<Point> allPoints = new ArrayList<Point>();
		
		// Ajout des points
		int midWidth = getWidth()/2;
		int midHeight = getHeight()/2;
		
		allPoints.add(new Point(0,0));						// Nord Ouest
		allPoints.add(new Point(midWidth,0));				// Nord
		allPoints.add(new Point(getWidth(),0));				// Nord Est
		allPoints.add(new Point(getWidth(),midHeight));		// Est
		allPoints.add(new Point(getWidth(),getHeight()));	// Sud Est
		allPoints.add(new Point(midWidth,getHeight()));		// Sud
		allPoints.add(new Point(0,getHeight()));			// Sud Ouest
		allPoints.add(new Point(0,midHeight));			// Ouest
		
		// Recherche du point le plus près
		double minDistance = midWidth + midHeight;
		double actualDistance;
		
		Point nearestPoint = null;
		for (Point currentPoint : allPoints)
		{
			actualDistance = actualPoint.distance(currentPoint);
			
			if(actualDistance < minDistance)
			{
				nearestPoint = currentPoint;
				minDistance = actualDistance;
			}
		}
		
		// Retour
		return nearestPoint;
	}
	
	//----------------------------------------------------------- METHODES --//	
	//-----------------------------------------------------------------------
	// Listeners (en générateur)
	//-----------------------------------------------------------------------
	public void addSelectionListener(UIKeySelectionListener listener) 
	{
        listeners.add(UIKeySelectionListener.class, listener);
    }
    
    public void removeSelectionListener(UIKeySelectionListener listener) 
    {
        listeners.remove(UIKeySelectionListener.class, listener);
    }
    
    public UIKeySelectionListener[] getSelectionListeners() 
    {
        return listeners.getListeners(UIKeySelectionListener.class);
    }
    
	//-----------------------------------------------------------------------
	// Divers
	//-----------------------------------------------------------------------  
	public boolean isEditable()
	{
		return resizableBorder.isVisible();
	}
	
	public void setEditable(boolean myIsResizable)
	{	
		if(myIsResizable == true)
		{
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
	
	public void setSelected(boolean myIsSelected)
	{
		// Génération des evenements
		if(myIsSelected == true)
		{
			fireKeySelected();
		}
		else
		{
			fireKeyUnselected();
		}
		
		resizableBorder.setVisible(myIsSelected);
		resizableBorder.setSelected(myIsSelected);
		
		// On redessine la key
		repaint();
	}
	
	public void eraseBorder()
	{
		resizableBorder.setVisible(false);
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
	
	public abstract void onBoundsChanged();
	
	MouseInputListener resizeMouseListener = new MouseInputAdapter()
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
			if (isSelected() == false)
			{
				resizableBorder.setVisible(false);
			}
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
			if ( startPos != null )
			{							
				Point mousePoint = null;				// Position de la souris
				Point mousePointParent = null;			// Position de la souris dans le repère du père
				Point gridPointParent = null;			// Position du coin de grille le plus près du curseur
				Point mouseNearestCorner = null;		// Position du coin le plus près de la souris
				Point mouseNearestCornerParent = null;  // Position du coin le plus près de la souris dans le repère du père
				
				int diffX, diffY;
				int dx = 0;
				int dy = 0;
				
				mousePoint = me.getPoint();
				mouseNearestCorner = getNearestCorner(mousePoint);
				
				if(magnetGrid != null)
				// Gestion avec les grilles
				{ 
					// Récupération de la souris dans les coordonnées du père
					mousePointParent = getParent().getMousePosition();

					// Récupération du point de la grille le plus proche
					gridPointParent = magnetGrid.getNearestPoint(mousePointParent);
					
					// Récupération des coordonnées du point le plus proche dans les coordonnées du père
					mouseNearestCornerParent = new Point((int)(mouseNearestCorner.getX() + getLocation().getX()),
							 							 (int)(mouseNearestCorner.getY() + getLocation().getY())); 
					
					// Calcul des différences (vecteur de déplacement ou d'agrandissement
					diffX = (int)(gridPointParent.getX() - mouseNearestCornerParent.getX());
					diffY = (int)(gridPointParent.getY() - mouseNearestCornerParent.getY());
					
					dx = diffX;
					dy = diffY;
				}
				else
				{
					dx = (int)mouseNearestCorner.getX() - startPos.x;
					dy = (int)mouseNearestCorner.getY() - startPos.y;
				}
				
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
						startPos = mouseNearestCorner;
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
						startPos = mouseNearestCorner;
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
						startPos = new Point((int)mouseNearestCorner.getX(), startPos.y);
						didResized();
						break;
						
					case Cursor.SW_RESIZE_CURSOR :
						newBounds = new Rectangle(getX() + dx, getY(), getWidth() - dx, getHeight() + dy);
						tryResize(newBounds);
						startPos = new Point(startPos.x, (int)mouseNearestCorner.getY());
						didResized();
						break;
						
					case Cursor.SE_RESIZE_CURSOR :
						newBounds = new Rectangle(getX(), getY(), getWidth() + dx, getHeight() + dy);
						tryResize(newBounds);
						startPos = mouseNearestCorner;
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
			// On indique que les dimensions ont changées
			onBoundsChanged();
			
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
			
			if (changeSelection == true)
			{
				setSelected(!isSelected());
			}
		}
	};
	
	
	//-----------------------------------------------------------------------
	// Listeners (en générateur)
	//-----------------------------------------------------------------------	    
    protected void fireKeySelected() 
    {
	    for ( UIKeySelectionListener listener : getSelectionListeners() )
		{
			listener.keySelected(this);
		}
    }
    
    protected void fireKeyUnselected() 
    {
	    for ( UIKeySelectionListener listener : getSelectionListeners() )
		{
			listener.keyUnselected(this);
		}
    }
}
