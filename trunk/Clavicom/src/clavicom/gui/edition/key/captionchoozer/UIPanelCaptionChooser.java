/*-----------------------------------------------------------------------------+

			Filename			: UIPanelCaptionChooser.java
			Creation date		: 20 juin 07
		
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

package clavicom.gui.edition.key.captionchoozer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import clavicom.gui.language.UIString;
import clavicom.gui.listener.UICaptionChooserListener;
import clavicom.tools.TImageUtils;

public class UIPanelCaptionChooser extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//		
	protected JLabel labelText;				// Label pour la caption de texte
	protected JLabel labelImage;			// Label pour la caption image
	
	protected JTextField captionText;		// Contient la caption de texte
	protected UIImageCombo comboImages;		// Contient la liste des images
	
	protected boolean isImage;				// Indique si l'état de selection
	
	protected EventListenerList listenerList;	// Listeners sur le changement de composant
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIPanelCaptionChooser(String directory)
	{
		// Création des composants
		labelText = new JLabel(UIString.getUIString("LB_EDITION_OPTION_KEY_CAPTION_TEXT"));
		labelImage = new JLabel(UIString.getUIString("LB_EDITION_OPTION_KEY_CAPTION_IMAGE"));
		
		captionText = new JTextField("");
		comboImages = new UIImageCombo(directory,getFilenames(directory));

		listenerList = new EventListenerList();
		
		// Ajout des actions
		captionText.addKeyListener(new KeyListener()
										{
											public void keyPressed(KeyEvent arg0)
											{
												
											}

											public void keyReleased(KeyEvent arg0)
											{
												// TODO Auto-generated method stub
												fireCaptionChanged(captionText.getText(), false);
											}

											public void keyTyped(KeyEvent arg0)
											{
												
											}
										});
		
		comboImages.addActionListener(new AbstractAction()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				fireCaptionChanged(comboImages.getSelectedFilename(), true);											
			}
			
		});
		
		isImage = false;
		
		// Ajout des composants au panel
		add(labelText);
		add(captionText);
		add(labelImage);
		add(comboImages);
		
		// Placement des composants
		GridBagLayout gbLayoutMain = new GridBagLayout();
		setLayout(gbLayoutMain);
		
		// Contraintes du panel du label de texte
		GridBagConstraints gbConstLabelText = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            30,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.NORTHWEST,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.HORIZONTAL,	// Manière de rétrécir le composant
	            new Insets(0, 10, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(labelText, gbConstLabelText);
		
		// Contraintes du panel du texte edit
		GridBagConstraints gbConstTextEdit = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            70,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.NORTHWEST,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.HORIZONTAL,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(captionText, gbConstTextEdit);
		
		// Contraintes du label d'image
		GridBagConstraints gbConstLabelImage = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            30,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.NORTHWEST,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.HORIZONTAL,	// Manière de rétrécir le composant
	            new Insets(0, 10, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(labelImage, gbConstLabelImage);
		
		// Contraintes du panel du panel d'image
		GridBagConstraints gbConstComboImage = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            70,							// Taille horizontale relative
	            50,							// Taille verticale relative
	            GridBagConstraints.NORTHWEST,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.HORIZONTAL,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(comboImages, gbConstComboImage);
		
		// On grise selon le choix de l'utilisateur
		setIsImage(isImage);
	}
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 * Permet de masquer certains composants
	 */
	public void setIsImage(boolean isImage)
	{
		// Changement de l'état
		this.isImage = isImage;
		
		// On grise/dégrise les composants
		if (isImage == true)
		{
			// On grise les options de choix de texte
			captionText.setEnabled(false);
			labelText.setEnabled(false);
			
			// On dégrise les options de choix d'image
			comboImages.setEnabled(true);
			labelImage.setEnabled(true);
		}
		else
		{
			// On grise les options de choix de texte
			comboImages.setEnabled(false);
			labelImage.setEnabled(false);
			
			// On dégrise les options de choix d'image
			captionText.setEnabled(true);
			labelText.setEnabled(true);	
		}
	}
	
	public void setCaptionText(String text)
	{
		captionText.setText(text);
	}
	
	/**
	 * Indique si on est en selection d'image ou non
	 */
	public boolean isImage()
	{
		return isImage;
	}
	
	/**
	 * Retourne la caption
	 * @return
	 */
	public String getCaption()
	{
		if(isImage == true)
		{
			// On retourne le nom de fichier selectionné
			return comboImages.getSelectedFilename();
		}
		else
		{
			// On retourne le texte de l'edit
			return captionText.getText();
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Retourne toutes les images d'un répertoire
	 */
	protected List<String> getFilenames(String directory)
	{
		File directoryFile = new File(directory);
		
		List<String> fileNames = new ArrayList<String>();
		if (directoryFile.isDirectory())
		{
			File[] list = directoryFile.listFiles();
			if(list != null)
			{
				 for ( File currentFile : list)
				 {
					 if (TImageUtils.hasImageExtension(currentFile) == true)
					 {
						 // On n'ajoute que si on peut créer une image
						 if (new ImageIcon(directory + currentFile) != null)
						 {
							 fileNames.add(currentFile.getName());
						 }
					 }
				 }
			}
		}
		
		return fileNames;
	}
	
	
	public UIImageCombo getComboImages()
	{
		return comboImages;
	}

	//	 Listener ==============================================
	public void addCaptionChooserListener(UICaptionChooserListener l)
	{
		this.listenerList.add(UICaptionChooserListener.class, l);
	}

	public void removeCaptionChooserListener(UICaptionChooserListener l)
	{
		this.listenerList.remove(UICaptionChooserListener.class, l);
	}

	protected void fireCaptionChanged(String myCaption, boolean myIsImage)
	{
		UICaptionChooserListener[] listeners = (UICaptionChooserListener[]) listenerList
				.getListeners(UICaptionChooserListener.class);
		for ( int i = listeners.length - 1; i >= 0; i-- )
		{
			listeners[i].captionChanged(this,myCaption, myIsImage);
		}
	}
}
