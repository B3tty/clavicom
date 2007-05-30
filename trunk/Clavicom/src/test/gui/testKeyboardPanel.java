package test.gui;
/*-----------------------------------------------------------------------------+

			Filename			: testKeyboardPanel.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: test.gui

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

import javax.swing.JFrame;

import clavicom.gui.keyboard.key.UIKeyboardPanel;

public final class testKeyboardPanel extends JFrame {

	private testKeyboardPanel() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().add((new UIKeyboardPanel()));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new testKeyboardPanel().setVisible(true);
    }
}
