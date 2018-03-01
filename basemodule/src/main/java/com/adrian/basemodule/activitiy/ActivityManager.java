package com.adrian.basemodule.activitiy;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class ActivityManager {
    private List<Activity> activityList;
    private static ActivityManager instance;
    public static String MAIN_ACTIVITY_NAME = "";

    private ActivityManager() {
        activityList = new LinkedList<Activity>();
    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void clear() {
        activityList.clear();
    }

    public void finishAll() {
        synchronized (activityList) {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList.clear();
        }
    }

    public boolean isLastActivity(String className) {
        if (activityList.size() <= 0) return false;
        Activity activity = activityList.get(activityList.size() - 1);
        return activity.getClass().getName().equals(className);
    }

    public Activity getLastActivity() {
        if (activityList.size() <= 0) return null;
        Activity activity = activityList.get(activityList.size() - 1);
        return activity;
    }

    public Activity getActivity(String className) {
        Activity result = null;
        for (Activity activity : activityList) {
            if (className.equals(activity.getClass().getName())) {
                result = activity;
                break;
            }
        }
        return result;
    }

    public void toMainActivity(String className) {
        synchronized (activityList) {
            Activity main = null;
            for (Activity activity : activityList) {
                if (!activity.getClass().getName().equals(className)) {
                    activity.finish();
                } else {
                    if (main == null) {
                        main = activity;
                    } else {
                        if (main instanceof BaseActivity) {
                            main.finish();
                        }
                        main = activity;
                    }
                }
            }
            activityList.clear();
            if (main == null) {
            } else {
                activityList.add(main);
            }
        }
    }
}
