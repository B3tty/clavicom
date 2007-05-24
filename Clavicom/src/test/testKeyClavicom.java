package test;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.gui.language.UIString;

public class testKeyClavicom
{
	static org.jdom.Document document;

	static Element racine;

	public static void main(String[] args)
	{
		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("src\\clavicom\\Ressources\\Application\\LanguagesUI\\francais.clg");
			CShortcutSet.CreateInstance("src\\clavicom\\Ressources\\Application\\ShortcutSets\\default.css");
			
			// On crée une instance de SAXBuilder
			SAXBuilder sxb = new SAXBuilder();
	
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			document = sxb.build(new File("src\\clavicom\\Ressources\\Temp\\key_clavicom.xml"));
	
			// On initialise un nouvel élément racine avec l'élément racine du
			// document.
			racine = document.getRootElement();
	
			Element uneTouche = racine.getChild("keyclavicom");
			CKeyClavicom key1;
	
			// Construction de la touche
			key1 = new CKeyClavicom(uneTouche);
			
			
	
			// Enregistrement de la touche
			Element racine2 = new Element("salut");
	
			// On crée un nouveau Document JDOM basé sur la racine que l'on vient de
			// créer
			org.jdom.Document documentOut = new org.jdom.Document(racine2);
			racine2.addContent(key1.buildNode(99));
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(documentOut,
					new FileOutputStream("src\\clavicom\\Ressources\\Temp\\key_clavicom_out.xml"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}