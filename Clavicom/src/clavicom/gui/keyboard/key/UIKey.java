/*-----------------------------------------------------------------------------+

			Filename			: UIKey.java
			Creation date		: 29 mai 07
		
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

+-----------------------------------------------------------------------------*/

package clavicom.gui.keyboard.key;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TUIKeyState;

public abstract class UIKey extends JPanel
{
		//--------------------------------------------------------- CONSTANTES --//

		//---------------------------------------------------------- ATTRIBUTS --//		
		private EventListenerList listenersList;	// Elements abonnés
		
		private TUIKeyState currentState;			// Etat de la touche
		//------------------------------------------------------ CONSTRUCTEURS --//
		
		public UIKey()
		{
			this.listenersList = new EventListenerList();
			 
			// Ajout des listener sur la souris
			addMouseListener(	new MouseAdapter() 
			{
				public void mouseEntered(MouseEvent e) 
				{
						fireButtonEntered();
				}
			});
			
			addMouseListener(	new MouseAdapter() 
			{
				public void mouseExited(MouseEvent e) 
				{
						fireButtonExited();
				}
			});
			
			addMouseListener(	new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e) 
				{
						fireButtonPressed();
				}
			});

			addMouseListener(	new MouseAdapter() 
			{
				public void mouseReleased(MouseEvent e) 
				{
						fireButtonReleased();
				}
			});
		}
		
		//----------------------------------------------------------- METHODES --//
		//-----------------------------------------------------------------------
		// Gestion des listeners
		//-----------------------------------------------------------------------
		public void addUIKeyListener(UIKeyListener listener)
		{
			listenersList.add(UIKeyListener.class, listener);
		}
	
		public void removeUIKeyListener(UIKeyListener listener)
		{
			listenersList.remove(UIKeyListener.class, listener);
		}		
		//-----------------------------------------------------------------------
		// Méthodes d'interface
		//-----------------------------------------------------------------------
		public TUIKeyState getState()
		{
			return currentState;
		}

		public void setState(TUIKeyState currentState)
		{
			this.currentState = currentState;
		}
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------		
		protected void paintComponent(Graphics myGraphic)
		{
			try
			{
				// Dessin de la touche
				paintBackground(myGraphic);
				
				// Dessin de la caption
				paintCaption(myGraphic);
			}
			catch (Exception ex)
			{
				// A COMPLETER
			}

//			System.out.println("paintComponent");
//	
//			Graphics2D graphic2D = (Graphics2D) myGraphic;
//	
//			graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_ON);
//	
//			graphic2D.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,
//					BasicStroke.JOIN_ROUND));
//	
//			// Contour
//			graphic2D.setStroke(new BasicStroke(5f));
//	
//			if ( currentState == TUIKeyState.NORMAL )
//			{
//				graphic2D.setColor(Color.BLACK);
//			}
//			else if ( currentState == TUIKeyState.SELECTED )
//			{
//				graphic2D.setColor(Color.BLUE);
//			}
//			else if ( currentState == TUIKeyState.PRESSED )
//			{
//				graphic2D.setColor(Color.RED);
//			}
//	
//			graphic2D.drawRect(0, 0, getWidth(), getHeight());
		}
		
		//--------------------------------------------------- METHODES PRIVEES --//
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------
		protected void paintBackground(Graphics myGraphic) throws Exception
		{
			// Récupération de la couleur de fond
			Color bgdColor = null;
			try
			{
				if (currentState == TUIKeyState.NORMAL)
				{
					bgdColor = getCoreKey().GetColorNormal().GetColor();
				}
				else if (currentState == TUIKeyState.PRESSED)
				{
					bgdColor = getCoreKey().GetColorClicked().GetColor();
				}
				else if (currentState == TUIKeyState.SELECTED)
				{
					bgdColor = getCoreKey().GetColorEntered().GetColor();
				}
			}
			catch (Exception ex)
			{
				throw new Exception(	UIString.getUIString("EX_UIKEY_COLOR_ERROR") + 
										ex.getMessage());
			}
			
			// Récupération du graphic
			Graphics2D graphic2D = (Graphics2D) myGraphic;
			
			// Couleur de fond
			graphic2D.setPaint(new GradientPaint(0,0, bgdColor, getWidth(),getHeight(),Color.WHITE,true));
			graphic2D.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
	
			// Contour
			graphic2D.setColor(bgdColor);
			graphic2D.setStroke(new BasicStroke(10f));
			graphic2D.drawRoundRect(0, 0, getWidth(), getHeight(),20,20);
			
//			graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//					RenderingHints.VALUE_ANTIALIAS_ON);
//	
//			graphic2D.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,
//					BasicStroke.JOIN_ROUND));
//	
//			// Contour
//			graphic2D.setStroke(new BasicStroke(50f));
//
//			graphic2D.setColor(bgdColor);
//	
//			graphic2D.drawRect(0, 0, getWidth(), getHeight());
			
		}
		
		protected void paintCaption(Graphics myGraphic)
		{
			// A COMPLETER
		}
		
		//-----------------------------------------------------------------------
		// Gestion des listeners
		//-----------------------------------------------------------------------
		protected UIKeyListener[] getButtonListeners()
		{
			return listenersList.getListeners(UIKeyListener.class);
		}
	
		protected void fireButtonEntered()
		{
			Object[] listeners = listenersList.getListenerList();
	
			for ( int i = listeners.length - 2; i >= 0; i -= 2 )
				if ( listeners[i] == UIKeyListener.class )
					((UIKeyListener) listeners[i + 1]).buttonEntered(this);
		}
		
		protected void fireButtonExited()
		{
			Object[] listeners = listenersList.getListenerList();
	
			for ( int i = listeners.length - 2; i >= 0; i -= 2 )
				if ( listeners[i] == UIKeyListener.class )
					((UIKeyListener) listeners[i + 1]).buttonExited(this);
		}
		
		protected void fireButtonPressed()
		{
			Object[] listeners = listenersList.getListenerList();
	
			for ( int i = listeners.length - 2; i >= 0; i -= 2 )
				if ( listeners[i] == UIKeyListener.class )
					((UIKeyListener) listeners[i + 1]).buttonPressed(this);
		}
		
		protected void fireButtonReleased()
		{
			Object[] listeners = listenersList.getListenerList();
	
			for ( int i = listeners.length - 2; i >= 0; i -= 2 )
				if ( listeners[i] == UIKeyListener.class )
					((UIKeyListener) listeners[i + 1]).buttonReleased(this);
		}

		protected abstract CKey getCoreKey();
}


