package test;

import java.awt.Color;
import java.io.File;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.core.keyboard.key.CKeyClavicom;
import clavicom.tools.TPoint;

public class testKey
{
   static org.jdom.Document document;
   static Element racine;

   public static void main(String[] args)
   {
      //On crée une instance de SAXBuilder
      SAXBuilder sxb = new SAXBuilder();
      try
      {
         //On crée un nouveau document JDOM avec en argument le fichier XML
         //Le parsing est terminé ;)
         document = sxb.build(new File("C:\\Temp\\key.xml"));
      }
      catch(Exception e){
    	  System.out.println("cxoucou");
      }

      //On initialise un nouvel élément racine avec l'élément racine du document.
      racine = document.getRootElement();
      
      Element uneTouche = racine.getChild("keyclavicom");
      CKeyClavicom key1;
      try
      {	  
    	  key1 = new CKeyClavicom(new Color(1,1,1),new TPoint(.1f,.1f),new TPoint(.1f,.1f));
      }
      catch (Exception e)
      {
    	  System.out.println(e.getMessage());
      }
      
   }
}