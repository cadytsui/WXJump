package com.bctc.adb;


import java.io.IOException;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.IDevice.DeviceState;
import com.bctc.adb.ADB;

/*
 * ADB���ݿ�����
 * ����adb�豸�����ӣ�adb�豸�Ĺ�����adb�豸����Ϣ����UI��
 * ÿ������ֻ��һ�����ݿ����������Բ��õ���ģʽ
 */

public class ADBDataController {
	
	// ����ģʽ
	private static ADBDataController mInstance = null;
	
	private ADB mADB;
	
	// ��ǰ�����ֻ��б�
	private IDevice[] mDevices;
				
	public static synchronized ADBDataController getInstance(){
		if(mInstance == null){
			mInstance = new ADBDataController();
		}
		return mInstance;
	}	
	
	public ADBDataController() {
		initialize();
	}
	public void initialize(){
		// ��ʼ��adb
		initializeADB();
		// ��ʼ�������豸
		initializeADBDevice();	
	}	
	
	public void initializeADB(){
		mADB = new ADB();
		if (!mADB.initialize()) {
			System.out.println("�Ҳ���adb,��ȷ�ϻ�����������adb");
		}
		else{
			System.out.println("adb��ʼ���ɹ�");
		}
	}
	
	public void initializeADBDevice(){
		mDevices = mADB.getDevices();
		if (mDevices != null && mDevices.length != 0) {
			for(int i=0; i<mDevices.length; i++){
				System.out.println("��������豸��" + mDevices[i].getSerialNumber());
			}
		}
	}
	
	public void startAdbServer(){
		String cmd = "adb start-server";
		try {
			Runtime.getRuntime().exec(cmd);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
    public synchronized void deviceChanged(IDevice iDevice, int changeMask) {   
    	String logString = "�豸�仯��"+ iDevice.getName()+" "+ iDevice.getSerialNumber()+ " "+iDevice.getState();
    	System.out.println(logString);
    	// �豸���Ӻ�״̬Ϊonline,��ʼ�����߳�
    	if(iDevice.getState() == DeviceState.ONLINE)
    	{
    		//���online״̬
    	}else
    	{
    		//���offline״̬
    	}		   	
    }

    public synchronized void deviceConnected(IDevice iDevice) {      	
    	String logString = "�豸�ո����ӣ�"+ iDevice.getName()+" "+ iDevice.getSerialNumber()+ " "+iDevice.getState();
    	System.out.println(logString);
    	// �豸���Ӻ�״̬Ϊonline,��ʼ�����߳�
    	if(iDevice.getState() == DeviceState.ONLINE)
    	{
    		//���online״̬
    	}
    	// ĳ���豸����
		mDevices = mADB.getDevices();
    }
	
	public synchronized void  deviceDisconnected(IDevice disconnectedDevice){
    	String logString = "�豸�Ͽ���"+ disconnectedDevice.getName()+" "+ disconnectedDevice.getSerialNumber()+ " "+disconnectedDevice.getState();
    	System.out.println(logString);
		// ĳ���豸�Ͽ�
		mDevices = mADB.getDevices();
	}
	
	// �ر�adb,�ͷ���Դ
	public void release(){
		System.out.println("�ر�adb,�ͷ���Դ");
		if (mADB != null) {
			mADB.terminate();
		}		
	}
	
	public ADB getADB(){
		return mADB;
	}
	
	public IDevice[] getDevices(){
		return mDevices;
	}
			
}
