package com.example.administrator.lumisound;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

@SuppressLint("NewApi")
 public class Command {

    /*����LEDֵ*/   
    public static byte[] setLedValue(int value){
    	
    	byte[] arr = {(byte)0xfa,	//0
    				  (byte)0x01,	//1
    				  (byte)0x04,	//2
    				  (byte)value,	//3
    				  (byte)((0xfa+0x01+0x04+value)%0xff),	//4
    				  (byte)0xaf};	//5
        return arr;     	
    }
    
    /*��������ֵ*/
    public static byte[] setSoundValue(int value){
        byte[] arr = {(byte)0xfa,	//0
        		      (byte)0x01,	//1
        		      (byte)0x05,	//2
        		      (byte)value,	//3
        		      (byte)((0xfa+0x01+0x05+value)%0xff),	//4
        		      (byte)0xaf};	//5
        return  arr;  	
    }
}
