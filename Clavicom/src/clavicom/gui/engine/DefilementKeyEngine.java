/*-----------------------------------------------------------------------------+

			Filename			: DefilementKeyEngine.java
			Creation date		: 20 juin 07
		
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

import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.engine.click.clickMouseHookListener;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyGroup;
import clavicom.gui.keyboard.keyboard.UIKeyList;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.listener.DefilListener;
import clavicom.tools.TUIKeyState;

public class DefilementKeyEngine implements DefilListener, clickMouseHookListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	UIKeyboard uiKeyboard;
	int currentTypeDefil; // type de defilement en cours
	
	UIKeyGroup currentGroup; // groupe en cours
	UIKeyList currentList; // liste en cours
	UIKeyKeyboard currentKey; // key en cours
	
	int currentIndexDefilementGroup;
	int currentIndexDefilementList;
	int currentIndexDefilementKey;
	
	DefilementEngine defilementEngine;
	ClickEngine clicEngine;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public DefilementKeyEngine( 
			UIKeyboard myUIKeyboard, 
			ClickEngine myClicEngine, 
			DefilementEngine myDefilementEngine,
			boolean started )
	{
		uiKeyboard = myUIKeyboard;
		
		// type de défilement
		//		0 : groupe
		//		1 : list
		//		2 : key
		currentTypeDefil = 0;
		
		defilementEngine = myDefilementEngine;
		clicEngine = myClicEngine;
		
		myClicEngine.addClickMouseHookListener( this );
		defilementEngine.addDefilListener( this );
		
		currentIndexDefilementGroup = 0;
		currentIndexDefilementList = 0;
		currentIndexDefilementKey = 0;
		
		currentGroup = null;
		currentList = null;
		currentKey = null;
		
		if (started)
			startKeyDefilement();
	}
	
	
	public void startKeyDefilement()
	{
		clicEngine.startHook();
		defilementEngine.startDefilement();
	}
	
	public void stopKeyDefilement()
	{
		defilementEngine.stopDefilement();
		clicEngine.stopMouseHook();
	}

	public void defil()
	{
		switch(currentTypeDefil)
		{
			case 0: // groupes
				
				// on remet en normal l'ancien groupe, et on séléction le nouveau
				if( currentGroup != null )
				{
					currentGroup.select( false );
				}
				
				if( currentIndexDefilementGroup >= (uiKeyboard.getGroupeListSize()-1) )
				{
					currentIndexDefilementGroup = 0;
				}
				else
				{
					currentIndexDefilementGroup++;
				}
				
				currentGroup = uiKeyboard.getUIKeyGroup( currentIndexDefilementGroup );
				currentGroup.select( true );
				
				break;
			case 1: // listes

				// on remet en normal l'ancienne liste, et on séléction la nouvelle
				if( currentList != null )
				{
					currentList.select( false );
				}
				
				if( currentIndexDefilementList >= (currentGroup.getKeyLists().size() - 1) )
				{
					currentIndexDefilementList = 0;
				}
				else
				{
					currentIndexDefilementList++;
				}
				
				currentList = currentGroup.getKeyLists().get( currentIndexDefilementList );
				currentList.select( true );
				break;
			case 2: // key
				
				// on remet en normal l'ancienne key, et on séléction la nouvelle
				if( currentKey != null )
				{
					currentKey.forceState( TUIKeyState.NORMAL );
				}
				
				if( currentIndexDefilementKey >= (currentList.getKeys().size() - 1) )
				{
					currentIndexDefilementKey = 0;
				}
				else
				{
					currentIndexDefilementKey++;
				}
				
				currentKey = currentList.getKeys().get( currentIndexDefilementKey );
				currentKey.forceState( TUIKeyState.ENTERED );
				break;
			default:
				break;
		}
	}

	public void clickMouseHook()
	{
		// on change de mode de défilmement
		// si on est en mode groupe, on passe en mode liste, etc...
		switch(currentTypeDefil)
		{
			case 0: // groupes
				currentTypeDefil = 1;
				currentIndexDefilementList = 0;
				break;
			case 1: // listes
				currentTypeDefil = 2;
				currentIndexDefilementKey = 0;
				break;
			case 2: // key
				// click sur la key
				if( currentKey != null )
				{
					currentKey.simulateClick();
				}
				currentTypeDefil = 0;
				currentIndexDefilementGroup = 0;
				break;
			default:
				currentTypeDefil = 0;
				currentIndexDefilementGroup = 0;
				break;
		}
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}


