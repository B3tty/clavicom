/*-----------------------------------------------------------------------------+

			Filename			: CKeyLevel.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keygroup.keyboard.key

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

import javax.swing.event.EventListenerList;

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.core.listener.OnClickKeyLastWordListener;
import clavicom.core.listener.OnClickKeyLevelListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyLevel extends CKeyOneLevel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	
	TLevelEnum level;
	
	protected EventListenerList listenerList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLevel(
			CColor myColorNormal, 
			CColor myColorClicked, 
			CColor myColorEntered, 
			TPoint myPointMin, 
			TPoint myPointMax,
			TLevelEnum myLevel,
			String myCaption)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin, myPointMax,myCaption);


		level = myLevel;
		
		listenerList = new EventListenerList();
	}

	public CKeyLevel( Element node ) throws Exception
	{
		super( node );
		
		// Récupération du level
		String s_level = node.getChildText( TXMLNames.KY_ELEMENT_LEVEL );
		if( s_level == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYLEVEL_BUILD" )+ "] : " + UIString.getUIString( "EX_KEYLEVEL_BUILD" ) + TXMLNames.CM_ATTRIBUTE_ID);
		}

		level = TLevelEnum.getValue( s_level );

		if( level == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYLEVEL_BUILD" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_level );
		}
		
		listenerList = new EventListenerList();
	}

	//----------------------------------------------------------- METHODES --//
	
	// Listener ==============================================
	public void addOnClickKeyLevelListener(OnClickKeyLevelListener l)
	{
		this.listenerList.add(OnClickKeyLevelListener.class, l);
	}

	public void removeOnClickKeyLevelListener(OnClickKeyLevelListener l)
	{
		this.listenerList.remove(OnClickKeyLevelListener.class, l);
	}

	protected void fireOnClickKeyLevel()
	{
		OnClickKeyLevelListener[] listeners = (OnClickKeyLevelListener[]) listenerList
				.getListeners(OnClickKeyLevelListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].onClickKeyLevel(this);
		}
	}
	// fin Listener ============================================
	
	
	
	public TLevelEnum GetLevel(){ return level; }
	
	@Override
	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		Element level_elem = new Element( TXMLNames.KY_ELEMENT_LEVEL );
		level_elem.setText( TLevelEnum.getString( level ) );
		
		eltKeyNode.addContent( level_elem );
	}
	
	@Override
	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_KEYLEVEL;
	}

	@Override
	protected Boolean toBeSave()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void Click()
	{
		fireOnClickKeyLevel();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	
}
