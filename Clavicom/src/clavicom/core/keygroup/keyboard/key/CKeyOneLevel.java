/*-----------------------------------------------------------------------------+

 Filename			: CKeyOneLevel.java
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
import clavicom.core.listener.CKeyCaptionChangedListener;
import clavicom.core.listener.CKeyColorChangedListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public abstract class CKeyOneLevel extends CKeyboardKey
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	String caption; // Caption de la touche (label ou chemin vers l'image à

	// afficher)

	// ------------------------------------------------------ CONSTRUCTEURS --//

	public CKeyOneLevel(CColor myColorNormal, CColor myColorClicked,
			CColor myColorEntered, TPoint myPointMin, TPoint myPointMax,
			String myCaption)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin,
				myPointMax);

		caption = myCaption;
	}

	public CKeyOneLevel(Element node) throws Exception
	{
		super(node);

		// ==========================================================================
		// Récupération du caption
		// ==========================================================================

		caption = node.getChildText(TXMLNames.KY_ELEMENT_CAPTION);
		
		// si le caption est null et si il aurait du en avoir un
		if ( (caption == null) && toBeSave() )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_KEY_ONE_LEVEL_BUILD") + " ] : "
					+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE")
					+ TXMLNames.KY_ELEMENT_CAPTION);
		}

	}

	// ----------------------------------------------------------- METHODES --//

	protected abstract Boolean toBeSave();
	
	@Override
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		Element caption_elem = new Element( TXMLNames.KY_ELEMENT_CAPTION );
		caption_elem.setText( caption );
		eltKeyNode.addContent( caption_elem );

		// appel de la méthode spécifique de la fille
		completeNodeSpecific2( eltKeyNode );
		
	}
	
	public abstract void completeNodeSpecific2(Element eltKeyNode) throws Exception;

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
		
		// Alerte de changement de la caption
		fireCaptionChanged();
	}

	// --------------------------------------------------- METHODES PRIVEES --//
}
