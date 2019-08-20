package com.atd.ducksters.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.atd.ducksters.DucksterApplication;
import com.atd.ducksters.R;
import com.atd.ducksters.common.CommonFunctions;
import com.atd.ducksters.common.CommonVar;
import com.atd.ducksters.pojo.LoginPojo;
import com.atd.ducksters.response.SplashResponse;
import com.atd.ducksters.viewmodel.SplashViewModel;
import com.atd.ducksters.viewmodelFactory.SplashViewModelFactory;

import javax.inject.Inject;

import timber.log.Timber;

import static com.atd.ducksters.common.Status.ERROR;

public class SplashActivity extends AppCompatActivity {

    public SplashViewModel viewModel;

    @Inject
    SplashViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CommonVar.loginMobile = CommonFunctions.getAppPref(this, "mobile");
        String pwd = CommonFunctions.getAppPref(this, "password");
        boolean chbRemember = CommonFunctions.getAppPrefBoolean(this, "chbRemember");
//        if (chbRemember && (!CommonVar.loginMobile.isEmpty()) && (!pwd.isEmpty())) {
            ((DucksterApplication) getApplication())
                    .getAppComponents()
                    .injectSplash(this);
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
            viewModel.directLogin().observe(this, response -> processResponse(response));
        viewModel.setSplashServiceImpl("1");
//        } else {
//            new Handler().postDelayed(() -> {
//                final Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
//                SplashActivity.this.startActivity(mainIntent);
//                SplashActivity.this.finish();
//            }, 2000);
//        }

    }

    public void processResponse(SplashResponse response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    public void renderLoadingState() {
//        Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
    }

    public void renderDataState(LoginPojo loginPojo) {
//        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    public void renderErrorState(Throwable throwable) {
        Timber.e(throwable);
//        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }
}
