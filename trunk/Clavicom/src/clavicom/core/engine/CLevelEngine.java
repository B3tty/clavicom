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


import java.util.ArrayList;
import java.util.List;

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
	
	protected List<CKeyLevel> alwaysHoldableList;

	static CLevelEngine instance;
	
	// ------------------------------------------------------ CONSTRUCTEURS --//
	public static void createInstance( CKeyboard keyboard )
	{
		instance = new CLevelEngine( keyboard );
	}
	
	public static CLevelEngine getInstance()
	{
		return instance;
	}
	
	protected CLevelEngine( CKeyboard keyboard )
	{
		listenerChangeLevelList = new EventListenerList();
		
		currentLevel = TLevelEnum.NORMAL;
		
		alwaysHoldableList = new ArrayList<CKeyLevel>();
		
		// Abonnement aux listeners
		listen( keyboard );
	}
	
	public void listen( CKeyKeyboard keyboardKey )
	{
		if( keyboardKey != null )
		{
			// on cast pour savoir de quel type est la key
			if( keyboardKey instanceof CKeyLevel )
			{
				((CKeyLevel)keyboardKey).addOnClickKeyLevelListener( this );
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
	

	public void unListen( CKeyKeyboard keyboardKey )
	{
		// on cast pour savoir de quel type est la key
		if( keyboardKey instanceof CKeyLevel )
		{
			((CKeyLevel)keyboardKey).removeOnClickKeyLevelListener( this );
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
		if (keyLevel.GetLevel() == null)
			return;
		
		if( keyLevel.isAlwaysHoldable() )
		{
			// si elle est déjà dans la liste
			if( alwaysHoldableList.contains( keyLevel ) )
			{
				alwaysHoldableList.remove( keyLevel );
			}
			else
			{
				alwaysHoldableList.add( keyLevel );
			}
		}
		

		
		// Si le level est le même et si c'est pas une touche isAlwaysHoldable, 
		// on repasse au level normal
		if( (keyLevel.GetLevel() == currentLevel)
				&&
			( ! keyLevel.isAlwaysHoldable() ) )
		{
			// mais il existe des always holdable keys, on met le niveau à celle-là
			if( alwaysHoldableList.size() > 0 )
			{
				currentLevel = alwaysHoldableList.get(0).GetLevel();
			}
			else
			{
				currentLevel = TLevelEnum.NORMAL;
			}
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

	public void setCurrentLevel(TLevelEnum myCurrentLevel, boolean force )
	{
		
		// si le level est le même que celui courant, on ne fait rien
		if( myCurrentLevel == currentLevel )
		{
			return;
		}
		
		// s'il existe des always holdable keys, on met le niveau à celle-là
		if( myCurrentLevel == TLevelEnum.NORMAL )
		{
			if( alwaysHoldableList.size() > 0 )
			{
				if( force )
				{
					this.currentLevel = myCurrentLevel;
					alwaysHoldableList.clear();
				}
				else
				{
					this.currentLevel = alwaysHoldableList.get(0).GetLevel();
				}
			}
			else
			{
				this.currentLevel = TLevelEnum.NORMAL;
			}
		}
		else
		{
			this.currentLevel = myCurrentLevel;
		}
		
		fireChangeLevel();
		
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
