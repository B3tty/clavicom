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
	
	ThreadSound soundThread;

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected SoundEngine( UIKeyboard uiKeyboard ) throws Exception
	{
		// abonnement
		for( UIKeyGroup uiKeyGroup : uiKeyboard.getKeyGroups() )
		{
			for( UIKeyList uiKeyList : uiKeyGroup.getKeyLists() )
			{
				for( UIKey uiKey : uiKeyList.getKeys() )
				{
					uiKey.addKeyEnteredListener( this );
					uiKey.addKeyPressedListener( this );
				}
			}
		}
		
		DefilementEngine.getInstance().addDefilListener( this );
		
		soundThread = new ThreadSound( CFilePaths.getClickSoundFilePath() );
	}


	public static void createInstance( UIKeyboard uiKeyboard ) throws Exception
	{
		instance = new SoundEngine( uiKeyboard );
	}
	
	public SoundEngine getInstance()
	{
		return instance;
	}
	//----------------------------------------------------------- METHODES --//	
	
	public void defil()
	{
		// si on est en mode défilement
		if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
		{
			soundThread = new ThreadSound( CFilePaths.getClickSoundFilePath() );
			soundThread.start();
		}
	}

	public void keyEntered()
	{
		
	}

	public void keyPressed()
	{
		// TODO Auto-generated method stub
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//

}

class ThreadSound extends Thread
{
	String soundPath;
	
	public ThreadSound( String mySoundPath )
	{
		soundPath = mySoundPath;
	}
	
	@Override
	public void run()
	{
		super.run();
		
		Sound.readAudioFile( soundPath );
	}
}
