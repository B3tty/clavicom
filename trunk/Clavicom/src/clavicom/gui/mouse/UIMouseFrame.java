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
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import clavicom.core.engine.CMouseEngine;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.profil.CFramePosition;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TSize;

public class UIMouseFrame extends UITranslucentFrame 
{
	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	UIMouse uiMouse;
	CMouse cMouse;



	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public UIMouseFrame() throws Exception 
	{
		super( );
		
		cMouse = CMouse.CreateMouse(
				CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(), 
				CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
				CProfil.getInstance().getDefaultColor().getDefaultKeyEntered().getColor() );
		
		uiMouse = new UIMouse( cMouse );
		
		setFocusableWindowState(true);
		setAlwaysOnTop(true);
		
		CFramePosition souricomPosition = CProfil.getInstance().getAdvancedOption().getSouricomFramePosition();

		setBounds( TSize.getRectangleBound( souricomPosition.getLeftUp(), souricomPosition.getRightDown() ) );
		
		UIMovingPanel movingPanel = new UIMovingPanel( this );
		BorderLayout myBorderLayout = new BorderLayout();
		movingPanel.setLayout( myBorderLayout );
		movingPanel.setEditable( true );
		movingPanel.add( uiMouse, BorderLayout.CENTER );

		add( movingPanel );
		
		addWindowListener(new WindowAdapter()
		{

			public void windowClosed(WindowEvent e)
			{
				ClickEngine.getInstance().stopMouseHook();
			}
			
		});
		
		
	}

	
	@Override
	public void setVisible(boolean b)
	{
		// TODO Auto-generated method stub
		super.setVisible(b);
		
		if( b )
		{
			setTransparency( CProfil.getInstance().getTransparency().getKeyboardTransparency() );
			CMouseEngine.getInstance().startTimer();
			uiMouse.SwitchMoveMode();
		}
		else
		{
			setTransparency( 1 );
			CMouseEngine.getInstance().stopTimer();
		}
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
