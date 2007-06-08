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


import java.awt.Color;
import org.jdom.Element;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyPrediction extends CKeyDynamicString
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyPrediction(Color myColorNormal, Color myColorClicked,
			Color myColorEntered, TPoint myPointMin, TPoint myPointMax,
			String myCaption)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin,
				myPointMax,myCaption);
	}
	
	public CKeyPrediction (Element eltKeyPrediction) throws Exception
	{
		super(eltKeyPrediction);
	}
	
	//----------------------------------------------------------- METHODES --//
	

	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_PREDICTION;
	}
	
	public void completeNodeSpecific2(Element eltKeyNode) throws Exception
	{
		// Rien à ajouter		
	}

	@Override
	protected Boolean toBeSave()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String GetStringCommand()
	{
		return caption;
	}


	
	//--------------------------------------------------- METHODES PRIVEES --//
}
