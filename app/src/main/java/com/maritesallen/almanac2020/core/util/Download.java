package com.maritesallen.almanac2020.core.util;

import android.content.Context;
import android.os.Environment;

import com.maritesallen.almanac2020.data.model.apis.books.Books;
import com.maritesallen.almanac2020.data.model.db.books.BookPremium;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.maritesallen.almanac2020.core.util.Constant.DIRECTORY_NOT_CREATED;
import static com.maritesallen.almanac2020.core.util.Constant.FILE_EXIST;
import static com.maritesallen.almanac2020.core.util.Constant.FILE_NOT_EXIST;

/**
 * Author : Arvindo Mondal
 * Created on 17-12-2019
 */
public class Download {
    public static final String TAG = Download.class.getSimpleName();
    private static Download download;
    private static File directory;

    private BookCallback callback;
    private BookCallback.BookPrrmium callbackPremium;

    private Download(){}

    public static Download getInstance(){
        if(download == null){
            download = new Download();
        }
        return download;
    }

    public static Download getInstance(Context context){
        directory = context.getDir(AppConstants.APP_DIRECTORY, Context.MODE_PRIVATE); //Creating an internal dir;
        if(download == null){
            download = new Download();
        }
        return download;
    }

    public File getDirectory(){
        return directory;
    }

    public void setCallback(BookCallback callback){
        this.callback = callback;
    }

    public void setCallback(BookCallback.BookPrrmium callback){
        this.callbackPremium = callback;
    }

    public void downloadBook(Books model){
//        File directory = context.getDir(AppConstants.APP_DIRECTORY, Context.MODE_PRIVATE); //Creating an internal dir;

        if (!directory.exists()) {
            Logger.e(TAG + " directory created successfully");
            directory.mkdirs();
        }
        else{
            Logger.e(TAG + " directory exist");
        }

        if(directory.exists()) {
            String fileName;
            try {
                fileName = AESUtils.encrypt(model.getFileName());
                model.setFileNameEncrypt(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                model.setFileNameEncrypt(model.getFileName());
            }
            File file = new File(directory + "/" + fileName);

            if(file.exists()){
                Logger.e(TAG + " file exist successful");
                Logger.e(TAG + file.getPath());
                Logger.e(TAG + file.getParent());
                callback.onResponse(FILE_EXIST, model);
            }
            else{
                Logger.e(TAG + " file not exist");
                callback.onResponse(FILE_NOT_EXIST, model);
            }
        }
        else{

            callback.onError(DIRECTORY_NOT_CREATED, null);
            Logger.e(TAG + " directory not exist");
        }
    }

    public void downloadBook1(Books model){
        File directory = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY);
        if (!directory.exists()) {
            Logger.e(TAG + " directory created successfully");
            directory.mkdirs();
        }
        else{
            Logger.e(TAG + " directory exist");
        }

        if(directory.exists()) {
            String fileName;
            try {
                fileName = AESUtils.encrypt(model.getFileName());
                model.setFileNameEncrypt(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                model.setFileNameEncrypt(model.getFileName());
            }
            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY + "/" +
                    fileName);

            if(file.exists()){
                Logger.e(TAG + " file exist successful");
                callback.onResponse(FILE_EXIST, model);
            }
            else{
                Logger.e(TAG + " file not exist");
                callback.onResponse(FILE_NOT_EXIST, model);
            }
        }
        else{

            callback.onError(DIRECTORY_NOT_CREATED, null);
            Logger.e(TAG + " directory not exist");
        }
    }

    public boolean saveFileToSdCard(Books model, InputStream inputStream) {
        try {
            String fileName;
            try {
                fileName = AESUtils.encrypt(model.getFileName());
                model.setFileNameEncrypt(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                model.setFileNameEncrypt(model.getFileName());
            }
            File file = new File(directory + "/" + fileName);
//            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY + "/" +
//                    model.getFileName());

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                //long fileSize = body.contentLength();
                //long fileSizeDownloaded = 0;

                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    //fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    //============================


    public void downloadBook(BookPremium model){
//        File directory = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY);
        if (!directory.exists()) {
            Logger.e(TAG + " directory created successfully");
            directory.mkdirs();
        }
        else{
            Logger.e(TAG + " directory exist");
        }

        if(directory.exists()) {
            String fileName;
            try {
                fileName = AESUtils.encrypt(model.getFileName());
                model.setFileNameEncrypt(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                model.setFileNameEncrypt(model.getFileName());
            }
            File file = new File(directory + "/" + fileName);
//            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY + "/" +
//                    model.getFileName());

            if(file.exists()){
                Logger.e(TAG + " file exist successful");
                callbackPremium
                        .onResponse(FILE_EXIST, model);
            }
            else{
                Logger.e(TAG + " file not exist");
                callbackPremium
                        .onResponse(FILE_NOT_EXIST, model);
            }
        }
        else{

            callbackPremium.onError(DIRECTORY_NOT_CREATED, null);
            Logger.e(TAG + " directory not exist");
        }
    }

    public boolean saveFileToSdCard(BookPremium model, InputStream inputStream) {
        try {
            String fileName;
            try {
                fileName = AESUtils.encrypt(model.getFileName());
                model.setFileNameEncrypt(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                fileName = model.getFileName();
                model.setFileNameEncrypt(model.getFileName());
            }
            File file = new File(directory + "/" + fileName);
            Logger.e("Saved file at:" + file.getPath());
            Logger.e("Saved absulute file at:" + file.getAbsolutePath());
//            File file = new File(Environment.getExternalStorageDirectory(), AppConstants.APP_DIRECTORY + "/" +
//                    model.getFileName());

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                //long fileSize = body.contentLength();
                //long fileSizeDownloaded = 0;

                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    //fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

//    public static class


}
