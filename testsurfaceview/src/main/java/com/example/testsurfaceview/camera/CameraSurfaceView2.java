package com.example.testsurfaceview.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.testsurfaceview.MainActivity;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * create by gqjjqg,.
 * easy to use camera.
 */
public class CameraSurfaceView2 extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {
    private final String TAG = this.getClass().getSimpleName();

    private Camera mCamera;
    private int mWidth, mHeight, mFormat;
    private OnCameraListener mOnCameraListener;
    private FrameHelper mFrameHelper;
    private BlockingQueue<CameraFrameData> mImageDataBuffers;

    public interface OnCameraListener {
        /**
         * setup camera.
         *
         * @return the camera
         */
        public Camera setupCamera();

        /**
         * reset on surfaceChanged.
         *
         * @param format image format.
         * @param width  width
         * @param height height.
         */
        public void setupChanged(int format, int width, int height);

        /**
         * start preview immediately, after surfaceCreated
         *
         * @return true or false.
         */
        public boolean startPreviewImmediately();

        /**
         * on ui thread.
         *
         * @param data      image data
         * @param width     width
         * @param height    height
         * @param format    format
         * @param timestamp time stamp
         * @return image params.
         */
        public Object onPreview(byte[] data, int width, int height, int format, long timestamp);

        public void onBeforeRender(CameraFrameData data);

        public void onAfterRender(CameraFrameData data);
    }

    public CameraSurfaceView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        onCreate();
    }

    public CameraSurfaceView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        onCreate();
    }

    public CameraSurfaceView2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        onCreate();
    }

    private void onCreate() {
        SurfaceHolder arg0 = getHolder();
        arg0.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        arg0.addCallback(this);

        mFrameHelper = new FrameHelper();
        mImageDataBuffers = new LinkedBlockingQueue<>();
    }

    public boolean startPreview() {
        Size imageSize = mCamera.getParameters().getPreviewSize();
        int lineBytes = imageSize.width * ImageFormat.getBitsPerPixel(mCamera.getParameters().getPreviewFormat()) / 8;
        mCamera.setPreviewCallbackWithBuffer(this);
        mCamera.addCallbackBuffer(new byte[lineBytes * imageSize.height]);
        mCamera.addCallbackBuffer(new byte[lineBytes * imageSize.height]);
        mCamera.addCallbackBuffer(new byte[lineBytes * imageSize.height]);
        mCamera.startPreview();
        return true;
    }

    public boolean stopPreview() {
        mCamera.setPreviewCallbackWithBuffer(null);
        mCamera.stopPreview();
        return true;
    }

    /**
     * used for front and rear exchange.
     *
     * @return
     */
    public boolean resetCamera() {
        if (closeCamera()) {
            if (openCamera()) {
                return true;
            }
        }
        Log.e(TAG, "resetCamera fail!");
        return false;
    }

    private boolean openCamera() {
        try {
            if (mCamera != null) {
                mCamera.reconnect();
            } else {
                Log.i(MainActivity.TAG,"camera==null------------");
                if (mOnCameraListener != null) {
                    Log.i(MainActivity.TAG,"mOnCameraListener!=null------------");
                    mCamera = mOnCameraListener.setupCamera();
                }
            }

            if (mCamera != null) {
                Log.i(MainActivity.TAG,"camera!=null------------");
                mCamera.setPreviewDisplay(getHolder());

                Size imageSize = mCamera.getParameters().getPreviewSize();
                mWidth = imageSize.width;
                mHeight = imageSize.height;
                mFormat = mCamera.getParameters().getPreviewFormat();

                if (mOnCameraListener != null) {
                    if (mOnCameraListener.startPreviewImmediately()) {
                        startPreview();
                    } else {
                        Log.i(MainActivity.TAG, "Camera not start preview!");
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean closeCamera() {
        try {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            mImageDataBuffers.clear();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(MainActivity.TAG,"surfaceChanged----------");
        // TODO Auto-generated method stub
        if (mOnCameraListener != null) {
            mOnCameraListener.setupChanged(format, width, height);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(MainActivity.TAG,"surfaceCreated----------");
        // TODO Auto-generated method stub
        if (!openCamera()) {
            Log.i(MainActivity.TAG, "camera start fail!");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(MainActivity.TAG,"surfaceDestroyed----------");
        // TODO Auto-generated method stub
        if (!closeCamera()) {
            Log.i(MainActivity.TAG, "camera close fail!");
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
		Log.i("MainActivity","onPreviewFrame预览数据-------"+ Arrays.toString(data));
        // TODO Auto-generated method stub
        long timestamp = System.nanoTime();
        mFrameHelper.printFPS();
        if (mOnCameraListener != null) {
            mOnCameraListener.onPreview(data.clone(), mWidth, mHeight, mFormat, timestamp);
        }
        if (mCamera != null) {
            mCamera.addCallbackBuffer(data);
        }
    }

    public void setOnCameraListener(OnCameraListener l) {
        mOnCameraListener = l;
    }


    public void debug_print_fps(boolean preview, boolean render) {
        mFrameHelper.enable(preview);
    }
}
