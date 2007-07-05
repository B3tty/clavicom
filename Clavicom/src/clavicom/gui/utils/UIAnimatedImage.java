/*-----------------------------------------------------------------------------+

			Filename			: UIAnimatedImage.java
			Creation date		: 5 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.utils

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

package clavicom.gui.utils;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JPanel;

import org.w3c.dom.Node;

public class UIAnimatedImage extends JPanel
{
	private BufferedImage[] images;

	private Point[] offsets;

	private BufferedImage composite;

	public UIAnimatedImage(String filePath) throws IOException
	{
		File inFile = new File(filePath);
		
		Iterator readers = ImageIO.getImageReadersBySuffix("gif");
		
		if (!readers.hasNext())
			throw new IOException("no gif readers");
		
		ImageReader reader = (ImageReader) readers.next();
		
		if (readers.hasNext())
			System.out.println("(there were oither readers)");
		
		ImageInputStream iis = ImageIO.createImageInputStream(inFile);
		reader.setInput(iis);
		
		final int numImages = reader.getNumImages(true);
		images = new BufferedImage[numImages];
		offsets = new Point[numImages];

		for (int i = 0; i < numImages; ++i)
		{
			images[i] = reader.read(i);
			offsets[i] = getPixelOffsets(reader, i);
		}
		composite = new BufferedImage(images[0].getWidth(), images[0]
				.getHeight(), BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2 = composite.createGraphics();
		g2.drawImage(images[0], offsets[0].x, offsets[0].y, null);
		new javax.swing.Timer(100, new ActionListener()
		{
			int j = 1;

			public void actionPerformed(ActionEvent evt)
			{
				g2.drawImage(images[j], offsets[j].x, offsets[j].y, null);
				j = (j + 1) % numImages;
				repaint();
			}
		}).start();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Insets insets = getInsets();
		g.drawImage(composite, insets.left, insets.top, null);
	}

	public Dimension getPreferredSize()
	{
		Insets insets = getInsets();
		int w = insets.left + insets.right + composite.getWidth();
		int h = insets.top + insets.bottom + composite.getHeight();
		return new Dimension(w, h);
	}

	static Point getPixelOffsets(ImageReader reader, int num)
			throws IOException
	{
		IIOMetadata meta = reader.getImageMetadata(num);
		Point point = new Point(-1, -1);
		Node root = meta.getAsTree("javax_imageio_1.0");
		for (Node c = root.getFirstChild(); c != null; c = c.getNextSibling())
		{
			String name = c.getNodeName();
			if ("Dimension".equals(name))
			{
				for (c = c.getFirstChild(); c != null; c = c.getNextSibling())
				{
					name = c.getNodeName();
					if ("HorizontalPixelOffset".equals(name))
						point.x = getValueAttribute(c);
					else if ("VerticalPixelOffset".equals(name))
						point.y = getValueAttribute(c);
				}
				return point;
			}
		}
		return point;
	}

	static int getValueAttribute(Node node)
	{
		try
		{
			return Integer.parseInt(node.getAttributes().getNamedItem("value")
					.getNodeValue());
		}
		catch (NumberFormatException e)
		{
			return -2;
		}
	}
}

