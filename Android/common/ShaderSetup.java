package com.gplio.common;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderSetup {

	private static final String TAG = "ShaderSetup";

	public static int compileShader(final int shaderType,
			final String shaderSource) {
		int shaderHandle = GLES20.glCreateShader(shaderType);

		if (shaderHandle != 0) {
			// pass shader source
			GLES20.glShaderSource(shaderHandle, shaderSource);

			// compile shader
			GLES20.glCompileShader(shaderHandle);

			// get compilation status

			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS,
					compileStatus, 0);

			if (compileStatus[0] == 0) {
				Log.e(TAG,
						"Error compiling shader"
								+ GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}
		if (shaderHandle == 0) {
			throw new RuntimeException("Error creating shader");
		}
		return shaderHandle;
	}

	public static int createAndLinkProgram(final int vertexShaderHandle,
			final int fragmentShaderHandle, final String[] attributes) {
		int programHandle = GLES20.glCreateProgram();
		
		if (programHandle != 0) {
			//Bind vertex shader to program
			GLES20.glAttachShader(programHandle, vertexShaderHandle);
			
			//Bind fragment shader to program
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);
			
			//Bind attributes before linking
			if(attributes!=null) {
				final int size=attributes.length;
				for (int i = 0; i < size; i++) {
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
				}
			}
			
			//link shaders into a program
			GLES20.glLinkProgram(programHandle);
			
			//get link status
			final int[] linkStatus=new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);
			
			//if failed delete the program
			if (linkStatus[0]==0) {
				Log.e(TAG, "Error compiling the program"+GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle=0;
			}
		}
		if (programHandle==0) {
			throw new RuntimeException("Error creating the program");
		}
		return programHandle;
	}

	private static int BareBonesLoadShader(final int type,
			final String shaderCode) {
		int shader = GLES20.glCreateShader(type);// GLES20.GL_VERTEX_SHADER or
													// GL_FRAGMENT_SHADER

		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	public int BareBonesSetupShaders(final String vertexShaderCode,
			final String fragmentShaderCode) {
		int vertexShader = BareBonesLoadShader(GLES20.GL_VERTEX_SHADER,
				vertexShaderCode);
		int fragmentShader = BareBonesLoadShader(GLES20.GL_FRAGMENT_SHADER,
				fragmentShaderCode);
		int programHandle = GLES20.glCreateProgram();
		GLES20.glAttachShader(programHandle, vertexShader);
		GLES20.glAttachShader(programHandle, fragmentShader);
		GLES20.glLinkProgram(programHandle);
		return programHandle;
	}

}
