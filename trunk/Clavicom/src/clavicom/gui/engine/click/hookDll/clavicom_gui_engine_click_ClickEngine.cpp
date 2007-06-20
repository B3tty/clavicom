

#include <windows.h>
#include "clavicom_gui_engine_click_ClickEngine.h"

HHOOK hHook = NULL;
JNIEnv *environement = NULL;
jobject object = NULL;
HINSTANCE hExe;
WPARAM click = WM_LBUTTONUP;
BOOL inhibit = FALSE;

LRESULT CALLBACK HookProc(int nCode,WPARAM wParam,LPARAM lParam)
{
	if( inhibit == TRUE )
	{
		return CallNextHookEx(hHook, nCode, wParam, lParam);
	}
			
			
	if( (environement != NULL) && (object != NULL) )
	{
		if  ((nCode == HC_ACTION) && (wParam == click) )
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
		// si le cliques est celui demandé, on ne le renvoi pas
		if  (nCode == HC_ACTION)
		{
			if( (click == WM_LBUTTONDOWN) || (click == WM_LBUTTONUP) )
			{
				if( (wParam == WM_LBUTTONDOWN) ||
					(wParam == WM_LBUTTONUP))
				{
					return 1;
				}
			}
			
			if( (click == WM_RBUTTONDOWN) || (click == WM_RBUTTONUP) )
			{
				if( (wParam == WM_RBUTTONDOWN) ||
					(wParam == WM_RBUTTONUP))
				{
					return 1;
				}
			}
		
		}
	}
	// Renvoi des messages au sytème pour permettre d'autres hooks
	return CallNextHookEx(hHook, nCode, wParam, lParam);
	
}


JNIEXPORT void JNICALL Java_clavicom_gui_engine_click_ClickEngine_InitMouseHook
  (JNIEnv * env, jobject obj, jint typeClick)
{
	environement = env;
	object = obj;
	hExe = GetModuleHandle(0);
    hHook = SetWindowsHookEx( WH_MOUSE_LL, (HOOKPROC) HookProc, hExe, 0);
	
	switch( typeClick )
	{
		case 0:
			click = WM_LBUTTONUP;
			break;
		case 1:
			click = WM_LBUTTONDOWN;
			break;
		case 2:
			click = WM_RBUTTONDOWN;
			break;
		case 3:
			click = WM_RBUTTONUP;
			break;
		default:
			click = WM_LBUTTONUP;
			break;
	}
	
	MSG message;
    while ( GetMessage(&message,NULL,0,0) )
    {
        TranslateMessage( &message );
		DispatchMessage( &message );
    }
}



JNIEXPORT void JNICALL Java_clavicom_gui_engine_click_ClickEngine_FinishMouseHook
  (JNIEnv * env, jobject obj)
{
	if ( hHook != NULL )
	{
		UnhookWindowsHookEx(hHook);
		hHook == NULL;
	}
}

JNIEXPORT void JNICALL Java_clavicom_gui_engine_click_ClickEngine_InhibitMouseHook
  (JNIEnv * env, jobject obj, jboolean myInhibit)
{
	inhibit = myInhibit;
}
