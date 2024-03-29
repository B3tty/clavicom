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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;
import clavicom.CSettings;
import clavicom.core.keygroup.CKey;
import clavicom.core.listener.CKeyCaptionChangedListener;
import clavicom.core.listener.CKeyClickListener;
import clavicom.core.listener.CKeyColorChangedListener;
import clavicom.core.listener.CKeyMouseOverEventListener;
import clavicom.core.profil.CFont;
import clavicom.core.profil.CProfil;
import clavicom.gui.keyboard.key.resizer.UIJResizer;
import clavicom.gui.listener.KeyEnteredListener;
import clavicom.gui.listener.KeyPressedListener;
import clavicom.gui.listener.UIRightClickListener;
import clavicom.tools.TColorKeyEnum;
import clavicom.tools.TNavigationType;
import clavicom.tools.TSwingUtils;
import clavicom.tools.TUIKeyState;


public abstract class UIKey extends UIJResizer implements ComponentListener, CKeyColorChangedListener, CKeyCaptionChangedListener, CKeyClickListener
{
		//--------------------------------------------------------- CONSTANTES --//
		// Dessin
		final int TAILLE_BORDURE_INTERIEURE = 4;	// Taille de la bordure intérieure
		final int TAILLE_ARC = 24;					// Rayon de l'arrondi du bouton
		final int TAILLE_ARC_INTERNE = 18;			// Rayon de l'arrondi du bouton
		
		final int SHADOW_INSET_H = 2;				// Décallage horizontal de l'ombre du texte
		final int SHADOW_INSET_V = 2;				// Décallage vertical de l'ombre du texte
		
		final int RESIZE_TIMER_DURATION = 500;		// Durée au delà de laquelle le calcul des
													// images est lancé, pendant un resize	
		
		final int CAPTION_IMAGE_BORDER_RELATIVE = 1;	// Taille de la bordure de l'image
		
		final int CAPTION_IMAGE_BORDER_SIZE = 	CAPTION_IMAGE_BORDER_RELATIVE + // Taille de la bordure de l'image, tout compris
												TAILLE_BORDURE_INTERIEURE;
		
		final int TAILLE_CADRE_SELECTION = 10;		// Taille du cadre de selection
		
		final int TAILLE_CONTOUR = 1;				// Taille du contour
		final int TAILLE_ARC_CONTOUR = TAILLE_ARC - TAILLE_CONTOUR;
		
		final int DEFAULT_FONT_SIZE = 12;			// Taille par défaut de la police
		
		final int TEXT_CAPTION_OFFSET_X = 1;
		final int TEXT_CAPTION_OFFSET_Y = 1;
		
		//---------------------------------------------------------- ATTRIBUTS --//	
		
		private TUIKeyState state;					// Etat de la touche
		
		private MouseAdapterUse mouseAdapterUse;	// MouseAdapteur en mode utilisation
		private MouseAdapterEdit mouseAdapterEdit;	// MouseAdapteur en mode édition
		
		private BufferedImage imgNormal;			// Buffer de l'image normale
		private BufferedImage imgEntered;			// Buffer de l'image survolée
		private BufferedImage imgPressed;			// Buffer de l'image cliquée
		
		private BufferedImage imgNormalBackground;	// Buffer de fond de l'image normale
		private BufferedImage imgEnteredBackground;	// Buffer de fond de l'image survolée
		private BufferedImage imgPressedBackground;	// Buffer de fond de l'image cliquée
		
		private BufferedImage currentImage;			// Buffer de l'image courante
		
		private Timer resizeTimer;					// Timer qui une fois expiré demande
													// le calcul des images
		
		private BufferedImage originalCaptionImage;	// Image correspondant à la caption
		
		boolean editable;
		
		private int fontSize;						// Entier indiquant la taille de la police
													// IL NE S'AGIT PAS DU HEIGHT FACTOR DE LA 
													// CFont, mais de la vraie taille
		boolean clicked;
		boolean mouseExited;		
		
		// EventListeners
		protected EventListenerList keyEnteredListenerList;
		protected EventListenerList keyPressedListenerList;
		protected EventListenerList rightClikListenerList;
		protected EventListenerList mouseOverListenerList;
		
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
				// Si c'est pas le clic gauche, on annule
				if (!SwingUtilities.isLeftMouseButton(e))
	            {
	                return;
	            }
				
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
				// Is we are on the delayed click, we cancel
				if ( CSettings.getNavigation().getTypeNavigation() == TNavigationType.CLICK_TEMPORISE )
	            {
	                return;
	            }
				
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
				buttonEnteredEdit();
			}
			
			public void mouseExitedSpecific(MouseEvent e) 
			{
				buttonExitedEdit();
			}
			
			public void mousePressedSpecific(MouseEvent e) 
			{
				buttonPressedEdit();
			}
			
			public void mouseReleasedSpecific(MouseEvent e) 
			{
				buttonReleasedEdit(e);
			}
		}
		
		// ------------------------------------------------------ CONSTRUCTEURS --//
		public UIKey()
		{ 
			super();
			
			editable = false;
			
			// Initialisation des attributs
			fontSize = DEFAULT_FONT_SIZE;
			
			// Création des mouseAdapters
			mouseAdapterEdit = new MouseAdapterEdit();
			mouseAdapterUse = new MouseAdapterUse();
			
			// listener
			keyEnteredListenerList = new EventListenerList();
			keyPressedListenerList = new EventListenerList();
			rightClikListenerList = new EventListenerList();
			mouseOverListenerList = new EventListenerList();
			
			// Ajout en tant que listener de component
			// (pour le resize,...)
			addComponentListener(this);

			// Initialisation des états
			setState( TUIKeyState.NORMAL );
			selectGoodImage();
			
			setEditable(false);
			
			// Création du Timer resize
			resizeTimer = createResizeTimer();		
			resizeTimer.setRepeats(false);
			setLayout(new FlowLayout());
		}
		
		//----------------------------------------------------------- METHODES --//	

		//-----------------------------------------------------------------------
		// Listeners (en écouteur)
		//-----------------------------------------------------------------------
		public abstract CKey getCoreKey();
		
		public void colorChanged(TColorKeyEnum colorType)
		{
			// On recréé l'image buffer qui a été modifiée
			if (colorType == TColorKeyEnum.NORMAL)
			{
				imgNormal = recreateNormalImage(TUIKeyState.NORMAL, true);
			}
			else if (colorType == TColorKeyEnum.ENTERED)
			{
				imgEntered = recreateNormalImage(TUIKeyState.ENTERED, true);
			}
			else if (colorType == TColorKeyEnum.PRESSED)
			{
				imgPressed = recreateNormalImage(TUIKeyState.PRESSED, true);
			}
			
			// On reselectionne la bonne image
			selectGoodImage();
			
			// Redessin
			repaint();
		}

		public void captionChanged()
		{
			// On alerte les touches que la caption à changé
			alertCaptionChanged();
			
			// On recréé les images, car la caption a changé
			recreateNormalImages(false);
			
			// On reselectionne la bonne image
			selectGoodImage();
			
			// Redessin
			repaint();
		}
		
		//-----------------------------------------------------------------------
		// Selection
		//-----------------------------------------------------------------------
		
		public void setClicked( boolean isClicked )
		{
			clicked = isClicked;
			
			setState( TUIKeyState.NORMAL );
	
			selectGoodImage();
			
			// On force le redessin
			repaint();
		}
		
		public void setEditable(boolean myIsEditable)
		{
			// Activation du resizable
			super.setEditable(myIsEditable);
			
			// Deselection
			setSelected(false);
			
			editable = myIsEditable;
			
			// Changement du listener sur la souris
			if(myIsEditable == true)
			{				
				// Maj des listeners
				removeMouseListener(mouseAdapterEdit);
				removeMouseListener(mouseAdapterUse);
				
				addMouseListener(mouseAdapterEdit);
				
				setOpaque(false);
			}
			else
			{				
				// Maj des listeners
				removeMouseListener(mouseAdapterEdit);
				removeMouseListener(mouseAdapterUse);
				
				addMouseListener(mouseAdapterUse);
				
				setOpaque(true);
			}
		}
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------		
		/**
		 * Recréé l'image de fond de la couleur correspondante
		 */
		protected BufferedImage recreateNormalImage(TUIKeyState state, boolean recreateBackground)
		{
			if (getWidth() == 0 || getHeight() == 0)
			{
				return null;
			}
			
			// Couleur de fond
			Color bgdColor;
			
			if (state == TUIKeyState.NORMAL)
			{
				bgdColor = getCoreKey().getColor(TColorKeyEnum.NORMAL);
			}
			else if (state == TUIKeyState.ENTERED)
			{
				bgdColor = getCoreKey().getColor(TColorKeyEnum.ENTERED);
			}
			else
			{
				bgdColor = getCoreKey().getColor(TColorKeyEnum.PRESSED);
			}
			
			// Variables
			Graphics2D buffer;
			BufferedImage image;

			// Création de l'image
			image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
			buffer = (Graphics2D) image.getGraphics();

			// Construction du buffer
			buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			buffer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			
			// Ajout du fond de la touche			
			addPaintBackground(buffer, bgdColor, recreateBackground, state);
			
			// Ajout de la caption
			addPaintCaption(buffer,bgdColor);

			return image;
		}
		
		/**
		 * Appelé lors du redimensionnement du composant
		 */
		public void componentResized(ComponentEvent e)
		{
			if((!editable && !(this instanceof UIKeyCreation)) || getWidth() <= 0 || getHeight() <= 0)
				return;
			
			// On ettend l'image
			if (currentImage != null)
			{
				currentImage = TSwingUtils.toBufferedImage(((Image)currentImage).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
			}
			
			// On réarme le timer
			resizeTimer.restart();
		}
		
		public void componentHidden(ComponentEvent e)
		{
			// Rien à faire
		}
		
		public void componentMoved(ComponentEvent e)
		{
			// Rien à faire
		}
		
		public void componentShown(ComponentEvent e)
		{
			// Rien à faire
		}
		
		/**
		 * Recréé les buffers des images dans chacun des états
		 *
		 */
		protected void recreateNormalImages(boolean recreateBackground)
		{
			imgNormal = recreateNormalImage(TUIKeyState.NORMAL,recreateBackground);
			imgEntered = recreateNormalImage(TUIKeyState.ENTERED,recreateBackground);
			imgPressed = recreateNormalImage(TUIKeyState.PRESSED,recreateBackground);
		}
		
		/**
		 * Redessine la touche
		 */
		public void paintComponent(Graphics myGraphic)
		{			
			if (currentImage == null)
			{
				recreateNormalImages(true);
				selectGoodImage();
			}
			
			// Récupération du Graphics2D
			Graphics2D g2 = (Graphics2D) myGraphic;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			
			// Dessin			
			g2.drawImage(currentImage, 0, 0, null);
		}
		
		//-----------------------------------------------------------------------
		// Getters / Setters
		//-----------------------------------------------------------------------	

		public int getFontSize()
		{
			return fontSize;
		}

		public void setFontSize(int fontSize)
		{
			this.fontSize = fontSize;
		}		

		//--------------------------------------------------- METHODES PRIVEES --//
		/**
		 * Méthodes ou l'objet doit IMPERATIVEMENT s'abonner en tant que listener
		 * au prêt de TOUS les evenements, y compris ceux des classes mères
		 */
		protected abstract void addListeners();
		//-----------------------------------------------------------------------
		// Mode utilisation
		//-----------------------------------------------------------------------	
		protected void buttonEnteredUse()
		{
			setState( TUIKeyState.ENTERED );
			selectGoodImage();
			
			// On force le redessin
			repaint();
			
			// On alerte les objets attentifs à l'évenement
			fireMouseOverEntered();
			
			mouseExited = false;

		}

		protected void buttonExitedUse()
		{			
			// si la touche est holdable et dans l'état PRESSED
			// il ne faut pas changer d'état
			if( getCoreKey().isHoldable() && clicked )
			{
				setState( TUIKeyState.PRESSED );
			}
			else
			{
				setState( TUIKeyState.NORMAL );
			}
			selectGoodImage();
			
			// On force le redessin
			repaint();
			
			// On alerte les objets attentifs à l'évenement
			fireMouseOverLeft();
			
			mouseExited = true;
		}
		
		protected void buttonPressedUse()
		{
			setState( TUIKeyState.PRESSED );
			selectGoodImage();
			
			// On force le redessin
			repaint();
		}

		protected void buttonReleasedUse()
		{
			// Si la souris n'est pas sortie de la zone de la touche
			if( ! mouseExited )
			{
				// On avertit le noyau du clic
				clickCoreKey();
				
				// si la touche est holdable et dans l'état PRESSED
				// il ne faut pas changer d'état
				if( getCoreKey().isHoldable() && clicked )
				{
					clicked = false;
					return;
				}
	
				setState( TUIKeyState.ENTERED );
				selectGoodImage();
				
				if( clicked )
				{
					clicked = false;
				}
				else
				{
					clicked = true;
				}
				
				// On force le redessin
				repaint();
			}
		}
		
		protected void clickCoreKey()
		{
			getCoreKey().Click();
		}
		
		public void Click()
		{
			buttonPressedUse();
			buttonReleasedUse();			
		}

		//-----------------------------------------------------------------------
		// Mode édition
		//-----------------------------------------------------------------------	
		protected void buttonEnteredEdit()
		{
			setState( TUIKeyState.ENTERED );
			selectGoodImage();
			
			// On force le redessin
			repaint();
			
			mouseExited = false;
		}

		protected void buttonExitedEdit()
		{
			setState( TUIKeyState.NORMAL );
			selectGoodImage();
			
			// On force le redessin
			repaint();
			
			mouseExited = true;
		}

		protected void buttonPressedEdit()
		{					
			// On selectione la bonne image
			setState( TUIKeyState.PRESSED );
			selectGoodImage();
			
			// On force le redessin
			repaint();
		}

		protected void buttonReleasedEdit(MouseEvent mouseEvent)
		{		
			// Si la souris n'est pas sortie de la zone de la touche
			if( ! mouseExited )
			{
				// Si c'est un clic droit, on avertit qu'il a eu lieu
				if (SwingUtilities.isRightMouseButton(mouseEvent))
	            {
	                fireRightClickOccured(new Point((int)(mouseEvent.getPoint().getX() + getLocation().getX()),
	                								(int)(mouseEvent.getPoint().getY() + getLocation().getY())));
	            }
				
				setState( TUIKeyState.ENTERED );
				selectGoodImage();
				
				// On force le redessin
				repaint();
			}
		}
		
		//-----------------------------------------------------------------------
		// Dessin
		//-----------------------------------------------------------------------
		/**
		 * Dessine l'image de fond de la touche
		 */
		protected void addPaintBackground(Graphics2D bg, Color bgdColor, boolean recreateBackground, TUIKeyState state)
		{
			if(recreateBackground == false)
			{
				if (state == TUIKeyState.NORMAL)
				{
					bg.drawImage(imgNormalBackground,0,0,null);
				}
				else if (state == TUIKeyState.ENTERED)
				{
					bg.drawImage(imgEnteredBackground,0,0,null);
				}
				else
				{
					bg.drawImage(imgPressedBackground,0,0,null);
				}
				
				return;
			}
			
			BufferedImage actualBackground;
			

			if (state == TUIKeyState.NORMAL)
			{
				imgNormalBackground = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
				actualBackground = imgNormalBackground;
			}
			else if (state == TUIKeyState.ENTERED)
			{
				imgEnteredBackground = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
				actualBackground = imgEnteredBackground;
			}
			else
			{
				imgPressedBackground = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
				actualBackground = imgPressedBackground;
			}
			
			Graphics2D g2 = (Graphics2D) actualBackground.getGraphics();
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
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
			g2.setPaint(vPaint);

			// Dessin du premier Paint
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), TAILLE_ARC, TAILLE_ARC);
			
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
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.8f));
			g2.setPaint(vPaint);
			g2.setClip(new RoundRectangle2D.Float(	TAILLE_BORDURE_INTERIEURE,
													TAILLE_BORDURE_INTERIEURE,
													vButtonHighlightWidth,
													vButtonHighlightHeight/2, 
													TAILLE_ARC_INTERNE,TAILLE_ARC_INTERNE));
			
			g2.fillRoundRect(	TAILLE_BORDURE_INTERIEURE,
								TAILLE_BORDURE_INTERIEURE,
								vButtonHighlightWidth,
								vButtonHighlightHeight,
								TAILLE_ARC_INTERNE,TAILLE_ARC_INTERNE);
			
			// Dessin du contour
			g2.setColor(bgdColor.darker());
			g2.setStroke(new BasicStroke(TAILLE_CONTOUR));
			
			g2.setClip(0, 0, getWidth(), getHeight());
			
			g2.drawRoundRect(	TAILLE_CONTOUR/2, 
								TAILLE_CONTOUR/2, 
								getWidth()-TAILLE_CONTOUR, 
								getHeight()-TAILLE_CONTOUR,
								TAILLE_ARC_CONTOUR,TAILLE_ARC_CONTOUR);
			
			// Dessin
			bg.drawImage(actualBackground,0,0,null);			
		}
		
		/**
		 * Méthode appelée lorsque les dimensions de la touche ont changé
		 *
		 */
		public void boundsUpdated()
		{
			recreateNormalImages(true);
			selectGoodImage();
		}
		
		/**
		 * Dessine la caption sur la touche (image ou texte)
		 * @param bg
		 * @param bgdColor
		 */
		protected void addPaintCaption(Graphics2D bg, Color bgdColor)
		{
			// On agrandit le clip (pour dessiner sur tout le bouton)
			bg.setClip( 0,0,getWidth(), getHeight() );
			
			// On regarde ce que l'on doit dessiner, image ou texte
			if (getCoreKey().isCaptionImage() == true)
			// Dessin de l'image
			{
				originalCaptionImage = getCaptionImage();
				if( originalCaptionImage == null )
				{
					return;
				}

				// Calcul du facteur de réduction
				float scaleFactor = 1f;
				
				// Dimensions de l'image originale
				int imageWidth = originalCaptionImage.getWidth(null);
				int imageHeight = originalCaptionImage.getHeight(null);

				// Calcul de la "place" qu'il reste en largeur et en hauteur
				int widthPlace  = getWidth() - imageWidth;
				int heightPlace = getHeight() - imageHeight;

				if (widthPlace >= heightPlace)
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
		        bg.drawImage(originalCaptionImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), xPosition,yPosition,null);
			}
			else
			// Dessin du texte
			{
				// Récupération du CFont du profil
				CFont profilFont = CProfil.getInstance().getKeyboardFont();

				// Création du style de police
				int fontStyle = Font.PLAIN;
				
				if(profilFont.isBold())
				{
					fontStyle += Font.BOLD;
				}

				if(profilFont.isItalic())
				{
					fontStyle += Font.ITALIC;
				}
				
				// Création de la bonne police
				Font captionFont = new Font(	profilFont.getFontName(), 
												fontStyle,
												fontSize);

				// Application de la font
				bg.setFont(captionFont);
				
				// Calcul des positions de dessin du texte
				FontMetrics fontMetrics = bg.getFontMetrics();
				int captionWidth= fontMetrics.stringWidth(getCaptionText());
				int captionHeight= fontMetrics.getAscent();
				
				int xPosition = (getWidth()/2) - (captionWidth/2) - TEXT_CAPTION_OFFSET_X;
				int yPosition = (getHeight()/2) + (captionHeight/3) - TEXT_CAPTION_OFFSET_Y;

				// Ajout de l'ombre
				if (profilFont.isShadow())
				{
					bg.setColor(profilFont.getFontColor().getColor().brighter().brighter().brighter());
					bg.drawString(	getCaptionText(),	
									xPosition + SHADOW_INSET_H,
									yPosition + SHADOW_INSET_V);
				}
				
				// On écrit le texte
				bg.setColor(profilFont.getFontColor().getColor());
				
				bg.scale(1.1, 1.1);
				bg.drawString(getCaptionText(),xPosition,yPosition);
				
				
			}
		}
		
		// s'abonne au mouse listeneur
		public void listenMouseListener( boolean b )
		{
							
			// Maj des listeners
			removeMouseListener(mouseAdapterEdit);
			removeMouseListener(mouseAdapterUse);
				
			if(editable == true)
			{
				addMouseListener(mouseAdapterEdit);
			}
			else
			{
				addMouseListener(mouseAdapterUse);			
			}
		}
		
		/**
		 * Selectionne la bonne image courante
		 *
		 */
		public void selectGoodImage()
		{
			if ( getState() == TUIKeyState.ENTERED)
			{
				currentImage = imgEntered;
			}
			else if ( getState() == TUIKeyState.NORMAL)
			{
				currentImage = imgNormal;
			}
			else if ( getState() == TUIKeyState.PRESSED)
			{
				currentImage = imgPressed;
			}
		}
		
		/**
		 * Force l'état de la touche
		 * @param myState
		 */
		public void forceState(TUIKeyState myState)
		{
			setState( myState );
			selectGoodImage();
			repaint();
		}
		
		/**
		 * Simule l'appui sur la touche
		 *
		 */
		public void simulateClick()
		{
			getCoreKey().Click();
		}
		
		
		public TUIKeyState getState()
		{
			return state;
		}

		public void setState(TUIKeyState state)
		{
			TUIKeyState stateTmp = this.state;
			this.state = state;
			
			if( state == TUIKeyState.ENTERED )
			{
				// si l'ancienne n'était pas PRESSED
				if( stateTmp != TUIKeyState.PRESSED )
				{
					fireKeyEnteredCharacter();
				}
			}
			if( state == TUIKeyState.PRESSED )
			{
				fireKeyPressedCharacter();
			}
		}
		

		/**
		 * Créé un Timer de redimension
		 * @return
		 */
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
					recreateNormalImages(true);
					selectGoodImage();
					repaint();
				}
			};
	
			// Création d'un timer qui génère un tic
			// chaque 500 millième de seconde
			return new Timer(RESIZE_TIMER_DURATION,action);
		}
		
		protected BufferedImage loadCaptionImage(String filePath)
		{
			BufferedImage image;
			
			// Récupération de l'image
			image = TSwingUtils.toBufferedImage(TSwingUtils.getImage(filePath).getImage());
			
			return image;
		}
		
		protected abstract void alertCaptionChanged();
		
		protected abstract String getCaptionText();
		protected abstract BufferedImage getCaptionImage();
		
		
		
		
		
		// Listener key Entered et Pressed ==============================================
		
		public void addKeyEnteredListener(KeyEnteredListener l)
		{
			this.keyEnteredListenerList.add(KeyEnteredListener.class, l);
		}
		
		public void addKeyPressedListener(KeyPressedListener l)
		{
			this.keyPressedListenerList.add(KeyPressedListener.class, l);
		}
		
		public void addRightClickListener(UIRightClickListener l)
		{
			this.rightClikListenerList.add(UIRightClickListener.class, l);
		}

		public void removeKeyEnteredListener(KeyEnteredListener l)
		{
			this.keyEnteredListenerList.remove(KeyEnteredListener.class, l);
		}
		
		public void removeKeyPressedListener(KeyPressedListener l)
		{
			this.keyPressedListenerList.remove(KeyPressedListener.class, l);
		}
		
		public void removeRightClickListener(UIRightClickListener l)
		{
			this.rightClikListenerList.remove(UIRightClickListener.class, l);
		}
		
		public void addMouseOverListener(CKeyMouseOverEventListener l )
		{
			this.mouseOverListenerList.add(CKeyMouseOverEventListener.class, l);
		}

		public void removeMouseOverListener(CKeyMouseOverEventListener l)
		{
			this.mouseOverListenerList.remove(CKeyMouseOverEventListener.class, l);
		}

		protected void fireKeyEnteredCharacter()
		{
			KeyEnteredListener[] listeners = (KeyEnteredListener[]) keyEnteredListenerList
					.getListeners(KeyEnteredListener.class);
			for ( int i = listeners.length - 1; i >= 0; i-- )
			{
				listeners[i].keyEntered();
			}
		}
		
		protected void fireKeyPressedCharacter()
		{
			KeyPressedListener[] listeners = (KeyPressedListener[]) keyPressedListenerList
					.getListeners(KeyPressedListener.class);
			for ( int i = listeners.length - 1; i >= 0; i-- )
			{
				listeners[i].keyPressed();
			}
		}
		
		protected void fireRightClickOccured(Point location)
		{
			UIRightClickListener[] listeners = (UIRightClickListener[]) rightClikListenerList
					.getListeners(UIRightClickListener.class);
			for ( int i = listeners.length - 1; i >= 0; i-- )
			{
				listeners[i].rightClickOccured(location);
			}
		}
		
		protected void fireMouseOverEntered()
		{
			CKeyMouseOverEventListener[] listeners = (CKeyMouseOverEventListener[]) mouseOverListenerList
					.getListeners(CKeyMouseOverEventListener.class);
			for ( int i = listeners.length - 1; i >= 0; i-- )
			{
				listeners[i].mouseEntered(this);
			}
		}
		
		protected void fireMouseOverLeft()
		{
			CKeyMouseOverEventListener[] listeners = (CKeyMouseOverEventListener[]) mouseOverListenerList
					.getListeners(CKeyMouseOverEventListener.class);
			for ( int i = listeners.length - 1; i >= 0; i-- )
			{
				listeners[i].mouseLeft();
			}
		}

		// fin Listener ============================================
}