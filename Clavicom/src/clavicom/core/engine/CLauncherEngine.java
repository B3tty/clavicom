/*-----------------------------------------------------------------------------+

			Filename			: CLauncherEngine.java
			Creation date		: 1 juin 07
		
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


import java.io.IOException;

import javax.swing.event.EventListenerList;

import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.listener.OnClickKeyLauncherListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CKeyboard;
import clavicom.gui.language.UIString;

public class CLauncherEngine implements OnClickKeyLauncherListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	protected EventListenerList listenerNewMessageList;

	static CLauncherEngine instance;
	
	// ------------------------------------------------------ CONSTRUCTEURS --//
	public static void createInstance( CKeyboard keyboard )
	{
		instance = new CLauncherEngine( keyboard );
	}
	
	public static CLauncherEngine getInstance()
	{
		return instance;
	}
	
	protected CLauncherEngine( CKeyboard keyboard )
	{
		listenerNewMessageList = new EventListenerList();
		
		// Abonnement aux listeners
		listen( keyboard );
	}
	
	public void listen( CKeyKeyboard keyboardKey )
	{
		if( keyboardKey != null )
		{
			// on cast pour savoir si le type est bien keyLauncher
			if( keyboardKey instanceof CKeyLauncher )
			{
				((CKeyLauncher)keyboardKey).addOnClickKeyLauncherListener( this );
			}
		}
	}
	
	public void listen( CKeyboard keyboard )
	{
		// abonnement au listener des keyLauncher
		
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
								// on cast pour savoir si le type est bien keyLauncher
								if( keyboardKey instanceof CKeyLauncher )
								{
									((CKeyLauncher)keyboardKey).addOnClickKeyLauncherListener( this );
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
			// on cast pour savoir si le type est bien keyLauncher
			if( keyboardKey instanceof CKeyLauncher )
			{
				((CKeyLauncher)keyboardKey).removeOnClickKeyLauncherListener( this );
			}
		}
	}
	

	public void onClickKeyLauncher(CKeyLauncher keyLauncher)
	{
		if(keyLauncher.getApplicationPath() == "")
			return;
		
		// Lancement de l'application
		try
		{
			Runtime.getRuntime().exec(keyLauncher.getApplicationPath());
		}
		catch (IOException e)
		{
			CMessageEngine.newError(UIString.getUIString("MSG_ENGINE_LAUNCHER_ERROR_1") + 
									keyLauncher.getApplicationPath() + 
									UIString.getUIString("MSG_ENGINE_LAUNCHER_ERROR_2"));			
		}

	}

	//----------------------------------------------------------- METHODES --//	
	

	//--------------------------------------------------- METHODES PRIVEES --//
}
