package com.maritesallen.almanac2020.core.fcm;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

/**
 * Author : Arvindo Mondal
 * Created on 12-02-2020
 */
public class FirebaseConfig {

    private static FirebaseConfig config;

    public static FirebaseConfig getInstance(){
        if(config == null){
            config = new FirebaseConfig();
        }
        return config;
    }


}
