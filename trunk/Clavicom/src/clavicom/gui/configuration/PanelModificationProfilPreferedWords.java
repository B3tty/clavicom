/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationPreferedWords.java
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import clavicom.core.engine.dictionary.CDictionaryWord;
import clavicom.core.profil.CPreferedWords;
import clavicom.gui.language.UIString;

public class PanelModificationProfilPreferedWords extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CPreferedWords preferedWord;
	JTable table;
	WordsTableModel wordTableModel;
	JPanel panelGlobal;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilPreferedWords(CPreferedWords myPreferedWord)
	{
		super( UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS") );
		
		preferedWord = myPreferedWord;
		
		LoadComponents();
		
		initValues();
	}
	
	private void LoadComponents()
	{
		setLayout( new BorderLayout() );
		panelGlobal = new JPanel( new BorderLayout() );

		// boutton
		JPanel buttons = new JPanel();
		
		JButton buttonDeleteSelected = new JButton( UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS_DELETE_SELECTED_WORDS") );
		buttonDeleteSelected.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// on supprime les lignes séléctionées
				int[] selectedRows = table.getSelectedRows();
				
				
				
				// on doit les supprimer du plus grand au plus petit
				Arrays.sort( selectedRows );
				for( int  i = (selectedRows.length-1) ; i>=0 ; --i )
				{
					wordTableModel.remove( selectedRows[i] );
				}
				
			}
		});
		buttons.add( buttonDeleteSelected );
		
		JButton buttonDeleteAll = new JButton( UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS_DELETE_ALL_WORDS") );
		buttonDeleteAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				wordTableModel.removeAll( );
			}
		});
		buttons.add( buttonDeleteAll );
		
		panelGlobal.add( buttons, BorderLayout.SOUTH );
		
		add(panelGlobal, BorderLayout.CENTER);
	}


	//----------------------------------------------------------- METHODES --//
	
	public void initValues()
	{
		// table
		List<CDictionaryWord> preferedWordTab = new ArrayList<CDictionaryWord>();
		for( int i = 0 ; i < preferedWord.getSize() ; ++i)
		{
			CDictionaryWord dicWord = preferedWord.getPreferedWord( i );
			if( dicWord != null )
			{
				preferedWordTab.add( dicWord );
			}
			
		}
		
		wordTableModel = new WordsTableModel( preferedWordTab );
		table = new JTable( wordTableModel );
		
		JScrollPane panelTable = new JScrollPane( table );
		
		panelGlobal.remove( panelTable );
		panelGlobal.add( panelTable, BorderLayout.CENTER );
	}

	@Override
	public boolean validateDataEntry()
	{
		return change(true);
	}
	
	@Override
	public boolean isChanged()
	{
		return change(false);
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected boolean change( boolean saveData )
	{
		// Si les mots préférés ont changé, on les change dans le profil
		
		// si la taille de la liste à changé, c'est différant
		if( preferedWord.getSize() != wordTableModel.getRowCount() )
		{
			if( saveData )
			{
				// on vide les prefered word
				preferedWord.clearPreferedWord();
				
				// et on les re-remplit
				for( int i = 0 ; i < wordTableModel.getRowCount() ; ++i )
				{
					CDictionaryWord dictionaryWord = (CDictionaryWord)wordTableModel.getValueAt(i, 0);
					if( dictionaryWord != null )
					{
						preferedWord.addPreferedWord( dictionaryWord );
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	
	protected class WordsTableModel extends AbstractTableModel implements TableModel
	{

		List<CDictionaryWord> dictionaryWords;
		
		protected String[] columnNames = new String[] {
				UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS_COL_WORDS"), 
				UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS_COL_FREQUENCY")  };
		
		protected Class[] columnClasses = new Class[] { 
				CDictionaryWord.class, Integer.class,   };
		
		
		public WordsTableModel( List<CDictionaryWord> myDictionaryWords )
		{
			dictionaryWords = myDictionaryWords;
			fireTableDataChanged();
		}
		

		


		public Class<?> getColumnClass(int arg0)
		{
			return columnClasses[arg0];
		}

		public int getColumnCount()
		{
			return columnNames.length;
		}

		public String getColumnName(int arg0)
		{
			return columnNames[arg0];
		}

		public int getRowCount()
		{
			return dictionaryWords.size();
		}

		public Object getValueAt(int arg0, int arg1)
		{
			CDictionaryWord dicitonaryWord = dictionaryWords.get(arg0);

			if( dicitonaryWord != null )
			{
				if( arg1 == 0 )
				{
					return dicitonaryWord;
				}
				else
				{
					return dicitonaryWord.getFrequency();
				}
			}
			else
			{
				return "";
			}
		}

		public boolean isCellEditable(int arg0, int arg1)
		{
			return false;
		}


		public void setValueAt(Object arg0, int arg1, int arg2)
		{
			CDictionaryWord dicitonaryWord = dictionaryWords.get(arg1);

			if( dicitonaryWord != null )
			{
				if( arg2 == 0 )
				{
					dicitonaryWord.setWord( (String)arg0 );
				}
				else
				{
					dicitonaryWord.setFrequency( (Integer)arg0 );
				}
				fireTableDataChanged();
			}
		}
		
		public void remove( int arg )
		{
			dictionaryWords.remove( arg );
			fireTableDataChanged();
		}
		
		public void removeAll()
		{
			dictionaryWords.clear();
			fireTableDataChanged();
		}
		
	}


	
}
