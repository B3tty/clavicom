/*-----------------------------------------------------------------------------+

			Filename			: UIImageCombo.java
			Creation date		: 20 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.edition.key

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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class UIImageCombo extends JPanel
{

	

	//--------------------------------------------------------- CONSTANTES --//
	protected final int RENDERED_HEIGHT = 100;
	protected final int SPACE_BETWEEN_IMAGES = 10;
	
	//---------------------------------------------------------- VARIABLES --//	
	protected ImageIcon[] images;

	protected List<String> filenames;
	protected String directory;
	protected JComboBox comboList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIImageCombo(String directory, List<String> filenames)
	{
		super(new BorderLayout());

		// Initialisation des attributs
		this.filenames = filenames;
		this.directory = directory;
		
		// Chargement des images
		images = new ImageIcon[filenames.size()];
		Integer[] intArray = new Integer[filenames.size()];
		for (int i = 0; i < filenames.size(); i++)
		{
			intArray[i] = new Integer(i);
			images[i] = new ImageIcon(directory + filenames.get(i));
			images[i].setImage(resizeImage(images[i].getImage()));
			
			if (images[i] != null)
			{
				images[i].setDescription(filenames.get(i));
			}
		}

		// Création de la combobox
		comboList = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		renderer.setPreferredSize(new Dimension(1, RENDERED_HEIGHT));
		comboList.setRenderer(renderer);
		comboList.setMaximumRowCount(3);

		//Lay out the demo.
		add(comboList, BorderLayout.PAGE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 * Sélectionne l'element du bon filename
	 * @param filename
	 * @return
	 */
	public boolean selectGoodImage(String filename)
	{
		for(int i = 0 ; i < filenames.size() ; ++i)
		{
			if (filenames.get(i).equals(filename))
			{
				comboList.setSelectedIndex(i);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Renvoi le nom de fichier correspondant à l'item selectionné
	 * @return
	 */
	public String getSelectedFilename()
	{
		if(comboList.getSelectedIndex() == -1)
		{
			return null;
		}
		else
		{
			return images[comboList.getSelectedIndex()].getDescription();
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	protected class ComboBoxRenderer extends JLabel implements ListCellRenderer
	{
		
		public ComboBoxRenderer()
		{
			setOpaque(true);
			setHorizontalAlignment(CENTER);
			setVerticalAlignment(CENTER);
		}

		/*
		 * This method finds the image and text corresponding to the selected
		 * value and returns the label, set up to display the text and image.
		 */
		public Component getListCellRendererComponent(	JList list, 
														Object value,
														int index, 
														boolean isSelected, 
														boolean cellHasFocus)
		{
			// Get the selected index. (The index param isn't
			// always valid, so just use the value.)
			int selectedIndex = ((Integer) value).intValue();

			if (isSelected)
			{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}
			else
			{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			// Set the icon and text. If icon was null, say so.
			ImageIcon icon = images[selectedIndex];
			setIcon(icon);

			return this;
		}
	}
	
	protected Image resizeImage (Image originalImage)
	{
		return originalImage.getScaledInstance(-1, RENDERED_HEIGHT - SPACE_BETWEEN_IMAGES, Image.SCALE_SMOOTH);
	}
}
