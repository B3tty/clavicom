/*-----------------------------------------------------------------------------+

			Filename			: TStartDefilEnum.java
			Creation date		: 16 mars 08
		
			Project				: Clavicom
			Package				: clavicom.tools

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2008) Centre ICOM'

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

public final class TStartDefilEnum 
{
	private TStartDefilEnum() 
	{
		// Rien à faire
	}

	// Liste des valeurs
	public final static TStartDefilEnum ON_STARTUP		= new TStartDefilEnum();
	public final static TStartDefilEnum ON_ACTION_CLICK	= new TStartDefilEnum();    
	
	public static TStartDefilEnum getValue( String str )
	{
	if( str.equals( "ON_STARTUP" ) )
	{
		return ON_STARTUP;
	} 
	else if( str.equals( "ON_ACTION_CLICK" ) )
	{
		return ON_ACTION_CLICK;
	}
	
	return null;
	}
	
	public static String getString( TStartDefilEnum val )
	{
		if( val == ON_STARTUP )
		{
			return "ON_STARTUP";
		} 
		else if( val == ON_ACTION_CLICK )
		{
			return "ON_ACTION_CLICK";
		} 
		
		return "";
	}
	
	@Override
	public String toString()
	{
		if( this == ON_STARTUP )
		{
			return UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_LB_STARTDEFIL_ON_STARTUP");
		} 
		else if( this == ON_ACTION_CLICK )
		{
			return UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_LB_STARTDEFIL_ON_ACTION_CLICK");
		} 
		
		return "";
	}
} 


