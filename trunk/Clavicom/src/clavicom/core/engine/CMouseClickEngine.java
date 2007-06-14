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
import java.awt.Robot;
import java.awt.event.InputEvent;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyClick;
import clavicom.core.listener.onClicMouseClickListener;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.language.UIString;
import clavicom.tools.TMouseKeyClickEnum;

public class CMouseClickEngine implements onClicMouseClickListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Robot robot = null;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseClickEngine( CMouse mouse )
	{
		
		

		// =============================================================
		// Abonnement aux listener
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
	}

	//----------------------------------------------------------- METHODES --//
	
	
	

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
			robot.mousePress( InputEvent.BUTTON2_MASK );
			robot.mouseRelease( InputEvent.BUTTON2_MASK );
		} else if( keyClic.GetClick() == TMouseKeyClickEnum.DOUBLE_BUTTON_1 )
		{
			robot.mousePress( InputEvent.BUTTON1_MASK );
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
			robot.mousePress( InputEvent.BUTTON1_MASK );
			robot.mouseRelease( InputEvent.BUTTON1_MASK );
		}

	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	
	
	

	
}
