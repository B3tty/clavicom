/*-----------------------------------------------------------------------------+

			Filename			: CSound.java
			Creation date		: 25 mai 07
		
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
import clavicom.tools.TXMLNames;

public class CSound
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	boolean soundOnDefil; 				// Jouer un son sur le défilement
	boolean soundOnClic; 				// Jouer un son sur le click sur un bouton
	boolean soundOnSurvol; 				// Jouer un son sur le survol sur un bouton
	boolean soundOnStartApplication; 	// Jouer un son au lancement de l'application
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CSound()
	{
		soundOnDefil = false;
		soundOnClic = false;
		soundOnSurvol = false;
		soundOnStartApplication = false;
	}
	
	public CSound (Element soundNode) throws Exception
	{
		// Récupération de l'element OnDefil
		Element eltOnDefil = soundNode.getChild(TXMLNames.PR_ELEMENT_SOUND_ON_DEFIL);
		
		if (eltOnDefil == null)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ELEMENT_1") +
									TXMLNames.PR_ELEMENT_SOUND_ON_DEFIL + 
									UIString.getUIString("EX_SOUND_MISSING_ELEMENT_2"));	
		}
		
		String strOnDefil = eltOnDefil.getAttributeValue(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE);
		
		if (strOnDefil == null || strOnDefil.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_1") +
									TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE + 
									UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_2"));				
		}
		
		try
		{
			soundOnDefil = Boolean.parseBoolean(strOnDefil);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_1") +
									strOnDefil + 
									UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_2"));				
		}

		// Récupération de l'element OnClic
		Element eltOnClic = soundNode.getChild(TXMLNames.PR_ELEMENT_SOUND_ON_CLIC);
		
		if (eltOnClic == null)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ELEMENT_1") +
									TXMLNames.PR_ELEMENT_SOUND_ON_CLIC + 
									UIString.getUIString("EX_SOUND_MISSING_ELEMENT_2"));	
		}
		
		String strOnClic = eltOnClic.getAttributeValue(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE);
		
		if (strOnClic == null || strOnClic.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_1") +
									TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE + 
									UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_2"));				
		}
		
		try
		{
			soundOnClic = Boolean.parseBoolean(strOnClic);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_1") +
									strOnClic + 
									UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_2"));				
		}
		
		// Récupération de l'element OnDefil
		Element eltOnSurvol = soundNode.getChild(TXMLNames.PR_ELEMENT_SOUND_ON_SURVOL);
		
		if (eltOnSurvol == null)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ELEMENT_1") +
									TXMLNames.PR_ELEMENT_SOUND_ON_SURVOL + 
									UIString.getUIString("EX_SOUND_MISSING_ELEMENT_2"));	
		}
		
		String strOnSurvol = eltOnSurvol.getAttributeValue(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE);
		
		if (strOnSurvol == null || strOnSurvol.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_1") +
									TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE + 
									UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_2"));				
		}
		
		try
		{
			soundOnSurvol = Boolean.parseBoolean(strOnSurvol);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_1") +
									strOnSurvol + 
									UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_2"));				
		}
		
		
		// Récupération de l'element OnStart
		Element eltOnStart = soundNode.getChild(TXMLNames.PR_ELEMENT_SOUND_ON_START);
		
		if (eltOnStart == null)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ELEMENT_1") +
									TXMLNames.PR_ELEMENT_SOUND_ON_START + 
									UIString.getUIString("EX_SOUND_MISSING_ELEMENT_2"));	
		}
		
		String srtOnStart = eltOnStart.getAttributeValue(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE);
		
		if (srtOnStart == null || srtOnStart.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_1") +
									TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE + 
									UIString.getUIString("EX_SOUND_MISSING_ATTRIBUTE_2"));				
		}
		
		try
		{
			soundOnStartApplication = Boolean.parseBoolean(srtOnStart);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_1") +
									srtOnStart + 
									UIString.getUIString("EX_SOUND_BAD_ATTRIBUTE_2"));				
		}
	}
	
	//----------------------------------------------------------- METHODES --//	
	public Element buildNode()
	{
		// Construction du noeud sound
		Element soundNode = new Element(TXMLNames.PR_ELEMENT_SOUND);
		
		// Construction des sous noeuds;
		Element eltOnDefil = new Element (TXMLNames.PR_ELEMENT_SOUND_ON_DEFIL);
		eltOnDefil.setAttribute(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE, Boolean.toString(soundOnDefil));
		soundNode.addContent(eltOnDefil);

		Element eltOnClic = new Element (TXMLNames.PR_ELEMENT_SOUND_ON_CLIC);
		eltOnClic.setAttribute(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE, Boolean.toString(soundOnClic));
		soundNode.addContent(eltOnClic);
		
		Element eltOnSurvol = new Element (TXMLNames.PR_ELEMENT_SOUND_ON_SURVOL);
		eltOnSurvol.setAttribute(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE, Boolean.toString(soundOnSurvol));
		soundNode.addContent(eltOnSurvol);
		
		Element eltOnStart = new Element (TXMLNames.PR_ELEMENT_SOUND_ON_START);
		eltOnStart.setAttribute(TXMLNames.PR_ATTRIBUTE_SOUND_ACTIVE, Boolean.toString(soundOnStartApplication));
		soundNode.addContent(eltOnStart);
				
		return soundNode;
	}

	public boolean isSoundOnClic()
	{
		return soundOnClic;
	}

	public void setSoundOnClic(boolean soundOnClic)
	{
		this.soundOnClic = soundOnClic;
	}

	public boolean isSoundOnDefil()
	{
		return soundOnDefil;
	}

	public void setSoundOnDefil(boolean soundOnDefil)
	{
		this.soundOnDefil = soundOnDefil;
	}

	public boolean isSoundOnSurvol()
	{
		return soundOnSurvol;
	}

	public void setSoundOnSurvol(boolean soundOnSurvol)
	{
		this.soundOnSurvol = soundOnSurvol;
	}

	public boolean isSoundOnStartApplication()
	{
		return soundOnStartApplication;
	}

	public void setSoundOnStartApplication(boolean soundOnStartApplication)
	{
		this.soundOnStartApplication = soundOnStartApplication;
	}
	
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
