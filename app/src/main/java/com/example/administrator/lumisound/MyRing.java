package com.example.administrator.lumisound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyRing extends View {
	//�ص��ӿ�
	IView mIView = null;
	//���ûص��ӿ�
	public void setIView(IView mIView) {
		this.mIView = mIView;
	}
	
	Context mContext = null;
	private RectF oval = null;
	private double width = 0;
	private double height = 0;
	private final String TAG ="TAG";
	private Paint mPaint = null;
	private double radius = 0;
	private double touchRad = 0;
	private double touchAngle = 0;
	
	private double startAngle = 33;
	
	private double touchTargetAngle = 0;
	
	private double viewCenterX = 0;
	private double viewCenterY = 0;
	
	public MyRing(Context context, AttributeSet attrs) {
		super( context, attrs );
		init(context);
	}
	 
	public MyRing(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
		init(context);
	}
	
	public MyRing(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context pContext){
		//����Context
		mContext = pContext;
		Log.d("TAG","My ring  width " + width +  "  height " + height);
		//ʵ���
		mPaint = new Paint();
		//����view�ļ����¼�
		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//����Ƕ�
				touchRad = Math.atan2(event.getY()-viewCenterX, event.getX()-viewCenterX);
				touchAngle =  touchRad *180 / Math.PI;
				//���Ƕ�ת�� 
				touchTargetAngle = touchAngle -90;
				
				if(!(touchTargetAngle > 0 && touchTargetAngle <90)){
					touchTargetAngle += 360;
				}
				
				//Log.d("TAG"," touchDecarAngle "+ touchTargetAngle)  ;
				if(!(touchTargetAngle < startAngle || touchTargetAngle > 360-startAngle)){
					//Log.d("TAG","value " + (touchTargetAngle - 11)*100/(360-2*startAngle) );
					//�жϽӿ��Ƿ�Ϊ�գ���Ϊ�յĻ������ûص�����
					if(mIView != null){
						mIView.valueCb((int) ((touchTargetAngle - startAngle)*100/(360-2*startAngle)));
					}
					//��ʼ�ػ�view
					invalidate();	
				}
				return true;
			}
		});	
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    super.onDraw(canvas);
	    //��ȡview ����
		width = getWidth();
		height = getHeight();
		//����Բ�İ뾶 �������
		radius = width/2 * 0.8;
		viewCenterX = width/2;
		viewCenterY = width/2;
		//���ñ��� Բ������
		setBackgroundResource(R.drawable.acc_biaopan);


	    mPaint.setAntiAlias(true);                       //���û���Ϊ�޾��  
	    mPaint.setColor(Color.YELLOW);                    //���û�����ɫ    
	    mPaint.setStrokeWidth( (float) 10.0);              //�߿�  
	    mPaint.setStyle(Style.STROKE);  
	    mPaint.setAlpha(150);
	    //����Բ���ı߿�
	    RectF oval = null;
	    if(oval == null){
	    	oval = new RectF();         //RectF����  
		    oval.left=(float) (width * 0.1);                              //���  
		    oval.top=(float) (width *0.1);                                   //�ϱ�  
		    oval.right=(float) (width *0.9);                             //�ұ�  
		    oval.bottom=(float) (width *0.9);                                //�±�	
	    }
	    mPaint.setStrokeWidth( (float) 30.0); 
	    //������
	    canvas.drawArc(oval, (float) (90+startAngle), (float) (touchTargetAngle - startAngle), false, mPaint);    //����Բ��
	    //��СԲ��
	    canvas.drawCircle((float)(viewCenterX + radius * Math.cos(touchRad)), (float)(viewCenterX + radius * Math.sin(touchRad)), 10, mPaint);
	}
}

