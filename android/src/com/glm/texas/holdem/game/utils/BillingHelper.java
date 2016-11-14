package com.glm.texas.holdem.game.utils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.android.vending.billing.IInAppBillingService;

/**
 * Created by gianluca on 08/08/16.
 */
public class BillingHelper implements ServiceConnection {
    public IInAppBillingService mService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = IInAppBillingService.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
    }
}
