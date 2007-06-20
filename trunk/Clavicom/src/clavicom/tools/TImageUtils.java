/*-----------------------------------------------------------------------------+

			Filename			: TImageUtils.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import clavicom.CFilePaths;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.language.UIString;

public class TImageUtils
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	
	/**
	 * Converti une Image en BufferedImage
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image)
	{
		// On test si l'image n'est pas déja une instance de BufferedImage
		if ( image instanceof BufferedImage )
		{
			return ((BufferedImage) image);
		}
		else
		{
			// On s'assure que l'image est complètement chargée
			image = new ImageIcon(image).getImage();

			// On crée la nouvelle image
			BufferedImage bufferedImage = new BufferedImage(image
					.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();

			return (bufferedImage);
		}
	}
	
	public static ImageIcon getImage(String filepath)
	{
		File fileImage;
		fileImage = new File(filepath);
		if (fileImage.exists() == false)
		{ 
			CMessageEngine.newError(	UIString.getUIString("MSG_KEY_IMAGE_FILE_NOT_FOUND_1") + 
										filepath,
										UIString.getUIString("MSG_KEY_IMAGE_FILE_NOT_FOUND_2"));
			
			filepath = CFilePaths.getDefaultPicturePath();
			fileImage = new File(filepath);
			
			if(fileImage.exists() == false)
			{
				CMessageEngine.newFatalError(	UIString.getUIString("MSG_KEY_IMAGE_FILE_NOT_FOUND_1") + 
												filepath,
												UIString.getUIString("MSG_KEY_IMAGE_FILE_NOT_FOUND_2"));
			}
			
		}
		
		// Création de l'image icon
		ImageIcon iconImage = new ImageIcon(filepath);
		
		return iconImage;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
