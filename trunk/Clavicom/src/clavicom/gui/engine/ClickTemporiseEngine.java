/*-----------------------------------------------------------------------------+

			Filename			: DefilementKeyEngine.java
			Creation date		: 20 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.engine

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

package clavicom.gui.engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.listener.DefilListener;

public class ClickTemporiseEngine implements DefilListener
{
	//--------------------------------------------------------- CONSTANTES --//
	

	//---------------------------------------------------------- VARIABLES --//
	
	Robot robot;
	
	static ClickTemporiseEngine instance;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	protected ClickTemporiseEngine()
	{
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createInstance()
	{
		instance = new ClickTemporiseEngine( );
	}
	public static ClickTemporiseEngine getInstance()
	{
		return instance;
	}
	
	public void startClickTempoEngine()
	{
		DefilementEngine.getInstance().addDefilListener( this );
	}
	
	public void stopClickTempoEngine()
	{
		DefilementEngine.getInstance().removeDefilListener( this );
	}


	public void defil()
	{
		ClickEngine.getInstance().mouseHookPause();
		robot.mousePress( InputEvent.BUTTON1_MASK );
		robot.mouseRelease( InputEvent.BUTTON1_MASK );
		ClickEngine.getInstance().mouseHookResume();
	}
	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}


