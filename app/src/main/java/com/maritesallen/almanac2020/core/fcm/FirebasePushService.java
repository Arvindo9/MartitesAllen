package com.maritesallen.almanac2020.core.fcm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.ui.dashboard.DashboardActivity;
import com.maritesallen.almanac2020.ui.splash.SplashActivity;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.Logger;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.maritesallen.almanac2020.utils.AppConstants.CHANNEL_ID;
import static com.maritesallen.almanac2020.utils.AppConstants.CHANNEL_NAME;

/**
 * Author : Arvindo Mondal
 * Created on 12-02-2020
 */
public class FirebasePushService extends FirebaseMessagingService {

    private static final String TAG = "FirebasePushService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a Notification payload.
        if (remoteMessage.getNotification() != null) {
            Logger.d("Message Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotificationPayload(remoteMessage.getNotification());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
/*

            if (*/
/* Check if data needs to be processed by long running job *//*
 true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
*/

            JSONObject json = null;
            try {
                json = new JSONObject(remoteMessage.getData().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(json != null) {
                try {
                    handleNotificationData(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(@NotNull String token) {
        Log.e(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob() {
        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.e(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

    }

    //==============================================================


    private void handleNotificationData(JSONObject jsonObject) throws JSONException {
        //message will contain the Push Message
//        final String notificationId = remoteMessage.get("notification_id");

/*
        final String message = remoteMessage.get("message");
        final String title = remoteMessage.get("title");
        final String type = remoteMessage.get("type");
        final String date = remoteMessage.get("date");
        String image = remoteMessage.get("image");
*/
        JSONObject jObject = jsonObject.getJSONObject("data");
        final String type = jObject.getString("type");

        if (type.equalsIgnoreCase(AppConstants.NOTIFICATION_TOPIC_GENERAL)) {
            final String message = jObject.getString("message");
            final String title = jObject.getString("title");
            final String date = jObject.getString("date");
            String image = jObject.getString("image");

            Bitmap bitmap = getBitmapfromUrl(image);

            if (bitmap != null) {
                showNotification(title, message, bitmap);
            } else {
                new Handler(Looper.getMainLooper()).post(() ->
                        new generatePictureStyleNotification(title, message, image).execute());
            }
            sendBrodcast();
        }
        else if (type.equalsIgnoreCase(AppConstants.NOTIFICATION_TOPIC_LOGOUT)) {
            final String logout = jObject.getString("logout");
            setForLogout(logout);
        }
    }

    private void setForLogout(String logout) {
        if(logout.equals(AppConstants.LOGOUT_CODE)){
            Intent intent = new Intent(AppConstants.INTENT_SERVER_RECEIVE);
            intent.putExtra(AppConstants.KEY_FROM_SERVICE, AppConstants.LOGOUT_CODE);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
        else if(logout.equals(AppConstants.BLOCK_CODE)){
            Intent intent = new Intent(AppConstants.INTENT_SERVER_RECEIVE);
            intent.putExtra(AppConstants.KEY_FROM_SERVICE, AppConstants.BLOCK_CODE);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    private void handleNotificationPayload(RemoteMessage.Notification notification) {

    }
    // [END receive_message]


    // [START on_new_token]


    /**
     * Create and show a simple Notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo Notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of Notification */, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /*get bitmap image from the URL received in background*/
    @SuppressLint("StaticFieldLeak")
    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private String title;
        private String message;
        private String image;
        private Bitmap bitmap;

        public generatePictureStyleNotification(String title, String message, String image) {
            super();
            this.title = title;
            this.message = message;
            this.image = image;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = getBitmapfromUrl(this.image);
            if (bitmap != null) {
                return bitmap;
            } else {
                return null;
            }

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            showNotification(title, message, bitmap);
        }
    }

    private void sendBrodcast() {
        Intent intent = new Intent("notification_detect");
        // You can also include some extra data.
        intent.putExtra("message", "Update Notification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void showNotification(String title, String message, Bitmap bitmap) {
//        String CHANNEL_ID = this.getPackageName() + "BroadCast";

        String notifyTime = "";
        RemoteViews contentViewSmall = new RemoteViews(getPackageName(), R.layout.base_push_notification_small);
        if (bitmap != null) {
            contentViewSmall.setImageViewBitmap(R.id.image, bitmap);
            contentViewSmall.setImageViewResource(R.id.app_icon, R.mipmap.ic_launcher_round);
            contentViewSmall.setViewVisibility(R.id.image, View.VISIBLE);
            contentViewSmall.setViewVisibility(R.id.app_icon, View.VISIBLE);
            contentViewSmall.setViewVisibility(R.id.image_default, View.GONE);
        } else {
            contentViewSmall.setImageViewResource(R.id.image_default, R.mipmap.ic_launcher_round);

            contentViewSmall.setViewVisibility(R.id.image, View.GONE);
            contentViewSmall.setViewVisibility(R.id.app_icon, View.GONE);
            contentViewSmall.setViewVisibility(R.id.image_default, View.VISIBLE);
        }

        notifyTime = DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME);
        contentViewSmall.setTextViewText(R.id.title, title);
        contentViewSmall.setTextViewText(R.id.time, notifyTime);
        contentViewSmall.setTextViewText(R.id.text, message);


        RemoteViews contentViewBig = new RemoteViews(getPackageName(), R.layout.base_push_notification_big);

        if (bitmap != null) {
            contentViewBig.setImageViewBitmap(R.id.image, bitmap);
            contentViewBig.setImageViewBitmap(R.id.image_big, bitmap);
            contentViewBig.setImageViewResource(R.id.app_icon, R.mipmap.ic_launcher_round);
            contentViewBig.setViewVisibility(R.id.image, View.VISIBLE);
            contentViewBig.setViewVisibility(R.id.app_icon, View.VISIBLE);
            contentViewBig.setViewVisibility(R.id.image_default, View.GONE);
        } else {
            contentViewBig.setImageViewResource(R.id.image_default, R.mipmap.ic_launcher_round);

            contentViewBig.setViewVisibility(R.id.image, View.GONE);
            contentViewBig.setViewVisibility(R.id.app_icon, View.GONE);
            contentViewBig.setViewVisibility(R.id.image_default, View.VISIBLE);
        }

        contentViewBig.setTextViewText(R.id.time, DateUtils.formatDateTime(this,
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        contentViewBig.setTextViewText(R.id.title, title);
        contentViewBig.setTextViewText(R.id.text, message);
        contentViewBig.setTextViewText(R.id.textBig, message);


        Intent intent = new Intent(this, DashboardActivity.class);
//        intent.putExtra("Key", key);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                AppConstants.PUSH_NOTIFICATION_ID /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)

                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setCustomContentView(contentViewSmall)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(Notification.PRIORITY_MAX)
                .setGroup(this.getPackageName() + ".Notification")
//                .setGroup(this.getPackageName() + "." + type)
                .setChannelId(CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent);
        if (bitmap != null) {
            notificationBuilder.setCustomBigContentView(contentViewBig);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        if (notificationManager != null) {
            notificationManager.notify(AppConstants.PUSH_NOTIFICATION_ID, notificationBuilder.build());
        }
    }
}
