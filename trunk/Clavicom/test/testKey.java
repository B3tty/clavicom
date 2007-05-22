package test;

import java.io.*;
import org.jdom.*;
import org.jdom.input.*;

import clavicom.core.keyboard.key.CKeyClavicom;

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
      catch(Exception e){}

      //On initialise un nouvel élément racine avec l'élément racine du document.
      racine = document.getRootElement();
      
      Element uneTouche = racine.getChild("keyclavicom");
      CKeyClavicom key1;
      try
      {	  
    	  key1 = new CKeyClavicom(uneTouche);
      }
      catch (Exception e)
      {
    	  System.out.println(e.getMessage());
      }
      
   }
}