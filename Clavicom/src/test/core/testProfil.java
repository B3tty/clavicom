package test.core;

import org.jdom.Element;

import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class testProfil
{
	static org.jdom.Document document;

	static Element racine;

	public static void main(String[] args)
	{
		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			
			// Chemins
			String input = "Ressources\\Temp\\profil.xml";
			String output = "Ressources\\Temp\\profil_out.xml";
			
			// Chargement
			CProfil.createInstance(input);
			
			// Sauvegarde
			CProfil.getInstance().SaveProfil(output);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}