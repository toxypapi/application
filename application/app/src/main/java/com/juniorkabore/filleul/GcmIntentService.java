package com.juniorkabore.filleul;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    final DatabaseHandler db = new DatabaseHandler(this);
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private static final String TAG = "gcmIntent";
    private static final String TAGDEBUG = "DebugGcmIntent";

    public String type = "sent";



    public GcmIntentService() {
        super("GcmIntentService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

       /* Log.d(TAG,"in gcm intent message "+messageType);
        Log.d(TAG,"in gcm intent message bundle "+extras);*/

        Log.d(TAG,"in gcm intent message "+messageType);
        Log.d(TAG,"in gcm intent message bundle "+extras);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                String recieved_message=intent.getStringExtra("text_message");
                Log.d(TAG,"in gcm intent message bundle recieved_message : "+recieved_message);
                if(!recieved_message.isEmpty()) {
                    sendNotification("message recieved :" + recieved_message);
                    Intent sendIntent = new Intent("message_recieved");
                    sendIntent.putExtra("message", recieved_message);
                    //type = "recieve";
                    //message = recieved_message;
                  //  newChat.showChat(type,message);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
                }else{
                    Log.d(TAGDEBUG,"recieve message est null");
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }







    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
  /*  private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NewChat.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logosmall)
                        .setContentTitle("Filleul")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg);


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }*/






    //test







    /*public void sendNotification(String msg) {



        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NewChat.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logosmall)
                        .setContentTitle("Filleul")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg);


        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }*/

    //test







    public void sendNotification(String msg) {



        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logosmall)
                        .setContentTitle("Filleul")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setContentText(msg);



        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }

















}
