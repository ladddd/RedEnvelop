package com.ladddd.redenveloprain;

import android.content.Context;

/**
 * Created by ladddd on 2017/2/20.
 */
public class RedEnvelopFactory {

    public static RedEnvelop createRedEnvelop(int bitmapWidth, Context context) {
        RedEnvelop redEnvelop = new RedEnvelop(context);
        redEnvelop.setBitmapWidth(bitmapWidth);
        redEnvelop.resetXY();
        //根据起始x轴位置 设置方向
        redEnvelop.setSpeedY(10 + (float) Math.random() * 20);
        float rotation;
        if (redEnvelop.getX() < Utils.getScreenWidth(context)/2) {
            rotation = (float) Math.random() * 30 - 30;
            redEnvelop.setRotation(rotation);
        } else {
            rotation = (float) Math.random() * 30;
            redEnvelop.setRotation(rotation);
        }
        redEnvelop.setSpeedX(redEnvelop.getSpeedY() * (float) Math.tan(Math.toRadians(rotation)) * -1);

        if (redEnvelop.getSpeedY() > 25) {
            redEnvelop.setScale(0.75f);
        } else if (redEnvelop.getSpeedY() > 20) {
            redEnvelop.setScale(0.9f);
        } else {
            redEnvelop.setScale(1);
        }

        return redEnvelop;
    }
}
