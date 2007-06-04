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

package clavicom.core.engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.CCode;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyDynamicString;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyboardKey;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.profil.CKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.message.CMessage;
import clavicom.gui.message.NewMessageListener;
import clavicom.tools.TKeyAction;

public class CCommandEngine implements OnClickKeyCharacterListener,OnClickKeyShortcutListener,OnClickKeyDynamicStringListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	protected EventListenerList listenerNewMessageList;
	
	protected CLevelEngine levelEngine;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CCommandEngine( CKeyboard keyboard, CLevelEngine myLevelEngine )
	{

		levelEngine = myLevelEngine;
		
		listenerNewMessageList = new EventListenerList();
		
		// =============================================================
		// Abonnement aux listener
		// =============================================================
		for( int i = 0 ; i < keyboard.size() ; ++i )
		{
			CKeyGroup keyGroup = keyboard.getKeyGroup( i );
			if( keyGroup != null )
			{
				for( int j = 0 ; j < keyGroup.size() ; ++j )
				{
					CKeyList keyList = keyGroup.GetkeyList( j );
					if( keyList != null )
					{
						for( int k = 0 ;  k < keyList.size() ; ++k )
						{
							CKeyboardKey keyboardKey = keyList.GetKeyboardKey( k );
							if( keyboardKey != null )
							{
								// on cast pour savoir de quel type est la key
								if( keyboardKey instanceof CKeyCharacter )
								{
									((CKeyCharacter)keyboardKey).addOnClickKeyCharacterListener( this );
								}else if( keyboardKey instanceof CKeyDynamicString )
								{
									((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListener( this );
								}else if( keyboardKey instanceof CKeyShortcut )
								{
									((CKeyShortcut)keyboardKey).addOnClickKeyShortcutListener( this );
								}
							}
						}
					}
				}
			}
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	// ========================================================|
	// Listener ===============================================|
	// ========================================================|
	public void addNewMessageListener(NewMessageListener l)
	{
		this.listenerNewMessageList.add(NewMessageListener.class, l);
	}
	
	public void removeNewMessageListener(NewMessageListener l)
	{
		this.listenerNewMessageList.remove(NewMessageListener.class, l);
	}
	
	
	protected void fireNewMessage( CMessage message )
	{
		NewMessageListener[] listeners = (NewMessageListener[]) listenerNewMessageList
				.getListeners(NewMessageListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].newMessage( message );
		}
	}
	// ========================================================|
	// fin Listener ==========================================|
	// ========================================================|
	
	protected void executeCommande( List<CCommand> commandList )
	{
		Robot robot = null;
		CCode code = null;
		
		
		try
		{
			robot = new Robot();
		}
		catch ( AWTException e )
		{
			CMessage message = new CMessage( UIString.getUIString( "MSG_COMMAND_ENGINE_NO_ROBOT" ) );
			fireNewMessage( message );
			return;
		}
		
		for( CCommand command : commandList )
		{
			for( int i = 0 ; i < command.Size() ; ++i )
			{
				code = command.GetCode( i );
				if( code.GetKeyAction() == TKeyAction.PRESSED )
				{
					try
					{
						robot.keyPress( code.GetKeyEvent() );
					}
					catch(Exception ex)
					{
						CMessage message = new CMessage( UIString.getUIString( "MSG_COMMAND_ENGINE_CODE_INCORECT" ) );
						fireNewMessage( message );
						return;
					}
				}
				else if( code.GetKeyAction() == TKeyAction.RELEASED )
				{
					try
					{
						robot.keyRelease( code.GetKeyEvent() );
					}
					catch(Exception ex)
					{
						CMessage message = new CMessage( UIString.getUIString( "MSG_COMMAND_ENGINE_CODE_INCORECT" ) );
						fireNewMessage( message );
						return;
					}
				}
			}
		}
	}
	
	
	// ========================================================|
	// COMMAND ACTION =========================================|
	// ========================================================|
	
	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		List<CCommand> commandList = new ArrayList<CCommand>();

		commandList.add( keyCharacter.getCommand( levelEngine.getCurrentLevel() ) );
		
		executeCommande( commandList );
	}

	public void onClickKeyShortcut(CKeyShortcut keyShortcut)
	{
		List<CCommand> commandList = new ArrayList<CCommand>();
		commandList.add( keyShortcut.getCommand() );
		
		executeCommande( commandList );
		
	}

	public void onClickKeyDynamicString(CKeyDynamicString keyDynamicString)
	{
		List<CCommand> commandList = null;
		
		try
		{
			commandList = keyDynamicString.getCommands();
		}
		catch ( Exception e )
		{
			CMessage message = new CMessage( e.getMessage() );
			fireNewMessage( message );
			return;
		}
		
		if(commandList != null)
		{
			executeCommande( commandList );
		}
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
