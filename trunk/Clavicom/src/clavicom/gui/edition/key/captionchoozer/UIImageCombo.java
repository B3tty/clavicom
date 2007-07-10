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

package clavicom.gui.edition.key.captionchoozer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import clavicom.tools.TSwingUtils;

public class UIImageCombo extends JPanel
{

	

	//--------------------------------------------------------- CONSTANTES --//
	protected final int RENDERED_HEIGHT = 50;
	protected final int RENDERED_WIDTH = 50;
	
	protected final int SPACE_BETWEEN_IMAGES = 5;
	
	//---------------------------------------------------------- VARIABLES --//	
	protected ImageIcon[] images;

	protected List<String> filenames;
	protected String directory;
	protected JComboBox comboList;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIImageCombo(String directory, List<String> filenames)
	{
		super();
		
		setLayout(new BorderLayout());

		// Initialisation des attributs
		this.filenames = filenames;
		this.directory = directory;
		
		// Chargement des images
		images = new ImageIcon[filenames.size()];
		Integer[] intArray = new Integer[filenames.size()];
		for (int i = 0; i < filenames.size(); i++)
		{
			intArray[i] = new Integer(i);
			images[i] = TSwingUtils.getImage(directory + filenames.get(i));
			images[i].setImage(resizeImage(images[i].getImage()));
			if (images[i] != null)
			{
				images[i].setDescription(directory + filenames.get(i));
			}
		}

		// Création de la combobox
		comboList = new JComboBox(intArray);
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		renderer.setPreferredSize(new Dimension(RENDERED_WIDTH, RENDERED_HEIGHT));
		comboList.setRenderer(renderer);
		comboList.setMaximumRowCount(3);

		//Lay out the demo.
		add(comboList, BorderLayout.WEST);
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
			if (images[i].getDescription().equals(filename))
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
			return filenames.get(comboList.getSelectedIndex());
		}
	}
	
	public void addActionListener(ActionListener a)
	{
		comboList.addActionListener(a);
	}
	
	@Override
	public void setEnabled(boolean enable)
	{
		// TODO Auto-generated method stub
		super.setEnabled(enable);
		comboList.setEnabled(enable);
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
				setBackground(list.getBackground().brighter());
				setForeground(list.getForeground().brighter());
			}

			// Set the icon and text. If icon was null, say so.
			if(comboList.isEnabled() == true)
			{
				ImageIcon icon = images[selectedIndex];
				setIcon(icon);
			}
			else
			{
				setIcon(new ImageIcon());
			}

			return this;
		}
	}
	
	protected Image resizeImage (Image originalImage)
	{
		return originalImage.getScaledInstance(-1, RENDERED_HEIGHT - SPACE_BETWEEN_IMAGES, Image.SCALE_SMOOTH);
	}
}
