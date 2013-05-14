package com.gplio.common;

import static android.opengl.GLES20.*;
import android.util.Log;

public class ShaderSetup {
	private static boolean DEBUG_LOGGING = true;
	private static final String TAG = "ShaderSetup";

	private static void log(String s, int shaderType) {
		if (ShaderSetup.DEBUG_LOGGING) {
			if (shaderType == GL_VERTEX_SHADER)
				Log.d(TAG, s + "( VERTEX_SHADER )");
			else if (shaderType == GL_FRAGMENT_SHADER)
				Log.d(TAG, s + "( FRAGMENT_SHADER )");
			else
				Log.d(TAG, s + "( Passed Wrong shader type?)");
		}
	}

	private static void log(String s) {
		if (ShaderSetup.DEBUG_LOGGING) {
			Log.d(TAG, s);
		}
	}

	private static int compileShader(final int shaderType,
			final String shaderSource) {

		// Create a new shader object
		final int shaderHandle = glCreateShader(shaderType);

		if (shaderHandle == 0) {
			log("Could not create new shader", shaderType);
			return 0;
		}

		// Pass shader source
		glShaderSource(shaderHandle, shaderSource);

		// Compile shader
		glCompileShader(shaderHandle);

		// Get compilation status
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderHandle, GL_COMPILE_STATUS, compileStatus, 0);

		log("Results of source compilation, code:\n" + shaderSource + "\n: "
				+ glGetShaderInfoLog(shaderHandle));

		// Check status
		if (compileStatus[0] == 0) {
			// Failed! delete the shader object
			glDeleteShader(shaderHandle);
			log("Shader compilation failed");
			return 0;
		}
		// Return the shader object id
		return shaderHandle;
	}

	public static int compileVertexShader(String shaderSourceCode) {
		return compileShader(GL_VERTEX_SHADER, shaderSourceCode);
	}

	public static int compileFragmentShader(String fragmentSourceCode) {
		return compileShader(GL_FRAGMENT_SHADER, fragmentSourceCode);
	}

	/**
	 * Link a vertex shader and a fragment shader together into a OpenGL program
	 * object id, returns 0 if failed
	 * */
	public static int linkPrograms(final int vertexShaderHandle,
			final int fragmentShaderHandle) {
		// Link compiled shaders with attributes
		int programHandle = glCreateProgram();

		if (programHandle == 0) {
			log("Could not create new program");
			return 0;
		}

		// Bind vertex shader to program
		glAttachShader(programHandle, vertexShaderHandle);

		// Bind fragment shader to program
		glAttachShader(programHandle, fragmentShaderHandle);

		// Could Bind attributes before linking, but it can be done after
		// glBindAttribLocation(programHandle, i, attributes[i]);

		// Link shaders into a program
		glLinkProgram(programHandle);

		// Get link status
		final int[] linkStatus = new int[1];
		glGetProgramiv(programHandle, GL_LINK_STATUS, linkStatus, 0);

		log("Linking results: " + glGetProgramInfoLog(programHandle));

		// Check link status
		if (linkStatus[0] == 0) {
			glDeleteProgram(programHandle);// delete the program object
			log("Program linking failed");
			return 0;
		}

		// Return program object id
		return programHandle;
	}

	public static int buildProgram(final String vertexShaderCode,
			final String fragmentShaderCode) {
		// Compile shaders
		int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = compileShader(GL_FRAGMENT_SHADER,
				fragmentShaderCode);

		// Link into program
		int programHandle = linkPrograms(vertexShader, fragmentShader);

		// validate if debugging
		if (ShaderSetup.DEBUG_LOGGING)
			validateProgram(programHandle);

		return programHandle;
	}

	private static boolean validateProgram(int programHandle) {
		glValidateProgram(programHandle);
		final int[] validateStatus = new int[1];
		glGetProgramiv(programHandle, GL_VALIDATE_STATUS, validateStatus, 0);

		log("Validation results: " + validateStatus[0] + " \nMessage: "
				+ glGetProgramInfoLog(programHandle));
		return validateStatus[0] != 0;
	}

	public static void setDebugMode(boolean set) {
		ShaderSetup.DEBUG_LOGGING = set;
	}

}
