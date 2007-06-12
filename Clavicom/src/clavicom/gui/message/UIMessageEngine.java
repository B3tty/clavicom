/*-----------------------------------------------------------------------------+

			Filename			: UIMessageEngine.java
			Creation date		: 12 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.message

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

package clavicom.gui.message;


import javax.swing.JOptionPane;

import clavicom.core.listener.CMessageListener;
import clavicom.core.message.CMessageEngine;

public class UIMessageEngine implements CMessageListener
{
	//--------------------------------------------------------- CONSTANTES --//
 
	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIMessageEngine()
	{
		// Ajout comme listener de nouveaux messages
		CMessageEngine.addNewMessageListener(this);
	}

	//----------------------------------------------------------- METHODES --//
	public void onNewFatalError(String newMessage, String detail)
	{
		if (detail == null)
		{
			JOptionPane.showMessageDialog(null, newMessage);
		}
		else
		{
			JOptionPane.showMessageDialog(null, newMessage + "\n\n" + detail);
		}
		
		
		// On ferme l'application
		System.exit(0);
	}
	
	public void onNewError(String newMessage, String detail)
	{
		if (detail == null)
		{
			JOptionPane.showMessageDialog(null, newMessage);
		}
		else
		{
			JOptionPane.showMessageDialog(null, newMessage + "\n\n" + detail);
		}
	}

	public void onNewInfo(String newMessage, String detail)
	{
		if (detail == null)
		{
			JOptionPane.showMessageDialog(null, newMessage);
		}
		else
		{
			JOptionPane.showMessageDialog(null, newMessage + "\n\n" + detail);
		}
	}

	public void onNewWarning(String newMessage, String detail)
	{
		if (detail == null)
		{
			JOptionPane.showMessageDialog(null, newMessage);
		}
		else
		{
			JOptionPane.showMessageDialog(null, newMessage + "\n\n" + detail);
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
