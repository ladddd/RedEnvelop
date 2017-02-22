package com.ladddd.redenvelop;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.ladddd.redenveloprain.RedEnvelopRainView;

/**
 * Created by ladddd on 2017/2/20.
 */

public class RedEnvelopActivity extends Activity implements RedEnvelopRainView.EventListener{

    private ViewGroup rootView;
    private RedEnvelopRainView redEnvelopRainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelop);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        rootView = (ViewGroup)getWindow().getDecorView().findViewById(android.R.id.content);
        redEnvelopRainView = new RedEnvelopRainView(RedEnvelopActivity.this, RedEnvelopActivity.this);
        rootView.addView(redEnvelopRainView);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
    }

    @Override
    public void onTimeTick(long leftSecond) {

    }

    @Override
    public void onRainStop() {

    }

    @Override
    public void onRedEnvelopUnfold(float present, String bonusId) {

    }

    @Override
    protected void onDestroy() {
        if (redEnvelopRainView != null) {
            redEnvelopRainView.onDestroy();
        }
        super.onDestroy();
    }
}
