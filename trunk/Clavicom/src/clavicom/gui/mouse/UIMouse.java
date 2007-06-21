/*-----------------------------------------------------------------------------+

			Filename			: UIMouse.java
			Creation date		: 13 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.mouse

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

package clavicom.gui.mouse;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyMove;
import clavicom.core.listener.OnClickKeyClavicomListener;
import clavicom.gui.engine.DefilementEngine;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.engine.click.clickMouseHookListener;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyClavicom;
import clavicom.gui.listener.DefilListener;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TUIKeyState;

public class UIMouse extends UIMovingPanel implements OnClickKeyClavicomListener, clickMouseHookListener, DefilListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CMouse mouse;
	
	// keyMove
	UIKeyMouse moveLeft;
	UIKeyMouse moveRight;
	UIKeyMouse moveUp;
	UIKeyMouse moveDown;

	// keyClic
	UIKeyMouse leftClick;
	UIKeyMouse rightClick;
	UIKeyMouse leftDubbleClick;
	UIKeyMouse leftPress;
	UIKeyMouse leftRelease;

	// keyClavicom
	UIKeyClavicom moveMouseMode;
	UIKeyClavicom clickMouseMode;
	UIKeyClavicom switchMouseKeyboard;

	int indexSelectedKey;
	List<UIKey> selectedList;
	
	// engine de click et de defilement
	ClickEngine clickEngine;
	DefilementEngine defilementEngine;
	
	JPanel movePanel;
	JPanel clickPanel;
	JPanel panelHaut;
	
	boolean dragAndDropMode; // indique si on est en mode drag and drop

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIMouse( CMouse myMouse, ClickEngine myClickEngine, JFrame parent, DefilementEngine myDefilementEngine )
	{
		super( parent );
		
		mouse = myMouse;
		
		clickEngine = myClickEngine;
		defilementEngine = myDefilementEngine;
		defilementEngine.addDefilListener( this );
		
		dragAndDropMode = false;		
		
		setLayout( new BorderLayout() );
		
		// Construction des UIKeyMouse
		moveLeft = new UIKeyMouse( mouse.getMoveLeft() );
		moveRight = new UIKeyMouse( mouse.getMoveRight() );
		moveUp = new UIKeyMouse( mouse.getMoveUp() );
		moveDown = new UIKeyMouse( mouse.getMoveDown() );
		
		leftClick = new UIKeyMouse( mouse.getLeftClick() );
		rightClick = new UIKeyMouse( mouse.getRightClick() );
		leftDubbleClick = new UIKeyMouse( mouse.getLeftDubbleClick() );
		leftPress = new UIKeyMouse( mouse.getLeftPress() );
		leftRelease = new UIKeyMouse( mouse.getLeftRelease() );
		
		CKeyClavicom clavMoveMoveMode = mouse.getMoveMouseMode();
		clavMoveMoveMode.addOnClickKeyClavicomListener( this );
		moveMouseMode = new UIKeyClavicom( clavMoveMoveMode );
		
		CKeyClavicom clavClickMoveMode = mouse.getClickMouseMode();
		clavClickMoveMode.addOnClickKeyClavicomListener( this );
		clickMouseMode = new UIKeyClavicom( clavClickMoveMode );
		
		CKeyClavicom clavSwitchMouseKeyboard = mouse.getSwitchMouseKeyboard();
		clavSwitchMouseKeyboard.addOnClickKeyClavicomListener( this );
		switchMouseKeyboard = new UIKeyClavicom( clavSwitchMouseKeyboard );

		
		
		GridBagLayout gbLayoutGlobal = new GridBagLayout();
		setLayout( gbLayoutGlobal );
		
		// Ajout des Contraintes de switchMouseKeyboard
		GridBagConstraints gbConstSwitchMouseKeyboard = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            25,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(switchMouseKeyboard, gbConstSwitchMouseKeyboard);
		add( switchMouseKeyboard );
		
		// Ajout des Contraintes de panelHaut
		GridBagConstraints gbConstPanelHaut = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            75,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		panelHaut = new JPanel();
		panelHaut.setLayout( new BorderLayout() );
		gbLayoutGlobal.setConstraints(panelHaut, gbConstPanelHaut);
		add( panelHaut );
		
		CreateClickPanel();
		CreateMovePanel();
		
		SwitchMoveMode();
		
		// abonnement au hook
		clickEngine.addClickMouseHookListener( this );
		
		defilementEngine.startDefilement();
		
	}
	

	private void CreateClickPanel()
	{
		clickPanel = new JPanel();
		
		GridBagLayout gbLayoutMain = new GridBagLayout();
		clickPanel.setLayout(gbLayoutMain);
		
		
		
		// Ajout des Contraintes de leftClick
		GridBagConstraints gbConstLeftClick = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(leftClick, gbConstLeftClick);
		clickPanel.add( leftClick );

		
		
		// Ajout des Contraintes de rightClick
		GridBagConstraints gbConstRightClick = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(rightClick, gbConstRightClick);
		clickPanel.add( rightClick );
		
		
		
		// Ajout des Contraintes de leftDubbleClick
		GridBagConstraints gbConstLeftDubbleClick = new GridBagConstraints (	
				2,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(leftDubbleClick, gbConstLeftDubbleClick);
		clickPanel.add( leftDubbleClick );
		
		
		
		// Ajout des Contraintes de leftPress
		GridBagConstraints gbConstLeftPress = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(leftPress, gbConstLeftPress);
		clickPanel.add( leftPress );
		

		
		
		
		// Ajout des Contraintes de moveMouseMode
		GridBagConstraints gbConstMoveMouseMode = new GridBagConstraints (	
				2,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(moveMouseMode, gbConstMoveMouseMode);
		clickPanel.add( moveMouseMode );
		

	}


	private void CreateMovePanel()
	{
		movePanel = new JPanel();
		
		GridBagLayout gbLayoutMain = new GridBagLayout();
		movePanel.setLayout(gbLayoutMain);
		
		
		
		// Ajout des Contraintes de rightClick
		GridBagConstraints gbConstMoveLeft = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(moveLeft, gbConstMoveLeft);
		movePanel.add( moveLeft );
		
		
		
		// Ajout des Contraintes de moveRight
		GridBagConstraints gbConstMoveRight = new GridBagConstraints (	
				2,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(moveRight, gbConstMoveRight);
		movePanel.add( moveRight );
		
		
		
		// Ajout des Contraintes de moveDown
		GridBagConstraints gbConstMoveDown = new GridBagConstraints (	
				1,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(moveDown, gbConstMoveDown);
		movePanel.add( moveDown );
		
		
		
		// Ajout des Contraintes de moveUp
		GridBagConstraints gbConstMoveUp = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(moveUp, gbConstMoveUp);
		movePanel.add( moveUp );
		

		// ====> On ajoute le leftRelease et le clickMouseMode au même
		// ====> endroit, mais il n'y en a qu'un qui est visible à la fois
		
		// Ajout des Contraintes de leftRelease
		GridBagConstraints gbConstLeftRelease = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(leftRelease, gbConstLeftRelease);
		movePanel.add( leftRelease );
		leftRelease.setVisible(false);
		
		// Ajout des Contraintes de clickMouseMode
		GridBagConstraints gbConstClickMouseMode = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            33,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(clickMouseMode, gbConstClickMouseMode);
		movePanel.add( clickMouseMode );
		
			
		

		
	}


	// ----------------------------------------------------------- METHODES --//
	
	public void defil()
	{
		UIKey mouseKey =  selectedList.get( indexSelectedKey );
		if( mouseKey != null )
		{
			mouseKey.forceState( TUIKeyState.NORMAL );
		}
		
		if( indexSelectedKey >= (selectedList.size() - 1)  )
		{
			indexSelectedKey = 0;
		}
		else
		{
			indexSelectedKey += 1;
		}
		
		// séléction de la nouvelle touche
		UIKey mouseKey2 = selectedList.get( indexSelectedKey );
		if( mouseKey2 != null )
		{
			mouseKey2.forceState( TUIKeyState.ENTERED );
		}
	}
	
	public void clickMouseHook()
	{
		UIKey uiKey = selectedList.get( indexSelectedKey );
		if( uiKey != null )
		{
			uiKey.simulateClick();
			
			// si c'est une keyMove
			if( uiKey.getCoreKey() instanceof CMouseKeyMove )
			{
				if ( defilementEngine.isDefilement() )
				{
					defilementEngine.stopDefilement();
				}
				else
				{
					defilementEngine.startDefilement();
				}
			}
			
			// si c'est la key de drag and drop
			if( uiKey == leftPress )
			{
				dragAndDropMode = true;
				SwitchMoveMode();
			}
			// si c'est la key de drag and drop
			if( uiKey == leftRelease )
			{
				dragAndDropMode = false;
				SwitchClickMode();
			}
		}
	}
	
	protected void SwitchClickMode(  )
	{
		// on désélsctione l'ancianne key
		if( selectedList != null )
		{
			if( selectedList.size() > 0 )
			{
				UIKey uiKey = selectedList.get(indexSelectedKey);
				uiKey.forceState( TUIKeyState.NORMAL );
			}
		}
		
		
		// création de la liste des touches
		selectedList = new ArrayList<UIKey>();
		
		panelHaut.removeAll();
		
		panelHaut.add( clickPanel );

		revalidate();
		
		selectedList.add(leftClick);
		selectedList.add(rightClick);
		selectedList.add(leftDubbleClick);
		selectedList.add(leftPress);
		selectedList.add(moveMouseMode);
		selectedList.add(switchMouseKeyboard);
		
		indexSelectedKey = 0;
		
		
		
	}
	
	protected void SwitchMoveMode()
	{
		// création de la liste des touches
		selectedList = new ArrayList<UIKey>();
		
		panelHaut.removeAll();
		
		panelHaut.add( movePanel, BorderLayout.CENTER );
		
		selectedList.add(moveUp);
		selectedList.add(moveRight);
		selectedList.add(moveDown);
		selectedList.add(moveLeft);
		if(dragAndDropMode)
		{
			selectedList.add(leftRelease);
		}
		else
		{
			selectedList.add(clickMouseMode);
		}
		selectedList.add(switchMouseKeyboard);

		indexSelectedKey = 0;
		
		if( dragAndDropMode )
		{
			clickMouseMode.setVisible( false );
			leftRelease.setVisible( true );
		}
		else
		{
			clickMouseMode.setVisible( true );
			leftRelease.setVisible( false );
		}
		
		panelHaut.revalidate();
		
	}
	
	


	
	public void onClickKeyClavicom(CKeyClavicom keyClavicom)
	{
		// switch sur le type de la keyClavicom
		if( keyClavicom.getAction() == TKeyClavicomActionType.SWITCH_MOUSE_KEYBOARD )
		{
			//TMP TODO
			System.exit(0);
		} else if( keyClavicom.getAction() == TKeyClavicomActionType.SWITCH_MOUSECLICK_MOUSEMOVE )
		{
			SwitchMoveMode();
		} else if( keyClavicom.getAction() == TKeyClavicomActionType.SWITCH_MOUSEMOVE_MOUSECLICK )
		{
			SwitchClickMode();
		}
	}

	public UIKeyClavicom getClickMouseMode()
	{
		return clickMouseMode;
	}

	public void setClickMouseMode(UIKeyClavicom clickMouseMode)
	{
		this.clickMouseMode = clickMouseMode;
	}

	public UIKeyMouse getLeftClick()
	{
		return leftClick;
	}

	public void setLeftClick(UIKeyMouse leftClick)
	{
		this.leftClick = leftClick;
	}

	public UIKeyMouse getLeftDubbleClick()
	{
		return leftDubbleClick;
	}

	public void setLeftDubbleClick(UIKeyMouse leftDubbleClick)
	{
		this.leftDubbleClick = leftDubbleClick;
	}

	public UIKeyMouse getLeftPress()
	{
		return leftPress;
	}

	public void setLeftPress(UIKeyMouse leftPress)
	{
		this.leftPress = leftPress;
	}

	public UIKeyMouse getLeftRelease()
	{
		return leftRelease;
	}

	public void setLeftRelease(UIKeyMouse leftRelease)
	{
		this.leftRelease = leftRelease;
	}

	public CMouse getMouse()
	{
		return mouse;
	}

	public void setMouse(CMouse mouse)
	{
		this.mouse = mouse;
	}

	public UIKeyMouse getMoveDown()
	{
		return moveDown;
	}

	public void setMoveDown(UIKeyMouse moveDown)
	{
		this.moveDown = moveDown;
	}

	public UIKeyMouse getMoveLeft()
	{
		return moveLeft;
	}

	public void setMoveLeft(UIKeyMouse moveLeft)
	{
		this.moveLeft = moveLeft;
	}

	public UIKeyClavicom getMoveMouseMode()
	{
		return moveMouseMode;
	}

	public void setMoveMouseMode(UIKeyClavicom moveMouseMode)
	{
		this.moveMouseMode = moveMouseMode;
	}

	public UIKeyMouse getMoveRight()
	{
		return moveRight;
	}

	public void setMoveRight(UIKeyMouse moveRight)
	{
		this.moveRight = moveRight;
	}

	public UIKeyMouse getMoveUp()
	{
		return moveUp;
	}

	public void setMoveUp(UIKeyMouse moveUp)
	{
		this.moveUp = moveUp;
	}

	public UIKeyMouse getRightClick()
	{
		return rightClick;
	}

	public void setRightClick(UIKeyMouse rightClick)
	{
		this.rightClick = rightClick;
	}

	public UIKeyClavicom getSwitchMouseKeyboard()
	{
		return switchMouseKeyboard;
	}

	public void setSwitchMouseKeyboard(UIKeyClavicom switchMouseKeyboard)
	{
		this.switchMouseKeyboard = switchMouseKeyboard;
	}




	

	

	//--------------------------------------------------- METHODES PRIVEES --//
}