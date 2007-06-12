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
import java.awt.GridLayout;
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
		
		JPanel panelGlobal = new JPanel( new BorderLayout() ); 
		
		// ========================================================================
		// Groupe de radio boutton pour la séléction du mode
		// ========================================================================
		JPanel panelNavigationMode = new JPanel( new GridLayout( 3, 1 ) );
		ButtonGroup buttonGroup = new ButtonGroup();
		
		// navigation standard
		radioButtonStandard = new JRadioButton( TNavigationType.STANDARD.toString(), true );
		buttonGroup.add( radioButtonStandard );
		radioButtonStandard.addActionListener(this);
		
		
		// navigation clic temporisé
		
		JPanel panelClicTempo = new JPanel( new BorderLayout() );
		radioButtonClickTempo = new JRadioButton( TNavigationType.CLICK_TEMPORISE.toString(), false );
		buttonGroup.add( radioButtonClickTempo );
		radioButtonClickTempo.addActionListener(this);
		panelClicTempo.add( radioButtonClickTempo, BorderLayout.WEST );
		
		JPanel panelTempoClic = new JPanel();
		
		// on initialise avec la temporisation de défilement  
		JLabel labelTempoClic = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_CLICK_TEMPORISATION") );
		panelTempoClic.add( labelTempoClic );
		
		sliderTempoClic = new JSlider();
		sliderTempoClic.setMaximum( 100 );
		sliderTempoClic.setMinimum( 0 );
		sliderTempoClic.setMajorTickSpacing(10);
		sliderTempoClic.setMinorTickSpacing(10);
		sliderTempoClic.setPaintTicks(true);
		sliderTempoClic.setPaintLabels(true);
		sliderTempoClic.setValue( navigation.getTemporisationDefilement() );
		panelTempoClic.add( sliderTempoClic );
		
		panelClicTempo.add( panelTempoClic, BorderLayout.CENTER );
		
		
		
		// navigation défilement
		JPanel defilement = new JPanel( new BorderLayout() );
		radioButtonDefilement = new JRadioButton( TNavigationType.DEFILEMENT.toString(), false );
		buttonGroup.add( radioButtonDefilement );
		radioButtonDefilement.addActionListener(this);
		defilement.add( radioButtonDefilement, BorderLayout.WEST );
		
		
		JPanel panelTempoDefil = new JPanel();
		
		// on initialise avec la temporisation de défilement  
		JLabel labelTempoDefil = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT_TEMPORISATION") );
		panelTempoDefil.add( labelTempoDefil );
		
		sliderTempoDefil = new JSlider();
		sliderTempoDefil.setMaximum( 100 );
		sliderTempoDefil.setMinimum( 0 );
		sliderTempoDefil.setMajorTickSpacing(10);
		sliderTempoDefil.setMinorTickSpacing(10);
		sliderTempoDefil.setPaintTicks(true);
		sliderTempoDefil.setPaintLabels(true);
		sliderTempoDefil.setValue( navigation.getTemporisationDefilement() );
		panelTempoDefil.add( sliderTempoDefil );
		
		defilement.add( panelTempoDefil, BorderLayout.CENTER );
		
		
		
		// checkBox BlocSelection
		blocSelection = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT_BLOC"), navigation.isRolloverActive() );
		defilement.add( blocSelection, BorderLayout.EAST );

		
		panelNavigationMode.add( radioButtonStandard );
		panelNavigationMode.add( panelClicTempo );
		panelNavigationMode.add( defilement );
		
		panelGlobal.add( panelNavigationMode, BorderLayout.NORTH );
		
		
		// ========================================================================
		// Séléction du rollOver ou non
		// ========================================================================
		JPanel panelRollOver = new JPanel();
		
		rollOver = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_ROLLOVER"), navigation.isRolloverActive() );
		panelRollOver.add( rollOver );
		
		panelGlobal.add( panelRollOver, BorderLayout.CENTER );
		
		// ========================================================================
		// Séléction de la temporisation du clic de la souricom
		// ========================================================================
		JPanel panelTempoSouricom = new JPanel();

		sliderMouseSpeed = new JSlider();
		sliderMouseSpeed.setMaximum( 100 );
		sliderMouseSpeed.setMinimum( 0 );
		sliderMouseSpeed.setMajorTickSpacing(10);
		sliderMouseSpeed.setMinorTickSpacing(10);
		sliderMouseSpeed.setPaintTicks(true);
		sliderMouseSpeed.setPaintLabels(true); 
		panelTempoSouricom.add( sliderMouseSpeed );
		
		
		panelGlobal.add( panelTempoSouricom, BorderLayout.SOUTH );
		
		add( panelGlobal );
	}


	//----------------------------------------------------------- METHODES --//
	
	@Override
	public int validateDataEntry()
	{
		// Si la navigation a changé, on la change dans le profil
		int retour = 0;
		
		if( radioButtonStandard.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.STANDARD )
			{
				navigation.setTypeNavigation( TNavigationType.STANDARD );
				
				retour = 1;
			}
		}else if( radioButtonDefilement.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.DEFILEMENT )
			{
				navigation.setTypeNavigation( TNavigationType.DEFILEMENT );

				navigation.setTemporisationDefilement( sliderTempoDefil.getValue() );
				
				navigation.setBlockSelectionActive( rollOver.isSelected() );
				
				retour = 1;
			}
		}else if( radioButtonClickTempo.isSelected() )
		{
			// si ca a changé
			if( navigation.getTypeNavigation() != TNavigationType.CLICK_TEMPORISE )
			{
				navigation.setTypeNavigation( TNavigationType.CLICK_TEMPORISE );
				navigation.setTemporisationDefilement( sliderTempoDefil.getValue() );
				
				retour = 1;
			}
		}
		
		// sliderMouseSpeed
		if( sliderMouseSpeed.getValue() != navigation.getMouseSpeed() )
		{
			navigation.setMouseSpeed( sliderMouseSpeed.getValue() );
			retour = 1;
		}
		
		// rollOver
		if( rollOver.isSelected() != navigation.isRolloverActive() )
		{
			navigation.setRolloverActive( rollOver.isSelected() );
			retour = 1;
		}
		
		return retour;
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
	}


	private void SwitchDefilementMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( true );
		blocSelection.setEnabled( true );
		
	}


	private void SwitchStandardMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( false );
		blocSelection.setEnabled( false );
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
