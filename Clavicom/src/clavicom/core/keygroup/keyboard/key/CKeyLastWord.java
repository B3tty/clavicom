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

import clavicom.CFilePaths;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyLastWord extends CKeyDynamicString
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLastWord(Color myColorNormal, Color myColorClicked,
			Color myColorEntered, boolean holdable,
			TPoint myPointMin, TPoint myPointMax)
	{
		this(	myColorNormal, 
				myColorClicked,
				myColorEntered,
				holdable,
				myPointMin, 
				myPointMax,
				"");
	}
	
	public CKeyLastWord(Color myColorNormal, Color myColorClicked,
			Color myColorEntered, boolean holdable, TPoint myPointMin, TPoint myPointMax,
			String myCaption)
	{
		super(myColorNormal, myColorClicked, myColorEntered, holdable, myPointMin,
				myPointMax,myCaption);
	}
	
	public CKeyLastWord (Element eltKeyLastWord) throws Exception
	{
		super(eltKeyLastWord);
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	@Override // pour ne pas sauvegarder la chaine
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		
		caption = "";
		// TODO Auto-generated method stub
		super.completeNodeSpecific(eltKeyNode);
	}
	
	
	public void completeNodeSpecific3(Element eltKeyNode) throws Exception
	{
		// Rien à rajouter		
	}

	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_LASTWORD;
	}
	//--------------------------------------------------- METHODES PRIVEES --//

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
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_LASTWORD"));
	}
}
