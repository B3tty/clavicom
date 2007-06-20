/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionOneLevelKey.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key.option

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

package clavicom.gui.edition.key;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.key.CKeyOneLevel;
import clavicom.gui.edition.key.captionchoozer.UIPanelCaptionChooser;

public class UIPanelOptionOneLevelKey extends UIPanelOptionKeyboardKey
{


	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	protected CKeyOneLevel keyOneLevel;
	protected UIPanelCaptionChooser captionChoozer;
	protected JCheckBox checkboxIsImage;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionOneLevelKey()
	{
		super( );
		
		// Création des objets
		captionChoozer = new UIPanelCaptionChooser(CFilePaths.getUserPicturesFolder());
		checkboxIsImage = new JCheckBox(	new AbstractAction()
											{
												public void actionPerformed(ActionEvent e)
												{
													onChecked();
												}
											});		
		// Ajout au panel
		add(captionChoozer);
		add(checkboxIsImage);
	}

	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyOneLevel(CKeyOneLevel myKeyOneLevel)
	{
		// Appel au père
		setValuesKey(myKeyOneLevel);
		
		keyOneLevel = myKeyOneLevel;
		
		// Mise à jour de la caption
		captionChoozer.setIsImage(keyOneLevel.isCaptionImage());
		
		if(keyOneLevel.isCaptionImage() == true)
		{
			captionChoozer.getComboImages().selectGoodImage(keyOneLevel.getCaption());
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Appelé lorsque la checkbox est cochée
	 */
	protected void onChecked()
	{
		// On grise les composants
		captionChoozer.setIsImage(checkboxIsImage.isSelected());
	}
}
