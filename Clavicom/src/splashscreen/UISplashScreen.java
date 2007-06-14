/*-----------------------------------------------------------------------------+

			Filename			: UISplashScreen.java
			Creation date		: 14 juin 07
		
			Project				: Clavicom
			Package				: splashscreen

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

package splashscreen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

import clavicom.core.message.CMessageEngine;
 
public class UISplashScreen extends JWindow implements Runnable 
{  
	//--------------------------------------------------------- CONSTANTES --//
	private final Font LABEL_FONT = new Font("Arial",Font.PLAIN,10);	// Police de caractère 
																		// pour les labels
	private final Color LABEL_COLOR = new Color(210,210,210);			// Couleur des caractères
	
	private final int LABEL_POS_X = 303;	// Position du label, X
	private final int LABEL_POS_Y = 198; 	// Position du label, y
	
	//---------------------------------------------------------- VARIABLES --//	
	private String imageFile;			// Chemin de l'image
	private BufferedImage bufImage;		// Buffer pour la capture
	private Rectangle rect;				// Rectangle capturé
	private boolean isAlive;			// Vrai au démarrage
	
	private String currentAction;		// Texte indiquant l'action courante
	private int waitTime;				// Temps d'attente entre chaque opération
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	/**
	 *  Construit et affiche le splash screen
	 *  
	 */
	public UISplashScreen(String imageFile, int waitTime) 
	{
		// Initialisation des attributs
		this.imageFile = imageFile;
		this.waitTime = waitTime;
		
		// Affichage du splashscreen
		run();
	}
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 *  Initialise le thread
	 */
	public void run()
	{
		// Initialisation des attributs
		isAlive = true;
		currentAction = "";
    
		// Utilisation d'ImageIcon, pas besoin de MediaTracker
		Image image = new ImageIcon(imageFile).getImage();
		int imageWidth = image.getWidth(this);
		int imageHeight = image.getHeight(this);
		
		// TODO : sélectionner la bonne police
		if (imageWidth > 0 && imageHeight > 0) 
		{
			int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
			  
			// On centre le rectangle à l'écran
			rect = new Rectangle(	(screenWidth - imageWidth) / 2, 
					  				(screenHeight - imageHeight) / 2,
					  				imageWidth, 
					  				imageHeight);
			  
			// Création d'un screenshot
			try 
			{
				bufImage = new Robot().createScreenCapture(rect);
			} 
			catch (AWTException e) 
			{
				CMessageEngine.newFatalError("Can not render splashscreen","Capture failed !");
			}
  
			// Obtention du graphic2D
			Graphics2D g2D = bufImage.createGraphics();
  
			// Dessine l'image sur le screenshot
			g2D.drawImage(image, 0, 0, this);
			  
			// On dessine l'image modifiée à la même place
			setBounds(rect);
			
			// Affichage
			setVisible(true);
		} 
		else 
		{
			CMessageEngine.newFatalError("Can not render splashscreen", "File " + imageFile + " was not found or is not an image file.");
		}
			    
		isAlive = false;
	}
 
	/**
	 * Masque et détruit le splash screen
	 * 
	 * @throws IllegalStateException
	 */
	public void close()
	{
		if (!isAlive)
		{
			dispose();
		}
		else
		{
			System.out.println("Splash string détruit mais mal initialisé...");
		}
	}

	/**
	 *  Redefinie la méthode de JWindow
	 *
	 */
	public void paint(Graphics g)
	{
		// Dessin du fond
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(bufImage, 0, 0, this);
		
		// Dessin du texte
		g2D.setFont(LABEL_FONT);
		g2D.setColor(LABEL_COLOR);
		g2D.setRenderingHint(	RenderingHints.KEY_TEXT_ANTIALIASING,
								RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2D.drawString(currentAction, LABEL_POS_X, LABEL_POS_Y);
	}
	
	/**
	 * Met à jour la chaîne de chargement
	 * @param message
	 */
	public void newStep(String message)
	{
		// Changement du message
		currentAction = message;
		
		// Redessin
		repaint();
		
		// On dort pour avoir le temps d'afficher le message
		try
		{
			Thread.sleep(waitTime);
		}
		catch (Exception e)
		{
			System.out.println("Sleep raté...");
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
