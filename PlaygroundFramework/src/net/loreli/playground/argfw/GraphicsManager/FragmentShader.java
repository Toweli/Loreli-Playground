package net.loreli.playground.argfw.GraphicsManager;

import net.loreli.playground.argfw.GraphicsManager.IShader;

import android.opengl.GLES20;

public class FragmentShader extends IShader {
	
	/* Member variables */
	
	/* Constructor(s) */

	public FragmentShader(String source)
	{
		super(source);
		m_iShaderType = GLES20.GL_FRAGMENT_SHADER;
	}
	
	/* Public */
	
	/* Private */
	
	/* Setter / Getter */

}
