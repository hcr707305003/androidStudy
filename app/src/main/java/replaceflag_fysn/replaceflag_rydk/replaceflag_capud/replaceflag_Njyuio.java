package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class replaceflag_Njyuio extends ContextWrapper {
    public static final String TAG = replaceflag_Njyuio.class.getSimpleName();

    public static final String id = "channel_1";
    public static final String name = "notification";
    public static final String id2 = "channel_2";
    public static final String name2 = "notification2";
    public static final String id3 = "channel_3";
    public static final String name3 = "notification3";
    private NotificationManager manager;
    private Context mContext;

    public replaceflag_Njyuio(Context base) {
        super(base);
        mContext = base;
    }

    @RequiresApi(api = 26)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    @RequiresApi(api = 26)
    public void createCustomNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id2, name2, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public void sendNotificationFullScreen(int icon,String title, String content, String type, PendingIntent pi) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotificationQ
                    (icon, title, content, type, pi);
            getManager().notify(2, notification);
        }
    }

    public void sendCustomNoti(int icon,String title, String content, String type, PendingIntent pi) {
        if (Build.VERSION.SDK_INT >= 26) {
            createCustomNotificationChannel();
            Notification notification = getCustomNotificationQ
                    (icon, title, content, type, pi);
            getManager().notify(10, notification);
        }
    }

    public void clearAllNotifiication(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(2);
    }

    public  Notification getChannelNotificationQ(int icon, String title, String content, String type, PendingIntent pi) {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setTicker(content)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setFullScreenIntent(pi,true);
        if(!TextUtils.isEmpty(title)){
            notificationBuilder.setColor(Color.parseColor("#ff0000"));
        }

        Notification incomingCallNotification = notificationBuilder.build();
        return incomingCallNotification;
    }

    public  Notification getCustomNotificationQ(int icon, String title, String content, String type, PendingIntent pi) {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id2)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setTicker(content)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setCustomContentView(getContentView())
                        .setContentIntent(pi);
        if(!TextUtils.isEmpty(title)){
            notificationBuilder.setColor(Color.parseColor("#ff0000"));
        }

        Notification incomingCallNotification = notificationBuilder.build();
        return incomingCallNotification;
    }

    private RemoteViews getContentView() {
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.replaceflag_notifysmalll_rer);
        mRemoteViews.setImageViewBitmap(R.id.logo, replaceflag_Abvffg.myBitmap);
        mRemoteViews.setTextViewText(R.id.name, replaceflag_Styuty.getString(replaceflag_Cskuio.SP_NOTI_NAME, ""));
        mRemoteViews.setTextViewText(R.id.content, replaceflag_Styuty.getString(replaceflag_Cskuio.SP_NOTI_TITLE, ""));
        mRemoteViews.setTextViewText(R.id.content1, replaceflag_Styuty.getString(replaceflag_Cskuio.SP_NOTI_CONTENT, ""));
        return mRemoteViews;
    }


    public void sendNotificationFullScreen2(int icon,String title, String content, String type, PendingIntent pi) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel2();
            Notification notification = getChannelNotificationQ2
                    (icon, title, content, type, pi);
            getManager().notify(3, notification);
        }
    }

    @RequiresApi(api = 26)
    public void createNotificationChannel2() {
        NotificationChannel channel = new NotificationChannel(id3, name3, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public  Notification getChannelNotificationQ2(int icon, String title, String content, String type, PendingIntent pi) {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, id3)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setTicker(content)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setCategory(Notification.CATEGORY_CALL)
                        .setFullScreenIntent(pi,true);
        if(!TextUtils.isEmpty(title)){
            notificationBuilder.setColor(Color.parseColor("#ff0000"));
        }

        Notification incomingCallNotification = notificationBuilder.build();
        return incomingCallNotification;
    }

}
