/*-----------------------------------------------------------------------------+

			Filename			: UIMouseFrame.java
			Creation date		: 25 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.mouse

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

package clavicom.gui.mouse;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.profil.CFramePosition;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TSize;

public class UIMouseFrame extends UITranslucentFrame
{
	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	UIMouse uiMouse;
	CMouse cMouse;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public UIMouseFrame(float myTransparency) throws Exception 
	{
		super(myTransparency);
		
		cMouse = CMouse.CreateMouse(
				CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(), 
				CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
				CProfil.getInstance().getDefaultColor().getDefaultKeyEntered().getColor() );
		
		uiMouse = new UIMouse( cMouse, this );
		
		setFocusableWindowState(true);
		setAlwaysOnTop(true);
		
		CFramePosition souricomPosition = CProfil.getInstance().getAdvancedOption().getSouricomFramePosition();

		setBounds( TSize.getRectangleBound( souricomPosition.getLeftUp(), souricomPosition.getRightDown() ) );
		
		add( uiMouse );
		
		addWindowListener(new WindowListener(){

			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent e)
			{
				ClickEngine.getInstance().stopMouseHook();
			}

			public void windowClosing(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	//----------------------------------------------------------- METHODES --//	

	public void SwitchClickMode(  )
	{
		uiMouse.SwitchClickMode();
	}
	public void SwitchMoveMode(  )
	{
		uiMouse.SwitchMoveMode();
	}
	public void startDefilMouse()
	{
		uiMouse.startDefilMouse();
	}
	public void stopDefilMouse()
	{
		uiMouse.stopDefilMouse();
	}
	//--------------------------------------------------- METHODES PRIVEES --//

	public CMouse getCMouse()
	{
		return cMouse;
	}

	public UIMouse getUiMouse()
	{
		return uiMouse;
	}
}
