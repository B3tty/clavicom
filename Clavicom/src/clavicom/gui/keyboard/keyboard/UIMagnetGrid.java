/*-----------------------------------------------------------------------------+

			Filename			: UIMagnetGrid.java
			Creation date		: 2 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.keyboard

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

package clavicom.gui.keyboard.keyboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIMagnetGrid
{
	//--------------------------------------------------------- CONSTANTES --//
	public final int NONE = -1;			// Numéro de ligne ou colonne par défaut
	
	//---------------------------------------------------------- VARIABLES --//	
	private List<Integer> listVerticals;	// Numéros des pixels des X
	private List<Integer> listHorizontals;		// Numéros des pixels des Y
	
	private int width;					// Largeur de la grille
	private int height;					// Hauteur de la grille
	
	private int nbVerticals;			// Nombre de verticales
	private int nbHorizontals;			// Nombre d'horizontales
	
	private Color colorGrid;			// Couleur de la grille
	private BufferedImage image;		// Image
	private boolean imageUpToDate;		// Indique si l'image est à jour
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIMagnetGrid()
	{
		// Création des attributs
		listVerticals = new ArrayList<Integer>();
		listHorizontals = new ArrayList<Integer>();
		width = 0;
		height = 0;
		nbVerticals = 0;
		nbHorizontals = 0;	
		
		colorGrid = Color.BLACK;
		imageUpToDate = false;
	}

	//----------------------------------------------------------- METHODES --//	
	/**
	 * Initialise tous les paramètres
	 */
	public void setAll(int width, int height, int nbVerticals, int nbHorizontals)
	{
		// Recopie des attributs
		this.width = width;
		this.height = height;
		this.nbVerticals = nbVerticals;
		this.nbHorizontals = nbHorizontals;
		
		// On met a jour les listes
		updateLists();
		
		imageUpToDate = false;
	}
	
	/**
	 * Initialise les dimensions de la grille
	 * @param width
	 * @param height
	 */
	public void setDimensions(int width, int height)
	{
		// Recopie des attributs
		this.width = width;
		this.height = height;
		
		// On met a jour les listes
		updateLists();
		
		imageUpToDate = false;
	}
	
	/**
	 * Met à jour le nombre de verticales
	 * @param nbVerticals
	 */
	public void setNbVerticals(int nbVerticals)
	{
		// Recopie des attributs		
		this.nbVerticals = nbVerticals;
		
		// On met a jour les listes
		updateLists();
		
		imageUpToDate = false;
	}
	
	/**
	 * Met à jour le nombre d'horizontales
	 * @param nbHorizontals
	 */
	public void setNbHorizontals(int nbHorizontals)
	{
		// Recopie des attributs
		this.nbHorizontals = nbHorizontals;
		
		// On met a jour les listes
		updateLists();
		
		imageUpToDate = false;
	}
	
	/**
	 * Retourne la position de la verticale la plus proche,
	 * -1 en cas d'erreur
	 */
	public int getNearestVertical (Point point)
	{
		int currentDifference = 0;
		int minimalDifference = width;
		Integer lastChoice = 0;
	
		for(Integer currentVertical : listVerticals)
		{
			// Calcul de la différence avec la verticale
			currentDifference = Math.abs((int)point.getX() - currentVertical);
			
			if (currentDifference < minimalDifference)
			// On se rapproche -> On continue
			{
				minimalDifference = currentDifference;
				lastChoice = currentVertical;
			}
			else
			// On s'eloigne -> STOP
			{
				return lastChoice;
			}
		}
		
		return NONE;
	}
	
	/**
	 * Retourne la position de l'horizontale la plus proche,
	 * -1 en cas d'erreur
	 */
	public int getNearestHorizontal (Point point)
	{
		int currentDifference = 0;
		int minimalDifference = width;
		Integer lastChoice = 0;
	
		for(Integer currentHorizontal : listHorizontals)
		{
			// Calcul de la différence avec la verticale
			currentDifference = Math.abs((int)point.getY() - currentHorizontal);
			
			if (currentDifference < minimalDifference)
			// On se rapproche -> On continue
			{
				minimalDifference = currentDifference;
				lastChoice = currentHorizontal;
			}
			else
			// On s'eloigne -> STOP
			{
				return lastChoice;
			}
		}
		
		return NONE;
	}
	
	/**
	 * Change la couleur de la grille
	 * @param colorGrid
	 */
	public void setGridColor(Color colorGrid)
	{
		this.colorGrid = colorGrid;
		
		imageUpToDate = false;
	}
	/**
	 * Retourne l'image correspondant à la grille
	 * @return
	 */
	protected BufferedImage getDrawing()
	{	
		// Si l'image est à jour, on ne la recréé pas
		if(imageUpToDate == true)
		{
			return image;
		}
		
		// Création de l'image
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D buffer = (Graphics2D) image.getGraphics();
		
		// Construction du buffer
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Application de la couleur
		buffer.setColor(colorGrid);
		
		// Création des verticales
		for(Integer currentVertical : listVerticals)
		{
			buffer.drawLine(	currentVertical, 	// X début
								0, 					// Y début
								currentVertical, 	// X fin
								height - 1);		// Y fin
		}
		
		// Création des horizontales
		for(Integer currentHorizontal : listHorizontals)
		{
			buffer.drawLine(	0, 					// X début
								currentHorizontal, 	// Y début
								width - 1, 			// X fin
								currentHorizontal);	// Y fin
		}
		
		// On indique que l'image est à jour
		imageUpToDate = true;
		
		// Retour
		return image;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Recréé les liste des lignes
	 */
	protected void updateLists()
	{
		// On vide les deux listes
		listVerticals.clear();
		listHorizontals.clear();
		
		// ---------- VERTICALES --------------------------------------------
		float pasVertical = (float)width/(float)nbVerticals;
		float currentVertivalPos = 0;
		
		for(int i = 0 ; i < nbVerticals ; ++i)
		{
			listVerticals.add(Math.round(currentVertivalPos));
			currentVertivalPos += pasVertical;
		}
		
		// On ajoute une ligne à la fin
		listVerticals.add(width);
		
		// ---------- HORIZONTALES ------------------------------------------
		float pasHorizontal = (float)height/(float)nbHorizontals;
		float currentHorizontalPos = 0;
		
		for(int i = 0 ; i < nbHorizontals ; ++i)
		{
			listHorizontals.add(Math.round(currentHorizontalPos));
			currentHorizontalPos += pasHorizontal;
		}
		
		// On ajoute une ligne à la fin
		listHorizontals.add(height);
	}
}