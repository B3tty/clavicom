/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationFont.java
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
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import clavicom.core.profil.CFont;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorPanel;

public class PanelModificationProfilFont extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CFont font;
	JList list;
	JCheckBox autoSize;
	JComboBox comboSize;
	JCheckBox autoColor;
	TColorPanel panelColor;
	JCheckBox shadow;
	JTextArea exempleText;
	JLabel labelSize;
	JLabel labelColor;
	JCheckBox bold;
	JCheckBox italic;
	
	MouseListenerBackGroundColor lisBackGround;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilFont(CFont myFont)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT") );
		
		lisBackGround = new MouseListenerBackGroundColor();
		
		font = myFont;
		
		LoadComponents();
		
		InitComponents();
		
		initValues();
	}
	
	private void InitComponents()
	{
		if( ! font.isAutoColor() )
		{
			panelColor.removeMouseListener( lisBackGround );
			panelColor.addMouseListener( lisBackGround );
			labelColor.setEnabled(true);
		}
		else
		{
			panelColor.removeMouseListener( lisBackGround );
			labelColor.setEnabled(false);
		}
		if( ! font.isAutoSize() )
		{
			comboSize.setEnabled( true );
			labelSize.setEnabled(true);
		}
		else
		{
			comboSize.setEnabled( false );
			labelSize.setEnabled(false);
		}
		
	}
	
	public void initValues()
	{
		autoSize.setSelected( font.isAutoSize() );
		autoColor.setSelected( font.isAutoColor() );
		panelColor.setBackground( font.getFontColor().getColor() );
		shadow.setSelected( font.isShadow() );
		bold.setSelected( font.isBold() );
		italic.setSelected( font.isItalic() );
		
		int pourcent = Math.round( font.getHeightFactor() * 100f );
		int modulo = pourcent % 5;
		comboSize.setSelectedItem( pourcent - modulo );
		
		
	    CFont cFont;
	    CFont selectedCFont = null;
		for ( int i = 0 ; i < list.getModel().getSize() ; i++ )
	    {
	    	cFont = (CFont)list.getModel().getElementAt( i );
	    	
	    	// on recherche la cFont séléctionné
	    	if( cFont.getFontName().equals( font.getFontName() ) )
	    	{
	    		selectedCFont = cFont;
	    	}
	    }
		list.setSelectedValue(selectedCFont, true);
	    
	}

	private void LoadComponents()
	{
		BorderLayout borderL = new BorderLayout();
		borderL.setHgap(5);
		borderL.setVgap(5);
		setLayout(borderL);		
	    
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String [] polices = ge.getAvailableFontFamilyNames();
	    CFont[] cfonts = new CFont[ polices.length ];
	    CFont cFont;
		for ( int i = 0 ; i < polices.length ; i++ )
	    {
	    	cFont = new CFont( polices[i] );
	    	
	    	cfonts[ i ] = cFont;
	    }
		
	    list = new JList( cfonts );
	    list.addListSelectionListener(new ListSelectionListener()
	    {
	    	public void valueChanged(ListSelectionEvent arg0)
	    	{
	    		// séléction d'un élément
	    		Object object = list.getSelectedValue();
	    		if( object != null )
	    		{
	    			if( object instanceof CFont )
	    			{
	    				CFont cFont = (CFont)object;
	    				Font exempleFont = new Font( cFont.getFontName(), Font.PLAIN, 20 );
	    				exempleText.setFont( exempleFont );
	    			}
	    		}
	    	}
	    });
	    
	    JScrollPane sc = new JScrollPane( list );
	    
	    add( sc, BorderLayout.NORTH  );
	    
	    // label de test
	    JPanel exemplePanel = new JPanel( new BorderLayout() );
	    
	    JPanel exemplePanelBis = new JPanel();
	    exemplePanelBis.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_LB_EXEMPLE") )  );
	    exemplePanel.add(exemplePanelBis, BorderLayout.NORTH );
	    
	    exempleText = new JTextArea( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_EXEMPLE") );
	    Font exempleFont = new Font( font.getFontName(), Font.PLAIN, 20 );
	    exempleText.setFont( exempleFont );
	    exempleText.setLineWrap(true);
	    exempleText.setWrapStyleWord(true);
	    exempleText.setBorder( BorderFactory.createLineBorder( Color.black ) );
	    //exempleText.setForeground( font.getFontColor().getColor() );
	    exempleText.setRows( 3 );
	    
	    exemplePanel.add( exempleText, BorderLayout.CENTER  );
	    add( exemplePanel, BorderLayout.CENTER  );
	    
	    
	    // =======================================================
	    // panel des option  (autoSize, color ...)
	    // =======================================================
	    JPanel panelOption = new JPanel();
	    GridBagLayout gbLayoutGlobal = new GridBagLayout();
	    panelOption.setLayout( gbLayoutGlobal );
	    
	    autoSize = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_AUTOSIZE"), font.isAutoSize() );
	    
	    autoSize.addChangeListener(new ChangeListener()
	    {
	    	public void stateChanged(ChangeEvent arg0)
	    	{
	    		if( autoSize.isSelected() )
	    		{
	    			comboSize.setEnabled(false);
	    			labelSize.setEnabled(false);
	    		}
	    		else
	    		{
	    			comboSize.setEnabled(true);
	    			labelSize.setEnabled(true);
	    		}
	    	}
	    });
		// Ajout des Contraintes de AutoSize
		GridBagConstraints gbConstAutoSize = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(autoSize, gbConstAutoSize);
		panelOption.add( autoSize );

	    
	    labelSize = new JLabel( "    " + UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_SIZE") + " : ");
	    comboSize = new JComboBox(new Object[]{0,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,125,150,175,200,250,300});

		// Ajout des Contraintes de LabelSize
		GridBagConstraints gbConstLabelSize = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            25,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(labelSize, gbConstLabelSize);
		panelOption.add( labelSize );
		
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstComboSize = new GridBagConstraints (	
				2,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            25,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(comboSize, gbConstComboSize);
		panelOption.add( comboSize );

	    
	    autoColor = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_AUTOCOLOR"), font.isAutoColor() );
	    autoColor.addChangeListener(new ChangeListener()
	    {
	    	public void stateChanged(ChangeEvent arg0)
	    	{
	    		if( autoColor.isSelected() )
	    		{
	    			panelColor.removeMouseListener( lisBackGround );
	    			labelColor.setEnabled(false);
	    		}
	    		else
	    		{
	    			panelColor.removeMouseListener( lisBackGround );
	    			panelColor.addMouseListener( lisBackGround );
	    			labelColor.setEnabled(true);
	    		}
	    	}
	    });
		// Ajout des Contraintes de AutoColor
		GridBagConstraints gbConstAutoColor = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(autoColor, gbConstAutoColor);
		panelOption.add( autoColor );
	    
	    labelColor = new JLabel( "    " + UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_COLOR")+ " : ");
	    panelColor = new TColorPanel();
	    panelColor.removeMouseListener( lisBackGround );
	    panelColor.addMouseListener( lisBackGround );
	    
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstLabelColor = new GridBagConstraints (	
				1,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            25,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(labelColor, gbConstLabelColor);
		panelOption.add( labelColor );
		
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstButtonColor = new GridBagConstraints (	
				2,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            25,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(panelColor, gbConstButtonColor);
		panelOption.add( panelColor );



	    shadow = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_SHADOW"), font.isShadow() );
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstShadow = new GridBagConstraints (	
				0,							// Numéro de colonne
	            2,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(shadow, gbConstShadow);
		panelOption.add( shadow );
		
	    bold = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_BOLD"), font.isBold() );
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstBold = new GridBagConstraints (	
				0,							// Numéro de colonne
	            3,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(bold, gbConstBold);
		panelOption.add( bold );
		
	    italic = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANEL_FONT_ITALIC"), font.isItalic() );
		// Ajout des Contraintes de ComboSize
		GridBagConstraints gbConstItalic = new GridBagConstraints (	
				0,							// Numéro de colonne
	            4,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            50,							// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutGlobal.setConstraints(italic, gbConstItalic);
		panelOption.add( italic );
  
	    
	    add( panelOption, BorderLayout.SOUTH  );

	}

	

	//----------------------------------------------------------- METHODES --//
	
	@Override
	public boolean validateDataEntry()
	{
		return change( true );
	}

	@Override
	public boolean isChanged()
	{
		return change( false );
	}
	
	protected boolean change( boolean saveData )
	{
		boolean retour = false;
		
		// si la police a changé, on la met dans le font
		Object object = list.getSelectedValue();
		if( object != null )
		{
			if( object instanceof CFont )
			{
				CFont selectedFont = ( CFont )object;
				// si c'est pas les même
				if( font.getFontName() != selectedFont.getFontName() )
				{
					if(saveData)
					{
						font.setFontName( selectedFont.getFontName() );
					}
					
					retour = true;
				}
			}
		}
		
		
		// si la check box autosize a changé
		if( font.isAutoSize() != autoSize.isSelected() )
		{
			if(saveData)
				font.setAutoSize( autoSize.isSelected() );
			
			retour = true;
		}
		
		
		// si la taille à changé
		if( comboSize.getSelectedItem() != null )
		{
			Object object2 = comboSize.getSelectedItem();
			if( object2 != null )
			{
				if( object2 instanceof Integer )
				{
					int size = (Integer)object2;
					float heightFactor = ((float)size)/100;
					if( font.getHeightFactor() != heightFactor )
					{
						if(saveData)
							font.setHeightFactor( heightFactor );
						
						retour = true;
					}
				}
			}
		}
		
		// si la check box autocolor a changé
		if( font.isAutoColor() != autoColor.isSelected() )
		{
			if(saveData)
				font.setAutoColor( autoColor.isSelected() );
			
			retour = true;
		}
		
		
		// si la color à changé
		Color color = panelColor.getBackground();
		if( color != font.getFontColor().getColor() )
		{
			if(saveData)
				font.getFontColor().setColor( color );
			
			retour = true;
		}
		
		// si la check box shadows a changé
		if( font.isShadow() != shadow.isSelected() )
		{
			if(saveData)
				font.setShadow( shadow.isSelected() );
			
			retour = true;
		}
		
		// si la check box bold a changé
		if( font.isBold() != bold.isSelected() )
		{
			if(saveData)
				font.setBold( bold.isSelected() );
			
			retour = true;
		}
		
		// si la check box italic a changé
		if( font.isItalic() != italic.isSelected() )
		{
			if(saveData)
				font.setItalic( italic.isSelected() );
			
			retour = true;
		}
		
		
		
		return retour;
	}

	
	
	
	
	
	
	class MouseListenerBackGroundColor extends MouseAdapter
	{

		public void mouseClicked(MouseEvent e)
		{
			
			Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), panelColor.getBackground() );
			if( newColor != null )
			{
				if( newColor != panelColor.getBackground() )
				{
					panelColor.setBackground( newColor );
				}
			}
		}

	}
}

