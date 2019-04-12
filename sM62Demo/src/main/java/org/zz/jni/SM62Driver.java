
package org.zz.jni;

public class SM62Driver{
	
	static {
		System.loadLibrary("SM62Driver");
	}
	
	/****************************************************************************
	 功  能：获取设备版本
	 参  数：szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate           - 波特率，默认为57600
	 * 			szVersion              - 版本信息，100字节
	 返  回： 0    - 成功
	 *         其他  - 失败
	 *****************************************************************************/
	public native int mxGetDeviceVersion(String szDevNodeName,int nBaudRate,byte[] szVersion);
	
	/****************************************************************************
	 功  能：探测手指
	 参  数：szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate           - 波特率，默认为57600
	 返  回：0   - 有手指
	 *         2   - 无手指
	 *       其他 - 通信失败
	 *****************************************************************************/
	public native int mxDetectFinger(String szDevNodeName,int nBaudRate);
	
	/****************************************************************************
	 功  能：生成图像
	 参  数：szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate           - 波特率，默认为57600
	 * 			nTimeOut              - 等待手指时间，单位：毫秒,大于等于0，其中0，表示无限等待。
	 返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxGenImg(String szDevNodeName,int nBaudRate,int nTimeOut);
	
	/****************************************************************************
	 功  能：取消生成图像
	 参  数：
	 返  回：
	 *****************************************************************************/
	public native void mxCancel();
	
	/****************************************************************************
	 功  能：上传图像
	 参  数：szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate           - 波特率，默认为57600
	 * 			 szImgBuf              - 接收图像数据缓存，256*304字节
	 返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxUpImg(String szDevNodeName,int nBaudRate,byte[] szImgBuf);
	
	/****************************************************************************
	功	能：获取设备存储容量
	参  数：szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate           - 波特率，默认为57600
	 * 			iCount   			   - 存储容量
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int  mxGetCount(String szDevNodeName,int nBaudRate,int[] iCount);

	/****************************************************************************
	功	能：查询指定存储位置是否存在有效模板
	参  数： szDevNodeName  - 串口设备节点名称
	 * 			nBaudRate            - 波特率，默认为57600
	 * 			iPos     					- 存储位置
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxCheckTemplate(String szDevNodeName,int nBaudRate,int iPos);

	/****************************************************************************
	功	能：登记模板（生成模板，并保存到指定存储位置）
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			nTimeOut               - 等待手指时间，单位：毫秒; 大于等于0，其中0，表示无限等待。
	 * 			iPos                       - 存储位置
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int  mxEnrollTemplate(String szDevNodeName,int nBaudRate,int nTimeOut,int iPos);

	/****************************************************************************
	功	能：上传指纹模板
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			iPos                       - 存储位置	
	 * 			szBuf                     - 接收模板数据缓存，256字节
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxUpTemplate(String szDevNodeName,int nBaudRate,int iPos,byte[] szBuf);

	/****************************************************************************
	功	能：下载指纹模板
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			iPos                       - 存储位置	
	 * 			szBuf                     - 接收模板数据缓存，256字节
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxDownTemplate(String szDevNodeName,int nBaudRate,int iPos,byte[] szBuf);

	/****************************************************************************
	功	能：删除指纹模板
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			iPos                       - 存储位置	
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxDelTemplate(String szDevNodeName,int nBaudRate,int iPos);

	/****************************************************************************
	功	能：清空指纹库中模板
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxClearAllTemplate(String szDevNodeName,int nBaudRate);
	
	/****************************************************************************
	功	能：搜索整个或部分指纹库
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			nTimeOut    			 - 等待手指时间，单位：毫秒; 大于等于0，其中0，表示无限等待。
	 * 			iStartPos    			 - 搜索的起始存储位置
	 * 			iTotalNum              - 搜索的模板数量
	 * 			iResult                    - [out]搜索到的模板存储位置
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxFingerSearch(String szDevNodeName,int nBaudRate,int nTimeOut,int iStartPos,int iTotalNum,int[] iResult);
	
	
	/****************************************************************************
	功	能：获取指纹模板
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			nTimeOut               - 等待手指时间，单位：毫秒; 大于等于0，其中0，表示无限等待。
	 * 			bMbData                - 指纹模板数据（256字节）
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int  mxGetTemplate(String szDevNodeName,int nBaudRate,int nTimeOut,byte[] bMbData);
	
	/****************************************************************************
	功	能：获取指纹特征
	参  数：szDevNodeName    - 串口设备节点名称
	 * 			nBaudRate             - 波特率，默认为57600
	 * 			nTimeOut               - 等待手指时间，单位：毫秒; 大于等于0，其中0，表示无限等待。
	 * 			bTzData                - 指纹特征数据（256字节）
	返  回：0：成功，其他：失败
	 *****************************************************************************/
	public native int  mxGetFeature(String szDevNodeName,int nBaudRate,int nTimeOut,byte[] bTzData);
	
	/****************************************************************************
	功	能：设置模块通讯波特率
	参  	数：szDevNodeName    - 串口设备节点名称
			ibaudRate     - 原始波特率
			ibaundBateNew - 待设置波特率
	返 	 回：0：成功，其他：失败
	 *****************************************************************************/
	public native int mxSetBps(String szDevNodeName, int ibaudRate, int ibaundBateNew);
}
