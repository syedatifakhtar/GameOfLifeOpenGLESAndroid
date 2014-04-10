package com.example.game_of_life_opengles.renderer;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import com.example.game_of_life_opengles.R;
import com.example.game_of_life_opengles.util.LoggerConfig;
import com.example.game_of_life_opengles.util.ShaderHelper;
import com.example.game_of_life_opengles.util.TextResourceReader;


/*
 *
 */
public class HelloWorldRenderer implements Renderer {

	private static final int POSITION_COMPONENT_COUNT = 2; //How many components per vertex,we have x,y
	
	//OpenGL renders with floats,
	//Cant draw rectangles,points,lines and triangles only!!
	float[] rectangleVertices= {
			//Triangle 1
			-0.5f, -0.5f,
			0.5f, 0.5f,
			-0.5f,0.5f,
			
			//Triangle 2
			-0.5f, -0.5f,
			0.5f,-0.5f,
			0.5f,0.5f
	};

	private static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	private final Context context;
	private int program;
	
	private static final String U_COLOR = "u_Color";
	private int uColorLocation;
	
	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;
	
	
	/*
	 *  
	 * OpenGL Pipeline:Read vertex data->execute vertex shader->
	 * assemble primitives-> rasterize primitives->
	 * exec fragment shader->write to frame buffer->display on screen
	 */
	public HelloWorldRenderer(Context context) {
		vertexData = ByteBuffer
				.allocateDirect(rectangleVertices.length * BYTES_PER_FLOAT) //Allocate in native memory - no Garbage Collection is gonna mess with this Buffer!!!
				.order(ByteOrder.nativeOrder())								//Big Endian/Small Endian - whatever the phone supports?
				.asFloatBuffer();											//Make it a float buffer extra special!
		vertexData.put(rectangleVertices);		//Finally - put our data!! biatch - dalvik jvm cant touch this!
		this.context=context;
	}
	//Called when its time to draw a frame - app calls this multiple times automagically!
	//Also in the words of Yoda - "Draw something you must"
	//Because the rendering buffer will be swapped and displayed on the screen after method return - bad flickering effect my friend!!
	@Override
	public void onDrawFrame(GL10 arg0) {
		// TODO Auto-generated method stub
		
		glClear(GL_COLOR_BUFFER_BIT);//Just clear the rendering suface
		
		//Draw our data man!
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
	}

	//Size change - switched from portrait to land
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);//Set the viewport - or the area onscreen...we set it here to take up full space!
	}

	//Called when surface is created - first time application is run,device wakes up...blah blah...go figure!
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Clear the screen to a black one as soon as its created
		
		String vertexShaderSource	=	TextResourceReader.readTextFileFromResource(context,R.raw.simple_vertex_shader);
		String fragmentShaderSource	=	TextResourceReader.readTextFileFromResource(context,R.raw.simple_fragment_shader);
		
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
		
		if (LoggerConfig.ON) {
			ShaderHelper.validateProgram(program);
			}
		glUseProgram(program);//Finally - finally - use the shader program we created!
		uColorLocation = glGetUniformLocation(program, U_COLOR); //Get the Location of the pixel
		aPositionLocation = glGetAttribLocation(program, A_POSITION);//Get vertex location
		vertexData.position(0);//Reset pointer to the beggining
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
		false, 0, vertexData);
		glEnableVertexAttribArray(aPositionLocation);
		
		
	}

}
