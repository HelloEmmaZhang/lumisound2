package com.example.administrator.lumisound;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public abstract class Bluetooth  extends AsyncTask<Void,byte[],Void>{
	
	String TAG = "TAG";
	//��������
	BluetoothSocket btSocket; 
	public BluetoothDevice bt;
	//����SPPͨѶ��UUID
	final String SPP_UUIDSTR = "00001101-0000-1000-8000-00805F9B34FB";   
	//SPP_UUIDSTR ���һ��UUID
    UUID SPPUUID = UUID.fromString(SPP_UUIDSTR); 
    
    //socket ͨ�ŵ��������Լ������
    private OutputStream OutputStream = null;
    private InputStream  InputStream = null;
   
    //���󷽷������ڻص������ӳɹ� ������ʧ��ʱ����
    abstract void connSucceseCb();
    abstract void connFail();
    
    //������������������Ϊ����������ֵ  true ���ӳɹ� false ����ʧ��
    boolean conndevice(BluetoothDevice pDevice){
    	try {
    		//����һ��socket����
			btSocket = bt.createRfcommSocketToServiceRecord(SPPUUID);
			//��ʼ����������������Ҫ���߳������
			btSocket.connect();
			//���ӳɹ����ȡ������� ��
			OutputStream = 	btSocket.getOutputStream();
			InputStream = btSocket.getInputStream();
			//�ɹ�����ûص�����  ֪ͨmainactivity
			connSucceseCb();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			//����ʧ�ܺ����connFail ֪ͨmain activity
			connFail();
			return false;
		}    	
    }
    //����
    public void conn(final BluetoothDevice pDevice){
    	bt = pDevice;
    	//����һ���߳�������������  
    	new Thread(){
    		public void run() {
    			//ʧ������  �����������Ӳ��ϵ����⣬�����ɹ��ʻ�����ܶ�
    			if (conndevice(pDevice) == false) {
    				//����falseʱ��������ʧ���ˣ���ʱ����������
    				conndevice(pDevice);
				}
    		};
    	}.start();
    }
    //�������  ����Ϊ��װ�õ�byte����
	@SuppressLint("NewApi") void sendData(byte[] arr){
		//�ж�socket�Ƿ�Ϊ��
    	if(btSocket!=null){
    		//�ж�socket�Ƿ�������״̬
	    	if(btSocket.isConnected()){
		    	try {
		    		//���Ϊ����״̬�� �������
					OutputStream.write(arr);
					Log.d("TAG", "send success");
				} catch (IOException e) {
				}
	    	}else {
			}
    	}
	}	
    //�Ͽ�����   
    @SuppressLint("NewApi") void disconn(){
    	//�ж�socket�Ƿ�Ϊ��
    	if(btSocket != null){
    		//�ж�socket�Ƿ�������״̬
    		if(btSocket.isConnected()){
    			//��ʼ�� �������رգ�������ر� ���socket�ر�
    			try {
    				InputStream.close();
    				OutputStream.close();
    				btSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }


	@Override
	protected Void doInBackground(Void... params) {
	while(btSocket!=null){

	}


		return null;
	}
}
