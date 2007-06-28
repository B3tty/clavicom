/*-----------------------------------------------------------------------------+

			Filename			: CCommandEngine.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.engine

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

package clavicom.gui.engine;


import javax.swing.event.EventListenerList;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.listener.OnClickKeyClavicomListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.gui.mouse.UIMouseFrame;
import clavicom.gui.windows.UIKeyboardFrame;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TNavigationType;

public class UIKeyClavicomEngine implements OnClickKeyClavicomListener
{
	//--------------------------------------------------------- CONSTANTES --//
	UIKeyboardFrame frameKeyboard;
	UIMouseFrame frameMouse;
	
	//---------------------------------------------------------- VARIABLES --//

	protected EventListenerList listeners;

	static UIKeyClavicomEngine instance;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public static void createInstance( CKeyboard keyboard, CMouse mouse )
	{
		instance = new UIKeyClavicomEngine( keyboard, mouse );
	}
	public static UIKeyClavicomEngine getInstance()
	{
		return instance;
	}
	
	protected UIKeyClavicomEngine( CKeyboard keyboard, CMouse mouse )
	{
		listeners = new EventListenerList();
		
		// Abonnement aux listeners
		listen( keyboard );
		
		// Abonnement au keyClavicom de la mouse
		mouse.getClickMouseMode().addOnClickKeyClavicomListener( this );
		mouse.getMoveMouseMode().addOnClickKeyClavicomListener( this );
		mouse.getSwitchMouseKeyboard().addOnClickKeyClavicomListener( this );
	}
	
	public void listen( CKeyKeyboard keyboardKey )
	{
		if( keyboardKey != null )
		{
			// on cast pour savoir de quel type est la key
			if( keyboardKey instanceof CKeyClavicom )
			{
				((CKeyClavicom)keyboardKey).addOnClickKeyClavicomListener(this);
			}
		}
	}
	
	public void listen( CKeyboard keyboard ) 
	{
		// =============================================================
		// Abonnement aux listener
		// =============================================================
		for( int i = 0 ; i < keyboard.groupCount() ; ++i )
		{
			CKeyGroup keyGroup = keyboard.getKeyGroup( i );
			if( keyGroup != null )
			{
				for( int j = 0 ; j < keyGroup.listCount() ; ++j )
				{
					CKeyList keyList = keyGroup.getkeyList( j );
					if( keyList != null )
					{
						for( int k = 0 ;  k < keyList.keyCount() ; ++k )
						{
							CKeyKeyboard keyboardKey = keyList.getKeyKeyboard( k );
							if( keyboardKey != null )
							{
								// on cast pour savoir de quel type est la key
								if( keyboardKey instanceof CKeyClavicom )
								{
									((CKeyClavicom)keyboardKey).addOnClickKeyClavicomListener(this);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void unListen( CKeyKeyboard keyboardKey )
	{
		// on cast pour savoir de quel type est la key
		if( keyboardKey instanceof CKeyClavicom )
		{
			((CKeyClavicom)keyboardKey).removeOnClickKeyClavicomListener( this );
		}
	}

	//----------------------------------------------------------- METHODES --//

	/**
	 * Appelé sur le click d'une key clavicom
	 */
	public void onClickKeyClavicom(TKeyClavicomActionType actionType)
	{		
		if (actionType == TKeyClavicomActionType.CLOSE_APPLICATION)
		{
			// Enregistrement des paramètres
			try
			{
				CProfil.getInstance().getAdvancedOption().recalculateFramesPosition( frameKeyboard, frameMouse);
				CProfil.getInstance().saveProfil( );
			}
			catch (Exception ex)
			{
				CMessageEngine.newFatalError(	UIString.getUIString("MSG_PROFIL_SAVE_FAILED_1")+
												CProfil.getInstance().getProfilFilePath() + 
												UIString.getUIString("MSG_PROFIL_SAVE_FAILED_2"),
												ex.getMessage());
			}
			
			// On quitte l'application
			System.exit(0);
		}
		else if (actionType == TKeyClavicomActionType.OPEN_CONFIGURATION)
		{
			// Passage en mode configuration
			frameKeyboard.edit(true);
		}
		else if (actionType == TKeyClavicomActionType.SWITCH_KEYBOARD_MOUSE)
		{
			// Switch en mode souriscom
			frameKeyboard.setVisible( false );
			frameMouse.setVisible( true );
			
			// si on est en défilement
			if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
			{
				DefilementKeyEngine.getInstance().stopKeyDefilEngine();
			}
			frameMouse.startDefilMouse();
		}
		else if (actionType == TKeyClavicomActionType.SWITCH_MOUSE_KEYBOARD)
		{
			// Switch en mode clavicom
			frameMouse.setVisible( false );
			frameKeyboard.setVisible( true );
			
			// si on est en défilement
			if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
			{
				DefilementKeyEngine.getInstance().startKeyDefilEngine();
			}
			
			frameMouse.stopDefilMouse();
		}
		else if (actionType == TKeyClavicomActionType.SWITCH_MOUSECLICK_MOUSEMOVE)
		{
			// Switch en mode clavicom
			frameMouse.SwitchMoveMode();
		}
		else if (actionType == TKeyClavicomActionType.SWITCH_MOUSEMOVE_MOUSECLICK)
		{
			// Switch en mode clavicom
			frameMouse.SwitchClickMode();
		}
	}

	public UIKeyboardFrame getFrameKeyboard()
	{
		return frameKeyboard;
	}

	public void setFrameKeyboard(UIKeyboardFrame frameKeyboard)
	{
		this.frameKeyboard = frameKeyboard;
	}

	public UIMouseFrame getFrameMouse()
	{
		return frameMouse;
	}

	public void setFrameMouse(UIMouseFrame frameMouse)
	{
		this.frameMouse = frameMouse;
	}
	
	
	
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
