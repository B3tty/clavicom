package test;

import clavicom.core.key.keyboard.command.shortcutSet.CShortcutSet;

public class testShortcutSet
{
	public static void main(String[] args)
	{
		try
		{	
			CShortcutSet.CreateInstance("C:\\Developpement Java\\Clavicom\\src\\clavicom\\Ressources\\Application\\ShortcutSets\\default.css");
			CShortcutSet.GetCommand("Copier");	
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}