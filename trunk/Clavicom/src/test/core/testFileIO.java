/*-----------------------------------------------------------------------------+

 Filename			: testDictionary.java
 Creation date		: 4 juin 07
 
 Project				: Clavicom
 Package				: test.core

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

package test.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class testFileIO
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		
		System.out.println("Début...");
		
		// Ouverture du fichier
		FileInputStream fis = new FileInputStream("Ressources\\Application\\Dictionaries\\francais.cdc");
		
		// On construit et on lit autant d'octets qu'il y a dans le fichier
		int x= fis.available();
		byte b[]= new byte[x];
		fis.read(b);
		
		// On construit la String à partir du buffer
		String content = new String(b,"UTF-8");
		
		System.out.println(content);
		System.out.println("...fin !");
	}
}
