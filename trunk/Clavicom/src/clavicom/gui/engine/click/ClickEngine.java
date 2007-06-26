/*-----------------------------------------------------------------------------+

			Filename			: ClickEngine.java
			Creation date		: 20 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.engine.click

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

package clavicom.gui.engine.click;

import javax.swing.event.EventListenerList;

import clavicom.core.profil.CProfil;
import clavicom.tools.TClickSouricomEnum;

public class ClickEngine
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Thread threadMouseHook = new Thread()
	{
		public void run() 
		{
			super.run();

			CProfil profil = CProfil.getInstance();
			
			// Initialisation du hook en passant le type de touche en parametre
			// 		0 : WM_LBUTTONUP
			// 		1 : WM_LBUTTONDOWN
			// 		2 : WM_RBUTTONDOWN
			// 		3 : WM_RBUTTONUP
			if( profil.getAdvancedOption().getClickSouricom() == TClickSouricomEnum.LEFT_RELEASE )
			{
				InitMouseHook( 0 );
			} else if( profil.getAdvancedOption().getClickSouricom() == TClickSouricomEnum.LEFT_PRESS )
			{
				InitMouseHook( 1 );
			} else if( profil.getAdvancedOption().getClickSouricom() == TClickSouricomEnum.RIGHT_PRESS )
			{
				InitMouseHook( 2 );
			} else if( profil.getAdvancedOption().getClickSouricom() == TClickSouricomEnum.RIGHT_RELEASE )
			{
				InitMouseHook( 3 );
			}
		}
		
		@Override
		protected void finalize() throws Throwable
		{
			super.finalize();
			
			FinishMouseHook();
		}
		
	};
	
	static ClickEngine instance;
	
	protected EventListenerList listenerList;
	
	// déclaration des méthodes natives
	public native void InitMouseHook( int click );
	public native void FinishMouseHook();
	public native void InhibitMouseHook( boolean inibit );

	//------------------------------------------------------ CONSTRUCTEURS --//
	protected ClickEngine( String dllPath )
	{
		listenerList = new EventListenerList();
		
		System.loadLibrary( dllPath );	
	}
	
	static public void createInstance( String dllPath )
	{
		instance = new ClickEngine( dllPath );
	}
	
	static public ClickEngine getInstance(  )
	{
		return instance;
	}

	//----------------------------------------------------------- METHODES --//
	public void startHook()
	{
		// Lancement du Hook
		threadMouseHook.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stopMouseHook()
	{
		threadMouseHook.stop();
		FinishMouseHook();
	}
	
	public void mouseHookPause()
	{
		InhibitMouseHook( true );
	}
	
	public void mouseHookResume()
	{
		InhibitMouseHook( false );
	}
	
	
	
	
	
	// ========================================================|
	// Listener ===============================================|
	// ========================================================|
	public void addClickMouseHookListener(clickMouseHookListener l)
	{
		this.listenerList.add(clickMouseHookListener.class, l);
	}

	public void removeChangeLevelListener(clickMouseHookListener l)
	{
		this.listenerList.remove(clickMouseHookListener.class, l);
	}

	protected void fireClickMouseHook( )
	{
		clickMouseHookListener[] listeners = (clickMouseHookListener[]) listenerList
				.getListeners(clickMouseHookListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].clickMouseHook( );
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	
	
	void Callback(  )
	{
		fireClickMouseHook();
	}
}
