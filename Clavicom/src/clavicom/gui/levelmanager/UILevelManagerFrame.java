/*-----------------------------------------------------------------------------+

			Filename			: UILevelManagerFrame.java
			Creation date		: 26 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.levelmanager

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

package clavicom.gui.levelmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import clavicom.CFilePaths;
import clavicom.gui.language.UIString;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TImageUtils;

public class UILevelManagerFrame extends UITranslucentFrame
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int BT_IMAGE_SIZE = 25;	// Taille des images des boutons
	private final int SPACE = 5;			// Espace entre les composants
	
	//---------------------------------------------------------- VARIABLES --//	
	// Panels principaux
	JPanel panelGlobal;
	JPanel panelUnclassedKeys;
	JPanel panelClassedKeys;
	JPanel panelClose;
	
	// Panels secondaires
	UILevelList panelGroups;
	UILevelList panelLists;
	UILevelList panelKeys;
	
	// Composants
	JButton btClassKey;
	JList listUnclassedKeys;
	JButton btClose;
	
	// TODO : ajouter --> JButton btClassAutomaticKey;
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UILevelManagerFrame()
	{
		// Création des objets
		createObjects();
		
		// Création du panel de touches non classées
		createPanelUnclassedKeys();
		
		// Création du panel de touches classées
		createPanelClassedKeys();
		
		// Création du panel de fermeture
		createPanelClose();
		
		// Création du panel global
		createGLobalPanel();
	
		add(panelGlobal);
		
		panelGlobal.setBackground(Color.GREEN);
	}
	
	//----------------------------------------------------------- METHODES --//	
	protected void createObjects()
	{
		// Panels principaux
		panelGlobal = new JPanel();
		panelUnclassedKeys = new JPanel();
		panelClassedKeys = new JPanel();
		panelClose = new JPanel();
		
		// Panels secondaires
		panelGroups = new UILevelList();
		panelLists = new UILevelList();
		panelKeys = new UILevelList();
		
		// Composants
		btClassKey = new JButton();
		listUnclassedKeys = new JList();
		btClose = new JButton();
	}
	
	protected void createPanelUnclassedKeys()
	{
		// Image du bouton
		btClassKey.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorAdd()),BT_IMAGE_SIZE,-1));
		
		// Ajout des composants au panel
		panelUnclassedKeys.add(listUnclassedKeys);
		panelUnclassedKeys.add(btClassKey);
		
		// Ajout de la bordure
		panelUnclassedKeys.setBorder( 	BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
										UIString.getUIString("FR_LEVEL_EDITOR_UNCLASSED_BORDER")) );
	}
	
	protected void createPanelClassedKeys()
	{
		// Ajout des composants au panel
		panelClassedKeys.add(panelGroups);
		panelClassedKeys.add(panelLists);
		panelClassedKeys.add(panelKeys);
		
		// Ajout des bordures
		panelGroups.setBorder( 	BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("FR_LEVEL_EDITOR_CLASSED_GROUPS_BORDER")) );
		
		panelLists.setBorder( 	BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("FR_LEVEL_EDITOR_CLASSED_LISTS_BORDER")) );
		
		panelKeys.setBorder( 	BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("FR_LEVEL_EDITOR_CLASSED_KEYS_BORDER")) );
		
		panelClassedKeys.setBorder( 	BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("FR_LEVEL_EDITOR_CLASSED_BORDER")) );
		
		// Mise en place des layouts
		GridBagLayout gbLayoutPanelClassedKey = new GridBagLayout();
		panelClassedKeys.setLayout(gbLayoutPanelClassedKey);
		
		// Contraintes du panel de groupes
		GridBagConstraints gbConstPanelGroups = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelClassedKey.setConstraints(panelGroups, gbConstPanelGroups);
		
		// Contraintes du panel de groupes
		GridBagConstraints gbConstPanelLists = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelClassedKey.setConstraints(panelLists, gbConstPanelLists);
		
		// Contraintes du panel de groupes
		GridBagConstraints gbConstPanelKeys = new GridBagConstraints (	
				2,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            33,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelClassedKey.setConstraints(panelKeys, gbConstPanelKeys);


	}
	
	protected void createPanelClose()
	{
		// Affectation de l'action du bouton
		btClose.setAction(new AbstractAction()
							{
								public void actionPerformed(ActionEvent arg0)
								{
									onBtClosePressed();
								}
							});
		// Mise en place des layouts
		panelClose.setLayout(new BorderLayout());
		
		// Ajout des composants au panel
		panelClose.add(btClose, BorderLayout.EAST);		
	}
	
	protected void createGLobalPanel()
	{
		// Ajout des composants
		panelGlobal.add(panelUnclassedKeys);
		panelGlobal.add(panelClassedKeys);
		panelGlobal.add(panelClose);
		
		add(panelGlobal, BorderLayout.CENTER);
		
		// Mise en place des layouts
		setLayout(new BorderLayout());
		
		GridBagLayout gbLayoutPanelGlobal = new GridBagLayout();
		panelGlobal.setLayout(gbLayoutPanelGlobal);
		
		// Contraintes du panel key non classées
		GridBagConstraints gbConstPanelUnclassedKeys = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            30,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelUnclassedKeys, gbConstPanelUnclassedKeys);
		
		// Contraintes du panel key classées
		GridBagConstraints gbConstPanelClassedKeys = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            60,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelClassedKeys, gbConstPanelClassedKeys);

		// Contraintes du panel fermeture
		GridBagConstraints gbConstPanelClose = new GridBagConstraints (	
				0,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelClose, gbConstPanelClose);
	}
	
	protected void onBtClosePressed()
	{
		// TODO : vérifier que tout est classé, etc...
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
