/*-----------------------------------------------------------------------------+

			Filename			: CMessageEngine.java
			Creation date		: 12 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.message

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

package clavicom.core.message;

import javax.swing.event.EventListenerList;

import clavicom.core.listener.CMessageListener;

public class CMessageEngine
{
	//--------------------------------------------------------- CONSTANTES --//
	private static CMessageEngine instance;
	
	//---------------------------------------------------------- VARIABLES --//	
    // un seul objet pour tous les types d'écouteurs
	private final EventListenerList listeners = new EventListenerList();
    
	//------------------------------------------------------ CONSTRUCTEURS --//
    private CMessageEngine(){}
	
	//----------------------------------------------------------- METHODES --//	
	//----------------------------------------------------------------
	// Acces
	//----------------------------------------------------------------
    /**
     * ATTENTION !!!
     * Cette méthode doit IMPERATIVEMENT ETRE APPELLEE UNE FOIS AVANT 
     * TOUTE UTILISATION DES METHODES DE LA CLASSE
     */
    public static void createInstance()
    {
    	instance = new CMessageEngine();
    }
    
	//----------------------------------------------------------------
	// Méthodes d'accès
	//----------------------------------------------------------------
	public static void newFatalError(String message)
	{
		instance.fireNewFatalError(message, null);
	}
	
	public static void newFatalError(String message,String detail)
	{
		instance.fireNewFatalError(message, detail);
	}
	
	public static void newError(String message)
	{
		instance.fireNewError(message, null);
	}
	
	public static void newError(String message,String detail)
	{
		instance.fireNewError(message, detail);
	}
	
	public static void newWarning(String message)
	{
		instance.fireNewWarning(message, null);
	}

	public static void newWarning(String message,String detail)
	{
		instance.fireNewError(message, detail);
	}
	
	public static void newInfo(String message)
	{
		instance.fireNewInfo(message, null);
	}

	public static void newInfo(String message,String detail)
	{
		instance.fireNewError(message, detail);
	}
	
	//----------------------------------------------------------------
	// Listener
	//----------------------------------------------------------------
	public static void addNewMessageListener(CMessageListener listener) 
	{
		instance.listeners.add(CMessageListener.class, listener);
    }
    
    public static void removeNewMessageListener(CMessageListener listener) 
    {
    	instance.listeners.remove(CMessageListener.class, listener);
    }
    
    public static CMessageListener[] getNewMessageListeners() 
    {
        return instance.listeners.getListeners(CMessageListener.class);
    }

	//--------------------------------------------------- METHODES PRIVEES --//
	//----------------------------------------------------------------
	// Listener
	//----------------------------------------------------------------   
    private void fireNewFatalError(String newMessage, String detail) 
    {
	    for ( CMessageListener listener : getNewMessageListeners() )
		{
			listener.onNewFatalError(newMessage, detail);
		}
    }
 
    private void fireNewError(String newMessage, String detail) 
    {
	    for ( CMessageListener listener : getNewMessageListeners() )
		{
			listener.onNewError(newMessage, detail);
		}
    }
    
    private void fireNewWarning(String newMessage, String detail)  
    {
	    for ( CMessageListener listener : getNewMessageListeners() )
		{
			listener.onNewWarning(newMessage, detail);
		}
    }
    
    private void fireNewInfo(String newMessage, String detail) 
    {
	    for ( CMessageListener listener : getNewMessageListeners() )
		{
			listener.onNewInfo(newMessage, detail);
		}
    }
}
