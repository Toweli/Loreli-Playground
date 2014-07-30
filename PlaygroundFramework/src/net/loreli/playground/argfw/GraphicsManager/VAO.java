package net.loreli.playground.argfw.GraphicsManager;

import java.util.HashMap;
import java.util.Map;

import net.loreli.playground.argfw.ErrorCheck;

import android.opengl.GLES20;

public class VAO {
	
	/* Member variables */
	
	public final int FACE_TYPE_TRIANGLES = 3;

	private int m_iCurrentShader = 0;
	private int m_iStride = 0;
	private HashMap<String, Attribute> m_vecAttributes = new HashMap<String, Attribute>();
	
	/* Constructor(s) */
	
	/* Public */
	
	/**
	 * Update attribute handlers using the given shader.
	 */
	public void BindShader(int p_iShaderProgram)
	{
		m_iCurrentShader = p_iShaderProgram;
		
		// Find attribute location in current shader
		for (Map.Entry<String, Attribute> current : m_vecAttributes.entrySet())
		{
			current.getValue().handle = GLES20.glGetAttribLocation(m_iCurrentShader, current.getValue().name);
			ErrorCheck.OpenGL("GLES20.glGetAttribLocation(" + m_iCurrentShader + ", " + current.getValue().name + "); failed");
		}
	}

	/**
	 * Call after binding the appropriate VBO.
	 */
	public void BindCurrentVBO()
	{
		for (Map.Entry<String, Attribute> current : m_vecAttributes.entrySet())
		{
			Attribute att = current.getValue();
			
			if (att.active)
			{
				GLES20.glVertexAttribPointer(att.handle, att.length, att.type, false, m_iStride, att.offset);
				ErrorCheck.OpenGL("GLES20.glVertexAttribPointer(" + att.handle + ", " + att.length + ", " + att.type + ", false, " + m_iStride + ", " + att.offset + "); [" + att.name + "] failed");
				GLES20.glEnableVertexAttribArray(att.handle);
				ErrorCheck.OpenGL("GLES20.glEnableVertexAttribArray(att.handle); [" + att.name + "] failed");
			}
			else
			{
				if (att.handle > 0)
				{
					GLES20.glDisableVertexAttribArray(att.handle);
					ErrorCheck.OpenGL("GLES20.glDisableVertexAttribArray(" + att.handle + "); [" + att.name + "] failed");
				}
			}
		}
	}

	public void NewAttribute(final String sName, final int piType, final int piLength)
	{
		NewAttribute(sName, piType, piLength, true);
	}

	public void NewAttribute(final String sName, final int piType, final int piLength, final boolean pbActive)
	{
		Attribute newatt = new Attribute();
		newatt.name = sName;
		newatt.type = piType;
		newatt.length = piLength;
		newatt.active = pbActive;
		newatt.offset = m_iStride;
		m_vecAttributes.put(sName, newatt);
		switch (piType)
		{
			case GLES20.GL_FLOAT:
			{
				m_iStride += 4 * piLength;
				break;
			}
			case GLES20.GL_BYTE:
			{
				m_iStride += 1 * piLength;
				break;
			}
			default:
		}
	}
	
	public void Activate(String sName)
	{
		m_vecAttributes.get(sName).active = true;
	}
	
	public void Deactivate(String sName)
	{
		m_vecAttributes.get(sName).active = false;
	}
	
	/* Private */
	
	/* Setter / Getter */
	
	public int GetStride()
	{
		return m_iStride;
	}
	
	/* Sub-classes */
	
	class Attribute {
		public String name;
		public int handle;
		public int type;
		public int length;
		public int offset;
		public boolean active = true;
	}
}
