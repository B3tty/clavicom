/*-----------------------------------------------------------------------------+

			Filename			: UILevelList.java
			Creation date		: 25 juin 07
		
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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import clavicom.CFilePaths;
import clavicom.tools.TImageUtils;

public class UILevelList extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int SPACE = 5;			// Espace entre les composants
	private final int BT_SIZE = 20;			// Taille des boutons
	private final int BT_IMAGE_SIZE = BT_SIZE - 5;	// Taille des boutons
	
	//---------------------------------------------------------- VARIABLES --//	
	// Panels
	private JPanel panelGlobal;				// Panel d'encadrement
	private JPanel panelList;				// Panel de liste
	private JPanel panelEdit;				// Panel d'edition
	private JPanel panelScroll;				// Panel de scroll
	
	private JPanel panelScrollSouth;		// Sous panel de scroll
	private JPanel panelScrollNorth;		// Sous panel de scroll
	
	// Composants
	private JList list;					// Liste contenant les données
	private JScrollPane listScroll;		// Liste contenant les données (avec ascenseurs)
	
	private JTextField textElement;			// Textbox pour l'element
	
	private JButton btAddElement;			// Ajout d'un élement
	private JButton btRemoveElement;		// Ajout d'un élement
	private JButton btUp;					// Remonter un element
	private JButton btDown;					// Redescendre un element
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UILevelList()
	{
		// Création des elements
		createObjects();
		
		// Creation du panel List
		createListPanel();
		
		// Création du panel d'edition
		createEditPanel();
		
		// Création du panel de scroll
		createScrollPanel();
		
		// Organisation des panels
		organizePanels();
		
		setLayout(new GridLayout());
	}
	//----------------------------------------------------------- METHODES --//	
	
	//--------------------------------------------------- METHODES PRIVEES --//
	private void organizePanels()
	{
		// Ajout des panels
		panelGlobal.add(panelScroll);
		panelGlobal.add(panelList);
		panelGlobal.add(panelEdit);
		
		add(panelGlobal);
		
		// Mise en place des layouts
		
		GridBagLayout gbLayoutPanelGlobal = new GridBagLayout();
		panelGlobal.setLayout(gbLayoutPanelGlobal);
		
		// Contraintes du panel de scroll
		GridBagConstraints gbConstPanelScroll = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            20,							// Taille horizontale relative
	            95,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, 0, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelScroll, gbConstPanelScroll);

		// Contraintes du panel de liste
		GridBagConstraints gbConstPanelList = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            80,							// Taille horizontale relative
	            95,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, 0, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelList, gbConstPanelList);
		
		// Contraintes du panel d'edit
		GridBagConstraints gbConstPanelEdit = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            80,							// Taille horizontale relative
	            5,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, SPACE, SPACE), 	// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelGlobal.setConstraints(panelEdit, gbConstPanelEdit);
	}
	
	private void createEditPanel()
	{
		// Initialisation des valeurs
		btAddElement.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorAdd()),BT_IMAGE_SIZE,-1));	
		btRemoveElement.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorRemove()),BT_IMAGE_SIZE,-1));
		
		textElement.setText("");

		// Ajout au panel
		panelEdit.add(textElement);
		panelEdit.add(btAddElement);
		panelEdit.add(btRemoveElement);

		
		// Mise en place des layouts
		GridBagLayout gbLayoutPanelEdit = new GridBagLayout();
		panelEdit.setLayout(gbLayoutPanelEdit);
		
		// Contraintes du panel de text
		GridBagConstraints gbConstTextEdit = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            80,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelEdit.setConstraints(textElement, gbConstTextEdit);
		
		// Contraintes du panel d'add
		GridBagConstraints gbConstBtAddElement = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            10,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelEdit.setConstraints(btAddElement, gbConstBtAddElement);
		
		// Contraintes du panel de remove
		GridBagConstraints gbConstBtRemoveElement = new GridBagConstraints (	
				2,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            10,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0), 	// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelEdit.setConstraints(btRemoveElement, gbConstBtRemoveElement);
	}
	
	private void createScrollPanel()
	{
		// Initialisation des valeurs
		btUp.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorUp()),BT_IMAGE_SIZE,-1));	
		btDown.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorDown()),BT_IMAGE_SIZE,-1));

		// Mise en place des layouts
		panelScroll.setLayout(new BorderLayout());
		
		panelScrollNorth.setLayout( new BorderLayout());
		panelScrollSouth.setLayout( new BorderLayout());
		
		// Ajout au panel
		panelScrollNorth.add(btUp, BorderLayout.SOUTH);
		panelScrollSouth.add(btDown, BorderLayout.NORTH);
		
		panelScroll.add(panelScrollNorth);
		panelScroll.add(panelScrollSouth);
		
		// Mise en place des layouts
		GridBagLayout gbLayoutPanelScroll = new GridBagLayout();
		panelScroll.setLayout(gbLayoutPanelScroll);
		
		// Contraintes du north
		GridBagConstraints gbConstPanelNorth = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, SPACE, 0), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelScroll.setConstraints(panelScrollNorth, gbConstPanelNorth);
		
		// Contraintes du south
		GridBagConstraints gbConstPanelSouth = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelScroll.setConstraints(panelScrollSouth, gbConstPanelSouth);
	}
	
	private void createListPanel()
	{
		panelList.setLayout(new BorderLayout());
		panelList.add(listScroll);
	}
	
	private void createObjects()
	{
		panelGlobal = new JPanel();
		panelList = new JPanel();
		panelEdit = new JPanel();
		panelScroll = new JPanel();
		
		panelScrollSouth = new JPanel();
		panelScrollNorth = new JPanel();
		
		textElement = new JTextField();
		
		list = new JList();
		listScroll = new JScrollPane(list);
		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		btAddElement = new JButton();
		btRemoveElement = new JButton();
		btUp = new JButton();
		btDown = new JButton();
		
		btAddElement.setMinimumSize(new Dimension(0,0));
		btRemoveElement.setMinimumSize(new Dimension(0,0));
		btUp.setMinimumSize(new Dimension(0,0));
		btDown.setMinimumSize(new Dimension(0,0));
		
		btAddElement.setPreferredSize(new Dimension(BT_SIZE,BT_SIZE));
		btRemoveElement.setPreferredSize(new Dimension(BT_SIZE,BT_SIZE));
		btUp.setPreferredSize(new Dimension(BT_SIZE,BT_SIZE));
		btDown.setPreferredSize(new Dimension(BT_SIZE,BT_SIZE));
	}

	public JButton getBtAddElement()
	{
		return btAddElement;
	}

	public JButton getBtDown()
	{
		return btDown;
	}

	public JButton getBtRemoveElement()
	{
		return btRemoveElement;
	}

	public JButton getBtUp()
	{
		return btUp;
	}

	public JList getList()
	{
		return list;
	}
	
	public void setListData(Vector<String> test)
	{
		list.setListData(test);
	}

	public JPanel getPanelEdit()
	{
		return panelEdit;
	}

	public JPanel getPanelGlobal()
	{
		return panelGlobal;
	}

	public JPanel getPanelList()
	{
		return panelList;
	}

	public JPanel getPanelScroll()
	{
		return panelScroll;
	}

	public JPanel getPanelScrollNorth()
	{
		return panelScrollNorth;
	}

	public JPanel getPanelScrollSouth()
	{
		return panelScrollSouth;
	}

	public JTextField getTextElement()
	{
		return textElement;
	}

	public JScrollPane getListScrollData()
	{
		return listScroll;
	}
}
