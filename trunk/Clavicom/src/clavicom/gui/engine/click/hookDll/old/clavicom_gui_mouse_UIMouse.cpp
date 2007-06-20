

#include <windows.h>
#include "clavicom_gui_mouse_UIMouse.h"

HHOOK hHook = NULL;
JNIEnv *environement = NULL;
jobject object = NULL;
HINSTANCE hExe; // Handle de notre exécutable

LRESULT CALLBACK HookProc(int nCode,WPARAM wParam,LPARAM lParam)
{
			
			
	if( (environement != NULL) && (object != NULL) )
	{
		if  ((nCode == HC_ACTION) && (wParam == WM_LBUTTONUP) )
		{
			// notifier la méthode Java
			jclass cls = environement->GetObjectClass(object);
			jmethodID mid;
 
			mid = environement->GetMethodID(cls, "Callback", "()V");
			if (mid == NULL)
			{
				return CallNextHookEx(hHook, nCode, wParam, lParam);
			}

			environement->CallVoidMethod(object, mid);
			
			
			
		}
		// si c'est un clique gauche (bas ou haut)
		if  ((nCode == HC_ACTION) && 
				( (wParam == WM_LBUTTONDOWN) ||
				  (wParam == WM_LBUTTONUP) ) )
		{
			return 1;
		}
	}
	// Renvoi des messages au sytème pour permettre d'autres hooks
	return CallNextHookEx(hHook, nCode, wParam, lParam);
	
}


JNIEXPORT void JNICALL Java_clavicom_gui_mouse_UIMouse_InitMouseHook
  (JNIEnv * env, jobject obj)
{
	environement = env;
	object = obj;
	hExe = GetModuleHandle(0);
    hHook = SetWindowsHookEx( WH_MOUSE_LL, (HOOKPROC) HookProc, hExe, 0);
	
	MSG message;
    while ( GetMessage(&message,NULL,0,0) )
    {
        TranslateMessage( &message );
		DispatchMessage( &message );
    }
}



JNIEXPORT void JNICALL Java_clavicom_gui_mouse_UIMouse_FinishMouseHook
  (JNIEnv *, jobject)
{
	if ( hHook != NULL )
	{
		UnhookWindowsHookEx(hHook);
		hHook == NULL;
	}
}