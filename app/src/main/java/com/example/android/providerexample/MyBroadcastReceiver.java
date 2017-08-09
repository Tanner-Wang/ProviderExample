package com.example.android.providerexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/8/9.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    public MyBroadcastReceiver(){}

    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(SMS_RECEIVED)){

            //TODO: Do something after receiving broadcast.

        }

    }
}
