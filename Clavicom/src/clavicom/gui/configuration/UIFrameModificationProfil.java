/*-----------------------------------------------------------------------------+

			Filename			: GlobalProfilModification.java
			Creation date		: 12 juin 07
		
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import clavicom.CFilePaths;
import clavicom.CSettings;
import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.mouse.MouseMoveEngine;
import clavicom.gui.windows.UIKeyboardFrame;
import clavicom.tools.TNavigationType;
import clavicom.tools.TSwingUtils;

public class UIFrameModificationProfil extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//
	// Espaces entre les différents élements de la fenêtre
	protected final int MAIN_PANEL_SPACE_LEFT = 5;
	protected final int MAIN_PANEL_SPACE_RIGHT = 5;
	protected final int MAIN_PANEL_SPACE_BOTTOM = 5;
	protected final int MAIN_PANEL_SPACE_UP = 5;
	
	protected final int BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS = 5;
	protected final int BOTTOM_PANEL_SPACE_UP = 5;
	protected final int BOTTOM_PANEL_SPACE_LEFT = 5;
	//---------------------------------------------------------- VARIABLES --//	
	// Panel des boutons
	JPanel bottomPanel;
	JButton btOk, btCancel, btApply;
	JProgressBar progressBarApply;
	
	JPanel buttonPanel, progressBarPanel;
	
	// Onglets
	JTabbedPane tabbedPane;
	
	
	PanelModificationProfilAdvancedOption panelAdvancedOption;
	PanelModificationProfilDictionaryName panelDictionaryName;
	PanelModificationProfilFont panelFont;
	PanelModificationProfilKeyboardColor panelKeyboardColor;
	PanelModificationProfilNavigation panelNavigation;
	PanelModificationProfilPreferedWords panelPreferedWords;
	PanelModificationProfilSound panelSound;
	PanelModificationProfilTransparency panelTransparency;
	PanelModificationProfilLangueUIName panelLangueUI;
	PanelModificationProfilAbout panelabout;
	
	// reference sur l'iukeyboard pour faire un clear
	UIKeyboard uiKeyboard;
	UIKeyboardFrame uiKeyboardFrame;
	
	UIFrameModificationProfil thisObject; // reference sur lui-même (utilisé dans le thread)
	boolean closeWindows;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIFrameModificationProfil( UIKeyboardFrame myUIKeyboardFrame)
	{
		uiKeyboardFrame = myUIKeyboardFrame;
		
		uiKeyboard = myUIKeyboardFrame.getUIKeyboard();
		thisObject = this;
		
		// Initialisation de la fenêtre
		initFrame();
		
		// Création des objets de bas de page
		createBottom();
		
		// Création des onglets
		createTabs();

		// Création des layouts
		setLayouts();
		
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
	/**
	 * Sauvegarde les données dans le profil en mémoire
	 * retour :
	 * 		true - Ok
	 * 		false - Probleme
	 */
	protected void SaveDataToProfil()
	{
		// sauvegarde des panels un par un
		
		Thread apply = new Thread()
		{
		
			@Override
			public void run()
			{
				super.run();

				boolean retour = true;

				int pourcentToAddToProgressBar = 10;
				
				// panelCommandSetName
				PanelModificationProfilCommandSetName panelCommandSetName = panelAdvancedOption.getPanelCommandSet();
				if ( panelCommandSetName.isChanged() )
				{
					// le CommandSet à changé
					if( JOptionPane.showConfirmDialog(
							thisObject, 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_COMMANDESET") + "\n" +
							UIString.getUIString("LB_CONFPROFIL_CHANGE_CONTINUE"), 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_COMMANDESET_TITLE"), 
							JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
					{
						// On change le commandset si aucune erreur
						String oldCommandSetName = CProfil.getInstance().getCommandSetName().getcommandSetName();
						try {
							panelCommandSetName.validateDataEntry();
							CCommandSet.GetInstance().LoadCommandSetFile(CFilePaths.getCommandSetsFolder() + CProfil.getInstance().getCommandSetName().getcommandSetName());
							uiKeyboard.getCoreKeyboard().flushAllKeysFromClass(CKeyCharacter.class);
						} 
						catch (Exception ex) 
						{
							CProfil.getInstance().getCommandSetName().setcommandSetName(oldCommandSetName);
							CMessageEngine.newError(	UIString.getUIString("EX_COMMANDSET_FILE_ERROR") + CProfil.getInstance().getCommandSetName().getcommandSetName(),
														ex.getMessage());
						}
					}
					else
					{
						retour = false;
					}
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelShortcutSetName
				PanelModificationProfilShortcutSetName panelShortcutSetName = panelAdvancedOption.getPanelShortcutSet();
				if ( panelShortcutSetName.isChanged() )
				{
					// le ShortcutSet à changé
					if( JOptionPane.showConfirmDialog(
							thisObject, 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_SHORTCUTSET") + "\n" +
							UIString.getUIString("LB_CONFPROFIL_CHANGE_CONTINUE"), 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_SHORTCUTSET_TITLE"), 
							JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
					{
						
						// On change le shortcutset si aucune erreur
						String oldShortcutSetName = CProfil.getInstance().getShortcutSetName().getShortCutName();
						try {
							panelShortcutSetName.validateDataEntry();
							CShortcutSet.GetInstance().LoadShortcutSetFile(CFilePaths.getShortcutSetsFolder() + CProfil.getInstance().getShortcutSetName().getShortCutName());
							uiKeyboard.getCoreKeyboard().flushAllKeysFromClass(CKeyShortcut.class);
						} catch (Exception ex) 
						{
							CProfil.getInstance().getShortcutSetName().setShortCutName(oldShortcutSetName);
							CMessageEngine.newError(	UIString.getUIString("EX_SHORTCUTSET_FILE_ERROR") + CProfil.getInstance().getShortcutSetName().getShortCutName(),
														ex.getMessage());
						}
						
					}
					else
					{
						retour = false;
					}
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelAdvancedOption
				if ( panelAdvancedOption.isChanged() )
				{
					panelAdvancedOption.validateDataEntry();
					
					// on prévient l'utilisateur qu'il faudra redémarer 
					JOptionPane.showMessageDialog(
							thisObject, 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_RESTART"),
							UIString.getUIString("LB_CONFPROFIL_CHANGE_RESTART_TITLE"),
						    JOptionPane.WARNING_MESSAGE);
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );

				
				// panelDictionaryName
				if ( panelDictionaryName.isChanged() )
				{
					// le dictionnaire à changé
					// chargement du nouveau dictionnaire
					
					// on met les données dans le noyau
					panelDictionaryName.validateDataEntry();
					
					// on vide le dictionnaire
					CDictionary.dispose();
					
					// on créé le nouveau
					try
					{
						CDictionary.createInstance( CSettings.getDictionnaryName(), CSettings.getPreferedWords() );
					}
					catch (Exception ex)
					{
						CMessageEngine.newFatalError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_DICTIONNARY_1") +
														CSettings.getDictionnaryName().getDictionaryName() +
														UIString.getUIString("MSG_MAIN_CANT_LOAD_DICTIONNARY_2"),
														ex.getMessage());
					}
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelFont l
				if ( panelFont.isChanged() )
				{
					// la police à changé
					panelFont.validateDataEntry();
					
					// on recharge le uikeyboard
					uiKeyboard.redrawAllKeys();
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelKeyboardColor l
				if ( panelKeyboardColor.isChanged() )
				{
					// la couleur du clavier à changé
					panelKeyboardColor.validateDataEntry();
					
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelPreferedWords
				if ( panelPreferedWords.isChanged() )
				{
					// les mots préférés ont changés
					panelPreferedWords.validateDataEntry();
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelSound l
				if ( panelSound.isChanged() )
				{
					// les option de son ont changés
					panelSound.validateDataEntry();
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelTransparency l
				if ( panelTransparency.isChanged() )
				{
					// la transparence à changé
					panelTransparency.validateDataEntry();
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelLangueUI
				if ( panelLangueUI.isChanged() )
				{
					// la langueUI à changé
					panelLangueUI.validateDataEntry();
					
					// on prévient l'utilisateur qu'il faudra redémarer 
					JOptionPane.showMessageDialog(
							thisObject, 
							UIString.getUIString("LB_CONFPROFIL_CHANGE_RESTART"),
							UIString.getUIString("LB_CONFPROFIL_CHANGE_RESTART_TITLE"),
						    JOptionPane.WARNING_MESSAGE);
					
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );
				
				// panelNavigation
				if ( panelNavigation.isChanged() )
				{
					// le type de navigation à changé
					panelNavigation.validateDataEntry();
					
					// si on est en défilement et qu'il a coché moveMouseOnEntered
					if( CSettings.getNavigation().isMoveMouseOnEntered() &&
						CSettings.getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT)
					{
						MouseMoveEngine.getInstance().listen();
					}
					else
					{
						MouseMoveEngine.getInstance().unListen();
					}
				}
				
				progressBarApply.setValue( progressBarApply.getValue() + pourcentToAddToProgressBar );

				
				EndThread( retour );
			}
		};
		
		apply.start();
	}
	

	/**
	 * Initialise la fenêtre
	 */
	protected void initFrame()
	{
		// Titre de la fenêtre
		setTitle(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_TITLE"));		
	}
	
	/**
	 * Créé le panel avec les onglets
	 */
	protected void createTabs()
	{
		tabbedPane = new JTabbedPane();		

		// panel des dictionary name
		JPanel panel = new JPanel(); 
		panelDictionaryName = new PanelModificationProfilDictionaryName( CSettings.getDictionnaryName() );
		panel.add( panelDictionaryName );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_DICTIONARY"), panel);
		
		// panel des fonts
		panel = new JPanel(); 
		panelFont = new PanelModificationProfilFont( CProfil.getInstance().getKeyboardFont() );
		panel.add( panelFont );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT"), panel);
		
		// panel des kayboard color
		panel = new JPanel(); 
		panelKeyboardColor = new PanelModificationProfilKeyboardColor( CProfil.getInstance().getDefaultColor() );
		panel.add( panelKeyboardColor );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_KEYBOARDCOLOR"), panel);
		
		// panel des languesUI
		panel = new JPanel(); 
		panelLangueUI = new PanelModificationProfilLangueUIName( CSettings.getLangueUI() );
		panel.add( panelLangueUI );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_LANGUAGE"), panel);
		
		// panel de navigation
		panel = new JPanel( new BorderLayout() ); 
		panelNavigation = new PanelModificationProfilNavigation( CSettings.getNavigation() );
		panel.add( panelNavigation, BorderLayout.CENTER );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_NAVIGATION"), panel);
		
		// panel des preferedWords
		panel = new JPanel(); 
		panelPreferedWords = new PanelModificationProfilPreferedWords( CSettings.getPreferedWords() );
		panel.add( panelPreferedWords );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_PREFEREDWORDS"), panel);
		
		// panel des sound
		panel = new JPanel(); 
		panelSound = new PanelModificationProfilSound( CSettings.getSound() );
		panel.add( panelSound );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_SOUND"), panel);
		
		// panel de la transparence
		panel = new JPanel(); 
		panelTransparency = new PanelModificationProfilTransparency( CSettings.getTransparency() );
		panel.add( panelTransparency );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_TRANSPARENCY"), panel);
		
		// panel advancedOptions
		panel = new JPanel(); 
		panelAdvancedOption = new PanelModificationProfilAdvancedOption( 
								CProfil.getInstance().getAdvancedOption(), 
								CProfil.getInstance().getCommandSetName(),
								CProfil.getInstance().getShortcutSetName());
		panel.add( panelAdvancedOption );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_ADVANCED_OPTION"), panel);
		
		// panel About
		panel = new JPanel(); 
		panelabout = new PanelModificationProfilAbout(  );
		panel.add( panelabout );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT"), panel);
		
		// Ajout au panel
		add(tabbedPane);
	}
	
	/**
	 * Créé les objets de bas de page (bouttons et progressBar)
	 * 
	 */
	protected void createBottom()
	{
		// Création de panels
		bottomPanel = new JPanel();
		buttonPanel = new JPanel();
		progressBarPanel = new JPanel();
		
		buttonPanel.setLayout(new GridLayout(1,3,BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS,BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS));
		progressBarPanel.setLayout(new GridLayout());
		
		// PROGRESS BAR
		progressBarApply = new JProgressBar();
		progressBarApply.setMaximum( 100 );
		progressBarPanel.add(progressBarApply);
		bottomPanel.add(progressBarPanel);
		
		// TOUCHES
		btOk = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_OK"));
		btCancel = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_CANCEL"));
		btApply = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_APPLY"));
		
		btOk.setAction(new BtOkAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_OK")));
		btCancel.setAction(new BtCancelAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_CANCEL")));
		btApply.setAction(new BtApplyAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_APPLY")));

		
		buttonPanel.add(btOk);
		buttonPanel.add(btCancel);
		buttonPanel.add(btApply);
		
		bottomPanel.add(buttonPanel);
		
		add(bottomPanel);
	}
	
	void EndThread(boolean retour)
	{
		uiKeyboard.recreateKeyboardBackground();
		uiKeyboard.updateKeyFontSize();
		uiKeyboard.repaint();
		
		if (retour)
		{
			if(closeWindows)
				setVisible( false );
		}
		
	}
	
	/**
	 * Applique les layouts
	 */
	protected void setLayouts()
	{
		// -------------- Layout du panel d'onglets ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		setLayout(gbLayoutMain);
		
		// Contraintes du panel avec les onglets
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            90,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(MAIN_PANEL_SPACE_UP, MAIN_PANEL_SPACE_LEFT, 0, MAIN_PANEL_SPACE_RIGHT),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(tabbedPane, gbConstToolbar);
		
		// Contraintes du panel bottom
		GridBagConstraints gbConstBottom = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
											// Espace autours (haut, gauche, bas, droite)
	            new Insets(MAIN_PANEL_SPACE_UP, MAIN_PANEL_SPACE_LEFT, MAIN_PANEL_SPACE_BOTTOM, MAIN_PANEL_SPACE_RIGHT),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(bottomPanel, gbConstBottom);
		
		// -------------- Layout du panel de bas de page ------------------------
		GridBagLayout gbLayoutBottom = new GridBagLayout();
		bottomPanel.setLayout(gbLayoutBottom);
		
		// Contraintes du panel progress bar
		GridBagConstraints gbConstProgressBar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            70,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, 0, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutBottom.setConstraints(progressBarPanel, gbConstProgressBar);
		
		// Contraintes du panel de boutons
		GridBagConstraints gbConstButtons = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            30,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, BOTTOM_PANEL_SPACE_LEFT, 0, 0),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutBottom.setConstraints(buttonPanel, gbConstButtons);
	}

	/**
	 * Appelé à l'appui sur le bouton Ok
	 */
	protected void btOkPressed()
	{
		closeWindows = true;
		SaveDataToProfil();	
	}
	
	/**
	 * Appelé à l'appui sur le bouton Annuler
	 */
	protected void btCancelPressed()
	{
		setVisible( false );
	}

	
	/**
	 * Appelé à l'appui sur le bouton Appliquer
	 */
	protected void btApplyPressed()
	{
		closeWindows = false;
		SaveDataToProfil();
	}
	//---------------------------------------------------- CLASSES PRIVEES --//
	protected class BtApplyAction extends AbstractAction
	{
		public BtApplyAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btApplyPressed();
		}
	}
	
	protected class BtOkAction extends AbstractAction
	{
		public BtOkAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btOkPressed();
		}
	}
	
	protected class BtCancelAction extends AbstractAction
	{
		public BtCancelAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btCancelPressed();
		}
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		if(visible)
		{
			progressBarApply.setValue(0);

			panelAdvancedOption.initValues();
			panelDictionaryName.initValues();
			panelFont.initValues();
			panelKeyboardColor.initValues();
			panelNavigation.initValues();
			panelPreferedWords.initValues();
			panelSound.initValues();
			panelTransparency.initValues();
			panelLangueUI.initValues();
		}
		
		// On centre à l'écran
		TSwingUtils.centerComponentToScreen(this);
		
		// TODO Auto-generated method stub
		super.setVisible(visible);
	}
	
}
