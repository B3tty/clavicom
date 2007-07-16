/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationTransparency.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

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

package clavicom.gui.configuration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clavicom.CFilePaths;
import clavicom.gui.language.UIString;
import clavicom.tools.TPicturePanel;

public class PanelModificationProfilAbout extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//


	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilAbout()
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANEL_TRANSPARENCY") );

		
		
		
		TPicturePanel ClavicomNG = new TPicturePanel( CFilePaths.getAboutPicture() );
		// labels
		JLabel ClavicomVersion = new JLabel(		UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_VERSION") );
		JLabel ConceptionAndDevBy = new JLabel(		UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_CONCEPTION") );
		JLabel TDAndGR = new JLabel(				"Thomas DEVAUX et Guillaume REBESCHE" );
		JLabel TDAndGREmail = new JLabel(			"thomas.devaux@gmail.com et guillaume.rebesche@gmail.com" );
		JLabel DevFor = new JLabel(					UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_DEV_FOR") );
		JLabel IComHI = new JLabel(					"Centre Icom (Handicap International)" );
		JLabel LEmail = new JLabel(					"Email" );
		JLabel Email = new JLabel(					"icom@handicap-icom.asso.fr" );
		JLabel LAdress = new JLabel(				UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_ADRESS") );
		JLabel Adress = new JLabel(					"18 rue de Gerland, Lyon, FRANCE");
		JLabel LWebSite = new JLabel(				UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_WEB_SITE") );
		JLabel WebSite = new JLabel(				"http://www.handicap-icom.asso.fr/" );
		JLabel bugCase = new JLabel(				UIString.getUIString("LB_CONFPROFIL_PANEL_ABOUT_CLAVICOM_BUG_CASE") );
		JLabel bugCaseContact = new JLabel(			"icom@handicap-icom.asso.fr" );
		
		// placement des labels		
		GridBagLayout gbLayoutGlobal = new GridBagLayout();
		
		setLayout( new BorderLayout () );
		JPanel panelGlobal = new JPanel( gbLayoutGlobal );

		// placement du clavicom
		ClavicomNG.setPreferredSize( new Dimension (200, 200) );
		add( ClavicomNG, BorderLayout.NORTH );
		
		// Ajout des Contraintes de ClavicomVersion
		GridBagConstraints gbConstClavicomVersion = new GridBagConstraints (	
				1,							// Numéro de colonne
	            6,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(ClavicomVersion, gbConstClavicomVersion);
		panelGlobal.add( ClavicomVersion );
		
		// Ajout des Contraintes de ConceptionAndDevBy
		GridBagConstraints gbConstConceptionAndDevBy = new GridBagConstraints (	
				0,							// Numéro de colonne
	            7,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(ConceptionAndDevBy, gbConstConceptionAndDevBy);
		panelGlobal.add( ConceptionAndDevBy );
		
		// Ajout des Contraintes de TDAndGR
		GridBagConstraints gbConstTDAndGR = new GridBagConstraints (	
				1,							// Numéro de colonne
	            7,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 0, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(TDAndGR, gbConstTDAndGR);
		panelGlobal.add( TDAndGR );
		
		// Ajout des Contraintes de TDAndGREmail
		GridBagConstraints gbConstTDAndGREmail = new GridBagConstraints (	
				1,							// Numéro de colonne
	            8,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(TDAndGREmail, gbConstTDAndGREmail);
		panelGlobal.add( TDAndGREmail );
		
		// Ajout des Contraintes de DevFor
		GridBagConstraints gbConstDevFor = new GridBagConstraints (	
				0,							// Numéro de colonne
	            9,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(DevFor, gbConstDevFor);
		panelGlobal.add( DevFor );
		
		// Ajout des Contraintes de IComHI
		GridBagConstraints gbConstIComHI = new GridBagConstraints (	
				1,							// Numéro de colonne
	            9,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(IComHI, gbConstIComHI);
		panelGlobal.add( IComHI );
		
		// Ajout des Contraintes de LAdress
		GridBagConstraints gbConstLAdress = new GridBagConstraints (	
				0,							// Numéro de colonne
	            10,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(LAdress, gbConstLAdress);
		panelGlobal.add( LAdress );
		
		// Ajout des Contraintes de LAdress
		GridBagConstraints gbConstAdress = new GridBagConstraints (	
				1,							// Numéro de colonne
	            10,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(Adress, gbConstAdress);
		panelGlobal.add( Adress );
		
		// Ajout des Contraintes de LEmail
		GridBagConstraints gbConstLEmail = new GridBagConstraints (	
				0,							// Numéro de colonne
	            11,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(LEmail, gbConstLEmail);
		panelGlobal.add( LEmail );
		
		// Ajout des Contraintes de Email
		GridBagConstraints gbConstEmail = new GridBagConstraints (	
				1,							// Numéro de colonne
	            11,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(Email, gbConstEmail);
		panelGlobal.add( Email );
		
		// Ajout des Contraintes de LWebSite
		GridBagConstraints gbConstLWebSite = new GridBagConstraints (	
				0,							// Numéro de colonne
	            12,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(LWebSite, gbConstLWebSite);
		panelGlobal.add( LWebSite );
		
		// Ajout des Contraintes de WebSite
		GridBagConstraints gbConstWebSite = new GridBagConstraints (	
				1,							// Numéro de colonne
	            12,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(WebSite, gbConstWebSite);
		panelGlobal.add( WebSite );
		
		// Ajout des Contraintes de bugCase
		GridBagConstraints gbConstbugCase = new GridBagConstraints (	
				0,							// Numéro de colonne
	            13,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(bugCase, gbConstbugCase);
		panelGlobal.add( bugCase );
		
		// Ajout des Contraintes de bugCaseContact
		GridBagConstraints gbConstbugCaseContact = new GridBagConstraints (	
				1,							// Numéro de colonne
	            13,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(10, 10,10,10),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(bugCaseContact, gbConstbugCaseContact);
		panelGlobal.add( bugCaseContact );
		
		add( panelGlobal, BorderLayout.SOUTH );
		
		
	}

	@Override
	public boolean isChanged()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateDataEntry()
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	

	//--------------------------------------------------- METHODES PRIVEES --//
}
