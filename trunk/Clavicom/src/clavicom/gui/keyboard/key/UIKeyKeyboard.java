/*-----------------------------------------------------------------------------+

			Filename			: UIKeyKeyboardKey.java
			Creation date		: 6 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2007) Centre ICOM'

							-------------------------

	This program is free software. You can redistribute it and/or modify it 
 	under the terms of the GNU Lesser General Public License as published by 
	the Free Software Foundation. Either version 2.1 of the License, or (at your 
    option) any later version.

	This program is distributed in the hope that it will be useful, but WITHOUT 
	ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
	FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
    more details.

+-----------------------------------------------------------------------------*/

package clavicom.gui.keyboard.key;

import java.awt.Point;

import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.tools.TPoint;


public abstract class UIKeyKeyboard extends UIKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyKeyboard()
	{
		// Appel de la mère
		super();
	}
	
	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	@Override
	protected void addListeners()
	{
		// Abonnement à la coreKey
		getCoreKey().addColorListener(this);
		
		// ATTENTION !!! On caste en CKeyKeyboard car toutes les classes fille
		// ont des fils de la CKeyBoardKey comme objet core
		if ( getCoreKey() instanceof CKeyKeyboard )
		{
			((CKeyKeyboard)getCoreKey()).addCaptionListener(this);
		}
	}
	
	@Override
	protected void onBoundsChanged()
	{		
		// Calcul des nouvelles positions
		// On caste en CKeyKeyboard
		CKeyKeyboard keyKeyboard = (CKeyKeyboard)(getCoreKey());
		
		// On récupère les points absolus
		Point ptMin = this.getLocation();
		Point ptMax = new Point(	(int)ptMin.getX() + getWidth(),
									(int)ptMin.getY() + getHeight());
		
		// Calcul des points en relatif
		TPoint tptMin = new TPoint(	(float)ptMin.getX()/(float)getParent().getWidth(),
									(float)ptMin.getY()/(float)getParent().getHeight());
		
		TPoint tptMax = new TPoint(	(float)ptMax.getX()/(float)getParent().getWidth(),
									(float)ptMax.getY()/(float)getParent().getHeight());
		
		// Affectation des nouveaux points à l'objet du noyau
		keyKeyboard.setPointMin(tptMin);
		keyKeyboard.setPointMax(tptMax);		
	}
}
