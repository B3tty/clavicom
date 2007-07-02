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

public final class TClickSouricomEnum 
{
    private TClickSouricomEnum() 
    {
    	// Rien à faire
    }
    
    // Liste des valeurs
    public final static TClickSouricomEnum LEFT_PRESS		= new TClickSouricomEnum();
    public final static TClickSouricomEnum RIGHT_RELEASE	= new TClickSouricomEnum();
    public final static TClickSouricomEnum RIGHT_PRESS		= new TClickSouricomEnum();
    public final static TClickSouricomEnum LEFT_RELEASE		= new TClickSouricomEnum();    

    public static TClickSouricomEnum getValue( String clickSouricom )
    {
    	if( clickSouricom == "LEFT_PRESS" )
    	{
    		return LEFT_PRESS;
    	} else if( clickSouricom.equals( "RIGHT_RELEASE" ) )
    	{
    		return RIGHT_RELEASE;
    	} else if( clickSouricom.equals( "RIGHT_PRESS" ) )
    	{
    		return RIGHT_PRESS;
    	} else if( clickSouricom.equals( "LEFT_RELEASE" ) )
    	{
    		return LEFT_RELEASE;
    	}
    	
    	return LEFT_RELEASE;
    }
    
    public static String getString( TClickSouricomEnum clickSouricom )
    {
    	if( clickSouricom == LEFT_PRESS )
    	{
    		return "LEFT_PRESS";
    	} else if( clickSouricom == RIGHT_RELEASE )
    	{
    		return "RIGHT_RELEASE";
    	} else if( clickSouricom == RIGHT_PRESS )
    	{
    		return "RIGHT_PRESS";
    	} else if( clickSouricom == LEFT_RELEASE )
    	{
    		return "LEFT_RELEASE";
    	}
    	
    	return "";
    }
    
    @Override
    public String toString()
    {
    	if( this == LEFT_PRESS )
    	{
    		return "LEFT_PRESS";
    	} else if( this == RIGHT_RELEASE )
    	{
    		return "RIGHT_RELEASE";
    	} else if( this == RIGHT_PRESS )
    	{
    		return "RIGHT_PRESS";
    	} else if( this == LEFT_RELEASE )
    	{
    		return "LEFT_RELEASE";
    	}
    	
    	return "";
    }
} 

