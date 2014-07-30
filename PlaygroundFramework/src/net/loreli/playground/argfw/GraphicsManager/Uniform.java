package net.loreli.playground.argfw.GraphicsManager;

import net.loreli.playground.argfw.ErrorCheck;
import android.opengl.GLES20;

public class Uniform {
	
	/* Member variables */
	
	private int m_iShaderProgram;
	private String m_strName;
	
	/* Constructor(s) */

	public Uniform(int p_iShaderProgram, String p_sName)
	{
		m_iShaderProgram = p_iShaderProgram;
		m_strName = p_sName;
	}
	
	/* Public */
	
	public void Update(float[] p_param)
	{
		int mLocation = GLES20.glGetUniformLocation(m_iShaderProgram, m_strName);
		ErrorCheck.OpenGL("int mLocation = GLES20.glGetUniformLocation(" + m_iShaderProgram + ", " + m_strName + "); failed");
		
		switch(p_param.length)
		{
			case 4:
			{
				GLES20.glUniform4fv(mLocation, 1, p_param, 0);
				ErrorCheck.OpenGL("GLES20.glUniform4fv(mLocation, 1, p_param, 0); failed");
				break;
			}
			case 16:
			{
				GLES20.glUniformMatrix4fv(mLocation, 1, false, p_param, 0);
				ErrorCheck.OpenGL("GLES20.glUniformMatrix4fv(mLocation, 1, false, p_param, 0); failed");
				break;
			}
			default:
				ErrorCheck.printDebug("Updating Uniform failed!");
		}
	}
	
	public void Update(int p_param)
	{
		int mLocation = GLES20.glGetUniformLocation(m_iShaderProgram, m_strName);
		ErrorCheck.OpenGL("int mLocation = GLES20.glGetUniformLocation(" + m_iShaderProgram + ", " + m_strName + "); failed");
		
		GLES20.glUniform1i(mLocation, p_param);
		ErrorCheck.OpenGL("GLES20.glUniform4fv(mLocation, 1, p_param, 0); failed");
	}
	
	/* Private */
	
	/* Setter / Getter */
}
