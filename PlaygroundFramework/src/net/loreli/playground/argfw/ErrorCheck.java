package net.loreli.playground.argfw;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;

public class ErrorCheck {
	
	private static final boolean m_bDebug = true;
	private static Context m_pContext = null;
	
	public static void SetContext(Context context)
	{
		m_pContext = context;
	}

	public static boolean OpenGL(String errmsg)
	{
		int error = GLES20.glGetError();
		if (error != GLES20.GL_NO_ERROR) {
			System.err.println("OpenGL Error (" + error + "): " + errmsg);
			System.err.flush();
			if (m_pContext != null)
				((Activity)m_pContext).finish();
			return true;
		}
		return false;
	}
	
	public static void printDebug(String msg)
	{
		if (m_bDebug)
			System.out.println(msg);
	}
}
