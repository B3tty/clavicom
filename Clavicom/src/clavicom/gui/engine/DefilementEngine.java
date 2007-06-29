/*-----------------------------------------------------------------------------+

			Filename			: DefilementEngine.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;
import clavicom.core.profil.CProfil;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.listener.DefilListener;
import clavicom.gui.mouse.UIMouse;

public class DefilementEngine
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Timer defilTimer;
	
	protected EventListenerList listenerList;
	static DefilementEngine instance;
	
	UIKeyboard uiKeyboard;
	UIMouse uiMouse;

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected DefilementEngine( )
	{
		// seront initialisés grace aux Set...
		uiKeyboard = null;
		uiMouse = null;
		
		listenerList = new EventListenerList();
		defilTimer = createSelectTimer( );
	}


	static public void createInstance(   )
	{
		instance = new DefilementEngine(  );
	}
	
	static public DefilementEngine getInstance(  )
	{
		return instance;
	}
	//----------------------------------------------------------- METHODES --//
	
	
	// ========================================================|
	// Listener ===============================================|
	// ========================================================|
	public void addDefilListener(DefilListener l)
	{
		this.listenerList.add(DefilListener.class, l);
	}

	public void removeDefilListener(DefilListener l)
	{
		this.listenerList.remove(DefilListener.class, l);
	}

	protected void fireDefil( )
	{
		DefilListener[] listeners = (DefilListener[]) listenerList
				.getListeners(DefilListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].defil();
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|

	
	public void startDefilement( )
	{
		if( defilTimer != null )
		{
			defilTimer.stop();
		}
		defilTimer = createSelectTimer( );
		defilTimer.start();
		
		// les keys ne fonctionnent plus
		uiKeyboard.setEnableKeys( false );
		uiMouse.setEnableKeys( false );
	}
	
	public void stopDefilement( )
	{
		defilTimer.stop();
		
		// les keys refonctionnent
		uiKeyboard.setEnableKeys( true );
		uiMouse.setEnableKeys( true );
	}
	
	public boolean isDefilement()
	{
		return defilTimer.isRunning();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected Timer createSelectTimer()
	{

		ActionListener action = new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				fireDefil();
			}
			
		};
		
		// Création d'un timer qui génère un tic - TODO mettre celui du profil
		int defilement = CProfil.getInstance().getNavigation().getTemporisationDefilement();
		return new Timer( defilement ,action );
	}


	public UIKeyboard getUiKeyboard()
	{
		return uiKeyboard;
	}


	public void setUiKeyboard(UIKeyboard uiKeyboard)
	{
		this.uiKeyboard = uiKeyboard;
	}


	public UIMouse getUiMouse()
	{
		return uiMouse;
	}


	public void setUiMouse(UIMouse uiMouse)
	{
		this.uiMouse = uiMouse;
	}
}

