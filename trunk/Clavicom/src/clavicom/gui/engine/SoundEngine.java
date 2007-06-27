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

package clavicom.gui.engine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import clavicom.CFilePaths;
import clavicom.core.profil.CProfil;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.keyboard.UIKeyGroup;
import clavicom.gui.keyboard.keyboard.UIKeyList;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.DefilListener;
import clavicom.gui.listener.KeyEnteredListener;
import clavicom.gui.listener.KeyPressedListener;
import clavicom.tools.TNavigationType;

public class SoundEngine implements DefilListener, KeyEnteredListener, KeyPressedListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	Clip scrollClip;
	Clip enteredClip;
	Clip clickedClip;
	
	static SoundEngine instance;

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
		

		// Chargement des sons
		
		try
		{
			// file
			File scrollSoundFile = new File( CFilePaths.getScrollSoundFilePath() );
			
			// AudioInputStream
			AudioInputStream ais_scroll = AudioSystem.getAudioInputStream( scrollSoundFile );
	              
			DataLine.Info info = new DataLine.Info(Clip.class, ais_scroll.getFormat());      
			scrollClip = (Clip) AudioSystem.getLine(info);
			scrollClip.open();
	        
		}
		catch (Exception ex)
		{
			throw new Exception(UIString.getUIString("EX_SOUND_ENGINE_BUILD") + "\n" +ex.getMessage() );
		}
		
		try
		{
			// file
			File enteredSoundFile = new File( CFilePaths.getEnteredSoundFilePath() );
			
			// AudioInputStream;
			AudioInputStream ais_entered = AudioSystem.getAudioInputStream( enteredSoundFile );
			     
			DataLine.Info info = new DataLine.Info(Clip.class, ais_entered.getFormat());      
			enteredClip = (Clip) AudioSystem.getLine(info);
			enteredClip.open();
		}
		catch (Exception ex)
		{
			throw new Exception(UIString.getUIString("EX_SOUND_ENGINE_BUILD") + "\n" +ex.getMessage() );
		}
		
		try
		{
			// file
			File clickedSoundFile = new File( CFilePaths.getClickSoundFilePath() );
			
			// AudioInputStream
			AudioInputStream ais_clicked = AudioSystem.getAudioInputStream( clickedSoundFile );
			      
			DataLine.Info info = new DataLine.Info(Clip.class, ais_clicked.getFormat());      
			clickedClip = (Clip) AudioSystem.getLine(info);
			clickedClip.open();
		}
		catch (Exception ex)
		{
			throw new Exception(UIString.getUIString("EX_SOUND_ENGINE_BUILD") + "\n" +ex.getMessage() );
		}
        
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
			if( scrollClip.isRunning() )
			{
				scrollClip.stop();
			}
			scrollClip.start();
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
