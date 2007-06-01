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

import java.awt.Font;

import javax.swing.event.EventListenerList;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.gui.message.CMessage;
import clavicom.gui.message.NewMessageListener;
import clavicom.tools.TXMLNames;

public class CFont
{
	//--------------------------------------------------------- CONSTANTES --//
	final String DEFAULT_FONT_NAME = "Arial";
	//---------------------------------------------------------- VARIABLES --//	
	Font usedFont;
	boolean autoSize;
	
	protected EventListenerList listenerNewMessageList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CFont (Element nodeFont) throws Exception
	{
		// Initialisation
		listenerNewMessageList = new EventListenerList();
		
		// Récupération du nom de la police
		String fontName = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_NAME);
		
		if(fontName == null || fontName.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_NAME + 
									UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_2"));
		}
		
		// Récupération de la taille
		String strFontSize = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_SIZE);
		
		if(strFontSize == null || strFontSize.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_SIZE + 
									UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_2"));
		}
		
		int fontSize;
		try
		{
			fontSize = Integer.parseInt(strFontSize);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_1") +
									strFontSize + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_2") + 
									TXMLNames.PR_ELEMENT_FONT_SIZE + 
									UIString.getUIString("EX_PROFIL_FONT_BAD_CAST_3"));			
		}
		
		
		// Autosize		
		String strAutoSize = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE);
		if(strAutoSize == null || strAutoSize.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE + 
									UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_2"));
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
		
		// Bold		
		String strBold = nodeFont.getChildText(TXMLNames.PR_ELEMENT_FONT_BOLD);
		if(strBold == null || strBold.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_BOLD + 
									UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_2"));
		}		
		
		boolean bold;
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
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_1") +
									TXMLNames.PR_ELEMENT_FONT_ITALIC + 
									UIString.getUIString("EX_PROFIL_FONT_ATTRIBUTE_MISSING_2"));
		}		
		
		boolean italic;
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
		
		// Vérification que la police a le bon nom
		if (Font.getFont(fontName) == null)
		{
			fontName = DEFAULT_FONT_NAME;
			fireNewMessage (new CMessage(	UIString.getUIString("MSG_FONT_MISSING_FONT_1") + 
											fontName + 
											UIString.getUIString("MSG_FONT_MISSING_FONT_2") + 
											DEFAULT_FONT_NAME +
											UIString.getUIString("MSG_FONT_MISSING_FONT_3")));
			
		}
		
		// Création du style
		int style = Font.PLAIN;
		if(bold == true)
		{
			style += Font.BOLD;
		}
		
		if(italic == true)
		{
			style += Font.ITALIC;
		}
		
		usedFont = new Font(fontName,style,fontSize);
		
		if(usedFont == null)
		{
			throw new Exception (	UIString.getUIString("EX_PROFIL_FONT_IMPOSSIBLE_LOAD_1") +
									fontName + 
									UIString.getUIString("EX_PROFIL_FONT_IMPOSSIBLE_LOAD_2"));
		}	
		
	}
	
	
	//----------------------------------------------------------- METHODES --//	
	public Element buildNode()
	{		
		// Création de l'element
		Element eltFont = new Element(TXMLNames.PR_ELEMENT_FONT);
		
		// Ajout du nom de la police
		Element eltName = new Element (TXMLNames.PR_ELEMENT_FONT_NAME);
		eltName.setText(usedFont.getFamily());
		eltFont.addContent(eltName);
		
		// Ajout de la taille		
		Element eltSize = new Element (TXMLNames.PR_ELEMENT_FONT_SIZE);
		eltSize.setText(String.valueOf(usedFont.getSize()));
		eltFont.addContent(eltSize);	
		
		// Ajout de l'autosize
		Element eltAutoSize = new Element (TXMLNames.PR_ELEMENT_FONT_AUTO_SIZE);
		eltAutoSize.setText(String.valueOf(autoSize));
		eltFont.addContent(eltAutoSize);
		
		// Ajout du bold
		Element eltBold = new Element (TXMLNames.PR_ELEMENT_FONT_BOLD);
		eltBold.setText(String.valueOf(usedFont.isBold()));
		eltFont.addContent(eltBold);
		
		// Ajout du italic
		Element eltItalic = new Element (TXMLNames.PR_ELEMENT_FONT_ITALIC);
		eltItalic.setText(String.valueOf(usedFont.isItalic()));
		eltFont.addContent(eltItalic);
		
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


	public Font getUsedFont()
	{
		return usedFont;
	}


	public void setUsedFont(Font usedFont)
	{
		this.usedFont = usedFont;
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
	
}
