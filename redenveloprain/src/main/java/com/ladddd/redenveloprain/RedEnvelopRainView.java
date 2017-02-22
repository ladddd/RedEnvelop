package com.ladddd.redenveloprain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ladddd on 2017/2/20.
 */
public class RedEnvelopRainView extends View {

    private MyThread thread;
    private EventListener listener;
    private Bitmap bitmap;
    private Bitmap emptyBitmap1;
    private Bitmap emptyBitmap2;
    private Bitmap emptyBitmap3;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private List<RedEnvelop> redEnvelopList = new LinkedList<>();
    private List<RedEnvelop> clickedList = new LinkedList<>();
    private boolean isGameOver = false; //游戏结束, 不能点击
    private Random random;
    private int count = 100;
    private int time = 15000; //15s
    private int timeInterval = 250;
    private boolean isFinish = false;

    public RedEnvelopRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RedEnvelopRainView(final Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_redbag);
        emptyBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_air_a);
        emptyBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_air_b);
        emptyBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.ico_air_c);

        random = new Random();
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                RedEnvelopRainView.this.post(new Runnable() {
                    @Override
                    public void run() {
                        int number = getDropNumber(); //计算得到每一波红包个数
                        for (int i = 0; i < number; i++) {
                            RedEnvelop redEnvelop = RedEnvelopFactory.createRedEnvelop(bitmap.getWidth(), context);
                            redEnvelopList.add(redEnvelop);
                        }
                        count-=number;
                        time-=timeInterval;
                        if (time % 1000 == 0) {
                            if (listener != null) {
                                listener.onSecondPassed(time/1000);
                            }
                        }
                        if (time == 0) {
                            isGameOver = true;
                            if (listener != null) {
                                listener.onRainStop();
                            }
                            timer.cancel();
                            timer.purge();
                        }
                    }
                });
            }
        }, 0, timeInterval); //几秒一波 0.2s
    }

    private int getDropNumber() {
        if (count <= 0) {
            return 0;
        }
        //结束前1秒不再掉落
        int leftDropTime = (time - 1000)/timeInterval; //剩余掉落次数
        if (leftDropTime > 0) {
            return count / leftDropTime + ((random.nextInt(100) % 2 == 0)?1:0);
        }
        return 0;
    }

    public RedEnvelopRainView(Context context, EventListener listener) {
        this(context);
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isGameOver) {
            return false;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            checkClickPosition(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN||
                event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN||
                event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN||
                event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
            //支持多点
            int pointerIndex = event.getActionIndex();
            checkClickPosition(event.getX(pointerIndex), event.getY(pointerIndex));
        }
        return true;
    }

    private void checkClickPosition(float touchX, float touchY) {
        for (int i = redEnvelopList.size() - 1; i >= 0; i--) {
            RedEnvelop redEnvelop = redEnvelopList.get(i);
            if (redEnvelop.getState() != RedEnvelop.STATE_CLICKED
                    && redEnvelop.getBitmapRect() != null
                    && redEnvelop.getBitmapRect().contains(touchX, touchY)) {
                redEnvelop.setState(RedEnvelop.STATE_CLICKED);
                clickedList.add(redEnvelop);
                if (listener != null){
                    listener.onRedEnvelopUnfold(redEnvelop.getPresent(), redEnvelop.getBonusId());
                }
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (thread == null) {
            thread = new MyThread();
            thread.start();
        }
        drawSub(canvas);
    }

    private void drawSub(Canvas canvas) {
        for (int i = 0; i < redEnvelopList.size(); i++) {
            toAppear(canvas, i);
        }
        //应该浮在所有红包的上层，不会被其他红包遮挡
        showPresent(canvas);
    }

    private void toAppear(Canvas canvas, int i) {
        RedEnvelop redEnvelop = redEnvelopList.get(i);
        if (RedEnvelop.STATE_NORMAL != redEnvelop.getState()) {
            return;
        }

        matrix.setTranslate(0, 0);
        matrix.setScale(redEnvelop.getScale(), redEnvelop.getScale());
        matrix.postRotate(redEnvelop.getRotation());
        matrix.postTranslate(redEnvelop.getX() - bitmap.getWidth()/2
                , redEnvelop.getY() - bitmap.getHeight()/2);

        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        matrix.mapRect(rect);
        canvas.drawBitmap(bitmap, matrix, paint);
        redEnvelop.setBitmapRect(rect);
    }

    private void move() {
        for (int i = 0; i < redEnvelopList.size(); i++) {
            RedEnvelop redEnvelop = redEnvelopList.get(i);
            if (RedEnvelop.STATE_NORMAL != redEnvelop.getState()) {
                continue;
            }
            redEnvelop.setY(redEnvelop.getY() + redEnvelop.getSpeedY());
            redEnvelop.setX(redEnvelop.getX() + redEnvelop.getSpeedX());
            if (redEnvelop.getY() > getHeight() + bitmap.getHeight() ||
                    redEnvelop.getX() + bitmap.getWidth() < 0 || redEnvelop.getX() > getWidth()) {
                redEnvelop.setState(RedEnvelop.STATE_MISSED);
            }
        }
    }

    private void showPresent(Canvas canvas) {
        Iterator iterator = clickedList.listIterator();
        for (int i = 0; i < clickedList.size(); i++) {
            RedEnvelop redEnvelop = (RedEnvelop) iterator.next();
            int leftFrameCount = redEnvelop.getPresentFrameCount();
            int maxPresentFrameCount = redEnvelop.getMaxPresentFrameCount();
            matrix.setTranslate(0, 0);
            matrix.setScale(redEnvelop.getScale(), redEnvelop.getScale());
            //只显示爆炸效果，不再显示金币
            if (leftFrameCount > 0) {
                if (leftFrameCount > maxPresentFrameCount * 2 / 3) {
                    matrix.postTranslate(redEnvelop.getX() - emptyBitmap1.getWidth()/2
                            , redEnvelop.getY() - emptyBitmap1.getHeight()/2);
                    canvas.drawBitmap(emptyBitmap1, matrix, paint);
                } else if (leftFrameCount > maxPresentFrameCount / 3) {
                    matrix.postTranslate(redEnvelop.getX() - emptyBitmap2.getWidth()/2
                            , redEnvelop.getY() - emptyBitmap2.getHeight()/2);
                    canvas.drawBitmap(emptyBitmap2, matrix, paint);
                } else if (leftFrameCount > 0) {
                    matrix.postTranslate(redEnvelop.getX() - emptyBitmap3.getWidth()/2
                            , redEnvelop.getY() - emptyBitmap3.getHeight()/2);
                    canvas.drawBitmap(emptyBitmap3, matrix, paint);
                }
            }
            redEnvelop.setPresentFrameCount(leftFrameCount - 1);
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (!isFinish) {
                postInvalidate();
                move();
                try {
                    sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onDestroy() {
        isFinish = true;
    }

    public interface EventListener {
        void onSecondPassed(int leftSecond);
        void onRainStop();
        void onRedEnvelopUnfold(float present, String bonusId);
    }
}
