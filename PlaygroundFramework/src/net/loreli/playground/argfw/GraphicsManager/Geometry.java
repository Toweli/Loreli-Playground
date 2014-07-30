package net.loreli.playground.argfw.GraphicsManager;

public class Geometry {
	
	/* Member variables */
	
	private int m_iDataSize;
	private int m_iBufferSize;
	private float[] m_faData;
	private VAO m_pVAO = null;
	private VBO m_pVBO = null;
	
	/* Constructor(s) */

	public Geometry(float[] p_vecCoords)
	{
		m_faData = p_vecCoords;
		m_iDataSize = m_faData.length;
		m_iBufferSize = m_faData.length * 4;
		
		m_pVAO = new VAO();
		m_pVBO = new VBO(m_iBufferSize, p_vecCoords).Init();
	}
	
	/* Public */
	
	/* Private */
	
	/* Setter / Getter */
	
	public VAO GetVAO()
	{
		return m_pVAO;
	}
	
	public VBO GetVBO()
	{
		return m_pVBO;
	}
	
	public int GetDataSize()
	{
		return m_iDataSize;
	}
}
