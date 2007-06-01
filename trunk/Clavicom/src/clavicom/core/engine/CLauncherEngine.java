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

import java.awt.Desktop.Action;
import java.io.File;

import javax.swing.event.EventListenerList;

import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyboardKey;
import clavicom.core.listener.ChangeLevelListener;
import clavicom.core.listener.OnClickKeyLauncherListener;
import clavicom.core.profil.CKeyboard;
import clavicom.gui.message.CMessage;
import clavicom.gui.message.NewMessageListener;

import org.jdesktop.jdic.*;
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;

public class CLauncherEngine implements OnClickKeyLauncherListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	protected EventListenerList listenerNewMessageList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CLauncherEngine( CKeyboard keyboard )
	{
		listenerNewMessageList = new EventListenerList();
		
		// abonnement au listener des keyLauncher
		
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

	public void onClickKeyLauncher(CKeyLauncher keyLauncher)
	{
		// lancement de l'application
		try
		{
			// String s = keyLauncher.getApplicationPath();
			String s = "C:\\Program Files\\QuickTime\\QuickTimePlayer.exe";
			Desktop.open( new File ( s ) );
		}
		catch ( DesktopException e )
		{
			CMessage message = new CMessage( e.getMessage() );
			fireNewMessage( message );
			return;
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

	//--------------------------------------------------- METHODES PRIVEES --//
}
