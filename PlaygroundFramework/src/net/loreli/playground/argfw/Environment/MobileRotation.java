package net.loreli.playground.argfw.Environment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Provide mobile phone's rotation, where Y points to the north pole, Z to the sky, and X=YxZ.
 * @author Somebody
 *
 */
public class MobileRotation {
	
	/* Member variables */
	
	private SensorManager mSensorManager;
	private RotationImpl mRotationImplementation;
	
	private float[] mRotationQuaternion = new float[4];
	private float[] mRotationMatrix = new float[16];
	
	/* Constructor(s) */
	
	/**
	 * Setting up sensors
	 * TODO: check if sensors are accessible
	 * TODO: provide a fallback method
	 * @param context
	 */
	public MobileRotation(Context context)
	{
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		
		mRotationImplementation = new RotationImpl(mSensorManager, 0);
	}
	
	public MobileRotation(Context context, int updateIntervalMs)
	{
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		
		if (mSensorManager == null)
			System.out.println("mSensorManager is null");
		
		mRotationImplementation = new RotationImpl(mSensorManager, updateIntervalMs);
	}
	
	/* Public */
	
	public void start()
	{
		onResume();
	}
	
	public void onResume()
	{
		mRotationImplementation.onResume();
	}
	
	public void onPause()
	{
		mRotationImplementation.onPause();
	}
	
	/* Private */
	
	/* Setter / Getter */
	
	public float[] getQuaternion()
	{
		mRotationQuaternion = mRotationImplementation.getQuaternion();
		return mRotationQuaternion.clone();
	}
	
	public float[] getMatrix()
	{
		getQuaternion();
        SensorManager.getRotationMatrixFromVector(mRotationMatrix, mRotationQuaternion);
        return mRotationMatrix.clone();
	}
	
	/* Sub-classes */
	
	/**
	 * Delivers best possible values
	 * @author Somebody
	 *
	 */
	class RotationImpl implements SensorEventListener {
		private Sensor mSensor;
		private float[] mRotationVector = new float[3];
		private int mUpdateInterval;
		
		long lLastUpdate = -1;
		
		public RotationImpl(SensorManager sensorManager, int updateIntervalMs)
		{
			mSensorManager = sensorManager;
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
			mUpdateInterval = updateIntervalMs * 1000;
			if (mUpdateInterval <= 0)
				mUpdateInterval = 100 * 1000;
		}
		
		@Override
		public void onSensorChanged(SensorEvent event)
		{
            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            	float alpha = 0;
            	mRotationVector[0] = (1-alpha)*event.values[0] + (alpha)*mRotationVector[0];
            	mRotationVector[1] = (1-alpha)*event.values[1] + (alpha)*mRotationVector[1];
            	mRotationVector[2] = (1-alpha)*event.values[2] + (alpha)*mRotationVector[2];
            }
			
			
//            if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//            	float alpha = 20000.0f/(20000.0f+mUpdateInterval);
//            	mRotationVector[0] = (1-alpha)*event.values[0] + (alpha)*mRotationVector[0];
//            	mRotationVector[1] = (1-alpha)*event.values[1] + (alpha)*mRotationVector[1];
//            	mRotationVector[2] = (1-alpha)*event.values[2] + (alpha)*mRotationVector[2];
//            }
            
            // PID-Regler
//			if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//
//				if(lLastUpdate==-1)
//					lLastUpdate=event.timestamp;
//				else
//				{
//					long timediff = event.timestamp-lLastUpdate;
//					lLastUpdate = event.timestamp;
//					mRotationVector[0] += (timediff/500000000.0f)*(event.values[0] - mRotationVector[0]);
//					mRotationVector[1] += (timediff/500000000.0f)*(event.values[1] - mRotationVector[1]);
//					mRotationVector[2] += (timediff/500000000.0f)*(event.values[2] - mRotationVector[2]);
//					System.out.println("" + (timediff/500000000.0f) + ": "+ mRotationVector[0] + ", " + mRotationVector[1] + ", " + mRotationVector[2]);
//				}
//            }
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1)
		{
			// TODO Auto-generated method stub
			
		}
		
		public void onResume()
		{
            mSensorManager.registerListener(this, mSensor, mUpdateInterval);
		}
		
		public void onPause()
		{
            mSensorManager.unregisterListener(this);
		}
		
		// Getter
		public float[] getQuaternion()
		{
			//float[] pQuaternion = new float[4];
			//SensorManager.getQuaternionFromVector(pQuaternion, mRotationVector);
			//return pQuaternion;
			return mRotationVector;
		}
		
		// Setter
		
		/**
		 * Takes milliseconds.
		 * @param updateIntervalMs
		 */
		public void setUpdateInterval(int updateIntervalMs)
		{
			if (updateIntervalMs <= 0)
				return;
			
			mUpdateInterval = updateIntervalMs * 1000;
			onPause();
			onResume();
		}
	}
}
