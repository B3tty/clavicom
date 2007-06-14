/*-----------------------------------------------------------------------------+

			Filename			: CCommandEngine.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.engine

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

package clavicom.core.engine;


import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyMove;
import clavicom.core.listener.onClicMouseMoveListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.tools.TMouseKeyMoveEnum;

public class CMouseMoveEngine implements onClicMouseMoveListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Robot robot = null;
	
	Timer moveTimer;
	TMouseKeyMoveEnum currentMove;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseMoveEngine( CMouse mouse )
	{
		
		

		// =============================================================
		// Abonnement aux listener
		// =============================================================
		mouse.getMoveDown().addOnClicMouseMoveListener( this );
		mouse.getMoveLeft().addOnClicMouseMoveListener( this );
		mouse.getMoveRight().addOnClicMouseMoveListener( this );
		mouse.getMoveUp().addOnClicMouseMoveListener( this );
		
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			CMessageEngine.newError( UIString.getUIString("MSG_COMMAND_ENGINE_NO_ROBOT"), e.getMessage());
		}
		
	}

	//----------------------------------------------------------- METHODES --//
	
	

	public void onClicMouseMove( CMouseKeyMove keyMove )
	{
		
		
		
		// création du timer
		moveTimer = createMoveTimer();
		
		// on regarde quel movement il veut faire
		currentMove = keyMove.GetDirection();
		
		moveTimer.start();
		
	}

	
	protected Timer createMoveTimer()
	{
		// Création d'une instance de listener
		// associée au timer
		ActionListener action = new ActionListener()
		{
			// Méthode appelée à chaque tic du timer
			public void actionPerformed(ActionEvent event)
			{
				if( robot != null )
				{
					int x = (int)MouseInfo.getPointerInfo().getLocation().getX();
					int y = (int)MouseInfo.getPointerInfo().getLocation().getY();
					
					// on regarde quel mouvement on doit faire
					if( currentMove == TMouseKeyMoveEnum.DOWN )
					{
						y += 1;
					} else if( currentMove == TMouseKeyMoveEnum.LEFT )
					{
						x -= 1;
					} else if( currentMove == TMouseKeyMoveEnum.RIGHT )
					{
						x += 1;
					} else if( currentMove == TMouseKeyMoveEnum.UP )
					{
						y -= 1;
					}
					
					robot.mouseMove( x , y );
				}
			}
		};
		
		CProfil profil = CProfil.getInstance();
		int vitesse = profil.getNavigation().getMouseSpeed();
		
		// on inverse la valeur
		int duration = 101 - vitesse;
		
		// on la multiplie par deux pour que ca coresponde mieux aux
		// attentes de l'utilisateur
		duration = (duration / 10) * 2;

		// Création d'un timer qui génère un tic
		return new Timer( duration ,action );
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
