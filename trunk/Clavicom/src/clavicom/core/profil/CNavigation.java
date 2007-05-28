/*-----------------------------------------------------------------------------+

			Filename			: CNavigation.java
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
import clavicom.tools.TNavigationType;
import clavicom.tools.TXMLNames;

public class CNavigation
{
	//--------------------------------------------------------- CONSTANTES --//
	private static int DEFAULT_TEMPORISATION_CLIC = 10;
	private static int DEFAULT_TEMPORISATION_DEFILEMENT = 10;
	
	private static boolean DEFAULT_BLOCK_SELECTION_ACTIVE = false;
	private static boolean DEFAULT_ROLLOVER_ACTIVE = false;

	//---------------------------------------------------------- VARIABLES --//	
	TNavigationType typeNavigation;
	
	int temporisationClic;
	int temporisationDefilement;
	
	boolean blockSelectionActive;
	boolean rolloverActive;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CNavigation (Element nodeNavigation) throws Exception
	{
		// Initialisation par défaut
		temporisationClic = DEFAULT_TEMPORISATION_CLIC;
		temporisationDefilement = DEFAULT_TEMPORISATION_DEFILEMENT;
		blockSelectionActive = DEFAULT_BLOCK_SELECTION_ACTIVE;
		rolloverActive = DEFAULT_ROLLOVER_ACTIVE;
		
		// Récupération du type de noeud
		Element eltTypeNavigation = nodeNavigation.getChild(TXMLNames.PR_ELEMENT_NAVIGATION_TYPE);
		
		if(eltTypeNavigation == null)
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_1")+
									TXMLNames.PR_ELEMENT_NAVIGATION_TYPE +
									UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_2"));
		}
		
		String strTypeNavigation = eltTypeNavigation.getAttributeValue(TXMLNames.PR_ATTRIBUTE_NAVIGATION_TYPE_VALUE);
		
		if(strTypeNavigation == null || strTypeNavigation.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_1")+
									TXMLNames.PR_ATTRIBUTE_NAVIGATION_TYPE_VALUE +
									UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_2"));
	
		}
		
		typeNavigation = TNavigationType.getValue(strTypeNavigation);
		
		if(typeNavigation == null)
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_BAD_TYPE_1")+
									strTypeNavigation +
									UIString.getUIString("EX_NAVIGATION_BAD_TYPE_2")+
									TXMLNames.PR_ATTRIBUTE_NAVIGATION_TYPE_VALUE +							
									UIString.getUIString("EX_NAVIGATION_BAD_TYPE_3"));
		}
		
		// Récupération du rollover
		Element eltRollover = nodeNavigation.getChild(TXMLNames.PR_ELEMENT_NAVIGATION_ROLLOVER);
		
		if (eltRollover == null)
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_1")+
									TXMLNames.PR_ELEMENT_NAVIGATION_ROLLOVER +
									UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_2"));			
		}
		
		String strRollover = eltRollover.getAttributeValue(TXMLNames.PR_ATTRIBUTE_NAVIGATION_ROLLOVER);
		if(strRollover == null || strRollover.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_1")+
									TXMLNames.PR_ATTRIBUTE_NAVIGATION_ROLLOVER +
									UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_2"));
	
		}
		
		try
		{
			rolloverActive = Boolean.parseBoolean(strRollover);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_NAVIGATION_BAD_ROLLOVER_1")+
									strRollover +
									UIString.getUIString("EX_NAVIGATION_BAD_ROLLOVER_2")+
									TXMLNames.PR_ATTRIBUTE_NAVIGATION_ROLLOVER +							
									UIString.getUIString("EX_NAVIGATION_BAD_ROLLOVER_3"));			
		}
		
		// Récupération des paramètres spécifiques à chaque type
		if (typeNavigation == TNavigationType.STANDARD)
		{
			// Rien à faire en plus
		}		
		else if (typeNavigation == TNavigationType.CLICK_TEMPORISE)
		{
			// Récupération de la temporisation
			Element eltTempo = nodeNavigation.getChild(TXMLNames.PR_ELEMENT_NAVIGATION_TEMPORISE_TEMPO);
			if (eltTempo == null)
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_1")+
										TXMLNames.PR_ELEMENT_NAVIGATION_TEMPORISE_TEMPO +
										UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_2"));				
			}
			
			String strTempoNavigation = eltTempo.getAttributeValue(TXMLNames.PR_ATTRIBUTE_NAVIGATION_TEMPORISE_TEMPO_VALUE);
			
			if (strTempoNavigation == null || strTempoNavigation.equals(""))
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_1")+
										TXMLNames.PR_ATTRIBUTE_NAVIGATION_TEMPORISE_TEMPO_VALUE +
										UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_2"));
				
			}
			
			try
			{
				temporisationClic = Integer.parseInt(strTempoNavigation);
			}
			catch (Exception ex)
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_1")+
										strTempoNavigation +
										UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_2")+
										TXMLNames.PR_ATTRIBUTE_NAVIGATION_TEMPORISE_TEMPO_VALUE +							
										UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_3"));
			}
		}
		else if (typeNavigation == TNavigationType.DEFILEMENT)
		{
			// Récupération de la temporisation
			Element eltTempo = nodeNavigation.getChild(TXMLNames.PR_ELEMENT_NAVIGATION_DEFILEMENT_TEMPO);
			if (eltTempo == null)
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_1")+
										TXMLNames.PR_ELEMENT_NAVIGATION_DEFILEMENT_TEMPO +
										UIString.getUIString("EX_NAVIGATION_MISSING_ELEMENT_2"));				
			}
			
			String strTempoNavigation = eltTempo.getAttributeValue(TXMLNames.PR_ATTRIBUTE_NAVIGATION_DEFILEMENT_TEMPO_VALUE);
			
			if (strTempoNavigation == null || strTempoNavigation.equals(""))
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_1")+
										TXMLNames.PR_ATTRIBUTE_NAVIGATION_DEFILEMENT_TEMPO_VALUE +
										UIString.getUIString("EX_NAVIGATION_MISSING_ATTRIBUTE_2"));
				
			}
			
			try
			{
				temporisationDefilement = Integer.parseInt(strTempoNavigation);
			}
			catch (Exception ex)
			{
				throw new Exception (	UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_1")+
										strTempoNavigation +
										UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_2")+
										TXMLNames.PR_ATTRIBUTE_NAVIGATION_DEFILEMENT_TEMPO_VALUE +							
										UIString.getUIString("EX_NAVIGATION_BAD_TEMPORISATION_3"));
			}		
		}
	}
	
	
	//----------------------------------------------------------- METHODES --//	
	public Element buildNode()
	{
		// Racine
		Element eltNavigation = new Element(TXMLNames.PR_ELEMENT_NAVIGATION);
		
		// Ajout du type
		Element eltTypeNavigation = new Element (TXMLNames.PR_ELEMENT_NAVIGATION_TYPE);
		eltTypeNavigation.setAttribute(TXMLNames.PR_ELEMENT_NAVIGATION_TYPE, TNavigationType.getString(typeNavigation));
		eltNavigation.addContent(eltTypeNavigation);
		
		// Ajout du rollover
		Element eltRollover = new Element (TXMLNames.PR_ELEMENT_NAVIGATION_ROLLOVER);
		eltRollover.setAttribute(TXMLNames.PR_ATTRIBUTE_NAVIGATION_ROLLOVER, Boolean.toString(rolloverActive));
		eltNavigation.addContent(eltRollover);
		
		// Ajout des informations spécifiques au type
		if (typeNavigation == TNavigationType.STANDARD)
		{
			// Rien à faire en plus
		}		
		else if (typeNavigation == TNavigationType.CLICK_TEMPORISE)
		{
			Element eltTemporisation = new Element (TXMLNames.PR_ELEMENT_NAVIGATION_TEMPORISE_TEMPO);
			eltTemporisation.setAttribute(TXMLNames.PR_ATTRIBUTE_NAVIGATION_TEMPORISE_TEMPO_VALUE, Integer.toString(temporisationClic));
			eltNavigation.addContent(eltTemporisation);
		}
		else if (typeNavigation == TNavigationType.DEFILEMENT)
		{
			Element eltTemporisation = new Element (TXMLNames.PR_ELEMENT_NAVIGATION_DEFILEMENT_TEMPO);
			eltTemporisation.setAttribute(TXMLNames.PR_ATTRIBUTE_NAVIGATION_DEFILEMENT_TEMPO_VALUE, Integer.toString(temporisationDefilement));
			eltNavigation.addContent(eltTemporisation);
		}
		
		return eltNavigation;
	}

	public boolean isBlockSelectionActive()
	{
		return blockSelectionActive;
	}


	public void setBlockSelectionActive(boolean blockSelectionActive)
	{
		this.blockSelectionActive = blockSelectionActive;
	}


	public boolean isRolloverActive()
	{
		return rolloverActive;
	}


	public void setRolloverActive(boolean rolloverActive)
	{
		this.rolloverActive = rolloverActive;
	}


	public int getTemporisationClic()
	{
		return temporisationClic;
	}


	public void setTemporisationClic(int temporisationClic)
	{
		this.temporisationClic = temporisationClic;
	}


	public int getTemporisationDefilement()
	{
		return temporisationDefilement;
	}

	public void setTemporisationDefilement(int temporisationDefilement)
	{
		this.temporisationDefilement = temporisationDefilement;
	}


	public TNavigationType getTypeNavigation()
	{
		return typeNavigation;
	}


	public void setTypeNavigation(TNavigationType typeNavigation)
	{
		this.typeNavigation = typeNavigation;
	}
	
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
