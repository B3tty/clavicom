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

package clavicom.gui.engine;


import javax.swing.event.EventListenerList;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.listener.OnClickKeyClavicomListener;
import clavicom.core.profil.CKeyboard;
import clavicom.gui.windows.UIKeyboardFrame;
import clavicom.tools.TKeyClavicomActionType;

public class UIKeyClavicomEngine implements OnClickKeyClavicomListener
{
	//--------------------------------------------------------- CONSTANTES --//
	UIKeyboardFrame frameKeyboard;
	
	//---------------------------------------------------------- VARIABLES --//

	protected EventListenerList listeners;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIKeyClavicomEngine( CKeyboard keyboard )
	{
		listeners = new EventListenerList();
		
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
								if( keyboardKey instanceof CKeyClavicom )
								{
									((CKeyClavicom)keyboardKey).addOnClickKeyClavicomListener(this);
								}
							}
						}
					}
				}
			}
		}
	}

	//----------------------------------------------------------- METHODES --//

	
	public void onClickKeyClavicom(TKeyClavicomActionType actionType)
	{
		// TODO
		System.out.println( "CLAVICOM clicked !");
	}

	public UIKeyboardFrame getFrameKeyboard()
	{
		return frameKeyboard;
	}

	public void setFrameKeyboard(UIKeyboardFrame frameKeyboard)
	{
		this.frameKeyboard = frameKeyboard;
	}
	
	
	
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
