package com.adrian.basemodule.activitiy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.adrian.basemodule.listener.HttpListener;
import com.adrian.basemodule.listener.HttpResponseListener;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public abstract class BaseActivity extends AppCompatActivity {
    public Activity mContext;

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityManager.getInstance().addActivity(this);
        initVariables();
        setContentView(getLayoutResId());
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(1);
        initViews();
        loadData();
    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化UI
     */
    protected abstract void initViews();

    /**
     * 数据加载
     */
    protected abstract void loadData();

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

        super.onDestroy();
        mContext = null;
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback,
                            boolean canCancel, boolean isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    /**
     * 输入法显隐控制
     * 注意修改AndroidManifest.xml中Activity的android:windowSoftInputMode属性
     * adjustResize:压缩模式.Activity主窗口总是被调整大小以便留出软键盘的空间
     * adjustPan:平移模式.Activity主窗口不调整屏幕的大小以便留出软键盘的空间,内容将自动移动以便当前焦点不被键盘覆盖和用户能总是看到输入内容的部分
     * adjustUspecified:自动模式.系统将自动选择这些模式中一种主要依赖于是否窗口的内容有任何布局视图能够滚动他们的内容
     *
     * @param show true为显示，false为隐藏
     */
    protected void showInputMethod(boolean show) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            if (true) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            } else if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 隐藏虚拟按键
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    Toast toast;

    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }

    public void showToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        }
        toast.setText(resId);
        toast.show();
    }

    public void startActivity(Class<? extends Activity> actCls) {
        Intent intent = new Intent(this, actCls);
        startActivity(intent);
    }

    protected void startActivity(Class<? extends Activity> actCls, Bundle extra) {
        Intent intent = new Intent(this, actCls);
        intent.putExtras(extra);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<? extends Activity> actCls, int requestCode) {
        Intent intent = new Intent(this, actCls);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<? extends Activity> actCls, int requestCode, Bundle options) {
        Intent intent = new Intent(this, actCls);
        startActivityForResult(intent, requestCode, options);
    }

}
