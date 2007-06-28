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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clavicom.CFilePaths;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyGroup;
import clavicom.gui.keyboard.keyboard.UIKeyList;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.tools.TImageUtils;

public class UILevelManagerFrame extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int BT_IMAGE_SIZE = 30;	// Taille des images des boutons
	private final int SPACE = 5;			// Espace entre les composants
	private final String DEFAULT_GROUP_NAME = "AUTO";	// Nom du groupe par défaut
	private final String DEFAULT_LIST_NAME = "AUTO";	// Nom du groupe par défaut
	
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
		
		// On met a jour les selections
		updateEnableState();
	}
	
	//----------------------------------------------------------- METHODES --//	
	public void setUIKeyboard(UIKeyboard uiKeyboard)
	{
		// Recopie des attributs
		this.uiKeyboard = uiKeyboard;
		
		initializeFrame();
	}
	
	@Override
	public void setVisible(boolean arg0)
	{
		// Initialisation des composants
		
		super.setVisible(arg0);
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
		// On met à jour les listes
		updateListGroupsContaint();
		
		// On deselectionne tout dans les listes
		panelGroups.getList().setSelectedIndex(-1);
		listUnclassedKeys.setSelectedIndex(-1);
		
		// On met à jour le grisage s'il n'y a rien
		updateEnableState();
	}
	
	protected void updateListUnclassedKeysContaint()
	{
		// On vide la liste
		listUnclassedKeys.setListData(new Vector<String>());
		
		// On récupère la liste de keys non classées
		Object[] unClassedKeys = uiKeyboard.getUnClassedKey().toArray();
		listUnclassedKeys.setListData(unClassedKeys);
	}
	
	protected void updateListKeysContaint()
	{
		// Récupération du groupe selectionné
		UIKeyList selectedList;
				
		if(panelLists.getList().getSelectedValue() instanceof UIKeyList)
		{
			selectedList = (UIKeyList) panelLists.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// On met à jour la liste de keys, correspondant aux keys de la liste
		// On vide la liste
		panelKeys.getList().setListData(new Vector<String>());
		
		// On récupère les keys de la liste
		Object[] keys = selectedList.getKeys().toArray();		
		panelKeys.getList().setListData(keys);
	}
	
	protected void updateListListsContaint()
	{
		// Récupération du groupe selectionné
		UIKeyGroup selectedGroup;
				
		if(panelGroups.getList().getSelectedValue() instanceof UIKeyGroup)
		{
			selectedGroup = (UIKeyGroup) panelGroups.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// On met à jour la liste listes, correspondant aux listes du groupe
		// On vide la liste
		panelLists.getList().setListData(new Vector<String>());
		
		// On récupère les listes du groupe
		Object[] keyLists = selectedGroup.getKeyLists().toArray();		
		panelLists.getList().setListData(keyLists);
	}
	
	protected void updateListGroupsContaint()
	{
		// On vide la liste
		panelGroups.getList().setListData(new Vector<String>());
		
		// On remplit la liste de groupes
		Object[] keyGroups = uiKeyboard.getKeyGroups().toArray();		
		panelGroups.getList().setListData(keyGroups);
	}
	
	/**
	 * Appelé lorsqu'on selectionne un groupe dans la liste.
	 * Met a jour la liste des listes
	 * @param event
	 */
	protected void onGroupSelected(ListSelectionEvent event)
	{
		// On ne fait rien si la selection est en train de changer
		if(event.getValueIsAdjusting())
		{
			return;
		}
		
		updateListListsContaint();
		
		// On met a jour la selection
		updateEnableState();
	}
	
	/**
	 * Appelé lorsqu'on selectionne une liste dans la liste.
	 * Met a jour la liste des keys
	 * @param event
	 */	
	protected void onListSelected(ListSelectionEvent event)
	{
		// On ne fait rien si la selection est en train de changer
		if(event.getValueIsAdjusting())
		{
			return;
		}
		
		updateListKeysContaint();
		
		// On met a jour la selection
		updateEnableState();
	}

	protected void onKeySelected(ListSelectionEvent event)
	{
		// On selectionne la touche
		UIKeyKeyboard selectedKey;
		
		if(!(panelKeys.getList().getSelectedValue() instanceof UIKeyKeyboard))
		{
			return;
		}
		selectedKey = (UIKeyKeyboard)panelKeys.getList().getSelectedValue();
		
		
		// TODO : déselectionner tout
		// On selectionne la key
		selectedKey.setSelected(true);
		selectedKey.repaint();
		
		// On met a jour la selection
		updateEnableState();
	}
	
	protected void onListTextTyped()
	{
		// On cache le bouton d'ajout s'il n'y a pas de texte
		if(panelLists.getTextElement().getText().length() == 0)
		{
			panelLists.getBtAddElement().setEnabled(false);
		}
		else
		{
			panelLists.getBtAddElement().setEnabled(true);
		}
	}

	protected void onListBtUpClicked()
	{
		// On regarde si on peux descendre le groupe
		if(panelLists.getList().getSelectedIndex() <= 0)
		{
			return;
		}
		
		// Index
		int oldIndex = panelLists.getList().getSelectedIndex();
		int newIndex = oldIndex - 1;
		
		// Récupération du groupe contenant la liste à déplacer
		if(!(panelGroups.getList().getSelectedValue() instanceof UIKeyGroup))
		{
			return;
		}
		UIKeyGroup selectedGroup = (UIKeyGroup)panelGroups.getList().getSelectedValue();
		
		// Déplacement de la liste
		selectedGroup.moveListToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListListsContaint();
		
		// On reselectionne l'item
		panelLists.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onListBtRemoveClicked()
	{
		if(panelLists.getList().getSelectedIndex() == -1)
		{
			return;
		}
		
		// Récupération de la UIList selectionnée
		UIKeyList selectedList;
		
		if(panelLists.getList().getSelectedValue() instanceof UIKeyList)
		{
			selectedList = (UIKeyList) panelLists.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// Déclassement de la keyList
		uiKeyboard.declassList(selectedList);
		
		// On met a jour la liste des listes
		updateListListsContaint();
		
		// On met a jour la liste des keys
		updateListKeysContaint();
		
		// On met a jour la liste des keys non classées
		updateListUnclassedKeysContaint();
		
		// On met a jour l'affichage
		updateEnableState();		
	}

	protected void onListBtDownClicked()
	{
		// On regarde si on peux descendre le groupe
		if(panelLists.getList().getSelectedIndex() >= (panelLists.getList().getModel().getSize()-1))
		{
			return;
		}
		
		// Index
		int oldIndex = panelLists.getList().getSelectedIndex();
		int newIndex = oldIndex + 1;
		
		// Récupération du groupe contenant la liste à déplacer
		if(!(panelGroups.getList().getSelectedValue() instanceof UIKeyGroup))
		{
			return;
		}
		UIKeyGroup selectedGroup = (UIKeyGroup)panelGroups.getList().getSelectedValue();
		
		// Déplacement de la liste
		selectedGroup.moveListToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListListsContaint();
		
		// On reselectionne l'item
		panelLists.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onListBtAddClicked()
	{
		// Création et ajout d'une nouvelle liste
		// portant le nom du texte tapé
		
		// Récupération du groupe selectionné
		UIKeyGroup selectedGroup;
		if( panelGroups.getList().getSelectedValue() instanceof UIKeyGroup)
		{
			selectedGroup = (UIKeyGroup) panelGroups.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		if(uiKeyboard.addUIKeyListToGroup(panelLists.getTextElement().getText(),selectedGroup) == null)
		{
			CMessageEngine.newInfo(	UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_LIST_1") + 
									panelLists.getTextElement().getText() +
									UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_LIST_2"),
									UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_LIST_3"));
			return;
		}
		
		// On revide le texte
		panelLists.getTextElement().setText("");
		
		// On met a jour la liste des listes
		updateListListsContaint();
		
		// On selectionne le groupe qu'on vient d'ajouter
		panelLists.getList().setSelectedIndex(panelLists.getList().getModel().getSize() - 1);
		
		// On met a jour la selection
		updateEnableState();
	}

	protected void onKeyBtUpClicked()
	{
		// On regarde si on peux descendre la key
		if(panelKeys.getList().getSelectedIndex() <= 0)
		{
			return;
		}
		
		// Index
		int oldIndex = panelKeys.getList().getSelectedIndex();
		int newIndex = oldIndex - 1;
		
		// Récupération du groupe contenant la liste à déplacer
		if(!(panelLists.getList().getSelectedValue() instanceof UIKeyList))
		{
			return;
		}
		UIKeyList selectedList = (UIKeyList)panelLists.getList().getSelectedValue();
		
		// Déplacement de la liste
		selectedList.moveKeyToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListKeysContaint();
		
		// On reselectionne l'item
		panelKeys.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onKeyBtRemoveClicked()
	{
		if(panelKeys.getList().getSelectedIndex() == -1)
		{
			return;
		}
		
		// Récupération de la UIKey selectionnée
		UIKeyKeyboard selectedKey;
		
		if(panelKeys.getList().getSelectedValue() instanceof UIKeyKeyboard)
		{
			selectedKey = (UIKeyKeyboard) panelKeys.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// Déclassement de la key
		uiKeyboard.declassKey(selectedKey);
		
		// On met a jour la liste des keys
		updateListKeysContaint();
		
		// On met a jour la liste des keys non classées
		updateListUnclassedKeysContaint();
		
		// On met a jour l'affichage
		updateEnableState();
	}

	protected void onKeyBtDownClicked()
	{
		// On regarde si on peux descendre la key
		if(panelKeys.getList().getSelectedIndex() >= (panelKeys.getList().getModel().getSize()-1))
		{
			return;
		}
		
		// Index
		int oldIndex = panelKeys.getList().getSelectedIndex();
		int newIndex = oldIndex + 1;
		
		// Récupération du groupe contenant la liste à déplacer
		if(!(panelLists.getList().getSelectedValue() instanceof UIKeyList))
		{
			return;
		}
		UIKeyList selectedList = (UIKeyList)panelLists.getList().getSelectedValue();
		
		// Déplacement de la liste
		selectedList.moveKeyToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListKeysContaint();
		
		// On reselectionne l'item
		panelKeys.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onGroupTextTyped()
	{
		// On cache le bouton d'ajout s'il n'y a pas de texte
		if(panelGroups.getTextElement().getText().length() == 0)
		{
			panelGroups.getBtAddElement().setEnabled(false);
		}
		else
		{
			panelGroups.getBtAddElement().setEnabled(true);
		}
	}

	protected void onGroupBtUpClicked()
	{		
		// On regarde si on peux remonter le groupe
		if(panelGroups.getList().getSelectedIndex() <= 0)
		{
			return;
		}
		
		// Index
		int oldIndex = panelGroups.getList().getSelectedIndex();
		int newIndex = oldIndex - 1;
		
		// Déplacement du groupe
		uiKeyboard.moveGroupToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListGroupsContaint();
		
		// On reselectionne l'item
		panelGroups.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onGroupBtRemoveClicked()
	{
		if(panelGroups.getList().getSelectedIndex() == -1)
		{
			return;
		}
		
		// Récupération du UIKeyGroup selectionné
		UIKeyGroup selectedGroup;
		
		if(panelGroups.getList().getSelectedValue() instanceof UIKeyGroup)
		{
			selectedGroup = (UIKeyGroup) panelGroups.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// Déclassement du UIKeyGroup
		uiKeyboard.declassGroup(selectedGroup);
		
		// On met a jour la liste des groupes
		updateListGroupsContaint();
		
		// On met a jour la liste des listes
		updateListListsContaint();
		
		// On met a jour la liste des keys
		updateListKeysContaint();
		
		// On met a jour la liste des keys non classées
		updateListUnclassedKeysContaint();
		
		// On met a jour l'affichage
		updateEnableState();			
	}

	protected void onGroupBtDownClicked()
	{
		// On regarde si on peux descendre le groupe
		if(panelGroups.getList().getSelectedIndex() >= (panelGroups.getList().getModel().getSize()-1))
		{
			return;
		}
		
		// Index
		int oldIndex = panelGroups.getList().getSelectedIndex();
		int newIndex = oldIndex + 1;
		
		// Déplacement du groupe
		uiKeyboard.moveGroupToIndex( oldIndex, newIndex );
		
		// Mise a jour de la liste de groupes
		updateListGroupsContaint();
		
		// On reselectionne l'item
		panelGroups.getList().setSelectedIndex(newIndex);
		
		// Mise a jour des enabled
		updateEnableState();
	}

	protected void onGroupBtAddClicked()
	{
		// Création et ajout d'un nouveau groupe
		// portant le nom du texte tapé
		if(uiKeyboard.addUIKeyGroup(panelGroups.getTextElement().getText()) == null)
		{
			CMessageEngine.newInfo(	UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_GROUP_1") + 
									panelGroups.getTextElement().getText() +
									UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_GROUP_2"),
									UIString.getUIString("MSG_LEVEL_EDITOR_EXISTING_GROUP_3"));
			return;
		}
		
		// On revide le texte
		panelGroups.getTextElement().setText("");
		
		// On met a jour la liste des groupes
		updateListGroupsContaint();
		
		// On selectionne le groupe qu'on vient d'ajouter
		panelGroups.getList().setSelectedIndex(panelGroups.getList().getModel().getSize() - 1);
		
		// On met a jour la selection
		updateEnableState();
	}
	
	protected void onListUnclassedKeySelection(ListSelectionEvent arg0)
	{
		updateEnableState();
	}

	protected void onBtClassKeyPressed()
	{
		// On vérifie qu'on a bien une liste selectionnée
		UIKeyList selectedList;
		if(panelLists.getList().getSelectedValue() instanceof UIKeyList)
		{
			selectedList = (UIKeyList) panelLists.getList().getSelectedValue();
		}
		else
		{
			return;
		}
		
		// Récupération des indices des keys selectionnée
		int[] selectedIndices = listUnclassedKeys.getSelectedIndices();
		
		// Ajout de toutes les touches selectionnées
		for (int i = 0 ; i < selectedIndices.length ; ++i )
		{
			if(uiKeyboard.classKeyToUIList((UIKeyKeyboard)listUnclassedKeys.getModel().getElementAt(selectedIndices[i]), selectedList) == false)
			{
				CMessageEngine.newInfo(	UIString.getUIString("MSG_LEVEL_EDITOR_IMPOSSIBLE_CLASSIFICATION_1") + 
										listUnclassedKeys.getModel().getElementAt(selectedIndices[i]).toString() +
										UIString.getUIString("MSG_LEVEL_EDITOR_IMPOSSIBLE_CLASSIFICATION_2"));
			}
		}
		
		// On met a jour la liste des keys non classées
		updateListUnclassedKeysContaint();
		
		// On met a jour la liste des keys
		updateListKeysContaint();
		
		// On met a jour la selection
		updateEnableState();
	}

	protected void onBtClassKeyAutomaticPressed()
	{
		// On essaye de récupérer le groupe par défault
		UIKeyGroup defaultGroup = uiKeyboard.getGroupByCaption(DEFAULT_GROUP_NAME);

		if(defaultGroup == null)
		{
			defaultGroup = uiKeyboard.addUIKeyGroup(DEFAULT_GROUP_NAME);
		}
		
		// On essaye de récupérer la liste par défault
		UIKeyList defaultList = defaultGroup.getListByCaption(DEFAULT_LIST_NAME);

		if(defaultList == null)
		{
			defaultList = uiKeyboard.addUIKeyListToGroup(DEFAULT_LIST_NAME, defaultGroup);
		}
		
		// On ajoute toutes les touches non classées à la liste par défaut
		while(uiKeyboard.getUnClassedKey().size() != 0)
		{
			uiKeyboard.classKeyToUIList(uiKeyboard.getUnClassedKey().get(0), defaultList);
		}
		
		// On met a jour la liste des groupes
		updateListGroupsContaint();
		
		// On met a jour la liste des listes
		updateListListsContaint();
		
		// On met a jour la liste des keys
		updateListKeysContaint();
		
		// On met a jour la liste des non classées
		updateListUnclassedKeysContaint();
		
		// On selectionne le groupe
		panelGroups.getList().setSelectedValue(defaultGroup, true);
		
		// On selectionne la liste
		panelLists.getList().setSelectedValue(defaultList, true);
		
		// On met a jour la selection
		updateEnableState();
	}
	
	protected void onBtClosePressed()
	{
		// On ferme la fenêtre
		dispose();
	}	
	
	//-----------------------------------------------------------------------
	// ATTRIBUTS GRAPHIQUES
	//-----------------------------------------------------------------------	
	protected void updateEnableState()
	{
		// Liste de touches non classées
		// (on disable s'il n'y a pas de keys non classées)
		if(listUnclassedKeys.getModel().getSize() == 0)
		// Pas de touches
		{
			listUnclassedKeys.setEnabled(false);
			btClassKey.setEnabled(false);
			btClassKeyAutomatic.setEnabled(false);
			panelUnclassedKeys.setEnabled(false);			
			listUnclassedKeys.setSelectedIndex(-1);
		}
		else
		// Il y a des touches
		{
			btClassKeyAutomatic.setEnabled(true);			
			listUnclassedKeys.setEnabled(true);
			panelUnclassedKeys.setEnabled(true);
			
			if(panelLists.getList().getSelectedIndex() != -1)
			// Une liste selectionnée
			{
				// Il y a au moins une key non classée selectionnée
				if(listUnclassedKeys.getSelectedIndices().length != 0)
				{
					btClassKey.setEnabled(true);
				}
				else
				{
					btClassKey.setEnabled(false);
				}
			}
			else
			{
				btClassKey.setEnabled(false);
			}

		}
		
		// Liste de listes (on la grise si pas de groupe selectionnés)
		if(panelGroups.getList().getSelectedIndex() == -1)
		{
			panelLists.setEnabled(false);
			
			panelGroups.getBtDown().setEnabled(false);
			panelGroups.getBtUp().setEnabled(false);
			panelGroups.getBtRemoveElement().setEnabled(false);
		}
		else
		{
			panelLists.setEnabled(true);
			panelGroups.setEnabled(true);
		}
		
		// Liste de keys (on la grise si pas de liste selectionnés)
		if(panelLists.getList().getSelectedIndex() == -1)
		{
			panelKeys.setEnabled(false);
		}
		else
		{
			panelKeys.setEnabled(true);
		}
	}
	
	protected void initFrame()
	{
		// Invisible
		setVisible(false);
		
		// Modale
		setModal(true);
		
		// Taille
		setSize(new Dimension(640,440));
		
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
		panelGroups = new UILevelList(true);
		panelLists = new UILevelList(true);
		panelKeys = new UILevelList(false);
		
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
		
		
		// Ajout des listeners d'action sur les elements
		listUnclassedKeys.addListSelectionListener( new ListSelectionListener()
		 											{public void valueChanged(ListSelectionEvent arg0)
													{onListUnclassedKeySelection(arg0);}});
		
		btClassKey.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent arg0)
										{onBtClassKeyPressed();}});
		
		btClassKeyAutomatic.addActionListener(new ActionListener(){
												public void actionPerformed(ActionEvent arg0)
												{onBtClassKeyAutomaticPressed();}});
	}
	
	protected void createPanelClassedKeys()
	{
		// Ajout des composants au panel
		panelClassedKeys.add(panelGroups);
		panelClassedKeys.add(panelLists);
		panelClassedKeys.add(panelKeys);
		
		// Par défaut tout est deselectionné, sauf le panel de groupes
		panelGroups.setEnabled(true);
		panelLists.setEnabled(false);
		panelClassedKeys.setEnabled(false);
		
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
		
		// Mise en place des listeners de clic sur un élement
		panelGroups.getList().addListSelectionListener( new ListSelectionListener()
														{ public void valueChanged(ListSelectionEvent arg0)
														  {onGroupSelected(arg0);}});
		
		panelLists.getList().addListSelectionListener( new ListSelectionListener()
													   { public void valueChanged(ListSelectionEvent arg0)
													     {onListSelected(arg0);}});
		
		panelKeys.getList().addListSelectionListener( new ListSelectionListener()
													  {	public void valueChanged(ListSelectionEvent arg0)
													  	{onKeySelected(arg0);}});
		
		// Mise en place des listeners de clic sur un bouton up
		panelGroups.getBtUp().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onGroupBtUpClicked();}});
		
		panelLists.getBtUp().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onListBtUpClicked();}});
		
		panelKeys.getBtUp().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onKeyBtUpClicked();}});
		
		// Mise en place des listeners de clic sur un bouton down
		panelGroups.getBtDown().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onGroupBtDownClicked();}});
		
		panelLists.getBtDown().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onListBtDownClicked();}});
		
		panelKeys.getBtDown().addActionListener( new ActionListener()
												 {public void actionPerformed(ActionEvent arg0)
												 {onKeyBtDownClicked();}});
		
		// Mise en place des listeners de clic sur un bouton add
		panelGroups.getBtAddElement().addActionListener( new ActionListener()
														 {public void actionPerformed(ActionEvent arg0)
														 {onGroupBtAddClicked();}});
		
		panelLists.getBtAddElement().addActionListener( new ActionListener()
														 {public void actionPerformed(ActionEvent arg0)
														 {onListBtAddClicked();}});
		
		// Mise en place des listeners de clic sur un bouton remove
		panelGroups.getBtRemoveElement().addActionListener( new ActionListener()
														 {public void actionPerformed(ActionEvent arg0)
														 {onGroupBtRemoveClicked();}});
		
		panelLists.getBtRemoveElement().addActionListener( new ActionListener()
														 {public void actionPerformed(ActionEvent arg0)
														 {onListBtRemoveClicked();}});
		
		panelKeys.getBtRemoveElement().addActionListener( new ActionListener()
														 {public void actionPerformed(ActionEvent arg0)
														 {onKeyBtRemoveClicked();}});
		
		// Mise en place des listeners d'entrée de texte
		panelGroups.getTextElement().addKeyListener( new KeyListener()
													{public void keyPressed(KeyEvent arg0){}
													 public void keyTyped(KeyEvent arg0){}
													 public void keyReleased(KeyEvent arg0)
													 {onGroupTextTyped();}});
		
		panelLists.getTextElement().addKeyListener( new KeyListener()
													{public void keyPressed(KeyEvent arg0){}
													 public void keyTyped(KeyEvent arg0){}
													 public void keyReleased(KeyEvent arg0)
													 {onListTextTyped();}});
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
