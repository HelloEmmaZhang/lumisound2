package com.example.administrator.lumisound;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends Activity{
	Button back2m;
	private String TAG="back2m";
	public final static String IntentBundeleStr = "BundeleStr";
	ArrayList<String> arraylistName;
	ArrayList<BluetoothDevice> ArrayListBluetoothDevices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicelist);
		back2m= (Button) findViewById(R.id.back2m);
		back2m.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "onClick: ");
				Intent intent=new Intent(DeviceList.this,MainActivity.class);
				startActivity(intent);
			}
		});
		//�ҵ�listview
		ListView listViewDevice = (ListView) findViewById(R.id.listViewDevice);
		arraylistName = new ArrayList<String>();

		ListAdapter listitemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arraylistName);
		listViewDevice.setAdapter(listitemAdapter);
		ArrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		//��������   
		btAdapter.enable();
		//��ȡ�������������
		Set<BluetoothDevice> bondedDevices =  btAdapter.getBondedDevices();
		Log.d("TAG", "get device");

		//ѭ������������豸�������豸��ӽ��豸list�У�����Ҳ��ӽ��豸��list��
		for(BluetoothDevice mBluetoothDevice :bondedDevices ){
			Log.d("TAG", mBluetoothDevice.getAddress());
			arraylistName.add(mBluetoothDevice.getName());
			ArrayListBluetoothDevices.add(mBluetoothDevice);
		}
		//����listview�����¼�
		listViewDevice.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				//Я�������豸����mainactivity������ֵΪ0xaa��mainactivity ���յ�����ֵOXAAʱ������������

				Intent intentBackToMain = new Intent();
				Bundle mBundle = new Bundle();
				mBundle.putParcelable(IntentBundeleStr, ArrayListBluetoothDevices.get(arg2));
				intentBackToMain.putExtras(mBundle);

				intentBackToMain.setClass(DeviceList.this, MainActivity.class);
				DeviceList.this.setResult(0XAA, intentBackToMain);

				Log.d("TAG", "Intent Back To Main");
				//�ѱ��������
				finish();


			}
		});
	}
}
