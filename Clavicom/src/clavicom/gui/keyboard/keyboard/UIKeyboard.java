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
//import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;
import javax.swing.event.MouseInputAdapter;

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
import clavicom.core.keygroup.keyboard.key.CKeySound;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.listener.ChangeLevelListener;
import clavicom.core.listener.OnClickKeyCreationListener;
import clavicom.core.listener.ReleaseHoldableKeysListener;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.UIKeyClavicomEngine;
import clavicom.gui.engine.UIKeyCreationEngine;
import clavicom.gui.engine.sound.SoundEngine;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyCharacter;
import clavicom.gui.keyboard.key.UIKeyClavicom;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.UIKeyLastWord;
import clavicom.gui.keyboard.key.UIKeyLauncher;
import clavicom.gui.keyboard.key.UIKeyLevel;
import clavicom.gui.keyboard.key.UIKeyPrediction;
import clavicom.gui.keyboard.key.UIKeyShortcut;
import clavicom.gui.keyboard.key.UIKeySound;
import clavicom.gui.keyboard.key.UIKeyString;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;
import clavicom.gui.keyboard.key.resizer.UIJResizer;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.UIKeySelectionListener;
import clavicom.gui.listener.UIKeyboardNewKeyCreated;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.listener.UIRightClickListener;
import clavicom.gui.utils.UIBackgroundPanel;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TPoint;
//import clavicom.tools.TSwingUtils;
import clavicom.tools.TUIKeyState;

public class UIKeyboard extends UIBackgroundPanel implements 
ComponentListener, 
UIKeySelectionListener, 
ChangeLevelListener, 
OnClickKeyCreationListener, 
ReleaseHoldableKeysListener,
UIRightClickListener
{
	//--------------------------------------------------------- CONSTANTES --//
	final int RESIZE_TIMER_DURATION = 200;		// Durée au delà de laquelle le calcul des
												// images est lancé, pendant un resize	
	
	final int NORMAL_TRANSLATION_STEP = 10;
	final int FINE_TRANSLATION_STEP = 1;
	
	final float FONT_REDUCTION_FACTOR = .2f;
	
	final float NEW_KEY_RELATIVE_WIDTH = .1f;		// Taille relative des boutons ajoutés
	final float NEW_KEY_RELATIVE_HEIGHT = .1f;		// Taille relative des boutons ajoutés

	//---------------------------------------------------------- VARIABLES --//	
	private List<UIKeyGroup> keyGroups;				// Liste des UIKeyGroups
	private List<UIKeyKeyboard> allKeys;			// Liste des keys
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
	
	private boolean resizing;					// Indique si on est en redimensionnement
	
	private boolean useMagnedGrid;				// Indique si on affiche la grille
	
	private int fontSize;						// Taille actuelle du texte des touches
	
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
		selectedKeys = new ArrayList<UIKeyKeyboard>();

		listeners = new EventListenerList();
		magnetGrid = new UIMagnetGrid();
		magnetGrid.setBorderSize(TAILLE_CONTOUR);
		
		firstEdition = true;
		resizing = false;
		
		// Récupération du nombre de groupes 
		int groupCount = coreKeyboard.groupCount();
		
		// Par défaut on n'est pas en édition
		isEdited = false;
		
		// On ne met pas de layout pour empecher qu'il place les touches
		// à sa façon
		setLayout(null);
		
		// abonnement au release holdable
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
		resizeTimer.setRepeats(false);
		
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
	public void setMagnetGridToKeys(UIMagnetGrid mGrid)
	{
		for(UIKey currentKey : allKeys)
		{
			currentKey.setMagnetGrid(mGrid);
		}
	}
	
	/**
	 * Dit a chaque touche si le magnetgrid est utilisé
	 *
	 */
	public void setMagnetGridUsedToKeys(boolean used)
	{
		for(UIKey currentKey : allKeys)
		{
			currentKey.setMagnetGridUsed(used);
		}
	}
	
	public void useMagnetGrid(boolean use)
	{
		useMagnedGrid = use;
		setMagnetGridUsedToKeys(use);
	}
	
	public void setMagnetGridSteps(int vertical, int horizontal)
	{
		magnetGrid.setVerticalStep(vertical);
		magnetGrid.setHorizontalStep(horizontal);
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
    
	public void addRightClickListener(UIRightClickListener listener) 
	{
        listeners.add(UIRightClickListener.class, listener);
    }
    
    public void removeRightClickListener(UIRightClickListener listener) 
    {
        listeners.remove(UIRightClickListener.class, listener);
    }
    
    public UIRightClickListener[] getRightClickListeners() 
    {
        return listeners.getListeners(UIRightClickListener.class);
    }
    
	//-----------------------------------------------------------------------
	// Changement de niveau
	//-----------------------------------------------------------------------
	public void changeLevel(TLevelEnum level)
	{
		for (UIKeyKeyboard currentKey : allKeys)
		{
			if ( currentKey instanceof UIKeyThreeLevel)
			{
				((UIKeyThreeLevel)currentKey).captionChanged();
			}
		}
	}
	
	//-----------------------------------------------------------------------
	// Selection
	//-----------------------------------------------------------------------	
	public void edit()
	{
		// Ajout des listeners
		addKeyListener(keyListener);
//		addMouseListener(mouseAdapter);
		
		// Maj des keys
		updateEdit(true);
		
		// Ajout de la grille si c'est la premiere edition		
		if(firstEdition == true)
		{			
			setMagnetGridToKeys(magnetGrid);
			firstEdition = false;
		}
		
		// On met à jour les dimensions de la grille
		magnetGrid.setDimensions(getWidth(), getHeight());
		recreateGrid();
		 
		// Changement de l'état
		isEdited = true;
		
		// on change le level, on se remet en normal
		CLevelEngine.getInstance().setCurrentLevel( TLevelEnum.NORMAL, true );
		CCommandEngine.getInstance().ClearHoldKey();
		releasedHoldableKeys();
		
		// on vide les prédictions et les lastWord
		CPredictionEngine.getInstance().clean();
		CLastWordEngine.getInstance().clean();
		
		// on désactive les sons
		if( SoundEngine.getInstance() != null )
		{
			SoundEngine.getInstance().unListenPressed( this );
			SoundEngine.getInstance().unListenEntered( this );
			SoundEngine.getInstance().unListenDefilement( );
		}
		
		// on relève toutes les keys
		releasedAllKeys();
		
	}
	
	public void unEdit()
	{
		// Retrait des listeners
		removeKeyListener(keyListener);
//		removeMouseListener(mouseAdapter);
		
		// Changement de l'état
		isEdited = false;
		
		// Maj des keys
		updateEdit(false);
		
		// vérifie le moteur de son
		SoundEngine.verifySoundEngine( this );
		
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

	@Override
	protected void paintChildren(Graphics arg0)
	{		
		// Si on est en train de resizer on ne redessine pas les fils
		if(resizing == true)
		{
			return;
		}
		else
		{
			//replaceUIKeys(true);
			super.paintChildren(arg0);
		}
	}
	
	static int cpt;
	@Override
	public void paint(Graphics arg0)
	{
		// On paint le composant
		paintComponent(arg0);
		
		// On replace les touches
		if(resizing)
			return;
		
		// On dessine les fils
		paintChildren(arg0);
		
		super.paint(arg0);
	}
	
	public void paintComponent(Graphics myGraphic)
	{	
		if (resizing == true)
		{
			return;
		}
		
		// Récupération du Graphics2D
		Graphics2D g2 = (Graphics2D) myGraphic;
		
		// On desactive l'optimisation
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
		// On vide le rectangle
		g2.clearRect(0, 0, getWidth(), getHeight());
		
		// On redessine le fond		
		g2.drawImage(imgBackground, 0, 0, null);
		
		// On ajoute la grille si nécessaire
		if(isEdited == true && useMagnedGrid == true)
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
    

	
	public void componentResized(ComponentEvent arg0)
	{
		resizing = true;
		
		// on relance le timer
		resizeTimer.restart();
		
//		// On recalcule le fond et on etend l'image
//		if (imgBackground != null)
//		{
//			imgBackground = TSwingUtils.toBufferedImage(((Image)imgBackground).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
//		}
//		
//		if (isEdited == true && imgGrid != null)
//		{
//			imgGrid = TSwingUtils.toBufferedImage(((Image)imgGrid).getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
//		}
	}
	
	public void componentShown(ComponentEvent arg0)
	{	
		// Rien à faire
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
    
    protected void fireRightClickOccured(Point mousePositionOnScreen) 
    {
	    for ( UIRightClickListener listener : getRightClickListeners() )
		{
			listener.rightClickOccured(mousePositionOnScreen);
		}
    }
    
	//-----------------------------------------------------------------------
	// Edition
	//-----------------------------------------------------------------------
	public void updateEdit( boolean inEdition)
	{
		// Maj des keys
		for (UIKeyKeyboard currentKey : allKeys)
		{
			currentKey.setEditable(inEdition);
			currentKey.forceState(TUIKeyState.NORMAL);
		}
	}
	
	public void redrawAllKeys( )
	{
		// Maj des keys
		for (UIKeyKeyboard currentKey : allKeys)
		{
			currentKey.captionChanged();
		}
	}
	
	//-----------------------------------------------------------------------
	// Construction
	//-----------------------------------------------------------------------		
	public void replaceUIKeys()
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
		currentKey.setBounds(new Rectangle (	absMinX,
												absMinY,
												absMaxX - absMinX,
												absMaxY - absMinY));
		if(!isEdited)
			currentKey.boundsUpdated();
	}
	
	private void setPositionUIKeys()
	{
		for (UIKeyKeyboard currentKey : allKeys)
		{						
			setPositionUIKey(currentKey);
		}
	}
	
	private void setPositionUIKey(UIKeyKeyboard currentKey)
	{
		if(getWidth() <= 0 || getHeight() <= 0)
			return;
		
		// On caste en CKeyKeyboard
		CKeyKeyboard currentKeyKeyboard = (CKeyKeyboard)(currentKey.getCoreKey());
		
		// On recalcule les positions
		float relMinX = (float)currentKey.getLocation().getX() / (float)getWidth();
		float relMinY = (float)currentKey.getLocation().getY() / (float)getHeight();
		
		float relMaxX = (float)(currentKey.getLocation().getX() + currentKey.getWidth()) / (float)getWidth();
		float relMaxY = (float)(currentKey.getLocation().getY() + currentKey.getHeight()) / (float)getHeight();
		
		// On affecte les positions
		currentKeyKeyboard.setPointMin(new TPoint(relMinX, relMinY));
		currentKeyKeyboard.setPointMax(new TPoint(relMaxX, relMaxY));
	}
	
	private void addUIKeys()
	{
		for (UIKeyKeyboard currentKey : allKeys)
		{
			// Ajout au panel
			add(currentKey);
			
			// Ajout en tant que listener
			currentKey.addSelectionListener(this);
			currentKey.addRightClickListener(this);
		}
	}
	
	protected void updateKeyFontSize()
	{
		// Calcul de la taille
		float heightFactor = CProfil.getInstance().getKeyboardFont().getHeightFactor();
		
		// Calcul de la valeur
		fontSize = Math.round(getHeight()*heightFactor* FONT_REDUCTION_FACTOR);
		
		// Changement de la taille de toutes les keys
		for (UIKeyKeyboard currentKey : allKeys)
		{						
			currentKey.setFontSize(fontSize);
		}
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
				//resizeTimer.stop();
				imgBackground = recreateBackground();
				
				updateKeyFontSize();
				
				if(isEdited == true)
				{
					// TODO : temporaire
					setPositionUIKeys();
					
					magnetGrid.setDimensions(getWidth(), getHeight());
					recreateGrid();
				}
				
				resizing = false;
				replaceUIKeys();

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
	 * Agrandit les touches selectionnées
	 */
	private void resizeSelectedKeys(KeyEvent keyEvent)
	{
		Dimension steps = getSteps(keyEvent);
		
		int xStep = (int)steps.getWidth();
		int yStep = (int)steps.getHeight();
		
		
		for (UIJResizer currentKey : selectedKeys)
		{						
			Rectangle bounds = currentKey.getBounds();
			
			// Réduction verticale
			if (xStep != 0)
			{
				bounds.setSize((int)bounds.getWidth() + 2*xStep, (int)bounds.getHeight());
				bounds.translate(-xStep, 0);
			}
			
			// Réduction horizontale
			if (yStep != 0)
			{
				bounds.setSize((int)bounds.getWidth(), (int)bounds.getHeight() - 2*yStep);
				bounds.translate(0, yStep);
			}
			
			currentKey.setBounds(bounds);
			currentKey.onBoundsChanged();
			currentKey.invalidate();
		}		
	}
	
	/**
	 * Retourne les offsets à faire selon la fleche pressée
	 * @param keyEvent
	 * @return
	 */
	private Dimension getSteps(KeyEvent keyEvent)
	{
		int xStep = 0;
		int yStep = 0;
		
		switch (keyEvent.getKeyCode())
		{
			case KeyEvent.VK_UP:
				if (keyEvent.isControlDown())
				{
					xStep = 0;
					yStep = -FINE_TRANSLATION_STEP;
				}
				else
				{
					xStep = 0;
					yStep = -magnetGrid.getHorizontalStep();
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if (keyEvent.isControlDown())
				{
					xStep = 0;
					yStep = FINE_TRANSLATION_STEP;
				}
				else
				{
					xStep = 0;
					yStep = magnetGrid.getHorizontalStep();
				}
				break;
				
			case KeyEvent.VK_LEFT:
				if (keyEvent.isControlDown())
				{
					xStep = -FINE_TRANSLATION_STEP;
					yStep = 0;
				}
				else
				{
					xStep = -magnetGrid.getVerticalStep();
					yStep = 0;
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if (keyEvent.isControlDown())
				{
					xStep = FINE_TRANSLATION_STEP;
					yStep = 0;
				}
				else
				{
					xStep = magnetGrid.getVerticalStep();
					yStep = 0;						
				}
				break;
		}
		
		return new Dimension(xStep, yStep);
	}
	/**
	 * Effectue une translation de x, y de toutes les touches selectionnées
	 */
	private void translateSelectedKeys(KeyEvent keyEvent)
	{
		Dimension steps = getSteps(keyEvent);
		
		for (UIJResizer currentKey : selectedKeys)
		{						
			Rectangle bounds = currentKey.getBounds();
			bounds.translate((int)steps.getWidth(),(int)steps.getHeight());
			currentKey.setBounds(bounds);
			currentKey.onBoundsChanged();
			currentKey.invalidate();
		}
	}
	
	/**
	 * Supprime toutes les keys selectionnées
	 */
	public void deleteSelectedKeys()
	{
		// on demande à l'utilisateur s'il comfirma la suppression
		
		// On construit le message le plus approprié
		String message;
		
		switch (selectedKeys.size())
		{
			case 0:
				return;

			case 1:
				message = UIString.getUIString("LB_UIKEYBOARD_DELETE_ONE_KEY");
				break;
				
			default:
				message = 	UIString.getUIString("LB_UIKEYBOARD_DELETE_MULTI_KEY_1") + 
							selectedKeys.size() + 
							UIString.getUIString("LB_UIKEYBOARD_DELETE_MULTI_KEY_2");
		}
		
		if( JOptionPane.showConfirmDialog(
				this, 
				message, 
				UIString.getUIString("LB_UIKEYBOARD_DELETE_KEY_TITLE"), 
				JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
		{
			List<UIKeyGroup> uiGroupsToDelete = new ArrayList<UIKeyGroup>();
			List<UIKeyList> uiListsToDelete = new ArrayList<UIKeyList>();
			List<UIKey> uiKeysToDelete = new ArrayList<UIKey>();
			
			// Suppression des listes
			allKeys.removeAll(selectedKeys);
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
			revalidate();
			repaint();
		}
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
				case (KeyEvent.VK_UP) :				
				case (KeyEvent.VK_LEFT) :					
				case (KeyEvent.VK_RIGHT) :
					if(arg0.isShiftDown())
					{
						resizeSelectedKeys(arg0);
					}
					else
					{
						translateSelectedKeys(arg0);
					}
					break;
					
				// SUPPRESSION
				case (KeyEvent.VK_DELETE) :
					deleteSelectedKeys();
				
					// On indique que la selection a changée
					fireSelectionChanged();
					break;
					
				case (KeyEvent.VK_A) :
					if(!arg0.isControlDown())
						return;
				
					select(true);
					// On indique que la selection a changée
					fireSelectionChanged();
					break;
					
				case (KeyEvent.VK_ESCAPE) :
					// On déselectionne tout
					select(false);
					// On indique que la selection a changée
					fireSelectionChanged();
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
	
	private MouseInputAdapter mouseAdapter = new MouseInputAdapter()
	{
		public void mouseEntered(MouseEvent arg0)
		{
			if(isEdited == false)
				return;
			

			requestFocus();
		}

		public void mousePressed(MouseEvent arg0)
		{
			if(isEdited == false)
				return;
			
			// Si c'est pas le clic gauche, on annule
			if (!SwingUtilities.isLeftMouseButton(arg0))
            {
                return;
            }
			
			// On deselectionne les keys
			unselectAllKeys();
			//repaint();
		}
	};

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
		else if (keyType == TEnumCreationKey.T_KEY_CLAVICOM_MINIMIZE_APPLICATION)
		{
			// Création de l'objet du noyau
			CKeyClavicom newCoreKey = new CKeyClavicom(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax,
														TKeyClavicomActionType.MINIMIZE_APPLICATION,
														CFilePaths.getToolKeyClavicomMinimizePicture());
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
		else if (keyType == TEnumCreationKey.T_KEY_SOUND)
		{
			// Création de l'objet du noyau
			CKeySound newCoreKey = new CKeySound(	normalColor,
														pressedColor,
														enteredColor,
														false,
														newKeyMin,
														newKeyMax);
			// Création de l'objet de l'UI
			UIKeySound newUIKey = new UIKeySound(newCoreKey);
			
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
		
		// On met la grille à la touche
		newUIKeyGlobal.setMagnetGrid(magnetGrid);
		newUIKeyGlobal.setMagnetGridUsed(useMagnedGrid);
		
		// Taille du texte par défaut
		newUIKeyGlobal.setFontSize(fontSize);
		
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
		
		// Ajout au listener de click droit
		newUIKeyKeyboard.addRightClickListener(this);
		
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

	public UIMagnetGrid getMagnetGrid()
	{
		return magnetGrid;
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
	
	public void recreateGrid()
	{
		imgGrid = magnetGrid.getDrawing();
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
	
	public MouseInputAdapter getMouseAdapter()
	{
		return mouseAdapter;
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
	
	public void releasedAllKeys()
	{
		for( UIKeyKeyboard uiKeyKeyboard : allKeys )
		{
			// on la désélectionne
			uiKeyKeyboard.setState( TUIKeyState.NORMAL );
		}
	}

	public void rightClickOccured(Point mousePositionOnScreen)
	{
		fireRightClickOccured(mousePositionOnScreen);
	}

	public int getFontSize()
	{
		return fontSize;
	}	
}
