/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationSound.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

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

package clavicom.gui.configuration;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import clavicom.core.profil.CSound;
import clavicom.gui.language.UIString;

public class PanelModificationProfilSound extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CSound sound;
	JCheckBox checkDefil;
	JCheckBox checkEntered;
	JCheckBox checkPressed;
	JCheckBox checkMouseDelay;
	JCheckBox checkStart;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilSound(CSound mySound)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND") );
		
		sound = mySound;
		
		LoadComponents();
		
		initValues();
	}
	
	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		JPanel pan = new JPanel();
		JPanel panelGlobal = new JPanel( new BorderLayout() );
		
		checkDefil = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND_ISDEFIL"), sound.isSoundOnDefil() );
		panelGlobal.add( checkDefil, BorderLayout.NORTH );
		
		checkEntered = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND_ISENTERED"), sound.isSoundOnSurvol() );
		panelGlobal.add( checkEntered, BorderLayout.CENTER );
		
		JPanel panelSouth = new JPanel( new BorderLayout() );
		checkPressed = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND_ISPRESSED"), sound.isSoundOnClic() );
		panelSouth.add( checkPressed, BorderLayout.CENTER );
		
		checkStart = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND_ISSTARTED"), sound.isSoundOnStartApplication() );
		panelSouth.add( checkStart, BorderLayout.SOUTH );
		
		checkMouseDelay = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND_IDMOUSEDELAY"), sound.isSoundOnMouseDelay() );
		panelSouth.add( checkMouseDelay, BorderLayout.SOUTH );
		
		panelGlobal.add( panelSouth, BorderLayout.SOUTH );
		
		pan.add(panelGlobal);
		
		add(pan, BorderLayout.CENTER);
	}

	

	//----------------------------------------------------------- METHODES --//	

	public void initValues()
	{
		checkDefil.setSelected( sound.isSoundOnDefil() );
		checkEntered.setSelected( sound.isSoundOnSurvol() );
		checkPressed.setSelected( sound.isSoundOnClic() );
		checkMouseDelay.setSelected( sound.isSoundOnMouseDelay());
		checkStart.setSelected( sound.isSoundOnStartApplication() );
	}
	
	@Override
	public boolean validateDataEntry()
	{
		return change(true);
	}
	
	@Override
	public boolean isChanged()
	{
		return change(false);
	}
	
	protected boolean change( boolean saveData )
	{
		// Si la gestion du son a changé, on la change dans le profil
		boolean retour = false;		
		if( checkDefil.isSelected() != sound.isSoundOnDefil() )
		{
			if( saveData )
			{
				sound.setSoundOnDefil( checkDefil.isSelected() );
			}
			retour = true;
		}
		if( checkEntered.isSelected() != sound.isSoundOnSurvol() )
		{
			if( saveData )
			{
				sound.setSoundOnSurvol( checkEntered.isSelected() );
			}
			retour = true;
		}
		if( checkPressed.isSelected() != sound.isSoundOnClic() )
		{
			if( saveData )
			{
				sound.setSoundOnClic( checkPressed.isSelected() );
			}
			retour = true;
		}
		if( checkMouseDelay.isSelected() != sound.isSoundOnMouseDelay() )
		{
			if( saveData )
			{
				sound.setSoundOnMouseDelay( checkMouseDelay.isSelected() );
			}
			retour = true;
		}
		if( checkStart.isSelected() != sound.isSoundOnStartApplication() )
		{
			if( saveData )
			{
				sound.setSoundOnStartApplication( checkStart.isSelected() );
			}
			retour = true;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
