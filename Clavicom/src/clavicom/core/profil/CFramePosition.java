/*-----------------------------------------------------------------------------+

			Filename			: CFramePosition.java
			Creation date		: 25 juin 07
		
			Project				: Clavicom
			Package				: clavicom.core.profil

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

package clavicom.core.profil;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CFramePosition
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TPoint leftUp;
	TPoint rightDown;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CFramePosition(Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_FRAME_POSITION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE"));
		}
		
		// left up
		Element eltLeftUp = node.getChild(TXMLNames.FP_LEFT_UP);
		if( eltLeftUp == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_FRAME_POSITION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_LEFT_UP);
		}
		try
		{
			leftUp = new TPoint( eltLeftUp );
		}
		catch(Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_FRAME_POSITION_BUILD") + "][" + TXMLNames.FP_LEFT_UP + "] :" + ex.getMessage());
		}
		
		// right down
		Element eltRightDown = node.getChild(TXMLNames.FP_RIGHT_DOWN);
		if( eltRightDown == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_FRAME_POSITION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_RIGHT_DOWN);
		}
		try
		{
			rightDown = new TPoint( eltRightDown );
		}
		catch(Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_FRAME_POSITION_BUILD") + "][" + TXMLNames.FP_RIGHT_DOWN + "] :" + ex.getMessage());
		}
		
	}

	//----------------------------------------------------------- METHODES --//	
	

	public void buildNode( Element node )
	{
		Element eltLeftUp = leftUp.buildNode();
		Element eltRightDown = rightDown.buildNode();
		
		node.addContent( eltLeftUp );
		node.addContent( eltRightDown );
	}
	
	public TPoint getLeftUp()
	{
		return leftUp;
	}

	public void setLeftUp(TPoint leftUp)
	{
		this.leftUp = leftUp;
	}

	public TPoint getRightDown()
	{
		return rightDown;
	}

	public void setRightDown(TPoint rightDown)
	{
		this.rightDown = rightDown;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
