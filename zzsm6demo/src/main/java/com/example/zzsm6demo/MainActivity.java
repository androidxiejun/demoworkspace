package com.example.zzsm6demo;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;

import org.zz.jni.SM62Driver;
import org.zz.sm621driver.BMP;
import org.zz.sm621driver.ToolUnit;

import com.lenwotion.gpio.GpioOperate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String TAG=MainActivity.class.getSimpleName();

	private static final int SUCCESS_MSG 			        = 0; // 操作成功
	private static final int FAILED_MSG 			            = 1; // 操作失败
	private static final int GET_IMG_SUCCESS_MSG      = 2; // 获取图像成功
	private static final int SHOW_DLG_MSG                 = 3; //更改提示信息

	//private static String DEV_NAME = "/dev/ttyMT2";
	//private static int BAUD_RATE = 115200;
	// Jni库
	SM62Driver devDriver;
	//
	int iTemplatePos   = 1;
	// 图像
	int iTimeout = 10 * 1000; // 等待按手指的超时时间，单位：毫秒
	int iImageX = 256;
	int iImageY = 304;
	int iImageSize      = iImageX*iImageY;
	byte[] m_bFingerImage = new byte[iImageSize];

	int iTzSize      = 256;

	private GetTemplateThread  m_getTemplateThread = null;
	private GetFeatureThread   m_getFeatureThread = null;

	private ProgressDialog m_progressDlg    = null;
	private GetImageThread m_getImageThread = null;
	private EnrollThread   m_enrollThread = null;
	private SearchThread   m_searchThread = null;

	private EditText edit_baudrate;
	private EditText edit_devNodename;

	String m_strSDCardPath = null;
	// 定义一个负责更新的进度的Handler
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置无标题
		setContentView(R.layout.activity_main);

		devDriver = new SM62Driver();
		edit_baudrate   = (EditText) findViewById(R.id.edit_baudrate);
		edit_devNodename = (EditText) findViewById(R.id.edit_devNodename);

		m_strSDCardPath = ToolUnit.getSDCardPath();
		if (m_strSDCardPath==null) {
			ShowDlg("获取sd卡路径失败 ");
		}
	}

	private void EnableButton(Boolean bFlag) {
		Button btn;
		btn= (Button) findViewById(R.id.btn_up);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_devver);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_devcount);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_detectfinger);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_getImg);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_enroll);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_search);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_enroll_2);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_upTemplate);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_downTemplate);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_delTemplate);
		btn.setEnabled(bFlag);
		btn= (Button) findViewById(R.id.btn_clearTemplate);
		btn.setEnabled(bFlag);

	}

	/* Toast控件显示提示信息 */
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void ShowDlg(String strMsg) {
		new AlertDialog.Builder(MainActivity.this).setTitle("提示信息")
				.setMessage(strMsg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialoginterface, int i) {
					}
				}).show();
	}

	private void ShowProgressDlg(String strTitle, String strMsg) {
		m_progressDlg = ProgressDialog.show(MainActivity.this, strTitle,
				strMsg, true);
	}

	private void CancelProgressDlg() {
		m_progressDlg.cancel();
	}

	private void SendMsg(int what, String obj) {
		Message message = new Message();
		message.what = what;
		message.obj  = obj;
		message.arg1 = 0;
		LinkDetectedHandler.sendMessage(message);
	}

	@SuppressLint("HandlerLeak")
	private Handler LinkDetectedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case GET_IMG_SUCCESS_MSG:
					Bitmap bm1 = BMP.Raw2Bimap(m_bFingerImage,iImageX,iImageY);
					if(bm1!=null){
						ImageView image_show = (ImageView) findViewById(R.id.image_show);
						image_show.setImageBitmap(bm1);
					}
					DisplayToast((String) msg.obj);
					CancelProgressDlg();
					EnableButton(true);
					break;
				case SUCCESS_MSG:
					ShowDlg((String) msg.obj);
					CancelProgressDlg();
					EnableButton(true);
					break;
				case SHOW_DLG_MSG:
					m_progressDlg.setMessage((CharSequence) msg.obj);
					break;
				case FAILED_MSG:
					ShowDlg((String) msg.obj);
					CancelProgressDlg();
					EnableButton(true);
					break;
				default:
					DisplayToast((String) msg.obj);
					break;
			}
		}
	};

	public void OnClickUp(View view) {
//		GpioOperate.GPIOInit();
//		GpioOperate.SetGpioOutput(8);
//		GpioOperate.SetGpioDataHigh(8);
//		GpioOperate.SetGpioOutput(12);
//		GpioOperate.SetGpioDataHigh(12);
//		GpioOperate.SetGpioOutput(43);
//		GpioOperate.SetGpioDataHigh(43);
		GpioOperate.GPIOInit();
		GpioOperate.SetGpioOutput(94);
		GpioOperate.SetGpioDataHigh(94);
		ShowDlg("模块上电 ");
	}

	public void OnClickGetDevVer(View view) {
		byte[] szVersion = new byte[100];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxGetDeviceVersion(strDevName, iBaudRate, szVersion);
		if (nRet == 0) {
			ShowDlg(new String(szVersion));
		} else {
			ShowDlg("获取设备版本失败,nRet = "+nRet);
		}
	}

	public void OnClickDevCount(View view) {
		int[] iCount = new int[1];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxGetCount(strDevName, iBaudRate, iCount);
		if (nRet == 0) {
			ShowDlg("模块容量:"+iCount[0]);
		} else {
			ShowDlg("获取模块容量失败,nRet = "+nRet);
		}
	}

	public void OnClickDetectFinger(View view) {
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDetectFinger(strDevName, iBaudRate);
		if (nRet == 0) {
			ShowDlg("有手指");
		}else if (nRet == 2) {
			ShowDlg("无手指");
		} else {
			ShowDlg("通信失败");
		}
	}

	public void OnClickGetImg(View view) {
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_getImg).toString(),"请按手指...");
		if (m_getImageThread != null) {
			m_getImageThread.interrupt();
			m_getImageThread = null;
		}
		m_getImageThread = new GetImageThread();
		m_getImageThread.start();
	}

	private class GetImageThread extends Thread {
		public void run() {
			try {
				GetImage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void GetImage() {
		Calendar time1 = Calendar.getInstance();
		int nRet = 0;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		Arrays.fill(m_bFingerImage, (byte) 0);
		nRet = devDriver.mxGenImg(strDevName, iBaudRate, iTimeout);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "生成图像失败,nRet="+nRet);
			return ;
		}
		SendMsg(SHOW_DLG_MSG, "正在上传图像，请稍候...");
		nRet = devDriver.mxUpImg(strDevName, iBaudRate, m_bFingerImage);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "上传图像失败,nRet="+nRet);
			return ;
		}
		Calendar time2 = Calendar.getInstance();
		long bt_time = time2.getTimeInMillis() - time1.getTimeInMillis();
		SendMsg(GET_IMG_SUCCESS_MSG, "获取图像成功，耗时:"+ bt_time + "ms");
	}

	public void OnClickEnroll(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_enroll).toString(),"请按两次手指...");
		if (m_enrollThread != null) {
			m_enrollThread.interrupt();
			m_enrollThread = null;
		}
		m_enrollThread = new EnrollThread();
		m_enrollThread.start();
	}

	private class EnrollThread extends Thread {
		public void run() {
			try {
				Enroll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	/**
	 * 注册指纹
	 */
	public void Enroll() {
		int nRet = 0;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		nRet = devDriver.mxEnrollTemplate(strDevName, iBaudRate, iTimeout,iTemplatePos);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "注册模板失败,nRet="+nRet);
			return ;
		}
		SendMsg(SUCCESS_MSG, "注册模板成功，模板位置["+ iTemplatePos+"]");
	}

	public void OnClickSearch(View view) {
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_search).toString(),"请按手指...");
		if (m_searchThread != null) {
			m_searchThread.interrupt();
			m_searchThread = null;
		}
		m_searchThread = new SearchThread();
		m_searchThread.start();
	}

	private class SearchThread extends Thread {
		public void run() {
			try {
				Search();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void Search() {
		int nRet = 0;
		int[] iResult = new int[1];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		nRet = devDriver.mxFingerSearch(strDevName, iBaudRate, iTimeout,0,256,iResult);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "未搜索到,nRet="+nRet);
			return ;
		}
		SendMsg(SUCCESS_MSG, "搜索成功，搜索到位置["+ iResult[0]+"]");
	}

	public void OnClickEnroll2(View view) {
		iTemplatePos = 2;
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_enroll).toString(),"请按两次手指...");
		if (m_enrollThread != null) {
			m_enrollThread.interrupt();
			m_enrollThread = null;
		}
		m_enrollThread = new EnrollThread();
		m_enrollThread.start();
	}

	public void OnClickUpTemplate(View view) {
		int iPos = 1;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxCheckTemplate(strDevName, iBaudRate,iPos);
		if (nRet != 0) {
			ShowDlg("位置["+iPos+"]不存在有效模板\r\n请先注册模板");
			return;
		}
		byte[] szBuf = new byte[iTzSize];
		nRet = devDriver.mxUpTemplate(strDevName, iBaudRate,iPos,szBuf);
		Log.i(MainActivity.TAG,"上傳指纹模块返回的数据------"+ Arrays.toString(szBuf));
		if (nRet != 0) {
			ShowDlg("上传模板失败");
			return;
		}
		String strMbpath = m_strSDCardPath+"/mb.dat";
		nRet = ToolUnit.SaveData(strMbpath,szBuf,iTzSize);
		if (nRet != 0) {
			ShowDlg("保存模板失败\r\n请检查保存路径是否有效\r\n"+strMbpath);
			return;
		}
		ShowDlg("位置["+iPos+"]模板数据保存至:\r\n"+strMbpath);
	}

	public void OnClickDownTemplate(View view) {
		String strMbpath = m_strSDCardPath+"/mb.dat";
		long iLen = ToolUnit.getFileSizes(strMbpath);
		if (iLen < iTzSize) {
			ShowDlg("读取模板数据失败\r\n请检查模板数据路径是否有效\r\n"+strMbpath);
			return;
		}
		byte[] szBuf = ToolUnit.ReadData(strMbpath);

		int iPos = 2;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDownTemplate(strDevName, iBaudRate,iPos,szBuf);
		if (nRet != 0) {
			ShowDlg("下载模板失败");
			return;
		}
		ShowDlg(strMbpath+"\r\n下载至位置["+iPos+"]");
	}

	public void OnClickDelTemplate(View view) {
		int iPos = 1;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDelTemplate(strDevName, iBaudRate,iPos);
		if (nRet == 0) {
			ShowDlg("删除模板位置："+iPos+"成功");
		}else {
			ShowDlg("删除模板位置："+iPos+"失败");
		}
	}

	public void OnClickClearTemplate(View view) {
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxClearAllTemplate(strDevName, iBaudRate);
		if (nRet == 0) {
			ShowDlg("清空模块中模板成功");
		}else {
			ShowDlg("清空模块中模板失败");
		}
	}

	public void OnClickGetTemplate(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg("获取指纹模板","请按两次手指...");
		if (m_getTemplateThread != null) {
			m_getTemplateThread.interrupt();
			m_getTemplateThread = null;
		}
		m_getTemplateThread = new GetTemplateThread();
		m_getTemplateThread.start();
	}

	private class GetTemplateThread extends Thread {
		public void run() {
			try {
				GetTemplate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void GetTemplate() {
		int nRet = 0;
		byte[] bTemplate = new byte[iTzSize];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		nRet = devDriver.mxGetTemplate(strDevName, iBaudRate, iTimeout,bTemplate);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "获取模板失败,nRet="+nRet);
			return ;
		}
		Log.i(MainActivity.TAG,"获取模板数据-----"+Arrays.toString(bTemplate));
		String strMbpath = m_strSDCardPath+"/mb.dat";
		nRet = ToolUnit.SaveData(strMbpath,bTemplate,iTzSize);
		if (nRet != 0) {
			ShowDlg("保存模板失败\r\n请检查保存路径是否有效\r\n"+strMbpath);
			return;
		}
		SendMsg(SUCCESS_MSG, "获取模板成功，模板数据保存至:\r\n"+strMbpath);
	}

	public void OnClickGetFeature(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg("获取指纹特征","请按手指...");
		if (m_getFeatureThread != null) {
			m_getFeatureThread.interrupt();
			m_getFeatureThread = null;
		}
		m_getFeatureThread = new GetFeatureThread();
		m_getFeatureThread.start();
	}

	private class GetFeatureThread extends Thread {
		public void run() {
			try {
				GetFeature();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void GetFeature() {
		int nRet = 0;
		byte[] bFeature = new byte[iTzSize];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		Calendar time1 = Calendar.getInstance();
		nRet = devDriver.mxGetFeature(strDevName, iBaudRate, iTimeout,bFeature);
		Calendar time2 = Calendar.getInstance();
		long bt_time = time2.getTimeInMillis() - time1.getTimeInMillis();
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "获取特征失败,nRet="+nRet);
			return ;
		}
		if(m_strSDCardPath!=null)
		{
			String strMbpath = m_strSDCardPath+"/tz.dat";
			nRet = ToolUnit.SaveData(strMbpath,bFeature,iTzSize);
			if (nRet != 0) {
				ShowDlg("保存特征失败\r\n请检查保存路径是否有效\r\n"+strMbpath);
				return;
			}
			SendMsg(SUCCESS_MSG, "获取特征成功，耗时:"+bt_time+"毫秒，特征数据保存至:\r\n"+strMbpath);
		}
		SendMsg(SUCCESS_MSG, "获取特征成功，耗时:"+bt_time+"毫秒\r\n");

	}

}
