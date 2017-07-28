package lol.chendong.funnovel.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/7/29 0029 - 4:48
 * 注释：
 */
public class AppCallback implements Application.ActivityLifecycleCallbacks {

    Map<String, Activity> map = new HashMap<>();

    public Map<String, Activity> getMap() {
        return map;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (map.get(activity.getLocalClassName()) != null) {
            map.get(activity.getLocalClassName()).finish();
        }
        map.put(activity.getLocalClassName(), activity);

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        map.remove(activity.getLocalClassName());
    }
}
