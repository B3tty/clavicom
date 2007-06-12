/*-----------------------------------------------------------------------------+

			Filename			: CMessageListener.java
			Creation date		: 12 juin 07
		
			Project				: Clavicom
			Package				: clavicom.core.listener

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

package clavicom.core.listener;

import java.util.EventListener;

public interface CMessageListener extends EventListener
{
	public void onNewFatalError(String newMessage, String detail);
	public void onNewError(String newMessage, String detail);
	public void onNewWarning(String newMessage, String detail);
	public void onNewInfo(String newMessage, String detail);
}
