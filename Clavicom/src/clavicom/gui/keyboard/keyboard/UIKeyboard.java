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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;

import clavicom.CFilePaths;
import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLastWordEngine;
import clavicom.core.engine.CLauncherEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.engine.CPredictionEngine;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.listener.ChangeLevelListener;
import clavicom.core.listener.OnClickKeyCreationListener;
import clavicom.core.listener.ReleaseHoldableKeysListener;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.UIKeyClavicomEngine;
import clavicom.gui.engine.UIKeyCreationEngine;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyCharacter;
import clavicom.gui.keyboard.key.UIKeyClavicom;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.UIKeyLastWord;
import clavicom.gui.keyboard.key.UIKeyLauncher;
import clavicom.gui.keyboard.key.UIKeyLevel;
import clavicom.gui.keyboard.key.UIKeyPrediction;
import clavicom.gui.keyboard.key.UIKeyShortcut;
import clavicom.gui.keyboard.key.UIKeyString;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;
import clavicom.gui.keyboard.key.resizer.UIJResizer;
import clavicom.gui.listener.UIKeySelectionListener;
import clavicom.gui.listener.UIKeyboardNewKeyCreated;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.utils.UIBackgroundPanel;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TSwingUtils;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TPoint;

public class UIKeyboard extends UIBackgroundPanel implements ComponentListener, UIKeySelectionListener, ChangeLevelListener, OnClickKeyCreationListener, ReleaseHoldableKeysListener
{
	//--------------------------------------------------------- CONSTANTES --//
	
	final int RESIZE_TIMER_DURATION = 500;		// Durée au delà de laquelle le calcul des
												// images est lancé, pendant un resize	
	
	final int NORMAL_TRANSLATION_STEP = 10;
	final int FINE_TRANSLATION_STEP = 1;
	
	final float FONT_REDUCTION_FACTOR = .2f;
	
	final float NEW_KEY_RELATIVE_WIDTH = .1f;		// Taille relative des boutons ajoutés
	final float NEW_KEY_RELATIVE_HEIGHT = .1f;		// Taille relative des boutons ajoutés

	//---------------------------------------------------------- VARIABLES --//	
	private List<UIKeyGroup> keyGroups;				// Liste des UIKeyGroups
	private List<UIKeyKeyboard> allKeys;			// Liste des keys
	private List<UIKeyThreeLevel> threeLevelKeys;	// Liste des ThreeLevelKeys
	private List<UIKeyKeyboard> selectedKeys;		// Liste des key selectionnées
	private List<UIKeyKeyboard> unClassedKey;		// Liste des key non placées dans les groupes

	private BufferedImage imgBackground;			// Buffer de l'image cliquée
	private BufferedImage imgGrid;					// Buffer de l'image de la grille
	
	private Timer resizeTimer;					// Timer qui une fois expiré demande
												// le calcul des images
	
	@SuppressWarnings("unused")
	private boolean isEdited;					// Indique si le clavier est en edition
	private CKeyboard coreKeyboard;				// Element du noyau
	
	
	private EventListenerList listeners;		// Listeners sur le keyboard
	
	private UIMagnetGrid magnetGrid;			// Grille de fond
	private boolean firstEdition;				// Vrai uniquement à la première édition
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	/**
	 * Créé l'UIKeyboard à partir du CKeyboard
	 */
	public UIKeyboard(CKeyboard myCoreKeyboard)
	{
		super();
		
		// Initialisation des attributs
		coreKeyboard = myCoreKeyboard;
		keyGroups = new ArrayList<UIKeyGroup>();
		allKeys = new ArrayList<UIKeyKeyboard>();
		unClassedKey = new ArrayList<UIKeyKeyboard>();
		threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		selectedKeys = new ArrayList<UIKeyKeyboard>();

		listeners = new EventListenerList();
		magnetGrid = new UIMagnetGrid();
		magnetGrid.setBorderSize(TAILLE_CONTOUR);
		
		// TODO : temporaire :
		magnetGrid.setNbHorizontals(5);
		magnetGrid.setNbVerticals(10);
		
		firstEdition = true;
		
		// Récupération du nombre de groupes 
		int groupCount = coreKeyboard.groupCount();
		
		// Par défaut on n'est pas en édition
		isEdited = false;
		
		// on s'abonne au moteur de commande pour qu'il nous prévienne
		// quand on doit déséléctionner les holdableKeys
		CCommandEngine.getInstance().addReleaseHoldableKeysListener( this );

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
		
		// On s'ajoute en tant que listener de changement de niveau		
		CLevelEngine.getInstance().addChangeLevelListener(this);
		
		// On s'ajoute en tant que listener de création de touche
		UIKeyCreationEngine.getInstance().addOnClickKeyCreationListener(this);
		
		// Ajout des touches au panel
		addUIKeys();
		
		// Ajout en tant que listener de component
		// (pour le resize,...)
		addComponentListener(this);
	}

	//----------------------------------------------------------- METHODES --//
	
	public void clear()
	{
		// Destruction de tous les groupes UI
		keyGroups.clear();
		allKeys.clear();
		threeLevelKeys.clear();
		selectedKeys.clear();
		unClassedKey.clear();
		
		// Destruction des objets du noyau
		coreKeyboard.clearKeyboard();		
		
		removeAll();
	}
	
	/**
	 * Affecte à chaque touche le magnetGrid
	 *
	 */
	public void setMagnetGridToKeys()
	{
		for(UIKey currentKey : allKeys)
		{
			currentKey.setMagnetGrid(magnetGrid);
		}
	}
	
	public void declassGroup(UIKeyGroup group)
	{
		for (UIKeyGroup currentGroup : keyGroups)
		{
			if(group == currentGroup)
			{			
				// On stocke toutes les keys comme non référencées
				unClassedKey.addAll(currentGroup.getKeys());
				
				// On supprime les keys de l'UI
				keyGroups.remove(currentGroup);
				
				// On supprime les keys du noyau
				coreKeyboard.removeKeyGroup(currentGroup.getCoreKeyGroup());
				
				// On s'arrête
				return;				
			}
		}
	}
	
	public void declassList(UIKeyList list)
	{
		for (UIKeyGroup currentGroup : keyGroups)
		{
			for (UIKeyList currentList : currentGroup.getKeyLists())
			{
				if (currentList == list)
				{
					//  On stocke toutes les keys comme non référencées
					unClassedKey.addAll(currentList.getKeys());
					
					// On supprime les keys de l'UI
					currentGroup.removeUIList(currentList);
					
					// On supprime les keys du noyau
					currentGroup.getCoreKeyGroup().removeList(currentList.getCoreKeyList());
					
					// On s'arrête
					return;
				}
			}
		}
	}
	
	public void declassKey(UIKeyKeyboard key)
	{
		for (UIKeyGroup currentGroup : keyGroups)
		{
			for (UIKeyList currentList : currentGroup.getKeyLists())
			{
				for(UIKeyKeyboard currentKey : currentList.getKeys())
				{
					if (currentKey == key)
					{
						// On stocke toutes les keys comme non référencées
						unClassedKey.add(currentKey);
						
						// On supprime les keys de l'UI
						currentList.removeKey(currentKey);
						
						// On supprime les keys du noyau
						currentList.getCoreKeyList().removeKey(currentKey.getCoreKey());
						
						// On s'arrête
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Ajoute un groupe à la liste des groupes
	 * @param caption
	 * 
	 */
	public UIKeyGroup addUIKeyGroup(String caption)
	{				
		// On regarde si un groupe du même nom n'existe pas
		if (coreKeyboard.keyGroupExists(caption) == true)
		{
			return null;
		}
		
		// Création de l'objet du noyau
		CKeyGroup keyGroup = new CKeyGroup(caption, true);
		coreKeyboard.addKeyGroup(keyGroup);
		
		// Création de l'objet de l'UI
		UIKeyGroup uiKeyGroup = new UIKeyGroup(keyGroup);
		keyGroups.add(uiKeyGroup);
		
		return uiKeyGroup;
	}
	
	public UIKeyList addUIKeyListToGroup(String caption, UIKeyGroup keyGroup)
	{
		// On regarde si une liste du même nom n'existe pas dans les listes
		// du groupe
		if(keyGroup.getCoreKeyGroup().keyListExists(caption) == true)
		{
			return null;
		}
		
		// Création de l'objet du noyau
		CKeyList keyList = new CKeyList(caption);
		keyGroup.getCoreKeyGroup().addKeyList(keyList);
		
		// Création de l'objet de l'UI
		UIKeyList uiKeyList = new UIKeyList(keyList);
		keyGroup.getKeyLists().add(uiKeyList);		
		
		return uiKeyList;
	}
	
	public boolean classKeyToUIList(UIKeyKeyboard uiKeyKeyboard, UIKeyList selectedList)
	{
		// Ajout de la key au noyau
		if(!(uiKeyKeyboard.getCoreKey() instanceof CKeyKeyboard))
		{
			return false;
		}
		selectedList.getCoreKeyList().addKeyboardKey((CKeyKeyboard)uiKeyKeyboard.getCoreKey());
		
		// Ajout de la key au noyau
		selectedList.getKeys().add(uiKeyKeyboard);	
		
		// Suppression des keys à classer
		unClassedKey.remove(uiKeyKeyboard);
		
		return true;
	}
	
	public void moveGroupToIndex(int currentIndex, int requiredIndex)
	{
		// Sauvegarde des objets
		UIKeyGroup uiKeyGroup = keyGroups.get(currentIndex);
		CKeyGroup cKeyGroup = uiKeyGroup.getCoreKeyGroup();
		
		// Déplacement de l'objet du noyau
		coreKeyboard.removeKeyGroup(cKeyGroup);
		coreKeyboard.addKeyGroup(requiredIndex, cKeyGroup);
		
		// Déplacement de l'objet de l'UI
		keyGroups.remove(uiKeyGroup);
		keyGroups.add(requiredIndex, uiKeyGroup);		
	}
	
	public UIKeyGroup getGroupByCaption(String caption)
	{
		for(UIKeyGroup currentGroup : keyGroups )
		{
			if(currentGroup.toString().equals(caption))
			{
				return currentGroup;
			}
		}
		
		return null;
	}
	
	//-----------------------------------------------------------------------
	// Listeners (en générateur)
	//-----------------------------------------------------------------------
	public void addSelectionChangeListener(UIKeyboardSelectionChanged listener) 
	{
        listeners.add(UIKeyboardSelectionChanged.class, listener);
    }
    
    public void removeSelectionChangeListener(UIKeyboardSelectionChanged listener) 
    {
        listeners.remove(UIKeyboardSelectionChanged.class, listener);
    }
    
    public UIKeyboardSelectionChanged[] getSelectionChangeListeners() 
    {
        return listeners.getListeners(UIKeyboardSelectionChanged.class);
    }
    
	public void addKeyCreatedListener(UIKeyboardNewKeyCreated listener) 
	{
        listeners.add(UIKeyboardNewKeyCreated.class, listener);
    }
    
    public void removeKeyCreatedListener(UIKeyboardNewKeyCreated listener) 
    {
        listeners.remove(UIKeyboardNewKeyCreated.class, listener);
    }
    
    public UIKeyboardNewKeyCreated[] getKeyCreatedListeners() 
    {
        return listeners.getListeners(UIKeyboardNewKeyCreated.class);
    }
    
	//-----------------------------------------------------------------------
	// Changement de niveau
	//-----------------------------------------------------------------------
	public void changeLevel(TLevelEnum level)
	{
		for (UIKeyThreeLevel currentKey : threeLevelKeys)
		{
			currentKey.captionChanged();
			currentKey.repaint();
		}
	}
	
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
		
		if(firstEdition == true)
		{
			setMagnetGridToKeys();
			firstEdition = false;
		}
		 
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
	
	public void select(boolean select)
	{
		if (select == true)
		// On selectionne tout
		{
			for(UIKeyKeyboard currentKey : allKeys)
			{
				currentKey.setSelected(true);
			}
		}
		else
		// On deselectionne les selectionnées
		{
			while(selectedKeys.size() != 0)
			{
				selectedKeys.get(0).setSelected(false);
			}
		}
	}
	
	//-----------------------------------------------------------------------
	// Dessin
	//-----------------------------------------------------------------------	
	public void paintComponent(Graphics myGraphic)
	{			
		// On vide le panel
		myGraphic.clearRect(0, 0, getWidth(), getHeight());
		
		// Récupération du Graphics2D
		Graphics2D g2 = (Graphics2D) myGraphic;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// On redessine le fond
		if (imgBackground == null)
		{
			imgBackground = recreateBackground();
		}
		
		g2.drawImage(imgBackground, 0, 0, null);
		
		// On ajoute la grille si nécessaire
		if(isEdited == true)
		{
			g2.drawImage(imgGrid, 0, 0, null);
		}
	}	
	
	//-----------------------------------------------------------------------
	// Méthodes de réaction à la selection de key
	//-----------------------------------------------------------------------	
	public void keySelected(UIJResizer selectedKey)
	{
		selectedKeys.add((UIKeyKeyboard)selectedKey);
		fireSelectionChanged();
	}

	public void keyUnselected(UIJResizer unselectedKey)
	{
		selectedKeys.remove((UIKeyKeyboard)unselectedKey);
		fireSelectionChanged();
	}

	//-----------------------------------------------------------------------
	// ComponentListener
	//-----------------------------------------------------------------------	
	public void componentHidden(ComponentEvent arg0)
	{
		// Rien à faire		
	}

	public void componentMoved(ComponentEvent arg0)
	{
		// Rien à faire				
	}
	
	public void updateAndRepaint()
	{
		magnetGrid.setDimensions(getWidth(), getHeight());
		imgGrid = magnetGrid.getDrawing();
		
		imgBackground = recreateBackground();
		replaceUIKeys();
		updateKeyFontSize();
		repaint();
	}
    
	public void componentResized(ComponentEvent arg0)
	{
		// On recalcule le fond
		// On ettend l'image
		if (imgBackground != null)
		{
			imgBackground = TSwingUtils.toBufferedImage(((Image)imgBackground).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
		}
		
		if (imgGrid != null)
		{
			imgGrid = TSwingUtils.toBufferedImage(((Image)imgGrid).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
		}
		
		// On recalcule la taille de police
		//updateKeyFontSize();
		
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
	// Listeners (en générateur)
	//-----------------------------------------------------------------------	    
    protected void fireSelectionChanged() 
    {
	    for ( UIKeyboardSelectionChanged listener : getSelectionChangeListeners() )
		{
			listener.selectionChanged(this.selectedKeys);
		}
    }
    
    protected void fireKeyCreated(UIKey keyCreated) 
    {
	    for ( UIKeyboardNewKeyCreated listener : getKeyCreatedListeners() )
		{
			listener.keyCreated(keyCreated);
		}
    }
    
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
			replaceUIKey(currentKey);
		}

	}
	
	private void replaceUIKey(UIKeyKeyboard currentKey)
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
				replaceUIKeys();
				updateKeyFontSize();
				magnetGrid.setDimensions(getWidth(), getHeight());
				imgGrid = magnetGrid.getDrawing();
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
		allKeys.removeAll(selectedKeys);
		threeLevelKeys.removeAll(selectedKeys);
		unClassedKey.removeAll(selectedKeys);
		
		for (UIKeyKeyboard currentKey : selectedKeys)
		{						
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
		
		// On vide la liste des selectionnées
		selectedKeys.clear();
		
		// Les groupes
		keyGroups.removeAll(uiGroupsToDelete);		
		
		// On redessine
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
		
		fireSelectionChanged();
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
				
					// On indique que la selection a changée
					fireSelectionChanged();
					break;
					
				case (KeyEvent.VK_SPACE): 
					
					break;
			}
		}

		public void keyReleased(KeyEvent arg0)
		{
			// Rien à faire		
		}

		public void keyTyped(KeyEvent arg0)
		{
			// Rien à faire		
		}
	};
	
	private MouseListener mouseListener = new MouseListener()
	{

		public void mouseClicked(MouseEvent arg0)
		{
			// Rien à faire		
		}

		public void mouseEntered(MouseEvent arg0)
		{
			if(isEdited == true)
			{
				requestFocus();
			}
		}

		public void mouseExited(MouseEvent arg0)
		{
			// Rien à faire			
		}

		public void mousePressed(MouseEvent arg0)
		{
			// Si c'est pas le clic gauche, on annule
			if (!SwingUtilities.isLeftMouseButton(arg0))
            {
                return;
            }
			
			// On deselectionne les keys
			unselectAllKeys();
			repaint();
		}

		public void mouseReleased(MouseEvent arg0)
		{
			// Rien à faire			
		}

	};
	
	protected void boundChanged()
	{
		invalidate();
		repaint();
	}

	public void onClickKeyCreation(TEnumCreationKey keyType)
	{		
		// Couleurs par défaut
		Color normalColor = CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor();
		Color enteredColor = CProfil.getInstance().getDefaultColor().getDefaultKeyEntered().getColor();
		Color pressedColor = CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor();
		
		TPoint newKeyMin = new TPoint(	.5f - (NEW_KEY_RELATIVE_WIDTH/2),
										.5f - (NEW_KEY_RELATIVE_HEIGHT/2));

		TPoint newKeyMax = new TPoint(	.5f + (NEW_KEY_RELATIVE_WIDTH/2),
										.5f + (NEW_KEY_RELATIVE_HEIGHT/2));
		
		
//		TPoint newKeyMin = new TPoint(	.2f,.2f);
//
//		TPoint newKeyMax = new TPoint(	.5f,.5f);	
		UIKey newUIKeyGlobal;
		
		// Points
		if (keyType == TEnumCreationKey.T_KEY_CHARACTER)
		{
			// Création de l'objet du noyau
			CKeyCharacter newCoreKey = new CKeyCharacter(	normalColor,
															pressedColor,
															enteredColor,
															false,
															newKeyMin,
															newKeyMax);
			// Création de l'objet de l'UI
			UIKeyCharacter newUIKey = new UIKeyCharacter(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);
			
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_CLAVICOM_CLOSE_APPLICATION)
		{
			// Création de l'objet du noyau
			CKeyClavicom newCoreKey = new CKeyClavicom(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax,
														TKeyClavicomActionType.CLOSE_APPLICATION,
														CFilePaths.getToolKeyClavicomClosePicture());
			// On dit que c'est une image
			newCoreKey.setCaptionImage(true);
			
			// Création de l'objet de l'UI
			UIKeyClavicom newUIKey = new UIKeyClavicom(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);		
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_CLAVICOM_OPEN_CONFIGURATION)
		{
			// Création de l'objet du noyau
			CKeyClavicom newCoreKey = new CKeyClavicom(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax,
														TKeyClavicomActionType.OPEN_CONFIGURATION,
														CFilePaths.getToolKeyClavicomConfigurationPicture());
			// On dit que c'est une image
			newCoreKey.setCaptionImage(true);
			
			// Création de l'objet de l'UI
			UIKeyClavicom newUIKey = new UIKeyClavicom(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);	
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_CLAVICOM_SWITCH_SOURICOM)
		{
			// Création de l'objet du noyau
			CKeyClavicom newCoreKey = new CKeyClavicom(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax,
														TKeyClavicomActionType.SWITCH_KEYBOARD_MOUSE,
														CFilePaths.getToolKeyClavicomSwitchSouricomPicture());
			// On dit que c'est une image
			newCoreKey.setCaptionImage(true);
			
			// Création de l'objet de l'UI
			UIKeyClavicom newUIKey = new UIKeyClavicom(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);		
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_LASTWORD)
		{
			// Création de l'objet du noyau
			CKeyLastWord newCoreKey = new CKeyLastWord(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax);
			// Création de l'objet de l'UI
			UIKeyLastWord newUIKey = new UIKeyLastWord(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);	
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_LAUNCHER)
		{
			// Création de l'objet du noyau
			CKeyLauncher newCoreKey = new CKeyLauncher(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax);
			// Création de l'objet de l'UI
			UIKeyLauncher newUIKey = new UIKeyLauncher(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);	
			newUIKeyGlobal = newUIKey;
			
		}
		else if (keyType == TEnumCreationKey.T_KEY_LEVEL_SHIFT)
		{
			// Création de l'objet du noyau
			CKeyLevel newCoreKey = new CKeyLevel(	normalColor,
													pressedColor,
													enteredColor,
													true,
													newKeyMin,
													newKeyMax,
													TLevelEnum.SHIFT,
													"",
													false);
			// Création de l'objet de l'UI
			UIKeyLevel newUIKey = new UIKeyLevel(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);
			newUIKeyGlobal = newUIKey;
		}		
		else if (keyType == TEnumCreationKey.T_KEY_LEVEL_ALTGR)
		{
			// Création de l'objet du noyau
			CKeyLevel newCoreKey = new CKeyLevel(	normalColor,
													pressedColor,
													enteredColor,
													true,
													newKeyMin,
													newKeyMax,
													TLevelEnum.ALT_GR,
													"",
													false);
			// Création de l'objet de l'UI
			UIKeyLevel newUIKey = new UIKeyLevel(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);		
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_LEVEL_CAPS_LOCK)
		{
			// Création de l'objet du noyau
			CKeyLevel newCoreKey = new CKeyLevel(	normalColor,
													pressedColor,
													enteredColor,
													true,
													newKeyMin,
													newKeyMax,
													TLevelEnum.SHIFT,
													"",
													true);
			// Création de l'objet de l'UI
			UIKeyLevel newUIKey = new UIKeyLevel(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);		
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_PREDICTION)
		{
			// Création de l'objet du noyau
			CKeyPrediction newCoreKey = new CKeyPrediction(	normalColor,
															pressedColor,
															enteredColor,
															false,
															newKeyMin,
															newKeyMax);
			// Création de l'objet de l'UI
			UIKeyPrediction newUIKey = new UIKeyPrediction(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);		
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_SHORTCUT)
		{
			// Création de l'objet du noyau
			CKeyShortcut newCoreKey = new CKeyShortcut(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax);
			// Création de l'objet de l'UI
			UIKeyShortcut newUIKey = new UIKeyShortcut(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);
			newUIKeyGlobal = newUIKey;
		}
		else if (keyType == TEnumCreationKey.T_KEY_STRING)
		{
			// Création de l'objet du noyau
			CKeyString newCoreKey = new CKeyString(	normalColor,
													pressedColor,
													enteredColor,
													false,
													newKeyMin,
													newKeyMax);
			// Création de l'objet de l'UI
			UIKeyString newUIKey = new UIKeyString(newCoreKey);
			
			// Affectation à l'objet global
			addCreatedKey(newUIKey);
			newUIKeyGlobal = newUIKey;
		}
		else
		{
			return;
		}
		
		// On alerte qu'une nouvelle key a été créée
		fireKeyCreated(newUIKeyGlobal);
	}
	
	public int getGroupeListSize()
	{
		return keyGroups.size();
	}
	public UIKeyGroup getUIKeyGroup( int index )
	{
		return keyGroups.get( index );
	}
	
	protected void addCreatedKey(UIKeyKeyboard newUIKeyKeyboard)
	{
		// On met a jour son état d'edition
		newUIKeyKeyboard.setEditable(isEdited);
		
		// Ajout au listener de selection
		newUIKeyKeyboard.addSelectionListener(this);
		
		// Ajout de l'objet à la liste des keys non classées
		unClassedKey.add(newUIKeyKeyboard);
		
		// Ajout de l'objet à la liste des UIKey
		allKeys.add(newUIKeyKeyboard);
		
		// Ajout de l'objet au panel (au premier plan)
		add(newUIKeyKeyboard, 0);
		
		// On calcule sa position absolue
		replaceUIKey(newUIKeyKeyboard);
		
		// On force le redessin
		revalidate();
	}
	
	public void setKeysTransparency(float keyboardTransparency)
	{
		for( UIKey uiKey : allKeys )
		{
			uiKey.setOpacity( keyboardTransparency );
		}
	}

	public List<UIKeyGroup> getKeyGroups()
	{
		return keyGroups;
	}
	
	public List<UIKeyKeyboard> getUnClassedKey()
	{
		return unClassedKey;
	}
	
	public List<UIKeyKeyboard> getSelectedKeys()
	{
		return selectedKeys;
	}
	
	

	@Override
	public String toString()
	{
		return coreKeyboard.toString();
	}
	
	public void unListenAllKeyKeyboard()
	{
		for( UIKeyKeyboard uiKeyKeyboard : allKeys )
		{
			CKeyKeyboard keyboardKey = (CKeyKeyboard)uiKeyKeyboard.getCoreKey();
			
			if( CCommandEngine.getInstance() != null )
			{
				CCommandEngine.getInstance().unListen( keyboardKey );
			}
			if( CLastWordEngine.getInstance() != null )
			{
				CLastWordEngine.getInstance().unListen( keyboardKey );
			}
			if( CLauncherEngine.getInstance() != null )
			{
				CLauncherEngine.getInstance().unListen( keyboardKey );
			}
			if( CLevelEngine.getInstance() != null )
			{
				CLevelEngine.getInstance().unListen( keyboardKey );
			}
			if( CPredictionEngine.getInstance() != null )
			{
				CPredictionEngine.getInstance().unListen( keyboardKey );
			}
			if( UIKeyClavicomEngine.getInstance() != null )
			{
				UIKeyClavicomEngine.getInstance().unListen( keyboardKey );
			}
		}
		
	}
	
	public void listenAllKeyKeyboard()
	{
		
		for( UIKeyKeyboard uiKeyKeyboard : allKeys )
		{
			CKeyKeyboard keyboardKey = (CKeyKeyboard)uiKeyKeyboard.getCoreKey();
			
			if( CCommandEngine.getInstance() != null )
			{
				CCommandEngine.getInstance().listen( keyboardKey );
			}
			if( CLastWordEngine.getInstance() != null )
			{
				CLastWordEngine.getInstance().listen( keyboardKey );
			}
			if( CLauncherEngine.getInstance() != null )
			{
				CLauncherEngine.getInstance().listen( keyboardKey );
			}
			if( CLevelEngine.getInstance() != null )
			{
				CLevelEngine.getInstance().listen( keyboardKey );
			}
			if( CPredictionEngine.getInstance() != null )
			{
				CPredictionEngine.getInstance().listen( keyboardKey );
			}
			if( UIKeyClavicomEngine.getInstance() != null )
			{
				UIKeyClavicomEngine.getInstance().listen( keyboardKey );
			}
		}
		
	}
	

	public CKeyboard getCoreKeyboard()
	{
		return coreKeyboard;
	}

	public void setEnableKeys(boolean b)
	{
		for( UIKeyKeyboard uiKeyKeyboard : allKeys )
		{
			uiKeyKeyboard.listenMouseListener( b );
		}
	}

	public void releasedHoldableKeys()
	{
		for( UIKeyKeyboard uiKeyKeyboard : allKeys )
		{
			// si c'est une key holdable
			if( uiKeyKeyboard.getCoreKey().isHoldable() )
			{
				// si c'est une key level et qu'elle n'est pas alwaysHoldable
				if( uiKeyKeyboard instanceof UIKeyLevel )
				{
					if( ! ((CKeyLevel)(uiKeyKeyboard.getCoreKey())).isAlwaysHoldable() )
					{
						// on la désélectionne
						uiKeyKeyboard.setClicked( false );
					}

				}
				else
				{
					// on la désélectionne
					uiKeyKeyboard.setClicked( false );
				}
			}
		}
	}

	
	
}
