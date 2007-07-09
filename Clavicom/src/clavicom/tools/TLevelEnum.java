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

public final class TLevelEnum 
{
    private TLevelEnum() 
    {
    	// Rien à faire
    }
    
    // Liste des valeurs
    public final static TLevelEnum NORMAL			 	= new TLevelEnum();
    public final static TLevelEnum SHIFT			 	= new TLevelEnum();
    public final static TLevelEnum ALT_GR				= new TLevelEnum();
    
    /**
     * Retourne la chaine correspondant à la valeur transmise
     * @param myVal : Valeur dont on veut la chaîne correspondante
     * @return
     */
    public static String getString(TLevelEnum myVal )
    {
    	if (myVal == NORMAL)
    	{
    		return "NORMAL";
    	}
    	else if (myVal == SHIFT)
    	{
    		return "SHIFT";
    	}
    	else if (myVal == ALT_GR)
    	{
    		return "ALT_GR";
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
    public static TLevelEnum getValue(String myString )
    {
    	if (myString.equals("NORMAL"))
    	{
    		return NORMAL;
    	}
    	else if (myString.equals("SHIFT"))
    	{
    		return SHIFT;
    	}
    	else if (myString.equals("ALT_GR"))
    	{
    		return ALT_GR;
    	}    	
    	else
    	{
    		return null;
    	}
    }

    @Override
    public String toString()
    {
    	if (this == NORMAL)
    	{
    		return UIString.getUIString("LB_KEY_ONE_LEVEL_ENUM_NORMAL");
    	}
    	else if (this == SHIFT)
    	{
    		return UIString.getUIString("LB_KEY_ONE_LEVEL_ENUM_SHIFT");
    	}
    	else if (this == ALT_GR)
    	{
    		return UIString.getUIString("LB_KEY_ONE_LEVEL_ENUM_ALTGR");
    	}    	
    	else
    	{
    		return "";
    	}
    }
    
} 
