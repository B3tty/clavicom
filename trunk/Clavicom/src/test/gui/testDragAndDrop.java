/*-----------------------------------------------------------------------------+

 Filename			: testDragAndDrop.java
 Creation date		: 5 juin 07
 
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

package test.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class testDragAndDrop extends JFrame implements MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel[] panels = new JPanel[100]; // Copies de l'image

	JPanel p;// notre panel principal qui contient les images

	int nbCopie = 0; // nombre de copies existantes

	public testDragAndDrop()
	{// constructeur qui créé la fenetre principale
		super("titre");
		p = new JPanel(null);
		createCopy(); // on ajoute une copie au panel
		this.getContentPane().add(p);
		this.setSize(800, 600);
		this.setVisible(true);
	}

	public static void main(String[] args)
	{
		new testDragAndDrop();
	}

	public void createCopy()
	{
		// créé une nouvelle copie de l'image
		panels[nbCopie] = new JPanel();
		panels[nbCopie].setBackground(Color.cyan);
		
		// initialise l'image
		panels[nbCopie].setBounds(0, 0, 120, 160); // initialise sa position
													// aux coordonnéees 0,0
		panels[nbCopie].addMouseMotionListener(this);// ajoute un
													// mousemotionlistener pour
													// détecter le drag n drop
		p.add(panels[nbCopie]);// on ajoute la copie au panel
		p.repaint();// on redessine le panel
		nbCopie++; // incrémente le nombre de copies existantes
	}

	public void mouseDragged(MouseEvent e)
	{
		// lorsque qu'on fais du drag n drop sur une copie

		// si la derniere copie créé a été bougée (coordonnées 0,0),
		// on en créé une nouvelle qui sert de modele
		if ( panels[nbCopie - 1].getX() != 0 && panels[nbCopie - 1].getY() != 0 )
			createCopy();

		JPanel temp = (JPanel) e.getSource();
		// créé une référence vers la copie sur laquelle on a cliqué

		temp.setBounds(temp.getX() + e.getX() - 60,
				temp.getY() + e.getY() - 80, 120, 160);
		// déplace la copie sur laquelle je fais du drag n drop
	}
	

	public void mouseMoved(MouseEvent e)
	{
	}
}
