/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationNavigation.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import clavicom.core.profil.CNavigation;
import clavicom.gui.language.UIString;
import clavicom.tools.TNavigationType;

public class PanelModificationProfilNavigation extends PanelModificationProfil implements ActionListener
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CNavigation navigation;
	JRadioButton radioButtonStandard;
	JRadioButton radioButtonDefilement;
	JRadioButton radioButtonClickTempo;
	JSlider sliderTempoDefil;
	JCheckBox blocSelection;
	JSlider sliderTempoClic;
	JSlider sliderMouseSpeed;
	JCheckBox rollOver;
	JLabel labelTempoDefil;
	JLabel labelTempoClic;
	JLabel labelTempoSouriom;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilNavigation(CNavigation myNavigation)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION") );
		
		navigation = myNavigation;
		
		LoadComponents();
		
		InitValues();
	}

	
	private void InitValues()
	{
		if( navigation.getTypeNavigation() == TNavigationType.STANDARD )
		{
			SwitchStandardMode();
			radioButtonStandard.setSelected(true);
			radioButtonDefilement.setSelected(false);
			radioButtonClickTempo.setSelected(false);
			
			
		} else if( navigation.getTypeNavigation() == TNavigationType.DEFILEMENT )
		{
			SwitchDefilementMode();
			radioButtonStandard.setSelected(false);
			radioButtonDefilement.setSelected(true);
			radioButtonClickTempo.setSelected(false);
			
			sliderTempoDefil.setValue( navigation.getTemporisationDefilement() );
		} else if( navigation.getTypeNavigation() == TNavigationType.CLICK_TEMPORISE )
		{
			SwitchClicMode();
			radioButtonStandard.setSelected(false);
			radioButtonDefilement.setSelected(false);
			radioButtonClickTempo.setSelected(true);
			
			sliderTempoClic.setValue( navigation.getTemporisationDefilement() );
			blocSelection.setSelected( navigation.isBlockSelectionActive() );
		}
		
		sliderMouseSpeed.setValue( navigation.getTemporisationClic() );
		rollOver.setSelected( navigation.isRolloverActive() ); 
	}

	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		
		JPanel panelGlobal = new JPanel( ); 
		panelGlobal.setLayout( null );
		
		// ========================================================================
		// Groupe de radio boutton pour la séléction du mode
		// ========================================================================
		ButtonGroup buttonGroup = new ButtonGroup();
		
		// navigation standard
		radioButtonStandard = new JRadioButton( TNavigationType.STANDARD.toString(), true );
		buttonGroup.add( radioButtonStandard );
		radioButtonStandard.addActionListener(this);
		
		panelGlobal.add( radioButtonStandard );
		

		
		
		// ========================================================================
		// Séléction du clic temporisé
		// ========================================================================
		
		// navigation clic temporisé
		radioButtonClickTempo = new JRadioButton( TNavigationType.CLICK_TEMPORISE.toString(), false );
		buttonGroup.add( radioButtonClickTempo );
		radioButtonClickTempo.addActionListener(this);
		
		panelGlobal.add( radioButtonClickTempo );
		
		
		
		// on initialise avec la temporisation de défilement  
		labelTempoClic = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_CLICK_TEMPORISATION") + " : " );
		panelGlobal.add( labelTempoClic );
		

		
		sliderTempoClic = new JSlider();
		sliderTempoClic.setMaximum( 3000 );
		sliderTempoClic.setMinimum( 500 );
		sliderTempoClic.setMajorTickSpacing(500);
		sliderTempoClic.setMinorTickSpacing(500);
		sliderTempoClic.setPaintTicks(true);
		sliderTempoClic.setPaintLabels(true);
		sliderTempoClic.setValue( navigation.getTemporisationDefilement() );
		
		panelGlobal.add( sliderTempoClic );
		
		
		
		// ========================================================================
		// Séléction du défilement
		// ========================================================================
		
		// navigation défilement
		radioButtonDefilement = new JRadioButton( TNavigationType.DEFILEMENT.toString(), false );
		buttonGroup.add( radioButtonDefilement );
		radioButtonDefilement.addActionListener(this);
		panelGlobal.add( radioButtonDefilement );
		
		
		
		// on initialise avec la temporisation de défilement  
		labelTempoDefil = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT_TEMPORISATION") + " : " );
		panelGlobal.add( labelTempoDefil );
		
		
		sliderTempoDefil = new JSlider();
		sliderTempoDefil.setMaximum( 3000 );
		sliderTempoDefil.setMinimum( 500 );
		sliderTempoDefil.setMajorTickSpacing(500);
		sliderTempoDefil.setMinorTickSpacing(500);
		sliderTempoDefil.setPaintTicks(true);
		sliderTempoDefil.setPaintLabels(true);
		sliderTempoDefil.setValue( navigation.getTemporisationDefilement() );
		
		panelGlobal.add( sliderTempoDefil );
		
		
		// checkBox BlocSelection
		blocSelection = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT_BLOC"), navigation.isRolloverActive() );
		panelGlobal.add( blocSelection );

		// ========================================================================
		// Séléction du rollOver ou non
		// ========================================================================

		rollOver = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_ROLLOVER"), navigation.isRolloverActive() );
		panelGlobal.add( rollOver );

		// ========================================================================
		// Séléction de la temporisation du clic de la souricom
		// ========================================================================
		
		labelTempoSouriom = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_MOUSE_SPEED") + " : " );
		panelGlobal.add( labelTempoSouriom );

		sliderMouseSpeed = new JSlider();
		sliderMouseSpeed.setMaximum( 100 );
		sliderMouseSpeed.setMinimum( 0 );
		sliderMouseSpeed.setMajorTickSpacing(10);
		sliderMouseSpeed.setMinorTickSpacing(10);
		sliderMouseSpeed.setPaintTicks(true);
		sliderMouseSpeed.setPaintLabels(true);
		
		panelGlobal.add( sliderMouseSpeed );
		
		
		// ========================================================================
		// Placement des composants
		// ========================================================================
		radioButtonStandard.setLocation( 10, 10 );
		
		radioButtonClickTempo.setLocation( 10, 85 );
		labelTempoClic.setLocation( 165, 60 );
		sliderTempoClic.setLocation( 155, 90 );
		
		radioButtonDefilement.setLocation( 10, 185 );
		labelTempoDefil.setLocation( 165, 160 );
		sliderTempoDefil.setLocation( 155, 190 );
		blocSelection.setLocation( 255, 250 );
		
		rollOver.setLocation( 16, 300 );
		
		labelTempoSouriom.setLocation( 20, 330 );
		sliderMouseSpeed.setLocation( 10, 360 );

		
		
		
		
		// ========================================================================
		// taille des composants
		// ========================================================================
		
		radioButtonStandard.setSize( 100, 30 );
		
		radioButtonClickTempo.setSize( 120, 30 );
		labelTempoClic.setSize( 200, 30 );
		sliderTempoClic.setSize( 300, 50 );
		
		radioButtonDefilement.setSize( 120, 30 );
		labelTempoDefil.setSize( 200, 30 );
		sliderTempoDefil.setSize( 300, 50 );
		blocSelection.setSize( 200, 30 );
		
		rollOver.setSize( 400, 30 );
		
		labelTempoSouriom.setSize( 200, 30 );
		sliderMouseSpeed.setSize( 300, 50 );
		
		
		

		add( panelGlobal, BorderLayout.CENTER );
	}


	//----------------------------------------------------------- METHODES --//
	
	@Override
	public boolean validateDataEntry()
	{
		return change( true );
	}


	public void actionPerformed(ActionEvent arg0)
	{
		// changement de radio boutton...
		if( radioButtonStandard.isSelected() )
		{
			SwitchStandardMode();
		} else if( radioButtonDefilement.isSelected() )
		{
			SwitchDefilementMode();
		} else if( radioButtonClickTempo.isSelected() )
		{
			SwitchClicMode();
		}
		
	}


	private void SwitchClicMode()
	{
		sliderTempoClic.setEnabled( true );
		
		sliderTempoDefil.setEnabled( false );
		blocSelection.setEnabled( false );
		
		labelTempoClic.setEnabled( true );
		labelTempoDefil.setEnabled( false );
	}


	private void SwitchDefilementMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( true );
		blocSelection.setEnabled( true );
		
		labelTempoClic.setEnabled( false );
		labelTempoDefil.setEnabled( true );
		
	}


	private void SwitchStandardMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( false );
		blocSelection.setEnabled( false );
		
		labelTempoClic.setEnabled( false );
		labelTempoDefil.setEnabled( false );
	}


	@Override
	public boolean isChanged()
	{
		return change( false );
	}
	
	protected boolean change( boolean saveData )
	{
		// Si la navigation a changé, on la change dans le profil
		boolean retour = false;
		
		if( radioButtonStandard.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.STANDARD )
			{
				if ( saveData )
					navigation.setTypeNavigation( TNavigationType.STANDARD );
				
				retour = true;
			}
		}else if( radioButtonDefilement.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.DEFILEMENT )
			{
				if ( saveData )
				{
					navigation.setTypeNavigation( TNavigationType.DEFILEMENT );
	
					navigation.setTemporisationDefilement( sliderTempoDefil.getValue() );
					
					navigation.setBlockSelectionActive( rollOver.isSelected() );
				}
				
				retour = true;
			}
		}else if( radioButtonClickTempo.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.CLICK_TEMPORISE )
			{
				if ( saveData )
				{
					navigation.setTypeNavigation( TNavigationType.CLICK_TEMPORISE );
					navigation.setTemporisationDefilement( sliderTempoDefil.getValue() );
				}
				
				retour = true;
			}
		}
		
		// sliderMouseSpeed
		if( sliderMouseSpeed.getValue() != navigation.getMouseSpeed() )
		{
			if ( saveData )
				navigation.setMouseSpeed( sliderMouseSpeed.getValue() );
			
			retour = true;
		}
		
		// rollOver
		if( rollOver.isSelected() != navigation.isRolloverActive() )
		{
			if ( saveData )
				navigation.setRolloverActive( rollOver.isSelected() );
			
			retour = true;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
