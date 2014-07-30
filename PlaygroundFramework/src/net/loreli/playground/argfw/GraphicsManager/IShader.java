package net.loreli.playground.argfw.GraphicsManager;

import net.loreli.playground.argfw.ErrorCheck;
import android.opengl.GLES20;

public class IShader {
	
	/* Member variables */

	public final int SHADER_ERROR_NO_TYPE = -1;
	
	protected String m_sSource;
	protected int m_iShaderType;
	
	/* Constructor(s) */
	
	public IShader(String source)
	{
		m_sSource = source;
		m_iShaderType = SHADER_ERROR_NO_TYPE;
	}
	
	/* Public */
	
	/* Private */
	
	/* Setter / Getter */
	
	public int GetShader()
	{
		if (m_iShaderType == SHADER_ERROR_NO_TYPE)
			return SHADER_ERROR_NO_TYPE;
		
		int shader = GLES20.glCreateShader(m_iShaderType);
		ErrorCheck.OpenGL("int shader = GLES20.glCreateShader(m_iShaderType); failed!");
		
		GLES20.glShaderSource(shader, m_sSource);
		ErrorCheck.OpenGL("GLES20.glShaderSource(shader, m_sSource); failed!");
		
		GLES20.glCompileShader(shader);
		ErrorCheck.OpenGL("GLES20.glCompileShader(shader); failed!");
		
		return shader;
	}
}
