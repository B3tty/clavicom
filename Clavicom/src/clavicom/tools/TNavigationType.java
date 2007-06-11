/*-----------------------------------------------------------------------------+

			Filename			: CKeyClavicomType.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

import clavicom.gui.language.UIString;

public final class TNavigationType 
{
    private TNavigationType() 
    {
    	// Rien à faire
    }
    
    // Liste des valeurs
    public final static TNavigationType STANDARD			 	= new TNavigationType();
    public final static TNavigationType DEFILEMENT			 	= new TNavigationType();
    public final static TNavigationType CLICK_TEMPORISE			= new TNavigationType();
    
    /**
     * Retourne la chaine correspondant à la valeur transmise
     * @param myVal : Valeur dont on veut la chaîne correspondante
     * @return
     */
    public static String getString(TNavigationType myVal )
    {
    	if (myVal == STANDARD)
    	{
    		return "STANDARD";
    	}
    	else if (myVal == DEFILEMENT)
    	{
    		return "DEFILEMENT";
    	}
    	else if (myVal == CLICK_TEMPORISE)
    	{
    		return "CLICK_TEMPORISE";
    	}    	
    	else
    	{
    		return "";
    	}
    }
    
    /**
     * Retourne la valeur correspondant à la chaine transmise
     * @param myString : Chaine dont on veut la valeur correspondante
     * @return
     */    
    public static TNavigationType getValue(String myString )
    {
    	if (myString.equals("STANDARD"))
    	{
    		return STANDARD;
    	}
    	else if (myString.equals("DEFILEMENT"))
    	{
    		return DEFILEMENT;
    	}
    	else if (myString.equals("CLICK_TEMPORISE"))
    	{
    		return CLICK_TEMPORISE;
    	}    	
    	else
    	{
    		return null;
    	}
    }
    
    
    @Override
    public String toString()
    {
    	if (this == STANDARD)
    	{
    		return UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_STANDARD");
    	}
    	else if (this == DEFILEMENT)
    	{
    		return UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT");
    	}
    	else if (this == CLICK_TEMPORISE)
    	{
    		return UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_CLIC_TEMPORISE");
    	}    	
    	else
    	{
    		return "";
    	}
    }
} 
