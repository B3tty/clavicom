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
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyDynamicString;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyAction;

public class CCommandEngine implements OnClickKeyCharacterListener,OnClickKeyShortcutListener,OnClickKeyDynamicStringListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	protected EventListenerList listenerNewMessageList;
	
	
	static CCommandEngine instance;

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected CCommandEngine( CKeyboard keyboard )
	{

		
		listenerNewMessageList = new EventListenerList();
		
		// Abonnement aux listeners
		listen( keyboard );
	}

	public static void createInstance( CKeyboard keyboard )
	{
		instance = new CCommandEngine( keyboard );
	}
	
	public static CCommandEngine getInstance()
	{
		return instance;
	}
	//----------------------------------------------------------- METHODES --//
	
	public void listen( CKeyKeyboard keyboardKey )
	{
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
	
	public void unListen( CKeyKeyboard keyboardKey )
	{
		if( keyboardKey != null )
		{
			// on cast pour savoir de quel type est la key
			if( keyboardKey instanceof CKeyCharacter )
			{
				((CKeyCharacter)keyboardKey).removeOnClickKeyCharacterListener( this );
			}else if( keyboardKey instanceof CKeyDynamicString )
			{
				((CKeyDynamicString)keyboardKey).removeOnClickKeyDynamicStringListener( this );
			}else if( keyboardKey instanceof CKeyShortcut )
			{
				((CKeyShortcut)keyboardKey).removeOnClickKeyShortcutListener( this );
			}
		}
	}
	
	
	
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
			CMessageEngine.newError( UIString.getUIString( "MSG_COMMAND_ENGINE_NO_ROBOT" ), e.getMessage() );
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
						CMessageEngine.newError( UIString.getUIString( "MSG_COMMAND_ENGINE_CODE_INCORECT" ) );
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
						CMessageEngine.newError( UIString.getUIString( "MSG_COMMAND_ENGINE_CODE_INCORECT" ) );
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
		if(keyCharacter.getCommand(CLevelEngine.getInstance().getCurrentLevel()) == null)
			return;
		
		List<CCommand> commandList = new ArrayList<CCommand>();

		commandList.add( keyCharacter.getCommand( CLevelEngine.getInstance().getCurrentLevel() ) );
		
		executeCommande( commandList );
	}

	public void onClickKeyShortcut(CKeyShortcut keyShortcut)
	{
		if(keyShortcut.getCommand() == null)
			return;
		
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
			return;
		}
		
		// s'il l'option "ajout d'un espace aprés la chaine" est coché
		if( CProfil.getInstance().getAdvancedOption().isAddSpaceAfterString() )
		{
			CCommand commandEspace = CCommandSet.GetInstance().GetCommand(" ");
			if( commandEspace != null )
			{
				commandList.add( commandEspace );
			}
		}
		
		if(commandList != null)
		{
			executeCommande( commandList );
		}
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
