/*-----------------------------------------------------------------------------+

			Filename			: CAdvancedOptions.java
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
import clavicom.tools.TClickSouricomEnum;
import clavicom.tools.TXMLNames;

public class CAdvancedOptions
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TClickSouricomEnum clickSouricom;
	boolean addSpaceAfterString;
	CFramePosition clavicomFramePosition;
	CFramePosition souricomFramePosition;
	boolean startWithOS;
	int numberOfDefilTurn;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CAdvancedOptions(Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE"));
		}
		
		// click Souricom
		Element eltClickSouricom = node.getChild( TXMLNames.FP_CLICK_SOURICOM );
		if( eltClickSouricom == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_CLICK_SOURICOM);
		}
		clickSouricom = TClickSouricomEnum.getValue( eltClickSouricom.getText() );
		
		// addSpaceAfterString
		Element eltAddSpaceBevorString = node.getChild( TXMLNames.FP_ADD_SPACE_AFTER_STRING );
		if( eltAddSpaceBevorString == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_ADD_SPACE_AFTER_STRING);
		}
		try
		{
			addSpaceAfterString = Boolean.parseBoolean( eltAddSpaceBevorString.getText() );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "][" + TXMLNames.FP_ADD_SPACE_AFTER_STRING + "] : " + UIString.getUIString("EX_KEYGROUP_CAN_NOT_CONVERT") + eltAddSpaceBevorString.getText() + UIString.getUIString("EX_KEYGROUP_TO_BOOLEAN"));
		}
		
		
		// clavicomFramePosition
		Element eltClavicomFramePosition = node.getChild( TXMLNames.FP_CLAVICOM_FRAME_POSITION );
		if( eltClavicomFramePosition == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_CLAVICOM_FRAME_POSITION);
		}
		try
		{
			clavicomFramePosition = new CFramePosition( eltClavicomFramePosition );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "][ " + TXMLNames.FP_CLAVICOM_FRAME_POSITION + "]" + ex.getMessage() );
		}
		
		
		// souricomFramePosition
		Element eltSouricomFramePosition = node.getChild( TXMLNames.FP_SOURICOM_FRAME_POSITION );
		if( eltSouricomFramePosition == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_SOURICOM_FRAME_POSITION);
		}
		try
		{
			souricomFramePosition = new CFramePosition( eltSouricomFramePosition );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "][ " + TXMLNames.FP_SOURICOM_FRAME_POSITION + "]" + ex.getMessage() );
		}
		
		
		// startWithOS
		Element eltStartWithOS = node.getChild( TXMLNames.FP_START_WITH_OS );
		if( eltStartWithOS == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_START_WITH_OS);
		}
		try
		{
			startWithOS = Boolean.parseBoolean( eltStartWithOS.getText() );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "][" + TXMLNames.FP_START_WITH_OS + "] : " + UIString.getUIString("EX_KEYGROUP_CAN_NOT_CONVERT") + eltStartWithOS.getText() + UIString.getUIString("EX_KEYGROUP_TO_BOOLEAN"));
		}
		
		
		// numberOfDefilTurn
		Element eltNumberOfDefilTurn = node.getChild( TXMLNames.FP_NB_DEFIL_TURN );
		if( eltNumberOfDefilTurn == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.FP_NB_DEFIL_TURN);
		}
		try
		{
			numberOfDefilTurn = Integer.parseInt( eltNumberOfDefilTurn.getText() );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_ADVANCED_OPTION_BUILD") + "][" + TXMLNames.FP_NB_DEFIL_TURN + "] : " + UIString.getUIString("EX_KEYGROUP_CAN_NOT_CONVERT") + eltNumberOfDefilTurn.getText() + UIString.getUIString("EX_KEYGROUP_TO_INTEGER"));
		}
		
	}

	public boolean isAddSpaceAfterString()
	{
		return addSpaceAfterString;
	}

	public void setAddSpaceAfterString(boolean addSpaceAfterString)
	{
		this.addSpaceAfterString = addSpaceAfterString;
	}

	public CFramePosition getClavicomFramePosition()
	{
		return clavicomFramePosition;
	}

	public void setClavicomFramePosition(CFramePosition clavicomFramePosition)
	{
		this.clavicomFramePosition = clavicomFramePosition;
	}

	public TClickSouricomEnum getClickSouricom()
	{
		return clickSouricom;
	}

	public void setClickSouricom(TClickSouricomEnum clickSouricom)
	{
		this.clickSouricom = clickSouricom;
	}

	public int getNumberOfDefilTurn()
	{
		return numberOfDefilTurn;
	}

	public void setNumberOfDefilTurn(int numberOfDefilTurn)
	{
		this.numberOfDefilTurn = numberOfDefilTurn;
	}

	public CFramePosition getSouricomFramePosition()
	{
		return souricomFramePosition;
	}

	public void setSouricomFramePosition(CFramePosition souricomFramePosition)
	{
		this.souricomFramePosition = souricomFramePosition;
	}

	public boolean isStartWithOS()
	{
		return startWithOS;
	}

	public void setStartWithOS(boolean startWithOS)
	{
		this.startWithOS = startWithOS;
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
