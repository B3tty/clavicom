/*-----------------------------------------------------------------------------+

			Filename			: CKeyPrediction.java
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

import org.jdom.Element;
import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyString extends CKeyDynamicString
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String baseString; // Désigne la chaîne générée, en minuscules, telle qu'elle sera sauvée

						
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyString(CColor myColorNormal, CColor myColorClicked,
			CColor myColorEntered, TPoint myPointMin, TPoint myPointMax,
			String myCaption)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin,
				myPointMax,myCaption);
	}
	
	public CKeyString (Element eltKeyString) throws Exception
	{
		super(eltKeyString);
		
		// Ajout la chaine
		try
		{
			baseString = eltKeyString.getChildText(TXMLNames.KY_ELEMENT_STRING_BASESTRING);
		}
		catch (Exception ex)
		{
			throw new Exception ( 	UIString.getUIString("EX_KEYSTRING_MISSING_BASESTRING_1") + 
									TXMLNames.KY_ELEMENT_STRING_BASESTRING + 
									UIString.getUIString("EX_KEYSTRING_MISSING_BASESTRING_2"));
		}
		
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	
	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		// Ajout de la chaine
		Element eltBaseString = new Element(TXMLNames.KY_ELEMENT_STRING_BASESTRING);
		
		eltBaseString.setText(baseString);
		
		eltKeyNode.addContent(eltBaseString);
	}

	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_STRING;
	}

	public String getBaseString()
	{
		return baseString;
	}

	public void setBaseString(String baseString)
	{
		this.baseString = baseString;
	}

	@Override
	protected Boolean toBeSave()
	{
		// TODO Auto-generated method stub
		return true;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
