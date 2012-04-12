package nochesdecafeina.circle;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;

public class Ball extends View{
	private float x;
    private float y;
    private int r;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    public Ball(Context context, float x, float y, int r, Paint.Style style) {
        super(context);
        mPaint.setColor(0xFFFFFFFF);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(style);
        mPaint.setTextSize(30);
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    public float getX(){
    	return x;
    }
    
    public float getY(){
    	return y;
    }
    
    public float getR(){
    	return r;
    }
    
    public void setX(float value){
    	x = value;
    }
    
    public void setY(float value){
    	y = value;
    }
    
    public Paint getPaint(){
    	return mPaint;
    }
}
