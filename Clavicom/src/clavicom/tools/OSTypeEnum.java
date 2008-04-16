/*-----------------------------------------------------------------------------+

			Filename			: OSTypeEnum.java
			Creation date		: 21 mars 08
		
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

public final class OSTypeEnum
{
	//--------------------------------------------------------- CONSTANTES --//
	//	 Liste des valeurs
    public final static OSTypeEnum WINDOWS			= new OSTypeEnum();
    public final static OSTypeEnum LINUX			= new OSTypeEnum();
    public final static OSTypeEnum MAC				= new OSTypeEnum();
    public final static OSTypeEnum UNKNOWN			= new OSTypeEnum();
    
    public static OSTypeEnum currentOSType;
    
    static
    {
    	String osString = System.getProperty("os.name").toLowerCase();    	
    	
    	// See the page : http://lopica.sourceforge.net/os.html
    	// to get all possible OS type name
    	
    	if ( osString.contains( "windows" ) )
    	{
    		currentOSType = WINDOWS;
    	} else if( osString.contains( "linux" ) )
    	{
    		currentOSType = LINUX;
    	} else if ( osString.contains( "mac" ) )
    	{
    		currentOSType = MAC;
    	} else 
    	{
    		// if we don't known the OS type
    		currentOSType = UNKNOWN;
    	}
    	
    	
    }
    
    //---------------------------------------------------------- VARIABLES --//	
	
	//------------------------------------------------------ CONSTRUCTEURS --//
    private OSTypeEnum( )
    {
    }
	
	//----------------------------------------------------------- METHODES --//	
	
    public static OSTypeEnum getCurrentOSType()
    {
    	return currentOSType;
    }
    
    public static String getMessageOSREstriction()
    {
    	return UIString.getUIString("LB_OS_RESTRICTION");
    }
    
	//--------------------------------------------------- METHODES PRIVEES --//	
}
