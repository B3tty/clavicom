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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import clavicom.CFilePaths;
import clavicom.gui.keyboard.keyboard.UIKeyGroup;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TImageUtils;

public class UILevelManagerFrame extends UITranslucentFrame
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int BT_IMAGE_SIZE = 30;	// Taille des images des boutons
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
	JButton btClassKeyAutomatic;
	JList listUnclassedKeys;
	JScrollPane listScrollUnclassedKeys;
	
	JButton btClose;	
	
	// UIKeyboard lié
	UIKeyboard uiKeyboard;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UILevelManagerFrame()
	{
		// Initialisation des propriétés de la frame
		initFrame();
		
		// Création des objets
		createObjects();
		
		// Création du panel de touches non classées
		createPanelUnclassedKeys();
		
		// Création du panel de touches classées
		createPanelClassedKeys();
		
		// Création du panel de fermeture
		createPanelClose();
		
		// Création du panel global
		createGlobalPanel();
	}
	
	//----------------------------------------------------------- METHODES --//	
	public void setUIKeyboard(UIKeyboard uiKeyboard)
	{
		// Recopie des attributs
		this.uiKeyboard = uiKeyboard;
		
		initializeFrame();
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	//-----------------------------------------------------------------------
	// TRAITEMENTS
	//-----------------------------------------------------------------------
	/**
	 * S'occupe d'initialiser tous les composants, selon les valeurs du
	 * uiKeyboard. Typiquement, cette méthode est appelée à l'ouverture
	 * de la fenêtre
	 */
	protected void initializeFrame()
	{		
		// On flush le contenu des listes
		panelGroups.getListScrollData().removeAll();
		panelLists.getListScrollData().removeAll();
		panelKeys.getListScrollData().removeAll();
		
		// On remplit la liste de groupes
		Object[] keyGroups = uiKeyboard.getKeyGroups().toArray();
		//Arrays.sort(keyGroups,);
		
		for (int i = 0 ; i < keyGroups.length ; ++i)
		{
			System.out.println(((UIKeyGroup)keyGroups[i]).toString());
		}
		
//		panelGroups.getList().getModel()
		Vector<String> T = new Vector<String>();
		T.add("sdflksjfl");
		T.add("sfdsdf");
		
		panelGroups.getList().setListData(T);
		
		//add(panelGroups.getListScrollData());
		
		System.out.println(panelGroups.getList().getModel().getSize());
		
	}
	
	
	//-----------------------------------------------------------------------
	// ATTRIBUTS GRAPHIQUES
	//-----------------------------------------------------------------------	
	protected void initFrame()
	{
		// Invisible
		setVisible(false);
		
		// Taille
		setSize(new Dimension(560,440));
		
		// Redimensionnement
		setResizable(false);
		
		// Titre
		setTitle(UIString.getUIString("FR_LEVEL_EDITOR_TITLE"));
	}
	
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
		btClassKeyAutomatic = new JButton();
		
		listUnclassedKeys = new JList();
		listScrollUnclassedKeys = new JScrollPane(listUnclassedKeys);
		btClose = new JButton();
		
		// Autres variables
		uiKeyboard = null;
	}
	
	protected void createPanelUnclassedKeys()
	{
		// Initialisation des composants
		btClassKey.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorClass()),BT_IMAGE_SIZE,-1));
		btClassKeyAutomatic.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorClassAutomatic()),BT_IMAGE_SIZE,-1));
		
		// Layout
		GridBagLayout gbLayoutPanelUnclassedKey = new GridBagLayout();
		panelUnclassedKeys.setLayout(gbLayoutPanelUnclassedKey);
		
		// Contraintes de la liste
		GridBagConstraints gbConstPanelList = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            2,							// Nombre de lignes occupées
	            95,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelUnclassedKey.setConstraints(listScrollUnclassedKeys, gbConstPanelList);
		
		// Contraintes du bouton de classement
		GridBagConstraints gbConstPanelBtClassKey = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            5,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(SPACE, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelUnclassedKey.setConstraints(btClassKey, gbConstPanelBtClassKey);		
		
		// Contraintes du bouton de classement automatique
		GridBagConstraints gbConstPanelBtClassAutomaticKey = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            5,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, SPACE, SPACE, SPACE), // Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutPanelUnclassedKey.setConstraints(btClassKeyAutomatic, gbConstPanelBtClassAutomaticKey);	
		
		// Ajout des composants au panel
		panelUnclassedKeys.add(listScrollUnclassedKeys);
		panelUnclassedKeys.add(btClassKey);
		panelUnclassedKeys.add(btClassKeyAutomatic);
		
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
		btClose.setAction(new AbstractActionBtClose());
		
		// Mise en place des layouts
		panelClose.setLayout(new BorderLayout());
		
		// Ajout des composants au panel
		panelClose.add(btClose, BorderLayout.EAST);		
	}
	
	protected void createGlobalPanel()
	{
		// Ajout des composants
		panelGlobal.add(panelUnclassedKeys);
		panelGlobal.add(panelClassedKeys);
		panelGlobal.add(panelClose);
		
		add(panelGlobal);
		
		// Mise en place des layouts		
		GridBagLayout gbLayoutPanelGlobal = new GridBagLayout();
		panelGlobal.setLayout(gbLayoutPanelGlobal);
		
		// Contraintes du panel key non classées
		GridBagConstraints gbConstPanelUnclassedKeys = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            20,							// Taille verticale relative
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
	            75,							// Taille verticale relative
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
	            5,							// Taille verticale relative
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
		// TODO : TEMPORAIRE --> vérifier que tout est classé, etc...
		System.exit(0);
	}
	
	protected class AbstractActionBtClose extends AbstractAction
	{
		public AbstractActionBtClose()
		{
			super(UIString.getUIString("FR_LEVEL_EDITOR_BT_OK"));
		}
		public void actionPerformed(ActionEvent arg0)
		{
			onBtClosePressed();
		}
	}
}
