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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TUIKeyState;

public abstract class UIKey extends JPanel
{
		//--------------------------------------------------------- CONSTANTES --//
//		private float[] BLUR = {0.10f, 0.10f, 0.10f, 0.10f, 0.30f, 0.10f, 0.10f, 0.10f, 0.10f};
		
		final int TAILLE_BORDURE_INTERIEURE = 4;
		final int TAILLE_BORDURE_EXTERIEURE = 3;
		final int TAILLE_ARC = 25;
		
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
			
			currentState = TUIKeyState.NORMAL;
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
		
		public void alertCoreKey()
		{
			getCoreKey().Click();
		}
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------		
		protected void paintComponent(Graphics myGraphic)
		{
			try
			{
				super.paintComponent(myGraphic);
				
				// Construction du buffer
				BufferedImage vBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D bg = vBuffer.createGraphics();
				bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
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
				
				// Ajout du fond de la touche
				addPaintBackground(bg,bgdColor);
				
				// Ajout de la caption
				addPaintCaption(bg,bgdColor);
				
				// Dessin
				Graphics2D g2 = (Graphics2D) myGraphic;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(vBuffer, 0, 0, null);	
			}
			catch (Exception ex)
			{
				// A COMPLETER
				ex.printStackTrace();
			}
		}
		
		//--------------------------------------------------- METHODES PRIVEES --//
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------
		protected void addPaintBackground(Graphics2D bg, Color bgdColor) throws Exception
		{						
			// Création du Paint du premier calque
			Color vGradientStartColor, vGradientEndColor;
			vGradientStartColor =  bgdColor.brighter();
			vGradientEndColor = bgdColor;				

			Paint vPaint = new GradientPaint(	0, 
												0, 
												vGradientStartColor, 
												0, 
												getHeight(), 
												vGradientEndColor, 
												true);
			bg.setPaint(vPaint);

			// Dessin du premier Paint
			bg.fillRoundRect(0, 0, getWidth(), getHeight(), TAILLE_ARC, TAILLE_ARC);
			
			// Taille du second Layer
			int vButtonHighlightHeight = getHeight() - (TAILLE_BORDURE_INTERIEURE * 2);
			int vButtonHighlightWidth = getWidth() - (TAILLE_BORDURE_INTERIEURE * 2);

			// Création du Paint du second calque
			vGradientStartColor = bgdColor.brighter().brighter();
			vGradientEndColor = bgdColor.brighter();				
			
			vPaint = new GradientPaint(	0,
										TAILLE_BORDURE_INTERIEURE,
										vGradientStartColor,
										0,
										TAILLE_BORDURE_INTERIEURE+(vButtonHighlightHeight/2), 
										vGradientEndColor, 
										false);

			// Dessin du second Paint
			bg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.8f));
			bg.setPaint(vPaint);
			bg.setClip(new RoundRectangle2D.Float(	TAILLE_BORDURE_INTERIEURE,
													TAILLE_BORDURE_INTERIEURE,
													vButtonHighlightWidth,
													vButtonHighlightHeight /2, 
													TAILLE_ARC,TAILLE_ARC));
			
			bg.fillRoundRect(	TAILLE_BORDURE_INTERIEURE,
								TAILLE_BORDURE_INTERIEURE,
								vButtonHighlightWidth,
								vButtonHighlightHeight,
								TAILLE_ARC,TAILLE_ARC);	
		}
		
		protected void addPaintCaption(Graphics2D bg, Color bgdColor)
		{
			if (getCoreKey().isCaptionImage() == true)
			// Dessin de l'image
			{
				
			}
			else
			// Dessin du texte
			{
				// Récupération de la caption
				String caption = getCaption();
				
				// On agrandit le clip
				bg.setClip(0,0,getWidth(), getHeight());
				
				// On met la couleur et la bonne police
				bg.setFont(new Font("Arial",Font.BOLD,32));
				bg.setColor(bgdColor.brighter());
				
				// Calcul de la taille que va prendre le texte à s'écrire
				// pour calculer la position de dessin
				FontMetrics fontMetrics = bg.getFontMetrics();
				int captionWidth = fontMetrics.stringWidth(getCaption());
				int captionHeight = fontMetrics.getHeight();
				
				int xPosition = (getWidth()/2) - (captionWidth/2);
				int yPosition = (getHeight()/2) + (captionHeight/2);
				
				// On écrit le texte
				bg.drawString(caption,xPosition,yPosition);
			}
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
		protected abstract String getCaption();
}


