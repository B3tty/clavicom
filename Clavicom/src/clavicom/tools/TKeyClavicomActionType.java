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

public final class TKeyClavicomActionType 
{
    private TKeyClavicomActionType() 
    {
    	// Rien à faire
    }
    
    // Liste des valeurs
    public final static TKeyClavicomActionType NONE					 	= new TKeyClavicomActionType();
    public final static TKeyClavicomActionType SWITCH_MOUSE_KEYBOARD 	= new TKeyClavicomActionType();
    public final static TKeyClavicomActionType CLOSE_APPLICATION 		= new TKeyClavicomActionType();
    public final static TKeyClavicomActionType OPEN_CONFIGURATION		= new TKeyClavicomActionType();
    public final static TKeyClavicomActionType SWITCH_KEYBOARD_MOUSE	= new TKeyClavicomActionType();
    /**
     * Retourne la chaine correspondant à la valeur transmise
     * @param myVal : Valeur dont on veut la chaîne correspondante
     * @return
     */
    public static String getString(TKeyClavicomActionType myVal )
    {
    	if (myVal == NONE)
    	{
    		return "NONE";
    	}
    	else if (myVal == SWITCH_KEYBOARD_MOUSE)
    	{
    		return "SWITCH_KEYBOARD_MOUSE";
    	}
    	else if (myVal == SWITCH_MOUSE_KEYBOARD)
    	{
    		return "SWITCH_MOUSE_KEYBOARD";
    	}
    	else if (myVal == CLOSE_APPLICATION)
    	{
    		return "CLOSE_APPLICATION";
    	}    	
    	else if (myVal == OPEN_CONFIGURATION)
    	{
    		return "OPEN_CONFIGURATION";
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
    public static TKeyClavicomActionType getValue(String myString )
    {
    	if (myString.equals("NONE"))
    	{
    		return NONE;
    	}
    	else if (myString.equals("SWITCH_KEYBOARD_MOUSE"))
    	{
    		return SWITCH_KEYBOARD_MOUSE;
    	}
    	else if (myString.equals("SWITCH_MOUSE_KEYBOARD"))
    	{
    		return SWITCH_MOUSE_KEYBOARD;
    	}
    	else if (myString.equals("CLOSE_APPLICATION"))
    	{
    		return CLOSE_APPLICATION;
    	}    	
    	else if (myString.equals("SWITCH_MOUSE_KEYBOARD"))
    	{
    		return SWITCH_MOUSE_KEYBOARD;
    	}
    	else
    	{
    		return null;
    	}
    }

    @Override
    public String toString()
    {
    	if (this == NONE)
    	{
    		return UIString.getUIString("LB_KEYCLAVICOM_NONE");
    	}
    	else if (this == SWITCH_MOUSE_KEYBOARD)
    	{
    		return UIString.getUIString("LB_KEYCLAVICOM_SWITCH_MOUSE_KEYBOARD");
    	}
    	else if (this == SWITCH_KEYBOARD_MOUSE)
    	{
    		return UIString.getUIString("LB_KEYCLAVICOM_SWITCH_KEYBOARD_MOUSE");
    	}
    	else if (this == CLOSE_APPLICATION)
    	{
    		return UIString.getUIString("LB_KEYCLAVICOM_CLOSE_APPLICATION");
    	}    	
    	else if (this == OPEN_CONFIGURATION)
    	{
    		return UIString.getUIString("LB_KEYCLAVICOM_OPEN_CONFIGURATION");
    	}
    	else
    	{
    		return "";
    	}
    }
} 
