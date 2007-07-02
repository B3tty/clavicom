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

import clavicom.core.profil.CProfil;
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
	
	int nbCurrentDefilement; // defilement courant
							//utilisé pour ne rester que trois fois de suite sur un niveau
	
	int nbTurnLevelMax; // nombre de tour que l'on peut faire sur le niveau
						// list et key
	
	
	static DefilementKeyEngine instance;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	protected DefilementKeyEngine( 
			UIKeyboard myUIKeyboard )
	{
		uiKeyboard = myUIKeyboard;
		
		// type de défilement
		//		0 : groupe
		//		1 : list
		//		2 : key
		currentTypeDefil = 0;

		
		currentIndexDefilementGroup = 0;
		currentIndexDefilementList = 0;
		currentIndexDefilementKey = 0;
		
		nbCurrentDefilement = 0;
		
		nbTurnLevelMax = CProfil.getInstance().getAdvancedOption().getNumberOfDefilTurn();
		
		currentGroup = null;
		currentList = null;
		currentKey = null;

	}
	
	public static void createInstance(
			UIKeyboard myUIKeyboard)
	{
		instance = new DefilementKeyEngine( myUIKeyboard );
	}
	public static DefilementKeyEngine getInstance()
	{
		return instance;
	}
	
	public void startKeyDefilEngine()
	{
		currentTypeDefil = 0;
		currentIndexDefilementGroup = 0;
		currentIndexDefilementList = 0;
		currentIndexDefilementKey = 0;
		nbCurrentDefilement = 0;
		currentGroup = null;
		currentList = null;
		currentKey = null;
		
		ClickEngine.getInstance().removeChangeLevelListener( this );
		DefilementEngine.getInstance().removeDefilListener( this );
		ClickEngine.getInstance().addClickMouseHookListener( this );
		DefilementEngine.getInstance().addDefilListener( this );
		
	}
	
	public void stopKeyDefilEngine()
	{
		ClickEngine.getInstance().removeChangeLevelListener( this );
		DefilementEngine.getInstance().removeDefilListener( this );
	}


	public void defil()
	{
		
		switch(currentTypeDefil)
		{
			case 0: // groupes
				
				if( uiKeyboard != null )
				{
					// on remet en normal l'ancien groupe, et on séléction le nouveau
					if( currentGroup != null )
					{
						currentGroup.simulateEnter( false );
					}
					
					if( currentIndexDefilementGroup >= (uiKeyboard.getGroupeListSize()-1) )
					{
						currentIndexDefilementGroup = 0;
						nbCurrentDefilement++;
					}
					else
					{
						currentIndexDefilementGroup++;
					}
					
					currentGroup = uiKeyboard.getUIKeyGroup( currentIndexDefilementGroup );
					currentGroup.simulateEnter( true );
					
				}

				
				break;
			case 1: // listes

				
				if( currentGroup != null )
				{
					// on remet en normal l'ancienne liste, et on séléction la nouvelle
					if( currentList != null )
					{
						currentList.simulateEnter( false );
					}
					
					if( currentIndexDefilementList >= (currentGroup.getKeyLists().size() - 1) )
					{
						currentIndexDefilementList = 0;
						nbCurrentDefilement++;
					}
					else
					{
						currentIndexDefilementList++;
					}
					
					currentList = currentGroup.getKeyLists().get( currentIndexDefilementList );
					currentList.simulateEnter( true );
					
					// si le nombre de tour sur le niveau est supérieur à trois
					
					if( nbCurrentDefilement > nbTurnLevelMax )
					{
						nbCurrentDefilement = 0;
						
						// on repasse en mode de defilement group
						currentTypeDefil = 0;
					}
				}

				break;
			case 2: // key
				
				if( currentList != null )
				{
					// on remet en normal l'ancienne key, et on séléction la nouvelle
					if( currentKey != null )
					{
						currentKey.forceState( TUIKeyState.NORMAL );
					}
					
					
					if( currentIndexDefilementKey >= (currentList.getKeys().size() - 1) )
					{
						currentIndexDefilementKey = 0;
						nbCurrentDefilement++;
					}
					else
					{
						currentIndexDefilementKey++;
					}
					
					currentKey = currentList.getKeys().get( currentIndexDefilementKey );
					currentKey.forceState( TUIKeyState.ENTERED );
					
					// si le nombre de tour sur le niveau est supérieur à trois
					if( nbCurrentDefilement > nbTurnLevelMax )
					{
						nbCurrentDefilement = 0;
						
						// on repasse en mode de defilement list
						currentTypeDefil = 1;
					}
				}
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
				nbCurrentDefilement = 0;
				if( currentGroup != null )
				{
					currentGroup.simulateEnter( false );
				}
				break;
			case 1: // listes
				currentTypeDefil = 2;
				currentIndexDefilementKey = 0;
				nbCurrentDefilement = 0;
				if( currentList != null )
				{
					currentList.simulateEnter( false );
				}
				break;
			case 2: // key
				// click sur la key
				if( currentKey != null )
				{
					currentKey.forceState( TUIKeyState.NORMAL );
					currentKey.simulateClick();
				}
				currentTypeDefil = 0;
				currentIndexDefilementGroup = 0;
				nbCurrentDefilement = 0;
				currentKey = null;
				currentList = null;
				currentGroup = null;
				break;
			default:
				currentTypeDefil = 0;
				currentIndexDefilementGroup = 0;
				nbCurrentDefilement = 0;
				break;
		}
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}


