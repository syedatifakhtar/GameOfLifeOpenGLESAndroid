package com.example.game_of_life_opengles.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;


//Dont you static import bro? - OpenGL2 classes use static functions :(
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;



public class HelloWorldRenderer implements Renderer {

	//Called when its time to draw a frame - app calls this multiple times automagically!
	//Also in the words of Yoda - "Draw something you must"
	//Because the rendering buffer will be swapped and displayed on the screen after method return - bad flickering effect my friend!!
	@Override
	public void onDrawFrame(GL10 arg0) {
		// TODO Auto-generated method stub
		
		glClear(GL_COLOR_BUFFER_BIT);//Just clear the rendering suface
		
	}

	//Size change - switched from portrait to land
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);//Set the viewport - or the area onscreen...we set it here to take up full space!
	}

	//Called when surface is created - first time application is run,device wakes up...blah blah...go figure!
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f); // Clear the screen to a red one as soon as its created
	}

}
