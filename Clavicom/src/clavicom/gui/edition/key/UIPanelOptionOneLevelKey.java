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

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import clavicom.CFilePaths;
import clavicom.core.keygroup.keyboard.key.CKeyOneLevel;
import clavicom.gui.edition.key.captionchoozer.UIPanelCaptionChooser;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.UICaptionChooserListener;

public class UIPanelOptionOneLevelKey extends UIPanelOptionKeyboardKey implements UICaptionChooserListener
{


	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	protected CKeyOneLevel keyOneLevel;
	protected UIPanelCaptionChooser captionChooser;
	protected JCheckBox checkboxIsImage;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionOneLevelKey()
	{
		super( );
		
		JPanel panelGlobal = new JPanel();
		panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
		
		// Création des objets
		captionChooser = new UIPanelCaptionChooser(CFilePaths.getUserPicturesFolder());
		checkboxIsImage = new JCheckBox(	new AbstractAction()
											{
												public void actionPerformed(ActionEvent e)
												{
													onChecked();
												}
											});		
		// Ajout du titre
		checkboxIsImage.setText(UIString.getUIString("LB_KEYSONELEVEL_CAPTION_CHECKBOX_LABEL"));
		
		// Ajout au panel
		panelGlobal.add(checkboxIsImage);
		
		panelGlobal.add(captionChooser);

		// Ajout du titre
		panelGlobal.setBorder( BorderFactory.createTitledBorder( 
				BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_KEYSONELEVEL_BORDER")) );
		
		// Ajout en tant que listener du chooser
		captionChooser.addCaptionChooserListener(this);
		
		add(panelGlobal);
	}

	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyOneLevel(CKeyOneLevel myKeyOneLevel)
	{
		// Appel au père
		setValuesKey(myKeyOneLevel);
		
		keyOneLevel = myKeyOneLevel;
		
		// Mise à jour de la caption
		captionChooser.setIsImage(keyOneLevel.isCaptionImage());
		
		// On met à jour la checkbox
		checkboxIsImage.setSelected(keyOneLevel.isCaptionImage());
		
		// On met à jour la combo
		if(keyOneLevel.isCaptionImage() == true)
		{
			captionChooser.getComboImages().selectGoodImage(keyOneLevel.getCaption());
		}
		else
		// On met à jour le texte
		{
			captionChooser.setCaptionText(keyOneLevel.getCaption());
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Appelé lorsque la checkbox est cochée
	 */
	protected void onChecked()
	{
		// On met à jour l'objet du noyau
		keyOneLevel.setCaptionImage(checkboxIsImage.isSelected());

		// On grise les composants
		captionChooser.setIsImage(checkboxIsImage.isSelected());
		
		// On met à jour le noyau
		keyOneLevel.setCaption(captionChooser.getCaption());
	}

	public void captionChanged(UIPanelCaptionChooser captionChooser, String newCaption, boolean isImage)
	{
		if (captionChooser == this.captionChooser)
		{
			// On met à jour le noyau
			keyOneLevel.setCaption(newCaption);
		}
	}
}
