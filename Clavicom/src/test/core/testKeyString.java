package test.core;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.gui.language.UIString;

public class testKeyString
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
			
			// On crée une instance de SAXBuilder
			SAXBuilder sxb = new SAXBuilder();
	
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			document = sxb.build(new File("Ressources\\Temp\\key_string.xml"));
	
			// On initialise un nouvel élément racine avec l'élément racine du
			// document.
			racine = document.getRootElement();
	
			Element uneTouche = racine.getChild("keystring");
			CKeyString key1;
	
			// Construction de la touche
			key1 = new CKeyString(uneTouche);
	
			// Enregistrement de la touche
			Element racine2 = new Element("blob");
	
			// On crée un nouveau Document JDOM basé sur la racine que l'on vient de
			// créer
			org.jdom.Document documentOut = new org.jdom.Document(racine2);
			racine2.addContent(key1.buildNode(10));
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(documentOut,
					new FileOutputStream("Ressources\\Temp\\key_string_out.xml"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}