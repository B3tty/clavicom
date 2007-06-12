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

import java.awt.Color;
import java.awt.Font;

import javax.swing.event.EventListenerList;

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CFont
{
	//--------------------------------------------------------- CONSTANTES --//
	final String DEFAULT_FONT_NAME = "Arial";
	final float DEFAULT_FONT_HEIGHT_FACTOR = .1f;
	//---------------------------------------------------------- VARIABLES --//	
	boolean autoSize;
	
	String fontName;
	CColor fontColor;
	boolean autoColor;
	boolean shadow;
	boolean bold;
	boolean italic;
	
	float heightFactor;
	
	protected EventListenerList listenerNewMessageList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CFont (Element nodeFont) throws Exception
	{
		// Initialisation
		listenerNewMessageList = new EventListenerList();
		heightFactor = DEFAULT_FONT_HEIGHT_FACTOR;
		
		// Récupération du nom de la police
		fontName = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_NAME);
		
		if(fontName == null || fontName.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_NAME + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}
		
		// Autosize		
		String strAutoSize = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE);
		if(strAutoSize == null || strAutoSize.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}		

		try
		{
			autoSize = Boolean.parseBoolean(strAutoSize);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strAutoSize + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));					
		}
		
		// Récupération de la taille
		
		if (autoSize == false)
		// On ne récupère la taille que si elle est nécessaire
		{
			
			String strFontSize = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_SIZE);
			
			if(strFontSize == null || strFontSize.equals(""))
			{
				throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
										TXMLNames.PR_ELEMENT_FONT_SIZE + 
										UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
			}
			
			
			try
			{
				heightFactor = Float.parseFloat(strFontSize);
			}
			catch (Exception ex)
			{
				throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
										strFontSize + 
										UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
										TXMLNames.PR_ELEMENT_FONT_SIZE + 
										UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));			
			}
		}
		else
		{
			heightFactor = DEFAULT_FONT_HEIGHT_FACTOR;
		}
		
		// Bold		
		String strBold = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_BOLD);
		if(strBold == null || strBold.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_BOLD + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}		

		try
		{
			bold = Boolean.parseBoolean(strBold);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strBold + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_BOLD + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));					
		}

		// Italic		
		String strItalic = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_ITALIC);
		if(strItalic == null || strItalic.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_ITALIC + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}		

		try
		{
			italic = Boolean.parseBoolean(strItalic);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strItalic + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_ITALIC + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));					
		}
		
		// AutoColor		
		String strAutoColor = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_AUTO_COLOR);
		if(strAutoColor == null || strAutoColor.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_AUTO_COLOR + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}		
		
		try
		{
			autoColor = Boolean.parseBoolean(strAutoColor);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strAutoColor + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_AUTO_COLOR + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));					
		}
		
		// Récupération de la couleur
		if(autoColor == false)
		{
			// Récupération de l'element color
			Element eltColor = nodeFont.getChild(TXMLNames.PR_ELEMENT_FONT_COLOR);
			
			if(eltColor == null)
			{
				throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
										TXMLNames.PR_ELEMENT_FONT_COLOR + 
										UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
			}	
			
			// Création de la CColor
			try
			{
				fontColor = new CColor(eltColor);
			}
			catch (Exception ex)
			{
				throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_COLOR") +
										ex.getMessage()); 
	
			}
		}
		
		// Ombre		
		String strShadow = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_SHADOW);
		if(strShadow == null || strShadow.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_SHADOW + 
									UIString.getUIString("EX_PROFIL_FONT_ELEMENT_MISSING_2"));
		}		
		
		try
		{
			shadow = Boolean.parseBoolean(strShadow);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strShadow + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_SHADOW + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));					
		}
		
		// Vérification que la police a le bon nom
		if (Font.getFont( fontName ) == null)
		{
			fontName = DEFAULT_FONT_NAME;
			fireNewMessage (new CMessage(	UIString.getUIString("MSG_FONT_MISSING_FONT_1") + 
											fontName + 
											UIString.getUIString("MSG_FONT_MISSING_FONT_2") + 
											DEFAULT_FONT_NAME +
											UIString.getUIString("MSG_FONT_MISSING_FONT_3")));
			
		}		
	}
	
	
	public CFont( String myFontName )
	{
		this( 
				myFontName,
				false,
				new CColor( Color.black ),
				false,
				true,
				50,
				false,
				false);
	}
	
	public CFont(
			String myFontName,
			boolean myAutoSize, 
			CColor myFontColor, 
			boolean myAutoColor,
			boolean myShadow,
			float myHeightFactor,
			boolean myBold,
			boolean myItalic)
	{
		fontName = myFontName;
		autoSize = myAutoSize;
		fontColor = myFontColor;
		autoColor = myAutoColor;
		shadow = myShadow;
		heightFactor = myHeightFactor;
		bold = myBold;
		italic = myItalic;
	}
	
	//----------------------------------------------------------- METHODES --//	
	public Element buildNode()
	{		
		// Création de l'element
		Element eltFont = new Element(TXMLNames.PR_ELEMENT_FONT);
		
		// Ajout du nom de la police
		Element eltName = new Element (TXMLNames.PR_ELEMENT_FONT_NAME);
		eltName.setText(fontName);
		eltFont.addContent(eltName);
		
		// Ajout de l'autosize
		Element eltAutoSize = new Element (TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE);
		eltAutoSize.setText(String.valueOf(autoSize));
		eltFont.addContent(eltAutoSize);
		
		// Ajout de la taille
		if (autoSize == false)
		{
			Element eltSize = new Element (TXMLNames.PR_ELEMENT_FONT_SIZE);
			eltSize.setText(String.valueOf(heightFactor));
			eltFont.addContent(eltSize);	
		}
		
		// Ajout de l'autocolor
		Element eltAutoColor = new Element (TXMLNames.PR_ELEMENT_FONT_AUTO_COLOR);
		eltAutoColor.setText(String.valueOf(autoColor));
		eltFont.addContent(eltAutoColor);
		
		// Ajout de la taille
		if (autoColor == false)
		{
			Element eltColor = new Element (TXMLNames.PR_ELEMENT_FONT_COLOR);
			eltColor = fontColor.BuildNode();
			eltColor.setName(TXMLNames.PR_ELEMENT_FONT_COLOR);
			eltFont.addContent(eltColor);
		}
		
		// Ajout du bold
		Element eltBold = new Element (TXMLNames.PR_ELEMENT_FONT_BOLD);
		eltBold.setText(String.valueOf(bold));
		eltFont.addContent(eltBold);
		
		// Ajout du italic
		Element eltItalic = new Element (TXMLNames.PR_ELEMENT_FONT_ITALIC);
		eltItalic.setText(String.valueOf(italic));
		eltFont.addContent(eltItalic);
		
		// Ajout de l'ombre
		Element eltShadow = new Element (TXMLNames.PR_ELEMENT_FONT_SHADOW);
		eltShadow.setText(String.valueOf(shadow));
		eltFont.addContent(eltShadow);
		
		// Fin
		return eltFont;
	}
	
	// Listeners
	public void addNewMessageListener(NewMessageListener l)
	{
		this.listenerNewMessageList.add(NewMessageListener.class, l);
	}
	
	
	public void removeNewMessageListener(NewMessageListener l)
	{
		this.listenerNewMessageList.remove(NewMessageListener.class, l);
	}	
	
	
	public boolean isAutoSize()
	{
		return autoSize;
	}


	public void setAutoSize(boolean autoSize)
	{
		this.autoSize = autoSize;
	}

	public boolean isAutoColor()
	{
		return autoColor;
	}


	public void setAutoColor(boolean autoColor)
	{
		this.autoColor = autoColor;
	}


	public CColor getFontColor()
	{
		return fontColor;
	}


	public void setFontColor(CColor fontColor)
	{
		this.fontColor = fontColor;
	}


	public boolean isShadow()
	{
		return shadow;
	}


	public void setShadow(boolean shadow)
	{
		this.shadow = shadow;
	}


	public float getHeightFactor()
	{
		return heightFactor;
	}


	public void setHeightFactor(float heightFactor)
	{
		this.heightFactor = heightFactor;
	}
	
	public boolean isBold()
	{
		return bold;
	}

	public void setBold(boolean bold)
	{
		this.bold = bold;
	}

	public String getFontName()
	{
		return fontName;
	}

	public void setFontName(String fontName)
	{
		this.fontName = fontName;
	}

	public boolean isItalic()
	{
		return italic;
	}

	public void setItalic(boolean italic)
	{
		this.italic = italic;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	// Listeners
	protected void fireNewMessage( CMessage message )
	{
		NewMessageListener[] listeners = (NewMessageListener[]) listenerNewMessageList
				.getListeners(NewMessageListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].newMessage( message );
		}
	}
	
	@Override
	public String toString()
	{
		return getFontName();
	}
}
