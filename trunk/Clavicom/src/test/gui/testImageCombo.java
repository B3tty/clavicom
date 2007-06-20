/*-----------------------------------------------------------------------------+

			Filename			: testPanelOptionKey.java
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

//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;

import javax.swing.JFrame;

//import clavicom.gui.edition.key.UIImageCombos;
import clavicom.gui.edition.key.captionchoozer.UIPanelCaptionChooser;

public class testImageCombo
{
	public static void main(String[] args)
	{	
		// Création de la frame
		JFrame frame = new JFrame();
		frame.setSize(500,500);
//		
//		// Récupération des fichiers du répertoire
//		File repertoire = new File("Ressources/Application/Pictures/");
//		File[] list;
//		
//		List<String> fileNames = new ArrayList<String>();
//		if (repertoire.isDirectory())
//		{
//			list = repertoire.listFiles();
//			if(list != null)
//			{
//				 for ( File currentFile : list)
//				 {
//					 fileNames.add(currentFile.getName());
//				 }
//			}
//		}
//		
//		// Création de la combobox
//		UIImageCombo imageCombo = new UIImageCombo(repertoire.getPath() + "/",fileNames);
//		
//		// Sélection d'une image
//		imageCombo.selectGoodImage("firefox.png");

		UIPanelCaptionChooser test = new UIPanelCaptionChooser("Ressources/Application/Pictures/");
		
		
		frame.add(test);

		
		frame.setVisible(true);
	}
}
