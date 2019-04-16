package com.example.testsurfaceview.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.example.testsurfaceview.MainActivity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaceCamera implements CameraSurfaceView2.OnCameraListener {
    private static FaceCamera instance;
    private CameraSurfaceView2 mSurfaceView;
    private Context mContext;
    private Display mDisplay;
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private boolean isOpen = false;
    public static Point pointPreView = new Point(800, 600);
//    CameraGLSurfaceView glSurfaceView;
//    static FaceTrackService faceTrackService;

    /**
     * 声音
     */
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    private FaceCamera() {
    }

    public static FaceCamera getInstance() {
        if (instance == null) {
            synchronized (FaceCamera.class) {
                if (instance == null) {
                    instance = new FaceCamera();
//                    faceTrackService = new FaceTrackService();
//                    faceTrackService.setSize(pointPreView.x, pointPreView.y);
                }
            }
        }
        return instance;
    }

    /**
     * 开启第一个摄像头
     *
     * @param context
     */
    public void onResume(Context context, CameraSurfaceView2 surfaceView) {
        this.mContext = context;
        this.mSurfaceView = surfaceView;
        mSurfaceView.setOnCameraListener(this);
        picPath = null;
        mSurfaceView.setZOrderOnTop(true);
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            this.mDisplay = manager.getDefaultDisplay();
        } else {
            throw new NullPointerException("WindowManager is null");
        }
        isOpen = true;
    }


    public boolean isOpen()  {
        return isOpen;
    }


    public void setPreViewSize(int width, int height) {
        Camera.Parameters para;
        if (null != mCamera) {
            para = mCamera.getParameters();
        } else {
            return;
        }

        if (width == 0) {
            List<Camera.Size> previewSizes = para.getSupportedPreviewSizes();
            width = previewSizes.get(0).width;
            height = previewSizes.get(0).height;
        } else {
            if (para.isZoomSupported()) {
                para.setZoom(13);
            }
        }

        para.setPreviewSize(width, height);
        mCamera.setParameters(para);
        mCamera.startPreview();
    }


    /**
     * 关闭相机
     */
    public void close() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 保存图片旋转角度使摆正
     *
     * @param bm
     * @return
     */
    private Bitmap rotateBitmap(Bitmap bm) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate((float) 90);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
    }

    @Override
    public Camera setupCamera() {
        mCamera = Camera.open(0);
        isOpen = true;
        Camera.Parameters para = mCamera.getParameters();
        if (para.isZoomSupported()) {
            para.setZoom(12);
        }
        para.setPreviewSize(pointPreView.x, pointPreView.y);
        para.setPreviewFormat(ImageFormat.NV21);
        para.setExposureCompensation(2);

        if (para.getMaxNumMeteringAreas() > 0) { // check that metering areas are supported
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
            Rect areaRect2 = new Rect(0, 0, 20, 20);    // specify an area in center of image
            meteringAreas.add(new Camera.Area(areaRect2, 1)); // set weight to 60%

            para.setMeteringAreas(meteringAreas);
        } else {
//            LogUtils.e("相机不支持测光区域");
        }

        if (para.getSupportedFocusModes().size() > 0) {
            para.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        } else {
//            LogUtils.e("相机不支对焦模式");
        }

        mCamera.setParameters(para);
        return mCamera;
    }

    @Override
    public void setupChanged(int format, int width, int height) {

    }

    @Override
    public boolean startPreviewImmediately() {
        return true;
    }


//    public FaceDataListener dataListener;
//
//    public synchronized void setDataListener(FaceDataListener dataListener) {
//        this.dataListener = dataListener;
//    }


    private static String picPath;


    public static String getPicPath() {
        return picPath;
    }

    @Override
    public Object onPreview(final byte[] data, final int width, final int height, int format, long timestamp) {
        Log.i(MainActivity.TAG,"onPreview----------"+ Arrays.toString(data));
//        Rect[] rects;
//        final List<AFT_FSDKFace> fsdkFaces = faceTrackService.getFtfaces(data);
//        final int maxIndex = ImageUtils.findFTMaxAreaFace(fsdkFaces);
//        if (fsdkFaces.size() > 0) {
//            rects = new Rect[1];
//            rects[0] = fsdkFaces.get(maxIndex).getRect();
//        } else {
//            rects = new Rect[0];
//        }
//
//        if (dataListener != null) {//开始了人脸验证
//            if (fsdkFaces.size() > 0) {//有人脸
//                AFT_FSDKFace aft_fsdkFace = fsdkFaces.get(maxIndex).clone();
//                savaPicture(data.clone(), width, height);//保存验证人的图片
//                List<AFT_FSDKFace> aft_fsdkFaces = new ArrayList<>();
//                aft_fsdkFaces.add(aft_fsdkFace);
//                if (dataListener != null)
//                    dataListener.getData(data.clone(), aft_fsdkFaces);
//            } else {
//                if (dataListener != null)
//                    dataListener.noData();
//            }
//        }
//        return rects;
        return null;
    }


    /**
     * 保存验证人的图片
     *
     * @param data
     * @param width
     * @param height
     */
    private void savaPicture(byte[] data, int width, int height) {
//        if (picPath == null) {
//            try {
//                picPath = CameraPhoto.getPicturePath();
//                YuvImage image = new YuvImage(data, ImageFormat.NV21, width, height, null);            //ImageFormat.NV21  640 480
//                ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
//                image.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 90, outputSteam); // 将NV21格式图片，以质量70压缩成Jpeg，并得到JPEG数据流
//                byte[] jpegData = outputSteam.toByteArray();                                                //从outputSteam得到byte数据
//                File file = getFileFromBytes(jpegData, picPath);
//                MLiteOrm.getInstance().save(new TakedPhoto(file.getPath()));
//                LogUtils.e("图片保存为：" + file.getPath());
//                outputSteam.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onBeforeRender(CameraFrameData data) {

    }

    @Override
    public void onAfterRender(CameraFrameData data) {
//        if (glSurfaceView != null)
//            glSurfaceView.getGLES2Render().draw_rect((Rect[]) data.getParams(), Color.GREEN, 2);
    }

//    public interface FaceDataListener {
//        void getData(byte[] mImageNV21, List<AFT_FSDKFace> fsdkFaces);
//
//        void noData();
//
//        void notLive();
//    }


    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


}




