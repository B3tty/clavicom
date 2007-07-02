/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionKey.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key.option

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

package clavicom.gui.edition.key;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clavicom.core.keygroup.CKey;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;

public class UIPanelOptionKey extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKey key;
	UIPanelOptionColor panelColorClicked;
	UIPanelOptionColor panelColorEntered;
	UIPanelOptionColor panelColorNormal;
	
	JCheckBox checkHoldable;
	
	List<UIKeyKeyboard> selectedKeys;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIPanelOptionKey( )
	{
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		JPanel panelGlobal = new JPanel( new BorderLayout() );
		JPanel colorPanel = new JPanel();
		
		// création des trois panels des couleurs et ajout
		panelColorClicked = new UIPanelOptionColor();
		panelColorEntered = new UIPanelOptionColor();
		panelColorNormal = new UIPanelOptionColor();
		
		colorPanel.add( panelColorNormal );
		colorPanel.add( panelColorEntered );
		colorPanel.add( panelColorClicked );
		
		panelGlobal.add( colorPanel, BorderLayout.CENTER );
		
		panelGlobal.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
								UIString.getUIString("LB_KEY_BORDER")) );
		
		// création de checkbox holdable
		JPanel panelHoldable = new JPanel();
		checkHoldable = new JCheckBox( UIString.getUIString("LB_KEY_HOLDABLE"), false );
		checkHoldable.addChangeListener( new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if( key != null )
				{
					key.setHoldable( checkHoldable.isSelected() );
				}
			}
		});
		panelHoldable.add( checkHoldable );
		panelGlobal.add( panelHoldable, BorderLayout.SOUTH );

		add( panelGlobal );
	}

	//----------------------------------------------------------- METHODES --//
	public void setValuesKey( List<UIKeyKeyboard> selectedKeys )
	{
		// Affectation de la key
		this.selectedKeys = selectedKeys;	
		
		// Création de la liste de CKey
		List<CKey> selectedCKeys = new ArrayList<CKey>();
		for(UIKeyKeyboard currentKey : selectedKeys)
		{
			selectedCKeys.add(currentKey.getCoreKey());
		}
		
		
		panelColorClicked.setValues( selectedCKeys, TColorKeyEnum.PRESSED );
		panelColorEntered.setValues( selectedCKeys, TColorKeyEnum.ENTERED );
		panelColorNormal.setValues( selectedCKeys, TColorKeyEnum.NORMAL );
		
		checkHoldable.setVisible( false );
		//checkHoldable.setSelected( false );
	}
	
	public void setValuesKey( CKey myKey)
	{
		// Affectation de la key
		key = myKey;		
		
		panelColorClicked.setValues( key, TColorKeyEnum.PRESSED );
		panelColorEntered.setValues( key, TColorKeyEnum.ENTERED );
		panelColorNormal.setValues( key, TColorKeyEnum.NORMAL );
		
		checkHoldable.setVisible( true );
		checkHoldable.setSelected( key.isHoldable() );
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
