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
import javax.swing.JTextField;
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
	JTextField textTempoDefil;
	JCheckBox blocSelection;
	JTextField textTempoClic;
	JSlider slider;
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
			
			textTempoDefil.setText( String.valueOf( navigation.getTemporisationDefilement() ) );
		} else if( navigation.getTypeNavigation() == TNavigationType.CLICK_TEMPORISE )
		{
			SwitchClicMode();
			radioButtonStandard.setSelected(false);
			radioButtonDefilement.setSelected(false);
			radioButtonClickTempo.setSelected(true);
			
			textTempoClic.setText( String.valueOf( navigation.getTemporisationDefilement() ) );
			blocSelection.setSelected( navigation.isBlockSelectionActive() );
		}
		
		slider.setValue( navigation.getTemporisationClic() );
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
		
		textTempoClic = new JTextField( navigation.getTemporisationDefilement() );
		panelTempoClic.add( textTempoClic );
		
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
		
		textTempoDefil = new JTextField( navigation.getTemporisationDefilement() );
		panelTempoDefil.add( textTempoDefil );
		
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

		slider = new JSlider();
		slider.setMaximum( 100 );
		slider.setMinimum( 0 );
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true); 
		panelTempoSouricom.add( slider );
		
		
		panelGlobal.add( panelTempoSouricom, BorderLayout.SOUTH );
		
		add( panelGlobal );
	}


	//----------------------------------------------------------- METHODES --//
	
	@Override
	public int validateDataEntry()
	{
		// Si la navigation a changé, on la change dans le profil
		
		return 0;
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
		textTempoClic.setEnabled( true );
		
		textTempoDefil.setEnabled( false );
		blocSelection.setEnabled( false );
	}


	private void SwitchDefilementMode()
	{
		textTempoClic.setEnabled( false );
		
		textTempoDefil.setEnabled( true );
		blocSelection.setEnabled( true );
		
	}


	private void SwitchStandardMode()
	{
		textTempoClic.setEnabled( false );
		
		textTempoDefil.setEnabled( false );
		blocSelection.setEnabled( false );
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
