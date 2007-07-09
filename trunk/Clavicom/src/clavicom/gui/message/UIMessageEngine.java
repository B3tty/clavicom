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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import clavicom.core.listener.CMessageListener;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.language.UIString;
import clavicom.gui.windows.UIKeyboardFrame;

public class UIMessageEngine implements CMessageListener
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	UIKeyboardFrame uiKeyboardFrame;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	public UIMessageEngine()
	{
		// Ajout comme listener de nouveaux messages
		CMessageEngine.addNewMessageListener(this);
		uiKeyboardFrame = null;
	}

	// ----------------------------------------------------------- METHODES --//
	public void onNewFatalError(String newMessage, String detail)
	{
		MonDialogue d = new MonDialogue( 
				uiKeyboardFrame, 
				UIString.getUIString("EX_TITLE_FATAL_ERROR"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );

		// On ferme l'application
		System.exit(0);
	}

	public void onNewError(String newMessage, String detail)
	{
		MonDialogue d = new MonDialogue( 
				uiKeyboardFrame, 
				UIString.getUIString("EX_TITLE_ERROR"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
	}

	public void onNewInfo(String newMessage, String detail)
	{
		MonDialogue d = new MonDialogue( 
				uiKeyboardFrame, 
				UIString.getUIString("EX_TITLE_INFO"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
	}

	public void onNewWarning(String newMessage, String detail)
	{
		MonDialogue d = new MonDialogue( 
				uiKeyboardFrame, 
				UIString.getUIString("EX_TITLE_WARNING"), 
				newMessage, 
				detail, 
				false );
		d.pack();
		d.setAlwaysOnTop( true ); 
		d.setVisible( true );
	}

	public UIKeyboardFrame getUiKeyboardFrame()
	{
		return uiKeyboardFrame;
	}

	public void setUiKeyboardFrame(UIKeyboardFrame uiKeyboardFrame)
	{
		this.uiKeyboardFrame = uiKeyboardFrame;
	}

	// --------------------------------------------------- METHODES PRIVEES --//
}

class MonDialogue extends JDialog
{
	// les données mises à jour par la boîte de dialogue
	String message;
	String detail;

	public MonDialogue(JFrame f, String titre, String myMessage, String myDetail, boolean modal)
	{
		super(f, titre, modal);
		
		message = myMessage;
		detail = myDetail;
		
		setLocationRelativeTo( f );
		
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// les methodes de réponse aux boutons OK et Cancel

	void jButtonOK_actionPerformed(ActionEvent e)
	{
		setVisible( false );
	}

	void jButtonCANCEL_actionPerformed(ActionEvent e)
	{
		setVisible( false );
	}


	JButton jButton1 = new JButton();
	JPanel panelButton = new JPanel();
	JLabel lmessage = new JLabel();
	JLabel ldetail = new JLabel();

	private void jbInit() throws Exception
	{
		jButton1.setText("OK");
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButtonOK_actionPerformed(e);
			}
		});

		lmessage.setText(message);
		ldetail.setText(detail);
		
		panelButton.add(jButton1);
		
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().add(panelButton, BorderLayout.SOUTH);
		this.getContentPane().add(lmessage, BorderLayout.NORTH);
		this.getContentPane().add(ldetail, BorderLayout.CENTER);

	}

}
