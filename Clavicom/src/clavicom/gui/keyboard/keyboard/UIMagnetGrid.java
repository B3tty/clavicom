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

import java.awt.BasicStroke;
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
	public final static int NONE = -1;			// Numéro de ligne ou colonne par défaut
	
	//---------------------------------------------------------- VARIABLES --//	
	private List<Integer> listVerticals;	// Numéros des pixels des X
	private List<Integer> listHorizontals;		// Numéros des pixels des Y
	
	private int width;					// Largeur de la grille
	private int height;					// Hauteur de la grille
	
	private int verticalStep;	// Step entre les colonnes en pourcents
	private int horizontalStep;// Step entre les lignes en pourcents
	
	private Color colorGrid;			// Couleur de la grille
	private BufferedImage image;		// Image
	private boolean imageUpToDate;		// Indique si l'image est à jour
	
	private int borderSize;				// Bordure du cadre dans lequel se trouve la griller
										// (permet de ne pas dessiner jusqu'au bord
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIMagnetGrid()
	{
		// Création des attributs
		listVerticals = new ArrayList<Integer>();
		listHorizontals = new ArrayList<Integer>();
		width = 0;
		height = 0;
		borderSize = 0;
		
		verticalStep = 0;
		horizontalStep = 0;
		
		colorGrid = new Color(0,0,0,.2f);	// Noir opaque à 20%

		imageUpToDate = false;
	}

	//----------------------------------------------------------- METHODES --//	
	/**
	 * Initialise tous les paramètres
	 */
	public void setAll(int width, int height, int verticalStep, int horizontalStep, int borderSize)
	{
		// Recopie des attributs
		this.width = width;
		this.height = height;
		this.verticalStep = verticalStep;
		this.horizontalStep = horizontalStep;
		this.borderSize = borderSize;
		
		// On met a jour les listes
		updateLists();
	}

	/**
	 * Spécifie la taille de la bordure
	 * @param borderSize
	 */
	public void setBorderSize(int borderSize)
	{
		this.borderSize = borderSize;
	}
	
	/**
	 * Initialise les dimensions de la grille
	 * @param width
	 * @param height
	 */
	public void setDimensions(int width, int height)
	{
		System.out.println("New dimensions : " + width + " " + height);
		// Recopie des attributs
		this.width = width;
		this.height = height;
		
		// On met a jour les listes
		updateLists();
	}
	
	public int getHorizontalStep()
	{
		return horizontalStep;
	}

	public void setHorizontalStep(int horizontalStep)
	{
		System.out.println(this + "H :" + horizontalStep);
		this.horizontalStep = horizontalStep;
		updateLists();
	}

	public int getVerticalStep()
	{
		return verticalStep;
	}

	public void setVerticalStep(int verticalStep)
	{
		System.out.println(this + "V :" + verticalStep);
		this.verticalStep = verticalStep;
		updateLists();
	}

	/**
	 * Retourne le point de la grille le plus proche d'un point 
	 * @param pt
	 * @return
	 */
	public Point getNearestPoint(Point pt)
	{
		if(pt == null)
			return null;
		
		int x = getNearestVertical((int)pt.getX());
		int y = getNearestHorizontal((int)pt.getY());
		
		if(x == NONE || y == NONE)
			return null;
		
		return new Point(x,y);
	}
	
	/**
	 * Retourne la position de la verticale la plus proche,
	 * -1 en cas d'erreur
	 */
	public int getNearestVertical (int x)
	{		
		int currentDifference = 0;
		int minimalDifference = width;
		Integer lastChoice = 0;
	
		int i = 0;
		for(Integer currentVertical : listVerticals)
		{
			// Calcul de la différence avec la verticale
			currentDifference = Math.abs(x - currentVertical);
			
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
			++i;
		}
		return lastChoice;
	}
	
	/**
	 * Retourne la position de l'horizontale la plus proche,
	 * -1 en cas d'erreur
	 */
	public int getNearestHorizontal (int y)
	{		
		int currentDifference = 0;
		int minimalDifference = height;
		Integer lastChoice = 0;
	
		int i = 0;
		for(Integer currentHorizontal : listHorizontals)
		{
			// Calcul de la différence avec la verticale
			currentDifference = Math.abs(y - currentHorizontal);
			
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
			i++;
		}
		return lastChoice;
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
		if(width <= 0 || height <= 0 )
			return null;
		
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
		
		// Application du pinceau
		buffer.setStroke(new BasicStroke(1));
		
		// Création des verticales
		// On dessine tout sauf les bords
		for(int i = 1 ; i < listVerticals.size() ; ++i)
		{
			buffer.drawLine(	listVerticals.get(i), 		// X début
								borderSize, 				// Y début
								listVerticals.get(i), 		// X fin
								height - 1 - borderSize);	// Y fin
		}
		
		// Création des horizontales
		for(int i = 1 ; i < listHorizontals.size() ; ++i)
		{
			buffer.drawLine(	borderSize, 				// X début
								listHorizontals.get(i), 	// Y début
								width - 1 - borderSize, 	// X fin
								listHorizontals.get(i));	// Y fin
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
		if(width <= 0 || height <= 0 || verticalStep <= 0 || horizontalStep <= 0)
			return;
		
		// On vide les deux listes
		listVerticals.clear();
		listHorizontals.clear();
		
		// ---------- VERTICALES --------------------------------------------
		float currentVertivalPos = 0;
		while (currentVertivalPos < width)
		{
			listVerticals.add(Math.round(currentVertivalPos));
			currentVertivalPos += verticalStep;
		}
		
		// ---------- HORIZONTALES ------------------------------------------
		float currentHorizontalPos = 0;
		while (currentHorizontalPos < height)
		{
			listHorizontals.add(Math.round(currentHorizontalPos));
			currentHorizontalPos += horizontalStep;
		}
		
		imageUpToDate = false;
	}
}