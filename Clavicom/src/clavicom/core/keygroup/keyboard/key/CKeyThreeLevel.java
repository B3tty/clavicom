/*-----------------------------------------------------------------------------+

			Filename			: CKeyThreeLevel.java
			Creation date		: 30 mai 07
		
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

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyThreeLevel extends CKeyboardKey
{

	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String captionNormal;
	String captionShift;
	String captionAltGr;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CKeyThreeLevel(
			CColor myColorNormal, 
			CColor myColorClicked, 
			CColor myColorEntered, 
			TPoint myPointMin, 
			TPoint myPointMax,
			String myCaptionNormal,
			String myCaptionShift,
			String myCaptionAltGr)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin, myPointMax);

		captionNormal = myCaptionNormal;
		captionShift = myCaptionShift;
		captionAltGr = myCaptionAltGr;
	}
	
	public CKeyThreeLevel(Element node) throws Exception
	{
		super(node);
		
		// Récupération du caption level 1
		captionNormal = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1);
		
		// si le caption est null et si il aurait du en avoir un
		if ( captionNormal == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1);
		}
		
		// Récupération du caption level 2
		captionShift = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2);
		
		// si le caption est null et si il aurait du en avoir un
		if ( captionShift == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2);
		}
		
		// Récupération du caption level 3
		captionAltGr = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3);
		
		// si le caption est null et si il aurait du en avoir un
		if ( captionAltGr == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3);
		}

	}

	

	//----------------------------------------------------------- METHODES --//

	public String getCaption( TLevelEnum level )
	{
		if( level == TLevelEnum.NORMAL )
		{
			return captionNormal;
		}else if( level == TLevelEnum.SHIFT )
		{
			return captionShift;
		}else if( level == TLevelEnum.ALT_GR )
		{
			return captionAltGr;
		}else
		{
			return "";
		}
	}
	
	public void setCaption( String caption, TLevelEnum level )
	{
		if( level == TLevelEnum.NORMAL )
		{
			captionNormal = caption;
		}
		else if( level == TLevelEnum.SHIFT )
		{
			captionShift = caption;
		}
		else if( level == TLevelEnum.ALT_GR )
		{
			captionAltGr = caption;
		}
		
		// Alerte de changement de la caption
		fireCaptionChanged();
	}
	
	@Override
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		// caption level 1
		Element caption_level_1_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1 );
		caption_level_1_elem.setText( captionNormal );
		eltKeyNode.addContent( caption_level_1_elem );
		
		// caption level 2
		Element caption_level_2_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2 );
		caption_level_2_elem.setText( captionShift );
		eltKeyNode.addContent( caption_level_2_elem );
		
		// caption level 3
		Element caption_level_3_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3 );
		caption_level_3_elem.setText( captionAltGr );
		eltKeyNode.addContent( caption_level_3_elem );
		
		completeNodeSpecific2( eltKeyNode );
		
	}
	
	public abstract void completeNodeSpecific2(Element eltKeyNode) throws Exception;

	//--------------------------------------------------- METHODES PRIVEES --//
}
