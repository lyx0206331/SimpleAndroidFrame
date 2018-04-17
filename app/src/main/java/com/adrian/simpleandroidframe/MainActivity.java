package com.adrian.simpleandroidframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.adrian.basemodule.activitiy.BaseActivity;
import com.adrian.basemodule.util.ShortUrlUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {
        String[] urls = ShortUrlUtil.shortText("http://www.51bi.com/bbs/_t_278433840/");
        for (String url : urls) {
            Log.e("SHORT_URL", url);

        }
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.activity_main;
    }
}
