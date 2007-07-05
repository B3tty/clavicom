/*-----------------------------------------------------------------------------+

			Filename			: SoundEngine.java
			Creation date		: 27 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.engine

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

package clavicom.gui.engine.sound;


import clavicom.CFilePaths;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.DefilementEngine;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.keyboard.UIKeyGroup;
import clavicom.gui.keyboard.keyboard.UIKeyList;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.listener.DefilListener;
import clavicom.gui.listener.KeyEnteredListener;
import clavicom.gui.listener.KeyPressedListener;
import clavicom.tools.TNavigationType;

public class SoundEngine implements DefilListener, KeyEnteredListener, KeyPressedListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	static SoundEngine instance;
	
	ThreadSound clickThread;
	ThreadSound enteredThread;
	ThreadSound scrollThread;

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected SoundEngine( )
	{
		clickThread = new ThreadSound( CFilePaths.getClickSoundFilePath() );
		clickThread.start();

		enteredThread = new ThreadSound( CFilePaths.getEnteredSoundFilePath() );
		enteredThread.start();

		scrollThread = new ThreadSound( CFilePaths.getScrollSoundFilePath() );
		scrollThread.start();
		
	}
	
	public void listenEntered( UIKeyboard uiKeyboard )
	{
		// abonnement
		for( UIKeyGroup uiKeyGroup : uiKeyboard.getKeyGroups() )
		{
			for( UIKeyList uiKeyList : uiKeyGroup.getKeyLists() )
			{
				for( UIKey uiKey : uiKeyList.getKeys() )
				{
					uiKey.addKeyEnteredListener( this );
				}
			}
		}
	}
	public void listenPressed( UIKeyboard uiKeyboard )
	{
		// abonnement
		for( UIKeyGroup uiKeyGroup : uiKeyboard.getKeyGroups() )
		{
			for( UIKeyList uiKeyList : uiKeyGroup.getKeyLists() )
			{
				for( UIKey uiKey : uiKeyList.getKeys() )
				{
					uiKey.addKeyPressedListener( this );
				}
			}
		}
	}
	public void listenDefilement( )
	{
		DefilementEngine.getInstance().addDefilListener( this );
	}
	
	
	public void unListenEntered( UIKeyboard uiKeyboard )
	{
		// déabonnement
		for( UIKeyGroup uiKeyGroup : uiKeyboard.getKeyGroups() )
		{
			for( UIKeyList uiKeyList : uiKeyGroup.getKeyLists() )
			{
				for( UIKey uiKey : uiKeyList.getKeys() )
				{
					uiKey.removeKeyEnteredListener( this );
				}
			}
		}
	}
	public void unListenPressed( UIKeyboard uiKeyboard )
	{
		// déabonnement
		for( UIKeyGroup uiKeyGroup : uiKeyboard.getKeyGroups() )
		{
			for( UIKeyList uiKeyList : uiKeyGroup.getKeyLists() )
			{
				for( UIKey uiKey : uiKeyList.getKeys() )
				{
					uiKey.removeKeyPressedListener( this );
				}
			}
		}
	}
	public void unListenDefilement( )
	{
		DefilementEngine.getInstance().removeDefilListener( this );
	}
	
	


	public static void createInstance( )
	{
		instance = new SoundEngine( );
	}
	
	public static SoundEngine getInstance()
	{
		return instance;
	}
	//----------------------------------------------------------- METHODES --//	
	
	public void defil()
	{
		// si on est en mode défilement
		if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
		{
			scrollThread.play();
		}
	}

	public void keyEntered()
	{
		if( CProfil.getInstance().getNavigation().getTypeNavigation() != TNavigationType.DEFILEMENT )
		{
			enteredThread.play();
		}
	}

	public void keyPressed()
	{
		clickThread.play();
	}

	//--------------------------------------------------- METHODES PRIVEES --//

}


