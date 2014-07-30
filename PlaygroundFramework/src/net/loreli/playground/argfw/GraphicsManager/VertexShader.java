package net.loreli.playground.argfw.GraphicsManager;

import android.opengl.GLES20;

public class VertexShader extends IShader {
	
	/* Member variables */
	
	/* Constructor(s) */

	public VertexShader(String source)
	{
		super(source);
		m_iShaderType = GLES20.GL_VERTEX_SHADER;
	}
	
	/* Public */
	
	/* Private */
	
	/* Setter / Getter */
	
}
