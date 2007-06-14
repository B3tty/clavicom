/*-----------------------------------------------------------------------------+

 Filename			: VirtualPointer.java
 Creation date		: 14 juin 07
 
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

package clavicom.core.engine.mouse;

import java.awt.Color;
import javax.swing.JFrame;

public class VirtualPointer extends JFrame
{

	public VirtualPointer( )
	{
		super();

		setSize(30, 50);
		setLocation(500,500);
		getContentPane().setBackground( Color.red );
		setUndecorated( true );
	}

	public void moveVirtualPointer(int x, int y)
	{
		setLocation(x, y);
	}
}
