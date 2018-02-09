package guominchuxing.tsbooking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import guominchuxing.tsbooking.R;


/**
 * ��iphone�����ȵĽ��������̰߳�ȫ��View����ֱ�����߳��и��½���
 * @author xiaanming
 *
 */
public class RoundProgressBar extends View {
    private static final String TAG = "RoundProgressBar";
    /**
	 * ���ʶ��������
	 */
	private Paint paint;
	private int ringBackColor = Color.parseColor("#72b2f8");// 圆环的背景色
	/**
	 * Բ������ɫ
	 */
	private int roundColor;

	/**
	 * Բ�����ȵ���ɫ
	 */
	private int roundProgressColor;

	/**
	 * �м���Ȱٷֱȵ��ַ�������ɫ
	 */
	private int textColor;

	/**
	 * �м���Ȱٷֱȵ��ַ���������
	 */
	private float textSize;

	/**
	 * Բ���Ŀ��
	 */
	private float roundWidth;

	/**
	 * ������
	 */
	private int max;

	/**
	 * ��ǰ����
	 */
	private int progress;
	/**
	 * �Ƿ���ʾ�м�Ľ���
	 */
	private boolean textIsDisplayable;

	/**
	 * ���ȵķ��ʵ�Ļ��߿���
	 */
	private int style;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	public RoundProgressBar(Context context) {
		this(context, null);
	}

	public RoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		paint = new Paint();


		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundProgressBar);

		//��ȡ�Զ������Ժ�Ĭ��ֵ
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, ringBackColor);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.rgb(255, 255, 255));
		textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.WHITE);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 50);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 50);
		max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

		mTypedArray.recycle();
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * �������Ĵ�Բ��
		 */
		int centre = getWidth()/2; //��ȡԲ�ĵ�x����
		int radius = (int) (centre - roundWidth/2); //Բ���İ뾶
        Log.e(TAG,centre+"==="+roundWidth+"---"+radius);
		paint.setColor(roundColor); //����Բ������ɫ
		paint.setStyle(Paint.Style.STROKE); //���ÿ���
		paint.setStrokeWidth(roundWidth); //����Բ���Ŀ��
		paint.setAntiAlias(true);  //�������
		canvas.drawCircle(centre, centre, radius, paint); //����Բ��

//		Log.e("log", centre + "");

		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0);
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		paint.setTypeface(Typeface.DEFAULT_BOLD); //��������
		int percent = (int)(((float)progress / (float)max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
		float textWidth = paint.measureText(percent + "%");    //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

		if(textIsDisplayable && percent != 0 && style == STROKE){
			canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize/2, paint); //�������Ȱٷֱ�
		}


		/**
		 * 画圆弧 ，画圆环的进度
		 */

		//设置进度是实心还是空心
		paint.setStrokeWidth(roundWidth); //����Բ���Ŀ��
		paint.setStyle(Paint.Style.STROKE);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(roundProgressColor);  //���ý��ȵ���ɫ
		paint.setStrokeCap(Paint.Cap.ROUND);
		RectF oval = new RectF(centre - radius, centre - radius, centre
				+ radius, centre + radius);  //���ڶ����Բ������״�ʹ�С�Ľ���

		switch (style) {
		case STROKE:{
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval, 0, 360 * progress / max, false, paint);  //���ݽ��Ȼ�Բ��
			break;
		}
		case FILL:{
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			if(progress !=0)
				canvas.drawArc(oval, 0, 360 * progress / max, true, paint);  //���ݽ��Ȼ�Բ��
			break;
		}
		}

	}


	public synchronized int getMax() {
		return max;
	}

	/**
	 * ���ý��ȵ����ֵ
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * ��ȡ����.��Ҫͬ��
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * ���ý��ȣ���Ϊ�̰߳�ȫ�ؼ������ڿ��Ƕ��ߵ����⣬��Ҫͬ��
	 * ˢ�½������postInvalidate()���ڷ�UI�߳�ˢ��
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;
			postInvalidate();
		}

	}


	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}



}
