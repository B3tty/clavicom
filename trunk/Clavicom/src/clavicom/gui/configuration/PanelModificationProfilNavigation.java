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
	JLabel labelTempoClicOrTempoDefil;
	JPanel panelTempoDefilClic;
	JTextField textTempoClicOrDefil;
	JCheckBox blocSelection;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilNavigation(CNavigation myNavigation)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION") );
		
		navigation = myNavigation;
		
		LoadComponents();
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
		radioButtonClickTempo = new JRadioButton( TNavigationType.CLICK_TEMPORISE.toString(), false );
		buttonGroup.add( radioButtonClickTempo );
		radioButtonClickTempo.addActionListener(this);
		
		
		// navigation défilement
		JPanel defilement = new JPanel( new BorderLayout() );
		radioButtonDefilement = new JRadioButton( TNavigationType.DEFILEMENT.toString(), false );
		buttonGroup.add( radioButtonDefilement );
		radioButtonDefilement.addActionListener(this);
		defilement.add( radioButtonDefilement, BorderLayout.NORTH );
		
		// checkBox BlocSelection
		blocSelection = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_ROLLOVER"), navigation.isRolloverActive() );
		defilement.add( blocSelection, BorderLayout.CENTER );

		
		panelNavigationMode.add( radioButtonStandard );
		panelNavigationMode.add( radioButtonClickTempo );
		panelNavigationMode.add( defilement );
		
		panelGlobal.add( panelNavigationMode, BorderLayout.NORTH );
		
		JPanel panelCenter = new JPanel( new BorderLayout() );
		// ========================================================================
		// Séléction de la temporisation du défilement ou du clic temporisé
		// ========================================================================
		panelTempoDefilClic = new JPanel();
		
		// on initialise avec la temporisation de défilement  
		labelTempoClicOrTempoDefil = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_DEFILEMENT_TEMPORISATION") );
		panelTempoDefilClic.add( labelTempoClicOrTempoDefil );
		
		textTempoClicOrDefil = new JTextField( navigation.getTemporisationDefilement() );
		panelTempoDefilClic.add( textTempoClicOrDefil );
		
		panelCenter.add( panelTempoDefilClic, BorderLayout.SOUTH );
		
		// ========================================================================
		// Séléction du rollOver ou non
		// ========================================================================
		JPanel panelRollOver = new JPanel();
		
		JCheckBox rollOver = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION_ROLLOVER"), navigation.isRolloverActive() );
		panelRollOver.add( rollOver );
		
		panelCenter.add( panelRollOver, BorderLayout.CENTER );
		
		panelGlobal.add( panelCenter, BorderLayout.CENTER );
		
		// ========================================================================
		// Séléction de la temporisation du clic de la souricom
		// ========================================================================
		JPanel panelTempoSouricom = new JPanel();

		JSlider slider = new JSlider();
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
		
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
