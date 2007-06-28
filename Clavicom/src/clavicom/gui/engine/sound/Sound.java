/*-----------------------------------------------------------------------------+

 Filename			: Sound.java
 Creation date		: 27 juin 07
 
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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class Sound
{
	SourceDataLine source;
	AudioInputStream ais;
	
	public Sound( String fileName )
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public static void readAudioFile(String fileName)
	{
		try
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(
					fileName));
			AudioFormat format = ais.getFormat();
			Info info = new Info(SourceDataLine.class, format);
			SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
			source.open(format);
			source.start();
			int read = 0;
			byte[] audioData = new byte[16384];
			while (read > -1)
			{
				read = ais.read(audioData, 0, audioData.length);
				if (read >= 0)
					source.write(audioData, 0, read);
			}
			source.drain();
			source.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}