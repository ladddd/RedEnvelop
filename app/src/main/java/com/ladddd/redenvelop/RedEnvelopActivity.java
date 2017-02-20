package com.ladddd.redenvelop;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 陈伟达 on 2017/2/20.
 */

public class RedEnvelopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelop);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
    }
}
