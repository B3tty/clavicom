/*-----------------------------------------------------------------------------+

			Filename			: UIKeyCreationEngine.java
			Creation date		: 19 juin 07
		
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

import javax.swing.event.EventListenerList;

import clavicom.core.listener.OnClickKeyCreationListener;
import clavicom.tools.TEnumCreationKey;

public class UIKeyCreationEngine implements OnClickKeyCreationListener
{
	//--------------------------------------------------------- CONSTANTES --//
	private static UIKeyCreationEngine instance;

	//---------------------------------------------------------- VARIABLES --//	
	protected EventListenerList listenerList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	private UIKeyCreationEngine()
	{
		listenerList = new EventListenerList();
	};

	//----------------------------------------------------------- METHODES --//	
	public static UIKeyCreationEngine getInstance ()
	{
		return instance;
	}
	
	public static void createInstance ()
	{
		instance = new UIKeyCreationEngine();
	}

	public void onClickKeyCreation(TEnumCreationKey type)
	{
		fireOnClickKeyCreation(type);
	}
	
	public void addOnClickKeyCreationListener(OnClickKeyCreationListener l)
	{
		this.listenerList.add(OnClickKeyCreationListener.class, l);
	}

	public void removeOnClickKeyCreationListener(OnClickKeyCreationListener l)
	{
		this.listenerList.remove(OnClickKeyCreationListener.class, l);
	}

	//--------------------------------------------------- METHODES PRIVEES --//

	protected void fireOnClickKeyCreation(TEnumCreationKey keyType)
	{
		OnClickKeyCreationListener[] listeners = (OnClickKeyCreationListener[]) listenerList
				.getListeners(OnClickKeyCreationListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyCreation(keyType);
		}
	}
}
