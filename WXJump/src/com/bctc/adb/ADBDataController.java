package com.bctc.adb;


import java.io.IOException;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.IDevice.DeviceState;
import com.bctc.adb.ADB;

/*
 * ADB数据控制器
 * 负责adb设备的连接，adb设备的管理，把adb设备的信息告诉UI层
 * 每个程序只有一个数据控制器，所以采用单例模式
 */

public class ADBDataController {
	
	// 单例模式
	private static ADBDataController mInstance = null;
	
	private ADB mADB;
	
	// 当前连接手机列表
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
		// 初始化adb
		initializeADB();
		// 初始化连接设备
		initializeADBDevice();	
	}	
	
	public void initializeADB(){
		mADB = new ADB();
		if (!mADB.initialize()) {
			System.out.println("找不到adb,请确认环境变量配置adb");
		}
		else{
			System.out.println("adb初始化成功");
		}
	}
	
	public void initializeADBDevice(){
		mDevices = mADB.getDevices();
		if (mDevices != null && mDevices.length != 0) {
			for(int i=0; i<mDevices.length; i++){
				System.out.println("加入操作设备：" + mDevices[i].getSerialNumber());
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
    	String logString = "设备变化："+ iDevice.getName()+" "+ iDevice.getSerialNumber()+ " "+iDevice.getState();
    	System.out.println(logString);
    	// 设备连接后，状态为online,开始监听线程
    	if(iDevice.getState() == DeviceState.ONLINE)
    	{
    		//变成online状态
    	}else
    	{
    		//变成offline状态
    	}		   	
    }

    public synchronized void deviceConnected(IDevice iDevice) {      	
    	String logString = "设备刚刚连接："+ iDevice.getName()+" "+ iDevice.getSerialNumber()+ " "+iDevice.getState();
    	System.out.println(logString);
    	// 设备连接后，状态为online,开始监听线程
    	if(iDevice.getState() == DeviceState.ONLINE)
    	{
    		//变成online状态
    	}
    	// 某个设备连接
		mDevices = mADB.getDevices();
    }
	
	public synchronized void  deviceDisconnected(IDevice disconnectedDevice){
    	String logString = "设备断开："+ disconnectedDevice.getName()+" "+ disconnectedDevice.getSerialNumber()+ " "+disconnectedDevice.getState();
    	System.out.println(logString);
		// 某个设备断开
		mDevices = mADB.getDevices();
	}
	
	// 关闭adb,释放资源
	public void release(){
		System.out.println("关闭adb,释放资源");
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
