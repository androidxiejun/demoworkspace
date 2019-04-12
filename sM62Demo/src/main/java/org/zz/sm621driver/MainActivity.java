package org.zz.sm621driver;

import java.util.Arrays;
import java.util.Calendar;

import org.zz.jni.SM62Driver;

import com.lenwotion.gpio.GpioOperate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int SUCCESS_MSG 			        = 0; // �����ɹ�
	private static final int FAILED_MSG 			            = 1; // ����ʧ��
	private static final int GET_IMG_SUCCESS_MSG      = 2; // ��ȡͼ��ɹ�
	private static final int SHOW_DLG_MSG                 = 3; //������ʾ��Ϣ
	
	//private static String DEV_NAME = "/dev/ttyMT2";
	//private static int BAUD_RATE = 115200;
	// Jni��
	SM62Driver devDriver;
	//
	int iTemplatePos   = 1; 
	// ͼ��
	int iTimeout = 10 * 1000; // �ȴ�����ָ�ĳ�ʱʱ�䣬��λ������
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
	// ����һ��������µĽ��ȵ�Handler
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // �����ޱ���
		setContentView(R.layout.activity_main);

		devDriver = new SM62Driver();
		edit_baudrate   = (EditText) findViewById(R.id.edit_baudrate);
		edit_devNodename = (EditText) findViewById(R.id.edit_devNodename);
		
		m_strSDCardPath = ToolUnit.getSDCardPath();
		if (m_strSDCardPath==null) {
			ShowDlg("��ȡsd��·��ʧ�� ");
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
	
	/* Toast�ؼ���ʾ��ʾ��Ϣ */
	public void DisplayToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	private void ShowDlg(String strMsg) {
		new AlertDialog.Builder(MainActivity.this).setTitle("��ʾ��Ϣ")
				.setMessage(strMsg)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
	
	private Handler LinkDetectedHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
//			if(msg.what != FINGER_ENROLL_MSG)
//			{
//				
//				EnableButton(true);
//			}
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
		ShowDlg("ģ���ϵ� ");
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
			ShowDlg("��ȡ�豸�汾ʧ��,nRet = "+nRet);
		}	
	}
	
	public void OnClickDevCount(View view) {
		int[] iCount = new int[1];
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxGetCount(strDevName, iBaudRate, iCount);
		if (nRet == 0) {
			ShowDlg("ģ������:"+iCount[0]);
		} else {
			ShowDlg("��ȡģ������ʧ��,nRet = "+nRet);
		}	
	}
	
	public void OnClickDetectFinger(View view) {
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDetectFinger(strDevName, iBaudRate);
		if (nRet == 0) {
			ShowDlg("����ָ");
		}else if (nRet == 2) {
			ShowDlg("����ָ");
		} else {
			ShowDlg("ͨ��ʧ��");
		}	
	}

	public void OnClickGetImg(View view) {
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_getImg).toString(),"�밴��ָ...");
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
			SendMsg(FAILED_MSG, "����ͼ��ʧ��,nRet="+nRet);	
			return ;
		}
		SendMsg(SHOW_DLG_MSG, "�����ϴ�ͼ�����Ժ�...");	
		nRet = devDriver.mxUpImg(strDevName, iBaudRate, m_bFingerImage);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "�ϴ�ͼ��ʧ��,nRet="+nRet);	
			return ;
		}
		Calendar time2 = Calendar.getInstance();
		long bt_time = time2.getTimeInMillis() - time1.getTimeInMillis();
		SendMsg(GET_IMG_SUCCESS_MSG, "��ȡͼ��ɹ�����ʱ:"+ bt_time + "ms");
	}
	
	public void OnClickEnroll(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_enroll).toString(),"�밴������ָ...");
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
	
	public void Enroll() {
		int nRet = 0;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		nRet = devDriver.mxEnrollTemplate(strDevName, iBaudRate, iTimeout,iTemplatePos);
		if (nRet != 0) {
			SendMsg(FAILED_MSG, "ע��ģ��ʧ��,nRet="+nRet);	
			return ;
		}
		SendMsg(SUCCESS_MSG, "ע��ģ��ɹ���ģ��λ��["+ iTemplatePos+"]");
	}
	
	public void OnClickSearch(View view) {
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_search).toString(),"�밴��ָ...");
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
			SendMsg(FAILED_MSG, "δ������,nRet="+nRet);	
			return ;
		}
		SendMsg(SUCCESS_MSG, "�����ɹ���������λ��["+ iResult[0]+"]");
	}
	
	public void OnClickEnroll2(View view) {
		iTemplatePos = 2;
		EnableButton(false);
		ShowProgressDlg(getString(R.string.btn_enroll).toString(),"�밴������ָ...");
		if (m_enrollThread != null) {
			m_enrollThread.interrupt();
			m_enrollThread = null;
		}
		m_enrollThread = new EnrollThread();
		m_enrollThread.start();
	}
	
	public void OnClickUpTemplate(View view) {
		int iPos = 2;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxCheckTemplate(strDevName, iBaudRate,iPos);
		if (nRet != 0) {
			ShowDlg("λ��["+iPos+"]��������Чģ��\r\n����ע��ģ��");
			return;
		}
		byte[] szBuf = new byte[iTzSize];
		nRet = devDriver.mxUpTemplate(strDevName, iBaudRate,iPos,szBuf);
		if (nRet != 0) {
			ShowDlg("�ϴ�ģ��ʧ��");
			return;
		}
		String strMbpath = m_strSDCardPath+"/mb.dat";
		nRet = ToolUnit.SaveData(strMbpath,szBuf,iTzSize);
		if (nRet != 0) {
			ShowDlg("����ģ��ʧ��\r\n���鱣��·���Ƿ���Ч\r\n"+strMbpath);
			return;
		}
		ShowDlg("λ��["+iPos+"]ģ�����ݱ�����:\r\n"+strMbpath);
	}
	
	public void OnClickDownTemplate(View view) {
		String strMbpath = m_strSDCardPath+"/mb.dat";
		long iLen = ToolUnit.getFileSizes(strMbpath);
		if (iLen < iTzSize) {
			ShowDlg("��ȡģ������ʧ��\r\n����ģ������·���Ƿ���Ч\r\n"+strMbpath);
			return;
		}
		byte[] szBuf = ToolUnit.ReadData(strMbpath);
	
		int iPos = 2;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDownTemplate(strDevName, iBaudRate,iPos,szBuf);
		if (nRet != 0) {
			ShowDlg("����ģ��ʧ��");
			return;
		}
		ShowDlg(strMbpath+"\r\n������λ��["+iPos+"]");
	}
	
	public void OnClickDelTemplate(View view) {
		int iPos = 2;
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxDelTemplate(strDevName, iBaudRate,iPos);
		if (nRet == 0) {
			ShowDlg("ɾ��ģ��λ�ã�"+iPos+"�ɹ�");
		}else {
			ShowDlg("ɾ��ģ��λ�ã�"+iPos+"ʧ��");
		}	
	}
	
	public void OnClickClearTemplate(View view) {
		String strDevName  = edit_devNodename.getText().toString();
		String strBaudRate = edit_baudrate.getText().toString();
		int iBaudRate = Integer.parseInt(strBaudRate);
		int nRet = devDriver.mxClearAllTemplate(strDevName, iBaudRate);
		if (nRet == 0) {
			ShowDlg("���ģ����ģ��ɹ�");
		}else {
			ShowDlg("���ģ����ģ��ʧ��");
		}	
	}
	
	public void OnClickGetTemplate(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg("��ȡָ��ģ��","�밴������ָ...");
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
			SendMsg(FAILED_MSG, "��ȡģ��ʧ��,nRet="+nRet);	
			return ;
		}
	
		String strMbpath = m_strSDCardPath+"/mb.dat";
		nRet = ToolUnit.SaveData(strMbpath,bTemplate,iTzSize);
		if (nRet != 0) {
			ShowDlg("����ģ��ʧ��\r\n���鱣��·���Ƿ���Ч\r\n"+strMbpath);
			return;
		}
		SendMsg(SUCCESS_MSG, "��ȡģ��ɹ���ģ�����ݱ�����:\r\n"+strMbpath);
	}
	
	public void OnClickGetFeature(View view) {
		iTemplatePos = 1;
		EnableButton(false);
		ShowProgressDlg("��ȡָ������","�밴��ָ...");
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
			SendMsg(FAILED_MSG, "��ȡ����ʧ��,nRet="+nRet);	
			return ;
		}
		if(m_strSDCardPath!=null)
		{
			String strMbpath = m_strSDCardPath+"/tz.dat";
			nRet = ToolUnit.SaveData(strMbpath,bFeature,iTzSize);
			if (nRet != 0) {
				ShowDlg("��������ʧ��\r\n���鱣��·���Ƿ���Ч\r\n"+strMbpath);
				return;
			}
			SendMsg(SUCCESS_MSG, "��ȡ�����ɹ�����ʱ:"+bt_time+"���룬�������ݱ�����:\r\n"+strMbpath);
		}
		SendMsg(SUCCESS_MSG, "��ȡ�����ɹ�����ʱ:"+bt_time+"����\r\n");
		
	}
	
}
