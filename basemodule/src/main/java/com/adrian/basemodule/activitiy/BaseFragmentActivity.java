package com.adrian.basemodule.activitiy;

import android.support.v4.app.Fragment;

/**
 * Created by adrian on 16-7-17 00:36.
 */
public class BaseFragmentActivity extends BaseActivity {

    protected Fragment fromFragment;

    protected void switchFragment(Fragment fragment, int layoutId) {
        if (null == fragment || fromFragment == fragment) {
            return;
        }
        try {
            if (!fragment.isAdded()) {
                if (null == fromFragment) {
                    getSupportFragmentManager().beginTransaction().add(layoutId, fragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().hide(fromFragment).add(layoutId, fragment).commit();
                }
            } else {
                getSupportFragmentManager().beginTransaction().hide(fromFragment).show(fragment).commit();
            }
            fromFragment = fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fromFragment = null;
    }
}
