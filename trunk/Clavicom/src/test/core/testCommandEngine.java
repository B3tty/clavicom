package test.core;

import org.jdom.Element;

import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class testCommandEngine
{
	static org.jdom.Document document;

	static Element racine;

	public static void main(String[] args)
	{
		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			
			// Chemins
			String input = "Ressources\\Temp\\profil.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			CKeyboard keyboard = profil.getKeyboard();
			
			// Chargement du commandEngine
			CLevelEngine levelEngine = new CLevelEngine( keyboard );
			CCommandEngine commandEngine = new CCommandEngine( keyboard, levelEngine );
			
			// on simule l'appuis sur une touche
			CKeyGroup group = keyboard.getKeyGroup( 0 );
			CKeyList list = group.GetkeyList( 0 );
			CKeyCharacter keyCharacter = (CKeyCharacter)list.GetKeyboardKey( 0 );
			
			// simulation du click
			keyCharacter.Click();

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}