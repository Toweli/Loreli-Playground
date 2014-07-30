package net.loreli.playground.argfw.Environment;

import net.loreli.playground.argfw.ErrorCheck;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;

public class MobileLocation {
	
	/* Member variables */
	
	private LocationManager m_pLocationManager = null;
	private MobileLocationImpl m_pLocImpl = null;
	private int m_iIntervall;
	
	/* Constructor(s) */
	
	public MobileLocation(Context context)
	{
		_Init(context, 5000);
	}
	
	public MobileLocation(Context context, int ms)
	{
		_Init(context, ms);
	}
	
	/* Public */
	
	public void onResume()
	{
		m_pLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, m_iIntervall, 0, m_pLocImpl);
	}
	
	public void onPause()
	{
		m_pLocationManager.removeUpdates(m_pLocImpl);
	}
	
	/* Private */
	
	private void _Init(Context context, int ms)
	{
		m_iIntervall = ms;
		m_pLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		if (m_pLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
			m_pLocationManager.getProvider(LocationManager.GPS_PROVIDER);
			m_pLocImpl = new MobileLocationImpl();
			m_pLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, m_iIntervall, 0, m_pLocImpl);
			ErrorCheck.printDebug("GPS accessed!");
		}
		else
			ErrorCheck.printDebug("GPS not accessable!");
	}
	
	/* Setter / Getter */
	
	public double[] GetLocation()
	{
		if (m_pLocImpl == null)
			return new double[] {0.0, 0.0};
		else
			return m_pLocImpl.getPosition();
	}
	
	public float[] GetMeterPerLatLon()
	{
		float retVal[],
			tmpResults[] = new float[3],
			meterPerLat,
			meterPerLon;
		double[] curPos = m_pLocImpl.getPosition();

		Location.distanceBetween(curPos[0], curPos[1], curPos[0]+1, curPos[1], tmpResults);
		meterPerLat = tmpResults[0];
		Location.distanceBetween(curPos[0], curPos[1], curPos[0], curPos[1]+1, tmpResults);
		meterPerLon = tmpResults[0];
		
		retVal = new float[] {meterPerLat, meterPerLon};
		
		return retVal;
	}
	
	/* Sub-classes */
	
	class MobileLocationImpl implements LocationListener {

		private double mLatitude = 50.782346;//0.0;
		private double mLongitude = 6.076351;//0.0;
		
		public double[] getPosition()
		{
			return new double[] {mLatitude, mLongitude};
		}
		
		@Override
		public void onLocationChanged(Location location)
		{
			System.out.println("Got a location!");
			mLatitude = location.getLatitude();
			mLongitude = location.getLongitude();
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			// TODO Auto-generated method stub
			System.out.println("onProviderDisabled");
		}

		@Override
		public void onProviderEnabled(String provider)
		{
			// TODO Auto-generated method stub
			System.out.println("onProviderEnabled");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			// TODO Auto-generated method stub
			System.out.println("onStatusChanged");
		}
	}
}
