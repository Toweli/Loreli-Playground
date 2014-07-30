package net.loreli.playground.opengltest;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.WindowManager;
import net.loreli.playground.argfw.ErrorCheck;
import net.loreli.playground.argfw.Environment.MobileLocation;
import net.loreli.playground.argfw.Environment.MobileRotation;

public class MyGLSurfaceView extends GLSurfaceView {

	private final TexRenderer mRenderer;
	
	/*
	private ScheduledThreadPoolExecutor scheduler;
	private Handler uiViewHandler;
	*/
	
	public MyGLSurfaceView(Context context) {
		super(context);
		ErrorCheck.SetContext(context);
		setEGLContextClientVersion(2);

		mRenderer = new TexRenderer(((WindowManager) context.getSystemService(Activity.WINDOW_SERVICE)).getDefaultDisplay());
		mRenderer.SetRotationSensor(new MobileRotation(context, 30));	// update interval <- 30 ms
		mRenderer.SetLocationSensor(new MobileLocation(context, 0));
		mRenderer.SetContext(context);
		setRenderer(mRenderer);
		
		//setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		/*
		new Thread(new Runnable() {
			public void run() {
				TcpBridge.TcpOpen();
			}
		}).start();
		
		uiViewHandler = new Handler();
		
		scheduler = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(
			new Runnable() {
				public void run() {
					TcpBridge.TcpFetchPosition();
					uiViewHandler.post(new Runnable() {
						@Override
						public void run() {
							float[] pos = TcpBridge.GetPosition();
							String output = "New pos: (" + pos[0] + ", " + pos[1] + ", " + pos[2] + ")";
							System.out.println(output);
						}
					});
				}
			},
			3000,
			500,
			TimeUnit.MILLISECONDS);
		*/
	}

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private float mPreviousX;
	private float mPreviousY;
	
	@Override
	public void onPause()
	{
		mRenderer.onPause();
	}
	
	@Override
	public void onResume()
	{
		mRenderer.onResume();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		float x = e.getX();
		float y = e.getY();
		
		switch (e.getAction())
		{
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			
			if (y > getHeight() / 2)
				dx = dx * -1;
			if (x < getWidth() / 2)
				dy = dy * -1;

			mRenderer.mTouchX += dx * TOUCH_SCALE_FACTOR;
			mRenderer.mTouchY += dy * TOUCH_SCALE_FACTOR;
			//requestRender();
		}
		
		mPreviousX = x;
		mPreviousY = y;
		
		return true;
	}
}
