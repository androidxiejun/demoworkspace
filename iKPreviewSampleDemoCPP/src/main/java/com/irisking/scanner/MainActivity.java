package com.irisking.scanner;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.irisking.irisalgo.bean.IKEnrIdenStatus;
import com.irisking.irisalgo.util.Config;
import com.irisking.irisalgo.util.EnrFeatrueStruct;
import com.irisking.irisalgo.util.EnumDeviceType;
import com.irisking.irisalgo.util.EnumEyeType;
import com.irisking.irisalgo.util.FeatureList;
import com.irisking.irisalgo.util.FileUtil;
import com.irisking.irisalgo.util.IKALGConstant;
import com.irisking.irisalgo.util.IrisInfo;
import com.irisking.irisalgo.util.Person;
import com.irisking.irisalgo.util.Portrait;
import com.irisking.irisalgo.util.Preferences;
import com.irisking.irisapp.R;
import com.irisking.scanner.callback.CameraPreviewCallback;
import com.irisking.scanner.callback.IrisCaptureCallback;
import com.irisking.scanner.callback.IrisProcessCallback;
import com.irisking.scanner.model.EyePosition;
import com.irisking.scanner.presenter.IrisConfig;
import com.irisking.scanner.presenter.IrisPresenter;
import com.irisking.scanner.util.ImageUtil;
import com.irisking.scanner.util.TimeArray;

// 主文件，完成界面显示，UI控件控制等逻辑
@SuppressWarnings("unused")
public class MainActivity extends Activity implements OnClickListener {
    private static final String TAG=MainActivity.class.getSimpleName().toString();
	private TimeArray uvcTimeArray = new TimeArray();
	
	private String curName = "test";
	
	public boolean previewParaUpdated = false;
	
	// ============声音播放器=============
	private MediaPlayer EnrollSuccess = null;
	private MediaPlayer IdentifySuccess = null;
	private MediaPlayer soundCloser = null;
	private MediaPlayer soundFarther = null;
	private MediaPlayer soundLeft = null;
	private MediaPlayer soundRight = null;
	//===================================

	//===============控件================
	private Button mIrisRegisterBtn;
	private Button mIrisCaptureBtn;
	private Button mIrisIdenBtn;
	private TextView mResultTextViewEnrRecFinal;
	private EditText mUserNameEditText; // 显示用户名
	private TextView mFrameRateTextView; // 帧率显示文本
	private IrisPresenter mIrisPresenter;
	private SurfaceView svCamera;
	//===================================
	
	//=========画IR图像=========
	private SurfaceHolder holder;
	private Matrix matrix;
	
	public  int eyeViewWidth = 0;
	public  int eyeViewHeight = 0;
	//==========================
	
	// ======load feature list==========
	FeatureList featureFaceList = null;
	FeatureList faceData = null;

	FeatureList featureIrisLeftList = null;
	FeatureList featureIrisRightList = null;
	FeatureList irisLeftData = null;
	FeatureList irisRightData = null;

	private SqliteDataBase sqliteDataBase;
	
	ArrayList<IrisUserInfo> leftEyeList = new ArrayList<IrisUserInfo>();//从数据库中获取的所有用户左眼特征的集合
	ArrayList<IrisUserInfo> rightEyeList = new ArrayList<IrisUserInfo>();//从数据库中获取的所有用户右眼特征的集合
	
	
	private int maxFeatureCount = 900;
	//==================================
	 //屏幕中双眼的坐标位置
	private float eyeX1;
	private float eyeX2;
	private float eyeHeight;
	private float hor_scale;//横屏下缩放比例
	private RoundProgressBar progressBar;
	
	private IrisConfig.EnrollConfig mEnrollConfig;
	private IrisConfig.CaptureConfig mCaptureConfig;
	private IrisConfig.IdentifyConfig mIdentifyConfig;
	
	SharedPreferences sp;
	private String sp_name = "iris_sp_user";
	private String sp_count_name = "iris_sp_user_count";
    private boolean isStop;
	 //Thread Handler What
    private SurfaceHandler mSurfaceHandler;
    public static final int HANDLER_DRAW_IMAGE = 0;
    public static final int HANDLE_MESSAGE_EXIT = 2;

    //Main Handler
    public static final int MAIN_HANDLER_UPDATE_TEXT = 0x0011;
    public static final int MAIN_HANDLER_RESET_UI = 0x0012;
    public static final int MAIN_HANDLER_IDEN = 0x0013;
    private EditText et_userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EnumDeviceType.getCurrentDevice().setCameraId(1);
		sqliteDataBase = SqliteDataBase.getInstance(this);
		sp = this.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
		screenUiAdjust();
		
		setContentView(R.layout.activity_iris_recognition);
		// 设置语音
		initSound();
		initUI();
		
		if(Config.USE_USBCAMERA){
			Log.i(TAG,"onCreate------------1-----------");
			mIrisPresenter = new IrisPresenter(this, uvcPreviewCallback);
		} else if(Config.USE_DOUBLECAMERA){
			Log.i(TAG,"onCreate------------2-----------");
		} else{
			Log.i(TAG,"onCreate------------3-----------");
					mIrisPresenter = new IrisPresenter(this, irPreviewCallback);
		}
		
		mEnrollConfig = new IrisConfig.EnrollConfig();
		mCaptureConfig = new IrisConfig.CaptureConfig();
		mIdentifyConfig = new IrisConfig.IdentifyConfig();
		
		initIrisData();
	}
	
	@Override
	protected void onStart() {
		mIrisPresenter.resume();
		isStop = false;
		super.onStart();
	}
	
	private void initSound() {
		this.EnrollSuccess = MediaPlayer.create(this, R.raw.enrsucc);
		this.IdentifySuccess = MediaPlayer.create(this, R.raw.idensucc);
		this.soundCloser = MediaPlayer.create(this, R.raw.closer);
		this.soundFarther = MediaPlayer.create(this, R.raw.farther);
		this.soundLeft = MediaPlayer.create(this, R.raw.moveleft);
		this.soundRight = MediaPlayer.create(this, R.raw.moveright);
	}
	
	private void initIrisData() {

		irisLeftData = new FeatureList(maxFeatureCount, "L");
		irisRightData = new FeatureList(maxFeatureCount, "R");

		// 2017.09.05 10:25修改，从数据库查询所有特征文件
		leftEyeList = (ArrayList<IrisUserInfo>) sqliteDataBase.queryLeftFeature();

		rightEyeList = (ArrayList<IrisUserInfo>) sqliteDataBase.queryRightFeature();

		if (leftEyeList.size() == 0 || leftEyeList == null
			&& rightEyeList.size() == 0 || rightEyeList == null) {
			return;
		}

		for (int i = 0; i < leftEyeList.size(); i++) {
			irisLeftData.irisput(new Person(leftEyeList.get(i).m_UserName), EnumEyeType.LEFT,
					new Portrait(1), leftEyeList.get(i).m_LeftTemplate);
		}

		for (int i = 0; i < rightEyeList.size(); i++) {
			irisRightData.irisput(new Person(rightEyeList.get(i).m_UserName), EnumEyeType.RIGHT,
					new Portrait(1), rightEyeList.get(i).m_RightTemplate);
		}

		mIrisPresenter.setIrisData(irisLeftData, irisRightData, faceData);//需要把特征传入jar包，以便识别
	}
	
	@Override
	protected void onStop() {
		isStop = true;
		mSurfaceHandler.removeMessages(HANDLER_DRAW_IMAGE);
		resetUI();
		mIrisPresenter.pause();
		super.onStop();
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	private int[] textureBuffer;
	private Bitmap gBitmap;
	private byte[] bmpData;
	private int bmpWidth;
	private int bmpHeight;
	
	private void drawImage() {
		if(textureBuffer == null){
			textureBuffer = new int[bmpWidth * bmpHeight];
		}
		if(gBitmap == null){
//			gBitmap = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);//32 位 ARGB 位图
			gBitmap = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.RGB_565);//8 位 RGB位图,没有透明度
		}

		ImageUtil.getBitmap8888(bmpData, bmpHeight, bmpWidth, 0, 0, bmpWidth-1, bmpHeight-1, textureBuffer, 0, 1);
		
		gBitmap.setPixels(textureBuffer, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);
		
		Canvas canvas = holder.lockCanvas();
		if(canvas != null){
			canvas.scale(-1, 1, eyeViewWidth / 2.0f, eyeViewHeight / 2.0f);
			canvas.drawBitmap(gBitmap, matrix, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}
	/**
	 * 屏幕UI调整
	 */
	private void screenUiAdjust() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int screenWidth = metrics.widthPixels; // 获取屏幕的宽
		Configuration mConfiguration = this.getResources().getConfiguration();
		int ori = mConfiguration.orientation;// 获取屏幕方向
		if (ori == Configuration.ORIENTATION_LANDSCAPE) {	// 如果是横屏，预览区域的宽为当前屏幕宽的80%，根据设备的不同可以动态再进行适配
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			hor_scale = 0.6f;
			eyeViewWidth = (int) (screenWidth * hor_scale);
			// 由于图像是16:9的图像
			eyeViewHeight = (int) (eyeViewWidth / 1.777f);
			// 在480*270的分辨率下，双眼相对于左上角的坐标点为（140,110），（340,110） ps：固定坐标点，修改需要咨询虹霸开发人员
			float x = (float) eyeViewWidth / IKALGConstant.IK_DISPLAY_IMG_WIDTH;
			float y = (float) eyeViewHeight / IKALGConstant.IK_DISPLAY_IMG_HEIGHT;
			DecimalFormat df = new DecimalFormat("0.00");
			int transWid = (screenWidth - eyeViewWidth)/2;
			eyeX1 = Float.parseFloat(df.format(x)) * EnumDeviceType.getCurrentDevice().getDefaultLeftIrisCol()+ transWid;
			eyeX2 = Float.parseFloat(df.format(x)) * EnumDeviceType.getCurrentDevice().getDefaultRightIrisCol()+ transWid;
			eyeHeight = Float.parseFloat(df.format(y)) * EnumDeviceType.getCurrentDevice().getDefaultLeftIrisRow();
		} else if (ori == Configuration.ORIENTATION_PORTRAIT) {	// 竖屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			hor_scale = 1.0f;
			eyeViewWidth = screenWidth;// 如果是竖屏，预览区域的宽为屏幕的宽
			// 由于图像是16:9的图像
			eyeViewHeight = (int) (eyeViewWidth / 1.777f);
			// 在480*270的分辨率下，双眼的坐标点为（140,110），（340,110） ps：固定坐标点，修改需要咨询虹霸开发人员
			float x = (float) eyeViewWidth / IKALGConstant.IK_DISPLAY_IMG_WIDTH;
			float y = (float) eyeViewHeight / IKALGConstant.IK_DISPLAY_IMG_HEIGHT;
			DecimalFormat df = new DecimalFormat("0.00");
			eyeX1 = Float.parseFloat(df.format(x)) * EnumDeviceType.getCurrentDevice().getDefaultLeftIrisCol();
			eyeX2 = Float.parseFloat(df.format(x)) * EnumDeviceType.getCurrentDevice().getDefaultRightIrisCol();
			eyeHeight = Float.parseFloat(df.format(y)) * EnumDeviceType.getCurrentDevice().getDefaultLeftIrisRow();
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 全屏，不出现图标
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MAIN_HANDLER_UPDATE_TEXT:
                    if (!isStop) {
                    	mResultTextViewEnrRecFinal.setText(msg.obj.toString());
                    }
                    break;
                case MAIN_HANDLER_RESET_UI:
                    resetUI();
                    break;
                case MAIN_HANDLER_IDEN:
//    				onClick(mIrisIdenBtn);	// 连续识别使用
                	break;
            }
        }
    };

	private static class SurfaceHandler extends Handler {
        private WeakReference<Context> reference;

        public SurfaceHandler(Context context) {
            reference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
        	MainActivity activity = (MainActivity) reference.get();
            if (activity.isStop) {
                return;
            }
            if (msg.what == HANDLER_DRAW_IMAGE) {
                activity.drawImage();
            }
            if (msg.what == HANDLE_MESSAGE_EXIT) {
                Looper.myLooper().quit();
            }
        }
    }

    class SurfaceViewThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            mSurfaceHandler = new SurfaceHandler(MainActivity.this);
            Looper.loop();
        }
    }

    Thread surfaceThread;
    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(0, 0, 0));
            holder.unlockCanvasAndPost(canvas);
            surfaceThread = new SurfaceViewThread();
            surfaceThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceHandler.sendEmptyMessage(HANDLE_MESSAGE_EXIT);
        }
    };
	protected int distRange = 0;

	// Init view
	private void initUI() {
		svCamera = (SurfaceView) findViewById(R.id.iv_camera);
		LayoutParams svParams = svCamera.getLayoutParams();
		svParams.width = eyeViewWidth;
		svParams.height = eyeViewHeight;
		svCamera.setLayoutParams(svParams);
		
		holder = svCamera.getHolder();
		holder.addCallback(surfaceCallback);
		matrix = new Matrix();

		progressBar = (RoundProgressBar) findViewById(R.id.roundProgress);
		progressBar.setXAndY(eyeX1, eyeX2, eyeHeight);// 设置双眼progressbar的位置
		progressBar.setHorScale(hor_scale);
		// Init button
		mIrisRegisterBtn = (Button) findViewById(R.id.btn_register);
		mIrisRegisterBtn.setOnClickListener(this);
		mIrisIdenBtn = (Button) findViewById(R.id.btn_scan);
		mIrisIdenBtn.setOnClickListener(this);
		mIrisCaptureBtn = (Button) findViewById(R.id.btn_capture);
		mIrisCaptureBtn.setOnClickListener(this);

		mResultTextViewEnrRecFinal = (TextView) findViewById(R.id.ie_final_result);
		et_userName = (EditText) findViewById(R.id.et_userName);
		previewParaUpdated = false;
	}
	
	private boolean isActive = false;
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_register: // 虹膜注册
			
//			if(mIrisPresenter.isActive()){
			if(isActive){
				resetUI();
			}else{
				curName = et_userName.getText().toString();
				if(TextUtils.isEmpty(curName)){
					Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				isActive = true;
				mIrisIdenBtn.setEnabled(false);
				mIrisCaptureBtn.setEnabled(false);
				mIrisRegisterBtn.setText(R.string.stop_register);
				svCamera.setKeepScreenOn(true);
				Preferences.getInstance(getApplicationContext()).setRegisterName(curName);
				
				mEnrollConfig.irisMode = IKALGConstant.IR_IM_EYE_BOTH;
				mEnrollConfig.irisNeedCount = 3;
				mEnrollConfig.overTime = 60;
				mEnrollConfig.singleUse = false;
				mIrisPresenter.startEnroll(mEnrollConfig, processCallback);
			}
			break;
		case R.id.btn_capture:
			if(isActive){
				resetUI();
			}else{
				curName = et_userName.getText().toString();
				if(TextUtils.isEmpty(curName)){
					Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
					return;
				}
				isActive = true;
				mIrisIdenBtn.setEnabled(false);
				mIrisRegisterBtn.setEnabled(false);
				mIrisCaptureBtn.setText(R.string.stop_capture);
				svCamera.setKeepScreenOn(true);
				Preferences.getInstance(getApplicationContext()).setRegisterName(curName);
				
				mCaptureConfig.irisMode = IKALGConstant.IR_IM_EYE_BOTH;
				mCaptureConfig.irisNeedCount = 1;
				mCaptureConfig.overTime = 60;
				mIrisPresenter.startCapture(mCaptureConfig, captureCallback);
			}
			break;
		// 单独虹膜识别
		case R.id.btn_scan:
//			if(mIrisPresenter.isActive()){
			if(isActive){
				resetUI();
			}else{
				isActive = true;
				mIrisIdenBtn.setText(R.string.stop_identify);
				mIrisCaptureBtn.setEnabled(false);
				mIrisRegisterBtn.setEnabled(false);
				svCamera.setKeepScreenOn(true);
				
				mIdentifyConfig.irisMode = IKALGConstant.IR_IM_EYE_UNDEF;
				mIdentifyConfig.overTime = 60;
//				mIdentifyConfig.reserve |= IKALGConstant.RESERVE_INFO_I_CONSTANT_LIGHT;		// 保持红外灯常亮
//				mIdentifyConfig.reserve |= IKALGConstant.RESERVE_INFO_I_CONSTANT_PREVIEW;	// 注册、识别结束 不执行Camera的stopPreview()方法
				mIrisPresenter.startIdentify(mIdentifyConfig, processCallback);
			}
			break;
		}
	}
	
	public void resetUI(){
		isActive = false;
		mResultTextViewEnrRecFinal.setText(" ");

		svCamera.setKeepScreenOn(false);
		
		mIrisIdenBtn.setText(R.string.start_identify);
		mIrisRegisterBtn.setEnabled(true);
		
		mIrisRegisterBtn.setText(R.string.start_register);
		mIrisIdenBtn.setEnabled(true);
		
		mIrisCaptureBtn.setText(R.string.start_capture);
		mIrisCaptureBtn.setEnabled(true);
		
		mIrisPresenter.stopAlgo();
		progressBar.setLeftAndRightProgress(0, 0, 0);
	}
	
	private void updateUIStatus(int status){
		
		String tips = "";
		
		switch (status) {
		case IKALGConstant.IRIS_FRAME_STATUS_BLINK:
			tips = "请眨眼";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_MOTION_BLUR:
		case IKALGConstant.IRIS_FRAME_STATUS_FOCUS_BLUR:
			tips = "保持稳定";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_BAD_EYE_OPENNESS:
			tips = "请睁大眼睛";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_WITH_GLASS:
			tips = "请摘掉眼镜";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_WITH_GLASS_HEADUP:
			tips = "请抬头";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_WITH_GLASS_HEADDOWN:
			tips = "请低头";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_CLOSE:
			int m_curDist = IKEnrIdenStatus.getInstance().irisPos.dist;
			int suit = EnumDeviceType.getCurrentDevice().getSuitablePosDist();
			int movedist = Math.abs(m_curDist - suit);	
			if (m_curDist != -1) {
				tips = String.format("请后移%02d厘米", movedist);
			} else {
				tips = "请后移" ;
			}
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_FAR:
			m_curDist = IKEnrIdenStatus.getInstance().irisPos.dist;
			suit = EnumDeviceType.getCurrentDevice().getSuitablePosDist();
			movedist = Math.abs(m_curDist - suit);
			if (m_curDist != -1) {
				tips = String.format("请前移%02d厘米", movedist); 
			} else{
				tips = "请前移";
			}
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_NOT_FOUND:
			tips = "请对准 EYE_NOT_FOUND";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_UNAUTHORIZED_ATTACK:
			tips = "请停止使用";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_CONTACTLENS:
			tips = "请摘掉隐形眼镜";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_ATTACK:
			tips = "请勿攻击";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_OUTDOOR:
			tips = "请室内使用";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_UP:
			tips = "请将双眼对准取景框 TOO_UP";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_DOWN:
			tips = "请将双眼对准取景框 TOO_DOWN";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_LEFT:
			tips = "请将双眼对准取景框 TOO_LEFT";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_EYE_TOO_RIGHT:
			tips = "请将双眼对准取景框 TOO_RIGHT";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_SUITABLE:
			tips = "正在扫描，请保持稳定...";
			break;
		case IKALGConstant.IRIS_FRAME_STATUS_BAD_IMAGE_QUALITY:
			tips = "请将双眼对准取景框 BAD_IMAGE";
			break;
		case IKALGConstant.ERR_INVALIDDATE:
			tips = "设备超过授权日期";
			break;
		case IKALGConstant.ERR_INVALIDDEVICE:
			tips = "设备未授权";
			break;
		default:
			break;
		}
		Message msg = Message.obtain();
		msg.obj = tips;
		msg.what = MAIN_HANDLER_UPDATE_TEXT;
		mainHandler.sendMessage(msg);
		
	}
	
	private IrisCaptureCallback captureCallback = new IrisCaptureCallback() {
		
		@Override
		public void onUIStatusUpdate(int status){
			updateUIStatus(status);
		}
		
		@Override
		public void onCaptureProgress(int currentLeftCount, int currentRightCount, int needCount) {
			progressBar.setLeftAndRightProgress(currentLeftCount, currentRightCount, needCount);
		}

		@Override
		public void onCaptureComplete(int ifSuccess,EnrFeatrueStruct leftEyeFeat, EnrFeatrueStruct rightEyeFeat,EnrFeatrueStruct faceFeat) {
			resetUI();
			// 首先判断是否成功，若失败提示后返回
			if (ifSuccess != IKALGConstant.ALGSUCCESS) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				if (ifSuccess == IKALGConstant.ERR_OVERTIME) {
					builder.setMessage("超时,请重试");
				} else if (ifSuccess == IKALGConstant.ERR_ENROLL_ERRORFEATURE) {
					builder.setMessage("多人注册!");
				} else{
					builder.setMessage("ErrorCode:" + ifSuccess);
				}

				builder.setTitle("注意");
				builder.setCancelable(false);
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();

				return;
			}
			EnrollSuccess.start();
//			saveIrisFile(curName, leftEyeFeat, rightEyeFeat);
			saveIrisImage(curName, leftEyeFeat, rightEyeFeat);
		}

		@Override
		public void onEyeDetected(boolean isValid,EyePosition leftPos,EyePosition rightPos,int captureDistance) {
		}

		@Override
		public void onAlgoExit() {
			resetUI();
		}
	};
	
	private IrisProcessCallback processCallback = new IrisProcessCallback() {
		
		@Override
		public void onUIStatusUpdate(int status){
			updateUIStatus(status);
		}
		
		@Override
		public void onEnrollProgress(int currentLeftCount, int currentRightCount, int needCount) {
			progressBar.setLeftAndRightProgress(currentLeftCount, currentRightCount, needCount);
		}

		@Override
		public void onEnrollComplete(int ifSuccess,EnrFeatrueStruct leftEyeFeat, EnrFeatrueStruct rightEyeFeat,EnrFeatrueStruct faceFeat) {
			resetUI();
			// 首先判断是否成功，若失败提示后返回
			if (ifSuccess != IKALGConstant.ALGSUCCESS) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				if (ifSuccess == IKALGConstant.ERR_OVERTIME) {
					builder.setMessage("超时,请重试");
				} else if (ifSuccess == IKALGConstant.ERR_ENROLL_ERRORFEATURE) {
					builder.setMessage("多人注册!");
				} else{
					builder.setMessage("ErrorCode:" + ifSuccess);
				}

				builder.setTitle("注意");
				builder.setCancelable(false);
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();

				return;
			}
			EnrollSuccess.start();
			
			int count = sp.getInt(sp_count_name, 1);
			if(count > 65535){
				count = 1;
			}
//			saveIrisFile("user_" + count, leftEyeFeat, rightEyeFeat);
			saveIrisFile(curName, leftEyeFeat, rightEyeFeat);
//			saveIrisImage(curName, leftEyeFeat, rightEyeFeat);
			sp.edit().putInt(sp_count_name, ++count).commit();
		}

		@Override
		public void onEyeDetected(boolean isValid,EyePosition leftPos,EyePosition rightPos,int captureDistance) {
		}

		@Override
		public void onIdentifyComplete(int ifSuccess, int matchIndex, int eyeFlag) {
			resetUI();
			if (ifSuccess != IKALGConstant.ALGSUCCESS) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				if (ifSuccess == IKALGConstant.ERR_IDENFAILED) {
					builder.setMessage("识别失败,请重试");
				} else if (ifSuccess == IKALGConstant.ERR_OVERTIME) {
					builder.setMessage("识别超时,请重试");
				} else if (ifSuccess == IKALGConstant.ERR_NOFEATURE) {
					builder.setMessage("没有特征,请先注册");
				} else if (ifSuccess == IKALGConstant.ERR_EXCEEDMAXMATCHCAPACITY) {
					builder.setMessage("特征数量过多!");
				} else if (ifSuccess == IKALGConstant.ERR_IDEN) {
					builder.setMessage("特征不匹配!");
				} else{
					builder.setMessage("error code:" + ifSuccess);
				}

				builder.setTitle("注意");
				builder.setCancelable(false);
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();

				return;
			}

			IdentifySuccess.start();
			String matchName = "";
			if(eyeFlag == EnumEyeType.LEFT){
				matchName = irisLeftData.personAt(matchIndex).getName();
			}else{
				matchName = irisRightData.personAt(matchIndex).getName();
			}
			Toast.makeText(getApplicationContext(),"识别成功 matchIndex: " + matchIndex + ", eyeFlag:" + eyeFlag + ", name:" + matchName, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onAlgoExit() {
			resetUI();
		}
	};
	
	private String featurePath = Environment.getExternalStorageDirectory() + "/IK_Demo/";
	
	private CameraPreviewCallback irPreviewCallback = new CameraPreviewCallback.IRPreviewCallback(){
		@Override
		public void onPreviewFrame(byte[] bmpData, int bmpWidth, int bmpHeight) {
			
			uvcTimeArray.newTime();
			if (uvcTimeArray.count() % 3 == 0) {
				Log.e("iris_verbose","MainActivity onPreviewFrame fps:" + uvcTimeArray.toString());
			}
			
			MainActivity.this.bmpWidth = bmpWidth;
			MainActivity.this.bmpHeight = bmpHeight;
			MainActivity.this.bmpData = bmpData;
			
			if(previewParaUpdated == false){
				previewParaUpdated = true;
				if(matrix != null){
					matrix.postScale(1.0f*eyeViewWidth/bmpWidth, 1.0f*eyeViewHeight/bmpHeight);
				}
			}
			
			if (!isStop && mSurfaceHandler != null && surfaceThread.isAlive()) {
                mSurfaceHandler.sendEmptyMessage(HANDLER_DRAW_IMAGE);
            }
		}
	};
	
	private CameraPreviewCallback uvcPreviewCallback = new CameraPreviewCallback.UVCPreviewCallback(){
		@Override
		public void onPreviewFrame(byte[] bmpData, int bmpWidth, int bmpHeight) {
			
			uvcTimeArray.newTime();
			if (uvcTimeArray.count() % 3 == 0) {
				Log.e("iris_verbose","MainActivity onPreviewFrame fps:" + uvcTimeArray.toString());
			}
			
			MainActivity.this.bmpWidth = bmpWidth;
			MainActivity.this.bmpHeight = bmpHeight;
			MainActivity.this.bmpData = bmpData;
			
			if(previewParaUpdated == false){
				previewParaUpdated = true;
				if(matrix != null){
					matrix.postScale(1.0f*eyeViewWidth/bmpWidth, 1.0f*eyeViewHeight/bmpHeight);
				}
			}
			
			if (!isStop && mSurfaceHandler != null && surfaceThread.isAlive()) {
                mSurfaceHandler.sendEmptyMessage(HANDLER_DRAW_IMAGE);
            }
		}

		@Override
		public void onCameraConnected() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "USB设备已连接", Toast.LENGTH_SHORT).show();
				}
			});
		}
		@Override
		public void onCameraDisconnected() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "USB设备失去连接", Toast.LENGTH_SHORT).show();
					resetUI();
				}
			});
		}
		@Override
		public void onCameraDettached() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(), "USB设备失去连接", Toast.LENGTH_SHORT).show();
					resetUI();
				}
			});
		}

		@Override
		public void onDeviceFlip(boolean isFlip) {
			if(isFlip){
				mainHandler.sendEmptyMessage(MAIN_HANDLER_RESET_UI);
			}
		}
	};

	public void onBackPressed() {
		resetUI();
		super.onBackPressed();
	};
	
	@Override
	protected void onDestroy() {
        mSurfaceHandler.removeCallbacksAndMessages(null);
		if(mIrisPresenter!=null){
			mIrisPresenter.release();
			mIrisPresenter = null;
		}
		super.onDestroy();
	}

	public void saveIrisImage(String name, EnrFeatrueStruct leftEyeFeat, EnrFeatrueStruct rightEyeFeat) {
		if (leftEyeFeat != null) {
			for (int i = 0; i < leftEyeFeat.enrCount; i++) {
				String filePath = Environment.getExternalStorageDirectory() + "/IK_FaceDemo/StreamCap/" + name;
				FileUtil.saveMonoBMPImage(filePath, leftEyeFeat.irisInfo[i].imgData, IKALGConstant.IKALG_Iris_ImH, IKALGConstant.IKALG_Iris_ImW, "L");	
			}
		}
		if (rightEyeFeat != null) {
			for (int i = 0; i < rightEyeFeat.enrCount; i++) {
				String filePath = Environment.getExternalStorageDirectory() + "/IK_FaceDemo/StreamCap/" + name;
				FileUtil.saveMonoBMPImage(filePath, rightEyeFeat.irisInfo[i].imgData, IKALGConstant.IKALG_Iris_ImH, IKALGConstant.IKALG_Iris_ImW, "R");	
			}
		}
	}
	
	public void saveIrisFile(String name, EnrFeatrueStruct leftEyeFeat,EnrFeatrueStruct rightEyeFeat) {
		//如果是单人使用
		deleteIrisData();
		int userCount = sqliteDataBase.getUserCount();

		for(int i=userCount; i>=IrisConfig.LimitNumber; i--){
			sqliteDataBase.removeFirstUser();
		}

		IrisUserInfo userInfo = new IrisUserInfo();

		if (leftEyeFeat != null && rightEyeFeat != null) {
			userInfo.m_Uid = name;
			userInfo.m_UserName = name;
			userInfo.m_UserFavicon = 0;
			userInfo.m_LeftTemplate = new byte[leftEyeFeat.enrCount * IKALGConstant.IKALG_Iris_Enr_CodeLen];
			for(int i=0; i<leftEyeFeat.enrCount; i++){
				IrisInfo irisInfo = leftEyeFeat.irisInfo[i];
				System.arraycopy(
						leftEyeFeat.irisInfo[i].irisEnrTemplate, 0, 
						userInfo.m_LeftTemplate, i*IKALGConstant.IKALG_Iris_Enr_CodeLen, 
						IKALGConstant.IKALG_Iris_Enr_CodeLen);
			}
			userInfo.m_RightTemplate = new byte[rightEyeFeat.enrCount * IKALGConstant.IKALG_Iris_Enr_CodeLen];
			for(int i=0; i<rightEyeFeat.enrCount; i++){
				IrisInfo irisInfo = rightEyeFeat.irisInfo[i];
				System.arraycopy(
						rightEyeFeat.irisInfo[i].irisEnrTemplate, 0, 
						userInfo.m_RightTemplate, i*IKALGConstant.IKALG_Iris_Enr_CodeLen, 
						IKALGConstant.IKALG_Iris_Enr_CodeLen);
			}
			userInfo.m_LeftTemplate_Count = leftEyeFeat.enrCount;
			userInfo.m_RightTemplate_Count = rightEyeFeat.enrCount;
			userInfo.m_EnrollTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//            Log.e("iris_info_1", "MainActivity saveIrisFile() userInfo.m_EnrollTime:" + userInfo.m_EnrollTime + ", userInfo.m_LeftTemplate:" + userInfo.m_LeftTemplate.length);
			sqliteDataBase.insertUserData(userInfo);
		}
		//重新查询数据库数据
		initIrisData();
	}

	public void deleteIrisData() {
		if(mEnrollConfig.singleUse) {
			sqliteDataBase.removeAll();
		}
	}
	
}
