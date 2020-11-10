
package com.toby.mlface.sample.facepoint;

import android.util.SparseArray;

public final class FacePointEngine {
    private static class EngineHolder {
        public static FacePointEngine instance = new FacePointEngine();
    }

    private FacePointEngine() {
        mFaceArrays = new SparseArray<EGLFace>();
    }

    public static FacePointEngine getInstance() {
        return EngineHolder.instance;
    }

    private final Object mSyncFence = new Object();

    private final SparseArray<EGLFace> mFaceArrays;

    public void setFaceSize(int size) {
        synchronized (mSyncFence) {
            if (mFaceArrays.size() > size) {
                mFaceArrays.removeAtRange(size, mFaceArrays.size() - size);
            }
        }
    }

    public boolean hasFace() {
        boolean result;
        synchronized (mSyncFence) {
            result = mFaceArrays.size() > 0;
        }
        return result;
    }

    public EGLFace getOneFace(int index) {
        EGLFace eglFace = null;
        synchronized (mSyncFence) {
            eglFace = mFaceArrays.get(index);
            if (eglFace == null) {
                eglFace = new EGLFace();
            }
        }
        return eglFace;
    }

    public void putOneFace(int index, EGLFace EGLFace) {
        synchronized (mSyncFence) {
            mFaceArrays.put(index, EGLFace);
        }
    }

    public int getFaceSize() {
        return mFaceArrays.size();
    }

    public SparseArray<EGLFace> getFaceArrays() {
        return mFaceArrays;
    }

    /**
     * clearAll
     */
    public void clearAll() {
        synchronized (mSyncFence) {
            mFaceArrays.clear();
        }
    }
}
