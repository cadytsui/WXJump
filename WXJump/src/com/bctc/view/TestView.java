package com.bctc.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.bctc.adb.ADBDataController;


public class TestView extends JFrame{
	
	public TestView(){
		initView();
		init();		
	}
	
	public void init(){
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设定窗体关闭后自动退出进程
		this.setPreferredSize(new Dimension(300, 200));
		pack();
		this.setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("restriction")
	public void initView(){
		this.setLayout(new GridLayout(2,1));
		
		final JTextField text = new JTextField();
		text.setToolTipText("输入距离(单位：mm)");
				
		
		JButton play = new JButton("跳");
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				IDevice[] devices = ADBDataController.getInstance().getDevices();
				if(devices.length == 0) return;
				
				IDevice device = devices[0];
				
				// 700ms 35mm
				
				final int const_time = 700;
				final int const_distance = 35;
				
				String str_distance = text.getText();
				
				if(str_distance.isEmpty()) return;
				
				
				int distance = Integer.parseInt(str_distance);
				
				int jump_duation = distance * const_time / const_distance; 
				
				
				
				String cmd = String.format("input swipe 300 600 300 600 %d", jump_duation);
				try {
					device.executeShellCommand(cmd, new DeviceShellReceiver());
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AdbCommandRejectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ShellCommandUnresponsiveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		this.getContentPane().add(text);
		this.getContentPane().add(play);
	}
	
	private class  DeviceShellReceiver extends MultiLineReceiver {
		
		public DeviceShellReceiver(){
			super();
		}
		
		@Override
		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void processNewLines(String[] arg0) {
			// TODO Auto-generated method stub
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 初始化adb
		ADBDataController.getInstance();
		
        new TestView();
	}

}
