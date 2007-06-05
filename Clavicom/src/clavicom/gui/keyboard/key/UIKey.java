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
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import clavicom.core.keygroup.CKey;
import clavicom.core.profil.CFont;
import clavicom.core.profil.CProfil;
import clavicom.tools.TUIKeySelectionState;
import clavicom.tools.TUIKeyState;

public abstract class UIKey extends JPanel implements ComponentListener
{
		//--------------------------------------------------------- CONSTANTES --//
		// Dessin
		final int TAILLE_BORDURE_INTERIEURE = 4;	// Taille de la bordure intérieure
		final int TAILLE_ARC = 25;					// Rayon de l'arrondi du bouton
		
		final int SHADOW_INSET_H = 1;				// Décallage horizontal de l'ombre du texte
		final int SHADOW_INSET_V = 1;				// Décallage vertical de l'ombre du texte
		
		final int RESIZE_TIMER_DURATION = 100;		// Durée au delà de laquelle le calcul des
													// images est lancé, pendant un resize	
		
		final int CAPTION_IMAGE_BORDER_RELATIVE = 0;	// Taille de la bordure de l'image
		
		final int CAPTION_IMAGE_BORDER_SIZE = 	CAPTION_IMAGE_BORDER_RELATIVE + // Taille de la bordure de l'image, tout compris
												TAILLE_BORDURE_INTERIEURE;
		
		//---------------------------------------------------------- ATTRIBUTS --//		
		private TUIKeyState state;					// Etat de la touche
		private TUIKeySelectionState selState;		// Etat de selection de la touche
		
		private MouseAdapterUse mouseAdapterUse;	// MouseAdapteur en mode utilisation
		private MouseAdapterEdit mouseAdapterEdit;	// MouseAdapteur en mode édition
		
		private BufferedImage imgNormal;			// Buffer de l'image normale
		private BufferedImage imgEntered;			// Buffer de l'image survolée
		private BufferedImage imgClicked;			// Buffer de l'image cliquée
		
		private BufferedImage currentImage;			// Buffer de l'image courante
		private Timer resizeTimer;					// Timer qui une fois expiré demande
													// le calcul des images
		
		private BufferedImage originalCaptionImage;	// Image correspondant à la caption
		
		private float opacity;						// Opacité du bouton (de 0 à 1)
		
		//---------------------------------------------------- CLASSES PRIVEES --//
		//-----------------------------------------------------------------------
		// Mère
		//-----------------------------------------------------------------------		
		private abstract class MouseAdapterKey extends MouseAdapter
		{	
			//----------------------------------------------------- METHODES --//			
			public void mouseEntered(MouseEvent e) 
			{
				mouseEnteredSpecific(e);
			}
			
			public void mouseExited(MouseEvent e) 
			{
				mouseExitedSpecific(e);
			}
			
			public void mousePressed(MouseEvent e) 
			{
				mousePressedSpecific(e);
			}
			
			public void mouseReleased(MouseEvent e) 
			{				
				mouseReleasedSpecific(e);
			}
			
			//-------------------------------------------- METHODES PRIVEES --//
			
			protected abstract void mouseEnteredSpecific(MouseEvent e);
			protected abstract void mouseExitedSpecific(MouseEvent e);
			protected abstract void mousePressedSpecific(MouseEvent e);
			protected abstract void mouseReleasedSpecific(MouseEvent e);
		}	
		
		//-----------------------------------------------------------------------
		// Utilisation
		//-----------------------------------------------------------------------		
		private class MouseAdapterUse extends MouseAdapterKey
		{
			//----------------------------------------------------- METHODES --//	
			public void mouseEnteredSpecific(MouseEvent e) 
			{
				buttonEnteredUse();
			}
			
			public void mouseExitedSpecific(MouseEvent e) 
			{
				buttonExitedUse();
			}
			
			public void mousePressedSpecific(MouseEvent e) 
			{
				buttonPressedUse();
			}
			
			public void mouseReleasedSpecific(MouseEvent e) 
			{
				buttonReleasedUse();
			}
			
			//-------------------------------------------- METHODES PRIVEES --//
		}	
		
		//-----------------------------------------------------------------------
		// Mode édition
		//-----------------------------------------------------------------------		
		private class MouseAdapterEdit extends MouseAdapterKey
		{
			public void mouseEnteredSpecific(MouseEvent e) 
			{
				
			}
			
			public void mouseExitedSpecific(MouseEvent e) 
			{
					
			}
			
			public void mousePressedSpecific(MouseEvent e) 
			{
					
			}
			
			public void mouseReleasedSpecific(MouseEvent e) 
			{
					
			}
		}	
		
		// ------------------------------------------------------ CONSTRUCTEURS --//
		public UIKey()
		{ 
			super();
			
			// Création des mouseAdapters
			mouseAdapterEdit = new MouseAdapterEdit();
			mouseAdapterUse = new MouseAdapterUse();
			
			// On définit la transparence
			setOpaque(false);
			opacity = 1-(float)CProfil.getInstance().getTransparency().getKeyTrancparencyPourcent() / 100;
			
			// Ajout des listener sur la souris
			addMouseListener(mouseAdapterUse);

			// Ajout en tant que listener de component
			// (pour le resize,...)
			addComponentListener(this);
			
			// Chargement de l'image si nécessaire
			if (getCoreKey().isCaptionImage() == true)
			{
				// On teste l'existence de l'image
				File fileImage = new File(getCaption());
				if (fileImage.exists() == false)
				{
					// A COMPLETER !!!
					System.out.println("fichier non existant...");
				}
				
				// Création de l'image icon
				ImageIcon iconImage = new ImageIcon(getCaption());	
				
				// Récupération de l'image
				originalCaptionImage = toBufferedImage(iconImage.getImage());
			}

			// Initialisation des états
			state = TUIKeyState.NORMAL;
			selState = TUIKeySelectionState.UNSELECTED;
			
			// Création du Timer resize
			resizeTimer = createResizeTimer();
		}
		
		//----------------------------------------------------------- METHODES --//	
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------	
		protected BufferedImage recreateNormalImage(Color bgdColor)
		{
			// Variables
			Graphics2D buffer;
			BufferedImage image;
			
			// Création de l'image
			image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
			buffer = (Graphics2D) image.getGraphics();
			
			//buffer.setBackground(Color.RED);
			
			// Construction du buffer
			buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			// Ajout du fond de la touche			
			addPaintBackground(buffer,bgdColor);
			
			// Ajout de la caption
			addPaintCaption(buffer,bgdColor);
			
			return image;
		}
		
		/**
		 * Appelé lors du redimensionnement du composant
		 */
		public void componentResized(ComponentEvent e)
		{			
			// On ettend l'image
			if (currentImage != null)
			{
				currentImage = toBufferedImage(((Image)currentImage).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
			}
			
			// On réarme le timer
			resizeTimer.restart();
		}
		
		public void componentHidden(ComponentEvent e)
		{
			// Rien à ajouter
		}
		
		public void componentMoved(ComponentEvent e)
		{
			// Rien à ajouter
		}
		
		public void componentShown(ComponentEvent e)
		{
			// Rien à ajouter
		}
		
		/**
		 * Recréé les buffers des images dans chacun des états
		 *
		 */
		protected void recreateNormalImages()
		{
			imgNormal = recreateNormalImage(getCoreKey().GetColorNormal().GetColor());
			imgEntered = recreateNormalImage(getCoreKey().GetColorEntered().GetColor());
			imgClicked = recreateNormalImage(getCoreKey().GetColorClicked().GetColor());
		}
		
		public void paintComponent(Graphics myGraphic)
		{
			super.paintComponent(myGraphic);
			try
			{
				// Appel du père
				super.paintComponent(myGraphic);
				
				// Récupération du Graphics2D
				Graphics2D g2 = (Graphics2D) myGraphic;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				// Application de la transparence
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
				
				// Dessin de l'image			
				g2.drawImage(currentImage, 0, 0, null);
			}
			catch (Exception ex)
			{
				// A COMPLETER
				ex.printStackTrace();
			}
		}
		
		//--------------------------------------------------- METHODES PRIVEES --//
		//-----------------------------------------------------------------------
		// Mode utilisation
		//-----------------------------------------------------------------------		
		protected void buttonEnteredUse()
		{
			state = TUIKeyState.ENTERED;
			selectCurrentStateImage();
			
			// On force le redessin
			repaint();
		}

		protected void buttonExitedUse()
		{
			state = TUIKeyState.NORMAL;
			selectCurrentStateImage();
			
			// On force le redessin
			repaint();
		}

		protected void buttonPressedUse()
		{
			state = TUIKeyState.PRESSED;
			
			getCoreKey().Click();
			selectCurrentStateImage();
			
			// On force le redessin
			repaint();
		}

		protected void buttonReleasedUse()
		{
			state = TUIKeyState.NORMAL;
			selectCurrentStateImage();
			
			// On force le redessin
			repaint();
		}
		
		//-----------------------------------------------------------------------
		// Mode édition
		//-----------------------------------------------------------------------	
		protected void buttonEnteredEdit()
		{
			// TODO
		}

		protected void buttonExitedEdit()
		{
			// TODO
		}

		protected void buttonPressedEdit()
		{
			// TODO	
		}

		protected void buttonReleasedEdit()
		{
			// TODO	
		}
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------	
		protected void addPaintBackground(Graphics2D bg, Color bgdColor)
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
			// On agrandit le clip (pour dessiner sur tout le bouton)
			bg.setClip(0,0,getWidth(), getHeight());
			
			// On regarde ce que l'on doit dessiner, image ou texte
			if (getCoreKey().isCaptionImage() == true)
			// Dessin de l'image
			{		
				// Calcul du facteur de réduction
				float scaleFactor = 1f;
				
				// Dimensions de l'image originale
				int imageWidth = originalCaptionImage.getWidth(null);
				int imageHeight = originalCaptionImage.getHeight(null);

				// Calcul de la "place" qu'il reste en largeur et en hauteur
				int widthPlace  = getWidth() - imageWidth;
				int heightPlace = getHeight() - imageHeight;
				
				if (widthPlace > heightPlace)
				// On redimensionne selon la HAUTEUR
				{
					scaleFactor = (float) getHeight() / (float)imageHeight;
				}
				else if (widthPlace < heightPlace)
				// On redimensionne selon la LARGEUR					
				{
					scaleFactor = (float)getWidth() / (float)imageWidth;
				}
				
				// Calcul des nouvelles dimensions
		        int newW = Math.round(imageWidth*scaleFactor) - 2*CAPTION_IMAGE_BORDER_SIZE;
		        int newH = Math.round(imageHeight*scaleFactor) - 2*CAPTION_IMAGE_BORDER_SIZE;
		        
		        // Calcul de la position de dessin
		        int xPosition = Math.round(((float)getWidth()/2) - ((float)newW/2));
		        int yPosition = Math.round(((float)getHeight()/2) - ((float)newH/2));
		        
		        // Dessin de l'image
		        bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		        
		        bg.drawImage(originalCaptionImage, xPosition, yPosition, newW, newH, null);
			}
			else
			// Dessin du texte
			{
				// Calcul des positions de dessin du texte
				FontMetrics fontMetrics = bg.getFontMetrics();
				int captionWidth= fontMetrics.stringWidth(getCaption());
				int captionHeight= fontMetrics.getHeight();
				
				int xPosition = (getWidth()/2) - (captionWidth/2);
				int yPosition = (getHeight()/2) + (captionHeight/3);
				
				// Récupération du CFont du profil
				CFont profilFont = CProfil.getInstance().getKeyboardFont();
				
				// On met la bonne police
				bg.setFont(profilFont.getUsedFont());

				// Ajout de l'ombre
				if (profilFont.isShadow())
				{
					bg.setColor(profilFont.getFontColor().GetColor().brighter().brighter().brighter());
					bg.drawString(	getCaption(),	
									xPosition + SHADOW_INSET_H,
									yPosition + SHADOW_INSET_V);
				}
				
				// On écrit le texte
				bg.setColor(profilFont.getFontColor().GetColor());
				bg.drawString(getCaption(),xPosition,yPosition);
			}
		}
		
		protected void selectCurrentStateImage()
		{
			if (currentImage == null)
			{
				recreateNormalImages();
			}
			
			if (state == TUIKeyState.ENTERED)
			{
				currentImage = imgEntered;
			}
			else if (state == TUIKeyState.NORMAL)
			{
				currentImage = imgNormal;
			}
			else if (state == TUIKeyState.PRESSED)
			{
				currentImage = imgClicked;
			}
		}
		
		protected Timer createResizeTimer()
		{
			// Création d'une instance de listener
			// associée au timer
			ActionListener action = new ActionListener()
			{
				// Méthode appelée à chaque tic du timer
				public void actionPerformed(ActionEvent event)
				{
					resizeTimer.stop();
					recreateNormalImages();
					selectCurrentStateImage();
					repaint();
				}
			};
	
			// Création d'un timer qui génère un tic
			// chaque 500 millième de seconde
			return new Timer(RESIZE_TIMER_DURATION,action);
		}
		
		/**
		 * Converti une Image en BufferedImage
		 * @param image
		 * @return
		 */
		BufferedImage toBufferedImage(Image image)
		{
			// On test si l'image n'est pas déja une instance de BufferedImage
			if ( image instanceof BufferedImage )
			{
				return ((BufferedImage) image);
			}
			else
			{
				// On s'assure que l'image est complètement chargée
				image = new ImageIcon(image).getImage();
	
				// On crée la nouvelle image
				BufferedImage bufferedImage = new BufferedImage(image
						.getWidth(null), image.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				Graphics g = bufferedImage.createGraphics();
				g.drawImage(image, 0, 0, null);
				g.dispose();
	
				return (bufferedImage);
			}
		}
		
		protected abstract CKey getCoreKey();
		protected abstract String getCaption();
}