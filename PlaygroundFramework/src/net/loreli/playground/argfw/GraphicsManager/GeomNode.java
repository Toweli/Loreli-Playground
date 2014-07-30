package net.loreli.playground.argfw.GraphicsManager;

import net.loreli.playground.argfw.ErrorCheck;
import android.opengl.GLES20;

public class GeomNode extends Node {
	
	/* Member variables */
	
	private SceneManager m_pSceneManager = null;
	private Geometry m_pGeometry = null;
	
	/* Constructor(s) */

	public GeomNode(SceneManager sm, Geometry p_oGeom)
	{
		m_pSceneManager = sm;
		m_pGeometry = p_oGeom;
		m_pParent = null;
	}

	public GeomNode(SceneManager sm, Geometry p_oGeom, TransformNode parent)
	{
		m_pSceneManager = sm;
		m_pGeometry = p_oGeom;
		m_pParent = parent;
		if (parent != null)
			parent.AddChild(this);
	}

	public GeomNode(SceneManager sm)
	{
		m_pSceneManager = sm;
	}
	
	/* Public */
	
	public void Draw(float[] modelMatrix)
	{
		// TODO: shader should change, right now: using always the default
		ShaderProgram shader = m_pSceneManager.GetCurrentShader();
		shader.Use();
		m_pGeometry.GetVAO().BindShader(shader.GetProgram());
		m_pGeometry.GetVBO().Bind();
		m_pGeometry.GetVAO().BindCurrentVBO();
		shader.GetUniform("uModelMat").Update(modelMatrix);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, m_pGeometry.GetDataSize() * 4 / m_pGeometry.GetVAO().GetStride());
		ErrorCheck.OpenGL("GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, m_Geometry.GetVertexCount()); failed");
	}
	
	/* Private */
	
	/* Setter / Getter */
	
	public void SetGeometry(Geometry p_oGeom)
	{
		m_pGeometry = p_oGeom;
	}
	
	public Geometry GetGeometry()
	{
		return m_pGeometry;
	}
	
}
