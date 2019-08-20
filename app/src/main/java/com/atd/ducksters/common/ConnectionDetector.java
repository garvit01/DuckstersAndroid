package com.atd.ducksters.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class ConnectionDetector {
    private Context appContext;

    public ConnectionDetector(Context context) {
        this.appContext = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager dataNetworkInfo = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (dataNetworkInfo != null) {
            NetworkInfo[] networkArr = dataNetworkInfo.getAllNetworkInfo();
            if (networkArr != null)
                for (int i = 0; i < networkArr.length; i++)
                    if (networkArr[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
        }
        return false;
    }

    public void enableWifiNetwork() {
        WifiManager wifi = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled())
            wifi.setWifiEnabled(true);
    }
}