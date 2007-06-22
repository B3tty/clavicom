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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class PanelModificationProfilFont extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CFont font;
	JList list;
	JCheckBox autoSize;
	JComboBox comboSize;
	JCheckBox autoColor;
	JButton buttonColor;
	JCheckBox shadow;
	JTextArea exempleText;
	JLabel labelSize;
	JLabel labelColor;
	
	JPanel buttonEstColor;
	JPanel comboEst;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilFont(CFont myFont)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT") );
		
		font = myFont;
		
		LoadComponents();
		
		InitComponents();
	}
	
	private void InitComponents()
	{
		if( ! font.isAutoColor() )
		{
			buttonColor.setEnabled( true );
			buttonEstColor.setEnabled(true);
		}
		else
		{
			buttonColor.setEnabled( false );
			buttonEstColor.setEnabled(false);
		}
		if( ! font.isAutoSize() )
		{
			comboSize.setEnabled( true );
			comboEst.setEnabled(true);
		}
		else
		{
			comboSize.setEnabled( false );
			comboEst.setEnabled(false);
		}
		int pourcent = Math.round( font.getHeightFactor() * 100f );
		int modulo = pourcent % 5;
		comboSize.setSelectedItem( pourcent - modulo );
		
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
	    Font fontTmp;
	    CFont selectedCFont = null;

	    for ( int i = 0 ; i < polices.length ; i++ )
	    {
	    	fontTmp = new Font( polices[i], Font.PLAIN, 12 );
	    	cFont = new CFont( fontTmp.getName() );
	    	
	    	cfonts[ i ] = cFont;
	    	
	    	// on recherche la cFont séléctionné
	    	if( cFont.toString().equals( font.getFontName() ) )
	    	{
	    		selectedCFont = cFont;
	    	}
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
	    exemplePanelBis.add( new JLabel( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_LB_EXEMPLE") )  );
	    exemplePanel.add(exemplePanelBis, BorderLayout.NORTH );
	    
	    exempleText = new JTextArea( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_EXEMPLE") );
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
		
	    GridLayout gridL = new GridLayout(3,2);
	    gridL.setHgap(5);
	    gridL.setVgap(5);
	    JPanel panelOption = new JPanel( gridL );
	    
	    JPanel panelHaut = new JPanel( new BorderLayout() );
	    
	    autoSize = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_AUTOSIZE"), font.isAutoSize() );
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
	    panelHaut.add( autoSize, BorderLayout.CENTER );
	    
	    comboEst = new JPanel( new BorderLayout() );
	    labelSize = new JLabel( "    " + UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_SIZE") + " : ");
	    comboEst.add( labelSize, BorderLayout.CENTER );
	    comboSize = new JComboBox(new Object[]{0,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100});
	    comboEst.add(comboSize, BorderLayout.EAST);
	    panelHaut.add( comboEst, BorderLayout.EAST );

	    
	    JPanel panelCentre = new JPanel( new BorderLayout() );
	    
	    autoColor = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_AUTOCOLOR"), font.isAutoColor() );
	    autoColor.addChangeListener(new ChangeListener()
	    {
	    	public void stateChanged(ChangeEvent arg0)
	    	{
	    		if( autoColor.isSelected() )
	    		{
	    			buttonColor.setEnabled(false);
	    			labelColor.setEnabled(false);
	    		}
	    		else
	    		{
	    			buttonColor.setEnabled(true);
	    			labelColor.setEnabled(true);
	    		}
	    	}
	    });
	    panelCentre.add( autoColor, BorderLayout.CENTER );
	    
	    buttonEstColor = new JPanel( new BorderLayout() );
	    labelColor = new JLabel( "    " + UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_COLOR")+ " : ");
	    buttonEstColor.add( labelColor, BorderLayout.CENTER );
	    buttonColor = new JButton();
	    buttonColor.setBackground( font.getFontColor().getColor() );
	    buttonColor.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Color newColor = JColorChooser.showDialog( null, UIString.getUIString("LB_CHOOSE_COLOR"), buttonColor.getBackground() );
				
				if( newColor != null )
				{
					if( newColor != buttonColor.getBackground() )
					{
						buttonColor.setBackground( newColor );
					}
				}
			}
		});
	    buttonEstColor.add(buttonColor, BorderLayout.EAST);



	    shadow = new JCheckBox( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT_SHADOW"), font.isShadow() );


	    
	    panelOption.add(autoSize);
	    panelOption.add(comboEst);
	    panelOption.add(autoColor);
	    panelOption.add(buttonEstColor);
	    panelOption.add(shadow);  
	    
	    add( panelOption, BorderLayout.SOUTH  );
	    
	    // séléction de la police du profil
	    if( selectedCFont != null )
	    {
	    	list.setSelectedValue(selectedCFont, true);
	    }
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
						font.setFontName( selectedFont.getFontName() );
					
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
					float heightFactor = (float)(size/100);
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
		Color color = buttonColor.getBackground();
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
		
		
		
		return retour;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}

