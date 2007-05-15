package fenetres;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.Timer;

public class SlidingWindow extends JWindow implements MouseListener {

	JLabel jlbHelloWorld;
	Timer timer;
	SlidingWindow ceci;
	
	boolean reduced = false;

	public static void main(String args[]) {
		new SlidingWindow();
	}

	SlidingWindow() {
		jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		/* la fenêtre prend sa taille... */

		this.setSize(screenSize.width, 250);

		this.setLocation(0, screenSize.height - this.getHeight());
		setVisible(true);

		addMouseListener(this);
		ceci = this;
		reduced = false;
	}

	/** * MOUSE LISTENER METHODS ** */
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) 
	{
		reduced = false;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// jlbHelloWorld.setText("test");
		// Cache de la fenetre
		timer = new Timer(10, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
				if(reduced == false)
				{
					if(ceci.getLocation().y < (screenSize.getHeight() - 50))
					{
						System.out.println("encore...");
						jlbHelloWorld.setText(ceci.getLocation().toString() + " -- " + screenSize.toString());
						ceci.setLocation(ceci.getLocation().x,ceci.getLocation().y+50);
					}
					else
					{
						timer.stop();
						reduced = true;
					}
				}
				else
				{
					if(ceci.getLocation().y > (screenSize.getHeight() - ceci.getHeight()))
					{
						System.out.println("encore...");
						jlbHelloWorld.setText(ceci.getLocation().toString() + " -- " + screenSize.toString());
						ceci.setLocation(ceci.getLocation().x,ceci.getLocation().y-50);
					}
					else
					{
						ceci.setLocation(ceci.getLocation().x, (int)screenSize.getHeight() - ceci.getHeight());
						timer.stop();
						reduced = false;
					}					
				}
			}
		});
		timer.start();
	}

	public void mouseExited(MouseEvent e) 
	{
	}
}
