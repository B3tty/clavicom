/*-----------------------------------------------------------------------------+

 Filename			: PanelModificationCommandSetName.java
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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import clavicom.core.profil.CAdvancedOptions;
import clavicom.core.profil.CCommandSetName;
import clavicom.core.profil.CShortCutSetName;
import clavicom.gui.language.UIString;
import clavicom.tools.TClickSouricomEnum;

public class PanelModificationProfilAdvancedOption extends
		PanelModificationProfil
{

	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	CAdvancedOptions advancedOption;
	CCommandSetName commandSetName;
	CShortCutSetName shortcutSetName;
	
	JComboBox comboClickSouricom;
	JCheckBox addSpaceAfterString;
	JCheckBox startWithOS;
	JSpinner nbDefilTurn;
	
	PanelModificationProfilCommandSetName panelCommandSet;
	PanelModificationProfilShortcutSetName panelShortcutSet;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfilAdvancedOption(
			CAdvancedOptions myAdvancedOption,
			CCommandSetName myCommandSetName,
			CShortCutSetName myShortcutSetName)
	{
		super(UIString.getUIString("LB_CONFPROFIL_ADVANCED_OPTION"));

		advancedOption = myAdvancedOption;
		commandSetName = myCommandSetName;
		shortcutSetName = myShortcutSetName;

		LoadComponents();
	}

	private void LoadComponents()
	{
		setLayout(new BorderLayout());
		
		// ========================================================
		// construction des composants
		// ========================================================
		TClickSouricomEnum[] clickSouricomArray = 
			{	TClickSouricomEnum.LEFT_RELEASE,
				TClickSouricomEnum.LEFT_PRESS,
				TClickSouricomEnum.RIGHT_RELEASE,
				TClickSouricomEnum.RIGHT_RELEASE
			};
		comboClickSouricom = new JComboBox( clickSouricomArray );
		comboClickSouricom.setSelectedItem( advancedOption.getClickSouricom() );
		
		addSpaceAfterString = new JCheckBox( 
				UIString.getUIString("LB_CONFPROFIL_ADVANCED_OPTION_ADD_SPACE_AFTER_STRING"), 
				advancedOption.isAddSpaceAfterString() );
		
		startWithOS = new JCheckBox(
				UIString.getUIString("LB_CONFPROFIL_ADVANCED_OPTION_START_WITH_OS"), 
				advancedOption.isStartWithOS() );
		
		String[] interval = new String[101];
		for( int i = 0 ; i < 101 ; ++i) 
			interval[i] = String.valueOf( i );
		SpinnerModel model = new SpinnerListModel(interval);
		nbDefilTurn = new JSpinner( model );
		nbDefilTurn.setValue( String.valueOf( advancedOption.getNumberOfDefilTurn() ) );
		nbDefilTurn.setPreferredSize( new Dimension( 40, 30 ) );
		
		panelCommandSet = new PanelModificationProfilCommandSetName( commandSetName );
		panelShortcutSet = new PanelModificationProfilShortcutSetName( shortcutSetName );
		
		// ========================================================
		// placement des composants
		// ========================================================
		
		// création des panels
		JPanel panelClickSouricom = new JPanel( new BorderLayout() );
		panelClickSouricom.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_ADVANCED_CLICK_SOURICOM") ), BorderLayout.WEST );
		panelClickSouricom.add( comboClickSouricom, BorderLayout.CENTER );
		
		JPanel panelNBDefilTurn = new JPanel();
		panelNBDefilTurn.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_ADVANCED_NB_DEFIL_TURN") ), BorderLayout.WEST );
		panelNBDefilTurn.add( nbDefilTurn, BorderLayout.CENTER );
		
		// placement dans le gridbag
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout( gridBagLayout );
		
		// clic souricom
		GridBagConstraints gbConstAOClicSouricom = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(panelClickSouricom, gbConstAOClicSouricom);
		add( panelClickSouricom );
		
		// space after
		GridBagConstraints gbConstAOSpaceAfterString = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(addSpaceAfterString, gbConstAOSpaceAfterString);
		add( addSpaceAfterString );
		
		// start with os
		GridBagConstraints gbConstAOStartWithOS = new GridBagConstraints (	
				0,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(startWithOS, gbConstAOStartWithOS);
		add( startWithOS );
		
		// nb turn
		GridBagConstraints gbConstAONBDefilTurn = new GridBagConstraints (	
				0,							// Numéro de colonne
	            3,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(panelNBDefilTurn, gbConstAONBDefilTurn);
		add( panelNBDefilTurn );
		
		// jeu de commande
		GridBagConstraints gbConstAOCommandeSet = new GridBagConstraints (	
				0,							// Numéro de colonne
	            4,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(panelCommandSet, gbConstAOCommandeSet);
		add( panelCommandSet );
		
		// jeu de raccourcis
		GridBagConstraints gbConstAOShortcutSet= new GridBagConstraints (	
				0,							// Numéro de colonne
	            5,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,							// Taille horizontale relative
	            17,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gridBagLayout.setConstraints(panelShortcutSet, gbConstAOShortcutSet);
		add( panelShortcutSet );

		
	}

	// ----------------------------------------------------------- METHODES --//

	
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
		// si les options avancés on changés
		boolean retour = false;
		
		// clic souricom
		if( comboClickSouricom.getSelectedItem() != advancedOption.getClickSouricom() )
		{
			advancedOption.setClickSouricom( (TClickSouricomEnum)comboClickSouricom.getSelectedItem() );
			retour = true;
		}
		
		// space after string
		if( addSpaceAfterString.isSelected() != advancedOption.isAddSpaceAfterString() )
		{
			advancedOption.setAddSpaceAfterString( addSpaceAfterString.isSelected()  );
			retour = true;			
		}
		
		// start with os
		if( startWithOS.isSelected() != advancedOption.isStartWithOS() )
		{
			advancedOption.setStartWithOS( startWithOS.isSelected()  );
			retour = true;			
		}
		
		// nb defil turn
		if( Integer.parseInt( (String)nbDefilTurn.getValue() ) != advancedOption.getNumberOfDefilTurn() )
		{
			advancedOption.setNumberOfDefilTurn( Integer.parseInt( (String)nbDefilTurn.getValue() )  );
			retour = true;			
		}
		
		

		return retour;
	}

	public PanelModificationProfilCommandSetName getPanelCommandSet()
	{
		return panelCommandSet;
	}

	

	public PanelModificationProfilShortcutSetName getPanelShortcutSet()
	{
		return panelShortcutSet;
	}


	// --------------------------------------------------- METHODES PRIVEES --//
}
