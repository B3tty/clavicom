/*-----------------------------------------------------------------------------+

			Filename			: UIDialogueProfilSelectLoadOptions.java
			Creation date		: 11 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.windows

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

package clavicom.gui.windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import clavicom.gui.language.UIString;

public class UIDialogueProfilSelectLoadOptions extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CProfilSelectLoadOption options;
	
	JCheckBox advancedOptions;
	JCheckBox commandSetName;
	JCheckBox dictionaryName;
	JCheckBox font;
	JCheckBox framePosition;
	JCheckBox keyboard;
	JCheckBox keyboardColors;
	JCheckBox langueUIName;
	JCheckBox navigation;
	JCheckBox preferedWord;
	JCheckBox shortcutSetName;
	JCheckBox sound;
	JCheckBox transparence;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	

	public UIDialogueProfilSelectLoadOptions( CProfilSelectLoadOption myOptions )
	{
		options = myOptions;
		
		setSize( new Dimension (300, 500 ) );
		
		advancedOptions = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_ADVANCED_OPTIONS"), options.isAdvancedOptions() );
		commandSetName = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_COMMANDSET_NAME"), options.isCommandSetName() );
		dictionaryName = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_DICTIONARY_NAME"), options.isDictionaryName() );
		font = 				new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_FONT"), options.isFont() );
		framePosition = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_FRAME_POSITION"), options.isFramePosition() );
		keyboard = 			new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_KEYBOARD"), options.isKeyboard() );
		keyboardColors = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_KEYBOARD_COLORS"), options.isKeyboardColors() );
		langueUIName = 		new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_LANGUE_UI_NAME"), options.isLangueUIName() );
		navigation = 		new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_NAVIGATION"), options.isNavigation() );
		preferedWord = 		new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_PREFERED_WORD"), options.isPreferedWord() );
		shortcutSetName = 	new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_SHORTCUTSET_NAME"), options.isShortcutSetName() );
		sound = 			new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_SOUND"), options.isSound() );
		transparence = 		new JCheckBox( UIString.getUIString("LB_LOAD_PROFIL_TRANSPARENCY"), options.isTransparence() );

		
		JPanel global = (JPanel)getContentPane();
		GridLayout grid = new GridLayout(15,1);
		global.setLayout( grid );
		
		JLabel intro = new JLabel( UIString.getUIString("LB_LOAD_PROFIL_INTRO") );
		
		global.add( intro );
		global.add( keyboard );
		global.add( commandSetName );
		global.add( shortcutSetName );
		global.add( dictionaryName );
		global.add( font );
		global.add( framePosition );
		global.add( keyboardColors );
		global.add( langueUIName );
		global.add( navigation );
		global.add( preferedWord );
		global.add( sound );
		global.add( transparence );
		global.add( advancedOptions );
		
		JButton valider = new JButton( UIString.getUIString("LB_LOAD_PROFIL_VALIDER") );
		valider.addActionListener( new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				// on enregistre tous les booléens
				options.setAdvancedOptions( advancedOptions.isSelected() );
				options.setCommandSetName( commandSetName.isSelected() );
				options.setDictionaryName( dictionaryName.isSelected() );
				options.setFont( font.isSelected() );
				options.setFramePosition( framePosition.isSelected() );
				options.setKeyboard( keyboard.isSelected() );
				options.setKeyboardColors( keyboardColors.isSelected() );
				options.setLangueUIName( langueUIName.isSelected() );
				options.setNavigation( navigation.isSelected() );
				options.setPreferedWord( preferedWord.isSelected() );
				options.setShortcutSetName( shortcutSetName.isSelected() );
				options.setSound( sound.isSelected() );
				options.setTransparence( transparence.isSelected() );
				
				setVisible( false );

			}
			
		});
		JButton annuler = new JButton( UIString.getUIString("LB_LOAD_PROFIL_ANNULER") );
		annuler.addActionListener( new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				options = null;
				setVisible( false );
			}
			
		});
		
		JPanel panelButtons = new JPanel();
		panelButtons.add( valider );
		panelButtons.add( annuler );
		
		global.add( panelButtons );
		
		addConstraint();
	}


	


	//----------------------------------------------------------- METHODES --//

	
	public CProfilSelectLoadOption getOptions()
	{
		return options;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	
	private void addConstraint()
	{
		// ajout des contraintes
		
		commandSetName.addItemListener( new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if( ! commandSetName.isSelected() )
				{
					// on désélectionne le keyboard et le shortcut
					keyboard.setSelected( false );
					shortcutSetName.setSelected( false );
				}
			}
		});
		
		keyboard.addItemListener( new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if( keyboard.isSelected() )
				{
					commandSetName.setSelected( true );
					shortcutSetName.setSelected( true );
				}
			}
		});
		
		shortcutSetName.addItemListener( new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if( ! shortcutSetName.isSelected() )
				{
					keyboard.setSelected( false );
					commandSetName.setSelected( false );
				}
			}
		});
	}
}
