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
import java.util.ArrayList;
import java.util.List;

import clavicom.core.keygroup.keyboard.command.CCode;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyLastWordListener;
import clavicom.tools.TKeyAction;
import clavicom.tools.TLevelEnum;

public class CCommandEngine implements OnClickKeyCharacterListener,OnClickKeyLastWordListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	TLevelEnum currentLevel;

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//
	
	protected void executeCommande( List<CCommand> commandList )
	{
		Robot robot = null;
		CCode code = null;
		
		
		try
		{
			robot = new Robot();
		}
		catch ( AWTException e )
		{
			e.printStackTrace();
		}
		
		for( CCommand command : commandList )
		{
			for( int i = 0 ; i < command.Size() ; ++i )
			{
				code = command.GetCode( i );
				if( code.GetKeyAction() == TKeyAction.PRESSED )
				{
					robot.keyPress( code.GetKeyEvent() );
				}
				else if( code.GetKeyAction() == TKeyAction.RELEASED )
				{
					robot.keyRelease( code.GetKeyEvent() );
				}
			}
		}
	}
	
	
	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		List<CCommand> commandList = new ArrayList<CCommand>();
		
		if( currentLevel == TLevelEnum.NORMAL )
		{
			commandList.add( keyCharacter.getCommandNormal() );
		}else if( currentLevel == TLevelEnum.SHIFT )
		{
			commandList.add( keyCharacter.getCommandShift() );
		}else if( currentLevel == TLevelEnum.ALT_GR )
		{
			commandList.add( keyCharacter.getCommandAltGr() );
		}
		
		executeCommande( commandList );
	}


	public void onClickKeyLastWord(CKeyLastWord keyLasWord)
	{
		//executeCommande( keyLasWord.getCommands() );
		
	}


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
