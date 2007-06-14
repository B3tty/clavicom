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

package clavicom.core.engine.mouse;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.Timer;
import javax.swing.event.EventListenerList;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyClick;
import clavicom.core.keygroup.mouse.CMouseKeyMove;
import clavicom.core.listener.onClicMouseClickListener;
import clavicom.core.listener.onClicMouseMoveListener;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.tools.TMouseKeyClickEnum;
import clavicom.tools.TMouseKeyMoveEnum;

public class CMouseEngine implements onClicMouseMoveListener, onClicMouseClickListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Robot robot = null;
	
	Timer moveTimer;
	TMouseKeyMoveEnum currentMove;
	
	protected EventListenerList listenerList;
	
	VirtualPointer virtualPointer;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseEngine( CMouse mouse )
	{
		
		

		// =============================================================
		// Abonnement aux listener de move
		// =============================================================
		mouse.getMoveDown().addOnClicMouseMoveListener( this );
		mouse.getMoveLeft().addOnClicMouseMoveListener( this );
		mouse.getMoveRight().addOnClicMouseMoveListener( this );
		mouse.getMoveUp().addOnClicMouseMoveListener( this );
		
		
		// =============================================================
		// Abonnement aux listener de click
		// =============================================================
		mouse.getLeftClick().addOnClicMouseClickListener( this );
		mouse.getLeftDubbleClick().addOnClicMouseClickListener( this );
		mouse.getLeftPress().addOnClicMouseClickListener( this );
		mouse.getLeftRelease().addOnClicMouseClickListener( this );
		mouse.getRightClick().addOnClicMouseClickListener( this );
		
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			CMessageEngine.newError( UIString.getUIString("MSG_COMMAND_ENGINE_NO_ROBOT"), e.getMessage());
		}
		
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			CMessageEngine.newError( UIString.getUIString("MSG_COMMAND_ENGINE_NO_ROBOT"), e.getMessage());
		}
		
		listenerList = new EventListenerList();
		
		virtualPointer = new VirtualPointer( );
		virtualPointer.setVisible( true );
		
		// création du timer
		moveTimer = createMoveTimer();
		
	}

	//----------------------------------------------------------- METHODES --//
	
	
	
	

	public void onClicMouseMove( CMouseKeyMove keyMove )
	{

		// on regarde quel movement il veut faire
		currentMove = keyMove.GetDirection();
		
		moveTimer.start();
		
	}
	
	public void onClicMouseClick(CMouseKeyClick keyClic)
	{

		// on regarde quel movement il veut faire
		if( keyClic.GetClick() == TMouseKeyClickEnum.BUTTON_1 )
		{
			robot.mousePress( InputEvent.BUTTON1_MASK );
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
		} else if( keyClic.GetClick() == TMouseKeyClickEnum.BUTTON_1_PRESS )
		{
			robot.mousePress( InputEvent.BUTTON1_MASK );
		} else if( keyClic.GetClick() == TMouseKeyClickEnum.BUTTON_1_RELEASE )
		{
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
		} else if( keyClic.GetClick() == TMouseKeyClickEnum.BUTTON_2 )
		{
			robot.mousePress( InputEvent.BUTTON3_MASK );
			robot.mouseRelease( InputEvent.BUTTON3_MASK );
		} else if( keyClic.GetClick() == TMouseKeyClickEnum.DOUBLE_BUTTON_1 )
		{
			robot.mousePress( InputEvent.BUTTON1_MASK );
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
			robot.mousePress( InputEvent.BUTTON1_MASK );
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
		}

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
					int x = (int)virtualPointer.getLocation().getX();
					int y = (int)virtualPointer.getLocation().getY();
					
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
					
					virtualPointer.setLocation( x, y );
					
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
