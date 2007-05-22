/*-----------------------------------------------------------------------------+

			Filename			: CKeyEventTools.java
			Creation date		: 22 mai 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

import java.awt.event.KeyEvent;

public class CKeyEventTools
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//
	/**
	 * Fait le mapping entre une chaine de caractère et le KeyEvent
	 * correspondant
	 * @param stringKeyEvent
	 * @return
	 */
	static public int GetKeyEvent( String stringKeyEvent )
	{
		if( stringKeyEvent.equals( "KEY_FIRST" ) )
		{
			return KeyEvent.KEY_FIRST;
		}else if( stringKeyEvent.equals( "KEY_LAST" ) )
		{
			return KeyEvent.KEY_LAST;
		}else if( stringKeyEvent.equals( "KEY_LOCATION_LEFT" ) )
		{
			return KeyEvent.KEY_LOCATION_LEFT;
		}else if( stringKeyEvent.equals( "KEY_LOCATION_NUMPAD" ) )
		{
			return KeyEvent.KEY_LOCATION_NUMPAD;
		}else if( stringKeyEvent.equals( "KEY_LOCATION_RIGHT" ) )
		{
			return KeyEvent.KEY_LOCATION_RIGHT;
		}else if( stringKeyEvent.equals( "KEY_LOCATION_STANDARD" ) )
		{
			return KeyEvent.KEY_LOCATION_STANDARD;
		}else if( stringKeyEvent.equals( "KEY_LOCATION_UNKNOWN" ) )
		{
			return KeyEvent.KEY_LOCATION_UNKNOWN;
		}else if( stringKeyEvent.equals( "KEY_PRESSED" ) )
		{
			return KeyEvent.KEY_PRESSED;
		}else if( stringKeyEvent.equals( "KEY_RELEASED" ) )
		{
			return KeyEvent.KEY_RELEASED;
		}else if( stringKeyEvent.equals( "KEY_TYPED" ) )
		{
			return KeyEvent.KEY_TYPED;
		}else if( stringKeyEvent.equals( "VK_0" ) )
		{
			return KeyEvent.VK_0;
		}else if( stringKeyEvent.equals( "VK_1" ) )
		{
			return KeyEvent.VK_1;
		}else if( stringKeyEvent.equals( "VK_2" ) )
		{
			return KeyEvent.VK_2;
		}else if( stringKeyEvent.equals( "VK_3" ) )
		{
			return KeyEvent.VK_3;
		}else if( stringKeyEvent.equals( "VK_4" ) )
		{
			return KeyEvent.VK_4;
		}else if( stringKeyEvent.equals( "VK_5" ) )
		{
			return KeyEvent.VK_5;
		}else if( stringKeyEvent.equals( "VK_6" ) )
		{
			return KeyEvent.VK_6;
		}else if( stringKeyEvent.equals( "VK_7" ) )
		{
			return KeyEvent.VK_7;
		}else if( stringKeyEvent.equals( "VK_8" ) )
		{
			return KeyEvent.VK_8;
		}else if( stringKeyEvent.equals( "VK_9" ) )
		{
			return KeyEvent.VK_9;
		}else if( stringKeyEvent.equals( "VK_A" ) )
		{
			return KeyEvent.VK_A;
		}else if( stringKeyEvent.equals( "VK_ACCEPT" ) )
		{
			return KeyEvent.VK_ACCEPT;
		}else if( stringKeyEvent.equals( "VK_ADD" ) )
		{
			return KeyEvent.VK_ADD;
		}else if( stringKeyEvent.equals( "VK_AGAIN" ) )
		{
			return KeyEvent.VK_AGAIN;
		}else if( stringKeyEvent.equals( "VK_ALL_CANDIDATES" ) )
		{
			return KeyEvent.VK_ALL_CANDIDATES;
		}else if( stringKeyEvent.equals( "VK_ALPHANUMERIC" ) )
		{
			return KeyEvent.VK_ALPHANUMERIC;
		}else if( stringKeyEvent.equals( "VK_ALT" ) )
		{
			return KeyEvent.VK_ALT;
		}else if( stringKeyEvent.equals( "VK_ALT_GRAPH" ) )
		{
			return KeyEvent.VK_ALT_GRAPH;
		}else if( stringKeyEvent.equals( "VK_AMPERSAND" ) )
		{
			return KeyEvent.VK_AMPERSAND;
		}else if( stringKeyEvent.equals( "VK_ASTERISK" ) )
		{
			return KeyEvent.VK_ASTERISK;
		}else if( stringKeyEvent.equals( "VK_AT" ) )
		{
			return KeyEvent.VK_AT;
		}else if( stringKeyEvent.equals( "VK_B" ) )
		{
			return KeyEvent.VK_B;
		}else if( stringKeyEvent.equals( "VK_BACK_QUOTE" ) )
		{
			return KeyEvent.VK_BACK_QUOTE;
		}else if( stringKeyEvent.equals( "VK_BACK_SLASH" ) )
		{
			return KeyEvent.VK_BACK_SLASH;
		}else if( stringKeyEvent.equals( "VK_BACK_SPACE" ) )
		{
			return KeyEvent.VK_BACK_SPACE;
		}else if( stringKeyEvent.equals( "VK_BRACELEFT" ) )
		{
			return KeyEvent.VK_BRACELEFT;
		}else if( stringKeyEvent.equals( "VK_BRACERIGHT" ) )
		{
			return KeyEvent.VK_BRACERIGHT;
		}else if( stringKeyEvent.equals( "VK_C" ) )
		{
			return KeyEvent.VK_C;
		}else if( stringKeyEvent.equals( "VK_CANCEL" ) )
		{
			return KeyEvent.VK_CANCEL;
		}else if( stringKeyEvent.equals( "VK_CAPS_LOCK" ) )
		{
			return KeyEvent.VK_CAPS_LOCK;
		}else if( stringKeyEvent.equals( "VK_CIRCUMFLEX" ) )
		{
			return KeyEvent.VK_CIRCUMFLEX;
		}else if( stringKeyEvent.equals( "VK_CLEAR" ) )
		{
			return KeyEvent.VK_CLEAR;
		}else if( stringKeyEvent.equals( "VK_CLOSE_BRACKET" ) )
		{
			return KeyEvent.VK_CLOSE_BRACKET;
		}else if( stringKeyEvent.equals( "VK_CODE_INPUT" ) )
		{
			return KeyEvent.VK_CODE_INPUT;
		}else if( stringKeyEvent.equals( "VK_COLON" ) )
		{
			return KeyEvent.VK_COLON;
		}else if( stringKeyEvent.equals( "VK_COMMA" ) )
		{
			return KeyEvent.VK_COMMA;
		}else if( stringKeyEvent.equals( "VK_COMPOSE" ) )
		{
			return KeyEvent.VK_COMPOSE;
		}else if( stringKeyEvent.equals( "VK_CONTROL" ) )
		{
			return KeyEvent.VK_CONTROL;
		}else if( stringKeyEvent.equals( "VK_CONVERT" ) )
		{
			return KeyEvent.VK_CONVERT;
		}else if( stringKeyEvent.equals( "VK_COPY" ) )
		{
			return KeyEvent.VK_COPY;
		}else if( stringKeyEvent.equals( "VK_CUT" ) )
		{
			return KeyEvent.VK_CUT;
		}else if( stringKeyEvent.equals( "VK_D" ) )
		{
			return KeyEvent.VK_D;
		}else if( stringKeyEvent.equals( "VK_DEAD_ABOVEDOT" ) )
		{
			return KeyEvent.VK_DEAD_ABOVEDOT;
		}else if( stringKeyEvent.equals( "VK_DEAD_ABOVERING" ) )
		{
			return KeyEvent.VK_DEAD_ABOVERING;
		}else if( stringKeyEvent.equals( "VK_DEAD_ACUTE" ) )
		{
			return KeyEvent.VK_DEAD_ACUTE;
		}else if( stringKeyEvent.equals( "VK_DEAD_BREVE" ) )
		{
			return KeyEvent.VK_DEAD_BREVE;
		}else if( stringKeyEvent.equals( "VK_DEAD_CARON" ) )
		{
			return KeyEvent.VK_DEAD_CARON;
		}else if( stringKeyEvent.equals( "VK_DEAD_CEDILLA" ) )
		{
			return KeyEvent.VK_DEAD_CEDILLA;
		}else if( stringKeyEvent.equals( "VK_DEAD_CIRCUMFLEX" ) )
		{
			return KeyEvent.VK_DEAD_CIRCUMFLEX;
		}else if( stringKeyEvent.equals( "VK_DEAD_DIAERESIS" ) )
		{
			return KeyEvent.VK_DEAD_DIAERESIS;
		}else if( stringKeyEvent.equals( "VK_DEAD_DOUBLEACUTE" ) )
		{
			return KeyEvent.VK_DEAD_DOUBLEACUTE;
		}else if( stringKeyEvent.equals( "VK_DEAD_GRAVE" ) )
		{
			return KeyEvent.VK_DEAD_GRAVE;
		}else if( stringKeyEvent.equals( "VK_DEAD_IOTA" ) )
		{
			return KeyEvent.VK_DEAD_IOTA;
		}else if( stringKeyEvent.equals( "VK_DEAD_MACRON" ) )
		{
			return KeyEvent.VK_DEAD_MACRON;
		}else if( stringKeyEvent.equals( "VK_DEAD_OGONEK" ) )
		{
			return KeyEvent.VK_DEAD_OGONEK;
		}else if( stringKeyEvent.equals( "VK_DEAD_SEMIVOICED_SOUND" ) )
		{
			return KeyEvent.VK_DEAD_SEMIVOICED_SOUND;
		}else if( stringKeyEvent.equals( "VK_DEAD_TILDE" ) )
		{
			return KeyEvent.VK_DEAD_TILDE;
		}else if( stringKeyEvent.equals( "VK_DEAD_VOICED_SOUND" ) )
		{
			return KeyEvent.VK_DEAD_VOICED_SOUND;
		}else if( stringKeyEvent.equals( "VK_DECIMAL" ) )
		{
			return KeyEvent.VK_DECIMAL;
		}else if( stringKeyEvent.equals( "VK_DELETE" ) )
		{
			return KeyEvent.VK_DELETE;
		}else if( stringKeyEvent.equals( "VK_DIVIDE" ) )
		{
			return KeyEvent.VK_DIVIDE;
		}else if( stringKeyEvent.equals( "VK_DOLLAR" ) )
		{
			return KeyEvent.VK_DOLLAR;
		}else if( stringKeyEvent.equals( "VK_DOWN" ) )
		{
			return KeyEvent.VK_DOWN;
		}else if( stringKeyEvent.equals( "VK_E" ) )
		{
			return KeyEvent.VK_E;
		}else if( stringKeyEvent.equals( "VK_END" ) )
		{
			return KeyEvent.VK_END;
		}else if( stringKeyEvent.equals( "VK_ENTER" ) )
		{
			return KeyEvent.VK_ENTER;
		}else if( stringKeyEvent.equals( "VK_EQUALS" ) )
		{
			return KeyEvent.VK_EQUALS;
		}else if( stringKeyEvent.equals( "VK_ESCAPE" ) )
		{
			return KeyEvent.VK_ESCAPE;
		}else if( stringKeyEvent.equals( "VK_EURO_SIGN" ) )
		{
			return KeyEvent.VK_EURO_SIGN;
		}else if( stringKeyEvent.equals( "VK_EXCLAMATION_MARK" ) )
		{
			return KeyEvent.VK_EXCLAMATION_MARK;
		}else if( stringKeyEvent.equals( "VK_F" ) )
		{
			return KeyEvent.VK_F;
		}else if( stringKeyEvent.equals( "VK_F1" ) )
		{
			return KeyEvent.VK_F1;
		}else if( stringKeyEvent.equals( "VK_F2" ) )
		{
			return KeyEvent.VK_F2;
		}else if( stringKeyEvent.equals( "VK_F3" ) )
		{
			return KeyEvent.VK_F3;
		}else if( stringKeyEvent.equals( "VK_F4" ) )
		{
			return KeyEvent.VK_F4;
		}else if( stringKeyEvent.equals( "VK_F5" ) )
		{
			return KeyEvent.VK_F5;
		}else if( stringKeyEvent.equals( "VK_F6" ) )
		{
			return KeyEvent.VK_F6;
		}else if( stringKeyEvent.equals( "VK_F7" ) )
		{
			return KeyEvent.VK_F7;
		}else if( stringKeyEvent.equals( "VK_F8" ) )
		{
			return KeyEvent.VK_F8;
		}else if( stringKeyEvent.equals( "VK_F9" ) )
		{
			return KeyEvent.VK_F9;
		}else if( stringKeyEvent.equals( "VK_F10" ) )
		{
			return KeyEvent.VK_F10;
		}else if( stringKeyEvent.equals( "VK_F11" ) )
		{
			return KeyEvent.VK_F11;
		}else if( stringKeyEvent.equals( "VK_F12" ) )
		{
			return KeyEvent.VK_F12;
		}else if( stringKeyEvent.equals( "VK_F13" ) )
		{
			return KeyEvent.VK_F13;
		}else if( stringKeyEvent.equals( "VK_F14" ) )
		{
			return KeyEvent.VK_F14;
		}else if( stringKeyEvent.equals( "VK_F15" ) )
		{
			return KeyEvent.VK_F15;
		}else if( stringKeyEvent.equals( "VK_F16" ) )
		{
			return KeyEvent.VK_F16;
		}else if( stringKeyEvent.equals( "VK_F17" ) )
		{
			return KeyEvent.VK_F17;
		}else if( stringKeyEvent.equals( "VK_F18" ) )
		{
			return KeyEvent.VK_F18;
		}else if( stringKeyEvent.equals( "VK_F19" ) )
		{
			return KeyEvent.VK_F19;
		}else if( stringKeyEvent.equals( "VK_F20" ) )
		{
			return KeyEvent.VK_F20;
		}else if( stringKeyEvent.equals( "VK_F21" ) )
		{
			return KeyEvent.VK_F21;
		}else if( stringKeyEvent.equals( "VK_F23" ) )
		{
			return KeyEvent.VK_F23;
		}else if( stringKeyEvent.equals( "VK_F24" ) )
		{
			return KeyEvent.VK_F24;
		}else if( stringKeyEvent.equals( "VK_FINAL" ) )
		{
			return KeyEvent.VK_FINAL;
		}else if( stringKeyEvent.equals( "VK_FIND" ) )
		{
			return KeyEvent.VK_FIND;
		}else if( stringKeyEvent.equals( "VK_FULL_WIDTH" ) )
		{
			return KeyEvent.VK_FULL_WIDTH;
		}else if( stringKeyEvent.equals( "VK_G" ) )
		{
			return KeyEvent.VK_G;
		}else if( stringKeyEvent.equals( "VK_GREATER" ) )
		{
			return KeyEvent.VK_GREATER;
		}else if( stringKeyEvent.equals( "VK_H" ) )
		{
			return KeyEvent.VK_H;
		}else if( stringKeyEvent.equals( "VK_HALF_WIDTH" ) )
		{
			return KeyEvent.VK_HALF_WIDTH;
		}else if( stringKeyEvent.equals( "VK_HELP" ) )
		{
			return KeyEvent.VK_HELP;
		}else if( stringKeyEvent.equals( "VK_HIRAGANA" ) )
		{
			return KeyEvent.VK_HIRAGANA;
		}else if( stringKeyEvent.equals( "VK_HOME" ) )
		{
			return KeyEvent.VK_HOME;
		}else if( stringKeyEvent.equals( "VK_I" ) )
		{
			return KeyEvent.VK_I;
		}else if( stringKeyEvent.equals( "VK_INPUT_METHOD_ON_OFF" ) )
		{
			return KeyEvent.VK_INPUT_METHOD_ON_OFF;
		}else if( stringKeyEvent.equals( "VK_INSERT" ) )
		{
			return KeyEvent.VK_INSERT;
		}else if( stringKeyEvent.equals( "VK_INVERTED_EXCLAMATION_MARK" ) )
		{
			return KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
		}else if( stringKeyEvent.equals( "VK_J" ) )
		{
			return KeyEvent.VK_J;
		}else if( stringKeyEvent.equals( "VK_JAPANESE_HIRAGANA" ) )
		{
			return KeyEvent.VK_JAPANESE_HIRAGANA;
		}else if( stringKeyEvent.equals( "VK_JAPANESE_KATAKANA" ) )
		{
			return KeyEvent.VK_JAPANESE_KATAKANA;
		}else if( stringKeyEvent.equals( "KEY_LAST" ) )
		{
			return KeyEvent.VK_JAPANESE_ROMAN;
		}else if( stringKeyEvent.equals( "VK_K" ) )
		{
			return KeyEvent.VK_K;
		}else if( stringKeyEvent.equals( "VK_KANA" ) )
		{
			return KeyEvent.VK_KANA;
		}else if( stringKeyEvent.equals( "VK_KANA_LOCK" ) )
		{
			return KeyEvent.VK_KANA_LOCK;
		}else if( stringKeyEvent.equals( "VK_KANJI" ) )
		{
			return KeyEvent.VK_KANJI;
		}else if( stringKeyEvent.equals( "VK_KATAKANA" ) )
		{
			return KeyEvent.VK_KATAKANA;
		}else if( stringKeyEvent.equals( "VK_KP_DOWN" ) )
		{
			return KeyEvent.VK_KP_DOWN;
		}else if( stringKeyEvent.equals( "VK_KP_LEFT" ) )
		{
			return KeyEvent.VK_KP_LEFT;
		}else if( stringKeyEvent.equals( "VK_KP_RIGHT" ) )
		{
			return KeyEvent.VK_KP_RIGHT;
		}else if( stringKeyEvent.equals( "VK_KP_UP" ) )
		{
			return KeyEvent.VK_KP_UP;
		}else if( stringKeyEvent.equals( "VK_L" ) )
		{
			return KeyEvent.VK_L;
		}else if( stringKeyEvent.equals( "VK_LEFT" ) )
		{
			return KeyEvent.VK_LEFT;
		}else if( stringKeyEvent.equals( "VK_LEFT_PARENTHESIS" ) )
		{
			return KeyEvent.VK_LEFT_PARENTHESIS;
		}else if( stringKeyEvent.equals( "VK_LESS" ) )
		{
			return KeyEvent.VK_LESS;
		}else if( stringKeyEvent.equals( "VK_M" ) )
		{
			return KeyEvent.VK_M;
		}else if( stringKeyEvent.equals( "VK_META" ) )
		{
			return KeyEvent.VK_META;
		}else if( stringKeyEvent.equals( "VK_MINUS" ) )
		{
			return KeyEvent.VK_MINUS;
		}else if( stringKeyEvent.equals( "VK_MODECHANGE" ) )
		{
			return KeyEvent.VK_MODECHANGE;
		}else if( stringKeyEvent.equals( "VK_MULTIPLY" ) )
		{
			return KeyEvent.VK_MULTIPLY;
		}else if( stringKeyEvent.equals( "VK_N" ) )
		{
			return KeyEvent.VK_N;
		}else if( stringKeyEvent.equals( "VK_NONCONVERT" ) )
		{
			return KeyEvent.VK_NONCONVERT;
		}else if( stringKeyEvent.equals( "VK_NUM_LOCK" ) )
		{
			return KeyEvent.VK_NUM_LOCK;
		}else if( stringKeyEvent.equals( "VK_NUMBER_SIGN" ) )
		{
			return KeyEvent.VK_NUMBER_SIGN;
		}else if( stringKeyEvent.equals( "VK_NUMPAD0" ) )
		{
			return KeyEvent.VK_NUMPAD0;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD1" ) )
		{
			return KeyEvent.VK_NUMPAD1;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD2" ) )
		{
			return KeyEvent.VK_NUMPAD2;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD3" ) )
		{
			return KeyEvent.VK_NUMPAD3;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD4" ) )
		{
			return KeyEvent.VK_NUMPAD4;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD5" ) )
		{
			return KeyEvent.VK_NUMPAD5;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD6" ) )
		{
			return KeyEvent.VK_NUMPAD6;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD7" ) )
		{
			return KeyEvent.VK_NUMPAD7;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD8" ) )
		{
			return KeyEvent.VK_NUMPAD8;
		}
		else if( stringKeyEvent.equals( "VK_NUMPAD9" ) )
		{
			return KeyEvent.VK_NUMPAD9;
		}else if( stringKeyEvent.equals( "VK_O" ) )
		{
			return KeyEvent.VK_O;
		}else if( stringKeyEvent.equals( "VK_OPEN_BRACKET" ) )
		{
			return KeyEvent.VK_OPEN_BRACKET;
		}else if( stringKeyEvent.equals( "VK_P" ) )
		{
			return KeyEvent.VK_P;
		}else if( stringKeyEvent.equals( "VK_PAGE_DOWN" ) )
		{
			return KeyEvent.VK_PAGE_DOWN;
		}else if( stringKeyEvent.equals( "VK_PAGE_UP" ) )
		{
			return KeyEvent.VK_PAGE_UP;
		}else if( stringKeyEvent.equals( "VK_PASTE" ) )
		{
			return KeyEvent.VK_PASTE;
		}else if( stringKeyEvent.equals( "VK_PAUSE" ) )
		{
			return KeyEvent.VK_PAUSE;
		}else if( stringKeyEvent.equals( "VK_PERIOD" ) )
		{
			return KeyEvent.VK_PERIOD;
		}else if( stringKeyEvent.equals( "VK_PLUS" ) )
		{
			return KeyEvent.VK_PLUS;
		}else if( stringKeyEvent.equals( "VK_PREVIOUS_CANDIDATE" ) )
		{
			return KeyEvent.VK_PREVIOUS_CANDIDATE;
		}else if( stringKeyEvent.equals( "VK_PRINTSCREEN" ) )
		{
			return KeyEvent.VK_PRINTSCREEN;
		}else if( stringKeyEvent.equals( "VK_PROPS" ) )
		{
			return KeyEvent.VK_PROPS;
		}else if( stringKeyEvent.equals( "VK_Q" ) )
		{
			return KeyEvent.VK_Q;
		}else if( stringKeyEvent.equals( "VK_QUOTE" ) )
		{
			return KeyEvent.VK_QUOTE;
		}else if( stringKeyEvent.equals( "VK_QUOTEDBL" ) )
		{
			return KeyEvent.VK_QUOTEDBL;
		}else if( stringKeyEvent.equals( "VK_R" ) )
		{
			return KeyEvent.VK_R;
		}else if( stringKeyEvent.equals( "VK_RIGHT" ) )
		{
			return KeyEvent.VK_RIGHT;
		}else if( stringKeyEvent.equals( "VK_RIGHT_PARENTHESIS" ) )
		{
			return KeyEvent.VK_RIGHT_PARENTHESIS;
		}else if( stringKeyEvent.equals( "VK_ROMAN_CHARACTERS" ) )
		{
			return KeyEvent.VK_ROMAN_CHARACTERS;
		}else if( stringKeyEvent.equals( "VK_S" ) )
		{
			return KeyEvent.VK_S;
		}else if( stringKeyEvent.equals( "VK_SCROLL_LOCK" ) )
		{
			return KeyEvent.VK_SCROLL_LOCK;
		}else if( stringKeyEvent.equals( "VK_SEMICOLON" ) )
		{
			return KeyEvent.VK_SEMICOLON;
		}else if( stringKeyEvent.equals( "VK_SEPARATER" ) )
		{
			return KeyEvent.VK_SEPARATER;
		}else if( stringKeyEvent.equals( "VK_SEPARATOR" ) )
		{
			return KeyEvent.VK_SEPARATOR;
		}else if( stringKeyEvent.equals( "VK_SHIFT" ) )
		{
			return KeyEvent.VK_SHIFT;
		}else if( stringKeyEvent.equals( "VK_SLASH" ) )
		{
			return KeyEvent.VK_SLASH;
		}else if( stringKeyEvent.equals( "VK_SPACE" ) )
		{
			return KeyEvent.VK_SPACE;
		}else if( stringKeyEvent.equals( "VK_STOP" ) )
		{
			return KeyEvent.VK_STOP;
		}else if( stringKeyEvent.equals( "VK_SUBTRACT" ) )
		{
			return KeyEvent.VK_SUBTRACT;
		}else if( stringKeyEvent.equals( "VK_T" ) )
		{
			return KeyEvent.VK_T;
		}else if( stringKeyEvent.equals( "VK_TAB" ) )
		{
			return KeyEvent.VK_TAB;
		}else if( stringKeyEvent.equals( "VK_U" ) )
		{
			return KeyEvent.VK_U;
		}else if( stringKeyEvent.equals( "VK_UNDEFINED" ) )
		{
			return KeyEvent.VK_UNDEFINED;
		}else if( stringKeyEvent.equals( "VK_UNDERSCORE" ) )
		{
			return KeyEvent.VK_UNDERSCORE;
		}else if( stringKeyEvent.equals( "VK_UNDO" ) )
		{
			return KeyEvent.VK_UNDO;
		}else if( stringKeyEvent.equals( "VK_UP" ) )
		{
			return KeyEvent.VK_UP;
		}else if( stringKeyEvent.equals( "VK_V" ) )
		{
			return KeyEvent.VK_V;
		}else if( stringKeyEvent.equals( "VK_W" ) )
		{
			return KeyEvent.VK_W;
		}else if( stringKeyEvent.equals( "VK_X" ) )
		{
			return KeyEvent.VK_X;
		}else if( stringKeyEvent.equals( "VK_Y" ) )
		{
			return KeyEvent.VK_Y;
		}else if( stringKeyEvent.equals( "VK_Z" ) )
		{
			return KeyEvent.VK_Z;
		}
		
		return 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
