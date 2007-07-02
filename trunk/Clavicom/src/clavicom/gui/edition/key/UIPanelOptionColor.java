/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionColor.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key.option

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

package clavicom.gui.edition.key;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;
import clavicom.tools.TColorPanel;

public class UIPanelOptionColor extends JPanel implements MouseListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CKey key;
	TColorPanel colorPanel;
	JLabel lColor;
	TColorKeyEnum colorEnum;
	
	List<CKey> selectedKeys;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIPanelOptionColor(  )
	{
		lColor = new JLabel();
		add(lColor);
		
		colorPanel = new TColorPanel();
		colorPanel.setPreferredSize( new Dimension(30,20) );		
		add( colorPanel );
		
		colorPanel.addMouseListener( this );
	}
	
	public void setValues(List<CKey> selectedKeys, TColorKeyEnum myColorEnum)
	{
		key = null;
		this.selectedKeys = selectedKeys;
		colorEnum = myColorEnum;	
		lColor.setText(colorEnum.toString());
	}
	
	public void setValues(CKey myKey, TColorKeyEnum myColorEnum)
	{
		selectedKeys = null;
		key = myKey;
		colorEnum = myColorEnum;	
		lColor.setText(colorEnum.toString());
		
		colorPanel.setBackground( key.getColor( colorEnum ) );
	}


	public void mouseClicked(MouseEvent e)
	{
		if((selectedKeys == null) && (key != null))
			// Selection unique
			{
				Color newColor = JColorChooser.showDialog( UIPanelOptionColor.this, UIString.getUIString("LB_CHOOSE_COLOR"), key.getColor( colorEnum ) );

				if( newColor !=  null )
				{
					if( newColor != key.getColor( colorEnum ) )
					{
						// la couleur à changé
						key.setColor( newColor, colorEnum );

						colorPanel.setBackground( newColor );
					}
				}
			}
			else if ((selectedKeys != null) && (key == null))
			// Selection multiple
			{
				Color newColor = JColorChooser.showDialog( UIPanelOptionColor.this, UIString.getUIString("LB_CHOOSE_COLOR"), Color.WHITE );

				if( newColor !=  null )
				{
					for (CKey currentKey : selectedKeys)
					{
						if( newColor != currentKey.getColor( colorEnum ) )
						{
							// la couleur à changé
							currentKey.setColor( newColor, colorEnum );
						}
					}
				}
				colorPanel.setBackground( newColor );
			}
	}

	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	//----------------------------------------------------------- METHODES --//
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
