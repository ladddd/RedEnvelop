package com.ladddd.redenveloprain;

import android.content.Context;
import android.graphics.RectF;
import android.view.WindowManager;

/**
 * Created by ladddd on 2017/2/20.
 */
public class RedEnvelop {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_CLICKED = -1;
    public static final int STATE_MISSED = -2;

    private Context context;

    private int state = STATE_NORMAL;
    private int screenWidth;
    private float x;
    private float y;
    private float rotation;
    private float speedY;
    private float speedX;
    private float speedR; //角速度
    private float scale;
    private int bitmapWidth;
    private float present;
    private RectF bitmapRect;
    private int maxpresentFrameCount = 30;
    private int presentFrameCount = 30;
    private String bonusId;

    public RedEnvelop(Context context) {
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void resetXY() {
        setX((float) Math.random() * screenWidth);
        setY(0f);
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getPresent() {
        return present;
    }

    public void setPresent(float present) {
        this.present = present;
    }

    public int getPresentFrameCount() {
        return presentFrameCount;
    }

    public void setPresentFrameCount(int presentFrameCount) {
        this.presentFrameCount = presentFrameCount;
    }

    public int getMaxpresentFrameCount() {
        return maxpresentFrameCount;
    }

    public void setMaxpresentFrameCount(int maxpresentFrameCount) {
        this.maxpresentFrameCount = maxpresentFrameCount;
    }

    public RectF getBitmapRect() {
        return bitmapRect;
    }

    public void setBitmapRect(RectF bitmapRect) {
        this.bitmapRect = bitmapRect;
    }

    public String getBonusId() {
        return bonusId;
    }

    public void setBonusId(String bonusId) {
        this.bonusId = bonusId;
    }
}
