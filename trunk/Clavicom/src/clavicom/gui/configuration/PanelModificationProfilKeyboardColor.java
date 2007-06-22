/*-----------------------------------------------------------------------------+

 Filename			: PanelModificationKeyboardColor.java
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import clavicom.core.profil.CKeyboardColor;
import clavicom.gui.language.UIString;

public class PanelModificationProfilKeyboardColor extends
		PanelModificationProfil
{

	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	CKeyboardColor keyboardColor;

	JButton buttonBackColor;

	JButton buttonNormal;

	JButton buttonEnteredColor;

	JButton buttonClickedColor;

	// ------------------------------------------------------ CONSTRUCTEURS --//

	public PanelModificationProfilKeyboardColor(CKeyboardColor myKeyboardColor)
	{
		super(UIString.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR"));

		keyboardColor = myKeyboardColor;

		LoadComponents();
	}

	private void LoadComponents()
	{
		setLayout(new BorderLayout());
		
		SpringLayout springLayout = new SpringLayout();
		JPanel panel = new JPanel( springLayout );
		
		

		buttonBackColor = new JButton();
		buttonBackColor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getBackColor().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getBackColor().getColor())
					{

						buttonBackColor.setBackground(newColor);
					}
				}
			}
		});

		DisplayColor(
				buttonBackColor,
				keyboardColor.getBackColor().getColor(),
				UIString
						.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_BACKCOLOR"),
				panel);

		buttonNormal = new JButton();
		buttonNormal.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyNormal().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyNormal()
							.getColor())
					{

						buttonNormal.setBackground(newColor);
					}
				}
			}
		});
		DisplayColor(buttonNormal, keyboardColor.getDefaultKeyNormal()
				.getColor(), UIString
				.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_NORMALCOLOR"),
				panel);

		buttonEnteredColor = new JButton();
		buttonEnteredColor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyEntered().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyEntered()
							.getColor())
					{

						buttonEnteredColor.setBackground(newColor);
					}
				}
			}
		});
		DisplayColor(
				buttonEnteredColor,
				keyboardColor.getDefaultKeyEntered().getColor(),
				UIString
						.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_ENTEREDCOLOR"),
				panel);

		buttonClickedColor = new JButton();
		buttonClickedColor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyClicked().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyClicked()
							.getColor())
					{

						buttonClickedColor.setBackground(newColor);
					}
				}
			}
		});
		DisplayColor(
				buttonClickedColor,
				keyboardColor.getDefaultKeyClicked().getColor(),
				UIString
						.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_PRESSEDCOLOR"),
				panel);
		
		
		
		makeCompactGrid(panel,
                4, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
		
		

		JPanel panelGlobal = new JPanel( );
		panelGlobal.add( panel );
		add(panelGlobal, BorderLayout.CENTER);
	}

	// ----------------------------------------------------------- METHODES --//

	@Override
	public boolean validateDataEntry()
	{
		return change( true );
	}
	
	@Override
	public boolean isChanged()
	{
		return change( false );
	}

	// --------------------------------------------------- METHODES PRIVEES --//

	protected void DisplayColor(JButton buttonColor, Color defautColor,
			String typeColor, 
			JPanel panel3)
	{
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel(typeColor));
		panel3.add(panel1);

		buttonColor.setBackground(defautColor);
		buttonColor.setPreferredSize(new Dimension(30, 20));
		JPanel panel2 = new JPanel();
		panel2.add(buttonColor);

		panel3.add(panel2);
	}
	
	
	
	/**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component in a column is as wide as the maximum
     * preferred width of the components in that column;
     * height is similarly determined for each row.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    protected static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width,
                                   getConstraintsForCell(r, c, parent, cols).
                                       getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height,
                                    getConstraintsForCell(r, c, parent, cols).
                                        getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        //Set the parent's size.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
    
    /* Used by makeCompactGrid. */
    protected static SpringLayout.Constraints getConstraintsForCell(
                                                int row, int col,
                                                Container parent,
                                                int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

	
	
	protected boolean change( boolean saveData )
	{
		// Si la keyboardColor a changé, on la met dans le profil
		boolean retour = false;

		if (buttonBackColor.getBackground() != keyboardColor.getBackColor()
				.getColor())
		{
			if ( saveData )
			{
				keyboardColor.getBackColor().setColor(
					buttonBackColor.getBackground());
			}
			
			retour = true;
		}
		if (buttonClickedColor.getBackground() != keyboardColor
				.getDefaultKeyClicked().getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyClicked().setColor(
						buttonClickedColor.getBackground());
			}
			
			retour = true;
		}
		if (buttonEnteredColor.getBackground() != keyboardColor
				.getDefaultKeyEntered().getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyEntered().setColor(
						buttonEnteredColor.getBackground());
			}
			
			retour = true;
		}
		if (buttonNormal.getBackground() != keyboardColor.getDefaultKeyNormal()
				.getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyNormal().setColor(
						buttonNormal.getBackground());
			}
			
			retour = true;
		}

		return retour;
	}
}
