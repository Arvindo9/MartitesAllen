package com.maritesallen.almanac2020.core.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.maritesallen.almanac2020.BuildConfig;
import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;
import com.maritesallen.almanac2020.utils.util.General;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import poptv.library.custom.drm.DrmInterface;
import poptv.library.custom.drm.DrmKey;

import static com.maritesallen.almanac2020.core.util.Constant.DECRYPT_FILE_NOT_FOUND;

/**
 * Author : Arvindo Mondal
 * Created on 18-12-2019
 */
public class ReadBook  implements DrmInterface {
    public static final String TAG = ReadBook.class.getSimpleName();
    private BookCallback callback;
    private static ReadBook book;

    public final static int PERMISSIONS_REQUESTCODE = 220;
    // don’t change this decleration
    private final static char[] hexArray = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

// you can change set any your unique integer value for handling the things on override function

    private final int REQUEST_CODE = 2312;

// set her the paramaters

    // set her the paramaters
/*
    private String token = "krWnWsLtY2wt42gwGjL2MrY";
    private String company_id =  "2";
    private String application_id =  "8";
    private String private_key = "o5D82mFAqJ0EjLibNNsPhuQ7GxnzIVhe0b8t";
    private String public_key =  "FmB3uVACDCdSvMOsD3PqFuUwDbt437yHeJhI";

    private String projectId = "41";
    private String userId =  "37";
    private String version = "1.1.0";
    private String projectName = "Boar_Horoscope";
    */

    private String token;   // = "krWnWsLtY2wt42gwGjL2MrY";
    private String company_id;  // =  "2";
    private String application_id;  // =  "8";
    private String private_key; // = "o5D82mFAqJ0EjLibNNsPhuQ7GxnzIVhe0b8t";
    private String public_key;  // =  "FmB3uVACDCdSvMOsD3PqFuUwDbt437yHeJhI";

    private String projectId;   // = "41";
    private String userId;  // =  "37";
//    private String version; // = "1.1.0";
    private String version = BuildConfig.VERSION_NAME;
    private String projectName; // = "Boar_Horoscope";


//    private String drmId; = "b6IC1aK4K7";
    private String drmId;// = "b6IC1aK4K7";

    private static String algorithmDmr = "HmacSHA1";

    private String extension = "pdf";

    private DrmKey mDrmKey;

    private String extensionSuffix = ".pdf";

    private ReadBook(){}

    public static ReadBook getInstance(){
        if(book == null){
            book = new ReadBook();
        }
        return book;
    }

    public void setUp(
        String token,
        String company_id,
        String application_id,
        String private_key,
        String public_key,
        String projectId,
        String userId,
        String projectName
    ){
        this.token = token;
        this.company_id = company_id;
        this.application_id = application_id;
        this.private_key = private_key;
        this.public_key = public_key;
        this.projectId = projectId;
        this.userId = userId;
        this.projectName = projectName;
    }

    public void setUp(String drmId){
        this.drmId = drmId;
    }

    public void setCallback(BookCallback callback){
        this.callback = callback;
    }

    public void readFile(Context context, Books model) {
        if (hasPermissions(context, (Activity) context, PERMISSIONS_REQUESTCODE, SD_PERMISSIONS)) {

            // create class object of DrmKey
//            mDrmKey = new DrmKey(context, this);
            mDrmKey = new DrmKey(context, this, true);

            //  File file = new File(getCacheDir() + "/" + "Boar_Horoscope_2019_-_App_e_1549271467.pdf");
//            File file = mDrmKey.getFileFromAssets(context, "Boar_Horoscope_2019_-_App_e_1549271467.pdf");

            File directory = context.getDir(AppConstants.APP_DIRECTORY, Context.MODE_PRIVATE);
//            File file = new File(directory + "/" + model.getFileName());    //working code

            String fileName = "";
            try {
//                fileName = AESUtils.decrypt(model.getFileNameEncrypt());
                fileName = model.getFileNameEncrypt();
                Logger.e("Encrypt file " + fileName);
                Logger.e("Encrypt original encry " + model.getFileNameEncrypt());
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                Logger.e("Encrypt file original" + model.getFileName());
            }

            File file = new File(directory + "/" + fileName);    //working code with encryption

//            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY + "/" +
//                    model.getFileName());

            // get your file and check whether the file exist and can read
            if (file.exists() && file.canRead()) {

                // REQUIRED PARAMATERS
                mDrmKey.setUser_id(userId);
                //   mDrmKey.setUser_email("w@w.com");
                //  mDrmKey.setUser_name("rahul");
                mDrmKey.setProject_id(projectId);
                mDrmKey.setProject_name(projectName);
                mDrmKey.setVersion(version);
                mDrmKey.setApplication_token(token);
                mDrmKey.setApplication_id(application_id);
                mDrmKey.setCompany_id(company_id);
                mDrmKey.setDrm_id(drmId);
                mDrmKey.setExtension(extension);
                mDrmKey.setEncryptedfile(file.getAbsolutePath());

                // Optional paramters
                // Create Token using the function below:
                String hashparams = token + mDrmKey.getDrm_id() + application_id + company_id + public_key;

                mDrmKey.setHash_key(getHASH(hashparams, private_key));

                /*
                     First time, the App needs to be connected to the network...
                     It will assign the key first time and decrypt the file together
                     Second onward, you can use offline and online. It should work.
                     You get the respone either in onRestApiResponse if any error will generated
                     Otherwise onDecryptResponse you get the decrypted file data
                */
                mDrmKey.requestDRMKey(REQUEST_CODE);
            }
        }
    }


    // save in pdf file
    // and read by pdf reader.
    // get byte save at .pdf
    // handle here decrypted data in byte[]
    public void readDecryptFile(Context context, byte[] bytes){
        try {
            File outputDir = context.getCacheDir(); // context being the Activity pointer
            File file = File.createTempFile(General.getCurrentDateTime(), extensionSuffix, outputDir);

            if(file.exists()) {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(bytes);
                bos.flush();
                bos.close();

                callback.onFileDecryptionSuccess(file);
            }
            else{
                    Logger.e(TAG + " Cache file not found successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(DECRYPT_FILE_NOT_FOUND, e);
        }
    }

    /**
     * On onRestApiResponse override function will be used for handling all
     * error with code 400 if the error code 200 i.e success automatically the
     * function decrypt and you will get the response into the function
     * onDecryptResponse
     */
    @Override
    public void onRestApiResponse(String serverResponse, int requestCode) {

        Log.d("TAG-", serverResponse);

        callback.onError(DECRYPT_FILE_NOT_FOUND, null);

        // handle here the rest api response

        // You will get the error here, you need to prompt to the user. the message will come with the
        // error JSON for example{"code":"400","status":"error","message":"Request on server failed,
        // please checkinternet connection and retry again."}
    }

    /**
     * When everything going correct it will directly
     * decrypt and send the file data on onDecryptResponse
     * override function
     */
    @Override
    public void onDecryptResponse(byte[] getByte, int requestCode) {
        //TODO save file in cache directory and open in pdf reader
        Log.d("TAG-LENGTH", "" + getByte.length);

        if(getByte != null && getByte.length > 0){
            // handle here decrypted data in byte[]
            Log.d("TAG-LENGTH", "" + getByte.length);
            callback.onBookDecrypt(getByte, requestCode);
        }

        /*
            Note* use this byte and destroy properly whenever the activity finishes.
            Don’t save this permanently in your device. If you still want to save and delete on the fly then,
            I would recommend temporary save in application cache directly.
        */
    }

    /**
     * API request required the hash key to get the key for decrypt inside
     * the DRM library
     * You can use below function or you can use other function also if you
     * have better then this
     */
    private static String getHASH(String baseString, String private_key) {

        String hashkey = "";

        SecretKey secretKey = null;

        try {

            byte[] keyBytes = private_key.getBytes();
            secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            byte[] text = baseString.getBytes();

            // Compute the hmac on input data bytes
            byte[] hashBytes = mac.doFinal(text);
            hashkey = bytesToHex(hashBytes);
        } catch (InvalidKeyException e) {

            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashkey.trim();

    }

    public static String bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {

            int v = bytes[j] & 0xFF;

            hexChars[j * 2] = hexArray[v >>> 4];

            hexChars[j * 2 + 1] = hexArray[v & 0x0F];

        }

        return new String(hexChars);

    }

    public static String[] SD_PERMISSIONS = {Manifest.permission.INTERNET,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static boolean hasPermissions(Context context, Activity act, int req_code, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(act, new String[]{permission}, req_code);
                    return false;
                }
            }
        }
        return true;
    }
}
