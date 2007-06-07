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


import javax.swing.event.EventListenerList;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.listener.ChangeLevelListener;
import clavicom.core.listener.OnClickKeyLevelListener;
import clavicom.core.profil.CKeyboard;
import clavicom.tools.TLevelEnum;

public class CLevelEngine implements OnClickKeyLevelListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TLevelEnum currentLevel;

	protected EventListenerList listenerChangeLevelList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CLevelEngine( CKeyboard keyboard )
	{
		listenerChangeLevelList = new EventListenerList();
		
		currentLevel = TLevelEnum.NORMAL;
		
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
								if( keyboardKey instanceof CKeyLevel )
								{
									((CKeyLevel)keyboardKey).addOnClickKeyLevelListener( this );
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
	public void addChangeLevelListener(ChangeLevelListener l)
	{
		this.listenerChangeLevelList.add(ChangeLevelListener.class, l);
	}

	public void removeChangeLevelListener(ChangeLevelListener l)
	{
		this.listenerChangeLevelList.remove(ChangeLevelListener.class, l);
	}

	protected void fireChangeLevel( )
	{
		ChangeLevelListener[] listeners = (ChangeLevelListener[]) listenerChangeLevelList
				.getListeners(ChangeLevelListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].changeLevel( currentLevel );
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|
	
	public void onClickKeyLevel(CKeyLevel keyLevel)
	{
		// Si le level est le même, on repasse au level normal
		if( keyLevel.GetLevel() == currentLevel )
		{
			currentLevel = TLevelEnum.NORMAL;
		}
		else
		{
			// changement de level
			currentLevel = keyLevel.GetLevel();
		}
		
		
		fireChangeLevel();
		
	}

	public TLevelEnum getCurrentLevel()
	{
		return currentLevel;
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
