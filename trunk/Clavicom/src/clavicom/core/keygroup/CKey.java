/*-----------------------------------------------------------------------------+

			Filename			: CKey.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core

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

package clavicom.core.keygroup;

import javax.swing.event.EventListenerList;
import java.awt.Color;

import org.jdom.Element;

import clavicom.core.listener.CKeyColorChangedListener;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;
import clavicom.tools.TXMLNames;

public abstract class CKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CColor normal;
	CColor clicked;
	CColor entered;
	
	boolean captionImage;

    // un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CKey( CColor myNormal, CColor myClicked, CColor myEntered )
	{
		normal = myNormal;
		clicked = myClicked;
		entered = myEntered;
	}
	
	public CKey( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) );
		}
		
		// ===============================================================
		//	Récupération de l'élément colors
		// ===============================================================
		Element colors = node.getChild( TXMLNames.CO_ELEMENT_COLORS );
		if( colors == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLORS );
		}
		
		// =================================================================
		// Récupération de la color_normal
		// =================================================================
		Element color_normal_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Récupération de la color_clicked
		// =================================================================
		Element color_clicked_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_CLICKED );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Récupération de la color_entrered
		// =================================================================
		Element color_entered_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_ENTERED );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Récupération du booléen captionImage
		// =================================================================
		String caption_image_string = node.getAttributeValue( TXMLNames.KY_ATTRIBUTE_CAPTION_IMAGE );
		if( caption_image_string == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.KY_ATTRIBUTE_CAPTION_IMAGE );
		}
		try
		{
			captionImage = Boolean.parseBoolean( caption_image_string );
		}
		catch ( Exception ex )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) + "] : "+ UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + caption_image_string + UIString.getUIString( "EX_KEYGROUP_TO_BOOLEAN" ) );
		}
		
		// =================================================================
		// Création des couleurs
		// =================================================================
		try
		{
			normal = new CColor( color_normal_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_NORMAL +"]" + ex.getMessage() );
		}

		try
		{
			clicked = new CColor( color_clicked_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_CLICKED +"]" + ex.getMessage() );
		}
		
		try
		{
			entered = new CColor( color_entered_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_ENTERED +"]" + ex.getMessage() );
		}
		
	}

	//----------------------------------------------------------- METHODES --//

	public CColor GetColorNormal(){return normal;}
	public CColor GetColorClicked(){return clicked;}
	public CColor GetColorEntered(){return entered;}
	
	public boolean isCaptionImage()
	{
		return captionImage;
	}
	
	public Color getColor( TColorKeyEnum colorEnum )
	{
		if( colorEnum == TColorKeyEnum.NORMAL )
		{
			return normal.getColor();
		}
		else if( colorEnum == TColorKeyEnum.ENTERED )
		{
			return entered.getColor();
		}
		else if( colorEnum == TColorKeyEnum.PRESSED )
		{
			return clicked.getColor();
		}
		else
		{
			return Color.WHITE;
		}
	}
	
	public void setColor( Color color, TColorKeyEnum coloEnum )
	{
		if( coloEnum == TColorKeyEnum.NORMAL )
		{
			normal.setColor( color );
		}else if( coloEnum == TColorKeyEnum.ENTERED )
		{
			entered.setColor( color );
		}else if( coloEnum == TColorKeyEnum.PRESSED )
		{
			clicked.setColor( color );
		}
	}
	
	public Element BuildNode ( )
	{
		// ==================================================================
		// Création du noeud colors
		// ==================================================================
		Element colors = new Element( TXMLNames.CO_ELEMENT_COLORS );

		Element color_normal = normal.BuildNode ( );
		color_normal.setName( TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		colors.addContent( color_normal );
		
		Element color_clicked = clicked.BuildNode( );
		color_clicked.setName( TXMLNames.CO_ELEMENT_COLOR_CLICKED );
		colors.addContent( color_clicked );
		
		Element color_entered = entered.BuildNode( );
		color_entered.setName( TXMLNames.CO_ELEMENT_COLOR_ENTERED );
		colors.addContent( color_entered );
		
		return colors;
	}

	public abstract void Click(); 
	//--------------------------------------------------- METHODES PRIVEES --//
	
	// Listeners sur les couleurs
	public void addColorListener(CKeyColorChangedListener listener) 
	{
        listeners.add(CKeyColorChangedListener.class, listener);
    }
    
    public void removeColorListener(CKeyColorChangedListener listener) 
    {
        listeners.remove(CKeyColorChangedListener.class, listener);
    }
    
    public CKeyColorChangedListener[] getColorListeners() 
    {
        return listeners.getListeners(CKeyColorChangedListener.class);
    }
    
    protected void fireColorChanged(TColorKeyEnum colorType) 
    {
	    for ( CKeyColorChangedListener listener : getColorListeners() )
		{
			listener.colorChanged(colorType);
		}
    }	
}
