package org.zz.sm621driver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import android.os.Environment;

public class ToolUnit {
	

	/**
	 * @author chen.gs
	 * @category ��ȡsd��·��
	 * */
	public static String getSDCardPath(){
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //�ж�sd���Ƿ����
		if (sdCardExist)
		{
			sdDir = Environment.getExternalStorageDirectory();  //��ȡ��Ŀ¼
			return sdDir.toString();
		}
		else
		{
			return null;
		}
		
	}
	
	/**
	 * @author chen.gs
	 * @category �ж��ļ����ļ����Ƿ����
	 * @param  path �ļ���·��
	 * @return true ����  false ������
	*/
	public static Boolean isExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * @author chen.gs
	 * 
	 * @category �ж��ļ����Ƿ����,����������򴴽��ļ���
	 * @param  path �ļ���·��
	 * @return true ����  false ������
	*/
	public static Boolean AddDirectory(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return true;
	}
	
	/**
	 * @author   chen.gs
	 * @category �����ļ�������Ŀ¼
	 * @param    strMainFolder �ļ���·��
	 * @param    strSubFolders ��Ŀ¼·���б�
	 * @return   
	 * */
	public static void GetSubFolders(String strMainFolder,List<String> strSubFolders) {
	    File[] files =new File(strMainFolder).listFiles();
	    for (int i =0; i < files.length; i++){
	        File f = files[i];
	        if (f.isDirectory()){	           
	        	strSubFolders.add(f.getPath());	 
	        }
	    }
	}
	
	/**
	 * @author   chen.gs
	 * @category ����Ŀ¼����չ����
	 * @param    Path        �ļ���·��
	 * @param    Extension   ��չ��
	 * @param    strSubFiles ���ļ�·���б�
	 * @return   
	 * */
	public static void GetSubFiles(String Path, String Extension,List<String> strSubFiles) 
	{
	    File[] files =new File(Path).listFiles();
	 
	    for (int i =0; i < files.length; i++)
	    {
	        File f = files[i];
	        if (f.isFile())
	        {
	        	//�ж���չ��
	            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) 
	            	strSubFiles.add(f.getPath());
	        }
	    }
	}
	
	/**
     * @author   chen.gs
     * @category ��������Ϊ�ļ�
     * @param    filepath - �ļ�·��
     *              buffer   - ���ݻ���
     *              size     - ���ݳ���
     * @return     0    - �ɹ�
     *            ����            - ʧ��      
     * */
    public static int SaveData(String filepath, byte[] buffer, int size) {
        File f = new File(filepath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        try {
            fos.write(buffer, 0, size);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -2;
        }
        return 0;
    }
    
    /**
     * @author   chen.gs
     * @category ��ȡ�ļ���С
     * @param    filepath - �ļ�·��
     * @return   �ļ���С
     * */
	public static long getFileSizes(String filepath) {
		File f = new File(filepath);
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				s = fis.available();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return -1;
		}
		return s;
	}
   
	/**
     * @author   chen.gs
     * @category ��ȡ�ļ����ݵ�byte����
     * @param    filepath - �ļ�·��
     * @return   byte����   
     * */
    public static byte[] ReadData(String filepath){   
        File f = new File(filepath);  
        if(!f.exists()){  
        	return null;  
        }  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());  
        BufferedInputStream in = null;  
        try{  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while(-1 != (len = in.read(buffer,0,buf_size))){  
                bos.write(buffer,0,len);  
            }  
            return bos.toByteArray();  
        }catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try{  
                in.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }
		return null;  
    } 
    
    /** 
     * ɾ�������ļ� 
     * @param   sPath    ��ɾ���ļ����ļ��� 
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false 
     */  
    public static boolean deleteFile(String sPath) {  
    	boolean flag = false;  
    	File file = new File(sPath);  
        // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }  
    
    /**   
     * ׷���ļ���ʹ��RandomAccessFile   
     *    
     * @param fileName �ļ���   
     * @param content  ׷�ӵ�����   
     * @param iPos     0-��ʼλ�ã�<0 ����λ�ã�>0-ָ��λ��
     */    
    public static void AppendFile(String fileName, String content,int iPos) {   
        RandomAccessFile randomFile = null;  
        try {     
            // ��һ����������ļ���������д��ʽ      
            randomFile = new RandomAccessFile(fileName, "rw");     
            // �ļ����ȣ��ֽ���      
            long fileLength = randomFile.length(); 
            if(iPos==0)
            {
            	fileLength = 0;
            }
            else if(iPos>0)
            {
            	fileLength = iPos;
            }
            // ��д�ļ�ָ���Ƶ��ļ�β��      
            randomFile.seek(fileLength);     
            randomFile.writeBytes(content);      
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally{  
            if(randomFile != null){  
                try {  
                    randomFile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
    
    /**   
     * ׷���ļ���ʹ��RandomAccessFile   
     *    
     * @param fileName �ļ���   
     * @param content  ׷�ӵ�����   
     * @param iPos     0-��ʼλ�ã�<0 ����λ�ã�>0-ָ��λ��
     */    
    public static void AppendFile_bk(String fileName, String content,int iPos) {   
        RandomAccessFile randomFile = null;  
        try {     
            // ��һ����������ļ���������д��ʽ      
            randomFile = new RandomAccessFile(fileName, "rw");     
            // �ļ����ȣ��ֽ���      
            long fileLength = randomFile.length(); 
            if(iPos==0)
            {
             fileLength = 0;
            }
            else if(iPos>0)
            {
             fileLength = iPos;
            }
            // ��д�ļ�ָ���Ƶ��ļ�β��      
            randomFile.seek(fileLength);               
            
            int szlen = content.length();
            byte  buffer[]  =  new   byte [szlen ];
            buffer  = content.getBytes();
            
            randomFile.write(buffer);
                                  
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally{  
            if(randomFile != null){  
                try {  
                    randomFile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }
}
