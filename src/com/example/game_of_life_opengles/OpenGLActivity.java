package com.example.game_of_life_opengles;

import com.example.game_of_life_opengles.renderer.HelloWorldRenderer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;
public class OpenGLActivity extends Activity {

	private GLSurfaceView glSurfaceView;//Punches a HOLE through the view heirarchy
	private boolean renderSet=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Create a new GLSurface and set the contentView of the activity to its instance!
		glSurfaceView=new GLSurfaceView(this);
		setContentView(glSurfaceView);
		
		//Check if our Device supports openGL2.0ES!
		final ActivityManager activityManager=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configInfo=activityManager.getDeviceConfigurationInfo();
		final boolean supportsEs2=configInfo.reqGlEsVersion >= 0x20000;
		
		//Ok now what?
		if(supportsEs2) {
			glSurfaceView.setEGLContextClientVersion(2); //We are using opengl2 bitch!!
			glSurfaceView.setRenderer(new HelloWorldRenderer(this));//Use my renderer man!!
			renderSet=true;
		}else {
			//But the princess is in another castle!!
			Toast.makeText(this, "Your device does not support OpenGL ES 2.0 - get a new phone biatch!", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(renderSet) {
			glSurfaceView.onPause();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(renderSet) {
			glSurfaceView.onResume();
		}
	}
}