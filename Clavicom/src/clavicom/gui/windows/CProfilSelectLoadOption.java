/*-----------------------------------------------------------------------------+

			Filename			: CProfilSelectLoadOption.java
			Creation date		: 11 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.windows

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

package clavicom.gui.windows;

public class CProfilSelectLoadOption
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	boolean advancedOptions;
	boolean commandSetName;
	boolean dictionaryName;
	boolean font;
	boolean framePosition;
	boolean keyboard;
	boolean keyboardColors;
	boolean LangueUIName;
	boolean navigation;
	boolean preferedWord;
	boolean shortcutSetName;
	boolean sound;
	boolean transparence;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CProfilSelectLoadOption()
	{
		advancedOptions = true;
		commandSetName = true;
		dictionaryName = true;
		font = true;
		framePosition = true;
		keyboard = true;
		keyboardColors = true;
		LangueUIName = true;
		navigation = true;
		preferedWord = true;
		shortcutSetName = true;
		sound = true;
		transparence = true;
	}

	public boolean isAdvancedOptions()
	{
		return advancedOptions;
	}

	public void setAdvancedOptions(boolean advancedOptions)
	{
		this.advancedOptions = advancedOptions;
	}

	public boolean isCommandSetName()
	{
		return commandSetName;
	}

	public void setCommandSetName(boolean commandSetName)
	{
		this.commandSetName = commandSetName;
	}

	public boolean isDictionaryName()
	{
		return dictionaryName;
	}

	public void setDictionaryName(boolean dictionaryName)
	{
		this.dictionaryName = dictionaryName;
	}

	public boolean isFont()
	{
		return font;
	}

	public void setFont(boolean font)
	{
		this.font = font;
	}

	public boolean isFramePosition()
	{
		return framePosition;
	}

	public void setFramePosition(boolean framePosition)
	{
		this.framePosition = framePosition;
	}

	public boolean isKeyboard()
	{
		return keyboard;
	}

	public void setKeyboard(boolean keyboard)
	{
		this.keyboard = keyboard;
	}

	public boolean isKeyboardColors()
	{
		return keyboardColors;
	}

	public void setKeyboardColors(boolean keyboardColors)
	{
		this.keyboardColors = keyboardColors;
	}

	public boolean isLangueUIName()
	{
		return LangueUIName;
	}

	public void setLangueUIName(boolean langueUIName)
	{
		LangueUIName = langueUIName;
	}

	public boolean isNavigation()
	{
		return navigation;
	}

	public void setNavigation(boolean navigation)
	{
		this.navigation = navigation;
	}

	public boolean isPreferedWord()
	{
		return preferedWord;
	}

	public void setPreferedWord(boolean preferedWord)
	{
		this.preferedWord = preferedWord;
	}

	public boolean isShortcutSetName()
	{
		return shortcutSetName;
	}

	public void setShortcutSetName(boolean shortcutSetName)
	{
		this.shortcutSetName = shortcutSetName;
	}

	public boolean isSound()
	{
		return sound;
	}

	public void setSound(boolean sound)
	{
		this.sound = sound;
	}

	public boolean isTransparence()
	{
		return transparence;
	}

	public void setTransparence(boolean transparence)
	{
		this.transparence = transparence;
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
