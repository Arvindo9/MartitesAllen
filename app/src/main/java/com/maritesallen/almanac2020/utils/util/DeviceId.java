package com.maritesallen.almanac2020.utils.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;

/**
 * Author : Arvindo Mondal
 * Created on 03-12-2019
 */
public class DeviceId {
    private static final int REQUEST_READ_PHONE_STATE = 10;

    private DeviceId(){}

    public static String GetDeviceId(WeakReference<Context> contextReference){
        return getDeviceId(contextReference);
//        return androidId(contextReference.get());
    }

    @SuppressLint("HardwareIds")
    private static String getDeviceId(WeakReference<Context> contextReference) {

//        int permissionCheck = ContextCompat.checkSelfPermission(contextReference.get(),
//                Manifest.permission.READ_PHONE_STATE);

        Context context = contextReference.get();
//        Activity activity = (Activity) context;
        String deviceId = "";
/*

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager)
                    contextReference.get().getSystemService(Context.TELEPHONY_SERVICE);
//            deviceId = telephonyManager.getDeviceId();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceId = telephonyManager.getImei();
            } else {
                deviceId = telephonyManager.getDeviceId();
            }
        }
*/

         deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return deviceId;
    }

    @SuppressLint("HardwareIds")
    private String getIMEI(Context context) {

        String IMEI = "";

        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);

        Activity activity = (Activity) context;

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            IMEI = telephonyManager.getDeviceId();
        }

//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        IMEI = "IMEI:"+telephonyManager.getDeviceId();

        return IMEI;
    }

    @SuppressLint("HardwareIds")
    private static String androidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    private static boolean CheckPermission(Context context, String Permission) {
        return ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED;
    }
}
