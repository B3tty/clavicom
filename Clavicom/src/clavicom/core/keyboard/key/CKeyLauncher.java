/*-----------------------------------------------------------------------------+

			Filename			: CKeyLauncher.java
			Creation date		: 22 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.key

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

package clavicom.core.keyboard.key;

import java.awt.Color;

import org.jdom.Element;

import clavicom.core.keyboard.key.CKeyboardKey;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyLauncher extends CKeyboardKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String applicationPath;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLauncher (Color myColor, TPoint myPointMin, TPoint myPointMax)
	{
		super(myColor,myPointMin,myPointMax);
	}
	
	public CKeyLauncher (Element eltKeyLauncher) throws Exception
	{
		// On appelle le chargement du père, qui récupèrera seulement les élements
		// qui le concerne.
		
		super(eltKeyLauncher);
		
		// Chargement du path
		Element eltPath = eltKeyLauncher.getChild(TXMLNames.KY_LAUNCHER_ELEMENT_PATH);
		
		if(eltPath == null)
		{
			throw new Exception (	"[Chargement d'une touche lancement] : Element " + 
					TXMLNames.KY_LAUNCHER_ELEMENT_PATH + " attendu manquant") ;		
		}
		applicationPath = eltPath.getText();
	}

	//----------------------------------------------------------- METHODES --//	
	public void completeNodeSpecific(Element keyNode) throws Exception
	{		
		// Ajout des elements spécifiques
		Element eltPath = new Element(TXMLNames.KY_LAUNCHER_ELEMENT_PATH);
		
		eltPath.setText(applicationPath);
		
		keyNode.addContent(eltPath);
	}
	
	public String getElementName()
	{
		return TXMLNames.KY_LAUNCHER_ELEMENT;
	}
	
	public String getApplicationPath()
	{
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath)
	{
		this.applicationPath = applicationPath;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
