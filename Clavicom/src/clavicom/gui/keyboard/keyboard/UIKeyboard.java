/*-----------------------------------------------------------------------------+

			Filename			: UIKeyboard.java
			Creation date		: 7 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.keyboard

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

package clavicom.gui.keyboard.keyboard;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;
import clavicom.gui.keyboard.key.resizer.UIJResizer;
import clavicom.gui.listener.UIKeySelectionListener;
import clavicom.tools.TImageUtils;
import clavicom.tools.TPoint;

public class UIKeyboard extends JPanel implements ComponentListener, UIKeySelectionListener
{
	//--------------------------------------------------------- CONSTANTES --//
	final int TAILLE_ARC = 25;					// Rayon de l'arrondi du fond
	
	final int TAILLE_CONTOUR = 3;				// Taille du contour
	final int TAILLE_ARC_CONTOUR = TAILLE_ARC - TAILLE_CONTOUR;
	
	final int RESIZE_TIMER_DURATION = 100;		// Durée au delà de laquelle le calcul des
												// images est lancé, pendant un resize	
	
	final int NORMAL_TRANSLATION_STEP = 10;
	final int FINE_TRANSLATION_STEP = 1;
	
	final float FONT_REDUCTION_FACTOR = .2f;

	//---------------------------------------------------------- VARIABLES --//	
	private List<UIKeyGroup> keyGroups;				// Liste des UIKeyGroups
	private List<UIKeyKeyboard> allKeys;			// Liste des keys
	private List<UIKeyThreeLevel> threeLevelKeys;	// Liste des ThreeLevelKeys
	private List<UIKeyKeyboard> selectedKeys;			// Liste des key selectionnées
	
	private float opacity;
	
	private BufferedImage imgBackground;			// Buffer de l'image cliquée
	
	private Timer resizeTimer;					// Timer qui une fois expiré demande
												// le calcul des images
	
	private boolean isEdited;					// Indique si le clavier est en edition
	private CKeyboard coreKeyboard;				// Element du noyau
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	/**
	 * Créé l'UIKeyboard à partir du CKeyboard
	 */
	public UIKeyboard(CKeyboard myCoreKeyboard)
	{
		// Initialisation des attributs
		coreKeyboard = myCoreKeyboard;
		keyGroups = new ArrayList<UIKeyGroup>();
		allKeys = new ArrayList<UIKeyKeyboard>();
		threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		selectedKeys = new ArrayList<UIKeyKeyboard>();
		
		// Récupération du nombre de groupes 
		int groupCount = coreKeyboard.groupCount();
		
		// On se met focusable
		setFocusable(true);
		requestFocus();
		
		// Par défaut on n'est pas en édition
		isEdited = false;
		
		// Récupération de l'opacité
		opacity = 1-(float)CProfil.getInstance().getTransparency().getKeyboardTransparencyPourcent() / 100;
		
		// Variables temporaires
		UIKeyGroup currentKeyGroup;
		List<UIKeyThreeLevel> currentThreeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		List<UIKeyKeyboard> currentKeys = new ArrayList<UIKeyKeyboard>();
		
		// On parcours tous les groupes
		for (int i = 0 ; i < groupCount ; ++i)
		{
			// Création du UIKeyGroup
			currentKeyGroup = new UIKeyGroup (coreKeyboard.getKeyGroup(i));
			
			// Ajout au KeyGroups
			keyGroups.add(currentKeyGroup);
			
			// Demande de récupération des ThreeLevelKeys
			currentThreeLevelKeys.clear();
			currentThreeLevelKeys = currentKeyGroup.getThreeLevelKeys();
			
			if(currentThreeLevelKeys != null)
			{
				threeLevelKeys.addAll(currentThreeLevelKeys);
			}
			
			// Demande de récupération des Keys
			currentKeys.clear();
			currentKeys = currentKeyGroup.getKeys();
			
			if(currentKeys != null)
			{
				allKeys.addAll(currentKeys);
			}
		}
		
		// Création du Timer resize
		resizeTimer = createResizeTimer();
		
		// Ajout des touches au panel
		addUIKeys();
		
		// Ajout en tant que listener de component
		// (pour le resize,...)
		addComponentListener(this);
	}

	//----------------------------------------------------------- METHODES --//	
	//-----------------------------------------------------------------------
	// Selection
	//-----------------------------------------------------------------------	
	public void edit()
	{
		// Ajout des listeners
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		
		// Maj des keys
		updateEdit(true);
		 
		// Changement de l'état
		isEdited = true;
	}
	
	public void unEdit()
	{
		// Ajout des listeners
		removeKeyListener(keyListener);
		removeMouseListener(mouseListener);
		
		// Changement de l'état
		isEdited = false;
		
		// Maj des keys
		updateEdit(false);
	}
	
	//-----------------------------------------------------------------------
	// Dessin
	//-----------------------------------------------------------------------	
	public void paintComponent(Graphics myGraphic)
	{		
		// Appel au père
		super.paintComponents(myGraphic);
		
		// On vide le panel
		myGraphic.clearRect(0, 0, getWidth(), getHeight());
		
		// Récupération du Graphics2D
		Graphics2D g2 = (Graphics2D) myGraphic;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Application de la transparence
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
		// On redessine le fond
		if (imgBackground == null)
		{
			imgBackground = recreateBackground();
		}
		
		g2.drawImage(imgBackground, 0, 0, null);
	}
	
	//-----------------------------------------------------------------------
	// Méthodes de réaction à la selection de key
	//-----------------------------------------------------------------------	
	public void keySelected(UIJResizer selectedKey)
	{
		selectedKeys.add((UIKeyKeyboard)selectedKey);
	}

	public void keyUnselected(UIJResizer unselectedKey)
	{
		selectedKeys.remove((UIKeyKeyboard)unselectedKey);
	}

	//-----------------------------------------------------------------------
	// ComponentListener
	//-----------------------------------------------------------------------	
	public void componentHidden(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void componentMoved(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resize(Dimension arg0)
	{
		// TODO Auto-generated method stub
	//	super.resize(arg0);
	}
    
	public void componentResized(ComponentEvent arg0)
	{
		// On recalcule le fond
		// On ettend l'image
		if (imgBackground != null)
		{
			imgBackground = TImageUtils.toBufferedImage(((Image)imgBackground).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
		}
		
		// On recalcule la taille de police
		updateKeyFontSize();
		
		//On réarme le timer
		resizeTimer.restart();
		
		// On redessine
		//repaint();
	}
	
	@Override
	public void paint(Graphics arg0)
	{
		// On replace les key. Sinon les touches seront placées
		// comme dans un panel normal
		replaceUIKeys();
		
		// Appel au père
		super.paint(arg0);
	}
	
	public void componentShown(ComponentEvent arg0)
	{		
		// Rien à ajouter
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	//-----------------------------------------------------------------------
	// Edition
	//-----------------------------------------------------------------------
	private void updateEdit( boolean inEdition)
	{
		// Maj des keys
		for (UIKeyKeyboard currentKey : allKeys)
		{
			currentKey.setEditable(inEdition);
		}
	}
	
	//-----------------------------------------------------------------------
	// Construction
	//-----------------------------------------------------------------------		
	private void replaceUIKeys()
	{		
		for (UIKeyKeyboard currentKey : allKeys)
		{						
			// On caste en CKeyKeyboard
			CKeyKeyboard currentKeyKeyboard = (CKeyKeyboard)(currentKey.getCoreKey());
			
			// On récupère les pourcentages des positions
			TPoint relMax = currentKeyKeyboard.getPointMax();
			TPoint relMin = currentKeyKeyboard.getPointMin();
			
			// Calcul des positions absolues
			int absMinX = Math.round(getWidth()*relMin.getX());
			int absMinY = Math.round(getHeight()*relMin.getY());
			int absMaxX = Math.round(getWidth()*relMax.getX());
			int absMaxY = Math.round(getHeight()*relMax.getY());
			
			
			// Affectation de la position
			currentKey.setLocation(absMinX,absMinY);
			currentKey.setPreferredSize(new Dimension (	absMaxX - absMinX,
												 		absMaxY - absMinY));
		}

	}
	
	private void addUIKeys()
	{
		for (UIKeyKeyboard currentKey : allKeys)
		{						
			// Ajout au panel
			add(currentKey);
			
			// Ajout en tant que listener
			currentKey.addSelectionListener(this);
		}
	}
	
	protected void updateKeyFontSize()
	{
		// Calcul de la taille
		float heightFactor = CProfil.getInstance().getKeyboardFont().getHeightFactor();
		
		// Calcul de la valeur
		int fontSize = Math.round(getHeight()*heightFactor* FONT_REDUCTION_FACTOR);
		
		// Changement de la taille de toutes les keys
		for (UIKeyKeyboard currentKey : allKeys)
		{						
			currentKey.setFontSize(fontSize);
		}
	}
	
	@Override
	public void invalidate()
	{
		// Appel au père
		super.invalidate();
		
		// On recalcule la taille de police
		updateKeyFontSize();
		
		// On replace les touches
		replaceUIKeys();
	}
	
	//-----------------------------------------------------------------------
	// Dessin
	//-----------------------------------------------------------------------	
	protected BufferedImage recreateBackground()
	{			
		// Variables
		Color bgdColor = CProfil.getInstance().getDefaultColor().getBackColor().getColor();
		Graphics2D buffer;
		BufferedImage image;
		
		// Création de l'image
		image = new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
		buffer = (Graphics2D) image.getGraphics();
		
		// Construction du buffer
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Création du Paint du premier calque
		Color vGradientStartColor, vGradientEndColor;
		vGradientStartColor =  bgdColor.brighter().brighter();
		vGradientEndColor = bgdColor;				

		Paint vPaint = new GradientPaint(	0, 
											0, 
											vGradientStartColor, 
											0, 
											getHeight(), 
											vGradientEndColor, 
											true);
		buffer.setPaint(vPaint);

		// Dessin du premier Paint
		buffer.fillRoundRect(0, 0, getWidth(), getHeight(), TAILLE_ARC, TAILLE_ARC);
		
		// Dessin du contour
		buffer.setColor(bgdColor.darker());
		buffer.setStroke(new BasicStroke(TAILLE_CONTOUR));
		
		buffer.drawRoundRect(	TAILLE_CONTOUR/2, 
							TAILLE_CONTOUR/2, 
							getWidth()-TAILLE_CONTOUR, 
							getHeight()-TAILLE_CONTOUR,
							TAILLE_ARC_CONTOUR,TAILLE_ARC_CONTOUR);
		
		// Retour
		return image;
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
				imgBackground = recreateBackground();
				//replaceUIKeys();
				updateKeyFontSize();
				repaint();
			}
		};

		// Création d'un timer qui génère un tic
		// chaque 500 millième de seconde
		return new Timer(RESIZE_TIMER_DURATION,action);
	}
	
	//-----------------------------------------------------------------------
	// Keylistener
	//-----------------------------------------------------------------------
	/**
	 * Effectue une translation de x, y de toutes les touches selectionnées
	 */
	private void translateSelectedKeys(int xTranslation, int yTranslation, boolean isControlDown)
	{
		int step;
		if (isControlDown)
		{
			step = FINE_TRANSLATION_STEP;
		}
		else
		{
			step = NORMAL_TRANSLATION_STEP;
		}
		
		for (UIJResizer currentKey : selectedKeys)
		{						
			Rectangle bounds = currentKey.getBounds();
			bounds.translate(xTranslation*step, yTranslation*step);
			currentKey.setBounds(bounds);
			currentKey.onBoundsChanged();
			currentKey.invalidate();
		}
	}
	
	/**
	 * Supprime toutes les keys selectionnées
	 */
	private void deleteSelectedKeys()
	{
		List<UIKeyGroup> uiGroupsToDelete = new ArrayList<UIKeyGroup>();
		List<UIKeyList> uiListsToDelete = new ArrayList<UIKeyList>();
		List<UIKey> uiKeysToDelete = new ArrayList<UIKey>();
		
		// Suppression des listes
		for (UIKeyKeyboard currentKey : selectedKeys)
		{						
			if(allKeys.contains(currentKey))
			{
				allKeys.remove(currentKey);
			}
			
			if(threeLevelKeys.contains(currentKey))
			{
				threeLevelKeys.remove(currentKey);
			}
			
			// Suppression du panel
			remove(currentKey);
		}
		
		// On parcours les groupes...
		for(UIKeyGroup currentGroup : keyGroups)
		{
			// ..les listes...
			for(UIKeyList currentList : currentGroup.getKeyLists())
			{
				/// ...et les keys
				for(UIKeyKeyboard currentKey : currentList.getKeys())
				{
					// On regarde si la key de la liste est une des keys à 
					// supprimer
					if(selectedKeys.contains(currentKey))
					{
						// UI
						uiKeysToDelete.add(currentKey);
						
						// Noyau
						currentList.getCoreKeyList().removeKey(currentKey.getCoreKey());
						
						// Suppression de la UIKey dans la liste des selectionnées
						selectedKeys.remove(currentKey);
					}
				}
				
				// On supprime la liste si elle est vide
				if (currentList.getCoreKeyList().keyCount() == 0)
				{
					// UI
					uiListsToDelete.add(currentList);
					
					// Noyau
					currentGroup.getCoreKeyGroup().removeList(currentList.getCoreKeyList());
				}
			}
			
			// On supprime le groupe si il est vide
			if (currentGroup.getCoreKeyGroup().listCount() == 0)
			{
				// UI
				uiGroupsToDelete.add(currentGroup);
				
				// Noyau
				coreKeyboard.removeKeyGroup(currentGroup.getCoreKeyGroup());
			}
		}		
		
		// Suppression des objets interface
		for(UIKeyGroup currentGroup : keyGroups)
		{
			
			for(UIKeyList currentList : currentGroup.getKeyLists())
			{
				// Les touches
				currentList.removeKeys(uiKeysToDelete);
			}
			
			// les listes
			currentGroup.removeLists(uiListsToDelete);
		}
		
		// Les groupes
		keyGroups.removeAll(uiGroupsToDelete);		
		
		repaint();
	}
	
	private void unselectAllKeys()
	{
		// On commence par se désabonner au keys selectionnées,
		// pour ne pas qu'elles envoient d'evenement pour dire
		// que la selection a changé
		for (UIKeyKeyboard currentKey : selectedKeys)
		{
			currentKey.removeSelectionListener(this);
		}
		
		// On les deselectionne une par une
		for (UIKeyKeyboard currentKey : selectedKeys)
		{
			currentKey.eraseBorder();
			currentKey.setSelected(false);
		}
		
		// On se réabonne
		for (UIKeyKeyboard currentKey : selectedKeys)
		{
			currentKey.addSelectionListener(this);
		}
		
		// On vide la selection
		selectedKeys.clear();
	}
	
	private KeyListener keyListener = new KeyListener()
	{
		public void keyPressed(KeyEvent arg0)
		{
			switch(arg0.getKeyCode())
			{
				case (KeyEvent.VK_DOWN) :
					translateSelectedKeys(0,1,arg0.isControlDown());
					break;
				
				case (KeyEvent.VK_UP) :
					translateSelectedKeys(0,-1,arg0.isControlDown());
					break;
				
				case (KeyEvent.VK_LEFT) :
					translateSelectedKeys(-1,0,arg0.isControlDown());
					break;
					
				case (KeyEvent.VK_RIGHT) :
					translateSelectedKeys(1,0,arg0.isControlDown());
					break;
					
				// SUPPRESSION
				case (KeyEvent.VK_DELETE) :
					deleteSelectedKeys();
					break;
					
				case (KeyEvent.VK_SPACE): 
					try
					{
						CProfil.getInstance().SaveProfil("Ressources\\Temp\\profil2.xml");
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				
					System.out.println("Profil saved !");
					break;
			}
		}

		public void keyReleased(KeyEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		public void keyTyped(KeyEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	private MouseListener mouseListener = new MouseListener()
	{

		public void mouseClicked(MouseEvent arg0)
		{
//			 TODO Auto-generated method stub
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
			// On deselectionne les keys
			unselectAllKeys();
			repaint();
		}

		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

	};
}
