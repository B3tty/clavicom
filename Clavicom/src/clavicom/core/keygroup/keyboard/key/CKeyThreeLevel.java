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
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyThreeLevel extends CKeyboardKey
{

	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String caption_level_1; // caption pour le niveau 1
	String caption_level_2; // caption pour le niveau 1 
	String caption_level_3; // caption pour le niveau 1

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CKeyThreeLevel(
			CColor myColorNormal, 
			CColor myColorClicked, 
			CColor myColorEntered, 
			TPoint myPointMin, 
			TPoint myPointMax,
			String myCaptionLevel1,
			String myCaptionLevel2,
			String myCaptionLevel3)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin, myPointMax);

		caption_level_1 = myCaptionLevel1;
		caption_level_2 = myCaptionLevel2;
		caption_level_3 = myCaptionLevel3;
	}
	
	public CKeyThreeLevel(Element node) throws Exception
	{
		super(node);
		
		// Récupération du caption level 1
		caption_level_1 = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1);
		
		// si le caption est null et si il aurait du en avoir un
		if ( caption_level_1 == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1);
		}
		
		// Récupération du caption level 2
		caption_level_2 = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2);
		
		// si le caption est null et si il aurait du en avoir un
		if ( caption_level_2 == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2);
		}
		
		// Récupération du caption level 3
		caption_level_3 = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3);
		
		// si le caption est null et si il aurait du en avoir un
		if ( caption_level_3 == null )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_THREE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3);
		}

	}

	

	//----------------------------------------------------------- METHODES --//
	
	public String getCaptionLevel1()
	{
		return caption_level_1;
	}

	public void setCaptionLevel1(String caption_level_1)
	{
		this.caption_level_1 = caption_level_1;
	}

	public String getCaptionLevel2()
	{
		return caption_level_2;
	}

	public void setCaptionLevel2(String caption_level_2)
	{
		this.caption_level_2 = caption_level_2;
	}

	public String getCaptionLevel3()
	{
		return caption_level_3;
	}

	public void setCaptionLevel3(String caption_level_3)
	{
		this.caption_level_3 = caption_level_3;
	}
	
	@Override
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		// caption level 1
		Element caption_level_1_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_1 );
		caption_level_1_elem.setText( caption_level_1 );
		eltKeyNode.addContent( caption_level_1_elem );
		
		// caption level 2
		Element caption_level_2_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_2 );
		caption_level_2_elem.setText( caption_level_2 );
		eltKeyNode.addContent( caption_level_2_elem );
		
		// caption level 3
		Element caption_level_3_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION_LEVEL_3 );
		caption_level_3_elem.setText( caption_level_3 );
		eltKeyNode.addContent( caption_level_3_elem );
		
		completeNodeSpecific2( eltKeyNode );
		
	}
	
	public abstract void completeNodeSpecific2(Element eltKeyNode) throws Exception;

	//--------------------------------------------------- METHODES PRIVEES --//
}
