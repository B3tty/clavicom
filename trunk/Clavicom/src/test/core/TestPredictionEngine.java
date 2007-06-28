package test.core;

import org.jdom.Element;
import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class TestPredictionEngine
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
			
			CDictionary.createInstance( profil.getDictionnaryName(), profil.getPreferedWords() );
			
			// Chargement du commandEngine
//			CLevelEngine levelEngine = new CLevelEngine( keyboard );
//			new CPredictionEngine( keyboard, levelEngine, profil.getPreferedWords() );
			
			// on simule l'appuis sur une touche
			CKeyGroup group = keyboard.getKeyGroup( 0 );
			CKeyList list = group.getkeyList( 0 );
			CKeyPrediction keyLastWord1 = (CKeyPrediction)list.getKeyKeyboard( 5 );
			CKeyPrediction keyLastWord2 = (CKeyPrediction)list.getKeyKeyboard( 6 );
			
			CKey key = (CKey)list.getKeyKeyboard( 0 );
			CKey key2 = (CKey)list.getKeyKeyboard( 1 );
			
			// simulation du click
			key.Click();
			key2.Click();
			
			System.out.println( keyLastWord1.GetStringCommand() );
			System.out.println( keyLastWord2.GetStringCommand() );

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}