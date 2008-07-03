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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import clavicom.core.listener.CKeyClickListener;
import clavicom.core.listener.CKeyMouseOverEventListener;
import clavicom.core.profil.CProfil;
import clavicom.tools.TNavigationType;

public class ClickTemporiseEngine implements CKeyMouseOverEventListener
{
	//--------------------------------------------------------- CONSTANTES --//
	

	//---------------------------------------------------------- VARIABLES --//
	
	static ClickTemporiseEngine instance;
	protected Timer clickTimer;
	protected CKeyClickListener curClickListener;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	protected ClickTemporiseEngine()
	{
		clickTimer = createTimer( );
	}
	
	public static void createInstance()
	{
		instance = new ClickTemporiseEngine( );
	}
	public static ClickTemporiseEngine getInstance()
	{
		return instance;
	}

	public void mouseEntered(CKeyClickListener clickListener)
	{
		if (CProfil.getInstance().getNavigation().getTypeNavigation() != TNavigationType.CLICK_TEMPORISE)
			return;
		
		curClickListener = clickListener;
		clickTimer.start();
	}

	public void mouseLeft()
	{
		if (CProfil.getInstance().getNavigation().getTypeNavigation() != TNavigationType.CLICK_TEMPORISE)
			return;
		
		clickTimer.stop();
		curClickListener = null;
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	protected Timer createTimer()
	{
		ActionListener action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (curClickListener != null)
				{
					curClickListener.Click();
					clickTimer.stop();
				}
			}
		};
		
		// Création d'un timer qui génère un tic		
		return new Timer( CProfil.getInstance().getNavigation().getTemporisationClic() ,action );
	} 
}


