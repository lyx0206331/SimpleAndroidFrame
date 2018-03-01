package com.adrian.basemodule;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by qing on 2018/3/1 0001.
 */

public class BaseModule {

    private static Context appContext;

    public static void init(@NonNull Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        if (appContext == null) {
            throw new NullPointerException("please initialize the module first.");
        }
        return appContext;
    }
}
