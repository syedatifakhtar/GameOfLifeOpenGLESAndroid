package com.example.game_of_life_opengles.util;

import android.util.Log;
import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;
public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	
	public static int compileVertexShader(String shaderCode) {
		return compileShader(GL_VERTEX_SHADER, shaderCode);
		}
	
	public static int compileFragmentShader(String shaderCode) {
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
		}
		
	private static int compileShader(int type, String shaderCode) {
		
		final int shaderObjectId = glCreateShader(type);
		if (shaderObjectId == 0) {
			if (LoggerConfig.ON) {
				Log.w(TAG, "Could not create new shader.");
			}
		}
		glShaderSource(shaderObjectId, shaderCode);//Associate our shaderCode with an actual shader Object
		glCompileShader(shaderObjectId);
		
		//Check if compilation was succesfull? - write the compile status of shaderObject to compileStatus at pos 0
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
		
		if (LoggerConfig.ON) {
			// Print the shader info log to the Android log output.
			Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
			+ glGetShaderInfoLog(shaderObjectId));
			}
			
		if (compileStatus[0] == 0) {
			// If it failed, delete the shader object.
			glDeleteShader(shaderObjectId);
			if (LoggerConfig.ON) {
			Log.w(TAG, "Compilation of shader failed.");
			}
			return 0;
			}
		//Return the shader if everything was succesfull
		return shaderObjectId;
		}
	
	/*
	 * OpenGL uses a combined vertex-fragmentShader
	 * it sucks!
	 */
	public static int linkProgram(int vertexShaderId,int fragmentShaderId) {
		final int programObjectId = glCreateProgram();
		if (programObjectId == 0) {
		if (LoggerConfig.ON) {
			Log.w(TAG, "Could not create new program");
			}
		return 0;
		}
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
		glLinkProgram(programObjectId);
		
		final int[] linkStatus = new int[1];
		glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
		if (LoggerConfig.ON) {
			// Print the program info log to the Android log output.
			Log.v(TAG, "Results of linking program:\n"
			+ glGetProgramInfoLog(programObjectId));
			}
		if (linkStatus[0] == 0) {
			// If it failed, delete the program object.
			glDeleteProgram(programObjectId);
			if (LoggerConfig.ON) {
			Log.w(TAG, "Linking of program failed.");
			}
			return 0;
			}
		return programObjectId;
	}
	
	public static boolean validateProgram(int programObjectId) {
		glValidateProgram(programObjectId);
		final int[] validateStatus = new int[1];
		glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
		Log.v(TAG, "Results of validating program: " + validateStatus[0]
		+ "\nLog:" + glGetProgramInfoLog(programObjectId));
		return validateStatus[0] != 0;
		}
}
