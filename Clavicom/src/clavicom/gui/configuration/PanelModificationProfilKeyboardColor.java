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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import clavicom.core.profil.CKeyboardColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorPanel;

public class PanelModificationProfilKeyboardColor extends
		PanelModificationProfil
{

	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	CKeyboardColor keyboardColor;

	TColorPanel panelBackColor;

	TColorPanel panelNormal;

	TColorPanel panelEnteredColor;

	TColorPanel panelClickedColor;

	// ------------------------------------------------------ CONSTRUCTEURS --//

	public PanelModificationProfilKeyboardColor(CKeyboardColor myKeyboardColor)
	{
		super(UIString.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR"));

		keyboardColor = myKeyboardColor;

		LoadComponents();
		
		initValues();
	}

	private void LoadComponents()
	{
		setLayout(new BorderLayout());
		
		SpringLayout springLayout = new SpringLayout();
		JPanel panel = new JPanel( springLayout );
		
		
		

		panelBackColor = new TColorPanel();
		panelBackColor.addMouseListener( new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getBackColor().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getBackColor().getColor())
					{

						panelBackColor.setBackground(newColor);
					}
				}
			}
		});

		DisplayColor(
				panelBackColor,
				UIString
						.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_BACKCOLOR"),
				panel);

		panelNormal = new TColorPanel();
		panelNormal.addMouseListener( new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyNormal().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyNormal()
							.getColor())
					{

						panelNormal.setBackground(newColor);
					}
				}
			}
		});
		
		DisplayColor(panelNormal, UIString
				.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_NORMALCOLOR"),
				panel);

		panelEnteredColor = new TColorPanel();
		panelEnteredColor.addMouseListener( new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyEntered().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyEntered()
							.getColor())
					{

						panelEnteredColor.setBackground(newColor);
					}
				}
			}

		});
		DisplayColor(
				panelEnteredColor,
				UIString
						.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR_ENTEREDCOLOR"),
				panel);

		panelClickedColor = new TColorPanel();
		panelClickedColor.addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent e)
			{
				Color newColor = JColorChooser.showDialog(null, UIString
						.getUIString("LB_CHOOSE_COLOR"), keyboardColor
						.getDefaultKeyClicked().getColor());

				if (newColor != null)
				{
					if (newColor != keyboardColor.getDefaultKeyClicked()
							.getColor())
					{

						panelClickedColor.setBackground(newColor);
					}
				}
			}

			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}

		});
		DisplayColor(
				panelClickedColor,
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
	
	public void initValues()
	{
		
		panelBackColor.setBackground( 		keyboardColor.getBackColor().getColor() );
		panelNormal.setBackground( 			keyboardColor.getDefaultKeyNormal().getColor() );
		panelEnteredColor.setBackground( 	keyboardColor.getDefaultKeyEntered().getColor() );
		panelClickedColor.setBackground( 	keyboardColor.getDefaultKeyClicked().getColor() );
	}

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

	protected void DisplayColor(JPanel panelColor,
			String typeColor, 
			JPanel panel3)
	{
		JPanel panel1 = new JPanel();
		panel1.add(new JLabel(typeColor));
		panel3.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.add(panelColor);

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

		if (panelBackColor.getBackground() != keyboardColor.getBackColor()
				.getColor())
		{
			if ( saveData )
			{
				keyboardColor.getBackColor().setColor(
						panelBackColor.getBackground());
			}
			
			retour = true;
		}
		if (panelClickedColor.getBackground() != keyboardColor
				.getDefaultKeyClicked().getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyClicked().setColor(
						panelClickedColor.getBackground());
			}
			
			retour = true;
		}
		if (panelEnteredColor.getBackground() != keyboardColor
				.getDefaultKeyEntered().getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyEntered().setColor(
						panelEnteredColor.getBackground());
			}
			
			retour = true;
		}
		if (panelNormal.getBackground() != keyboardColor.getDefaultKeyNormal()
				.getColor())
		{
			if ( saveData )
			{
				keyboardColor.getDefaultKeyNormal().setColor(
						panelNormal.getBackground());
			}
			
			retour = true;
		}

		return retour;
	}
}
