package com.atd.ducksters;

import android.support.multidex.MultiDexApplication;

import com.atd.ducksters.di.AppComponent;
import com.atd.ducksters.di.AppModule;
import com.atd.ducksters.di.DaggerAppComponent;

public class DucksterApplication extends MultiDexApplication {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createMyComponent();
    }

    public AppComponent getAppComponents() {
        return appComponent;
    }

    private AppComponent createMyComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();
    }
}
