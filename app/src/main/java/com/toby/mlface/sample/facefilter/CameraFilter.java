package com.toby.mlface.sample.facefilter;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;

import com.toby.mlface.sample.util.OpenGLUtils;

public class CameraFilter extends BaseFilter {
    private int mTransformMatrixHandle;
    private float[] mTransformMatrix = new float[16];

    public CameraFilter(Context context) {
        this(context, OpenGLUtils.getShaderFromAssets(context, "shader/vertex_oes_input.glsl"),
                OpenGLUtils.getShaderFromAssets(context, "shader/fragment_oes_input.glsl"));
    }

    public CameraFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }

    @Override
    public void initProgramHandle() {
        super.initProgramHandle();
        mTransformMatrixHandle = GLES30.glGetUniformLocation(mProgramHandle, "transformMatrix");
    }

    @Override
    public int getTextureType() {
        return GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
    }

    @Override
    public void onDrawFrameBegin() {
        super.onDrawFrameBegin();
        GLES30.glUniformMatrix4fv(mTransformMatrixHandle, 1, false, mTransformMatrix, 0);
    }

    public void setTextureTransformMatrix(float[] transformMatrix) {
        for (int i = 0; i < transformMatrix.length; i++) {
            mTransformMatrix[i] = transformMatrix[i];
        }
    }
}

