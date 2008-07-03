/*-----------------------------------------------------------------------------+

			Filename			: CMouseKeyClick.java
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
import clavicom.core.listener.onClicMouseClickListener;
import clavicom.tools.TMouseKeyClickEnum;

public class CMouseKeyClick extends CMouseKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TMouseKeyClickEnum click;
	
	protected EventListenerList listenerList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseKeyClick( 
			TMouseKeyClickEnum myClick,
			String caption,
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered,
			boolean holdable)
	{
		super( caption, myColorNormal, myColorClicked, myColorEntered, holdable );
		click = myClick;
		
		listenerList = new EventListenerList();
	}

	//----------------------------------------------------------- METHODES --//	
	
	
	// ========================================================|
	// Listener ===============================================|
	// ========================================================|
	public void addOnClicMouseClickListener(onClicMouseClickListener l)
	{
		this.listenerList.add(onClicMouseClickListener.class, l);
	}

	public void removeOnClicMouseClickListener(onClicMouseClickListener l)
	{
		this.listenerList.remove(onClicMouseClickListener.class, l);
	}

	protected void fireOnClicMouseClick( )
	{
		onClicMouseClickListener[] listeners = (onClicMouseClickListener[]) listenerList
				.getListeners(onClicMouseClickListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClicMouseClick( this );
		}
	}
	// ========================================================|
	// fin Listeners ==========================================|
	// ========================================================|
	
	
	
	public TMouseKeyClickEnum GetClick(){return click;}

	public void Click()
	{
		fireOnClicMouseClick();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
