package net.loreli.playground.opengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.loreli.playground.opengltest.R;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.view.Display;
import net.loreli.playground.argfw.ErrorCheck;
import net.loreli.playground.argfw.Environment.GpsToGameCoords;
import net.loreli.playground.argfw.Environment.MobileLocation;
import net.loreli.playground.argfw.Environment.MobileRotation;
import net.loreli.playground.argfw.GraphicsManager.GeomNode;
import net.loreli.playground.argfw.GraphicsManager.Geometry;
import net.loreli.playground.argfw.GraphicsManager.GeometryFactory;
import net.loreli.playground.argfw.GraphicsManager.SceneManager;
import net.loreli.playground.argfw.GraphicsManager.ShaderRegistry;
import net.loreli.playground.argfw.GraphicsManager.TextureFactory;
import net.loreli.playground.argfw.GraphicsManager.TransformNode;

public class TexRenderer implements Renderer {
	 
	
	private Display defaultDisplay;
	
	public TexRenderer(Display oDisplay)
	{
		defaultDisplay=oDisplay;
	}
	
	 public static String getTileNumber(final double lat, final double lon, final int zoom)
	 {
		 int xtile = (int)Math.floor( (lon + 180) / 360 * (1<<zoom) ) ;
		 int ytile = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1<<zoom) ) ;
		 return("" + zoom + "/" + xtile + "/" + ytile);
	 }

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		mStartPos = null;
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClearColor(0.057f, 0.221f, 0.400f, 1.0f);
		
		/*
		mSM = new SceneManager(ShaderRegistry.CreatePhong());
		TransformNode gn = mSM.CreateTransformNode(mSM.GetRoot());
		TransformNode gn2 = mSM.CreateTransformNode(gn);
		gn.Translate(0, 0, -3.0f);
		gn2.Translate(0, 2, 0);
		Geometry g = GeometryFactory.CreateBetterIcosphere();
		//g.GetVAO().Deactivate("aNormal");
		mSM.CreateGeomNode(g, gn);
		mSM.CreateGeomNode(g, gn2);
		*/
		
		mSM = new SceneManager(ShaderRegistry.CreateSimpleTextured());

		//GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		//mTextureDataHandle = TextureFactory.LoadFromFile(mActivityContext, R.drawable.thetexture256);
		//GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		//mTextureDataHandle = TextureFactory.LoadFromFile(mActivityContext, R.drawable.gravel);

		//int zoom = 18;
		//double lat = 47.587757d; 
		//double lon = 11.310940d;
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		mTextureDataHandle0 = TextureFactory.LoadFromFile(mActivityContext, R.drawable.gravel);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		//mTextureDataHandle1 = TextureFactory.LoadFromUrl(mActivityContext, R.drawable.gravel,
		//		"http://tile.openstreetmap.org/" + getTileNumber(lat, lon, zoom) + ".png");
		
		pRealRoot = mSM.CreateTransformNode(mSM.GetRoot());
		pRealRoot.Translate(0, 0, -2);
		//pRealRoot.Rotate(270.0f, 0, 0, 1);

		TransformNode pTransTmp;
		pTransTmp = mSM.CreateTransformNode(pRealRoot);
		pTransTmp.Rotate(270.0f, 0, 0, 1);
		mSM.CreateGeomNode(GeometryFactory.CreateTexturedPlane(), pTransTmp);
		pTransTmp.Scale(1.0f);

		Geometry dog = new Geometry(Dog.dog);
		dog.GetVAO().NewAttribute("aPosition", GLES20.GL_FLOAT, 3);
		dog.GetVAO().NewAttribute("aNormal", GLES20.GL_FLOAT, 3);
		GeomNode pGeom = new GeomNode(mSM, dog);

		pTrans0 = new TransformNode(mSM, null);
		pTrans0.AddChild(pGeom);

		pTrans1 = mSM.CreateTransformNode(pRealRoot);
		pTrans1.SetShader(ShaderRegistry.CreatePhong());
		pTrans1.Translate(0, 0, 1);

		pTransTmp = mSM.CreateTransformNode(pTrans1);
		pTransTmp.Translate(5, 0, 0);
		pTransTmp.Rotate(0, 0, 0, 1);
		pTransTmp.AddChild(new GeomNode(mSM, GeometryFactory.CreateBetterIcosphere()));

		pTransTmp = mSM.CreateTransformNode(pTrans1);
		pTransTmp.Translate(0, 5, 0);
		pTransTmp.SetShader(ShaderRegistry.CreatePhong());
		//pTransTmp.Rotate(180, 0, 0, 1);
		
		Geometry monkey = new Geometry(SuzanneSmooth.Suzanne);
		monkey.GetVAO().NewAttribute("aPosition", GLES20.GL_FLOAT, 3);
		monkey.GetVAO().NewAttribute("aNormal", GLES20.GL_FLOAT, 3);

		GeomNode newGeom = new GeomNode(mSM, monkey);
		//newGeom.GetGeometry().GetVAO().Deactivate("aTexCoord");
		pTransTmp.AddChild(newGeom);

		pVormStudentenwohnheim = new TransformNode(mSM, pRealRoot);
		pVormStudentenwohnheim.AddChild(newGeom);
		pHaltestellePonttor = new TransformNode(mSM, pRealRoot);
		pHaltestellePonttor.AddChild(pGeom);
		pRwthVR = new TransformNode(mSM, pRealRoot);
		pRwthVR.AddChild(newGeom);

		pTransTmp = mSM.CreateTransformNode(pTrans1);
		pTransTmp.Translate(-5, 0, 0);
		pTransTmp.Rotate(180, 0, 0, 1);
		pTransTmp.AddChild(pTrans0);

		pTransTmp = mSM.CreateTransformNode(pTrans1);
		pTransTmp.Translate(0, -5, 0);
		pTransTmp.Rotate(270, 0, 0, 1);
		pTransTmp.AddChild(pTrans0);
		
		mSM.GetDefaultShader().GetUniform("uTexture").Update(1);
	}

	public void onPause()
	{
		mLocSens.onPause();
	}
	
	public void onResume()
	{
		mLocSens.onResume();
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0,  0, width, height);
		
		float ratio = (float)width / height;
		//Matrix.frustumM(mProjMat, 0, -ratio, ratio, -1, 1, 1, 10);
		Matrix.perspectiveM(mProjMat, 0, 60, ratio, 1, 350);
		//Matrix.orthoM(mProjMat, 0, -ratio, ratio, -1, 1, 1, 10);
		mSM.SetProjMat(mProjMat);
	}
	
	static int framecounter = 0;
	private boolean m_bCoordinatesReceived = false;
	private GpsToGameCoords m_pGpsConv;

	@Override
	public void onDrawFrame(GL10 gl)
	{
		//GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		double[] loc = new double[] {0, 0};
		if (mLocSens != null) {
			loc = mLocSens.GetLocation();
			ErrorCheck.printDebug("" + loc[0] + ", " + loc[1] + "\n");
			float[] newCamPos = new float[] {(float) loc[0], (float) loc[1], 0};
			if (mStartPos == null && loc[0] > 50.0 && loc[1] > 6.0)
				mStartPos = newCamPos;
			
			framecounter++;
			if (framecounter % (30 * 10) == 0 && loc[0] != 0 && loc[1] != 0)
			{
				ErrorCheck.printDebug("Position: (" + loc[0] + ", " + loc[1] + ")");
				GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
				TextureFactory.UpdateFromUrl(mTextureDataHandle0, mActivityContext, R.drawable.thetexture256,
					"http://tile.openstreetmap.org/" + getTileNumber(loc[0], loc[1], 18) + ".png");
			}
			
			/*
			float[] camPos = new float[] {0,0,0};
			if (mStartPos != null) {
				camPos = new float[] {
					(mStartPos[0] - newCamPos[0]) * 100000,
					(mStartPos[1] - newCamPos[1]) * 10000,
					0
				};
			}
			mSM.GetCamera().SetCamPos(camPos);
			*/
		}
		
		double newPos[] = new double[2];
		if (m_bCoordinatesReceived == false && m_pGpsConv != null)
		{
			newPos = m_pGpsConv.GetGameCoords(new double[] {50.78033, 6.075949});
			pTrans1.SetTranslation((float)newPos[0], (float)newPos[1], (float)newPos[2]);

			newPos = m_pGpsConv.GetGameCoords(new double[] {50.782454, 6.076145});
			pVormStudentenwohnheim.SetTranslation((float)newPos[0], (float)newPos[1], (float)newPos[2]);
			pHaltestellePonttor.SetScale(5);
			
			newPos = m_pGpsConv.GetGameCoords(new double[] {50.781133, 6.078845});
			pHaltestellePonttor.SetTranslation((float)newPos[0], (float)newPos[1], (float)newPos[2]);
			pHaltestellePonttor.SetScale(5);
			
			newPos = m_pGpsConv.GetGameCoords(new double[] {50.780806, 6.065609});
			pRwthVR.SetTranslation((float)newPos[0], (float)-newPos[1], (float)newPos[2]);
			pHaltestellePonttor.SetScale(5);
			
			m_bCoordinatesReceived = true;
		}
		newPos = m_pGpsConv.GetGameCoords(loc);
		float[] newCamPos = new float[] {(float) newPos[0], (float) newPos[1], 0};
		mSM.GetCamera().SetCamPos(newCamPos);
		
		mSM.SetShader(mSM.GetDefaultShader());
		mSM.GetCurrentShader().Use();
		/*
		if (mTouchX > 0)
			mSM.GetDefaultShader().GetUniform("uTexture").Update(0);
		else
			mSM.GetDefaultShader().GetUniform("uTexture").Update(1);
		*/

		if (mTouchX > 1)
			mTouchX--;
		else if (mTouchX < -1)
			mTouchX++;
		else mTouchX = 0;
		if (mTouchY > 1)
			mTouchY--;
		else if (mTouchY < -1)
			mTouchY++;
		else mTouchY = 0;

		pTrans0.Rotate(mTouchX * 0.1f, 0, 0, 1);
		pTrans1.Rotate(mTouchY * 0.1f, 0, 0, 1);
		if (mRotSens != null) {
			float[] lookAt = new float[3];
			float[] up = new float[3];
			mRotSens.onResume();
			float[] rotMat = mRotSens.getMatrix();
			
			Matrix.rotateM(rotMat, 0, defaultDisplay.getRotation()*90,rotMat[2], rotMat[6], rotMat[10]);

			up[0] = rotMat[1];
			up[1] = rotMat[5];
			up[2] = rotMat[9];
			
			lookAt[0] = -rotMat[2];
			lookAt[1] = -rotMat[6];
			lookAt[2] = -rotMat[10];

			mSM.GetCamera().SetLookAt(lookAt);
			mSM.GetCamera().SetUp(up);
		}
		mSM.Draw();
	}
	
	private float[] mStartPos = null;
	private Context mActivityContext;
	private int mTextureDataHandle0;
	//private int mTextureDataHandle1;
	public void SetContext(Context context) {
		mActivityContext = context;
	}
	
	public void SetRotationSensor(MobileRotation rotSens) {
		mRotSens = rotSens;
	}
	
	public void SetLocationSensor(MobileLocation locSens) {
		mLocSens = locSens;
		m_pGpsConv = new GpsToGameCoords(mLocSens);
	}

	private MobileRotation mRotSens = null;
	private MobileLocation mLocSens = null;
	public float mTouchX;
	public float mTouchY;
	
	/* Private */
	
	SceneManager mSM;
	float[] mProjMat = new float[16];

	private TransformNode pRealRoot;
	private TransformNode pTrans0;
	private TransformNode pTrans1;
	private TransformNode pVormStudentenwohnheim;
	private TransformNode pHaltestellePonttor;
	private TransformNode pRwthVR;
}
