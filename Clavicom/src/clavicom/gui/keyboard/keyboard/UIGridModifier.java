/*-----------------------------------------------------------------------------+

			Filename			: UIGridModifier.java
			Creation date		: 6 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.keyboard

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

package clavicom.gui.keyboard.keyboard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import clavicom.gui.language.UIString;
import clavicom.gui.listener.UIGridChangedListener;

public class UIGridModifier extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int SPACE = 2;
	//---------------------------------------------------------- VARIABLES --//	
	private UIMagnetGrid magnetGrid;	// Composant grille
	private SpinnerNumberModel spinnerModelV;
	private SpinnerNumberModel spinnerModelH;
	
	private JSpinner spinnerV;
	private JSpinner spinnerH;
	
	private EventListenerList listeners;
	JPanel panelMain;
	
	JPanel panelLabelV;
	JPanel panelUseGrid;
	JPanel panelSpinnerV;
	JPanel panelLabelH;
	JPanel panelSpinnerH;
	
	JLabel labelVertical;
	JLabel labelHorizontal;
	
	JCheckBox chkUseGrid;
	
//	private boolean alert;	// Sert a dire si on alerte le noyau en cas de changement de valeur
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIGridModifier(UIMagnetGrid magnetGrid)
	{
		// Recopie des attributs
		this.magnetGrid = magnetGrid;
		
		// Création et initialisation des objets
		createObjects();
		
		// Création des panels
		createPanels();
	}
	
	//----------------------------------------------------------- METHODES --//		
	/**
	 * Met a jour les valeurs du spinner
	 * @param verticalValue
	 * @param horizontalValue
	 */
	public void setValues(int verticalValue, int horizontalValue, boolean used)
	{
		// Mise a jour de valeurs
//		alert = false;
		
		spinnerModelV.setValue(verticalValue);
		spinnerModelH.setValue(horizontalValue);
		chkUseGrid.setSelected(used);
	}
	
	public void addGridChangedListener(UIGridChangedListener listener) 
	{
        listeners.add(UIGridChangedListener.class, listener);
    }
    
    public void removeGridChangedListener(UIGridChangedListener listener) 
    {
        listeners.remove(UIGridChangedListener.class, listener);
    }
    
	//--------------------------------------------------- METHODES PRIVEES --//
	private void createObjects()
	{
		// Création des objets
		listeners = new EventListenerList();
		
		// Valeurs des spinners
		Integer value = new Integer(5); 
		Integer min = new Integer(2);
		Integer max = new Integer(200); 
		Integer step = new Integer(1); 
		
		spinnerModelV = new SpinnerNumberModel(value, min, max, step); 
		spinnerModelH = new SpinnerNumberModel(value, min, max, step);
		
		spinnerV = new JSpinner(spinnerModelV);
		spinnerH = new JSpinner(spinnerModelH);
		
		chkUseGrid = new JCheckBox(UIString.getUIString("LB_GRID_MODIFIER_MAIN"));
		
		chkUseGrid.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent arg0)
			{
				labelHorizontal.setEnabled(chkUseGrid.isSelected());
				labelVertical.setEnabled(chkUseGrid.isSelected());
				spinnerH.setEnabled(chkUseGrid.isSelected());
				spinnerV.setEnabled(chkUseGrid.isSelected());
				
				fireGridUsed(chkUseGrid.isSelected());
			}
			
		});
		 
		spinnerModelV.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{				
				magnetGrid.setVerticalStep(spinnerModelV.getNumber().intValue());
				fireGridChanged();
			} 
		});
		
		spinnerModelH.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{				
				magnetGrid.setHorizontalStep(spinnerModelH.getNumber().intValue());
				fireGridChanged();
			} 
		});
		
		chkUseGrid.setOpaque(false);
	}
	
	protected void createPanels()
	{
		// Création des objets
		panelMain = new JPanel();
		
		panelUseGrid = new JPanel();
		panelLabelV = new JPanel();
		panelSpinnerV = new JPanel();
		panelLabelH = new JPanel();
		panelSpinnerH = new JPanel();
		
		panelUseGrid.setOpaque(false);
		panelLabelV.setOpaque(false);
		panelSpinnerV.setOpaque(false);
		panelLabelH.setOpaque(false);
		panelSpinnerH.setOpaque(false);
		panelMain.setOpaque(false);
		
		labelVertical = new JLabel(UIString.getUIString("LB_GRID_MODIFIER_VERTICAL"));
		labelHorizontal = new JLabel(UIString.getUIString("LB_GRID_MODIFIER_HORIZONTAL"));
		
		// Ajout des panels entre eux
		panelUseGrid.add(chkUseGrid);
		
		panelLabelV.add(labelVertical);
		panelLabelH.add(labelHorizontal);
		
		panelSpinnerV.add(spinnerV);
		panelSpinnerH.add(spinnerH);
		
		panelMain.add(panelUseGrid);
		panelMain.add(panelLabelV);
		panelMain.add(panelSpinnerV);
		panelMain.add(panelLabelH);
		panelMain.add(panelSpinnerH);
		
		add(panelMain);
		
		setLayout(new GridLayout());
		
		panelUseGrid.setLayout(new GridLayout());
		panelLabelV.setLayout(new GridLayout());
		panelLabelH.setLayout(new GridLayout());
		panelSpinnerV.setLayout(new GridLayout());
		panelSpinnerH.setLayout(new GridLayout());
		
		chkUseGrid.setPreferredSize(new Dimension(0,0));
		labelVertical.setPreferredSize(new Dimension(0,0));
		labelHorizontal.setPreferredSize(new Dimension(0,0));

		spinnerH.setPreferredSize(new Dimension(0,0));
		spinnerV.setPreferredSize(new Dimension(0,0));
		
		// Application des layouts
		// -------------- Layout du panel d'onglets ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		panelMain.setLayout(gbLayoutMain);
		
		// Contraintes du panel de grille
		GridBagConstraints gbConstUseGrid = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            2,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            60,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, SPACE, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelUseGrid, gbConstUseGrid);
		
		// Contraintes du panel de label V
		GridBagConstraints gbConstLabelV= new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            20,							// Taille horizontale relative
	            40,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, SPACE, SPACE),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelLabelV, gbConstLabelV);

		// Contraintes du panel de label H
		GridBagConstraints gbConstLabelH= new GridBagConstraints (	
				0,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            20,							// Taille horizontale relative
	            40,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, SPACE, SPACE),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelLabelH, gbConstLabelH);
		
		// Contraintes du panel de spinner V
		GridBagConstraints gbConstSpinnerV= new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            80,							// Taille horizontale relative
	            40,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, SPACE, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelSpinnerV, gbConstSpinnerV);
		
		// Contraintes du panel de spinner H
		GridBagConstraints gbConstSpinnerH= new GridBagConstraints (	
				1,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            80,							// Taille horizontale relative
	            40,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, SPACE, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelSpinnerH, gbConstSpinnerH);
	}
	
	// Listeners sur le changement de grille
    
    protected UIGridChangedListener[] getGridChangedListeners() 
    {
        return listeners.getListeners(UIGridChangedListener.class);
    }
    
    protected void fireGridChanged() 
    {
	    for ( UIGridChangedListener listener : getGridChangedListeners() )
		{
			listener.gridChanged();
		}
    }
    
    protected void fireGridUsed(boolean used) 
    {
	    for ( UIGridChangedListener listener : getGridChangedListeners() )
		{
			listener.gridUsed(used);
		}
    }
}
