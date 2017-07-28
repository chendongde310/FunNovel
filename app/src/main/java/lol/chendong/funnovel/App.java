package lol.chendong.funnovel;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.hawk.Hawk;

import lol.chendong.funnovel.ui.AppCallback;

/**
 * Created by chen on 2017/7/28.
 */

public class App extends Application {
    public static App get;
    AppCallback callback;

    @Override
    public void onCreate() {
        super.onCreate();
        get = this;
        Hawk.init(this).build();
        callback = new AppCallback();
        registerActivityLifecycleCallbacks(callback);
    }


    public void exit() {
        for (Activity a : callback.getMap().values()) {
            a.finish();
        }
        System.exit(0);
    }


}
