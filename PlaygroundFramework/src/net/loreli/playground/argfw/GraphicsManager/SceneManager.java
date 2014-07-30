package net.loreli.playground.argfw.GraphicsManager;

import android.opengl.Matrix;

public class SceneManager {
	
	/* Member variables */
	
	private Uniform m_pProjMatrix = null;
	private Uniform m_pViewMatrix = null;
	private ShaderProgram m_pDefaultShader = null;
	private ShaderProgram m_pCurrentShader = null;
	private GameCamera m_pCamera = null;
	private TransformNode m_pRootNode = null;
	float[] m_matProjMatrix = new float[16];
	float[] m_matViewMatrix = new float[16];
	float[] m_matModelMatrix = new float[16];
	
	/* Constructor(s) */
	
	public SceneManager(ShaderProgram shader)
	{
		_Init(shader);
	}
	
	public SceneManager()
	{
		_Init(ShaderRegistry.CreateSimple());
	}

	private void _Init(ShaderProgram shader)
	{
		m_pDefaultShader = shader;
		m_pCurrentShader = m_pDefaultShader;
		m_pCurrentShader.Use();
		
		m_pProjMatrix = m_pCurrentShader.GetUniform("uProjMat");
		m_pViewMatrix = m_pCurrentShader.GetUniform("uViewMat");
		m_pCamera = new GameCamera();
		m_pRootNode = new TransformNode(this, null);
		
		float[] id = new float[16];
		Matrix.setIdentityM(id, 0);
		m_pProjMatrix.Update(id);
		m_pViewMatrix.Update(id);
	}
	
	/* Public */
	
	public void Draw()
	{
		m_pCurrentShader = m_pDefaultShader;
		m_pCurrentShader.Use();
		m_matViewMatrix = m_pCamera.GetViewMatrix();
		m_pProjMatrix.Update(m_matProjMatrix);
		m_pViewMatrix.Update(m_matViewMatrix);
		
		Matrix.setIdentityM(m_matModelMatrix, 0);
		m_pRootNode.Draw(m_matModelMatrix);
	}
	
	public TransformNode CreateTransformNode(TransformNode parent)
	{
		return new TransformNode(this, parent);
	}
	
	public GeomNode CreateGeomNode(Geometry geom, TransformNode parent)
	{
		return new GeomNode(this, geom, parent);
	}
	
	/* Private */
	
	/* Setter / Getter */
	
	public TransformNode GetRoot()
	{
		return m_pRootNode;
	}
	
	public ShaderProgram GetCurrentShader()
	{
		return m_pCurrentShader;
	}
	
	public void SetShader(ShaderProgram newShader)
	{
		m_pCurrentShader = newShader;
		m_pCurrentShader.Use();
		m_pCurrentShader.GetUniform("uProjMat").Update(m_matProjMatrix);
		m_pCurrentShader.GetUniform("uViewMat").Update(m_matViewMatrix);
	}
	
	public ShaderProgram GetDefaultShader()
	{
		return m_pDefaultShader;
	}
	
	public void SetProjMat(float[] projMat)
	{
		m_matProjMatrix = projMat;
	}
	
	public GameCamera GetCamera()
	{
		return m_pCamera;
	}
}
