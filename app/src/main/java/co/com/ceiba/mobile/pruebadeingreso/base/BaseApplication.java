package co.com.ceiba.mobile.pruebadeingreso.base;

import android.app.Application;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("ARRANCA LA APP");
    }
}
