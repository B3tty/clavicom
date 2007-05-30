/*-----------------------------------------------------------------------------+

			Filename			: UIKey.java
			Creation date		: 29 mai 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard

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

package clavicom.gui.keyboard.key;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import clavicom.tools.TUIKeyState;

public class UIKey extends JPanel
{
		//--------------------------------------------------------- CONSTANTES --//

		//---------------------------------------------------------- ATTRIBUTS --//		
		private EventListenerList listenersList;
		private TUIKeyState currentState;
		//------------------------------------------------------ CONSTRUCTEURS --//
		
		public UIKey()
		{
			this.listenersList = new EventListenerList();
			
			// Ajout des listener sur la souris
			addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
					buttonClicked();
				}
			});
			
			addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
					buttonEntered();
			}
			});					
		}
			
		//--------------------------------------------------- METHODES PRIVEES --//
		void buttonClicked()
		{
			System.out.println("clicked");
		}
		
		void buttonEntered()
		{
			System.out.println("entered");
		}
		
		void buttonLeaved()
		{
			System.out.println("entered");
		}
		
//		----------------------------------------------------------- METHODES --//
		
		protected void paintComponent(Graphics myGraphic) {
			Graphics2D graphic2D = (Graphics2D) myGraphic;
			
			graphic2D.setRenderingHint(
											RenderingHints.KEY_ANTIALIASING,
											RenderingHints.VALUE_ANTIALIAS_ON);
			
			graphic2D.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND,
												BasicStroke.JOIN_ROUND));
					
			
			// Contour
				graphic2D.setStroke(new BasicStroke(5f));
				graphic2D.drawRect(0,0,getWidth(),getHeight());
		}
		
//			setPreferredSize(new Dimension(50, 60));
//			setSize(50, 60);
//			
//			// Ajout des listener sur la souris
//			addMouseListener(new MouseAdapter() {
//					public void mouseClicked(MouseEvent e) {
//						fireButtonClick();
//				}
//			});
//			
//			addMouseListener(new MouseAdapter() {
//				public void mouseEntered(MouseEvent e) {
//					fireButtonEntered();
//			}
//		});
//		}
//		
//		//----------------------------------------------------------- METHODES --//
//		
//		public void addButtonListener(ButtonListener listener) {
//			listenersList.add(ButtonListener.class, listener);
//		}
//		
//		public void removeButtonListener(ButtonListener listener) {
//			listenersList.remove(ButtonListener.class, listener);
//		}
//		
//		protected ButtonListener[] getButtonListeners() {
//			return listenersList.getListeners(ButtonListener.class);
//		}
//		
//		protected void fireButtonClick() {
//			Object[] listeners = listenersList.getListenerList();
//			
//			for (int i = listeners.length - 2; i >= 0; i -= 2)
//				if (listeners[i] == ButtonListener.class)
//					((ButtonListener) listeners[i + 1]).buttonClicked(this);
//		}
//		
//		protected void fireButtonEntered() 
//		{
//			Object[] listeners = listenersList.getListenerList();
//			
//			for (int i = listeners.length - 2; i >= 0; i -= 2)
//				if (listeners[i] == ButtonListener.class)
//					((ButtonListener) listeners[i + 1]).buttonEntered(this);
//		}
//		
//		
		//--------------------------------------------------- METHODES PRIVEES --//
}


