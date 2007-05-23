package test;

import clavicom.gui.language.UIString;

public class testUIString
{
	public static void main(String[] args)
	{
		try
		{	
				UIString.LoadUIStringFile("C:\\Developpement Java\\Clavicom\\src\\clavicom\\Ressources\\Application\\LanguagesUI\\francais.clg");
				
				System.out.println(UIString.getUIString("EX_KEYCLAVICOM_INVALID_ACTION_1"));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}