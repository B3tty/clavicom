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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Timer;

import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyMove;
import clavicom.core.listener.OnClickKeyClavicomListener;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyClavicom;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TUIKeyState;
import clavicom.tools.TUIMouseDefilementEnum;

public class UIMouse extends JPanel implements OnClickKeyClavicomListener
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
	
	JPanel panelSwitchKeyboard;
	JPanel panelUIKey;
	JPanel panelDefaultMouse;

	int indexSelectedKey;
	List<UIKey> selectedList;
	Timer selectionTimer;
	
	Thread threadMouseHook = new Thread()
	{
		public void run() 
		{
			super.run();
			
			InitMouseHook();
		}
		
	};
	public native void InitMouseHook();
	public native void FinishMouseHook();

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIMouse( CMouse myMouse )
	{
		mouse = myMouse;
		
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
		moveMouseMode.setPreferredSize(new Dimension(50, 50));
		
		CKeyClavicom clavClickMoveMode = mouse.getClickMouseMode();
		clavClickMoveMode.addOnClickKeyClavicomListener( this );
		clickMouseMode = new UIKeyClavicom( clavClickMoveMode );
		clickMouseMode.setPreferredSize(new Dimension(50, 50));
		
		CKeyClavicom clavSwitchMouseKeyboard = mouse.getSwitchMouseKeyboard();
		clavSwitchMouseKeyboard.addOnClickKeyClavicomListener( this );
		switchMouseKeyboard = new UIKeyClavicom( clavSwitchMouseKeyboard );
		switchMouseKeyboard.setPreferredSize(new Dimension(50, 50));
		
		panelSwitchKeyboard = new JPanel( new BorderLayout() );
		panelUIKey = new JPanel();

		
		// =============================================================================
		// Placement du panelSwitchKeyboard
		// =============================================================================

		panelSwitchKeyboard.add( switchMouseKeyboard, BorderLayout.CENTER );
		add( panelSwitchKeyboard, BorderLayout.NORTH );
		

		// =============================================================================
		// Placement des UIKey
		// =============================================================================
		panelUIKey.setLayout( new BoxLayout( panelUIKey, BoxLayout.PAGE_AXIS ) );
		
		add( panelUIKey, BorderLayout.CENTER );
		
		panelDefaultMouse = new JPanel();
		panelDefaultMouse.setPreferredSize( new Dimension( 50, 50 ) );
		add( panelDefaultMouse, BorderLayout.SOUTH );
		
		// panel pour la simulation du clique
		panelDefaultMouse.addMouseListener( new MouseListener()
		{

			public void mouseClicked(MouseEvent arg0)
			{
				UIKey uiKey = selectedList.get( indexSelectedKey );
				if( uiKey != null )
				{
					uiKey.simulateClick();
					
					// si c'est une keyMove
					if( uiKey.getCoreKey() instanceof CMouseKeyMove )
					{
						if ( selectionTimer.isRunning() )
						{
							selectionTimer.stop();
						}
						else
						{
							selectionTimer.start();
						}
					}
				}
			}

			public void mouseEntered(MouseEvent arg0){}

			public void mouseExited(MouseEvent arg0){}

			public void mousePressed(MouseEvent arg0){}

			public void mouseReleased(MouseEvent arg0){}
			
		});
		
		SwitchMoveMode();
		//SwitchClickMode();
		
		// Lancement du Hook
		System.loadLibrary("clavicom_gui_mouse_UIMouse");		
		threadMouseHook.start();
		
		
	}
	
	public JPanel getDefaultMousePanel()
	{
		return panelDefaultMouse;
	}
	
	public void stopMouseHook()
	{
		threadMouseHook.stop();
		FinishMouseHook();
	}
	
	void Callback(  )
	{
		System.out.println("click !!!!!!!");
	}
	
	
	// ----------------------------------------------------------- METHODES --//
	
	protected void SwitchClickMode(  )
	{
		// création de la liste des touches
		selectedList = new ArrayList<UIKey>();
		
		panelUIKey.removeAll();
		
		panelUIKey.add( leftClick );
		selectedList.add(leftClick);
		
		panelUIKey.add( rightClick );
		selectedList.add(rightClick);
		
		panelUIKey.add( leftDubbleClick );
		selectedList.add(leftDubbleClick);
		
		panelUIKey.add( leftPress );
		selectedList.add(leftPress);
		
		panelUIKey.add( leftRelease );
		selectedList.add(leftRelease);
		
		panelUIKey.add( moveMouseMode );
		selectedList.add(moveMouseMode);
		
		panelUIKey.invalidate();
		panelUIKey.repaint();
		
		indexSelectedKey = 0;
		
		if( selectionTimer != null )
		{
			selectionTimer.stop();
		}
		selectionTimer = createSelectTimer( TUIMouseDefilementEnum.DEFILEMENT_CLICK );
		selectionTimer.start();
	}
	
	protected void SwitchMoveMode()
	{
		// création de la liste des touches
		selectedList = new ArrayList<UIKey>();
		
		panelUIKey.removeAll();
		
		panelUIKey.add( moveLeft );
		selectedList.add(moveLeft);
		
		panelUIKey.add( moveRight );
		selectedList.add(moveRight);
		
		panelUIKey.add( moveDown );
		selectedList.add(moveDown);
		
		panelUIKey.add( moveUp );
		selectedList.add(moveUp);
		
		
		panelUIKey.add( clickMouseMode );
		selectedList.add(clickMouseMode);
		
		panelUIKey.invalidate();
		panelUIKey.repaint();
		

		indexSelectedKey = 0;
		
		if( selectionTimer != null )
		{
			selectionTimer.stop();
		}
		selectionTimer = createSelectTimer( TUIMouseDefilementEnum.DEFILEMENT_MOVE );
		selectionTimer.start();
	}
	
	
	
	protected Timer createSelectTimer( TUIMouseDefilementEnum type )
	{
		
		// Création d'une instance de listener
		// associée au timer
		ActionListener action = new ActionListener()
		{
			// Méthode appelée à chaque tic du timer
			public void actionPerformed(ActionEvent event)
			{
				// déselection de l'ancienne key
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
		};
		
		
		// Création d'un timer qui génère un tic
		return new Timer( 1000 ,action );
	}

	
	public void onClickKeyClavicom(CKeyClavicom keyClavicom)
	{
		// switch sur le type de la keyClavicom
		if( keyClavicom.getAction() == TKeyClavicomActionType.SWITCH_MOUSE_KEYBOARD )
		{
		
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
