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

import javax.swing.JFrame;

import clavicom.core.listener.CMessageListener;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.language.UIString;

public class UIMessageEngine implements CMessageListener
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	JFrame parentFrame;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	public UIMessageEngine(JFrame parentFrame)
	{
		// Ajout comme listener de nouveaux messages
		CMessageEngine.addNewMessageListener(this);
		this.parentFrame = parentFrame;
	}

	// ----------------------------------------------------------- METHODES --//
	public void onNewFatalError(String newMessage, String detail)
	{
		ClavicomMessageDialog d = new ClavicomMessageDialog( 
				parentFrame, 
				UIString.getUIString("EX_TITLE_FATAL_ERROR"), 
				newMessage, 
				detail, 		
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
		
		System.out.println(newMessage + "|" + detail);

		// On ferme l'application
		System.exit(0);

		
//		// Affichage du dialog
//		JOptionPane.showMessageDialog(	null,											// Parent
//										formatMessage(newMessage, detail),				// Message (avec détail)
//										UIString.getUIString("EX_TITLE_FATAL_ERROR"),	// Titre
//										JOptionPane.ERROR_MESSAGE);						// Icône
//		// On ferme l'application
//		System.exit(0);

	}

	public void onNewError(String newMessage, String detail)
	{
		ClavicomMessageDialog d = new ClavicomMessageDialog( 
				parentFrame, 
				UIString.getUIString("EX_TITLE_ERROR"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
		
		System.out.println(newMessage + "|" + detail);
		
//		// Affichage du dialog
//		
//		JOptionPane.showMessageDialog(	null,								// Parent
//										formatMessage(newMessage, detail),				// Message (avec détail)
//										UIString.getUIString("EX_TITLE_ERROR"),			// Titre
//										JOptionPane.ERROR_MESSAGE);						// Icône
//		System.out.println(formatMessage(newMessage, detail));
	}

	public void onNewInfo(String newMessage, String detail)
	{
		ClavicomMessageDialog d = new ClavicomMessageDialog( 
				parentFrame, 
				UIString.getUIString("EX_TITLE_INFO"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
		
		System.out.println(newMessage + "|" + detail);
		
//		// Affichage du dialog
//		JOptionPane.showMessageDialog(	null,								// Parent
//										formatMessage(newMessage, detail),				// Message (avec détail)
//										UIString.getUIString("EX_TITLE_INFO"),			// Titre
//										JOptionPane.INFORMATION_MESSAGE);				// Icône
	}

	public void onNewWarning(String newMessage, String detail)
	{
		ClavicomMessageDialog d = new ClavicomMessageDialog( 
				parentFrame, 
				UIString.getUIString("EX_TITLE_WARNING"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
		
		
		System.out.println(newMessage + "|" + detail);
		
		// Affichage du dialog
//		JOptionPane.showMessageDialog(	null,								// Parent
//										formatMessage(newMessage, detail),				// Message (avec détail)
//										UIString.getUIString("EX_TITLE_WARNING"),		// Titre
//										JOptionPane.WARNING_MESSAGE);					// Icône
//		
//		
	}

//	public void setUiKeyboardFrame(UIKeyboardFrame uiKeyboardFrame)
//	{
//		this.uiKeyboardFrame = uiKeyboardFrame;
//	}

	// --------------------------------------------------- METHODES PRIVEES --//
//	private String formatMessage (String message, String detail)
//	{
//		if(detail == null)
//		{
//			return message;
//		}
//		else
//		{
//			return (message + "\n\n" + detail);
//		}
//	}
}
