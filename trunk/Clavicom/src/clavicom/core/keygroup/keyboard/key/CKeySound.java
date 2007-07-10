/*-----------------------------------------------------------------------------+

			Filename			: CKeyLauncher.java
			Creation date		: 22 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.key

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

package clavicom.core.keygroup.keyboard.key;

import java.awt.Color;
import org.jdom.Element;
import clavicom.CFilePaths;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.engine.sound.ThreadSound;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeySound extends CKeyOneLevel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	String soundPath;
	
	ThreadSound soundThread;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeySound(
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered ,
			boolean holdable,
			TPoint myPointMin, 
			TPoint myPointMax)
	{
		this (	myColorNormal, 
				myColorClicked , 
				myColorEntered ,
				holdable,
				myPointMin, 
				myPointMax,
				"");
		
		soundThread = new ThreadSound( soundPath );
		soundThread.start();
	}
	
	public CKeySound(
			Color myColorNormal, 
			Color myColorClicked , 
			Color myColorEntered , 
			boolean holdable,
			TPoint myPointMin, 
			TPoint myPointMax,
			String myCaption)
	{
		super(myColorNormal,myColorClicked,myColorEntered,holdable,myPointMin,myPointMax,myCaption);
		
		soundThread = new ThreadSound( soundPath );
		soundThread.start();
	}
	
	public CKeySound (Element eltKeyLauncher) throws Exception
	{
		// On appelle le chargement du père, qui récupèrera seulement les élements
		// qui le concerne.
		
		super(eltKeyLauncher);
		
		// Chargement du path
		Element eltPath = eltKeyLauncher.getChild(TXMLNames.KY_ELEMENT_SOUND_PATH);
		
		if(eltPath == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEYSOUND_MISSING_PATH_1") + 
									TXMLNames.KY_ELEMENT_SOUND_PATH + 
									UIString.getUIString("EX_KEYSOUND_MISSING_PATH_2")) ;		
		}
		soundPath = eltPath.getText();
		
		soundThread = new ThreadSound( soundPath );
		soundThread.start();
		
	}

	//----------------------------------------------------------- METHODES --//	
	

	
	public void completeNodeSpecific2(Element keyNode) throws Exception
	{		
		// Ajout des elements spécifiques
		Element eltPath = new Element(TXMLNames.KY_ELEMENT_SOUND_PATH);
		
		eltPath.setText(soundPath);
		
		keyNode.addContent(eltPath);
	}
	
	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_SOUND;
	}
	
	public String getSoundPath()
	{
		return soundPath;
	}

	public void setSoundPath(String soundPath)
	{
		this.soundPath = soundPath;
	}
	//--------------------------------------------------- METHODES PRIVEES --//

	@Override
	protected Boolean toBeSave()
	{
		return true;
	}

	@Override
	public void Click()
	{
		// jouer le son
		try
		{
			soundThread.play();
		}
		catch( Exception ex )
		{
			CMessageEngine.newError( UIString.getUIString("MSG_SOUND_NOT_LOADED") + " : " + soundPath, ex.getMessage() );
		}
	}
	
	@Override
	public String getCaption()
	{
		if(isCaptionImage())
			return CFilePaths.getUserPicturesFolder() + caption;
		else
			return caption;
	}
	
	@Override
	public String toString()
	{
		return ( 	UIString.getUIString("ST_KEY_TOSTRING_SOUND") +
					" [" + caption + "]");
	}
}
