package edu.hebut.dundun;

import android.app.Application;

import com.xuexiang.xui.XUI;

/**
 * 用于引入XUI
 * 不用管
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
    }
}
