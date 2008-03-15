/*-----------------------------------------------------------------------------+

			Filename			: ClavicomMessageDialog.java
			Creation date		: 15 mars 08
		
			Project				: Clavicom
			Package				: clavicom.gui.message

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2008) Centre ICOM'

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

import clavicom.tools.TSwingUtils;

class ClavicomMessageDialog extends JDialog
{
	// les données mises à jour par la boîte de dialogue
	String message;
	String detail;

	public ClavicomMessageDialog(JFrame f, String titre, String myMessage, String myDetail, boolean modal)
	{
		super(f, titre, modal);
		
		message = myMessage;
		detail = myDetail;
		
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
	
	@Override
	public void setVisible(boolean arg0)
	{
		// TODO Auto-generated method stub
		TSwingUtils.centerComponentToScreen(this);
		super.setVisible(arg0);
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
