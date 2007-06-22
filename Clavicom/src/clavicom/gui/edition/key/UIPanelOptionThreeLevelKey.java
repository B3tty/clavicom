/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionThreeLevelKey.java
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
import clavicom.core.keygroup.keyboard.key.CKeyThreeLevel;
import clavicom.gui.edition.key.captionchoozer.UIPanelCaptionChooser;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.UICaptionChooserListener;
import clavicom.tools.TLevelEnum;

public class UIPanelOptionThreeLevelKey extends UIPanelOptionKeyboardKey implements UICaptionChooserListener
{


	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	protected CKeyThreeLevel keyThreeLevel;
	
	protected JCheckBox checkboxIsImage;
	
	protected UIPanelCaptionChooser captionChooserNormal;
	protected UIPanelCaptionChooser captionChooserShift;
	protected UIPanelCaptionChooser captionChooserAltGr;
	
	protected JPanel panelChoosers;
	
	protected JPanel panelNormal;
	protected JPanel panelShift;
	protected JPanel panelAltGr;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIPanelOptionThreeLevelKey()
	{
		super( );
		
		JPanel panelGlobal = new JPanel();
		panelGlobal.setLayout(new BoxLayout(panelGlobal, BoxLayout.Y_AXIS));
		
		// Création des objets
		captionChooserNormal = new UIPanelCaptionChooser(CFilePaths.getUserPicturesFolder());
		captionChooserShift = new UIPanelCaptionChooser(CFilePaths.getUserPicturesFolder());
		captionChooserAltGr = new UIPanelCaptionChooser(CFilePaths.getUserPicturesFolder());
		
		checkboxIsImage = new JCheckBox(	new AbstractAction()
											{
												public void actionPerformed(ActionEvent e)
												{
													onChecked();
												}
											});		
		panelChoosers = new JPanel();
		panelNormal = new JPanel();
		panelShift = new JPanel();
		panelAltGr = new JPanel();
		
		// Ajout du titre
		checkboxIsImage.setText(UIString.getUIString("LB_KEYSONELEVEL_CAPTION_CHECKBOX_LABEL"));

		// Ajout des panels de premier niveau
		panelGlobal.add(checkboxIsImage);
		panelGlobal.add(panelChoosers);
		
		panelChoosers.setLayout(new BoxLayout(panelChoosers, BoxLayout.X_AXIS));
		panelChoosers.add(panelNormal);
		panelChoosers.add(panelShift);
		panelChoosers.add(panelAltGr);
		
		panelNormal.add(captionChooserNormal);
		panelShift.add(captionChooserShift);
		panelAltGr.add(captionChooserAltGr);

		// Ajout des titres
		panelGlobal.setBorder( BorderFactory.createTitledBorder( 
							   BorderFactory.createLineBorder( Color.BLACK ), 
							   UIString.getUIString("LB_KEYSTHREELEVEL_BORDER")) );

		panelNormal.setBorder( BorderFactory.createTitledBorder( 
							   BorderFactory.createLineBorder( Color.BLACK ), 
							   UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_NORMAL")) );
		
		panelShift.setBorder(  BorderFactory.createTitledBorder( 
							   BorderFactory.createLineBorder( Color.BLACK ), 
							   UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_SHIFT")) );
		
		panelAltGr.setBorder(  BorderFactory.createTitledBorder( 
							   BorderFactory.createLineBorder( Color.BLACK ), 
							   UIString.getUIString("LB_KEYSTHREELEVEL_BORDER_ALTGR")) );
		
		// Ajout en tant que listener du chooser
		captionChooserNormal.addCaptionChooserListener(this);
		captionChooserShift.addCaptionChooserListener(this);
		captionChooserAltGr.addCaptionChooserListener(this);
		
		add(panelGlobal);
	}

	//----------------------------------------------------------- METHODES --//	
	public void setValuesKeyThreeLevel(CKeyThreeLevel myKeyThreeLevel)
	{
		// Appel au père
		setValuesKey(myKeyThreeLevel);
		
		// Affectation des attributs
		keyThreeLevel = myKeyThreeLevel;
		
		// Mise à jour de la caption
		captionChooserNormal.setIsImage(keyThreeLevel.isCaptionImage());
		captionChooserShift.setIsImage(keyThreeLevel.isCaptionImage());
		captionChooserAltGr.setIsImage(keyThreeLevel.isCaptionImage());
		
		// On met à jour la checkbox
		checkboxIsImage.setSelected(keyThreeLevel.isCaptionImage());
		
		// On met à jour la combo
		if(keyThreeLevel.isCaptionImage() == true)
		{
			captionChooserNormal.getComboImages().selectGoodImage(keyThreeLevel.getCaption(TLevelEnum.NORMAL));
			captionChooserShift.getComboImages().selectGoodImage(keyThreeLevel.getCaption(TLevelEnum.SHIFT));
			captionChooserAltGr.getComboImages().selectGoodImage(keyThreeLevel.getCaption(TLevelEnum.ALT_GR));
			
			captionChooserNormal.setCaptionText("");
			captionChooserShift.setCaptionText("");
			captionChooserAltGr.setCaptionText("");
		}
		else
		// On met à jour le texte
		{
			captionChooserNormal.setCaptionText(keyThreeLevel.getCaption(TLevelEnum.NORMAL));
			captionChooserShift.setCaptionText(keyThreeLevel.getCaption(TLevelEnum.SHIFT));
			captionChooserAltGr.setCaptionText(keyThreeLevel.getCaption(TLevelEnum.ALT_GR));
			
			captionChooserNormal.getComboImages().selectDefault();
			captionChooserShift.getComboImages().selectDefault();
			captionChooserAltGr.getComboImages().selectDefault();
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Appelé lorsque la checkbox est cochée
	 */
	protected void onChecked()
	{
		// On met à jour le noyau (image/non image)
		keyThreeLevel.setCaptionImage(checkboxIsImage.isSelected());
		
		// On grise les composants
		captionChooserNormal.setIsImage(checkboxIsImage.isSelected());
		captionChooserShift.setIsImage(checkboxIsImage.isSelected());
		captionChooserAltGr.setIsImage(checkboxIsImage.isSelected());
		
		// On met à jour le noyau
		keyThreeLevel.setCaption(captionChooserNormal.getCaption(),TLevelEnum.NORMAL, false);
		keyThreeLevel.setCaption(captionChooserShift.getCaption(),TLevelEnum.SHIFT, false);
		keyThreeLevel.setCaption(captionChooserAltGr.getCaption(),TLevelEnum.ALT_GR, true);			
	}

	public void captionChanged(UIPanelCaptionChooser captionChooser, String newCaption, boolean isImage)
	{
		// Mise à jour du noyau
		if (captionChooser == this.captionChooserNormal)
		{
			keyThreeLevel.setCaption(newCaption,TLevelEnum.NORMAL, true);
		}
		else if (captionChooser == this.captionChooserShift)
		{
			keyThreeLevel.setCaption(newCaption,TLevelEnum.SHIFT, true);
		}
		else if (captionChooser == this.captionChooserAltGr)
		{
			keyThreeLevel.setCaption(newCaption,TLevelEnum.ALT_GR, true);
		}
	}
}
