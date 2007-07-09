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

import java.awt.Color;

import javax.swing.event.EventListenerList;
import org.jdom.Element;

import clavicom.CFilePaths;
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
	
	boolean alwaysHoldable; // pour savoir si la touche doit rester appuiyé ou non
	
	protected EventListenerList listenerList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLevel(
			Color myColorNormal, 
			Color myColorClicked, 
			Color myColorEntered, 
			boolean holdable,
			TPoint myPointMin, 
			TPoint myPointMax,
			TLevelEnum myLevel,
			String myCaption,
			boolean myAlwaysHoldable)
	{
		super(myColorNormal, myColorClicked, myColorEntered, holdable, myPointMin, myPointMax,myCaption);


		level = myLevel;
		alwaysHoldable = myAlwaysHoldable;
		
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
		
		// Récupération du alwaysHoldable
		String s_alwaysHoldable = node.getChildText( TXMLNames.KY_ELEMENT_ALWAYSHOLDABLE );
		if( s_alwaysHoldable != null )
		{
			try
			{
				alwaysHoldable = Boolean.parseBoolean( s_alwaysHoldable );
			} 
			catch ( Exception ex )
			{
				throw new Exception("[" + UIString.getUIString( "EX_KEYLEVEL_BUILD" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_alwaysHoldable + UIString.getUIString( "EX_KEYGROUP_TO_BOOLEAN" ) );
			}
		}
		else
		{
			// par default
			alwaysHoldable = false;
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
		
		if( alwaysHoldable )
		{
			Element alwaysHoldable_elem = new Element( TXMLNames.KY_ELEMENT_ALWAYSHOLDABLE );
			alwaysHoldable_elem.setText( String.valueOf( alwaysHoldable ) );
			eltKeyNode.addContent( alwaysHoldable_elem );
		}
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

	public void setLevel(TLevelEnum level)
	{
		this.level = level;
	}

	@Override
	public String getCaption()
	{
		if(isCaptionImage())	
			return CFilePaths.getUserPicturesFolder() + caption;
		else
			return caption;
	}
	
	@Override
	public String toString()
	{
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_LEVEL") +
					" [" + level.toString() + "]");
	}
	//--------------------------------------------------- METHODES PRIVEES --//

	public boolean isAlwaysHoldable()
	{
		return alwaysHoldable;
	}

	public void setAlwaysHoldable(boolean alwaysHoldable)
	{
		this.alwaysHoldable = alwaysHoldable;
	}
	
	
}
