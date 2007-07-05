/*-----------------------------------------------------------------------------+

			Filename			: ThreadSound.java
			Creation date		: 5 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.engine.sound

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

package clavicom.gui.engine.sound;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class ThreadSound extends Thread
{
	private String url; 
	private Clip clip;
	
	public ThreadSound( String mySound )
	{
		url = mySound;
		
		try  
		{  

			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(url)); 
			AudioFormat format = stream.getFormat(); 
			DataLine.Info info = new DataLine.Info(
			  Clip.class, stream.getFormat(), ((int)stream.getFrameLength()*format.getFrameSize()));
			clip = (Clip) AudioSystem.getLine(info); 
			clip.open(stream); 
		} 
		catch (Exception e) {}
	}
	
	@Override
	public void run()
	{
		
	}
	
	public void play()
	{ 
		clip.setFramePosition( 0 );
		clip.start();
	}
}