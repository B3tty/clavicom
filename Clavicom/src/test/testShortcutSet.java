package test;

import clavicom.core.keyboard.command.shortcutset.CShortcutSet;

public class testShortcutSet
{
	public static void main(String[] args)
	{
		try
		{	
			CShortcutSet shortcutSet = new CShortcutSet("C:\\Developpement Java\\Clavicom\\src\\clavicom\\Ressources\\Application\\ShortcutSets\\default.css");
			shortcutSet.GetCommand("Copier");	
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}