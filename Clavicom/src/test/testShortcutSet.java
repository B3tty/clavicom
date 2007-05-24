package test;

import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;

public class testShortcutSet
{
	public static void main(String[] args)
	{
		try
		{	
			CShortcutSet.CreateInstance("src\\clavicom\\Ressources\\Application\\ShortcutSets\\default.css");
			CShortcutSet.GetInstance().GetCommand("Copier");	
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}