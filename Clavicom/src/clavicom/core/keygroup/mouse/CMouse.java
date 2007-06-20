/*-----------------------------------------------------------------------------+

 Filename			: CMouse.java
 Creation date		: 24 mai 07
 
 Project				: Clavicom
 Package				: clavicom.core.mouse

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

package clavicom.core.keygroup.mouse;

import java.awt.Color;

import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TMouseKeyClickEnum;
import clavicom.tools.TMouseKeyMoveEnum;
import clavicom.tools.TPoint;

public class CMouse
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//

	// keyMove
	CMouseKeyMove moveLeft;
	CMouseKeyMove moveRight;
	CMouseKeyMove moveUp;
	CMouseKeyMove moveDown;

	// keyClic
	CMouseKeyClick leftClick;
	CMouseKeyClick rightClick;
	CMouseKeyClick leftDubbleClick;
	CMouseKeyClick leftPress;
	CMouseKeyClick leftRelease;

	// keyClavicom
	CKeyClavicom moveMouseMode;
	CKeyClavicom clickMouseMode;
	CKeyClavicom switchMouseKeyboard;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	private CMouse()
	{
	}

	// ----------------------------------------------------------- METHODES --//

	public static CMouse CreateMouse(Color myColorNormal,
			Color myColorClicked, Color myColorEntered) throws Exception
	{
		// Construction d'un cmouse avec toutes les touches et
		// tous les cliques :
		// - clic gauche
		// - clic droit
		// - double clic gauche
		// - appuis
		// - relachement
		// - passer dans le mode SourisMouvement
		// - passer dans le mode SourisClic
		// - Aller à gauche
		// - Aller à droite
		// - Aller en haut
		// - Aller en bas
		// - retourner au clavicom

		CMouse mouse = new CMouse();

		// Touche clic gauche
		CMouseKeyClick leftClick = new CMouseKeyClick(
				TMouseKeyClickEnum.BUTTON_1, "** clic gauche **",
				myColorNormal, myColorClicked, myColorEntered);
		mouse.setLeftClick(leftClick);

		// Touche clic droit
		CMouseKeyClick rightClick = new CMouseKeyClick(
				TMouseKeyClickEnum.BUTTON_2, "** clic droit **", myColorNormal,
				myColorClicked, myColorEntered);
		mouse.setRightClick(rightClick);

		// Touche double clic gauche
		CMouseKeyClick leftDubbleClick = new CMouseKeyClick(
				TMouseKeyClickEnum.DOUBLE_BUTTON_1, "** double clic gauche **",
				myColorNormal, myColorClicked, myColorEntered);
		mouse.setLeftDubbleClick(leftDubbleClick);

		// Touche appuis
		CMouseKeyClick leftPress = new CMouseKeyClick(
				TMouseKeyClickEnum.BUTTON_1_PRESS,
				"** clic gauche - pression **", myColorNormal, myColorClicked,
				myColorEntered);
		mouse.setLeftPress(leftPress);

		// Touche relachement
		CMouseKeyClick leftRelease = new CMouseKeyClick(
				TMouseKeyClickEnum.BUTTON_1_RELEASE,
				"** clic gauche - relachement **", myColorNormal,
				myColorClicked, myColorEntered);
		mouse.setLeftRelease(leftRelease);
		try
		{
			// Touche passer dans le mode SourisMouvement
			CKeyClavicom moveMouseMode = new CKeyClavicom(myColorNormal,
					myColorClicked, myColorEntered, new TPoint(0, 0),
					new TPoint(0, 0),
					TKeyClavicomActionType.SWITCH_MOUSECLICK_MOUSEMOVE,
					"** passage en mode -move- **");
			mouse.setMoveMouseMode(moveMouseMode);
		}
		catch (Exception ex)
		{
			throw new Exception("[" + UIString.getUIString("EX_MOUSE_BUILD")
					+ "]["
					+ UIString.getUIString("EX_MOUSE_KEYSWITCH_MOVEMODE")
					+ "] :" + ex.getMessage());
		}

		try
		{
			// Touche passer dans le mode SourisClic
			CKeyClavicom clickMouseMode = new CKeyClavicom(myColorNormal,
					myColorClicked, myColorEntered, new TPoint(0, 0),
					new TPoint(0, 0),
					TKeyClavicomActionType.SWITCH_MOUSEMOVE_MOUSECLICK,
					CFilePaths.getToolKeyMouseGeneralClick());
			clickMouseMode.setCaptionImage( true );
			mouse.setClickMouseMode(clickMouseMode);
		}
		catch (Exception ex)
		{
			throw new Exception("[" + UIString.getUIString("EX_MOUSE_BUILD")
					+ "]["
					+ UIString.getUIString("EX_MOUSE_KEYSWITCH_CLICKMODE")
					+ "] :" + ex.getMessage());
		}

		// Touche aller à gauche
		CMouseKeyMove moveLeft = new CMouseKeyMove(TMouseKeyMoveEnum.LEFT,
				CFilePaths.getToolKeyMouseLeft(), myColorNormal, myColorClicked,
				myColorEntered);
		moveLeft.setCaptionImage( true );
		mouse.setMoveLeft(moveLeft);

		// Touche aller à droite
		CMouseKeyMove moveRight = new CMouseKeyMove(TMouseKeyMoveEnum.RIGHT,
				CFilePaths.getToolKeyMouseRight(), myColorNormal, myColorClicked,
				myColorEntered);
		moveRight.setCaptionImage( true );
		mouse.setMoveRight(moveRight);

		// Touche aller en haut
		CMouseKeyMove moveUp = new CMouseKeyMove(TMouseKeyMoveEnum.UP,
				CFilePaths.getToolKeyMouseUp(), myColorNormal, myColorClicked,
				myColorEntered);
		moveUp.setCaptionImage( true );
		mouse.setMoveUp(moveUp);

		// Touche aller en bas
		CMouseKeyMove moveDown = new CMouseKeyMove(TMouseKeyMoveEnum.DOWN,
				CFilePaths.getToolKeyMouseDown(), myColorNormal, myColorClicked,
				myColorEntered);
		moveDown.setCaptionImage( true );
		mouse.setMoveDown(moveDown);

		// Touche aller en mode clavier
		try
		{
			CKeyClavicom switchMouseKeyboard = new CKeyClavicom(myColorNormal,
					myColorClicked, myColorEntered, new TPoint(0, 0),
					new TPoint(0, 0),
					TKeyClavicomActionType.SWITCH_MOUSE_KEYBOARD,
					CFilePaths.getToolKeyMouseClavicom());
			switchMouseKeyboard.setCaptionImage( true );
			mouse.setSwitchMouseKeyboard(switchMouseKeyboard);
		}
		catch (Exception ex)
		{
			throw new Exception("[" + UIString.getUIString("EX_MOUSE_BUILD")
					+ "]["
					+ UIString.getUIString("EX_MOUSE_KEYSWITCH_KEYBOARDMODE")
					+ "] :" + ex.getMessage());
		}

		return mouse;
	}

	// --------------------------------------------------- METHODES PRIVEES --//

	public CKeyClavicom getClickMouseMode()
	{
		return clickMouseMode;
	}

	public void setClickMouseMode(CKeyClavicom clickMouseMode)
	{
		this.clickMouseMode = clickMouseMode;
	}

	public CKeyClavicom getMoveMouseMode()
	{
		return moveMouseMode;
	}

	public void setMoveMouseMode(CKeyClavicom moveMouseMode)
	{
		this.moveMouseMode = moveMouseMode;
	}

	public CKeyClavicom getSwitchMouseKeyboard()
	{
		return switchMouseKeyboard;
	}

	public void setSwitchMouseKeyboard(CKeyClavicom switchMouseKeyboard)
	{
		this.switchMouseKeyboard = switchMouseKeyboard;
	}

	public CMouseKeyClick getLeftClick()
	{
		return leftClick;
	}

	public void setLeftClick(CMouseKeyClick leftClick)
	{
		this.leftClick = leftClick;
	}

	public CMouseKeyClick getLeftDubbleClick()
	{
		return leftDubbleClick;
	}

	public void setLeftDubbleClick(CMouseKeyClick leftDubbleClick)
	{
		this.leftDubbleClick = leftDubbleClick;
	}

	public CMouseKeyClick getLeftPress()
	{
		return leftPress;
	}

	public void setLeftPress(CMouseKeyClick leftPress)
	{
		this.leftPress = leftPress;
	}

	public CMouseKeyClick getLeftRelease()
	{
		return leftRelease;
	}

	public void setLeftRelease(CMouseKeyClick leftRelease)
	{
		this.leftRelease = leftRelease;
	}

	public CMouseKeyMove getMoveDown()
	{
		return moveDown;
	}

	public void setMoveDown(CMouseKeyMove moveDown)
	{
		this.moveDown = moveDown;
	}

	public CMouseKeyMove getMoveLeft()
	{
		return moveLeft;
	}

	public void setMoveLeft(CMouseKeyMove moveLeft)
	{
		this.moveLeft = moveLeft;
	}

	public CMouseKeyMove getMoveRight()
	{
		return moveRight;
	}

	public void setMoveRight(CMouseKeyMove moveRight)
	{
		this.moveRight = moveRight;
	}

	public CMouseKeyMove getMoveUp()
	{
		return moveUp;
	}

	public void setMoveUp(CMouseKeyMove moveUp)
	{
		this.moveUp = moveUp;
	}

	public CMouseKeyClick getRightClick()
	{
		return rightClick;
	}

	public void setRightClick(CMouseKeyClick rightClick)
	{
		this.rightClick = rightClick;
	}
}
