package net.loreli.playground.argfw.Environment;

public class GpsToGameCoords {
	
	/* Member variables */

	private MobileLocation m_pMobLoc;
	private double[] m_fGameOrigin;
	private boolean m_bReady = false;
	private float[] m_fMeterPerLatLon;
	
	/* Constructor(s) */
	
	public GpsToGameCoords(MobileLocation mobLoc)
	{
		m_pMobLoc = mobLoc;
		m_bReady = false;
	}
	
	/* Public */
	
	public boolean IsReady()
	{
		return m_bReady;
	}
	
	public double[] GetGameCoords(double[] gpsCoord)
	{
		if (m_bReady == false)
		{
			m_fGameOrigin = m_pMobLoc.GetLocation();
			if (m_fGameOrigin[0] == 0 && m_fGameOrigin[1] == 0)
				return new double[] {0, 0, -10};
			else
			{
				m_bReady = true;
				m_fMeterPerLatLon = m_pMobLoc.GetMeterPerLatLon();
			}
		}
		
		double x, y;

		x = (m_fGameOrigin[0] - gpsCoord[0]) * m_fMeterPerLatLon[0];
		y = (m_fGameOrigin[1] - gpsCoord[1]) * m_fMeterPerLatLon[1];
		
		return new double[] {-y, -x, 0};
	}
	
}
