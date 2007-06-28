package test.core;

import org.jdom.Element;
import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class TestLastWordEngine
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
//			CLevelEngine levelEngine = new CLevelEngine( keyboard );
//			new CLastWordEngine( keyboard, levelEngine );
			
			// on simule l'appuis sur une touche
			CKeyGroup group = keyboard.getKeyGroup( 0 );
			CKeyList list = group.getkeyList( 0 );
			CKeyLastWord keyLastWord = (CKeyLastWord)list.getKeyKeyboard( 4 );
			
			CKey key = (CKey)list.getKeyKeyboard( 0 );
			CKey key2 = (CKey)list.getKeyKeyboard( 1 );
			
			// simulation du click
			key.Click();
			key2.Click();
			
			System.out.println( keyLastWord.GetStringCommand() );

		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}