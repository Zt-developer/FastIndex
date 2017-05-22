package com.zhangtian.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Fast on 2017/5/19.
 */

public class FastIndex extends View{

    private String[] letterArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private float cellHeight;
    private boolean isDown = false;
    private Paint circlePaint;
    private float circleX;
    private float circleY;
    private int circleRadius;
    private int textHeight;

    public FastIndex(Context context) {
        this(context,null);
    }

    public FastIndex(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    Paint paint;
    public FastIndex(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        int textSize = getResources().getDimensionPixelSize(R.dimen.text_size);
        paint.setTextSize(textSize);
        //由于文字绘制的起点默认是左下角，
        paint.setTextAlign(Paint.Align.CENTER);//设置起点为底边的中心，
        circlePaint = new Paint();
        circlePaint.setColor(Color.argb(88,23,23,23));
        circleRadius = getResources().getDimensionPixelSize(R.dimen.circleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < letterArr.length; i++) {
            String text = letterArr[i];
            float x = getMeasuredWidth() / 2;//当前view宽度的一半
            //文字高度一半+格子高度一半+ i*格子高度

            textHeight = getTextHeight(text);
            float y =textHeight / 2 + cellHeight / 2 + i * cellHeight;
            paint.setColor(i == index ? Color.RED : Color.BLUE);
            canvas.drawText(text, x, y, paint);
        }
        //算Y的值 用if判断
            Log.d("tag","circleY" + circleY + "textheight" + textHeight + "cellHeight" + cellHeight + "index" + index);

            if (circleY > circleindex * cellHeight && circleY < (circleindex+1) * cellHeight){

                circleY = circleindex * cellHeight + textHeight;
            }
            canvas.drawCircle(getMeasuredWidth() / 2,circleindex == -1? -30:circleY,circleRadius,circlePaint);
//            canvas.drawRoundRect();//圆角矩形

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / letterArr.length;
    }

    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        //该方法执行完毕，bounds就有值了 相当于把text的高度赋值给bounds了
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();

    }
    int index = -1;
    int circleindex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                circleX = event.getX();
                circleY = event.getY();


                int current = (int) (event.getY() / cellHeight);
                if (current != index){
                    index = current;
                    circleindex = current;
                    Log.d("tag" , index+ "");


                    dragIndexListener.onDrag(letterArr[index]);

                }





                break;
            case MotionEvent.ACTION_UP:
                index = -1;
                dragIndexListener.OnRelease();

            break;

        }
        invalidate();

        return true;
    }
    DragIndexListener dragIndexListener = null;

    public void setDragIndexListener(DragIndexListener dragIndexListener) {
        this.dragIndexListener = dragIndexListener;
    }

    public interface DragIndexListener{
        void onDrag(String index);
        void OnRelease();
    }
}
