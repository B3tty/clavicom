/*-----------------------------------------------------------------------------+

			Filename			: CKeyCommand.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.key.keyboard.key

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

package clavicom.core.keygroup.keyboard.key;

import java.awt.Color;

import javax.swing.event.EventListenerList;
import org.jdom.Element;

import clavicom.CFilePaths;
import clavicom.core.listener.OnClickKeyCreationListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TEnumCreationKey;
import clavicom.tools.TPoint;

public class CKeyCreation extends CKeyOneLevel
{	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	TEnumCreationKey keyType;
	String tooltip;				// Texte affiché en tooltip
	
	protected EventListenerList listenerList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyCreation(	Color myColorNormal, 
							Color myColorClicked , 
							Color myColorEntered ,
							boolean holdable,
							TPoint myPointMin, 
							TPoint myPointMax,
							String myCaption,
							TEnumCreationKey myKeyType,
							String tooltip)
	{
		super(myColorNormal,myColorClicked,myColorEntered,holdable,myPointMin,myPointMax,myCaption);
		
		keyType = myKeyType;
		this.tooltip = tooltip;
		
		listenerList = new EventListenerList();
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	public String getToolTip()
	{
		return tooltip;
	}
	
	
	// Listener ==============================================
	public void addOnClickKeyCreationListener(OnClickKeyCreationListener l)
	{
		this.listenerList.add(OnClickKeyCreationListener.class, l);
	}

	public void removeOnClickKeyCreationListener(OnClickKeyCreationListener l)
	{
		this.listenerList.remove(OnClickKeyCreationListener.class, l);
	}

	protected void fireOnClickKeyCreation()
	{
		OnClickKeyCreationListener[] listeners = (OnClickKeyCreationListener[]) listenerList
				.getListeners(OnClickKeyCreationListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyCreation(keyType);
		}
	}
	// fin Listener ============================================
	

	@Override
	protected Boolean toBeSave()
	{
		return false;
	}

	@Override
	public void Click()
	{
		// On balance un evenement
		fireOnClickKeyCreation();
	}

	@Override
	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getElementName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public TEnumCreationKey getKeyType()
	{
		return keyType;
	}

	public void setKeyType(TEnumCreationKey keyType)
	{
		this.keyType = keyType;
	}
	
	@Override
	public String getCaption()
	{
		return CFilePaths.getPictures() + caption;
	}
	
	@Override
	public String toString()
	{
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_CREATION") +
					" [" + caption + "]");
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
}

