package test.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class testDragAndDrop3 extends JPanel implements DragGestureListener,
		DragSourceListener, DropTargetListener, Transferable
{
	static final DataFlavor[] supportedFlavors = { null };

	static
	{
		try
		{
			supportedFlavors[0] = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType);
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	Object object;

	// Transferable methods.
	public Object getTransferData(DataFlavor flavor)
	{
		if ( flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType) )
			return object;
		else
			return null;
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		return supportedFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType);
	}

	// DragGestureListener method.
	public void dragGestureRecognized(DragGestureEvent ev)
	{
		ev.startDrag(null, this, this);
	}

	// DragSourceListener methods.
	public void dragDropEnd(DragSourceDropEvent ev)
	{
	}

	public void dragEnter(DragSourceDragEvent ev)
	{
	}

	public void dragExit(DragSourceEvent ev)
	{
	}

	public void dragOver(DragSourceDragEvent ev)
	{
		object = ev.getSource();
	}

	public void dropActionChanged(DragSourceDragEvent ev)
	{
	}

	// DropTargetListener methods.
	public void dragEnter(DropTargetDragEvent ev)
	{
	}

	public void dragExit(DropTargetEvent ev)
	{
	}

	public void dragOver(DropTargetDragEvent ev)
	{
		dropTargetDrag(ev);
	}

	public void dropActionChanged(DropTargetDragEvent ev)
	{
		dropTargetDrag(ev);
	}

	void dropTargetDrag(DropTargetDragEvent ev)
	{
		ev.acceptDrag(ev.getDropAction());
	}

	public void drop(DropTargetDropEvent ev)
	{
		ev.acceptDrop(ev.getDropAction());
		try
		{
			Object target = ev.getSource();
			Object source = ev.getTransferable().getTransferData(
					supportedFlavors[0]);
			Component component = ((DragSourceContext) source).getComponent();
			Container oldContainer = component.getParent();
			Container container = (Container) ((DropTarget) target)
					.getComponent();
			container.add(component);
			oldContainer.validate();
			oldContainer.repaint();
			container.validate();
			container.repaint();
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}
		ev.dropComplete(true);
	}

	public testDragAndDrop3()
	{
		// Création des composants
		Button button = new Button("Drag this button");
		Label label = new Label("Drag this label");
		Checkbox checkbox = new Checkbox("Drag this check box");
		
		// Ajout des composants sur le panel de DROITE -> SOURCE
		JPanel source = new JPanel();
		source.setLayout(new FlowLayout());
		source.add(button);
		source.add(label);
		source.setPreferredSize(new Dimension(300, 600));

		// Panel de GAUCHE -> DESTINATION
		JPanel target = new JPanel();
		target.setLayout(new FlowLayout());
		target.add(checkbox);
		target.setPreferredSize(new Dimension(300, 600));
		
		target.addMouseListener(new MouseListener()
		{

			public void mouseClicked(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent arg0)
			{
				// TODO Auto-generated method stub
				System.out.println(arg0.getPoint().toString());
			}
			
			
			
		});

		// Création du split panel (qui contient les deux)
		final JSplitPane splitPane = new JSplitPane();
		splitPane.setLeftComponent(target);
		splitPane.setRightComponent(source);

		// Création du drag source
		DragSource dragSource = new DragSource();
		new DropTarget(source,DnDConstants.ACTION_MOVE, this);
		new DropTarget(target,DnDConstants.ACTION_MOVE, this);
		
		dragSource.createDefaultDragGestureRecognizer(button,DnDConstants.ACTION_MOVE, this);
		dragSource.createDefaultDragGestureRecognizer(label,DnDConstants.ACTION_MOVE, this);
		dragSource.createDefaultDragGestureRecognizer(checkbox,DnDConstants.ACTION_MOVE, this);

		source.setBounds(0, 200, 200, 200);
		target.setBounds(220, 200, 200, 200);
		add(splitPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(600, 600));
	}

	public static void main(String[] args)
	{
		testDragAndDrop3 dm = new testDragAndDrop3();
		JFrame frame = new JFrame();

		frame.getContentPane().add(dm, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
}
