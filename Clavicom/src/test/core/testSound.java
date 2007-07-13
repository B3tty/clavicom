package test.core;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.core.profil.CSound;
import clavicom.gui.language.UIString;

public class testSound
{
	static org.jdom.Document document;

	static Element racine;

	public static void main(String[] args)
	{
		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			
			// On crée une instance de SAXBuilder
			SAXBuilder sxb = new SAXBuilder();
	
			// On crée un nouveau document JDOM avec en argument le fichier XML
			// Le parsing est terminé ;)
			document = sxb.build(new File("Ressources\\Temp\\sound.xml"));
	
			// On initialise un nouvel élément racine avec l'élément racine du
			// document.
			racine = document.getRootElement();
	
			Element eltSound = racine.getChild("sound");
			CSound sound = new CSound (eltSound);
	
			// Enregistrement de la touche
			Element racine2 = new Element("sound_généré");
	
			// On crée un nouveau Document JDOM basé sur la racine que l'on vient de
			// créer
			org.jdom.Document documentOut = new org.jdom.Document(racine2);
			racine2.addContent(sound.buildNode());
			
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(documentOut,
					new FileOutputStream("Ressources\\Temp\\sound_out.xml"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}