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

package clavicom.gui.listener;

import java.util.EventListener;

import clavicom.gui.edition.key.captionchoozer.UIPanelCaptionChooser;

public interface UICaptionChooserListener extends EventListener
{
	public void captionChanged(UIPanelCaptionChooser captionChooser, String newCaption, boolean isImage);
}
