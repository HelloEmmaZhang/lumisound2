package com.example.administrator.lumisound;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����Ŀ������ͨѶ����Ҫ��manifest �������������Ȩ��
 * @author Administrator
 *
 */

public class MainActivity extends Activity {
	IView mIView=null;
	Button back2d;
	Blue mblue = new Blue();
	TextView textView1,bianpan;
	ImageView imageView2;
	boolean isOn=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��ת��listview �տ�ʼ��Ҫ��ת���豸�б�
		setContentView(R.layout.activity_main);
		textView1= (TextView) findViewById(R.id.textView1);
		bianpan= (TextView) findViewById(R.id.biaopan);
		imageView2= (ImageView) findViewById(R.id.imageView2);
		imageView2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isOn=!isOn;
				if (isOn){
					mblue.sendData(Command.setLedValue(0));
				}else{
					mblue.sendData(Command.setLedValue(50));
				}
			}
		});
		back2d= (Button) findViewById(R.id.back2d);
		back2d.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MainActivity.this,DeviceList.class);
				startActivity(intent);
			}
		});



		//�ҵ� myring
		MyRing myring = (MyRing) findViewById(R.id.myring);

		//���ü���  ��myview������ʱ�򣬻Ὣ��ݻص���valueCb
		myring.setIView(new IView() {
			@Override
			public void valueCb(int value) {
				Log.d("TAG", "Led Value " + value);
				try {
					Thread.sleep(5);

				} catch (InterruptedException e) {
				}
				//��ʼ������ݡ�����ΪLED������
				Command.setLedValue(value);
				bianpan.setText(value+"");

				mblue.sendData(Command.setLedValue(value));
			}
		});
	}
	//ʵ�������࣬�̳���Bluetooth
	//��Ҫ��д����������connSucceseCb��connFail
	//���������ӳɹ�����ʧ�ܵ�ʱ�򣬻����ص�
	class Blue extends Bluetooth{
		@Override
		void connSucceseCb() {
			Log.d("TAG", "connSucceseCb");
		}
		@Override
		void connFail() {
			Log.d("TAG", "connFail");
		}
	}
	//���豸�б?��ʱ�Ļص�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//�ж�resultcode�Ƿ�Ϊ0xaa��resultcodeΪ0XAAʱ��˵���Ѿ�ѡ�����豸
		if(resultCode == 0xaa ){
			//��bundle �� ��ȡ�����豸
			Bundle myBundle  =data.getExtras();
			//����bundle�л�ȡ����ݣ�ǿתΪBluetoothDevice
			BluetoothDevice btDevice = (BluetoothDevice)myBundle.getParcelable("BundeleStr");
			//�������ĵ�ַ�Լ��豸���ӡ����
			Log.d("TAG", btDevice.getName() + "  "+btDevice.getAddress()  );
			//����������������豸����
			mblue.conn(btDevice);
			textView1.setText("Connected");
		}
	}
	//����ҳ�水�·��ؼ�ʱ������ɱ����̣�����Ӧ��
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//Process.killProcess(Process.myPid());
	}
}
