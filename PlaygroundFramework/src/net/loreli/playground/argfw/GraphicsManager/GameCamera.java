package net.loreli.playground.argfw.GraphicsManager;

import android.opengl.Matrix;

public class GameCamera {
	
	/* Member variables */
	
	private float[] m_vecCamPos = new float[] {0, 0, 0};
	private float[] m_vecLookAt = new float[] {0, 0, -1};
	private float[] m_vecUp = new float[] {0, 1, 0};
	private float[] m_matViewMatrix = new float[16];
	
	/* Constructor(s) */

	public GameCamera()
	{
		_Init(new float[] {0, 0, 0}, new float[] {0, 0, -1});
	}
	
	public GameCamera(float[] camPos)
	{
		_Init(camPos, new float[] {0, 0, -1});
	}
	
	public GameCamera(float[] camPos, float[] lookAt)
	{
		_Init(camPos, lookAt);
	}
	
	/* Public */
	
	/* Private */
	
	private void _Init(float[] camPos, float[] lookAt)
	{
		m_vecCamPos = camPos;
		m_vecLookAt = lookAt;
	}
	
	/* Setter / Getter */
	
	public void SetCamPos(final float[] camPos)
	{
		m_vecCamPos = camPos;
	}
	
	public void SetLookAt(final float[] lookAt)
	{
		m_vecLookAt = new float[] {
			lookAt[0] + m_vecCamPos[0],
			lookAt[1] + m_vecCamPos[1],
			lookAt[2] + m_vecCamPos[2]
		};
	}
	
	public void SetUp(final float[] up)
	{
		m_vecUp = up;
	}
	
	public float[] GetViewMatrix()
	{
		Matrix.setLookAtM(m_matViewMatrix, 0,
				m_vecCamPos[0], m_vecCamPos[1], m_vecCamPos[2],
				m_vecLookAt[0], m_vecLookAt[1], m_vecLookAt[2],
				m_vecUp[0], m_vecUp[1], m_vecUp[2]);
		return m_matViewMatrix;
	}
	
}
