package net.loreli.playground.argfw.GraphicsManager;

import java.io.InputStream;
import java.net.URL;

import net.loreli.playground.argfw.ErrorCheck;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class TextureFactory {

	public static int LoadFromFile(final Context context, final int resourceId)
	{
		final int[] textureHandle = new int[1];
		
		GLES20.glGenTextures(1, textureHandle, 0);
		
		if (textureHandle[0] != 0)
		{
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			
			bitmap.recycle();
		}

		return textureHandle[0];
	}

	public static int LoadFromUrl(final Context context, final int backupResourceId, final String p_strUrl)
	{
		final int[] textureHandle = new int[1];
		
		GLES20.glGenTextures(1, textureHandle, 0);
		
		if (textureHandle[0] != 0)
		{
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inScaled = false;
			Bitmap bitmap = null;
			try
			{
				InputStream in = new URL(p_strUrl).openStream();
				bitmap = BitmapFactory.decodeStream(in, null, options);
				
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
				
				bitmap.recycle();
			}
			catch (Exception e) {
				e.printStackTrace();
				ErrorCheck.printDebug("LoadFromUrl(\"" + p_strUrl + "\"); failed [" + e.toString() + "]");
				return LoadFromFile(context, backupResourceId);
			}
		}

		return textureHandle[0];
	}

	public static int UpdateFromFile(int textureHandle, final Context context, final int resourceId)
	{
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
		GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);
		
		bitmap.recycle();

		return textureHandle;
	}

	public static int UpdateFromUrl(int textureHandle, final Context context, final int backupResourceId, final String p_strUrl)
	{	
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		Bitmap bitmap = null;
		try
		{
			InputStream in = new URL(p_strUrl).openStream();
			bitmap = BitmapFactory.decodeStream(in, null, options);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
			//GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);
			
			bitmap.recycle();
		}
		catch (Exception e) {
			e.printStackTrace();
			ErrorCheck.printDebug("LoadFromUrl(\"" + p_strUrl + "\"); failed [" + e.toString() + "]");
			return UpdateFromFile(textureHandle, context, backupResourceId);
		}

		return textureHandle;
	}
	
}
