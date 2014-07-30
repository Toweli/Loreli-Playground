package net.loreli.playground.argfw.GraphicsManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import net.loreli.playground.argfw.ErrorCheck;


import android.opengl.GLES20;

public class VBO {
	
	/* Member variables */
	
	private int[] m_aiVBOid = new int[1];
	private final int m_iBufferSize;
	private final FloatBuffer m_oBuffer;
	
	/* Constructor(s) */

	public VBO(int p_iBufferSize, float[] p_fData)
	{
		m_iBufferSize = p_iBufferSize;
		m_oBuffer = ByteBuffer
				.allocateDirect(p_iBufferSize)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		
		m_oBuffer.put(p_fData)
				.position(0);
	}
	
	/* Public */
	
	public VBO Init()
	{
		GLES20.glGenBuffers(1, m_aiVBOid, 0);
		ErrorCheck.OpenGL("GLES20.glGenBuffers(1, m_VBOid, 0); failed");
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_aiVBOid[0]);
		ErrorCheck.OpenGL("GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_VBOid[0]); failed");
		
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, m_iBufferSize, m_oBuffer, GLES20.GL_STATIC_DRAW);
		ErrorCheck.OpenGL("GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, m_iBufferSize, m_buffer, GLES20.GL_STATIC_DRAW); failed");
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		ErrorCheck.OpenGL("GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0); failed");
		
		return this;
	}
	
	public void Bind()
	{
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_aiVBOid[0]);
		ErrorCheck.OpenGL("GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, m_VBOid[0]); failed");
	}
	
	/* Private */
	
	/* Setter / Getter */
}
