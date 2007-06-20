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

public class ClickEngine
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Thread threadMouseHook = new Thread()
	{
		public void run() 
		{
			super.run();
			
			// Initialisation du hook en passant le type de touche en parametre
			// 		0 : WM_LBUTTONUP
			// 		1 : WM_LBUTTONDOWN
			// 		2 : WM_RBUTTONDOWN
			// 		3 : WM_RBUTTONUP
			InitMouseHook( 0 );
		}
		
	};
	
	// déclaration des méthodes natives
	public native void InitMouseHook( int click );
	public native void FinishMouseHook();
	public native void InhibitMouseHook( boolean inibit );

	//------------------------------------------------------ CONSTRUCTEURS --//
	public ClickEngine( String dllPath )
	{
		System.loadLibrary( dllPath );	
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
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	void Callback(  )
	{
		System.out.println("click !!!!!!!");
	}
}
