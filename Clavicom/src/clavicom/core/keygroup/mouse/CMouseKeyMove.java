/*-----------------------------------------------------------------------------+

			Filename			: CMouseKeyMove.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.mouse

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

package clavicom.core.keygroup.mouse;

import java.awt.Color;

import javax.swing.event.EventListenerList;
import clavicom.core.listener.onClicMouseMoveListener;
import clavicom.tools.TMouseKeyMoveEnum;

public class CMouseKeyMove extends CMouseKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TMouseKeyMoveEnum direction;
	
	protected EventListenerList listenerList;
	


	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseKeyMove( 
			TMouseKeyMoveEnum mydirection,
			String caption,
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered,
			boolean holdable)
	{
		super( caption, myColorNormal, myColorClicked, myColorEntered, holdable );
		direction = mydirection;
		
		listenerList = new EventListenerList();
	}

	//----------------------------------------------------------- METHODES --//	
	
	
	
	// ========================================================|
	// Listener ===============================================|
	// ========================================================|
	public void addOnClicMouseMoveListener(onClicMouseMoveListener l)
	{
		this.listenerList.add(onClicMouseMoveListener.class, l);
	}

	public void removeOnClicMouseMoveListener(onClicMouseMoveListener l)
	{
		this.listenerList.remove(onClicMouseMoveListener.class, l);
	}

	protected void fireOnClicMouseMove( )
	{
		onClicMouseMoveListener[] listeners = (onClicMouseMoveListener[]) listenerList
				.getListeners(onClicMouseMoveListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClicMouseMove( this );
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|
	
	
	
	
	public TMouseKeyMoveEnum GetDirection(){ return direction; }
	
	//--------------------------------------------------- METHODES PRIVEES --//

	@Override
	public void Click()
	{
		fireOnClicMouseMove();
	}
}
