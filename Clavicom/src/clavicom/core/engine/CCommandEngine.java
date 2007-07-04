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
import clavicom.core.keygroup.CKey;
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
import clavicom.core.listener.OnClickKeyDynamicStringCommandListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.listener.ReleaseHoldableKeysListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.tools.TKeyAction;
import clavicom.tools.TLevelEnum;

public class CCommandEngine implements OnClickKeyCharacterListener,OnClickKeyShortcutListener,OnClickKeyDynamicStringCommandListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	protected EventListenerList listenerNewMessageList;
	
	static CCommandEngine instance;
	
	List<CKey> holdKey;
	
	Robot robot;
	
	protected EventListenerList listenerList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected CCommandEngine( CKeyboard keyboard )
	{
		listenerNewMessageList = new EventListenerList();
		
		holdKey = new ArrayList<CKey>();
		
		try
		{
			robot = new Robot();
		}
		catch ( AWTException e )
		{
			CMessageEngine.newError( UIString.getUIString( "MSG_COMMAND_ENGINE_NO_ROBOT" ), e.getMessage() );
			return;
		}
		
		// Abonnement aux listeners
		listen( keyboard );
		
		listenerList = new EventListenerList();
		
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
				((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerCommand( this );
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
									((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerCommand( this );
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
				((CKeyDynamicString)keyboardKey).removeOnClickKeyDynamicStringListenerCommand( this );
			}else if( keyboardKey instanceof CKeyShortcut )
			{
				((CKeyShortcut)keyboardKey).removeOnClickKeyShortcutListener( this );
			}
		}
	}
	
	
	
	protected void executeCommande( List<CCommand> commandList, CKey key )
	{
		CCode code = null;
		

		// ===============================================================
		// Cas des touche holdable
		// ===============================================================
		if( key.isHoldable() )
		{
			// si elle est déjà dans la liste, alors, on le retire de la liste
			if( holdKey.contains( key ) )
			{
				// on le retire de la liste
				holdKey.remove( key );
			}
			else
			{
				// on l'ajoute a la liste
				holdKey.add( key );
			}
		} else
		{
			// ===============================================================
			// touche non holdable
			// ===============================================================
			
			
			// on fait le press des touches holdable
			for( CKey holdableKey : holdKey )
			{
				if( holdableKey instanceof CKeyCharacter )
				{
					DoKeyCharacter( (CKeyCharacter)holdableKey, false );
				}
			}
			

			// on execute la touche normalement
			for( CCommand command : commandList )
			{
				for( int i = 0 ; i < command.Size() ; ++i )
				{
					code = command.GetCode( i );
					if( code.GetKeyAction() == TKeyAction.PRESSED )
					{
						doKeyCodePress( code );	
					}
					else if( code.GetKeyAction() == TKeyAction.RELEASED )
					{
						doKeyCodeRelease( code );
					}
				}
			}
			
			
			
			
			// on fait le relesed des touches holdable
			for( CKey holdableKey : holdKey )
			{
				if( holdableKey instanceof CKeyCharacter )
				{
					DoKeyCharacter( (CKeyCharacter)holdableKey, true );
				}
			}
			
			
			
		
			
			// on vide la liste des touche holdable
			holdKey.clear();
			

			// on change le level, on se remet en normal
			CLevelEngine.getInstance().setCurrentLevel( TLevelEnum.NORMAL );
			
			
			// on prévient l'UI pour que les holdKeys soient déséléctionnées
			fireReleaseHoldableKeys();
			
			
			
			
		}
	}
	
	
	// ========================================================|
	// COMMAND ACTION =========================================|
	// ========================================================|
	
	private void DoKeyCharacter(CKeyCharacter character, boolean release )
	{
		CCommand command = character.getCommand( CLevelEngine.getInstance().getCurrentLevel() );
		CCode code = null;
		
		if( command != null )
		{
			for( int i = 0 ; i < command.Size() ; ++i )
			{
				code = command.GetCode( i );
				if( release )
				{
					if( code.GetKeyAction() == TKeyAction.RELEASED )
					{
						doKeyCodeRelease( code );
					}
				}
				else
				{
					if( code.GetKeyAction() == TKeyAction.PRESSED )
					{
						doKeyCodePress( code );
					}
				}
			}
		}
	}
	
	protected void doKeyCodePress( CCode code )
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
	
	protected void doKeyCodeRelease( CCode code )
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

	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		// Cas normaux
		if(keyCharacter.getCommand(CLevelEngine.getInstance().getCurrentLevel()) == null)
			return;
		
		List<CCommand> commandList = new ArrayList<CCommand>();

		CCommand keyCommand = keyCharacter.getCommand( CLevelEngine.getInstance().getCurrentLevel() );

		commandList.add( keyCommand );

		executeCommande( commandList, keyCharacter );
		
		
	}

	public void onClickKeyShortcut(CKeyShortcut keyShortcut)
	{
		if(keyShortcut.getCommand() == null)
			return;
		
		List<CCommand> commandList = new ArrayList<CCommand>();
		commandList.add( keyShortcut.getCommand() );
		
		executeCommande( commandList, keyShortcut );
		
	}

	public void onClickKeyDynamicStringCommand(CKeyDynamicString keyDynamicString)
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
		
		// on enlève les première lettre déja écrites
		if( commandList.size() < keyDynamicString.getCurrentIndex() )
		{
			commandList.clear();
		}
		else
		{
			for(int i = 0 ; i < keyDynamicString.getCurrentIndex() ; ++i )
			{
				commandList.remove( 0 );
				//KeyEvent.VK_EURO_SIGN;
			}
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
			executeCommande( commandList, keyDynamicString );
		}
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
	// ========================================================|
	// Listener release holdable keys =========================|
	// ========================================================|
	public void addReleaseHoldableKeysListener(ReleaseHoldableKeysListener l)
	{
		this.listenerList.add(ReleaseHoldableKeysListener.class, l);
	}

	public void removeReleaseHoldableKeysListener(ReleaseHoldableKeysListener l)
	{
		this.listenerList.remove(ReleaseHoldableKeysListener.class, l);
	}

	protected void fireReleaseHoldableKeys( )
	{
		ReleaseHoldableKeysListener[] listeners = (ReleaseHoldableKeysListener[]) listenerList
				.getListeners(ReleaseHoldableKeysListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].releasedHoldableKeys();
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|

	
}
