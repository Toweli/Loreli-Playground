	package net.loreli.playground.argfw.GraphicsManager;

import java.util.HashMap;

import net.loreli.playground.argfw.ErrorCheck;

import android.opengl.GLES20;

public class ShaderProgram {
	
	/* Member variables */

	private boolean m_bDirty = true;
	private int m_iProgram;
	private HashMap<String, Uniform> m_mapUniforms = new HashMap<String, Uniform>();
	
	/* Constructor(s) */
	
	public ShaderProgram()
	{
		m_iProgram = GLES20.glCreateProgram();
		ErrorCheck.printDebug("Shader " + m_iProgram + " created");
		ErrorCheck.OpenGL("m_iProgram = GLES20.glCreateProgram(); failed!");
	}
	
	/* Public */
	
	public void AttachShader(IShader p_oShader)
	{
		GLES20.glAttachShader(m_iProgram, p_oShader.GetShader());
		ErrorCheck.OpenGL("GLES20.glAttachShader(m_iProgram, p_oShader.getShader()); failed!");
		m_bDirty = true;
	}
	
	public void Use()
	{
		if (m_bDirty == true)
		{
			GLES20.glLinkProgram(m_iProgram);
			ErrorCheck.OpenGL("GLES20.glLinkProgram(m_iProgram); failed!");
			m_bDirty = false;
		}
		GLES20.glUseProgram(m_iProgram);
		ErrorCheck.OpenGL("GLES20.glUseProgram(" + m_iProgram + "); failed!");
	}
	
	public boolean NewUniform(String sName)
	{
		if (sName == null || m_mapUniforms.containsKey(sName))
			return false;
		
		m_mapUniforms.put(sName, new Uniform(m_iProgram, sName));
		return true;
	}
	
	/* Private */
	
	/* Setter / Getter */
	
	public int GetProgram()
	{
		return m_iProgram;
	}
	
	public Uniform GetUniform(String sName)
	{
		if (m_mapUniforms.containsKey(sName))
			return m_mapUniforms.get(sName);
		return null;
	}
}
