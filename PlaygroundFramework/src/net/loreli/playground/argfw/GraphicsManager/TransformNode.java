package net.loreli.playground.argfw.GraphicsManager;

import java.util.Vector;

import android.opengl.Matrix;

public class TransformNode extends Node {
	
	/* Member variables */

	private Vector<INode> m_vecChildren = new Vector<INode>();
	private ShaderProgram m_pShader = null;
	private SceneManager m_pSceneManager = null;
	
	private float[] m_matTranslation = new float[16];
	private float[] m_matRotation = new float[16];
	private float[] m_matScale = new float[16];
	
	/* Constructor(s) */
	
	public TransformNode(SceneManager sm, TransformNode parent)
	{
		m_pSceneManager = sm;
		m_pParent = parent;
		if (parent != null)
			parent.AddChild(this);
		
		Matrix.setIdentityM(m_matTranslation, 0);
		Matrix.setIdentityM(m_matRotation, 0);
		Matrix.setIdentityM(m_matScale, 0);
	}
	
	/* Public */
	
	@Override
	public void Draw(float[] modelMatrix)
	{
		if (m_vecChildren.isEmpty())
			return;
		if (m_pShader != null)
			m_pSceneManager.SetShader(m_pShader);
		
		float[] newModelMatrix = new float[16];
		Matrix.multiplyMM(newModelMatrix, 0, modelMatrix, 0, GetTransformation(), 0);
		for (INode it : m_vecChildren)
		{
			it.Draw(newModelMatrix);
		}
	}
	
	public void Translate(final float fX, final float fY, final float fZ)
	{
		Matrix.translateM(m_matTranslation, 0, fX, fY, fZ);
	}
	
	public void Rotate(final float angle, final float fX, final float fY, final float fZ)
	{
		Matrix.rotateM(m_matRotation, 0, angle, fX, fY, fZ);
	}
	
	public void Scale(final float fScale)
	{
		Matrix.scaleM(m_matScale, 0, fScale, fScale, fScale);
	}
	
	public void Scale(final float fX, final float fY, final float fZ)
	{
		Matrix.scaleM(m_matScale, 0, fX, fY, fZ);
	}
	
	public void AddChild(INode child)
	{
		m_vecChildren.add(child);
	}
	
	public void DisconnectChild(INode child)
	{
		m_vecChildren.remove(child);
	}
	
	/* Private */
	
	/* Setter / Getter */

	public void SetTranslation(final float fX, final float fY, final float fZ)
	{
		Matrix.setIdentityM(m_matTranslation, 0);
		Matrix.translateM(m_matTranslation, 0, fX, fY, fZ);
	}
	
	public float[] GetTransformation()
	{
		float[] result = new float[16];
		Matrix.setIdentityM(result, 0);
		Matrix.multiplyMM(result, 0, m_matRotation, 0, m_matScale, 0);
		Matrix.multiplyMM(result, 0, m_matTranslation, 0, result, 0);
		return result;
	}

	public void SetRotation(final float angle, final float fX, final float fY, final float fZ)
	{
		Matrix.setRotateM(m_matRotation, 0, angle, fX, fY, fZ);
	}
	
	public void SetScale(final float fScale)
	{
		Matrix.setIdentityM(m_matScale, 0);
		Scale(fScale);
	}
	
	public void SetScale(final float fX, final float fY, final float fZ)
	{
		Matrix.setIdentityM(m_matScale, 0);
		Scale(fX, fY, fZ);
	}
	
	public void SetShader(ShaderProgram shader)
	{
		m_pShader = shader;
	}
}
