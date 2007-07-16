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
import java.util.Hashtable;

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
	final int TEMPO_CLIC_MINIMUM = 500;
	final int TEMPO_CLIC_MAXIMUM = 3000;
	
	final int TEMPO_DEFIL_MINIMUM = 500;
	final int TEMPO_DEFIL_MAXIMUM = 5000;
	
	final int MOUSE_SPEED_CLIC_MINIMUM = 0;
	final int MOUSE_SPEED_CLIC_MAXIMUM = 100;
	//---------------------------------------------------------- VARIABLES --//
	CNavigation navigation;
	JRadioButton radioButtonStandard;
	JRadioButton radioButtonDefilement;
	JRadioButton radioButtonClickTempo;
	JSlider sliderTempoDefil;
	JSlider sliderTempoClic;
	JSlider sliderMouseSpeed;
	JCheckBox mouseMoveOnEntered;
	JLabel labelTempoDefil;
	JLabel labelTempoClic;
	JLabel labelTempoSouriom;
	
	Hashtable<Integer,JLabel> sliderTempoDefilLabel;
	Hashtable<Integer,JLabel> sliderTempoClicLabel;
	Hashtable<Integer,JLabel> sliderMouseSpeedLabel;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilNavigation(CNavigation myNavigation)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION") );
		
		navigation = myNavigation;
		
		LoadComponents();
		
		initValues();
	}

	
	public void initValues()
	{
		if( navigation.getTypeNavigation() == TNavigationType.STANDARD )
		{
			SwitchStandardMode();
			radioButtonStandard.setSelected(true);
			radioButtonDefilement.setSelected(false);
			radioButtonClickTempo.setSelected(false);
			
		} 
		else if( navigation.getTypeNavigation() == TNavigationType.DEFILEMENT )
		{
			SwitchDefilementMode();
			radioButtonStandard.setSelected(false);
			radioButtonDefilement.setSelected(true);
			radioButtonClickTempo.setSelected(false);
			
		} 
		else if( navigation.getTypeNavigation() == TNavigationType.CLICK_TEMPORISE )
		{
			SwitchClicMode();
			radioButtonStandard.setSelected(false);
			radioButtonDefilement.setSelected(false);
			radioButtonClickTempo.setSelected(true);
		}
		
		sliderTempoDefilLabel = new Hashtable<Integer,JLabel>();
		sliderTempoDefilLabel.put( new Integer(TEMPO_DEFIL_MINIMUM), new JLabel(UIString.getUIString("LB_CONFIG_TEMPO_DEFIL_FAST")));
		sliderTempoDefilLabel.put( new Integer(TEMPO_DEFIL_MAXIMUM), new JLabel(UIString.getUIString("LB_CONFIG_TEMPO_DEFIL_SLOW")));
		sliderTempoDefil.setLabelTable(sliderTempoDefilLabel);
		
		sliderTempoClicLabel = new Hashtable<Integer,JLabel>();
		sliderTempoClicLabel.put( new Integer(TEMPO_CLIC_MINIMUM), new JLabel(UIString.getUIString("LB_CONFIG_TEMPO_CLIC_FAST")));
		sliderTempoClicLabel.put( new Integer(TEMPO_CLIC_MAXIMUM), new JLabel(UIString.getUIString("LB_CONFIG_TEMPO_CLIC_SLOW")));
		sliderTempoClic.setLabelTable(sliderTempoClicLabel);
		
		sliderMouseSpeedLabel = new Hashtable<Integer,JLabel>();
		sliderMouseSpeedLabel.put( new Integer(MOUSE_SPEED_CLIC_MINIMUM), new JLabel(UIString.getUIString("LB_CONFIG_MOUSE_SPEED_SLOW")));
		sliderMouseSpeedLabel.put( new Integer(MOUSE_SPEED_CLIC_MAXIMUM), new JLabel(UIString.getUIString("LB_CONFIG_MOUSE_SPEED_FAST")));
		sliderMouseSpeed.setLabelTable(sliderMouseSpeedLabel);
		
		sliderMouseSpeed.setValue( navigation.getMouseSpeed() );
		sliderTempoClic.setValue( navigation.getTemporisationClic() );
		sliderTempoDefil.setValue( navigation.getTemporisationDefilement() );
		
		// On inverse les deux tempo pour afficher les 3 dans le même sens
		sliderTempoClic.setInverted(true);
		sliderTempoDefil.setInverted(true);
		
		mouseMoveOnEntered.setSelected( navigation.isMoveMouseOnEntered() ); 
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
		labelTempoClic = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_CLICK_TEMPORISATION") + " : " );
		panelGlobal.add( labelTempoClic );
		

		
		sliderTempoClic = new JSlider();
		sliderTempoClic.setMaximum( TEMPO_CLIC_MAXIMUM );
		sliderTempoClic.setMinimum( TEMPO_CLIC_MINIMUM );
		sliderTempoClic.setMajorTickSpacing(500);
		sliderTempoClic.setMinorTickSpacing(500);
		sliderTempoClic.setPaintTicks(true);
		sliderTempoClic.setPaintLabels(true);
		
		
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
		labelTempoDefil = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_DEFILEMENT_TEMPORISATION") + " : " );
		panelGlobal.add( labelTempoDefil );
		
		
		sliderTempoDefil = new JSlider();
		sliderTempoDefil.setMaximum( TEMPO_DEFIL_MAXIMUM );
		sliderTempoDefil.setMinimum( TEMPO_DEFIL_MINIMUM );
		sliderTempoDefil.setMajorTickSpacing(500);
		sliderTempoDefil.setMinorTickSpacing(500);
		sliderTempoDefil.setPaintTicks(true);
		sliderTempoDefil.setPaintLabels(true);
		
		
		panelGlobal.add( sliderTempoDefil );
		
		// ========================================================================
		// Séléction du mouseMoveOnEntered ou non
		// ========================================================================
		mouseMoveOnEntered = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_MOUSE_MOVE_ON_ENTERED"), navigation.isMoveMouseOnEntered() );
		panelGlobal.add( mouseMoveOnEntered );

		// ========================================================================
		// Séléction de la temporisation du clic de la souricom
		// ========================================================================
		
		labelTempoSouriom = new JLabel( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION_MOUSE_SPEED") + " : " );
		panelGlobal.add( labelTempoSouriom );

		sliderMouseSpeed = new JSlider();
		sliderMouseSpeed.setMaximum( MOUSE_SPEED_CLIC_MAXIMUM );
		sliderMouseSpeed.setMinimum( MOUSE_SPEED_CLIC_MINIMUM );
		sliderMouseSpeed.setMajorTickSpacing(10);
		sliderMouseSpeed.setMinorTickSpacing(10);
		sliderMouseSpeed.setPaintTicks(true);
		sliderMouseSpeed.setPaintLabels(true);
		sliderMouseSpeed.setValue( navigation.getMouseSpeed() );
		
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
		
		mouseMoveOnEntered.setLocation( 60, 250 );
		
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
		
		mouseMoveOnEntered.setSize( 500, 30 );
		
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
		
		labelTempoClic.setEnabled( true );
		labelTempoDefil.setEnabled( false );
		
		mouseMoveOnEntered.setEnabled( false );
	}


	private void SwitchDefilementMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( true );
		
		labelTempoClic.setEnabled( false );
		labelTempoDefil.setEnabled( true );
		
		mouseMoveOnEntered.setEnabled( true );
		
	}


	private void SwitchStandardMode()
	{
		sliderTempoClic.setEnabled( false );
		
		sliderTempoDefil.setEnabled( false );
		
		labelTempoClic.setEnabled( false );
		labelTempoDefil.setEnabled( false );
		
		mouseMoveOnEntered.setEnabled( false );
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
				{
					navigation.setTypeNavigation( TNavigationType.STANDARD );
				}
				
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
				}
				
				retour = true;
			}
		}
		
		// on sauvegarde ces valeurs dans tous les cas
		navigation.setTemporisationDefilement( sliderTempoDefil.getValue() );
		navigation.setTemporisationClic( sliderTempoClic.getValue() );
		
		// sliderMouseSpeed
		if( sliderMouseSpeed.getValue() != navigation.getMouseSpeed() )
		{
			if ( saveData )
				navigation.setMouseSpeed( sliderMouseSpeed.getValue() );
			
			retour = true;
		}
		
		// mouseMoveOnEntered
		if( mouseMoveOnEntered.isSelected() != navigation.isMoveMouseOnEntered() )
		{
			if ( saveData )
				navigation.setMoveMouseOnEntered( mouseMoveOnEntered.isSelected() );
			
			retour = true;
		}
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
