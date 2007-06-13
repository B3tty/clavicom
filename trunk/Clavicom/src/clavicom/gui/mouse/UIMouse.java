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
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.gui.keyboard.key.UIKeyClavicom;

public class UIMouse extends JPanel
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
		
		moveMouseMode = new UIKeyClavicom( mouse.getMoveMouseMode() );
		clickMouseMode = new UIKeyClavicom( mouse.getClickMouseMode() );
		switchMouseKeyboard = new UIKeyClavicom( mouse.getSwitchMouseKeyboard() );
		
		panelSwitchKeyboard = new JPanel( new BorderLayout() );
		panelUIKey = new JPanel();
		
		
		
		// =============================================================================
		// Placement du panelSwitchKeyboard
		// =============================================================================

		panelSwitchKeyboard.add( switchMouseKeyboard, BorderLayout.CENTER );
		add( panelSwitchKeyboard, BorderLayout.NORTH );
		
		// switchMouseKeyboard.setPreferredSize( new Dimension(0,1000) );
		
		// =============================================================================
		// Placement des UIKey
		// =============================================================================
		SwitchMoveMode();
		add( panelUIKey, BorderLayout.CENTER );
	}
	
	
	// ----------------------------------------------------------- METHODES --//
	
	protected void SwitchClickMode()
	{
		panelUIKey.setLayout( new BoxLayout( panelUIKey, BoxLayout.PAGE_AXIS ) );
	}
	
	protected void SwitchMoveMode()
	{
		panelUIKey.removeAll();
		panelUIKey.setLayout( new BoxLayout( panelUIKey, BoxLayout.PAGE_AXIS ) );
		
		panelUIKey.add( moveLeft );
		panelUIKey.add( moveRight );
		panelUIKey.add( moveUp );
		panelUIKey.add( moveDown );
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
