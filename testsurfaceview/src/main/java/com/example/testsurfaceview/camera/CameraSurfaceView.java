package com.example.testsurfaceview.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.testsurfaceview.MainActivity;

import java.util.Arrays;

/**
 * Created by AndroidXJ on 2019/4/16.
 */

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Camera.PreviewCallback{
    private static final String TAG = CameraSurfaceView.class.getSimpleName();

    private SurfaceHolder mSurfaceHolder;



    public CameraSurfaceView(Context context) {
        super(context);
        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(MainActivity.TAG,"surfaceCreated-------");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(MainActivity.TAG,"surfaceChanged-----format---"+ format);
        Log.i(MainActivity.TAG,"surfaceChanged-----width---"+ width);
        Log.i(MainActivity.TAG,"surfaceChanged-----height---"+ height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void init(){
        mSurfaceHolder=getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(MainActivity.TAG,"onPreviewFrame---数据--------"+ Arrays.toString(data));
    }
}
