package lol.chendong.funnovel;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * Created by chen on 2017/7/28.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
